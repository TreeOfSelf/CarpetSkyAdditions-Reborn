package com.jsorrell.carpetskyadditions.advancements.predicates;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SkyAdditionsLootItemConditions {
    public static void bootstrap() {
        register("location_check", SkyAdditionsLocationCheck.CODEC);
        register("entity_properties", SkyAdditionsLootItemEntityPropertyCondition.CODEC);
    }

    private static void register(String registryName, MapCodec<? extends LootItemCondition> codec) {
        Registry.register(
                BuiltInRegistries.LOOT_CONDITION_TYPE,
                new SkyAdditionsResourceLocation(registryName).getResourceLocation(),
                codec);
    }
}
