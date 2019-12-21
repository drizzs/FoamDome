package com.drizzs.foamdome.blocks;

import com.drizzs.foamdome.items.FoamCartridge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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

import java.util.ArrayList;
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistry.BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeRegistry.DISOLVABLE_BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeTags.*;

public class CreatorTile extends TileEntity implements ITickableTileEntity{

    public boolean activated = false;
    public boolean noPos = false;

    public Direction direction = null;

    public List<BlockPos> outsidePos = new ArrayList<BlockPos>();
    public List<BlockPos> insidePos = new ArrayList<BlockPos>();

    public LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public CreatorTile(TileEntityType<?> type) {
        super(type);
    }

    @Override
    public void tick() {
        foamMovieMagic();
    }


    public void foamMovieMagic() {
        if(activated){
            handler.ifPresent(inventory -> {
                ItemStack item = inventory.getStackInSlot(0);
                if (!item.isEmpty()) {
                    int size = getSize(item.getItem());
                    if (!this.noPos) {
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

    public void possibleTargets(Direction direction, int size) {
        for (int x = -size; x < size; ++x) {
            for (int y = -size; y < size; ++y) {
                for (int z = -size; z < size; ++z) {
                    double xp = Math.pow(x, 2);
                    double yp = Math.pow(y, 2);
                    double zp = Math.pow(z, 2);
                    if (xp + yp + zp < (Math.pow(size, 2))) {
                        BlockPos targetPos = pos.add(x, y, z);
                        if (xp + yp + zp < (Math.pow((size - 1), 2))) {
                            addPosIfValid(targetPos, insidePos);
                        } else {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        }
    }

    public Tag<Block> getTag() {
        return UNDERWATER;
    }

    public BlockState getFoam() {
        return BASIC_FOAM.get().getDefaultState();
    }
    public BlockState getFoam2() {
        return DISOLVABLE_BASIC_FOAM.get().getDefaultState();
    }

    public int getSize(Item item) {
        int size = 0;
        if (item instanceof FoamCartridge) {
            size = ((FoamCartridge) item).getDomeSize();
        }
        return size;
    }

    public void addPosIfValid(BlockPos pos, List<BlockPos> list) {
        BlockState state = world.getBlockState(pos);
        boolean isNotCreator = !state.equals(this.getBlockState());
        if(!insidePos.contains(pos) && isNotCreator){
            if(this.getTag().equals(ACID)){
                list.add(pos);
            }
            if(this.getTag().equals(GRAVITYDOME)){
                if(state.isIn(getTag()) || state.isAir(world, pos)) {
                    list.add(pos);
                }
            }
            if(state.isIn(getTag())) {
                list.add(pos);
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

        };
    }

    public void extractInsertItemMethod(PlayerEntity player, Hand hand) {
        handler.ifPresent(inventory -> {
            ItemStack held = player.getHeldItem(hand);

            if (!held.isEmpty()) {
                if (held.getItem().isIn(CARTRIDGE)) {
                    if (inventory.getStackInSlot(0).isEmpty()) {
                        ItemStack heldCopy = held.copy();
                        heldCopy.setCount(1);
                        inventory.insertItem(0, heldCopy, false);
                        held.shrink(1);
                    }
                }
            } else {
                ItemStack item = inventory.extractItem(0, 1, false);
                player.setHeldItem(hand, item);
            }
        });
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        NonNullList<ItemStack> stack = NonNullList.withSize(1, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, stack);
        ItemStack item = stack.get(0);
        handler.ifPresent(inventory -> {
            inventory.insertItem(0,item,false);
        });
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        NonNullList<ItemStack> stack = NonNullList.withSize(1, ItemStack.EMPTY);
        handler.ifPresent(inventory -> {
            stack.set(0, inventory.getStackInSlot(0));
        });
        if(!stack.isEmpty()) {
            ItemStackHelper.saveAllItems(compound, stack);
        }
        return compound;
    }
}
