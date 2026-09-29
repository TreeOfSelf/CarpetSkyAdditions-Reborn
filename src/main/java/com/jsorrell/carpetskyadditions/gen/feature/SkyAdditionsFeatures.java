package com.jsorrell.carpetskyadditions.gen.feature;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public abstract class SkyAdditionsFeatures {
    public static void registerAll() {
        Registry.register(
                BuiltInRegistries.FEATURE_TYPE,
                new SkyAdditionsResourceLocation("locatable_structure").getResourceLocation(),
                LocatableStructureFeature.CODEC);
        Registry.register(
                BuiltInRegistries.FEATURE_TYPE,
                new SkyAdditionsResourceLocation("spawn_platform").getResourceLocation(),
                SpawnPlatformFeature.CODEC);
        Registry.register(
                BuiltInRegistries.FEATURE_TYPE,
                new SkyAdditionsResourceLocation("end_gateway_island").getResourceLocation(),
                EndGatewayIslandFeature.CODEC);
    }
}
