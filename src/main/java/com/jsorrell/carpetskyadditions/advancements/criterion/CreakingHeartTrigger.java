package com.jsorrell.carpetskyadditions.advancements.criterion;

import java.util.Optional;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CreakingHeartTrigger extends SimpleCriterionTrigger<CreakingHeartTrigger.Conditions> {

    static final ResourceLocation ID = new SkyAdditionsResourceLocation("creaking_heart").getResourceLocation();

    @Override
    public Codec<CreakingHeartTrigger.Conditions> codec() {
        return CreakingHeartTrigger.Conditions.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, conditions -> true);
    }

    public static record Conditions(Optional<ContextAwarePredicate> player)
        implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<CreakingHeartTrigger.Conditions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.optionalField("player",EntityPredicate.ADVANCEMENT_CODEC, false)
                        .forGetter(CreakingHeartTrigger.Conditions::player))
                .apply(instance, CreakingHeartTrigger.Conditions::new));

    }
}
