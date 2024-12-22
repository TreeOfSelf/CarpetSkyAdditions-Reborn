package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class BlazeToBreezeMixin {

    @Redirect(
        method = "teleportCrossDimension",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntitySpawnReason;)Lnet/minecraft/world/entity/Entity;")
    )
    private Entity redirectEntityCreation(EntityType<?> entityType, Level level, EntitySpawnReason reason) {
        if (SkyAdditionsSettings.blazeToBreeze) {
            if ((Object) this instanceof Blaze) {
                Blaze blaze = (Blaze) (Object) this;
                if (blaze.level().dimension() == Level.NETHER && !blaze.hasCustomName()) {
                    return EntityType.BREEZE.create(level, reason);
                }
            }
        }

        return entityType.create(level, reason);
    }
}
