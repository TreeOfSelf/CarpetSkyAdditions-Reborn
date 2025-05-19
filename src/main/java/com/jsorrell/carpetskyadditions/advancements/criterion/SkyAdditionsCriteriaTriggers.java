package com.jsorrell.carpetskyadditions.advancements.criterion;

import net.minecraft.advancements.CriteriaTriggers;

public class SkyAdditionsCriteriaTriggers {
    public static final GenerateGeodeTrigger GENERATE_GEODE = new GenerateGeodeTrigger();
    public static final ConvertSpiderTrigger CONVERT_SPIDER = new ConvertSpiderTrigger();
    public static final AllayVexTrigger ALLAY_VEX = new AllayVexTrigger();
    public static final BreezeRodTrialSpawnerTrigger ACTIVATE_TRIAL_SPAWNER = new BreezeRodTrialSpawnerTrigger();
    public static final CreakingHeartTrigger CREAKING_HEART = new CreakingHeartTrigger();
    public static final DeadBushToBushTrigger DEAD_BUSH_TO_BUSH = new DeadBushToBushTrigger();


    public static void registerAll() {
        CriteriaTriggers.register("carpetskyadditions:generate_geode", GENERATE_GEODE);
        CriteriaTriggers.register("carpetskyadditions:convert_spider", CONVERT_SPIDER);
        CriteriaTriggers.register("carpetskyadditions:allay_vex", ALLAY_VEX);
        CriteriaTriggers.register("carpetskyadditions:activate_trial_spawner", ACTIVATE_TRIAL_SPAWNER);
        CriteriaTriggers.register("carpetskyadditions:creaking_heart", CREAKING_HEART);
        CriteriaTriggers.register("carpetskyadditions:bush", DEAD_BUSH_TO_BUSH);

    }
}
