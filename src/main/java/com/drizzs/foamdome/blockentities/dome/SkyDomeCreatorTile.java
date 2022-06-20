package com.drizzs.foamdome.blockentities.dome;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.*;
import static com.drizzs.foamdome.util.DomeTags.SKY;

public class SkyDomeCreatorTile extends CreatorEntity {

    public SkyDomeCreatorTile(BlockPos pos, BlockState state) {
        super(SKY_DOME_TILE.get(),pos,state);
    }

    @Override
    public TagKey<Block> getTag() {
        return SKY;
    }

    @Override
    public BlockState getFoam() {
        return DISOLVABLE_SKY_FOAM.get().defaultBlockState();
    }

    @Override
    public BlockState getFoam2() {
        return HARD_SKY_FOAM.get().defaultBlockState();
    }

}
