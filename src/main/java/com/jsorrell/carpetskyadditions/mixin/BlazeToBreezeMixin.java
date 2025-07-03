package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class BlazeToBreezeMixin {

    @Shadow
    public abstract Level level();

    @Shadow
    public abstract boolean hasCustomName();

    @Unique
    protected Optional<EntityType<?>> carpetskyadditions$getEntityTypeForDimensionChange() {
        return Optional.empty();
    }

    @WrapOperation(
        method = "teleportCrossDimension",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntitySpawnReason;)Lnet/minecraft/world/entity/Entity;"
        )
    )
    protected <T extends Entity> T redirectEntityCreation(EntityType<T> instance, Level level, EntitySpawnReason entitySpawnReason, Operation<T> original) {
        return original.call(carpetskyadditions$getEntityTypeForDimensionChange().orElse(instance), level, entitySpawnReason);
    }

    @Mixin(Blaze.class)
    private abstract static class BlazeMixin extends BlazeToBreezeMixin {

        @Override
        protected Optional<EntityType<?>> carpetskyadditions$getEntityTypeForDimensionChange() {
            if (SkyAdditionsSettings.blazeToBreeze
                && this.level().dimension() == Level.NETHER
                && !this.hasCustomName())
                return Optional.of(EntityType.BREEZE);
            return super.carpetskyadditions$getEntityTypeForDimensionChange();
        }
    }
}
