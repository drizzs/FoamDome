package com.drizzs.foamdome.blockentities.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.foamdome.util.DomeTags.SHAPE;
import static com.drizzs.foamdome.util.DomeTags.SIZE;

public class InventoryEntity extends BlockEntity {

    protected LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
    protected final int itemSlots;
    protected final int stackSize;
    private int timer;

    protected boolean requiresUpdate = false;

    public InventoryEntity(BlockEntityType<?> type, int slotSize,int stackSize,BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemSlots = slotSize;
        this.stackSize = stackSize;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }

    public void removeTime(){
        --timer;
    }

    public @NotNull IItemHandler createHandler() {
        return new ItemStackHandler(itemSlots) {

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.is(SIZE) && slot == 0 || stack.is(SHAPE) && slot == 1;
            }

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return stackSize;
            }

            @Override
            protected void onContentsChanged(int slot) {
                updateEntity();
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.handler.cast(): super.getCapability(cap,side);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.handler.cast(): super.getCapability(cap);
    }

    public LazyOptional<IItemHandler> getHandler() {
        return handler;
    }



    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        ListTag list = nbt.getList("Items", 10);
        for (int x = 0; x < list.size(); ++x) {
            CompoundTag tag = list.getCompound(x);
            int r = tag.getByte("Slot") & 255;
            handler.ifPresent(inventory -> {
                int invslots = inventory.getSlots();
                if (r < invslots) {
                    inventory.insertItem(r, ItemStack.of(nbt), false);
                }
            });
        }
        requiresUpdate = true;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        if(tag.isEmpty()){
            tag = new CompoundTag();
        }
        ListTag list = new ListTag();
        handler.ifPresent(inventory -> {
            int slots = inventory.getSlots();
            for (int x = 0; x < slots; ++x) {
                ItemStack stack = inventory.getStackInSlot(x);
                if (!stack.isEmpty()) {
                    CompoundTag nbt = new CompoundTag();
                    nbt.putByte("Slot", (byte) x);
                    stack.deserializeNBT(nbt);
                    list.add(nbt);
                }
            }
        });
        if (!list.isEmpty()) {
            tag.put("Items", list);
        }
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        deserializeNBT(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }

    @Override
    public void requestModelDataUpdate() {
        super.requestModelDataUpdate();
    }

    @NotNull
    @Override
    public IModelData getModelData() {
        return super.getModelData();
    }

    public void updateEntity() {
        requestModelDataUpdate();
        this.setChanged();
        if (this.getLevel() != null) {
            this.getLevel().setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


}
