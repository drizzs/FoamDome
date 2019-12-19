package com.drizzs.foamdome.domecreators.tile;

import net.minecraft.block.BlockState;

import static com.drizzs.foamdome.util.DomeRegistryNew.GLASS_DOME_TILE;
import static com.drizzs.foamdome.util.DomeRegistryNew.GLASS_FOAM;

public class GlassDomeCreatorTile extends DomeCreatorTile {

    public GlassDomeCreatorTile() {
        super(GLASS_DOME_TILE.get());
    }

    @Override
    public BlockState getFoam() {
        return GLASS_FOAM.get().getDefaultState();
    }
}
