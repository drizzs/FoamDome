package com.drizzs.foamdome.blockentities.dome;

import com.drizzs.foamdome.blockentities.base.BaseSkyCreator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.*;

public class SkyFoamCreatorTile extends BaseSkyCreator {

    public SkyFoamCreatorTile(BlockPos pos, BlockState state) {
        super(SKY_DOME_TILE.get(),pos,state);
    }

    @Override
    public BlockState getFoam() {
        return HARD_SKY_FOAM.get().defaultBlockState();
    }

}
