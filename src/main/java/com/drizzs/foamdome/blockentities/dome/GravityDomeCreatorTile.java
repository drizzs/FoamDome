package com.drizzs.foamdome.blockentities.dome;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.*;
import static com.drizzs.foamdome.util.DomeTags.*;

public class GravityDomeCreatorTile extends CreatorEntity {

    public GravityDomeCreatorTile(BlockPos pos, BlockState state) {
        super(GRAVITY_DOME_TILE.get(),pos,state);
    }

    @Override
    public TagKey<Block> getTag() {
        return GRAVITYDOME;
    }

    @Override
    public BlockState getFoam() {
        return DISOLVABLE_GRAVITY_FOAM.get().defaultBlockState();
    }

    @Override
    public BlockState getFoam2() {
        return HARD_GRAVITY_FOAM.get().defaultBlockState();
    }

}
