package com.drizzs.foamdome.tunnelcreators;

import com.drizzs.foamdome.domecreators.tile.AcidDomeCreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class GlassTunnelCreatorBlock extends Block{

    public GlassTunnelCreatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AcidDomeCreatorTile();
    }
}
