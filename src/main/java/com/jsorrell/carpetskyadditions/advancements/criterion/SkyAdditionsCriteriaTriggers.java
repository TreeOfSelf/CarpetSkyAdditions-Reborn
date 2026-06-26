package com.jsorrell.carpetskyadditions.advancements.criterion;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class SkyAdditionsCriteriaTriggers {
    public static final GenerateGeodeTrigger GENERATE_GEODE = new GenerateGeodeTrigger();
    public static final ConvertSpiderTrigger CONVERT_SPIDER = new ConvertSpiderTrigger();
    public static final AllayVexTrigger ALLAY_VEX = new AllayVexTrigger();
    public static final BreezeRodTrialSpawnerTrigger ACTIVATE_TRIAL_SPAWNER = new BreezeRodTrialSpawnerTrigger();
    public static final CreakingHeartTrigger CREAKING_HEART = new CreakingHeartTrigger();
    public static final DeadBushToBushTrigger DEAD_BUSH_TO_BUSH = new DeadBushToBushTrigger();


    public static void registerAll() {
        Registry.register(BuiltInRegistries.TRIGGER_TYPES, "carpetskyadditions:generate_geode", GENERATE_GEODE);
        Registry.register(BuiltInRegistries.TRIGGER_TYPES, "carpetskyadditions:convert_spider", CONVERT_SPIDER);
        Registry.register(BuiltInRegistries.TRIGGER_TYPES, "carpetskyadditions:allay_vex", ALLAY_VEX);
        Registry.register(BuiltInRegistries.TRIGGER_TYPES, "carpetskyadditions:activate_trial_spawner", ACTIVATE_TRIAL_SPAWNER);
        Registry.register(BuiltInRegistries.TRIGGER_TYPES, "carpetskyadditions:creaking_heart", CREAKING_HEART);
        Registry.register(BuiltInRegistries.TRIGGER_TYPES, "carpetskyadditions:bush", DEAD_BUSH_TO_BUSH);

    }
}
