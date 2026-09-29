package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class BreezeRodTrialSpawnerTrigger extends SimpleCriterionTrigger<BreezeRodTrialSpawnerTrigger.@org.jetbrains.annotations.NotNull Conditions> {

    @Override
    public Codec<BreezeRodTrialSpawnerTrigger.Conditions> codec() {
        return BreezeRodTrialSpawnerTrigger.Conditions.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, conditions -> true);
    }

    public record Conditions(Optional<Holder<LootItemCondition>> player)
        implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<BreezeRodTrialSpawnerTrigger.Conditions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.optionalField("player", LootItemCondition.CODEC, false)
                        .forGetter(BreezeRodTrialSpawnerTrigger.Conditions::player))
                .apply(instance, BreezeRodTrialSpawnerTrigger.Conditions::new));

    }
}
