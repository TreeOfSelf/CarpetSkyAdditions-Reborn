package com.jsorrell.carpetskyadditions.advancements.criterion;

import java.util.Optional;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class DeadBushToBushTrigger extends SimpleCriterionTrigger<DeadBushToBushTrigger.Conditions> {

    static final ResourceLocation ID = new SkyAdditionsResourceLocation("bush").getResourceLocation();

    @Override
    public Codec<DeadBushToBushTrigger.Conditions> codec() {
        return DeadBushToBushTrigger.Conditions.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, conditions -> true);
    }

    public static record Conditions(Optional<ContextAwarePredicate> player)
        implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<DeadBushToBushTrigger.Conditions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.optionalField("player",EntityPredicate.ADVANCEMENT_CODEC, false)
                        .forGetter(DeadBushToBushTrigger.Conditions::player))
                .apply(instance, DeadBushToBushTrigger.Conditions::new));

    }
}
