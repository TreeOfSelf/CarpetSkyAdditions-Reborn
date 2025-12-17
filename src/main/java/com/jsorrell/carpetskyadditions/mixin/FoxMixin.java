package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public abstract class FoxMixin extends Mob {

    protected FoxMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "populateDefaultEquipmentSlots",
        cancellable = true,
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextFloat()F", ordinal = 1))
    private void addFoxHeldItem(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (0 < SkyAdditionsSettings.foxesSpawnWithSweetBerriesChance) {
            float f = random.nextFloat();
            ItemStack equippedItem;
            if (f < SkyAdditionsSettings.foxesSpawnWithSweetBerriesChance) {
                equippedItem = new ItemStack(Items.SWEET_BERRIES);
                this.setItemSlot(EquipmentSlot.MAINHAND, equippedItem);
                ci.cancel();
            }
        }
    }
}
