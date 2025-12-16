package com.jsorrell.carpetskyadditions.advancements.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record SkyAdditionsLootItemEntityPropertyCondition(Optional<EntityPredicate> predicate, LootContext.EntityTarget entityTarget) implements LootItemCondition {

   public static final MapCodec<SkyAdditionsLootItemEntityPropertyCondition> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
					Codec.optionalField("predicate",EntityPredicate.CODEC, false ).forGetter(SkyAdditionsLootItemEntityPropertyCondition::predicate),
					LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(SkyAdditionsLootItemEntityPropertyCondition::entityTarget)
				)
				.apply(instance, SkyAdditionsLootItemEntityPropertyCondition::new)
	);

    @Override
    public @NotNull LootItemConditionType getType() {
        return SkyAdditionsLootItemConditions.ENTITY_PROPERTIES;
    }

    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParameter(LootContextParams.THIS_ENTITY);
        Vec3 origin = lootContext.getParameter(LootContextParams.ORIGIN);
        return this.predicate.isPresent() && this.predicate.get().matches(lootContext.getLevel(), origin, entity);
    }

	public static LootItemCondition.Builder hasProperties(LootContext.EntityTarget target, EntityPredicate.Builder predicateBuilder) {
		return () -> new SkyAdditionsLootItemEntityPropertyCondition(Optional.of(predicateBuilder.build()), target);
	}


}
