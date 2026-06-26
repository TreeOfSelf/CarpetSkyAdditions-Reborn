package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.CoralSpreader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

    @Unique
    private static CoralSpreader.CustomCalciteBlock registerCalcite(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, CoralSpreader.CustomCalciteBlock> function, BlockBehaviour.Properties properties) {
        CoralSpreader.CustomCalciteBlock block = function.apply(properties.setId(resourceKey));
        return Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
    }

    @Unique
    private static Block registerCalcite(BlockItemId id, BlockBehaviour.Properties properties) {
        return registerCalcite(id.block(), CoralSpreader.CustomCalciteBlock::new, properties);
    }

    @Redirect(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Lnet/minecraft/references/BlockItemId;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0),
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=calcite"))
    )
    private static Block registerCustomCalcite(BlockItemId id, BlockBehaviour.Properties properties) {
        return registerCalcite(id, properties);
    }

}
