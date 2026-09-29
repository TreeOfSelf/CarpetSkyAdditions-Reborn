package com.jsorrell.carpetskyadditions.gen.feature;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;

public abstract class SkyAdditionsConfiguredFeatures {
    public static final ResourceKey<Feature> SPAWN_PLATFORM = feature("spawn_platform");
    public static final ResourceKey<Feature> GATEWAY_ISLAND = feature("end_gateway_island");

    private static ResourceKey<Feature> feature(String path) {
        return ResourceKey.create(Registries.FEATURE, new SkyAdditionsResourceLocation(path).getResourceLocation());
    }
}
