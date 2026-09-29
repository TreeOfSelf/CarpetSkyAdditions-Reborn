package com.jsorrell.carpetskyadditions.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public record SpawnPlatformFeature(LocatableStructureFeature platform, boolean spawnRelative) implements Feature {
    public static final MapCodec<SpawnPlatformFeature> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                            LocatableStructureFeature.CODEC.codec().fieldOf("platform").forGetter(SpawnPlatformFeature::platform),
                            Codec.BOOL.fieldOf("spawn_relative").forGetter(SpawnPlatformFeature::spawnRelative))
                    .apply(instance, SpawnPlatformFeature::new));

    @Override
    public MapCodec<SpawnPlatformFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        // Always absolute with Y
        BlockPos platformOrigin = spawnRelative ? origin.atY(0) : BlockPos.ZERO;
        return platform.place(level, chunkGenerator, random, platformOrigin);
    }
}
