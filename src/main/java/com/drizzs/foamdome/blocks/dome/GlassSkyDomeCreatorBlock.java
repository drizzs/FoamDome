package com.drizzs.foamdome.blocks.dome;

import com.drizzs.foamdome.blocks.base.AntiGravityBlock;
import com.drizzs.foamdome.util.DomeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GlassSkyDomeCreatorBlock extends AntiGravityBlock {
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return DomeRegistry.GLASS_SKY_DOME_TILE.get().create(pos,state);
    }

    @Override
    protected String getType() {
        return "glass_sky_foam_container";
    }

}
