package com.jsorrell.carpetskyadditions.helpers;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import java.util.Optional;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class WanderingTraderHelper {
    public static void addSkyAdditionsTrades(WanderingTrader trader) {
        if (!SkyAdditionsSettings.tallFlowersFromWanderingTrader
            && !SkyAdditionsSettings.lavaFromWanderingTrader) {
            return;
        }
        if (SkyAdditionsSettings.tallFlowersFromWanderingTrader) {
            trader.getOffers()
                .add(
                    new MerchantOffer(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.SUNFLOWER, 1),
                        12,
                        1,
                        0.05f));
            trader.getOffers()
                .add(
                    new MerchantOffer(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.LILAC, 1),
                        12,
                        1,
                        0.05f));
            trader.getOffers()
                .add(
                    new MerchantOffer(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.ROSE_BUSH, 1),
                        12,
                        1,
                        0.05f));
            trader.getOffers()
                .add(
                    new MerchantOffer(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.PEONY, 1),
                        12,
                        1,
                        0.05f));
        }
        if (SkyAdditionsSettings.lavaFromWanderingTrader) {
            trader.getOffers()
                .add(
                    new MerchantOffer(
                        new ItemCost(Items.BUCKET, 1),
                        Optional.of(new ItemCost(Items.EMERALD, 16)),
                        new ItemStack(Items.LAVA_BUCKET, 1),
                        1,
                        1,
                        0.05f));
        }
    }
}
