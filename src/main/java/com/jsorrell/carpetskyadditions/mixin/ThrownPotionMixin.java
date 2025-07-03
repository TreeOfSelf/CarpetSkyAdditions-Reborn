package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.DeepslateConversionHelper;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownLingeringPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractThrownPotion.class)
public abstract class ThrownPotionMixin extends ThrowableItemProjectile {

    public ThrownPotionMixin(EntityType<? extends AbstractThrownPotion> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "onHit",
        at =
        @At(
            value = "INVOKE",
            target ="Lnet/minecraft/world/item/alchemy/PotionContents;getColor()I")
    )
    private void onThickPotionCollision(HitResult result, CallbackInfo ci, @Local ItemStack itemStack, @Local PotionContents potionContents) {
        if (SkyAdditionsSettings.renewableDeepslateFromSplash) {
            if (potionContents.is(DeepslateConversionHelper.CONVERSION_POTION)) {
                Vec3 hitPos = result.getType() == HitResult.Type.BLOCK ? result.getLocation() : position();
                if ((AbstractThrownPotion)(Object)this instanceof ThrownLingeringPotion) {
                    AreaEffectCloud cloud = new AreaEffectCloud(level(), hitPos.x(), hitPos.y(), hitPos.z());
                    cloud.setRadius(3.0F);
                    cloud.setRadiusOnUse(-0.5F);
                    cloud.setWaitTime(10);
                    cloud.setDuration(cloud.getDuration() / 2);
                    cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
                    cloud.setPotionContents(potionContents);
                    level().addFreshEntity(cloud);
                } else {
                    DeepslateConversionHelper.convertDeepslateAtSplash(level(), hitPos);
                }
            }
        }
    }
}
