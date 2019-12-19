package com.drizzs.foamdome.tunnelcreators.tile;

import com.drizzs.foamdome.domecreators.tile.DomeCreatorTile;
import net.minecraft.block.BlockState;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;

public class GlassTunnelCreatorTile extends DomeCreatorTile {

    public GlassTunnelCreatorTile() {
        super(GLASS_TUNNEL_TILE.get());
    }

    @Override
    public BlockState getFoam() {
        return GLASS_FOAM.get().getDefaultState();
    }
}
