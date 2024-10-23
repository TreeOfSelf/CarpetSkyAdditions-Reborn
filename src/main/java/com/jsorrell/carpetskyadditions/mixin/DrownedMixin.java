package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Drowned.class)
public class DrownedMixin extends Zombie {
    public DrownedMixin(Level level) {
        super(level);
    }

    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    private void holdSnifferEgg(
        ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason entitySpawnReason, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (SkyAdditionsSettings.sniffersFromDrowneds
                && getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()
                && random.nextFloat() < 0.01F) {
            setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SNIFFER_EGG));
            setDropChance(EquipmentSlot.OFFHAND, 0);
        }
    }
}
