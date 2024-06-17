package com.jsorrell.carpetskyadditions.mixin;

import static com.jsorrell.carpetskyadditions.helpers.SkyAdditionsEnchantmentHelper.SWIFT_SNEAK_ENCHANTABLE_TAG;

import com.google.common.collect.Lists;
import com.jsorrell.carpetskyadditions.helpers.SkyAdditionsEnchantmentHelper;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.core.Holder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(
        method = "getAvailableEnchantmentResults",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
        ),
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void forceAllowSwiftSneak(int i, ItemStack itemStack, Stream<Holder<Enchantment>> stream, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {

        if (itemStack.getTags().findAny().isPresent() && itemStack.getTags().anyMatch(tag -> tag.equals(SWIFT_SNEAK_ENCHANTABLE_TAG))) {
            if ( /*Enchantments.SWIFT_SNEAK.canEnchant(itemStack.getItem().getDefaultInstance()) ||itemStack.is(Items.BOOK)*/ true) {
                for (int level = 3; 1 <= level; --level) {
                    if (SkyAdditionsEnchantmentHelper.getSwiftSneakMinCost(level) <= i
                            && i <= SkyAdditionsEnchantmentHelper.getSwiftSneakMaxCost(level)) {
                        List<EnchantmentInstance> list = Lists.newArrayList();
                        list.add(new EnchantmentInstance((Holder<Enchantment>) Enchantments.SWIFT_SNEAK, level));
                        break;
                    }
                }
            }
        }
    }
}
