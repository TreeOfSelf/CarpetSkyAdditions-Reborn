package com.jsorrell.carpetskyadditions.helpers;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Pair;

public class WanderingTraderHelper {
    public static List<Pair<VillagerTrades.ItemListing[], Integer>> getTrades() {
        List<Pair<VillagerTrades.ItemListing[], Integer>> originalTradeGroups = VillagerTrades.WANDERING_TRADER_TRADES;
        List<Pair<VillagerTrades.ItemListing[], Integer>> newTrades = new ArrayList<>();

        for (int i = 0; i < originalTradeGroups.size(); i++) {
            Pair<VillagerTrades.ItemListing[], Integer> currentVanillaPair = originalTradeGroups.get(i);
            List<VillagerTrades.ItemListing> currentGroupTradeList = new ArrayList<>(Arrays.asList(currentVanillaPair.getLeft()));
            Integer tradesToPickFromGroup = currentVanillaPair.getRight();

            if (i == 2) {
                if (SkyAdditionsSettings.tallFlowersFromWanderingTrader) {
                    currentGroupTradeList.add(new VillagerTrades.ItemsForEmeralds(Items.SUNFLOWER, 1, 1, 12, 1));
                    currentGroupTradeList.add(new VillagerTrades.ItemsForEmeralds(Items.LILAC, 1, 1, 12, 1));
                    currentGroupTradeList.add(new VillagerTrades.ItemsForEmeralds(Items.ROSE_BUSH, 1, 1, 12, 1));
                    currentGroupTradeList.add(new VillagerTrades.ItemsForEmeralds(Items.PEONY, 1, 1, 12, 1));
                }

                //currentGroupTradeList.add(new VillagerTrades.ItemsForEmeralds(Items.PINK_PETALS, 1, 16, 12, 1));
            } else if (i == 1) {
                if (SkyAdditionsSettings.lavaFromWanderingTrader) {
                    currentGroupTradeList.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.BUCKET, 1, 16, Items.LAVA_BUCKET, 1, 1, 1, 1));
                }
            }


            VillagerTrades.ItemListing[] currentGroupTradeArray = currentGroupTradeList.toArray(new VillagerTrades.ItemListing[0]);
            newTrades.add(Pair.of(currentGroupTradeArray, tradesToPickFromGroup));
        }

        return newTrades;
    }
}
