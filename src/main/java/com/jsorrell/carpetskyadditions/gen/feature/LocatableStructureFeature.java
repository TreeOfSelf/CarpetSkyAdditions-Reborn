package com.jsorrell.carpetskyadditions.gen.feature;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public record LocatableStructureFeature(Identifier structure, BlockPos pos) implements Feature {
    public static final MapCodec<LocatableStructureFeature> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                            Identifier.CODEC.fieldOf("structure").forGetter(LocatableStructureFeature::structure),
                            BlockPos.CODEC.fieldOf("pos").forGetter(LocatableStructureFeature::pos))
                    .apply(instance, LocatableStructureFeature::new));

    @Override
    public MapCodec<LocatableStructureFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        MinecraftServer server = level.getServer();
        if (server == null) {
            return false;
        }
        StructureTemplate template =
                server.getStructureTemplateManager().get(structure).orElse(null);
        if (template == null) {
            SkyAdditionsSettings.LOG.warn("Missing structure " + structure);
            return false;
        }

        return template.placeInWorld(
                level,
                origin.offset(pos),
                null,
                new StructurePlaceSettings(),
                random,
                Block.UPDATE_CLIENTS);
    }
}
