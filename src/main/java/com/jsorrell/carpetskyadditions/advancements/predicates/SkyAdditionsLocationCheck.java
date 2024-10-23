package com.jsorrell.carpetskyadditions.advancements.predicates;

import java.util.Optional;

import com.jsorrell.carpetskyadditions.advancements.criterion.SkyAdditionsLocationPredicate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

public record SkyAdditionsLocationCheck(Optional<SkyAdditionsLocationPredicate> predicate, BlockPos offset)
                implements LootItemCondition {

        public static final MapCodec<BlockPos> OFFSET_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                Codec.optionalField("offsetX", Codec.INT, false).forGetter(vec3i -> Optional.of(vec3i.getX())),
                Codec.optionalField("offsetY", Codec.INT, false).forGetter(vec3i -> Optional.of(vec3i.getY())),
                Codec.optionalField("offsetZ", Codec.INT, false).forGetter(vec3i -> Optional.of(vec3i.getZ()))
            ).apply(instance, (x, y, z) -> new BlockPos(x.orElse(0), y.orElse(0), z.orElse(0)))
        );


        public static final MapCodec<SkyAdditionsLocationCheck> CODEC = RecordCodecBuilder.mapCodec(
                        instance -> instance.group(
                                        Codec.optionalField("predicate", SkyAdditionsLocationPredicate.CODEC,
                                                        false)
                                                        .forGetter(SkyAdditionsLocationCheck::predicate),
                                        OFFSET_CODEC.forGetter(SkyAdditionsLocationCheck::offset))
                                        .apply(instance, SkyAdditionsLocationCheck::new));

        @Override
        public LootItemConditionType getType() {
                return SkyAdditionsLootItemConditions.LOCATION_CHECK;
        }

        public boolean test(LootContext lootContext) {
                Vec3 origin = lootContext.getParameter(LootContextParams.ORIGIN);
                return origin != null
                                && (predicate.isPresent() && predicate.get().matches(
                                                lootContext.getLevel(),
                                                origin.x() + offset.getX(),
                                                origin.y() + offset.getY(),
                                                origin.z() + offset.getZ()));
        }

        public static LootItemCondition.Builder checkLocation(LocationPredicate.Builder locationPredicateBuilder) {
                return () -> new SkyAdditionsLocationCheck(
                                Optional.of(new SkyAdditionsLocationPredicate(
                                                Optional.of(locationPredicateBuilder.build()), null, null, null, null)),
                                BlockPos.ZERO);
        }

        public static LootItemCondition.Builder checkLocation(LocationPredicate.Builder locationPredicateBuilder,
                        BlockPos offset) {
                return () -> new SkyAdditionsLocationCheck(
                                Optional.of(new SkyAdditionsLocationPredicate(
                                                Optional.of(locationPredicateBuilder.build()), null, null, null, null)),
                                offset);
        }

}
