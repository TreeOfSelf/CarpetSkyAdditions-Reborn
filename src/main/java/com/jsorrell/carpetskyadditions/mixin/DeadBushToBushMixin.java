package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(PotionItem.class)
public class DeadBushToBushMixin {
    @ModifyReturnValue(
        method = "useOn",
        at = @At("TAIL")
    )
    private InteractionResult convertDeadBushToBush(InteractionResult original, UseOnContext context) {
        if (SkyAdditionsSettings.doDeadBushToBush) {
            ItemStack itemStack = context.getItemInHand();

            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

            if (potionContents.is(Potions.WATER)) {
                Level level = context.getLevel();
                BlockPos blockPos = context.getClickedPos();
                Player playerEntity = context.getPlayer();
                BlockState blockState = level.getBlockState(blockPos);

                if (context.getClickedFace() != Direction.DOWN && blockState.is(Blocks.DEAD_BUSH)) {
                    level.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.PLAYERS, 1.0f, 1.0f);
                    Objects.requireNonNull(playerEntity)
                        .setItemInHand(
                            context.getHand(),
                            ItemUtils.createFilledResult(
                                itemStack, playerEntity, new ItemStack(Items.GLASS_BOTTLE)));
                    playerEntity.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                    level.setBlock(blockPos, Blocks.BUSH.defaultBlockState(),0);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return original;
    }
}
