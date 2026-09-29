package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

public class ConvertSpiderTrigger extends SimpleCriterionTrigger<ConvertSpiderTrigger.Conditions> {


    public void trigger(ServerPlayer player, Spider spider, CaveSpider caveSpider) {
        LootContext spiderLootContext = EntityPredicate.createContext(player, spider);
        LootContext caveSpiderLootContext = EntityPredicate.createContext(player, caveSpider);
        trigger(player, conditions -> conditions.matches(spiderLootContext, caveSpiderLootContext));
    }

    @Override
    public Codec<ConvertSpiderTrigger.Conditions> codec() {
        return ConvertSpiderTrigger.Conditions.CODEC;
    }
    public static record Conditions(Optional<Holder<LootItemCondition>> player, Optional<Holder<LootItemCondition>> spider,
                                    Optional<Holder<LootItemCondition>> caveSpider) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<ConvertSpiderTrigger.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.optionalField("player",LootItemCondition.CODEC, false)
                                .forGetter(ConvertSpiderTrigger.Conditions::player),
                        Codec.optionalField("spider",LootItemCondition.CODEC, false)
                                .forGetter(ConvertSpiderTrigger.Conditions::spider),
                        Codec.optionalField("caveSpider",LootItemCondition.CODEC, false)
                                .forGetter(ConvertSpiderTrigger.Conditions::caveSpider))
                        .apply(instance, ConvertSpiderTrigger.Conditions::new));

        public boolean matches(LootContext spiderContext, LootContext caveSpiderContext) {
                // Check if spider and caveSpider predicates are present and match their contexts
                boolean spiderMatches = spider.map(predicate -> predicate.value().test(spiderContext)).orElse(true);
                boolean caveSpiderMatches = caveSpider.map(predicate -> predicate.value().test(caveSpiderContext)).orElse(true);

                return spiderMatches && caveSpiderMatches;
        }
    }
}
