package com.drizzs.foamdome.tunnelcreators.tile;

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
import net.minecraft.tileentity.TileEntityType;
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

import static com.drizzs.foamdome.util.DomeRegistryNew.BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeTags.CARTRIDGE;
import static com.drizzs.foamdome.util.DomeTags.UNDERWATER;

public class TunnelCreatorTile extends TileEntity implements ITickableTileEntity {

    protected NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    private List<BlockPos> targetPos;

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public TunnelCreatorTile(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public void tick() {
        foamMovieMagic();
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

    public void foamMovieMagic() {
        if (this.world.isBlockPowered(this.pos)) {
            if (!this.inventory.isEmpty()) {
                Item item = this.inventory.get(0).getItem();
                int size = getSize(item);
                if (this.targetPos.isEmpty()) {
                    tunnelActivator(size);
                }
                BlockPos pos = this.targetPos.get(0);
                this.world.setBlockState(pos, this.getFoam());
                this.targetPos.remove(0);
                if (this.targetPos.isEmpty()) {
                    item.getDefaultInstance().shrink(1);
                }
            }
        }
    }

    public void tunnelActivator(int size){
        int sizex1 = 0;
        int sizex2 = 0;
        int sizey1 = 0;
        int sizey2 = 0;
        int sizez1 = 0;
        int sizez2 = 0;
        size += 1;
        Direction direction = null;
        if(world.isSidePowered(pos, Direction.SOUTH)){
            sizex1 = -2;
            sizex2 = 2;
            sizey1 = -1;
            sizey2 = 4;
            sizez2 = -size;
            direction = Direction.SOUTH;
        } else if(world.isSidePowered(pos, Direction.NORTH)){
            sizex1 = -2;
            sizex2 = 2;
            sizey1 = -1;
            sizey2 = 4;
            sizez2 = size;
            direction = Direction.NORTH;
        }else if(world.isSidePowered(pos, Direction.EAST)){
            sizex2 = -size;
            sizey1 = -1;
            sizey2 = 4;
            sizez1 = -2;
            sizez2 = 2;
            direction = Direction.EAST;
        }else if(world.isSidePowered(pos, Direction.WEST)){
            sizex2 = size;
            sizey1 = -1;
            sizey2 = 4;
            sizez1 = -2;
            sizez2 = 2;
            direction = Direction.WEST;
        }else if(world.isSidePowered(pos, Direction.DOWN)){
            sizex1 = -2;
            sizex2 = 2;
            sizey2 = size;
            sizez1 = -2;
            sizez2 = 2;
            direction = Direction.DOWN;
        }else if(world.isSidePowered(pos, Direction.UP)){
            sizex1 = -2;
            sizex2 = 2;
            sizey2 = -size;
            sizez1 = -2;
            sizez2 = 2;
            direction = Direction.UP;
        }
        for (int y = sizey1; y < sizey2; ++y) {
            for (int x = sizex1; x <= sizex2; ++x) {
                for (int z = sizez1; z < sizez2; ++z) {
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
                    addPosIfValid(pos);
                }
            }
        }
    }

    public void addPosIfValid(BlockPos pos) {
        if (getTag() != null) {
            if (this.world.getBlockState(pos).isIn(getTag())) {
                this.targetPos.add(pos);
            }
        } else {
            this.targetPos.add(pos);
        }
    }

    public BlockState getFoam() {
        return BASIC_FOAM.get().getDefaultState();
    }

    public Tag<Block> getTag() {
        return UNDERWATER;
    }

    public int getSize(Item item) {
        int size = 0;
        if (item instanceof FoamCartridge) {
            size = ((FoamCartridge) item).getDomeSize();
        }
        return size;
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
