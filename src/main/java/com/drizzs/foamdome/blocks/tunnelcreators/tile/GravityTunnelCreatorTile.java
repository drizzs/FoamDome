package com.drizzs.foamdome.blocks.tunnelcreators.tile;

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
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;
import static com.drizzs.foamdome.util.DomeTags.CARTRIDGE;
import static com.drizzs.foamdome.util.DomeTags.GRAVITYDOME;

public class GravityTunnelCreatorTile extends TileEntity implements ITickableTileEntity {

    protected NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    private List<BlockPos> outsidePos;
    private List<BlockPos> insidePos;
    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public GravityTunnelCreatorTile() {
        super(GRAVITY_TUNNEL_TILE.get());
    }

    @Override
    public void tick() {
        foamMovieMagic();
    }

    public void foamMovieMagic() {
        if (this.world.isBlockPowered(this.pos)) {
            if (!this.inventory.isEmpty()) {
                Item item = this.inventory.get(0).getItem();
                int size = getSize(item);
                if (this.insidePos.isEmpty() && this.outsidePos.isEmpty()) {
                    disolvingGravityFoamCreation(size);
                    hardGravityFoamCreation(size);
                }
                BlockPos pos = this.insidePos.get(0);
                BlockPos pos2 = this.outsidePos.get(0);
                this.world.setBlockState(pos, this.getFoam2());
                this.world.setBlockState(pos2, this.getFoam());
                this.insidePos.remove(0);
                this.outsidePos.remove(0);
                if (this.insidePos.isEmpty() && this.outsidePos.isEmpty() ) {
                    item.getDefaultInstance().shrink(1);
                }
            }
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem().isIn(CARTRIDGE);
            }
        };
    }

    public Tag<Block> getTag() {
        return GRAVITYDOME;
    }

    public int getSize(Item item) {
        int size = 0;
        if (item instanceof FoamCartridge) {
            size = ((FoamCartridge) item).getDomeSize();
        }
        return size;
    }

    private void disolvingGravityFoamCreation(int size) {
        size -= 1;
        if (this.world.isSidePowered(this.pos, Direction.SOUTH)) {
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
        }else if (this.world.isSidePowered(this.pos, Direction.NORTH)) {
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
        }else if (this.world.isSidePowered(this.pos, Direction.EAST)) {
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
        } else if (this.world.isSidePowered(this.pos, Direction.WEST)) {
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
        }else if (this.world.isSidePowered(this.pos, Direction.DOWN)) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(0, -y, 1));
                outsidePos.add(this.pos.add(1, -y, 0));
                outsidePos.add(this.pos.add(-1, -y, 0));
                outsidePos.add(this.pos.add(0, -y, -1));
                outsidePos.add(this.pos.add(0, -y, 0));
            }
        }else if (this.world.isSidePowered(this.pos, Direction.UP)) {
            for (int y = 0; y < size; ++y) {
                outsidePos.add(this.pos.add(0, y, 1));
                outsidePos.add(this.pos.add(1, y, 0));
                outsidePos.add(this.pos.add(-1, y, 0));
                outsidePos.add(this.pos.add(0, y, -1));
                outsidePos.add(this.pos.add(0, y, 0));
            }
        }
    }

    private void hardGravityFoamCreation(int size) {
        if (this.world.isSidePowered(this.pos, Direction.SOUTH)) {
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
        } else if (this.world.isSidePowered(this.pos, Direction.NORTH)) {
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
        } else if (this.world.isSidePowered(this.pos, Direction.EAST)) {
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
        } else if (this.world.isSidePowered(this.pos, Direction.WEST)) {
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
        } else if (this.world.isSidePowered(this.pos, Direction.DOWN)) {
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

        } else if (this.world.isSidePowered(this.pos, Direction.UP)) {
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



    public BlockState getFoam() {
        return HARD_GRAVITY_FOAM.get().getDefaultState();
    }
    public BlockState getFoam2() {
        return DISOLVABLE_GRAVITY_FOAM.get().getDefaultState();
    }

    public boolean onActivated(PlayerEntity playerIn, Hand hand, Direction facing, double hitX, double hitY, double hitZ) {
        if (playerIn instanceof ServerPlayerEntity) {
            Item item = playerIn.getHeldItem(hand).getItem();
            if (item.isIn(CARTRIDGE)) {
                this.inventory.add(0, item.getDefaultInstance());
                item.getDefaultInstance().shrink(1);
            } else if (playerIn.getHeldItem(hand).isEmpty()) {
                ItemStack stack = this.inventory.get(0);
                if (!stack.isEmpty()) {
                    this.inventory.remove(0);
                    playerIn.inventory.addItemStackToInventory(stack);
                }
            }
        }
        return true;
    }

}
