package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.advancements.criterion.SkyAdditionsCriteriaTriggers;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

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
        if (SkyAdditionsSettings.paleBlossomCreakingHeart && state.getBlock() == Blocks.PALE_OAK_SAPLING) {

            // Check if the sapling is in the Pale Garden biome
            ResourceKey biomeKey = level.getBiome(pos).unwrapKey().orElse(null);
            if (biomeKey == null || !biomeKey.equals(Biomes.PALE_GARDEN)) {
                return; // Exit if not in the Pale Garden biome
            }

            boolean eyeBlossomNearby = false;

            // Check for Open Eyeblossom nearby
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

            // Single random chance per tree
            if (eyeBlossomNearby && random.nextInt(10) == 1) { // 10% chance
                // Replace eligible Pale Oak Logs with Creaking Heart
                List<BlockPos> eligibleLogs = new ArrayList<>();
                int xzRange = 1;
                int yRange = 4;
                for (int dx = -xzRange; dx <= xzRange; dx++) {
                    for (int dy = 0; dy <= yRange; dy++) {
                        for (int dz = -xzRange; dz <= xzRange; dz++) {
                            BlockPos logPos = pos.offset(dx, dy, dz);
                            if (level.getBlockState(logPos).is(Blocks.PALE_OAK_LOG)) {
                                eligibleLogs.add(logPos);
                            }
                        }
                    }
                }

                if (!eligibleLogs.isEmpty()) {
                    BlockPos selectedLog = eligibleLogs.get(random.nextInt(eligibleLogs.size()));
                    level.setBlock(selectedLog, Blocks.CREAKING_HEART.defaultBlockState(), SaplingBlock.UPDATE_ALL);

                    // Trigger advancements
                    AABB criteriaTriggerBox = new AABB(selectedLog).inflate(50, 20, 50);
                    level.getEntitiesOfClass(ServerPlayer.class, criteriaTriggerBox)
                        .forEach(SkyAdditionsCriteriaTriggers.CREAKING_HEART::trigger);

                    // Update the active state of the placed Creaking Heart
                    updateCreakingHeartState(level, selectedLog);
                }
            }
        }
    }

    private void updateCreakingHeartState(ServerLevel level, BlockPos pos) {
        BlockState currentState = level.getBlockState(pos);
        if (currentState.is(Blocks.CREAKING_HEART)) {
            BooleanProperty activeProperty = (BooleanProperty) Blocks.CREAKING_HEART.getStateDefinition().getProperty("active");
            if (activeProperty != null) {
                boolean isActive = level.getBlockState(pos.above()).is(Blocks.PALE_OAK_LOG) &&
                                    level.getBlockState(pos.below()).is(Blocks.PALE_OAK_LOG);

                level.setBlock(pos, currentState.setValue(activeProperty, isActive), 3);
            }
        }
    }
}