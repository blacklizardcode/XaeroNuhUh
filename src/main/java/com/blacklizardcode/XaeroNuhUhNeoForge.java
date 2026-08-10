package com.blacklizardcode;


import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;



//? if =26.2 {
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;
import net.neoforged.neoforge.event.level.GameRuleChangedEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.flag.FeatureFlagSet;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
//?}

//? if =1.21.1 {
/*import net.minecraft.world.level.GameRules;
*///?}
import net.minecraft.world.level.Level;

@Mod("xaeronuhuh")
public class XaeroNuhUhNeoForge {

    private static final String MOD_ID = XaeroNuhUhCommon.MOD_ID;

    //? if =1.21.1 {
    /*public static final GameRules.Key<GameRules.BooleanValue> DISABLE_MINIMAP =
        GameRules.register("xaeronuhuh:disableminimap", GameRules.Category.MISC, GameRules.BooleanValue.create(true, (server, rule) -> {
            gameRuleChangeEvent(server);
        }));
    public static final GameRules.Key<GameRules.BooleanValue> ENABLE_FAIR_MODE =
        GameRules.register("xaeronuhuh:enablefairmode", GameRules.Category.MISC, GameRules.BooleanValue.create(true, (server, rule) -> {
            gameRuleChangeEvent(server);
        }));
    public static final GameRules.Key<GameRules.BooleanValue> ENABLE_CAVE_MODE_NETHER =
        GameRules.register("xaeronuhuh:enablenethercavemode", GameRules.Category.MISC, GameRules.BooleanValue.create(true, (server, rule) -> {
            gameRuleChangeEvent(server);
        }));
    *///?}
    //? if =26.2 {
    
    public static final DeferredRegister<GameRule<?>> GAME_RULES = DeferredRegister.create(Registries.GAME_RULE, MOD_ID);

    public static final DeferredHolder<GameRule<?>, GameRule<Boolean>> DISABLE_MINIMAP
            = GAME_RULES.register("disableminimap", () -> new GameRule<Boolean>(
            GameRuleCategory.MISC,
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            value -> value ? 1 : 0,
            true,
            FeatureFlagSet.of()
    ));

    public static final DeferredHolder<GameRule<?>, GameRule<Boolean>> ENABLE_FAIR_MODE
            = GAME_RULES.register("enablefairmode", () -> new GameRule<Boolean>(
            GameRuleCategory.MISC,
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            value -> value ? 1 : 0,
            true,
            FeatureFlagSet.of()
    ));

    public static final DeferredHolder<GameRule<?>, GameRule<Boolean>> ENABLE_CAVE_MODE_NETHER
            = GAME_RULES.register("enablenethercavemode", () -> new GameRule<Boolean>(
            GameRuleCategory.MISC,
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            value -> value ? 1 : 0,
            true,
            FeatureFlagSet.of()
    ));
    
    //?}

    public XaeroNuhUhNeoForge(IEventBus modEventBus) {
        //? if =26.2 {
         GAME_RULES.register(modEventBus);
        //?}
        @EventBusSubscriber(modid = "xaeronuhuh")
        final class GameEvents {

            @SubscribeEvent
            public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
                Player player = event.getEntity();
                ServerLevel serverlevel = (ServerLevel) player.level();
                //? if =26.2 {
                boolean disableMinimap = serverlevel.getGameRules().get(DISABLE_MINIMAP.get());
                boolean enableFairMode = serverlevel.getGameRules().get(ENABLE_FAIR_MODE.get());
                boolean enableNetherCaveMode = serverlevel.getGameRules().get(ENABLE_CAVE_MODE_NETHER.get());
                //?}
                //? if =1.21.1 {
                /*boolean disableMinimap = serverlevel.getGameRules().getBoolean(DISABLE_MINIMAP);
                boolean enableFairMode = serverlevel.getGameRules().getBoolean(ENABLE_FAIR_MODE);
                boolean enableNetherCaveMode = serverlevel.getGameRules().getBoolean(ENABLE_CAVE_MODE_NETHER);
                *///?}
                if (disableMinimap) {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§f§a§i§r§x§a§e§r§o"));
                }
                if (enableFairMode) {
                    player.sendSystemMessage(Component.literal("§n§o§m§i§n§i§m§a§p"));
                }
                if (enableNetherCaveMode) {
                    player.sendSystemMessage(Component.literal("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r"));
                }
            }
            //? if =26.2 {
            @SubscribeEvent
            public static void onGameRuleChanged(GameRuleChangedEvent event) {
                MinecraftServer server = event.getServer();
                ServerLevel serverlevel = (ServerLevel) server.getLevel(Level.OVERWORLD);
                boolean disableMinimap = serverlevel.getGameRules().get(DISABLE_MINIMAP.get());
                boolean enableFairMode = serverlevel.getGameRules().get(ENABLE_FAIR_MODE.get());
                boolean enableNetherCaveMode = serverlevel.getGameRules().get(ENABLE_CAVE_MODE_NETHER.get());

                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    player.sendSystemMessage(Component.literal("§r§e§s§e§t§x§a§e§r§o"));
                    if (disableMinimap) {
                        player.sendSystemMessage(Component.literal("§f§a§i§r§x§a§e§r§o"));
                    }
                    if (enableFairMode) {
                        player.sendSystemMessage(Component.literal("§n§o§m§i§n§i§m§a§p"));
                    }
                    if (enableNetherCaveMode) {
                        player.sendSystemMessage(Component.literal("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r"));
                    }
                }
            }
            //?}
            //? if =1.21.1 {
            /*public static void gameRuleChangeEvent(MinecraftServer server) {
                ServerLevel serverlevel = server.getLevel(Level.OVERWORLD);
                boolean disableMinimap = serverlevel.getGameRules().getBoolean(DISABLE_MINIMAP);
                boolean enableFairMode = serverlevel.getGameRules().getBoolean(ENABLE_FAIR_MODE);
                boolean enableNetherCaveMode = serverlevel.getGameRules().getBoolean(ENABLE_CAVE_MODE_NETHER);

                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    player.sendSystemMessage(Component.literal("§r§e§s§e§t§x§a§e§r§o"));
                    if (disableMinimap) {
                        player.sendSystemMessage(Component.literal("§f§a§i§r§x§a§e§r§o"));
                    }
                    if (enableFairMode) {
                        player.sendSystemMessage(Component.literal("§n§o§m§i§n§i§m§a§p"));
                    }
                    if (enableNetherCaveMode) {
                        player.sendSystemMessage(Component.literal("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r"));
                    }
                }
            }
            *///?}
        }
    }
    //? if =1.21.1 {
    /*private static void gameRuleChangeEvent(MinecraftServer server) {
        ServerLevel serverlevel = server.getLevel(Level.OVERWORLD);
        boolean disableMinimap = serverlevel.getGameRules().getBoolean(DISABLE_MINIMAP);
        boolean enableFairMode = serverlevel.getGameRules().getBoolean(ENABLE_FAIR_MODE);
        boolean enableNetherCaveMode = serverlevel.getGameRules().getBoolean(ENABLE_CAVE_MODE_NETHER);

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.sendSystemMessage(Component.literal("§r§e§s§e§t§x§a§e§r§o"));
            if (disableMinimap) {
                player.sendSystemMessage(Component.literal("§f§a§i§r§x§a§e§r§o"));
            }
            if (enableFairMode) {
                player.sendSystemMessage(Component.literal("§n§o§m§i§n§i§m§a§p"));
            }
            if (enableNetherCaveMode) {
                player.sendSystemMessage(Component.literal("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r"));
            }
        }
    }
    *///?}
}