package com.jsorrell.carpetskyadditions;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.api.settings.CarpetRule;
import carpet.api.settings.InvalidRuleValueException;
import carpet.api.settings.SettingsManager;
import carpet.utils.Translations;
import com.jsorrell.carpetskyadditions.advancements.criterion.SkyAdditionsCriteriaTriggers;
import com.jsorrell.carpetskyadditions.advancements.predicates.SkyAdditionsLootItemConditions;
import com.jsorrell.carpetskyadditions.commands.SkyIslandCommand;
import com.jsorrell.carpetskyadditions.config.SkyAdditionsConfig;
import com.jsorrell.carpetskyadditions.gen.SkyBlockChunkGenerator;
import com.jsorrell.carpetskyadditions.gen.feature.SkyAdditionsFeatures;
import com.jsorrell.carpetskyadditions.helpers.PiglinBruteSpawnPredicate;
import com.jsorrell.carpetskyadditions.helpers.SkyAdditionsMinecartComparatorLogic;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.jsorrell.carpetskyadditions.settings.SkyBlockDefaults;
import com.jsorrell.carpetskyadditions.util.SkyAdditionsDataComponents;
import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.MinecartComparatorLogicRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class SkyAdditionsExtension implements CarpetExtension, ModInitializer {
    public static MinecraftServer minecraftServer;

    public static final String MOD_ID = "carpetskyadditions";
    public static final ModContainer MOD_CONTAINER =
            FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow();
    public static final String MOD_VERSION =
            MOD_CONTAINER.getMetadata().getVersion().toString();
    public static final String MOD_NAME = MOD_CONTAINER.getMetadata().getName();

    private static SettingsManager settingsManager;

    public SkyAdditionsExtension() {
        CarpetServer.manageExtension(this);
    }

    @Override
    public void onInitialize() {
        settingsManager = new SettingsManager(MOD_VERSION, MOD_ID, MOD_NAME);

        // Register SkyAdditions settings
        settingsManager.parseSettingsClass(SkyAdditionsSettings.class);

        AutoConfig.register(SkyAdditionsConfig.class, Toml4jConfigSerializer::new);

        // Register a server lifecycle event to handle configuration and settings
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

        // Restrict Piglin Brute spawning when piglinsSpawningInBastions is true
        SpawnPlacements.register(
                EntityType.PIGLIN_BRUTE,
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                new PiglinBruteSpawnPredicate());

        Registry.register(
                BuiltInRegistries.CHUNK_GENERATOR,
                new SkyAdditionsResourceLocation("skyblock").getResourceLocation(),
                SkyBlockChunkGenerator.CODEC
        );

        SkyAdditionsFeatures.registerAll();
        SkyAdditionsCriteriaTriggers.registerAll();
        SkyAdditionsLootItemConditions.bootstrap();
        SkyAdditionsDataComponents.bootstrap();
        MinecartComparatorLogicRegistry.register(EntityType.MINECART, new SkyAdditionsMinecartComparatorLogic());
        SkyAdditionsDataPacks.register();
    }

    private void onServerStarted(net.minecraft.server.MinecraftServer server) {
        SkyAdditionsSettings.LOG.info("Server started. Processing configuration files and settings.");
        minecraftServer = server;

        // Write default configuration files
        try {
            Path configPath = server.getWorldPath(net.minecraft.world.level.storage.LevelResource.ROOT);
            SkyBlockDefaults.writeDefaults(configPath);
            SkyAdditionsSettings.LOG.info("Configuration files written successfully to: " + configPath);
        } catch (IOException e) {
            SkyAdditionsSettings.LOG.error("Failed to write configuration files.", e);
        }

        // Apply Carpet settings dynamically
        applyCarpetRules(server);

        // Apply SkyAdditions settings dynamically
        applySkyAdditionsRules(server);
    }

    private void applyCarpetRules(net.minecraft.server.MinecraftServer server) {
        CarpetServer.settingsManager.getCarpetRules().forEach(rule -> {
            try {
                if ("renewableSponges".equals(rule.name())) {
                    rule.set(server.createCommandSourceStack(), "true");
                    SkyAdditionsSettings.LOG.info("Set renewableSponges to true.");
                } else if ("piglinsSpawningInBastions".equals(rule.name())) {
                    rule.set(server.createCommandSourceStack(), "true");
                    SkyAdditionsSettings.LOG.info("Set piglinsSpawningInBastions to true.");
                }
            } catch (Exception e) {
                SkyAdditionsSettings.LOG.error("Failed to apply rule: " + rule.name(), e);
            }
        });
        SkyAdditionsSettings.LOG.info("Default Carpet rules configured programmatically.");
    }

    private void applySkyAdditionsRules(net.minecraft.server.MinecraftServer server) {
        SkyAdditionsSettings.getRules().forEach((ruleName, defaultValue) -> {
            CarpetRule<?> rule = settingsManager.getCarpetRule(ruleName);
            if (rule != null) {
                try {
                    rule.set(server.createCommandSourceStack(), defaultValue.toString());
                    SkyAdditionsSettings.LOG.info("Set SkyAdditions rule: " + ruleName + " to " + defaultValue);
                } catch (InvalidRuleValueException e) {
                    SkyAdditionsSettings.LOG.error("Invalid value for SkyAdditions rule: " + ruleName, e);
                }
            } else {
                SkyAdditionsSettings.LOG.warn("SkyAdditions rule not found: " + ruleName);
            }
        });
        SkyAdditionsSettings.LOG.info("SkyAdditions rules applied dynamically.");
    }

    @Override
    public void onGameStarted() {
        settingsManager.parseSettingsClass(SkyAdditionsSettings.class);

        // Ensure rules are applied on game start
        if (CarpetServer.minecraft_server != null) {
            applySkyAdditionsRules(CarpetServer.minecraft_server);
        }
    }

    @Override
    public SettingsManager extensionSettingsManager() {
        return settingsManager;
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return Translations.getTranslationFromResourcePath(
                String.format("assets/%s/carpet/lang/%s.json", MOD_ID, lang));
    }

    @Override
    public void registerCommands(
            CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
        SkyIslandCommand.register(dispatcher);
    }

    @Override
    public String version() {
        return MOD_ID + " " + MOD_VERSION;
    }
}
