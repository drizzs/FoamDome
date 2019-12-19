package com.drizzs.foamdome.blocks.domecreators.tile;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.blocks.CreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;
import static com.drizzs.foamdome.util.DomeTags.*;
import static net.minecraft.block.FallingBlock.canFallThrough;

public class GravityDomeCreatorTile extends CreatorTile {

    public List<BlockPos> outsidePos;
    public List<BlockPos> insidePos;

    public boolean build = false;

    public GravityDomeCreatorTile() {
        super(GRAVITY_DOME_TILE.get());
    }

    @Override
    public void tick() {
        foamMovieMagic();
    }

    @Override
    public void foamMovieMagic() {
        if (activated) {
            FoamDome.LOGGER.info("activated");
            handler.ifPresent(inventory -> {
                ItemStack item = inventory.getStackInSlot(0);
                if (!item.isEmpty()) {
                    int size = getSize(item.getItem());
                    if (insidePos.isEmpty() && outsidePos.isEmpty()) {
                        possibleTargets(direction, size);
                    }
                    else if (insidePos.size() != 0) {
                        BlockPos pos = insidePos.get(0);
                        world.setBlockState(pos, getFoam());
                        insidePos.remove(0);
                        if (insidePos.isEmpty() && outsidePos.isEmpty()) {
                            item.shrink(1);
                            activated = false;
                        }
                    }
                    else if (outsidePos.size() != 0) {
                        BlockPos pos2 = outsidePos.get(0);
                        world.setBlockState(pos2, getFoam2());
                        outsidePos.remove(0);
                        if (insidePos.isEmpty() && outsidePos.isEmpty()) {
                            item.shrink(1);
                            activated = false;
                        }
                    }
                } else {
                    activated = false;
                }
            });
        }
    }

    public void addPosIfValid(BlockPos pos, List<BlockPos> list) {
        BlockState state = world.getBlockState(pos);
        if (state.isIn(getTag())) {
            list.add(pos);
        }
    }

    @Override
    public Tag<Block> getTag() {
        return UNDERWATER;
    }

    @Override
    public BlockState getFoam() {
        return DISOLVABLE_GRAVITY_FOAM.get().getDefaultState();
    }

    public BlockState getFoam2() {
        return HARD_GRAVITY_FOAM.get().getDefaultState();
    }

    @Override
    public void possibleTargets(Direction direction, int size) {
        for (int x = -size; x < size; ++x) {
            for (int y = -size; y < size; ++y) {
                for (int z = -size; z < size; ++z) {
                    double xp = Math.pow(x, 2);
                    double yp = Math.pow(y, 2);
                    double zp = Math.pow(z, 2);
                    if (xp + yp + zp < (Math.pow(size, 2))) {
                        BlockPos targetPos = pos.add(x, y, z);
                        if (xp + yp + zp < (Math.pow(size, 2) - 1)) {
                            addPosIfValid(targetPos, insidePos);
                        } else {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        }
    }
}
