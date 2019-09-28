package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.domeregistry.DomeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class Foam extends Block {

    public Foam(Properties properties) {
        super(properties);
    }



    @Override
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random random)
    {
        BlockPos up = pos.up();
        BlockPos down = pos.down();
        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos east = pos.east();
        BlockPos west = pos.west();
        int i = random.nextInt(50);
        if(i == 0) {
            if (worldIn.getBlockState(east).getBlock() == Blocks.WATER
                    || worldIn.getBlockState(west).getBlock() == Blocks.WATER
                    || worldIn.getBlockState(south).getBlock() == Blocks.WATER
                    || worldIn.getBlockState(north).getBlock() == Blocks.WATER
                    || worldIn.getBlockState(up).getBlock() == Blocks.WATER
                    || worldIn.getBlockState(down).getBlock() == Blocks.WATER) {
                worldIn.setBlockState(pos, DomeBlocks.notfoam.getDefaultState());
            } else {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }




    }




}
