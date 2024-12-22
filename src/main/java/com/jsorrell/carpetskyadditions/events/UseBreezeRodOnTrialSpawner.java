package com.jsorrell.carpetskyadditions.events;

import com.jsorrell.carpetskyadditions.advancements.criterion.SkyAdditionsCriteriaTriggers;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class UseBreezeRodOnTrialSpawner {
    public static void register(){
        UseBlockCallback.EVENT.register(UseBreezeRodOnTrialSpawner::onUseBlock);
    }

    private static InteractionResult onUseBlock(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (stack.getItem() == Items.BREEZE_ROD) {
            BlockEntity tileEntity = player.level().getBlockEntity(blockHitResult.getBlockPos());
            if (tileEntity instanceof TrialSpawnerBlockEntity) {
                TrialSpawnerBlockEntity spawner = (TrialSpawnerBlockEntity) tileEntity;
                if (spawner.getState() == TrialSpawnerState.INACTIVE) {
                    spawner.setEntityId(EntityType.BREEZE, player.level().random);
                    spawner.markUpdated();
                    spawner.setChanged();
                    stack.shrink(1);
                    spawner.setState(player.level(),TrialSpawnerState.ACTIVE);

                    player.level().playSound(null, blockHitResult.getBlockPos(),
                        SoundEvents.BREEZE_WHIRL,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                    );

                    AABB criteriaTriggerBox = new AABB(blockHitResult.getBlockPos()).inflate(50, 20, 50);
                    level.getEntitiesOfClass(ServerPlayer.class, criteriaTriggerBox)
                        .forEach(SkyAdditionsCriteriaTriggers.ACTIVATE_TRIAL_SPAWNER::trigger);

                    return InteractionResult.CONSUME;

                }
            }
        }

        return InteractionResult.PASS;
    }


}
