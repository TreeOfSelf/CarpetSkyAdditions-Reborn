package com.jsorrell.carpetskyadditions.gen.feature;

import com.mojang.serialization.MapCodec;
import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.EndIslandFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

public record EndGatewayIslandFeature() implements Feature {
    public static final MapCodec<EndGatewayIslandFeature> CODEC = MapCodec.unit(EndGatewayIslandFeature::new);
    private static final EndIslandFeature END_ISLAND = new EndIslandFeature();
    private static final ChorusPlantFeature CHORUS_PLANT = new ChorusPlantFeature();

    @Override
    public MapCodec<EndGatewayIslandFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        if (!END_ISLAND.place(level, chunkGenerator, random, origin)) {
            return false;
        }

        int x = origin.getX();
        int y = origin.getY();
        int z = origin.getZ();

        // Try to generate in a 11x11 area around the center of the island.
        // 20 tries should be more than enough, even for small islands.
        final int r = 5;
        for (BlockPos pos : BlockPos.randomBetweenClosed(random, 20, x - r, y, z - r, x + r, y, z + r)) {
            // Force not generating on edge
            if (Direction.Plane.HORIZONTAL.stream().noneMatch(dir -> level.isEmptyBlock(pos.relative(dir)))
                    && CHORUS_PLANT.place(level, chunkGenerator, random, pos.above())) {
                return true;
            }
        }
        return false;
    }

    // Finds a place to spawn a gateway that won't overwrite chorus
    // Allows a gateway that pops off chorus flowers
    public static BlockPos findGatewayLocation(LevelReader level, BlockPos origin) {
        return StreamSupport.stream(BlockPos.withinBoxByManhattanDistance(origin, 7, 0, 7).spliterator(), false)
                .filter(pos -> level.getBlockState(pos).is(Blocks.END_STONE)
                        && Direction.stream()
                                .allMatch(direction ->
                                        level.isEmptyBlock(pos.above(11).relative(direction)))
                        && Direction.stream()
                                .allMatch(direction ->
                                        level.isEmptyBlock(pos.above(9).relative(direction))))
                .findFirst()
                .orElse(origin);
    }
}
