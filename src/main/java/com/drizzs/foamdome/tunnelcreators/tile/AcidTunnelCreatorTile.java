package com.drizzs.foamdome.tunnelcreators.tile;

import com.drizzs.foamdome.domecreators.tile.DomeCreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.Tag;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;

public class AcidTunnelCreatorTile extends DomeCreatorTile {

    public AcidTunnelCreatorTile() {
        super(ACID_TUNNEL_TILE.get());
    }

    @Override
    public BlockState getFoam() {
        return ACID_FOAM.get().getDefaultState();
    }

    @Override
    public Tag<Block> getTag() {
        return null;
    }
}
