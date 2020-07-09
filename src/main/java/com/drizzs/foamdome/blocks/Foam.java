package com.drizzs.foamdome.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import static com.drizzs.foamdome.util.DomeRegistry.*;


public class Foam extends Block {

    public Foam(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        Block block = this.getBlock();
        if (block.equals(HARD_GRAVITY_FOAM.get()) || block.equals(GLASS_FOAM.get())) {
            hardFoamTransformation(world, pos, Blocks.GLASS);

        } else if (block.equals(BASIC_FOAM.get())) {
            hardFoamTransformation(world, pos, NOT_FOAM.get());
        }else {
            foamDeath(world, pos);
        }
    }

    private void foamDeath(World world, BlockPos pos) {
        world.destroyBlock(pos, false);
    }

    private void hardFoamTransformation(World world, BlockPos pos, Block state) {
        world.setBlockState(pos, state.getDefaultState());
    }
}
