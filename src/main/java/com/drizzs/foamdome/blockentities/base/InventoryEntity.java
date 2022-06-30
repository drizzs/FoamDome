package com.drizzs.foamdome.blockentities.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import static com.drizzs.foamdome.util.DomeTags.SHAPE;
import static com.drizzs.foamdome.util.DomeTags.SIZE;

public class InventoryEntity extends BlockEntity {

    protected final int itemSlots;
    protected final int stackSize;
    private int timer = 0;
    protected boolean requiresUpdate = false;

    public final ItemStackHandler inventory;
    protected LazyOptional<ItemStackHandler> handler;

    public InventoryEntity(BlockEntityType<?> type, int slotSize,int stackSize,BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemSlots = slotSize;
        this.stackSize = stackSize;
        this.inventory = createInventory();
        this.handler = LazyOptional.of(() -> this.inventory);
    }

    private ItemStackHandler createInventory() {
        return new ItemStackHandler(this.itemSlots) {
            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                InventoryEntity.this.updateEntity();
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                InventoryEntity.this.updateEntity();
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.is(SIZE) && slot == 0 || stack.is(SHAPE) && slot == 1;
            }

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return stackSize;
            }
        };
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.handler.cast()
                : super.getCapability(cap, side);
    }

    public LazyOptional<ItemStackHandler> getHandler() {
        return this.handler;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.inventory.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    public void tick() {
        this.timer++;
        if (this.requiresUpdate && this.level != null) {
            updateEntity();
            this.requiresUpdate = false;
        }
    }

    public void updateEntity() {
        requestModelDataUpdate();
        setChanged();
        if (this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", this.inventory.serializeNBT());
    }


}
