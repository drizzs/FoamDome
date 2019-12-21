package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.blocks.CreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;
import static com.drizzs.foamdome.util.DomeTags.GRAVITYDOME;

public class GravityTunnelCreatorTile extends CreatorTile {

    private List<BlockPos> outsidePos = new ArrayList<BlockPos>();
    private List<BlockPos> insidePos = new ArrayList<BlockPos>();

    public GravityTunnelCreatorTile() {
        super(GRAVITY_TUNNEL_TILE.get());
    }

    @Override
    public void tick() {
        foamMovieMagic();
    }

    @Override
    public Tag<Block> getTag() {
        return GRAVITYDOME;
    }

    @Override
    public void foamMovieMagic() {
        if(activated){
            handler.ifPresent(inventory -> {
                ItemStack item = inventory.getStackInSlot(0);
                if (!item.isEmpty()) {
                    int size = getSize(item.getItem());
                    if (!this.noPos) {
                        insideFoam(direction, size);
                        possibleTargets(direction, size);
                        int checker1 = insidePos.size();
                        int checker2 = outsidePos.size();
                        FoamDome.LOGGER.info("attained this many of each pos type" + checker1 + " " +checker2);
                        noPos = true;
                    }
                    if (!insidePos.isEmpty()) {
                        BlockPos pos = insidePos.get(0);
                        world.setBlockState(pos, getFoam2());
                        insidePos.remove(0);
                    }
                    if (!outsidePos.isEmpty()) {
                        BlockPos pos = outsidePos.get(0);
                        world.setBlockState(pos, getFoam());
                        outsidePos.remove(0);
                    }
                    if(outsidePos.isEmpty() && insidePos.isEmpty()){
                        activated = false;
                        item.shrink(1);
                        noPos = false;
                    }
                }
                else{activated = false;}
            });
        }
    }

    @Override
    public void possibleTargets(Direction direction, int size) {
        size += 4;
        if (direction == Direction.SOUTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z > -size; --z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z < size; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z > -size; --z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = pos.add(z, y, x);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        } else if (direction == Direction.WEST) {

            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z < size; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = pos.add(z, y, x);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 0; y < size; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = 0; y > -size; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        }
    }

    @Override
    public void addPosIfValid(BlockPos pos, List<BlockPos> list) {
        BlockState state = world.getBlockState(pos);
        boolean isNotCreator = !state.equals(this.getBlockState());
        if(!insidePos.contains(pos) && isNotCreator){
            if(state.isIn(getTag()) || state.isAir(world, pos)) {
                list.add(pos);
            }
        }
    }

    private void insideFoam(Direction direction, int size) {
        size += 3;
        if (direction == Direction.SOUTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z > -size; --z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < size; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = -0; y < 3; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 1; z > -size; --z) {
                        BlockPos targetPos = pos.add(z, y, x);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.WEST) {

            for (int y = -0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < size; ++z) {
                        BlockPos targetPos = pos.add(z, y, x);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 1; y < size; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = 1; y > -size; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        }
    }

    @Override
    public BlockState getFoam() {
        return HARD_GRAVITY_FOAM.get().getDefaultState();
    }

    public BlockState getFoam2() {
        return DISOLVABLE_GRAVITY_FOAM.get().getDefaultState();
    }
}
