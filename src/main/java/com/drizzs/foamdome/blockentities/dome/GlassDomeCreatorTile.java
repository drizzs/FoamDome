package com.drizzs.foamdome.blockentities.dome;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.*;

public class GlassDomeCreatorTile extends CreatorEntity {

    public GlassDomeCreatorTile(BlockPos pos, BlockState state) {
        super(GLASS_DOME_TILE.get(),pos,state);
    }

    @Override
    public BlockState getFoam() {
        return GLASS_FOAM.get().defaultBlockState();
    }

    @Override
    public BlockState getFoam2() {
        return DISOLVABLE_GLASS_FOAM.get().defaultBlockState();
    }
}
