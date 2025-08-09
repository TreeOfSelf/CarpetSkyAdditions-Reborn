package com.jsorrell.carpetskyadditions.advancements.criterion;

import java.util.Optional;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class GenerateGeodeTrigger extends SimpleCriterionTrigger<GenerateGeodeTrigger.Conditions> {

    static final ResourceLocation ID = new SkyAdditionsResourceLocation("generate_geode").getResourceLocation();

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
                        Codec.optionalField("player",EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(GenerateGeodeTrigger.Conditions::player))
                        .apply(instance, GenerateGeodeTrigger.Conditions::new));

    }
}
