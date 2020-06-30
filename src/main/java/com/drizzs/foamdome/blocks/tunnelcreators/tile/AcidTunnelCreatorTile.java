package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;

import static com.drizzs.foamdome.util.DomeRegistry.*;
import static com.drizzs.foamdome.util.DomeTags.ACID;

public class AcidTunnelCreatorTile extends TunnelCreatorTile {

    public AcidTunnelCreatorTile() {
        super(ACID_TUNNEL_TILE.get());
    }

    @Override
    public BlockState getFoam() {
        return ACID_FOAM.get().getDefaultState();
    }

    @Override
    public BlockState getFoam2() {return ACID_FOAM.get().getDefaultState();}

    @Override
    public ITag.INamedTag<Block> getTag() {
        return ACID;
    }
}
