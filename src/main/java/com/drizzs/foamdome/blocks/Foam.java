package com.drizzs.foamdome.blocks;


import com.drizzs.foamdome.util.DomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;


public class Foam extends Block {

    public Foam(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (this.getBlock().equals(BASIC_FOAM.get()) || this.getBlock().equals(GLASS_FOAM.get())) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    waterFoamDissipation(world, pos, random);
                }
            }, 80);
        } else if (this.getBlock().equals(ACID_FOAM.get())) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    acidFoamDissipation(world, pos, random);
                }
            }, 80);
        } else if (this.getBlock().equals(GLASS_FOAM.get())) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    waterFoamDissipation(world, pos, random);
                }
            }, 80);
        } else if (this.getBlock().equals(HARD_GRAVITY_FOAM.get())) {
            hardGravityFoamDissipation(world, pos, random);
        } else if (this.getBlock().equals(DISOLVABLE_GRAVITY_FOAM.get())) {
            gravityFoamDissipation(world, pos, random);
        }
    }

    private void waterFoamDissipation(World world, BlockPos pos, Random random) {
        BlockPos up = pos.up();
        BlockPos down = pos.down();
        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos east = pos.east();
        BlockPos west = pos.west();
        int i = random.nextInt(1);
        if (i == 0) {
            if (world.getBlockState(east).getBlock().isIn(DomeTags.UNDERWATER) || world.getBlockState(west).getBlock().isIn(DomeTags.UNDERWATER)
                    || world.getBlockState(south).getBlock().isIn(DomeTags.UNDERWATER) || world.getBlockState(north).getBlock().isIn(DomeTags.UNDERWATER)
                    || world.getBlockState(up).getBlock().isIn(DomeTags.UNDERWATER) || world.getBlockState(down).getBlock().isIn(DomeTags.UNDERWATER)) {
                if (this.getBlock().equals(BASIC_FOAM.get())) {
                    world.setBlockState(pos, NOT_FOAM.get().getDefaultState());
                } else if (this.getBlock().equals(GLASS_FOAM.get())) {
                    world.setBlockState(pos, Blocks.GLASS.getDefaultState());
                }
            } else {
                world.destroyBlock(pos, true);
            }
        }
    }

    private void acidFoamDissipation(World world, BlockPos pos, Random random) {
        int i = random.nextInt(1);
        if (i == 0) {
            world.destroyBlock(pos, false);
        }
    }

    private void hardGravityFoamDissipation(World world, BlockPos pos, Random random) {
        int i = random.nextInt(1);
        if (i == 0) {
            world.setBlockState(pos, Blocks.GLASS.getDefaultState());
        }
    }

    private void gravityFoamDissipation(World world, BlockPos pos, Random random) {
        int i = random.nextInt(1);
        if (i == 0) {
            world.destroyBlock(pos, false);
        }
    }
}
