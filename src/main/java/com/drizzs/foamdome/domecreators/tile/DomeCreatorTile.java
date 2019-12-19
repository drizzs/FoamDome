package com.drizzs.foamdome.domecreators.tile;

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

public class DomeCreatorTile extends TileEntity implements ITickableTileEntity {

    public NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public List<BlockPos> targetPos;

    public LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public DomeCreatorTile(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
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
                Item item = this.inventory.get(0).getItem();
                int size = getSize(item);
                if (this.targetPos.isEmpty()) {
                    possibleTargets(size);
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

    public void addPosIfValid(BlockPos pos) {
        if (getTag() != null) {
            if (this.world.getBlockState(pos).isIn(getTag())) {
                this.targetPos.add(pos);
            }
        } else {
            this.targetPos.add(pos);
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
                    if ((x * x) + (y * y) + (z * z) < (size * size) + size + 1) {
                        BlockPos pos = this.pos.add(x, y, z);
                        addPosIfValid(pos);
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

}
