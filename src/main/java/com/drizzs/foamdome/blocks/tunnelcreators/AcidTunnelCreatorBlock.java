package com.drizzs.foamdome.blocks.tunnelcreators;

import com.drizzs.foamdome.blocks.CreatorBlock;
import com.drizzs.foamdome.blocks.domecreators.tile.AcidDomeCreatorTile;
import com.drizzs.foamdome.blocks.tunnelcreators.tile.AcidTunnelCreatorTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class AcidTunnelCreatorBlock extends CreatorBlock {

    public AcidTunnelCreatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AcidTunnelCreatorTile();
    }

}
