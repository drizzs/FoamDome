package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import net.minecraft.block.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.*;

public class GlassTunnelCreatorTile extends TunnelCreatorTile {

    public GlassTunnelCreatorTile() {
        super(GLASS_TUNNEL_TILE.get());
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
