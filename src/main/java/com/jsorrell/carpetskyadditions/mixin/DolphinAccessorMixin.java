
package com.jsorrell.carpetskyadditions.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Dolphin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Dolphin.class)
public interface DolphinAccessorMixin {
    @Accessor
    BlockPos getTreasurePos();

    @Accessor
    void setTreasurePos(BlockPos pos);
}
