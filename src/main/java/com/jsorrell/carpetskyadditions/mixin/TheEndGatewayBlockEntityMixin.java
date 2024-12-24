package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.gen.feature.EndGatewayIslandFeature;
import com.jsorrell.carpetskyadditions.gen.feature.SkyAdditionsConfiguredFeatures;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TheEndGatewayBlockEntity.class)
public class TheEndGatewayBlockEntityMixin {
    @WrapOperation(
            method = "method_46695",
            at =
                    @At(
                            value = "FIELD",
                            opcode = Opcodes.GETSTATIC,
                            target =
                                    "Lnet/minecraft/data/worldgen/features/EndFeatures;END_ISLAND:Lnet/minecraft/resources/ResourceKey;"))
    private static ResourceKey<ConfiguredFeature<?, ?>> replaceGeneratedEndIslandFeature(Operation<ResourceKey<ConfiguredFeature<?, ?>>> original) {
        if (SkyAdditionsSettings.gatewaysSpawnChorus) {
            return SkyAdditionsConfiguredFeatures.GATEWAY_ISLAND;
        } else {
            return EndFeatures.END_ISLAND/*original.call()*/;
        }
    }

    // By default, the exit portal will find the highest block and generate 10 above that.
    // This will always put it directly above the highest chorus fruit block (which can be very high)
    // Additionally, the player will spawn on top of the highest chorus fruit that isn't directly under the portal.
    // This function ensures the portal spawns only 10 blocks above the End Stone.
    // This overrides the default, but that's OK.
    // The portal location can still be manipulated the same b/c this function only runs if the area is all void.
    @Inject(
            method = "findOrCreateValidTeleportPos",
            cancellable = true,
            at =
                    @At(
                            value = "INVOKE",
                            target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V",
                            shift = At.Shift.AFTER
                    ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void forceExitPortalPos(
            ServerLevel level,
            BlockPos pos,
            CallbackInfoReturnable<BlockPos> cir,
            @Local Vec3 teleportLocation,
            @Local LevelChunk portalChunk,
            @Local(ordinal = 1) BlockPos blockPos,
            @Local(ordinal = 2) BlockPos islandCenterPos) {
        if (SkyAdditionsSettings.gatewaysSpawnChorus) {
            BlockPos portalPos = EndGatewayIslandFeature.findGatewayLocation(level, islandCenterPos);
            cir.setReturnValue(portalPos);
        }
    }
}
