package com.drizzs.foamdome.blocks.domecreators.tile;

import com.drizzs.foamdome.blocks.CreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.Tag;

import static com.drizzs.foamdome.util.DomeRegistryNew.ACID_DOME_TILE;
import static com.drizzs.foamdome.util.DomeRegistryNew.ACID_FOAM;
import static com.drizzs.foamdome.util.DomeTags.ACID;

public class AcidDomeCreatorTile extends CreatorTile {

    public AcidDomeCreatorTile() {
        super(ACID_DOME_TILE.get());
    }

    @Override
    public BlockState getFoam() {
        return ACID_FOAM.get().getDefaultState();
    }

    @Override
    public Tag<Block> getTag() {
        return ACID;
    }

}
