package com.drizzs.foamdome.blockentities.dome;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.ACID_DOME_TILE;
import static com.drizzs.foamdome.util.DomeRegistry.ACID_FOAM;
import static com.drizzs.foamdome.util.DomeTags.ACID;

public class AcidFoamCreatorTile extends CreatorEntity {

    public AcidFoamCreatorTile(BlockPos pos, BlockState state) {
        super(ACID_DOME_TILE.get(),pos,state);
    }

    @Override
    public BlockState getFoam() {
        return ACID_FOAM.get().defaultBlockState();
    }

    @Override
    public BlockState getFoam2() {
        return ACID_FOAM.get().defaultBlockState();
    }

    @Override
    public TagKey<Block> getTag() {
        return ACID;
    }

}
