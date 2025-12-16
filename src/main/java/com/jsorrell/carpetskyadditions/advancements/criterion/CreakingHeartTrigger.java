package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CreakingHeartTrigger extends SimpleCriterionTrigger<CreakingHeartTrigger.@org.jetbrains.annotations.NotNull Conditions> {

    @Override
    public @NotNull Codec<CreakingHeartTrigger.Conditions> codec() {
        return CreakingHeartTrigger.Conditions.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, conditions -> true);
    }

    public record Conditions(Optional<ContextAwarePredicate> player)
        implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<CreakingHeartTrigger.Conditions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.optionalField("player", EntityPredicate.ADVANCEMENT_CODEC, false)
                        .forGetter(CreakingHeartTrigger.Conditions::player))
                .apply(instance, CreakingHeartTrigger.Conditions::new));

    }
}
