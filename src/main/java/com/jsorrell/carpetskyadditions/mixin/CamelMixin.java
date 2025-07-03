package com.jsorrell.carpetskyadditions.mixin;

import com.google.common.collect.ImmutableMap;
import com.jsorrell.carpetskyadditions.fakes.CamelInterface;
import com.jsorrell.carpetskyadditions.helpers.TraderCamelHelper;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.camel.CamelAi;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camel.class)
public abstract class CamelMixin extends AbstractHorse implements CamelInterface {

    protected CamelMixin(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    @SuppressWarnings("ConstantConditions")
    private Camel asCamel() {
        if ((AbstractHorse) this instanceof Camel camel) {
            return camel;
        } else {
            throw new AssertionError("Not camel");
        }
    }

    @Unique
    public boolean isTraderCamel() {
        return TraderCamelHelper.isTraderCamel(asCamel());
    }

    @Inject(method = "canAddPassenger", at = @At("HEAD"), cancellable = true)
    public void canAddPassengerToTraderCamel(Entity passenger, CallbackInfoReturnable<Boolean> cir) {
        if (isTraderCamel()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isFood", at = @At("HEAD"), cancellable = true)
    public void isFoodForTraderCamel(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (isTraderCamel()) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private Dynamic<?> getBlankBrainDynamic() {
        NbtOps nbtOps = NbtOps.INSTANCE;
        return new Dynamic<>(
                nbtOps, nbtOps.createMap(ImmutableMap.of(nbtOps.createString("memories"), nbtOps.emptyMap())));
    }

    @Unique
    public void carpetSkyAdditions$makeTraderCamel() {
        brain = TraderCamelHelper.TraderCamelAI.makeBrain(
                CamelAi.brainProvider().makeBrain(getBlankBrainDynamic()));
    }

    @Unique
    public void carpetSkyAdditions$makeStandaloneCamel() {
        brain = makeBrain(getBlankBrainDynamic());
    }

}
