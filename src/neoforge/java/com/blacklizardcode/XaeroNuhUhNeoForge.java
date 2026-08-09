package com.blacklizardcode;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.GameRuleChangedEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod("xaeronuhuh")
public class XaeroNuhUhNeoForge {

    private static final String MOD_ID = XaeroNuhUhCommon.MOD_ID;
    public static final DeferredRegister<GameRule<?>> GAME_RULES = DeferredRegister.create(Registries.GAME_RULE, MOD_ID);

    public static final DeferredHolder<GameRule<?>, GameRule<Boolean>> DISABLE_MINIMAP
            = GAME_RULES.register("disableminimap", () -> new GameRule<Boolean>(
            GameRuleCategory.MISC, // or your own via GameRuleCategory.register(...)
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            value -> value ? 1 : 0,
            true, // default value
            FeatureFlagSet.of()
    ));

    public static final DeferredHolder<GameRule<?>, GameRule<Boolean>> ENABLE_FAIR_MODE
            = GAME_RULES.register("enablefairmode", () -> new GameRule<Boolean>(
            GameRuleCategory.MISC, // or your own via GameRuleCategory.register(...)
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            value -> value ? 1 : 0,
            true, // default value
            FeatureFlagSet.of()
    ));

    public static final DeferredHolder<GameRule<?>, GameRule<Boolean>> ENABLE_CAVE_MODE_NETHER
            = GAME_RULES.register("enablenethercavemode", () -> new GameRule<Boolean>(
            GameRuleCategory.MISC, // or your own via GameRuleCategory.register(...)
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            value -> value ? 1 : 0,
            true, // default value
            FeatureFlagSet.of()
    ));

    public XaeroNuhUhNeoForge(IEventBus modEventBus) {
        GAME_RULES.register(modEventBus);

        @EventBusSubscriber(modid = "xaeronuhuh")
        final class GameEvents {

            @SubscribeEvent
            public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
                Player player = event.getEntity();
                ServerLevel serverlevel = (ServerLevel) player.level();
                boolean disableMinimap = serverlevel.getGameRules().get(DISABLE_MINIMAP.get());
                boolean enableFairMode = serverlevel.getGameRules().get(ENABLE_FAIR_MODE.get());
                boolean enableNetherCaveMode = serverlevel.getGameRules().get(ENABLE_CAVE_MODE_NETHER.get());

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
        }
    }

}
//?}
