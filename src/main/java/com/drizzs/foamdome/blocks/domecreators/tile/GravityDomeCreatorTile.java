package com.drizzs.foamdome.blocks.domecreators.tile;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.blocks.CreatorTile;
import com.drizzs.foamdome.util.FoamVariables;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;
import static com.drizzs.foamdome.util.DomeTags.*;

public class GravityDomeCreatorTile extends CreatorTile {

    public List<BlockPos> outsidePos = new ArrayList<BlockPos>();
    public List<BlockPos> insidePos = new ArrayList<BlockPos>();


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
            ItemStack item = FoamVariables.item;
            FoamDome.LOGGER.info("info");
            if (!item.isEmpty()) {
                int size = getSize(item.getItem());
                FoamDome.LOGGER.info(noPos);
                if (!noPos) {
                    possibleTargets(direction, size);
                    FoamDome.LOGGER.info("this many inside positions" + insidePos.size() + "and this many outside positions" + outsidePos.size());
                    noPos = true;
                }
                if (!insidePos.isEmpty()) {
                    BlockPos pos = insidePos.get(0);
                    world.setBlockState(pos, getFoam());
                    insidePos.remove(0);
                }
                if (!outsidePos.isEmpty()) {
                    BlockPos pos2 = outsidePos.get(0);
                    world.setBlockState(pos2, getFoam2());
                    outsidePos.remove(0);
                }
                if (insidePos.isEmpty() && outsidePos.isEmpty()) {
                    FoamVariables.item = null;
                    activated = false;
                    noPos = false;
                }
            } else {
                activated = false;
            }

        }
    }

    @Override
    public Tag<Block> getTag() {
        return GRAVITYDOME;
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

    @Override
    public void addPosIfValid(BlockPos pos, List<BlockPos> list) {
        BlockState state = world.getBlockState(pos);
        boolean isNotCreator = !state.equals(this.getBlockState());
        if(!insidePos.contains(pos) && isNotCreator){
            if(state.isIn(getTag()) || state.isAir(world, pos)) {
                list.add(pos);
            }
        }
    }
}
