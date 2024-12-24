package com.jsorrell.carpetskyadditions.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnderMan.EndermanTakeBlockGoal.class)
public abstract class EnderMan_EndermanTakeBlockGoalMixin {
    @Inject(
            method = "tick",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"
//                            shift = At.Shift.BEFORE once again, doesn't shift far enough to actually have an impact
                    ),
//            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private void inject(
            CallbackInfo ci,
            @Local RandomSource random,
            @Local Level level,
            @Local(ordinal = 0) int x,
            @Local(ordinal = 1) int y,
            @Local(ordinal = 2) int z,
            @Local BlockPos targetBlockPos,
            @Local BlockState targetBlockState) {
        Block targetBlock = targetBlockState.getBlock();
        if (targetBlock instanceof DoublePlantBlock || targetBlock instanceof DoorBlock) {
            // Only allow picking up the bottom half
            if (targetBlockState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                ci.cancel();
            }
        }
    }
}
