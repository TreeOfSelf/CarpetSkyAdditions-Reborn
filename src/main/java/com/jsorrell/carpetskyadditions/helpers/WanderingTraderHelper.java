package com.jsorrell.carpetskyadditions.helpers;

import com.google.common.collect.ImmutableMap;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Pair;

public class WanderingTraderHelper {
    public static Int2ObjectMap<VillagerTrades.ItemListing[]> getTrades() {
        Pair<VillagerTrades.ItemListing[], Integer> tier1Pair = VillagerTrades.WANDERING_TRADER_TRADES.get(1);
        Pair<VillagerTrades.ItemListing[], Integer> tier2Pair = VillagerTrades.WANDERING_TRADER_TRADES.get(2);

        List<VillagerTrades.ItemListing> tier1List = new ArrayList<>(Arrays.asList(tier1Pair.getLeft()));
        List<VillagerTrades.ItemListing> tier2List = new ArrayList<>(Arrays.asList(tier2Pair.getLeft()));

        if (SkyAdditionsSettings.tallFlowersFromWanderingTrader) {
            tier1List.add(new VillagerTrades.ItemsForEmeralds(Items.SUNFLOWER, 1, 1, 12, 1));
            tier1List.add(new VillagerTrades.ItemsForEmeralds(Items.LILAC, 1, 1, 12, 1));
            tier1List.add(new VillagerTrades.ItemsForEmeralds(Items.ROSE_BUSH, 1, 1, 12, 1));
            tier1List.add(new VillagerTrades.ItemsForEmeralds(Items.PEONY, 1, 1, 12, 1));
        }
        tier1List.add(new VillagerTrades.ItemsForEmeralds(Items.PINK_PETALS, 1, 16, 12, 1));

        if (SkyAdditionsSettings.lavaFromWanderingTrader) {
            tier2List.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.BUCKET, 1, 16, Items.LAVA_BUCKET, 1, 1, 1, 1));
        }

        VillagerTrades.ItemListing[] tier1Array = tier1List.toArray(new VillagerTrades.ItemListing[0]);
        VillagerTrades.ItemListing[] tier2Array = tier2List.toArray(new VillagerTrades.ItemListing[0]);

        return new Int2ObjectOpenHashMap<>(ImmutableMap.of(
            1, tier1Array,
            2, tier2Array
        ));
    }
}
