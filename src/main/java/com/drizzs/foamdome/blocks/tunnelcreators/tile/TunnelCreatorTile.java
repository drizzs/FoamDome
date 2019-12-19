package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.blocks.CreatorTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class TunnelCreatorTile extends CreatorTile {

    public TunnelCreatorTile(TileEntityType<?> type) {
        super(type);
    }

    @Override
    public void possibleTargets(Direction direction, int size) {
        size += 4;
        if (direction == Direction.SOUTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z >= -size; --z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }

                        }
                        BlockPos pos = this.pos.add(x, y, z);
                        addPosIfValid(pos, targetPos);
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z <= size; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }

                        }
                        BlockPos pos = this.pos.add(x, y, z);
                        addPosIfValid(pos, targetPos);
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z >= -size; --z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }

                        }
                        BlockPos pos = this.pos.add(z, y, x);
                        addPosIfValid(pos, targetPos);
                    }
                }
            }
        } else if (direction == Direction.WEST) {

            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z <= size; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }

                        }
                        BlockPos pos = this.pos.add(z, y, x);
                        addPosIfValid(pos, targetPos);
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 0; y <= size; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos pos = this.pos.add(x, y, z);
                        addPosIfValid(pos, targetPos);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = 0; y >= -size; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos pos = this.pos.add(x, y, z);
                        addPosIfValid(pos, targetPos);
                    }
                }
            }
        }
    }
}
