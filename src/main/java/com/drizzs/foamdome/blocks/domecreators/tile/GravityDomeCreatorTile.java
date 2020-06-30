package com.drizzs.foamdome.blocks.domecreators.tile;

import com.drizzs.foamdome.blocks.CreatorTile;
import com.drizzs.foamdome.util.FoamVariables;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.math.BlockPos;

import static com.drizzs.foamdome.util.DomeRegistry.*;
import static com.drizzs.foamdome.util.DomeTags.*;

public class GravityDomeCreatorTile extends CreatorTile {

    public GravityDomeCreatorTile() {
        super(GRAVITY_DOME_TILE.get());
    }

    @Override
    public ITag.INamedTag<Block> getTag() {
        return GRAVITYDOME;
    }

    @Override
    public BlockState getFoam() {
        return DISOLVABLE_GRAVITY_FOAM.get().getDefaultState();
    }

    @Override
    public BlockState getFoam2() {
        return HARD_GRAVITY_FOAM.get().getDefaultState();
    }

    @Override
    public void foamMovieMagic() {
        if (activated) {
            ItemStack item = FoamVariables.item;
            if (!item.isEmpty()) {
                int size = getSize(item.getItem());
                if (!noPos) {
                    possibleTargets(direction, size);
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

}
