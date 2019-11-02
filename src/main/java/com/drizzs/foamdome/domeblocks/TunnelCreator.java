package com.drizzs.foamdome.domeblocks;

import com.drizzs.foamdome.util.DomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;


import static com.drizzs.foamdome.util.DomeLib.*;

public class TunnelCreator extends Block {

    public TunnelCreator(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        Direction direction = trace.getFace();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                foamActivation(world, pos, direction);
            }
        }, 4000);
        return true;
    }

    private void foamMethodMagic(World world, BlockPos pos, int x, int y, int z) {
        BlockPos posAll = pos.add(x, y, z);
        BlockState targetState = world.getBlockState(posAll);
        Block targetBlock = targetState.getBlock();
        if (this.getBlock().equals(tunnelcreator) || this.getBlock().equals(domecreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, foam.getDefaultState());
            }
        } else if (this.getBlock().equals(glassdomecreator) || this.getBlock().equals(glasstunnelcreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, glassfoam.getDefaultState());
            }
        } else if (this.getBlock().equals(aciddomecreator) || this.getBlock().equals(acidtunnelcreator)) {
            world.setBlockState(posAll, acidfoam.getDefaultState());
        } else if (this.getBlock().equals(gravitydomecreator) || this.getBlock().equals(airtunnelcreator)) {
            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                world.setBlockState(posAll, gravityfoam.getDefaultState());
        }
    }

    private void foamActivation(World world, BlockPos pos, Direction direction) {
        if (direction == Direction.SOUTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x < 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x < 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x < 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.WEST) {

            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x < 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x < 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x < 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        }
    }


}
