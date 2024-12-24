package com.jsorrell.carpetskyadditions.mixincontext;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.stream.Stream;

public class EnchantmentHelperContexts {
    /**
     * An Additional Parameter for {@link net.minecraft.world.item.enchantment.EnchantmentHelper#selectEnchantment(RandomSource, ItemStack, int, Stream)}
     * if true, swift sneak will be available within the enchantment table
     */
    public static final ThreadLocal<Boolean> FORCE_ALLOW_SWIFT_SNEAK = ThreadLocal.withInitial(() -> false);
}
