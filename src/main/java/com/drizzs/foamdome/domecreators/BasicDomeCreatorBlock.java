package com.drizzs.foamdome.domecreators;

import com.drizzs.foamdome.domecreators.tile.BasicDomeCreatorTile;
import com.drizzs.foamdome.domecreators.tile.DomeCreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BasicDomeCreatorBlock extends Block {

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
