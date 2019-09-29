package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.domeregistry.DomeBlocks;
import com.drizzs.foamdome.util.FoamTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class Foam extends Block {

    public Foam(Properties properties) {
        super(properties);
    }


    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random)
    {
        BlockPos up = pos.up();
        BlockPos down = pos.down();
        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos east = pos.east();
        BlockPos west = pos.west();
        int i = random.nextInt(50);
        if(i == 0) {
            if (world.getBlockState(east).getBlock().getTags().contains(FoamTags.UNDERWATER) || world.getBlockState(west).getBlock().getTags().contains(FoamTags.UNDERWATER)
                    || world.getBlockState(south).getBlock().getTags().contains(FoamTags.UNDERWATER)  || world.getBlockState(north).getBlock().getTags().contains(FoamTags.UNDERWATER)
                    || world.getBlockState(up).getBlock().getTags().contains(FoamTags.UNDERWATER) || world.getBlockState(down).getBlock().getTags().contains(FoamTags.UNDERWATER) )
           {
                world.setBlockState(pos, DomeBlocks.notfoam.getDefaultState());
            } else {
                world.destroyBlock(pos,true);
            }
        }




    }




}
