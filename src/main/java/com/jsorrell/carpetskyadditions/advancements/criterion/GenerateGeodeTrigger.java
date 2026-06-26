package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class GenerateGeodeTrigger extends SimpleCriterionTrigger<GenerateGeodeTrigger.@org.jetbrains.annotations.NotNull Conditions> {

    @Override
    public Codec<GenerateGeodeTrigger.Conditions> codec() {
        return GenerateGeodeTrigger.Conditions.CODEC;
    }

      public void trigger(ServerPlayer player) {
        trigger(player, conditions -> true);
    }

    public static record Conditions(Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<GenerateGeodeTrigger.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.optionalField("player", EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(GenerateGeodeTrigger.Conditions::player))
                        .apply(instance, GenerateGeodeTrigger.Conditions::new));

    }
}
