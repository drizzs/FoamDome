package com.drizzs.foamdome.blockentities.dome;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.BASIC_DOME_TILE;

public class BasicDomeCreatorTile extends CreatorEntity {

    public BasicDomeCreatorTile(BlockPos pos, BlockState state) {
        super(BASIC_DOME_TILE.get(),pos,state);
    }

}
