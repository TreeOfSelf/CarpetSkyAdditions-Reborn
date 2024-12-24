package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.helpers.DeadCoralToSandHelper;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseCoralFanBlock;
import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({BaseCoralPlantBlock.class, BaseCoralFanBlock.class})
public abstract class BaseCoralMixin extends BaseCoralPlantTypeBlock {
    public BaseCoralMixin(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        if (SkyAdditionsSettings.coralErosion) {
            level.scheduleTick(pos, this, DeadCoralToSandHelper.getSandDropDelay(level.getRandom()));
        }
        super.onPlace(state, level, pos, oldState, notify);
    }

    @Unique
    @SuppressWarnings("ConstantConditions")
    private boolean isCoralFan() {
        return (BaseCoralPlantTypeBlock) this instanceof CoralFanBlock;
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {

        if (SkyAdditionsSettings.coralErosion && !isCoralFan()) {

            scheduledTickAccess.scheduleTick(pos, this, DeadCoralToSandHelper.getSandDropDelay(randomSource));
        }

        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (SkyAdditionsSettings.coralErosion && DeadCoralToSandHelper.tryDropSand(state, level, pos, random)) {
            level.scheduleTick(pos, this, DeadCoralToSandHelper.getSandDropDelay(random));
        }
    }
}
