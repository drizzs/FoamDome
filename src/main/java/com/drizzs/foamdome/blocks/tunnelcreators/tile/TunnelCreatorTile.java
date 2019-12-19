package com.drizzs.foamdome.blocks.tunnelcreators.tile;

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
        int sizex1 = 0;
        int sizex2 = 0;
        int sizey1 = 0;
        int sizey2 = 0;
        int sizez1 = 0;
        int sizez2 = 0;
        size += 1;
        if(direction == Direction.SOUTH){
            sizex1 = -2;
            sizex2 = 2;
            sizey1 = -1;
            sizey2 = 4;
            sizez2 = -size;
        } else if(direction == Direction.NORTH){
            sizex1 = -2;
            sizex2 = 2;
            sizey1 = -1;
            sizey2 = 4;
            sizez2 = size;
        }else if(direction == Direction.EAST){
            sizex2 = -size;
            sizey1 = -1;
            sizey2 = 4;
            sizez1 = -2;
            sizez2 = 2;
        }else if(direction == Direction.WEST){
            sizex2 = size;
            sizey1 = -1;
            sizey2 = 4;
            sizez1 = -2;
            sizez2 = 2;
        }else if(direction == Direction.DOWN){
            sizex1 = -2;
            sizex2 = 2;
            sizey2 = size;
            sizez1 = -2;
            sizez2 = 2;
        }else if(direction == Direction.UP){
            sizex1 = -2;
            sizex2 = 2;
            sizey2 = -size;
            sizez1 = -2;
            sizez2 = 2;
        }
        for (int y = sizey1; y <= sizey2; ++y) {
            for (int x = sizex1; x <= sizex2; ++x) {
                for (int z = sizez1; z <= sizez2; ++z) {
                    if(direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
                        if (y == 3) {
                            if (x == -2 || x == 2 || x == -1 || x == 1) {
                                break;
                            }
                        } else if (y == 2) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                    }
                    if(direction.equals(Direction.EAST) || direction.equals(Direction.WEST)) {
                        if (y == 3) {
                            if (z == -2 || z == 2 || z == -1 || z == 1) {
                                break;
                            }
                        } else if (y == 2) {
                            if (z == -2 || z == 2) {
                                break;
                            }
                        }
                    }
                    BlockPos pos = this.pos.add(x, y, z);
                    addPosIfValid(pos, targetPos);
                }
            }
        }
    }
}
