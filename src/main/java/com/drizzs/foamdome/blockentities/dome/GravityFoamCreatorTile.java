package com.drizzs.foamdome.blockentities.dome;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import com.drizzs.foamdome.entities.GravityEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.foamdome.util.DomeRegistry.*;
import static com.drizzs.foamdome.util.DomeTags.*;
import static net.minecraft.world.level.block.FallingBlock.isFree;

public class GravityFoamCreatorTile extends CreatorEntity {

    public GravityFoamCreatorTile(BlockPos pos, BlockState state) {
        super(GRAVITY_DOME_TILE.get(),pos,state);
    }

    @Override
    public TagKey<Block> getTag() {
        return GRAVITYDOME;
    }

    @Override
    public BlockState getFoam() {
        return HARD_GRAVITY_FOAM.get().defaultBlockState();
    }

    @Override
    public BlockState getFoam2() {
        return DISOLVABLE_GRAVITY_FOAM.get().defaultBlockState();
    }

    @Override
    public void foamMovieMagic() {
        if(activated && !foamStart) {
            assert level != null;
            checkFallable(level, getBlockPos());
        }
        if(activated && foamStart) {
            super.foamMovieMagic();
        }
    }

    public ItemStack getShapeStack(){
        return inventory.getStackInSlot(1);
    }

    @Override
    protected void FoamStart() {
        activated = true;
        foamStart = !shape.equals("dome");
    }

    private void checkFallable(Level level, BlockPos pos) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            GravityEntity gravityEntity = GravityEntity.fall(level, pos, getBlockState());
            gravityEntity.setValues(shape,size,getShapeStack());
            this.falling(gravityEntity);
        }
    }

    protected void falling(FallingBlockEntity entity) {
    }

}
