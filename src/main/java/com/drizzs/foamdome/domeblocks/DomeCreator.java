package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.domeregistry.DomeBlocks;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class DomeCreator extends Block {

    public DomeCreator(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {

        int size = 5;

        for(int i = -size; i <= size; i++){
            for(int k = -size; k <= size; k++){
                for(int j = -size; j <= size; j++){
                    BlockPos posAll = pos.add(i,j,k);
                    if(world.getBlockState(posAll).getBlock() == Blocks.WATER){
                        world.setBlockState(posAll, DomeBlocks.foam.getDefaultState());
                    }
                    else{
                        break;
                    }
                }
            }
        }
        return onBlockActivated(state, world, pos, player, hand, trace);
    }










}
