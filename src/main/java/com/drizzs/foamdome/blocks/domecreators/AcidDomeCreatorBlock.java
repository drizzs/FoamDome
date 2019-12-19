package com.drizzs.foamdome.blocks.domecreators;

import com.drizzs.foamdome.blocks.CreatorBlock;
import com.drizzs.foamdome.blocks.domecreators.tile.AcidDomeCreatorTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class AcidDomeCreatorBlock extends CreatorBlock {

    public AcidDomeCreatorBlock(Properties properties) {
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
