package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import com.drizzs.foamdome.blocks.CreatorTile;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class TunnelCreatorTile extends CreatorTile {

    public TunnelCreatorTile(TileEntityType<?> type) {
        super(type);
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
        int newSize = (size+4);
        if (direction == Direction.SOUTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -newSize; z < 1; ++z) {
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
                    for (int z = 0; z < newSize; ++z) {
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
                    for (int z = -newSize; z < 1; ++z) {
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
                    for (int z = 0; z < newSize; ++z) {
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
            for (int y = 0; y < newSize; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = 0; y > -newSize; --y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        }
    }

    private void insideFoam(Direction direction, int size) {
        int newSize = (size+3);
        if (direction == Direction.SOUTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -newSize; z < 0; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < newSize; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -newSize; z < 0; ++z) {
                        BlockPos targetPos = pos.add(z, y, x);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.WEST) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < newSize; ++z) {
                        BlockPos targetPos = pos.add(z, y, x);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 1; y < newSize; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = -newSize; y < 1; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos targetPos = pos.add(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        }
    }
}
