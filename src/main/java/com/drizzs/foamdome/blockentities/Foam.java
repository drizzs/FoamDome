package com.drizzs.foamdome.blockentities;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.drizzs.foamdome.util.DomeRegistry.*;


public class Foam extends Block {

    public Foam(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull Random random) {
        Block block = this.asBlock();
        if (block.equals(HARD_GRAVITY_FOAM.get()) || block.equals(GLASS_FOAM.get()) || block.equals(GLASS_SKY_FOAM.get())) {
            hardFoamTransformation(world, pos, Blocks.GLASS);
        } else if (block.equals(BASIC_FOAM.get())) {
            hardFoamTransformation(world, pos, NOT_FOAM.get());
        }else {
            foamDeath(world, pos);
        }
    }

    private void foamDeath(ServerLevel world, BlockPos pos) {
        world.destroyBlock(pos, false);
    }

    private void hardFoamTransformation(ServerLevel world, BlockPos pos, Block state) {
        world.setBlock(pos, state.defaultBlockState(),1);
    }
}
