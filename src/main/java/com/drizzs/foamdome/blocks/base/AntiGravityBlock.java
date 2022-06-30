package com.drizzs.foamdome.blocks.base;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import com.drizzs.foamdome.common.interfaces.Floatable;
import com.drizzs.foamdome.entities.AntiGravityEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import java.util.function.Predicate;

public abstract class AntiGravityBlock extends InventoryBlock implements Floatable {

    public void checkRiseable(Level level, BlockPos pos, String shape, int size, ItemStack stack) {
        if (isFree(level.getBlockState(pos.above())) && pos.getY() >= level.getMinBuildHeight()) {
            AntiGravityEntity antiGravity = AntiGravityEntity.raise(level, pos, this.defaultBlockState());
            antiGravity.setValues(shape,size,stack);
            this.rising(antiGravity);
        }
    }

    protected void rising(AntiGravityEntity entity) {
    }

    public static boolean isFree(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    @Override
    public void onFloatStop(Level level, BlockPos pos, BlockState state1, BlockState state2, AntiGravityEntity entity) {
        entity.remove(Entity.RemovalReason.DISCARDED);
        BlockPos pos2 = new BlockPos(entity.position().x, entity.position().y, entity.position().z);
        level.setBlock(pos, entity.getBlockState(),2);
        BlockEntity entityTile = level.getBlockEntity(pos2);
        if (entityTile instanceof CreatorEntity et) {
            et.setShape(entity.shape);
            et.setSize(entity.size);
            et.inventory.setStackInSlot(1,entity.shapeStack);
            et.activated = true;
            et.foamStart = true;
        }
    }

    @Override
    public void onBrokenAfterFloatStop(Level level, BlockPos pos, AntiGravityEntity entity) {

    }

    @Override
    public DamageSource getRaiseDamageSource() {
        return new DamageSource("risingBlock");
    }

    @Override
    public @NotNull Predicate<Entity> getHurtsEntitySelector() {
        return EntitySelector.NO_SPECTATORS;
    }

}

