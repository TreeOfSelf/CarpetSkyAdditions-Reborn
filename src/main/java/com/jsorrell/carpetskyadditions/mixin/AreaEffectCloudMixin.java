package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.DeepslateConversionHelper;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaEffectCloud.class)
public class AreaEffectCloudMixin {
    @Shadow private PotionContents potionContents;

    @Inject(method = "tick", at = @At("HEAD"))
    private void convertDeepslateOnTick(CallbackInfo ci) {
        if (SkyAdditionsSettings.renewableDeepslateFromSplash) {
            AreaEffectCloud cloud = (AreaEffectCloud)(Object)this;

            if (this.potionContents.is(DeepslateConversionHelper.CONVERSION_POTION)) {
                DeepslateConversionHelper.convertDeepslateInCloud(cloud.level(), cloud.getBoundingBox());
            }
        }
    }
}
