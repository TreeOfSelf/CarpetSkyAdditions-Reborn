package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(Blaze.class)
public abstract class BlazeMixin extends BlazeToBreezeMixin {
    @Override
    protected Optional<EntityType<?>> carpetskyadditions$getEntityTypeForDimensionChange() {
        if (SkyAdditionsSettings.blazeToBreeze
            && this.level().dimension() == Level.NETHER
            && !this.hasCustomName())
            return Optional.of(EntityType.BREEZE);
        return super.carpetskyadditions$getEntityTypeForDimensionChange();
    }
}
