package com.drizzs.foamdome.blocks.tunnelcreators;

import com.drizzs.foamdome.blocks.CreatorBlock;
import com.drizzs.foamdome.blocks.tunnelcreators.tile.BasicTunnelCreatorTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BasicTunnelCreatorBlock extends CreatorBlock {

    public BasicTunnelCreatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BasicTunnelCreatorTile();
    }
}
