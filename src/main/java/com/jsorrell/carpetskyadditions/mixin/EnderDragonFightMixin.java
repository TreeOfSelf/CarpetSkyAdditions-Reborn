package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.gen.SkyBlockChunkGenerator;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {
    @Shadow
    private boolean hasPreviouslyKilledDragon;

    @Shadow
    @Final
    private ServerLevel level;

    @Shadow
    private @Nullable BlockPos exitPortalLocation;

    @Inject(method = "spawnExitPortal", at = @At(value = "HEAD"))
    private void setExitPortalLocation(boolean openPortal, CallbackInfo ci) {
        if (level.getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator chunkGenerator) {
            if (exitPortalLocation == null) {
                int y = chunkGenerator.getBaseHeightInEquivalentNoiseWorld(
                                0, 0, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, level)
                        - 1;
                exitPortalLocation = BlockPos.ZERO.atY(y);
            }
        }
    }

    @Inject(
            method = "setDragonKilled",
            at =
                    @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/world/level/dimension/end/EnderDragonFight;hasPreviouslyKilledDragon:Z",
                            opcode = Opcodes.PUTFIELD))
    private void spawnShulkerOnDragonReKill(EnderDragon dragon, CallbackInfo ci) {
        if (SkyAdditionsSettings.shulkerSpawnsOnDragonKill && exitPortalLocation != null) {
            BlockPos shulkerPosition = exitPortalLocation.offset(0, 4, 0);
            if (hasPreviouslyKilledDragon && level.getBlockState(shulkerPosition).isAir()) {
                Shulker shulker = EntityTypes.SHULKER.create(level, null, shulkerPosition, EntitySpawnReason.EVENT, true, false);
                if (level.noCollision(shulker)) {
                    level.addFreshEntity(shulker);
                }
            }
        }
    }
}
