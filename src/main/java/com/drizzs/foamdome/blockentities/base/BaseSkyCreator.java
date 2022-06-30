package com.drizzs.foamdome.blockentities.base;

import com.drizzs.foamdome.blocks.base.AntiGravityBlock;
import com.drizzs.foamdome.entities.AntiGravityEntity;
import com.drizzs.foamdome.entities.GravityEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.DISOLVABLE_SKY_FOAM;
import static com.drizzs.foamdome.util.DomeTags.SKY;

public abstract class BaseSkyCreator extends CreatorEntity {

    public BaseSkyCreator(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public BlockState getFoam2() {
        return DISOLVABLE_SKY_FOAM.get().defaultBlockState();
    }

    @Override
    public TagKey<Block> getTag() {
        return SKY;
    }

    @Override
    public void foamMovieMagic() {
        if(activated && !foamStart) {
            assert level != null;
            ((AntiGravityBlock)getBlockState().getBlock()).checkRiseable(level, getBlockPos(),shape,size,inventory.getStackInSlot(1));
        }
        if(activated && foamStart) {
            super.foamMovieMagic();
        }
    }

    @Override
    protected void FoamStart() {
        activated = true;
        foamStart = !shape.equals("dome");
    }
}
