package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.DeepslateConversionHelper;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin extends ThrowableItemProjectile {
    @Shadow
    protected abstract boolean isLingering();

    public ThrownPotionMixin(EntityType<? extends ThrownPotion> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "onHit",
        at =
        @At(
            value = "INVOKE",
            target ="Lnet/minecraft/world/item/alchemy/PotionContents;getAllEffects()Ljava/lang/Iterable;")
    )
    private void onThickPotionCollision(HitResult result, CallbackInfo ci, @Local ItemStack itemStack, @Local PotionContents potionContents) {
        if (SkyAdditionsSettings.renewableDeepslateFromSplash) {
            if (potionContents.is(DeepslateConversionHelper.CONVERSION_POTION)) {
                Vec3 hitPos = result.getType() == HitResult.Type.BLOCK ? result.getLocation() : position();
                if (isLingering()) {
                    // Create the cloud b/c vanilla doesn't when there are no potion effects
                    AreaEffectCloud cloud = new AreaEffectCloud(level(), hitPos.x(), hitPos.y(), hitPos.z());
                    cloud.setRadius(3.0f);
                    cloud.setWaitTime(10);
                    cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());
                    cloud.setPotionContents(potionContents);
                    /*CompoundTag nbt = stack.getTag();
                    if (nbt != null && nbt.contains("CustomPotionColor", Tag.TAG_ANY_NUMERIC)) {
                        cloud.setFixedColor(nbt.getInt("CustomPotionColor"));
                    }*/
                    level().addFreshEntity(cloud);
                } else {
                    DeepslateConversionHelper.convertDeepslateAtSplash(level(), hitPos);
                }
            }
        }
    }
}
