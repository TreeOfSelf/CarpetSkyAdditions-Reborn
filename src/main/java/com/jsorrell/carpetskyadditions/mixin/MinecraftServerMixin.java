package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.gen.SkyBlockChunkGenerator;
import com.jsorrell.carpetskyadditions.gen.feature.SkyAdditionsConfiguredFeatures;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

// Lower priority to ensure loadWorld mixin is called before carpet loads the settings
@Mixin(value = MinecraftServer.class, priority = 999)
public abstract class MinecraftServerMixin {
    @Inject(
        method = "setInitialSpawn",
        at =
        @At(
            value = "INVOKE",
            target =
                "Lnet/minecraft/world/level/storage/ServerLevelData;setSpawn(Lnet/minecraft/core/BlockPos;F)V",
            ordinal = 1,
            shift = At.Shift.AFTER),
        cancellable = true)
    private static void generateSpawnPlatform(
            ServerLevel level,
            ServerLevelData levelData,
            boolean bonusChest,
            boolean debugWorld,
            CallbackInfo ci,
        @Local ChunkPos spawnChunk,
            @Local int spawnHeight) {
        ServerChunkCache chunkManager = level.getChunkSource();
        ChunkGenerator chunkGenerator = chunkManager.getGenerator();
        if (!(chunkGenerator instanceof SkyBlockChunkGenerator)) return;
        BlockPos worldSpawn = spawnChunk.getMiddleBlockPosition(spawnHeight);

        WorldgenRandom random = new WorldgenRandom(new LegacyRandomSource(0));
        random.setLargeFeatureSeed(level.getSeed(), spawnChunk.x, spawnChunk.z);

        Holder.Reference<ConfiguredFeature<?, ?>> spawnPlatformFeature = level.registryAccess()
                .lookupOrThrow(Registries.CONFIGURED_FEATURE)
            .get(SkyAdditionsConfiguredFeatures.SPAWN_PLATFORM).get();

        if (!spawnPlatformFeature.value().place(level, chunkGenerator, random, worldSpawn)) {
            SkyAdditionsSettings.LOG.error("Couldn't generate spawn platform");
        }

        ci.cancel();
    }
}
