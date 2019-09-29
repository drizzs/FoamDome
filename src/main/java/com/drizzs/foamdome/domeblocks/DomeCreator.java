package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.domeregistry.DomeBlocks;
import com.drizzs.foamdome.util.FoamTags;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class DomeCreator extends Block {

    private final int size;

    public DomeCreator(Properties properties, int size)
    {
        super(properties);
        this.size = size;
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {

        int size = this.size;

        for(int i = -size; i <= size; i++){
            for(int k = -size; k <= size; k++){
                for(int j = -size; j <= size; j++){
                    if((i*i) + (j*j) + (k*k) < (size * size) + size + 1) {
                        BlockPos posAll = pos.add(i, j, k);
                        if (world.getBlockState(posAll).getBlock().isIn(FoamTags.UNDERWATER)){
                            world.setBlockState(posAll, DomeBlocks.foam.getDefaultState());
                        }
                    }

                }
            }
        }
        return true;
    }










}
