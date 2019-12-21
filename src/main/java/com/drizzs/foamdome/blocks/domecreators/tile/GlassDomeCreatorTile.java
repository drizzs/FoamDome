package com.drizzs.foamdome.blocks.domecreators.tile;

import com.drizzs.foamdome.blocks.CreatorTile;
import net.minecraft.block.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.*;

public class GlassDomeCreatorTile extends CreatorTile {

    public GlassDomeCreatorTile() {
        super(GLASS_DOME_TILE.get());
    }

    @Override
    public BlockState getFoam() {
        return GLASS_FOAM.get().getDefaultState();
    }

    @Override
    public BlockState getFoam2() {
        return DISOLVABLE_GLASS_FOAM.get().getDefaultState();
    }
}
