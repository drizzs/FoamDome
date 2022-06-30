package com.drizzs.foamdome.common.containers.base;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import com.drizzs.foamdome.util.DomeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FoamContainer extends AbstractContainerMenu {
    protected final ContainerLevelAccess containerAccess;
    private final BlockPos pos;

    public FoamContainer(int id, Inventory playerinv) {
        this(id,playerinv,new ItemStackHandler(2), BlockPos.ZERO);
    }

    protected FoamContainer(int id, Inventory playerInv, IItemHandler slots, BlockPos pos) {
        super(DomeRegistry.FOAM_CONTAINER.get(),id);
        this.containerAccess = ContainerLevelAccess.create(playerInv.player.level,pos);
        this.pos = pos;
        final int slotSizePlus2 = 18, startX = 8, startY = 86, hotbarY = 144;

        addSlot(new SlotItemHandler(slots, 0, 62,
                18));
        addSlot(new SlotItemHandler(slots, 1, 98,
                18));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv, 9 + row * 9 + column, startX + column * slotSizePlus2,
                        startY + row * slotSizePlus2));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv, column, startX + column * slotSizePlus2, hotbarY));
        }

    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(containerAccess,player,DomeRegistry.BASIC_DOME_CREATOR.get()) ||
                stillValid(containerAccess,player,DomeRegistry.ACID_DOME_CREATOR.get()) ||
                stillValid(containerAccess,player,DomeRegistry.GLASS_DOME_CREATOR.get()) ||
                stillValid(containerAccess,player,DomeRegistry.GLASS_SKY_DOME_CREATOR.get()) ||
                stillValid(containerAccess,player,DomeRegistry.GRAVITY_DOME_CREATOR.get()) ||
                stillValid(containerAccess,player,DomeRegistry.SKY_DOME_CREATOR.get()) ;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = getSlot(index);
        if (slot.hasItem()) {
            final ItemStack item = slot.getItem();
            retStack = item.copy();
            if (index < 27) {
                if (!moveItemStackTo(item, 27, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(item, 0, 27, false))
                return ItemStack.EMPTY;

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return retStack;
    }

    public static MenuConstructor getServerContainer(CreatorEntity entity, BlockPos pos){
        return (id,playerInventory, player) -> new FoamContainer(id,playerInventory, entity.inventory, pos);
    }

}
