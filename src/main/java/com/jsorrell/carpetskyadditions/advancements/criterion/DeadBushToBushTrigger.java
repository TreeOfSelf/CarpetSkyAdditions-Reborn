package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class DeadBushToBushTrigger extends SimpleCriterionTrigger<DeadBushToBushTrigger.@org.jetbrains.annotations.NotNull Conditions> {

    @Override
    public Codec<DeadBushToBushTrigger.Conditions> codec() {
        return DeadBushToBushTrigger.Conditions.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, conditions -> true);
    }

    public record Conditions(Optional<ContextAwarePredicate> player)
        implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<DeadBushToBushTrigger.Conditions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.optionalField("player", EntityPredicate.ADVANCEMENT_CODEC, false)
                        .forGetter(DeadBushToBushTrigger.Conditions::player))
                .apply(instance, DeadBushToBushTrigger.Conditions::new));

    }
}
