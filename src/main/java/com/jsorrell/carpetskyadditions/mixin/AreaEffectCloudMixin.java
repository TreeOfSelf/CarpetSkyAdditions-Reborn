package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.DeepslateConversionHelper;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(AreaEffectCloud.class)
public class AreaEffectCloudMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void convertDeepslateOnTick(CallbackInfo ci) {
        if (SkyAdditionsSettings.renewableDeepslateFromSplash) {
            try {
                AreaEffectCloud cloud = (AreaEffectCloud)(Object)this;
                Field potionContentsField = AreaEffectCloud.class.getDeclaredField("potionContents");
                potionContentsField.setAccessible(true);
                PotionContents potionContents = (PotionContents) potionContentsField.get(cloud);

                if (potionContents.equals(DeepslateConversionHelper.CONVERSION_POTION)) {
                    DeepslateConversionHelper.convertDeepslateInCloud(cloud.level(), cloud.getBoundingBox());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
