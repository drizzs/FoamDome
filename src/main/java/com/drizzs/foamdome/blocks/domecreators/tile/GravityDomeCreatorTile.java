package com.drizzs.foamdome.domecreators.tile;

import com.drizzs.foamdome.items.FoamCartridge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.drizzs.foamdome.util.DomeRegistryNew.BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeRegistryNew.GRAVITY_DOME_TILE;
import static com.drizzs.foamdome.util.DomeTags.*;
import static com.drizzs.foamdome.util.DomeTags.CARTRIDGE;
import static net.minecraft.block.FallingBlock.canFallThrough;

public class GravityDomeCreatorTile extends TileEntity implements ITickableTileEntity {

    public NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public List<BlockPos> outsidePos;
    public List<BlockPos> insidePos;

    public boolean build = false;

    public LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);


    public GravityDomeCreatorTile() {
        super(GRAVITY_DOME_TILE.get());
    }


    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public IItemHandler createHandler() {
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

    @Override
    public void tick() {
        foamMovieMagic();
    }

    public void foamMovieMagic() {
        if (this.world.isBlockPowered(this.pos)) {
            if (!this.inventory.isEmpty()) {
                checkFallable(this.world,this.pos);
                Item item = this.inventory.get(0).getItem();
                int size = getSize(item);
                if (this.insidePos.isEmpty() && this.outsidePos.isEmpty() && build) {
                    possibleTargets(size);
                }
                BlockPos pos = this.insidePos.get(0);
                BlockPos pos2 = this.outsidePos.get(0);
                this.world.setBlockState(pos, this.getFoam());
                this.world.setBlockState(pos2, this.getFoam());
                this.insidePos.remove(0);
                this.outsidePos.remove(0);
                if (this.insidePos.isEmpty() && this.outsidePos.isEmpty() ) {
                    item.getDefaultInstance().shrink(1);
                    build = false;
                }
            }
        }
    }

    public void addPosIfValid(BlockPos pos, List<BlockPos> list) {
            if (getTag() != null) {
                if (this.world.getBlockState(pos).isIn(getTag())) {
                    list.add(pos);
                }
            } else {
                list.add(pos);
            }
    }

    public Tag<Block> getTag() {
        return UNDERWATER;
    }

    public BlockState getFoam() {
        return BASIC_FOAM.get().getDefaultState();
    }

    public void possibleTargets(int size) {
        for (int x = -size; x < size; ++x) {
            for (int y = -size; y < size; ++y) {
                for (int z = -size; z < size; ++z) {
                    int w = (x * x) + (y * y) + (z * z);
                    if (w < (size * size) + 1) {
                        BlockPos pos = this.pos.add(x, y, z);
                        addPosIfValid(pos, this.outsidePos);
                    }
                    else if (w < (size * size) + size - 1) {
                        BlockPos pos = this.pos.add(x, y, z);
                        addPosIfValid(pos, this.insidePos);
                    }
                }
            }
        }
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

    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                worldIn.addEntity(fallingblockentity);
                this.onStartFalling(fallingblockentity);
            }
        }
    }

    protected void onStartFalling(FallingBlockEntity fallingEntity) {
        int random = fallingEntity.world.rand.nextInt(5);
        int timer = 1000;
        if (random == 0) {
            timer = 2000;
        } else if (random == 1) {
            timer = 1500;
        } else if (random == 2) {
            timer = 1300;
        } else if (random == 3) {
            timer = 2200;
        } else if (random == 4) {
            timer = 1800;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                build = true;
            }
        }, timer);
    }

}
