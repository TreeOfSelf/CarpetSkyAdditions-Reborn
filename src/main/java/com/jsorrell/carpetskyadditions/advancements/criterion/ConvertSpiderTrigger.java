package com.jsorrell.carpetskyadditions.advancements.criterion;

import java.util.Optional;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.storage.loot.LootContext;

public class ConvertSpiderTrigger extends SimpleCriterionTrigger<ConvertSpiderTrigger.Conditions> {
    static final ResourceLocation ID = new SkyAdditionsResourceLocation("convert_spider").getResourceLocation();


    public void trigger(ServerPlayer player, Spider spider, CaveSpider caveSpider) {
        LootContext spiderLootContext = EntityPredicate.createContext(player, spider);
        LootContext caveSpiderLootContext = EntityPredicate.createContext(player, caveSpider);
        trigger(player, conditions -> conditions.matches(spiderLootContext, caveSpiderLootContext));
    }

    @Override
    public Codec<ConvertSpiderTrigger.Conditions> codec() {
        return ConvertSpiderTrigger.Conditions.CODEC;
    }
    public static record Conditions(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> spider,
            Optional<ContextAwarePredicate> caveSpider) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<ConvertSpiderTrigger.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.optionalField("player",EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(ConvertSpiderTrigger.Conditions::player),
                        Codec.optionalField("spider",EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(ConvertSpiderTrigger.Conditions::spider),
                        Codec.optionalField("caveSpider",EntityPredicate.ADVANCEMENT_CODEC, false)
                                .forGetter(ConvertSpiderTrigger.Conditions::caveSpider))
                        .apply(instance, ConvertSpiderTrigger.Conditions::new));

        public boolean matches(LootContext spiderContext, LootContext caveSpiderContext) {
                // Check if spider and caveSpider predicates are present and match their contexts
                boolean spiderMatches = spider.map(predicate -> predicate.matches(spiderContext)).orElse(true);
                boolean caveSpiderMatches = caveSpider.map(predicate -> predicate.matches(caveSpiderContext)).orElse(true);

                return spiderMatches && caveSpiderMatches;
        }
    }
}
