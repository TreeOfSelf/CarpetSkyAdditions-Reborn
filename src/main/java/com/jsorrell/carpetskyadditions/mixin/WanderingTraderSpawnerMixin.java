package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.TraderCamelHelper;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import java.util.Optional;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WanderingTraderSpawner.class)
public abstract class WanderingTraderSpawnerMixin {
    @Unique
    private int currentSpawnTimer;

    @Shadow
    private int spawnChance;

    @Shadow
    private int tickDelay;

    @Shadow
    @Final
    private ServerLevelData serverLevelData;

    @Shadow
    private int spawnDelay;

    @Shadow
    @Final
    private RandomSource random;

    @Shadow
    protected abstract boolean spawn(ServerLevel level);

    @Unique
    private boolean usesDefaultSettings() {
        return SkyAdditionsSettings.wanderingTraderSpawnRate == 24000
                && SkyAdditionsSettings.maxWanderingTraderSpawnChance == 0.075;
    }

    // For some reason vanilla has 2 probability guards that do nothing but makes
    // the chance not be able to go above 0.1
    // Merging these two checks will slightly change the chance of resetting the
    // spawn chance when no players are online
    @WrapOperation(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    private int skipSecondChanceCheck(RandomSource instance, int i, Operation<Integer> original) {
        return 100 < spawnChance ? 0 : instance.nextInt(i) /*original.call(i)*/;
    }

    @Unique
    private boolean hasEnoughSpace(BlockGetter level, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, 0, -1), pos.offset(1, 2, 1))) {
            if (!level.getBlockState(blockPos)
                    .getCollisionShape(level, blockPos)
                    .isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Inject(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/WanderingTraderSpawner;hasEnoughSpace(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void spawnTrader(
            ServerLevel serverLevel,
            CallbackInfoReturnable<Boolean> cir,
            @Local Player player,
            @Local(ordinal = 0) BlockPos playerPos,
            @Local int i,
            @Local PoiManager poiManager,
            @Local Optional<BlockPos> optional,
            @Local(ordinal = 1) BlockPos playerOrMeetingPos,
            @Local(ordinal = 2) BlockPos spawnPos) {
        if (!TraderCamelHelper.tradersRideCamelsAt(serverLevel, spawnPos)) {
            return;
        }

        if (hasEnoughSpace(serverLevel, spawnPos)) {
            if (serverLevel.getBiome(spawnPos).is(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)) {
                cir.setReturnValue(false);
                return;
            }

            Camel traderCamel = EntityType.CAMEL.spawn(serverLevel, spawnPos, EntitySpawnReason.EVENT);
            if (traderCamel != null) {
                WanderingTrader wanderingTrader = EntityType.WANDERING_TRADER.create(serverLevel, EntitySpawnReason.EVENT);
                if (wanderingTrader != null) {
                    serverLevelData.setWanderingTraderId(wanderingTrader.getUUID());
                    wanderingTrader.setDespawnDelay(48000);

                    traderCamel.equipItemIfPossible(serverLevel,Items.SADDLE.getDefaultInstance());
                    wanderingTrader.setPos(traderCamel.getX(), traderCamel.getY(), traderCamel.getZ());
                    wanderingTrader.setYRot(traderCamel.getYRot());
                    wanderingTrader.setXRot(0.0F);
                    wanderingTrader.startRiding(traderCamel, true);
                    wanderingTrader.setWanderTarget(playerOrMeetingPos);
                    serverLevel.addFreshEntity(wanderingTrader);
                    cir.setReturnValue(true);
                    return;
                }
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(
            ServerLevel level, boolean spawnMonsters, boolean spawnAnimals, CallbackInfo ci) {

        if (usesDefaultSettings()) {
            return;
        }

        ci.cancel();

        if (!level.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING)) {
            return;
        }

        if (SkyAdditionsSettings.wanderingTraderSpawnRate < spawnDelay) {
            spawnDelay = SkyAdditionsSettings.wanderingTraderSpawnRate;
            currentSpawnTimer = Math.min(1200, spawnDelay);
            tickDelay = currentSpawnTimer;
        }

        if (--tickDelay > 0) {
            return;
        }

        spawnDelay -= currentSpawnTimer;
        boolean trySpawn = spawnDelay <= 0;

        spawnDelay = trySpawn ? SkyAdditionsSettings.wanderingTraderSpawnRate : spawnDelay;
        currentSpawnTimer = Math.min(1200, spawnDelay);
        tickDelay = currentSpawnTimer;

        serverLevelData.setWanderingTraderSpawnDelay(spawnDelay);

        if (trySpawn && level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
            if (random.nextInt(100 < spawnChance ? 1000 : 100) < spawnChance && spawn(level)) {
                spawnChance = 25;
            } else {
                spawnChance = Mth.clamp(spawnChance + 25, 25,
                        (int) Math.round(SkyAdditionsSettings.maxWanderingTraderSpawnChance * 1000d));
            }
            serverLevelData.setWanderingTraderSpawnChance(spawnChance);
        }
    }
}
