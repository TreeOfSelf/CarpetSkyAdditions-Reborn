package com.jsorrell.carpetskyadditions;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.minecraft.network.chat.Component;

public class SkyAdditionsDataPacks {
    public static final SkyAdditionsResourceLocation SKYBLOCK = new SkyAdditionsResourceLocation("skyblock");
    public static final SkyAdditionsResourceLocation SKYBLOCK_MMH = new SkyAdditionsResourceLocation("skyblock_mmh");
    public static final SkyAdditionsResourceLocation SKYBLOCK_ACACIA = new SkyAdditionsResourceLocation("skyblock_acacia");
    public static final SkyAdditionsResourceLocation SKYBLOCK_ACACIA_MMH = new SkyAdditionsResourceLocation("skyblock_acacia_mmh");
    public static final SkyAdditionsResourceLocation SKYBLOCK_NO_TREE = new SkyAdditionsResourceLocation("skyblock_no_tree");
    public static final SkyAdditionsResourceLocation SKYBLOCK_NO_TREE_MMH = new SkyAdditionsResourceLocation("skyblock_no_tree_mmh");

    public static void register() {
        // Add the embedded datapacks as an option on the create world screen
        if (!ResourceManagerHelper.registerBuiltinResourcePack(
                new SkyAdditionsResourceLocation("skyblock").getResourceLocation(),
                SkyAdditionsExtension.MOD_CONTAINER,
                Component.translatable("datapack.carpetskyadditions.skyblock"),
                ResourcePackActivationType.NORMAL)) {
            SkyAdditionsSettings.LOG.warn("Could not register built-in datapack \"skyblock\".");
        }

        if (!ResourceManagerHelper.registerBuiltinResourcePack(
                new SkyAdditionsResourceLocation("skyblock_mmh").getResourceLocation(),
                SkyAdditionsExtension.MOD_CONTAINER,
                Component.translatable("datapack.carpetskyadditions.skyblock_mmh"),
                ResourcePackActivationType.NORMAL)) {
            SkyAdditionsSettings.LOG.warn("Could not register built-in datapack \"skyblock_mmh\".");
        }

        if (!ResourceManagerHelper.registerBuiltinResourcePack(
                new SkyAdditionsResourceLocation("skyblock_acacia").getResourceLocation(),
                SkyAdditionsExtension.MOD_CONTAINER,
                Component.translatable("datapack.carpetskyadditions.acacia"),
                ResourcePackActivationType.NORMAL)) {
            SkyAdditionsSettings.LOG.warn("Could not register built-in datapack \"skyblock_acacia\".");
        }

        if (!ResourceManagerHelper.registerBuiltinResourcePack(
                new SkyAdditionsResourceLocation("skyblock_acacia_mmh").getResourceLocation(),
                SkyAdditionsExtension.MOD_CONTAINER,
                Component.translatable("datapack.carpetskyadditions.acacia_mmh"),
                ResourcePackActivationType.NORMAL)) {
            SkyAdditionsSettings.LOG.warn("Could not register built-in datapack \"skyblock_acacia_mmh\".");
        }

        if (!ResourceManagerHelper.registerBuiltinResourcePack(
                new SkyAdditionsResourceLocation("skyblock_no_tree").getResourceLocation(),
                SkyAdditionsExtension.MOD_CONTAINER,
                Component.translatable("datapack.carpetskyadditions.no_tree"),
                ResourcePackActivationType.NORMAL)) {
            SkyAdditionsSettings.LOG.warn("Could not register built-in datapack \"skyblock_no_tree\".");
        }

        if (!ResourceManagerHelper.registerBuiltinResourcePack(
                new SkyAdditionsResourceLocation("skyblock_no_tree_mmh").getResourceLocation(),
                SkyAdditionsExtension.MOD_CONTAINER,
                Component.translatable("datapack.carpetskyadditions.no_tree_mmh"),
                ResourcePackActivationType.NORMAL)) {
            SkyAdditionsSettings.LOG.warn("Could not register built-in datapack \"skyblock_no_tree_mmh\".");
        }

    }
}
