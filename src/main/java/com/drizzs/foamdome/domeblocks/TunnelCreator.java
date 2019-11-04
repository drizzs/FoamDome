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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import static com.drizzs.foamdome.util.DomeLib.*;

public class TunnelCreator extends Block {

    private Direction direction;

    public TunnelCreator(Properties properties) {
        super(properties);
    }


    @Override
    @Deprecated
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        super.tick(state, world, pos, rand);
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        Direction direction = trace.getFace();
        this.direction = direction;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gravityFoamCreation(world,pos,direction);
                foamActivation(world, pos, direction);
            }
        }, 4000);
        return true;
    }

    private void foamMethodMagic(World world, BlockPos pos, int x, int y, int z) {
        BlockPos posAll = pos.add(x, y, z);
        BlockState targetState = world.getBlockState(posAll);
        Block targetBlock = targetState.getBlock();
        if (this.getBlock().equals(tunnelcreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, foam.getDefaultState());
            }
        } else if (this.getBlock().equals(glasstunnelcreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, glassfoam.getDefaultState());
            }
        } else if (this.getBlock().equals(acidtunnelcreator)) {
            world.setBlockState(posAll, acidfoam.getDefaultState());
        } else if (this.getBlock().equals(airtunnelcreator)) {
            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                world.setBlockState(posAll, hardgravityfoam.getDefaultState());
        }
    }

    private void foamActivation(World world, BlockPos pos, Direction direction) {
        if (direction == Direction.SOUTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        if (y == 3) {
                            if (x == -2 || x == 2 || x == -1 || x == 1) {
                                break;
                            }

                        } else if (y == 2) {
                            if (x == -2 || x == 2) {
                                break;
                            }

                        }
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z < 9; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2 || x == -1 || x == 1) {
                                break;
                            }

                        } else if (y == 2) {
                            if (x == -2 || x == 2) {
                                break;
                            }

                        }
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z > -9; --z) {
                        if (y == 3) {
                            if (x == -2 || x == 2 || x == -1 || x == 1) {
                                break;
                            }

                        } else if (y == 2) {
                            if (x == -2 || x == 2) {
                                break;
                            }

                        }
                        foamMethodMagic(world, pos, z, y, x);
                    }
                }
            }
        } else if (direction == Direction.WEST) {

            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z < 9; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2 || x == -1 || x == 1) {
                                break;
                            }

                        } else if (y == 2) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        foamMethodMagic(world, pos, z, y, x);
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 0; y < 9; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = 0; y > -9; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        }
    }

    private void gravityFoamCreation(World world, BlockPos pos, Direction direction) {
        if (direction == Direction.SOUTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z > -8; --z) {
                        BlockPos posAll = pos.add(x, y, z);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (this.getBlock().equals(airtunnelcreator)) {
                            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                                world.setBlockState(posAll, gravityfoam.getDefaultState());
                        }
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < 8; ++z) {
                        if(y == 2){
                            if(x==-1 || x == 1){
                                break;
                            }
                        }
                        BlockPos posAll = pos.add(x, y, z);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (this.getBlock().equals(airtunnelcreator)) {
                            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                                world.setBlockState(posAll, gravityfoam.getDefaultState());
                        }
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = -0; y < 3; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 1; z > -8; --z) {
                        if(y == 2){
                            if(x==-1 || x == 1){
                                break;
                            }
                        }
                        BlockPos posAll = pos.add(z, y, x);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (this.getBlock().equals(airtunnelcreator)) {
                            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                                world.setBlockState(posAll, gravityfoam.getDefaultState());
                        }
                    }
                }
            }
        } else if (direction == Direction.WEST) {

            for (int y = -0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < 8; ++z) {
                        if(y == 2){
                            if(x==-1 || x == 1){
                                break;
                            }
                        }
                        BlockPos posAll = pos.add(z, y, x);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (this.getBlock().equals(airtunnelcreator)) {
                            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                                world.setBlockState(posAll, gravityfoam.getDefaultState());
                        }
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 1; y < 8; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos posAll = pos.add(x, y, z);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (this.getBlock().equals(airtunnelcreator)) {
                            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                                world.setBlockState(posAll, gravityfoam.getDefaultState());
                        }
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = 1; y > -8; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos posAll = pos.add(x, y, z);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (this.getBlock().equals(airtunnelcreator)) {
                            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                                world.setBlockState(posAll, gravityfoam.getDefaultState());
                        }
                    }
                }
            }
        }
    }


}
