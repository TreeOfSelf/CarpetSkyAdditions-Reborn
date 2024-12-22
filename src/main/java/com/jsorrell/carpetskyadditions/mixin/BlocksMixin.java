package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.CoralSpreader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
    private static Block register(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        Block block = function.apply(properties.setId(resourceKey));
        return  Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
    }

    @Unique
    private static CoralSpreader.CustomCalciteBlock registerCalcite(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, CoralSpreader.CustomCalciteBlock> function, BlockBehaviour.Properties properties) {
        CoralSpreader.CustomCalciteBlock block = function.apply(properties.setId(resourceKey));
        return  Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
    }


    @Unique
    private static Block register(String string, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        return register(vanillaBlockId(string), function, properties);
    }

    @Unique
    private static CoralSpreader.CustomCalciteBlock registerCalcite(String string, Function<BlockBehaviour.Properties, CoralSpreader.CustomCalciteBlock> function, BlockBehaviour.Properties properties) {
        return registerCalcite(vanillaBlockId(string), function, properties);
    }

    @Unique
    private static Block register(String string, BlockBehaviour.Properties properties) {
        return register(string, Block::new, properties);
    }

    @Unique
    private static Block registerCalcite(String string, BlockBehaviour.Properties properties) {
        return register(string, CoralSpreader.CustomCalciteBlock::new, properties);
    }

    @Unique
    private static ResourceKey<Block> vanillaBlockId(String string) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace(string));
    }

    @Redirect(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"),
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=calcite"))
    )
    private static Block registerCustomCalcite(String name, BlockBehaviour.Properties properties) {
        if (name == "calcite") {
            return registerCalcite(name, properties);
        } else {
            return register(name, properties);
        }
    }


}
