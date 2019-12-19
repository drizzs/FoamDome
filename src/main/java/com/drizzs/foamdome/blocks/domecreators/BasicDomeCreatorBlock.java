package com.drizzs.foamdome.blocks.domecreators;

import com.drizzs.foamdome.blocks.domecreators.tile.BasicDomeCreatorTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BasicDomeCreatorBlock extends DomeCreatorBlock {

    public BasicDomeCreatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BasicDomeCreatorTile();
    }
}
