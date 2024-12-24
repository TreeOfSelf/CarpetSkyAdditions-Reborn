package com.jsorrell.carpetskyadditions.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
@Debug(export = true)
@Mixin(EnderMan.EndermanLeaveBlockGoal.class)
public abstract class EnderMan_EndermanLeaveBlockGoalMixin {
    @Shadow
    @Final
    private EnderMan enderman;

    @Inject(
            method = "tick",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/level/block/Block;updateFromNeighbourShapes(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
//                            shift = At.Shift.BEFORE doesn't actually do anything?
                    ),
            cancellable = true)
    private void inject(
            CallbackInfo ci,
            @Local RandomSource random,
            @Local Level world,
            @Local(ordinal = 0) int x,
            @Local(ordinal = 1) int y,
            @Local(ordinal = 2) int z,
            @Local(ordinal = 0) BlockPos placePosBottom,
            @Local(ordinal = 0) BlockState placeStateBottom,
            @Local(ordinal = 1) BlockPos belowPlacePos,
            @Local(ordinal = 1) BlockState belowPosState,
            @Local(ordinal = 2) BlockState heldBlockState) {
        Block heldBlock = heldBlockState.getBlock();
        if (heldBlock instanceof DoublePlantBlock || heldBlock instanceof DoorBlock) {
            BlockPos placePosTop = placePosBottom.above();
            BlockState placeStateTop = world.getBlockState(placePosTop);
            if (placePosTop.getY() < world.getMaxY()
                    && placeStateBottom.isAir()
                    && placeStateTop.isAir()
                    && !belowPosState.isAir()
                    && !belowPosState.is(Blocks.BEDROCK)
                    && belowPosState.isCollisionShapeFullBlock(world, belowPlacePos)
                    && heldBlockState.canSurvive(world, placePosBottom)
                    && world.getEntities(enderman, new AABB(x, y, z, x + 1.0, y + 2.0, z + 1.0))
                            .isEmpty()) {

                if (heldBlock instanceof DoorBlock) {
                    boolean powered = world.hasNeighborSignal(placePosBottom) || world.hasNeighborSignal(placePosTop);
                    // FIXME what about facing and hinge?
                    heldBlockState = heldBlockState
                            .setValue(BlockStateProperties.POWERED, powered)
                            .setValue(BlockStateProperties.OPEN, powered);
                }
                world.setBlockAndUpdate(placePosBottom, heldBlockState);
                heldBlock.setPlacedBy(world, placePosBottom, heldBlockState, enderman, ItemStack.EMPTY);
                world.gameEvent(GameEvent.BLOCK_PLACE, placePosBottom, GameEvent.Context.of(enderman, heldBlockState));
                enderman.setCarriedBlock(null);
            }
            ci.cancel();
        }
    }
}
