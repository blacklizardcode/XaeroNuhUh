package com.blacklizardcode;

import java.util.HashMap;
import java.util.Map;

//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;

import net.minecraft.world.level.gamerules.GameRuleCategory;


public class XaeroNuhUhFabric implements ModInitializer {
    public static final String MOD_ID = XaeroNuhUhCommon.MOD_ID;

    private static final Identifier DISABLE_MINIMAP_ID = Identifier.fromNamespaceAndPath(MOD_ID, "disableminimap");
    private static final Identifier ENABLE_FAIR_MODE_ID = Identifier.fromNamespaceAndPath(MOD_ID, "enablefairmode");
    private static final Identifier ENABLE_CAVE_MODE_NETHER_ID = Identifier.fromNamespaceAndPath(MOD_ID, "enablenethercavemode");

    public static GameRule<Boolean> DISABLE_MINIMAP;
    public static GameRule<Boolean> ENABLE_FAIR_MODE;
    public static GameRule<Boolean> ENABLE_CAVE_MODE_NETHER;

    private final Map<GameRule<Boolean>, Boolean> lastRuleStates = new HashMap<>();

    @Override
    @SuppressWarnings({ "nullness", "null" })
    public void onInitialize() {

        // Initialize and register gamerules
        DISABLE_MINIMAP = GameRuleBuilder
                .forBoolean(true)
                .category(GameRuleCategory.PLAYER)
                .buildAndRegister(DISABLE_MINIMAP_ID);

        ENABLE_FAIR_MODE = GameRuleBuilder
                .forBoolean(true)
                .category(GameRuleCategory.PLAYER)
                .buildAndRegister(ENABLE_FAIR_MODE_ID);

        ENABLE_CAVE_MODE_NETHER = GameRuleBuilder
                .forBoolean(true)
                .category(GameRuleCategory.PLAYER)
                .buildAndRegister(ENABLE_CAVE_MODE_NETHER_ID);

        // Initialize tracking map
        lastRuleStates.put(ENABLE_FAIR_MODE, true);
        lastRuleStates.put(DISABLE_MINIMAP, true);
        lastRuleStates.put(ENABLE_CAVE_MODE_NETHER, true);

        // Run logic on player join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> runJoinLogic(handler.getPlayer(), server.getLevel(Level.OVERWORLD)));

        // Check game rules each server tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ServerLevel world = server.getLevel(Level.OVERWORLD);

            boolean fairMode = world.getGameRules().get(ENABLE_FAIR_MODE);
            boolean worldMap = world.getGameRules().get(DISABLE_MINIMAP);
            boolean nethercavemode = world.getGameRules().get(ENABLE_CAVE_MODE_NETHER);

            boolean fairModeChanged = fairMode != lastRuleStates.get(ENABLE_FAIR_MODE);
            boolean worldMapChanged = worldMap != lastRuleStates.get(DISABLE_MINIMAP);
            boolean nethercaveModeChanged = nethercavemode != lastRuleStates.get(ENABLE_CAVE_MODE_NETHER);


            if (fairModeChanged || worldMapChanged || nethercaveModeChanged) {
                // Re-run join logic for all online players
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    runJoinLogic(player, world);
                }

                // Update lastRuleStates
                lastRuleStates.put(ENABLE_FAIR_MODE, fairMode);
                lastRuleStates.put(DISABLE_MINIMAP, worldMap);
                lastRuleStates.put(ENABLE_CAVE_MODE_NETHER, nethercavemode);
            }
        });
    }

    private void runJoinLogic(ServerPlayer player, ServerLevel world) {
        player.sendSystemMessage(Component.literal("§r§e§s§e§t§x§a§e§r§o"));

        // Use getValue() with the GameRule objects
        boolean getEnableFairMode = world.getGameRules().get(ENABLE_FAIR_MODE);
        boolean getDisableMiniMap = world.getGameRules().get(DISABLE_MINIMAP);
        boolean getEnablenethercavemode = world.getGameRules().get(ENABLE_CAVE_MODE_NETHER);

        if (getEnableFairMode) {
            player.sendSystemMessage(Component.literal("§f§a§i§r§x§a§e§r§o"));
        }
        if (getDisableMiniMap) {
            player.sendSystemMessage(Component.literal("§n§o§m§i§n§i§m§a§p"));
        }
        if (getEnablenethercavemode) {
            player.sendSystemMessage(Component.literal("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r"));
        }
    }
}
//?}