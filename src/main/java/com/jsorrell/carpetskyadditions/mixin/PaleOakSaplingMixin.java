package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.advancements.criterion.SkyAdditionsCriteriaTriggers;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SaplingBlock.class)
public class PaleOakSaplingMixin {
    @Shadow
    @Final
    protected TreeGrower treeGrower;

    @Inject(
        method = "advanceTree",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/grower/TreeGrower;growTree(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;)Z",
            shift = At.Shift.AFTER
        )
    )
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random, CallbackInfo ci) {
        // Ensure the block is the Pale Oak Sapling
        if ( SkyAdditionsSettings.paleBlossomCreakingHeart &&
            state.getBlock() == Blocks.PALE_OAK_SAPLING) {
            boolean eyeBlossomNearby = false;

            int range = 3;
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    for (int dz = -range; dz <= range; dz++) {
                        mutablePos.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                        if (level.getBlockState(mutablePos).is(Blocks.OPEN_EYEBLOSSOM)) {
                            eyeBlossomNearby = true;
                            break;
                        }
                    }
                    if (eyeBlossomNearby) break;
                }
                if (eyeBlossomNearby) break;
            }

            if (eyeBlossomNearby) {
                int xzRange = 1;
                int yRange = 4;
                BlockPos.MutableBlockPos logPos = new BlockPos.MutableBlockPos();

                boolean creakingHeartSet = false;

                for (int dx = -xzRange; dx <= xzRange; dx++) {
                    for (int dy = 0; dy <= yRange; dy++) {
                        for (int dz = -xzRange; dz <= xzRange; dz++) {
                            logPos.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                            if (level.getBlockState(logPos).is(Blocks.PALE_OAK_LOG) && !creakingHeartSet && random.nextInt(10) == 1) {
                                level.setBlock(logPos, Blocks.CREAKING_HEART.defaultBlockState(), SaplingBlock.UPDATE_ALL);
                                creakingHeartSet = true;
                                AABB criteriaTriggerBox = new AABB(logPos).inflate(50, 20, 50);
                                level.getEntitiesOfClass(ServerPlayer.class, criteriaTriggerBox)
                                    .forEach(SkyAdditionsCriteriaTriggers.CREAKING_HEART::trigger);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
