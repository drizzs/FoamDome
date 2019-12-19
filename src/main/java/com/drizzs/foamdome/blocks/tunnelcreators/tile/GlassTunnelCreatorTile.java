package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import net.minecraft.block.BlockState;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;

public class GlassTunnelCreatorTile extends TunnelCreatorTile {

    public GlassTunnelCreatorTile() {
        super(GLASS_TUNNEL_TILE.get());
    }

    @Override
    public BlockState getFoam() {
        return GLASS_FOAM.get().getDefaultState();
    }

}
