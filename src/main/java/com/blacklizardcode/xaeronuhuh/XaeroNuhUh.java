package com.blacklizardcode.xaeronuhuh;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class XaeroNuhUh implements ModInitializer {
    public static final String MOD_ID = "xaeronuhuh";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Define Identifiers
    private static final Identifier DISABLE_MINIMAP_ID = Identifier.of(MOD_ID, "disableminimap");
    private static final Identifier ENABLE_FAIR_MODE_ID = Identifier.of(MOD_ID, "enablefairmode");
    private static final Identifier ENABLE_CAVE_MODE_NETHER_ID = Identifier.of(MOD_ID, "enablenethercavemode");

    // Declare gamerules as GameRule<Boolean>
    public static GameRule<Boolean> DISABLE_MINIMAP;
    public static GameRule<Boolean> ENABLE_FAIR_MODE;
    public static GameRule<Boolean> ENABLE_CAVE_MODE_NETHER;


    // Track previous states using the GameRule objects as keys
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
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> runJoinLogic(handler.getPlayer(), server.getOverworld()));

        // Check game rules each server tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ServerWorld world = server.getOverworld();

            boolean fairMode = world.getGameRules().getValue(ENABLE_FAIR_MODE);
            boolean worldMap = world.getGameRules().getValue(DISABLE_MINIMAP);
            boolean nethercavemode = world.getGameRules().getValue(ENABLE_CAVE_MODE_NETHER);

            boolean fairModeChanged = fairMode != lastRuleStates.get(ENABLE_FAIR_MODE);
            boolean worldMapChanged = worldMap != lastRuleStates.get(DISABLE_MINIMAP);
            boolean nethercaveModeChanged = worldMap != lastRuleStates.get(ENABLE_CAVE_MODE_NETHER);


            if (fairModeChanged || worldMapChanged || nethercaveModeChanged) {
                // Re-run join logic for all online players
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    runJoinLogic(player, world);
                }

                // Update lastRuleStates
                lastRuleStates.put(ENABLE_FAIR_MODE, fairMode);
                lastRuleStates.put(DISABLE_MINIMAP, worldMap);
                lastRuleStates.put(ENABLE_CAVE_MODE_NETHER, nethercavemode);
            }
        });
    }

    private void runJoinLogic(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(Text.literal("§r§e§s§e§t§x§a§e§r§o"), false);

        // Use getValue() with the GameRule objects
        boolean getEnableFairMode = world.getGameRules().getValue(ENABLE_FAIR_MODE);
        boolean getDisableMiniMap = world.getGameRules().getValue(DISABLE_MINIMAP);
        boolean getEnablenethercavemode = world.getGameRules().getValue(ENABLE_CAVE_MODE_NETHER);

        if (getEnableFairMode) {
            player.sendMessage(Text.literal("§f§a§i§r§x§a§e§r§o"), false);
        }
        if (getDisableMiniMap) {
            player.sendMessage(Text.literal("§n§o§m§i§n§i§m§a§p"), false);
        }
        if (getEnablenethercavemode) {
            player.sendMessage(Text.literal("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r"), false);
        }
    }
}