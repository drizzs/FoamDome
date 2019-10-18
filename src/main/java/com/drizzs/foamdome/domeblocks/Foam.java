package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.util.DomeLib;
import com.drizzs.foamdome.util.DomeRegistry;
import com.drizzs.foamdome.util.DomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
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
        int i = random.nextInt(1);
        if(i == 0) {
            if (world.getBlockState(east).getBlock().isIn(DomeTags.UNDERWATER) || world.getBlockState(west).getBlock().isIn(DomeTags.UNDERWATER)
                    || world.getBlockState(south).getBlock().isIn(DomeTags.UNDERWATER)  || world.getBlockState(north).getBlock().isIn(DomeTags.UNDERWATER)
                    || world.getBlockState(up).getBlock().isIn(DomeTags.UNDERWATER) || world.getBlockState(down).getBlock().isIn(DomeTags.UNDERWATER) )
           {
                world.setBlockState(pos, DomeLib.notfoam.getDefaultState());
            } else {
                world.destroyBlock(pos,true);
            }
        }




    }




}
