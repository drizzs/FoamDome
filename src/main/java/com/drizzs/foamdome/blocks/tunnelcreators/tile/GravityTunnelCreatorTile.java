package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;

import static com.drizzs.foamdome.util.DomeRegistry.*;
import static com.drizzs.foamdome.util.DomeTags.GRAVITYDOME;

public class GravityTunnelCreatorTile extends TunnelCreatorTile {

    public GravityTunnelCreatorTile() {
        super(GRAVITY_TUNNEL_TILE.get());
    }

    @Override
    public ITag.INamedTag<Block> getTag() {
        return GRAVITYDOME;
    }

    @Override
    public BlockState getFoam() {
        return HARD_GRAVITY_FOAM.get().getDefaultState();
    }

    public BlockState getFoam2() {
        return DISOLVABLE_GRAVITY_FOAM.get().getDefaultState();
    }
}
