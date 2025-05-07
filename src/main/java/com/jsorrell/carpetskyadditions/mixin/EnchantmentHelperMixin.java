package com.jsorrell.carpetskyadditions.mixin;


import com.jsorrell.carpetskyadditions.SkyAdditionsExtension;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {


    @Inject(
        method = "getAvailableEnchantmentResults",
        at =
        @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private static void forceAllowSwiftSneak(
        int i, ItemStack stack, Stream<Holder<Enchantment>> stream,
        CallbackInfoReturnable<List<EnchantmentInstance>> cir, @Local List<EnchantmentInstance> enchantmentList) {
        Holder.Reference<Enchantment> swiftSneak = SkyAdditionsExtension.minecraftServer.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.SWIFT_SNEAK).orElseThrow();
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        if (customData.copyTag().getBoolean("SWIFT_SNEAK_ENCHANTABLE").get() /*EnchantmentHelperContexts.FORCE_ALLOW_SWIFT_SNEAK.get()*/) {
            if (swiftSneak.value().canEnchant(stack) || stack.is(Items.BOOK)) {
                for (int level = 1; level <= 3; level++) {
                    enchantmentList.add(new EnchantmentInstance(
                        swiftSneak,
                        level));
                }
            }
        }
    }
}
