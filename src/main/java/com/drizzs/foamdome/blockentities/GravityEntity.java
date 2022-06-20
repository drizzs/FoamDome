package com.drizzs.foamdome.blockentities;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Random;

public class GravityEntity extends FallingBlockEntity {

    private final Random rand = new Random();

    public GravityEntity(EntityType<? extends FallingBlockEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        int fallingTime = 10;
        int random = rand.nextInt(5);
        if (random == 0) {
            fallingTime = 25;
        } else if (random == 1) {
            fallingTime = 40;
        }else if (random == 2) {
            fallingTime = 99;
        }else if (random == 3) {
            fallingTime = 15;
        }else {
            fallingTime = 55;
        }
           if (time == fallingTime || onGround) {
            stopTheFalling();
        }
    }

    public void stopTheFalling() {
        this.remove(RemovalReason.DISCARDED);
        BlockPos pos = new BlockPos(position().x, position().y, position().z);
        level.setBlock(pos, this.getBlockState(),1);
        BlockEntity entityTile = level.getBlockEntity(pos);
        if (entityTile instanceof CreatorEntity) {
            ((CreatorEntity) entityTile).activated = true;
            ((CreatorEntity) entityTile).getHandler().ifPresent(inventory -> inventory.insertItem(0, ItemStack.EMPTY, true));
        }
    }
}
