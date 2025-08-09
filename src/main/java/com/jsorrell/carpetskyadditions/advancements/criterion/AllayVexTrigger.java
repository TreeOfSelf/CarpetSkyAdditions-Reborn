package com.jsorrell.carpetskyadditions.advancements.criterion;

import java.util.Optional;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.storage.loot.LootContext;

public class AllayVexTrigger extends SimpleCriterionTrigger<AllayVexTrigger.Conditions> {
    static final ResourceLocation ID = new SkyAdditionsResourceLocation("allay_vex").getResourceLocation();

    public void trigger(ServerPlayer player, Vex vex, Allay allay) {

        LootContext vexLootContext = EntityPredicate.createContext(player, vex);
        LootContext allayLootContext = EntityPredicate.createContext(player, allay);

        trigger(player, conditions -> conditions.matches(vexLootContext, allayLootContext));
    }

    @Override
    public Codec<AllayVexTrigger.Conditions> codec() {
        return AllayVexTrigger.Conditions.CODEC;
    }

    public static record Conditions(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> vex,
            Optional<ContextAwarePredicate> allay) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<AllayVexTrigger.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.optionalField("player", EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(AllayVexTrigger.Conditions::player),
                        Codec.optionalField("vex", EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(AllayVexTrigger.Conditions::vex),
                        Codec.optionalField("allay", EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(AllayVexTrigger.Conditions::allay))
                        .apply(instance, AllayVexTrigger.Conditions::new));

        public boolean matches(LootContext vexContext, LootContext allayContext) {
            boolean vexMatches = vex.map(v -> v.matches(vexContext)).orElse(true); // Defaults to true if no predicate
            boolean allayMatches = allay.map(a -> a.matches(allayContext)).orElse(true); // Defaults to true if no predicate

            return vexMatches && allayMatches;
        }
    }
}
