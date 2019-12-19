package com.drizzs.foamdome.blocks.tunnelcreators.tile;

import com.drizzs.foamdome.blocks.CreatorTile;
import com.drizzs.foamdome.items.FoamCartridge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;
import static com.drizzs.foamdome.util.DomeTags.CARTRIDGE;
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

    private void disolvingGravityFoamCreation(int size) {
        size -= 1;
        if (direction == Direction.SOUTH) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(1, 1, -y));
                outsidePos.add(this.pos.add(-1, 1, -y));
                outsidePos.add(this.pos.add(2, 1, -y));
                outsidePos.add(this.pos.add(-2, 1, -y));
                outsidePos.add(this.pos.add(0, 0, -y));
                outsidePos.add(this.pos.add(-1, 0, -y));
                outsidePos.add(this.pos.add(1, 0, -y));
                outsidePos.add(this.pos.add(-2, 0, -y));
                outsidePos.add(this.pos.add(2, 0, -y));
                outsidePos.add(this.pos.add(0, 2, -y));
            }
        }else if (direction == Direction.NORTH) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(1, 1, y));
                outsidePos.add(this.pos.add(-1, 1, y));
                outsidePos.add(this.pos.add(2, 1, y));
                outsidePos.add(this.pos.add(-2, 1, y));
                outsidePos.add(this.pos.add(0, 0, y));
                outsidePos.add(this.pos.add(-1, 0, y));
                outsidePos.add(this.pos.add(1, 0, y));
                outsidePos.add(this.pos.add(-2, 0, y));
                outsidePos.add(this.pos.add(2, 0, y));
                outsidePos.add(this.pos.add(0, 2, y));
            }
        }else if (direction == Direction.EAST) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(-y, 1, 1));
                outsidePos.add(this.pos.add(-y, 1, -1));
                outsidePos.add(this.pos.add(-y, 1, 2));
                outsidePos.add(this.pos.add(-y, 1, -2));
                outsidePos.add(this.pos.add(-y, 0, 0));
                outsidePos.add(this.pos.add(-y, 0, -1));
                outsidePos.add(this.pos.add(-y, 0, 1));
                outsidePos.add(this.pos.add(-y, 0, 2));
                outsidePos.add(this.pos.add(-y, 0, -2));
                outsidePos.add(this.pos.add(-y, 2, 0));
            }
        } else if (direction == Direction.WEST) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(y, 1, 1));
                outsidePos.add(this.pos.add(y, 1, -1));
                outsidePos.add(this.pos.add(y, 1, 2));
                outsidePos.add(this.pos.add(y, 1, -2));
                outsidePos.add(this.pos.add(y, 0, 0));
                outsidePos.add(this.pos.add(y, 0, -1));
                outsidePos.add(this.pos.add(y, 0, 1));
                outsidePos.add(this.pos.add(y, 0, 2));
                outsidePos.add(this.pos.add(y, 0, -2));
                outsidePos.add(this.pos.add(y, 2, 0));
            }
        }else if (direction == Direction.DOWN) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(0, -y, 1));
                outsidePos.add(this.pos.add(1, -y, 0));
                outsidePos.add(this.pos.add(-1, -y, 0));
                outsidePos.add(this.pos.add(0, -y, -1));
                outsidePos.add(this.pos.add(0, -y, 0));
            }
        }else if (direction == Direction.UP) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(0, y, 1));
                outsidePos.add(this.pos.add(1, y, 0));
                outsidePos.add(this.pos.add(-1, y, 0));
                outsidePos.add(this.pos.add(0, y, -1));
                outsidePos.add(this.pos.add(0, y, 0));
            }
        }
    }

    private void hardGravityFoamCreation(Direction direction,int size) {
        if (direction == Direction.SOUTH) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(0, -1, -y));
                outsidePos.add(this.pos.add(1, -1, -y));
                outsidePos.add(this.pos.add(-1, -1, -y));
                outsidePos.add(this.pos.add(-2, -1, -y));
                outsidePos.add(this.pos.add(2, -1, -y));
                outsidePos.add(this.pos.add(0, 4, -y));
                outsidePos.add(this.pos.add(-1, 3, -y));
                outsidePos.add(this.pos.add(1, 3, -y));
                outsidePos.add(this.pos.add(2, -2, -y));
                outsidePos.add(this.pos.add(-2, 2, -y));
                outsidePos.add(this.pos.add(-2, -1, -y));
                outsidePos.add(this.pos.add(2, 1, -y));
            }
            outsidePos.add(this.pos.add(1, 1, -size));
            outsidePos.add(this.pos.add(-1, 1, -size));
            outsidePos.add(this.pos.add(2, 1, -size));
            outsidePos.add(this.pos.add(-2, 1, -size));
            outsidePos.add(this.pos.add(0, 0, -size));
            outsidePos.add(this.pos.add(-1, 0, -size));
            outsidePos.add(this.pos.add(1, 0, -size));
            outsidePos.add(this.pos.add(-2, 0, -size));
            outsidePos.add(this.pos.add(2, 0, -size));
            outsidePos.add(this.pos.add(0, 2, -size));
        } else if (direction == Direction.NORTH) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(0, -1, y));
                outsidePos.add(this.pos.add(1, -1, y));
                outsidePos.add(this.pos.add(-1, -1, y));
                outsidePos.add(this.pos.add(-2, -1, y));
                outsidePos.add(this.pos.add(2, -1, y));
                outsidePos.add(this.pos.add(0, 4, y));
                outsidePos.add(this.pos.add(-1, 3, y));
                outsidePos.add(this.pos.add(1, 3, y));
                outsidePos.add(this.pos.add(2, -2, y));
                outsidePos.add(this.pos.add(-2, 2, y));
                outsidePos.add(this.pos.add(-2, -1, y));
                outsidePos.add(this.pos.add(2, 1, y));
            }
            outsidePos.add(this.pos.add(1, 1, size));
            outsidePos.add(this.pos.add(-1, 1, size));
            outsidePos.add(this.pos.add(2, 1, size));
            outsidePos.add(this.pos.add(-2, 1, size));
            outsidePos.add(this.pos.add(0, 0, size));
            outsidePos.add(this.pos.add(-1, 0, size));
            outsidePos.add(this.pos.add(1, 0, size));
            outsidePos.add(this.pos.add(-2, 0, size));
            outsidePos.add(this.pos.add(2, 0, size));
            outsidePos.add(this.pos.add(0, 2, size));
        } else if (direction == Direction.EAST) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(-y, -1, 0));
                outsidePos.add(this.pos.add(-y, -1, 1));
                outsidePos.add(this.pos.add(-y, -1, -1));
                outsidePos.add(this.pos.add(-y, -1, -2));
                outsidePos.add(this.pos.add(-y, -1, 2));
                outsidePos.add(this.pos.add(-y, 4, 0));
                outsidePos.add(this.pos.add(-y, 3, -1));
                outsidePos.add(this.pos.add(-y, 3, 1));
                outsidePos.add(this.pos.add(-y, -2, 2));
                outsidePos.add(this.pos.add(-y, 2, -2));
                outsidePos.add(this.pos.add(-y, -1, -2));
                outsidePos.add(this.pos.add(-y, 1, 2));
            }
            outsidePos.add(this.pos.add(-size, 1, 1));
            outsidePos.add(this.pos.add(-size, 1, -1));
            outsidePos.add(this.pos.add(-size, 1, 2));
            outsidePos.add(this.pos.add(-size, 1, -2));
            outsidePos.add(this.pos.add(-size, 0, 0));
            outsidePos.add(this.pos.add(-size, 0, -1));
            outsidePos.add(this.pos.add(-size, 0, 1));
            outsidePos.add(this.pos.add(-size, 0, 2));
            outsidePos.add(this.pos.add(-size, 0, -2));
            outsidePos.add(this.pos.add(-size, 2, 0));
        } else if (direction == Direction.WEST) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(y, -1, 0));
                outsidePos.add(this.pos.add(y, -1, 1));
                outsidePos.add(this.pos.add(y, -1, -1));
                outsidePos.add(this.pos.add(y, -1, -2));
                outsidePos.add(this.pos.add(y, -1, 2));
                outsidePos.add(this.pos.add(y, 4, 0));
                outsidePos.add(this.pos.add(y, 3, -1));
                outsidePos.add(this.pos.add(y, 3, 1));
                outsidePos.add(this.pos.add(y, -2, 2));
                outsidePos.add(this.pos.add(y, 2, -2));
                outsidePos.add(this.pos.add(y, -1, -2));
                outsidePos.add(this.pos.add(y, 1, 2));
            }
            outsidePos.add(this.pos.add(size, 1, 1));
            outsidePos.add(this.pos.add(size, 1, -1));
            outsidePos.add(this.pos.add(size, 1, 2));
            outsidePos.add(this.pos.add(size, 1, -2));
            outsidePos.add(this.pos.add(size, 0, 0));
            outsidePos.add(this.pos.add(size, 0, -1));
            outsidePos.add(this.pos.add(size, 0, 1));
            outsidePos.add(this.pos.add(size, 0, 2));
            outsidePos.add(this.pos.add(size, 0, -2));
            outsidePos.add(this.pos.add(size, 2, 0));
        } else if (direction == Direction.DOWN) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(2, -y, 0));
                outsidePos.add(this.pos.add(0, -y, 2));
                outsidePos.add(this.pos.add(-2, -y, 0));
                outsidePos.add(this.pos.add(0, -y, -2));
                outsidePos.add(this.pos.add(1, -y, 1));
                outsidePos.add(this.pos.add(-1, -y, 1));
                outsidePos.add(this.pos.add(-1, -y, -1));
                outsidePos.add(this.pos.add(1, -y, 11));
            }
            outsidePos.add(this.pos.add(0, -size, 1));
            outsidePos.add(this.pos.add(1, -size, 0));
            outsidePos.add(this.pos.add(-1, -size, 0));
            outsidePos.add(this.pos.add(0, -size, -1));
            outsidePos.add(this.pos.add(0, -size, 0));

        } else if (direction == Direction.UP) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(2, y, 0));
                outsidePos.add(this.pos.add(0, y, 2));
                outsidePos.add(this.pos.add(-2, y, 0));
                outsidePos.add(this.pos.add(0, y, -2));
                outsidePos.add(this.pos.add(1, y, 1));
                outsidePos.add(this.pos.add(-1, y, 1));
                outsidePos.add(this.pos.add(-1, y, -1));
                outsidePos.add(this.pos.add(1, y, 1));

            }
            outsidePos.add(this.pos.add(0, size, 1));
            outsidePos.add(this.pos.add(1, size, 0));
            outsidePos.add(this.pos.add(-1, size, 0));
            outsidePos.add(this.pos.add(0, size, -1));
            outsidePos.add(this.pos.add(0, size, 0));
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
