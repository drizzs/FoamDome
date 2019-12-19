package com.drizzs.foamdome.blocks.domecreators;

import com.drizzs.foamdome.blocks.domecreators.tile.GlassDomeCreatorTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class GlassDomeCreatorBlock extends DomeCreatorBlock {

    public GlassDomeCreatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GlassDomeCreatorTile();
    }
}
