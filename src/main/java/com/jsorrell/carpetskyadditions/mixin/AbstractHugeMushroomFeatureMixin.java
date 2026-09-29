package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.HugeBrownMushroomFeature;
import net.minecraft.world.level.levelgen.feature.HugeRedMushroomFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// AbstractHugeMushroomFeature is an interface since 26.3, so override its default place() on the implementations
@Mixin({HugeRedMushroomFeature.class, HugeBrownMushroomFeature.class})
public abstract class AbstractHugeMushroomFeatureMixin implements AbstractHugeMushroomFeature {
    // Like podzol under huge spruces: only replace ground blocks, never place into air
    @Unique
    private static final Holder<BlockStateProvider> MYCELIUM_BENEATH_MUSHROOM = Holder.direct(
            RuleBasedStateProvider.ifTrueThenProvide(
                    BlockPredicate.matchesTag(BlockTags.BENEATH_TREE_PODZOL_REPLACEABLE), Blocks.MYCELIUM));

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        int treeHeight = this.getTreeHeight(random);
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        if (!this.isValidPosition(level, origin, treeHeight, blockPos)) {
            return false;
        }

        this.makeCap(level, random, origin, treeHeight, blockPos);
        this.placeTrunk(level, random, origin, treeHeight, blockPos);
        if (SkyAdditionsSettings.hugeMushroomsSpreadMycelium) {
            generateMycelium(level, random, origin);
        }
        return true;
    }

    @Unique
    private static void generateMycelium(WorldGenLevel level, RandomSource random, BlockPos pos) {
        new AlterGroundDecorator(MYCELIUM_BENEATH_MUSHROOM).place(
            new TreeDecorator.Context(
                level,
                (blockPos, blockState) -> level.setBlock(blockPos, blockState, Block.UPDATE_ALL),
                random,
                Set.of(pos),
                Set.of(),
                Set.of()
            )
        );
    }
}
