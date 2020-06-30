package com.drizzs.foamdome.blocks;

import com.drizzs.foamdome.items.FoamCartridge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistry.BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeRegistry.DISOLVABLE_BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeTags.*;

public class CreatorTile extends InventoryTile {

    public boolean activated = false;
    public boolean noPos = false;

    public Direction direction = null;

    public List<BlockPos> outsidePos = new ArrayList<>();
    public List<BlockPos> insidePos = new ArrayList<>();

    public CreatorTile(TileEntityType<?> type) {
        super(type,1);
    }

    @Override
    public void tick() {
        if(getTimer() > 0){
            removeTime();
        }
        foamMovieMagic();
    }


    public void foamMovieMagic() {
        if (activated) {
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
                    if (outsidePos.isEmpty() && insidePos.isEmpty()) {
                        activated = false;
                        item.shrink(1);
                        noPos = false;
                    }
                } else {
                    activated = false;
                }
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

    public ITag.INamedTag<Block> getTag() {
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
        if (!insidePos.contains(pos) && isNotCreator) {
            if (this.getTag().equals(ACID)) {
                list.add(pos);
            }
            if (this.getTag().equals(GRAVITYDOME)) {
                if (state.getBlock().isIn(getTag()) || state.isAir(world, pos)) {
                    list.add(pos);
                }
            }
            if (state.getBlock().isIn(getTag())) {
                list.add(pos);
            }
        }
    }

    public void extractInsertItemMethod(PlayerEntity player, Hand hand) {
        if(getTimer() <= 0) {
            ItemStack held = player.getHeldItem(hand);
            if (!held.isEmpty() && held.getItem() instanceof FoamCartridge && getItemInSlot(0).isEmpty()) {
                insertItem(0, held);
            } else if (!getItemInSlot(0).isEmpty()) {
                player.addItemStackToInventory(extractItem(0));
            }
            setTimer(20);
        }
    }
}
