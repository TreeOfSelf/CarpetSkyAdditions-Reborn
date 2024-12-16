package com.jsorrell.carpetskyadditions.mixin;

import static com.jsorrell.carpetskyadditions.helpers.SkyAdditionsEnchantmentHelper.SWIFT_SNEAK_ENCHANTABLE_TAG;

import com.google.common.collect.Lists;
import com.jsorrell.carpetskyadditions.SkyAdditionsExtension;
import com.jsorrell.carpetskyadditions.helpers.SkyAdditionsEnchantmentHelper;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.jsorrell.carpetskyadditions.util.SkyAdditionsDataComponents;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {


    @Inject(
        method = "getAvailableEnchantmentResults",
        at =
        @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"),
        locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void forceAllowSwiftSneak(
        int i, ItemStack stack, Stream<Holder<Enchantment>> stream,
        CallbackInfoReturnable<List<EnchantmentInstance>> cir, @Local List<EnchantmentInstance> enchantmentList) {
        Holder.Reference<Enchantment> swiftSneak = SkyAdditionsExtension.minecraftServer.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.SWIFT_SNEAK).orElseThrow();
        if (Boolean.TRUE.equals(stack.get(SkyAdditionsDataComponents.SWIFT_SNEAK_ENCHANTABLE_COMPONENT))) {

            if (   swiftSneak.value().canEnchant(stack) || stack.is(Items.BOOK)) {
                for (int level = 3; 1 <= level; --level) {
                    /*if (SkyAdditionsEnchantmentHelper.getSwiftSneakMinCost(level) <= i
                        && i <= SkyAdditionsEnchantmentHelper.getSwiftSneakMaxCost(level)) {*/
                        enchantmentList.add(new EnchantmentInstance(
                            swiftSneak,
                            level));
                        //break;
                    //}
                }
            }
        }
    }
}
