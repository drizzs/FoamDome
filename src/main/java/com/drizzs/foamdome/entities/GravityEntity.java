package com.drizzs.foamdome.entities;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class GravityEntity extends FallingBlockEntity {

    private final Random rand = new Random();

    private int size = 0;
    private String shape = "";
    private ItemStack shapeStack = ItemStack.EMPTY;

    public GravityEntity(EntityType<? extends FallingBlockEntity> type, Level world) {
        super(type, world);
    }

    public GravityEntity(Level level, double x, double y, double z, BlockState state){
        this(EntityType.FALLING_BLOCK, level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    public static GravityEntity fall(Level level, BlockPos pos, BlockState state) {
        GravityEntity gravityEntity = new GravityEntity(level, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, false) : state);
        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(gravityEntity);
        return gravityEntity;
    }

    public void setValues(String shape, int size,ItemStack shapeStack){
        this.shape = shape;
        this.size = size;
        this.shapeStack = shapeStack;
    }

    @Override
    public void tick() {
        super.tick();
        int fallingTime;
        int random = rand.nextInt(5);
        if (random == 0) {
            fallingTime = 45;
        } else if (random == 1) {
            fallingTime = 25;
        }else if (random == 2) {
            fallingTime = 35;
        }else if (random == 3) {
            fallingTime = 40;
        }else {
            fallingTime = 30;
        }
        if (time >= fallingTime || onGround) {
            stopTheFalling();
        }
    }

    public void stopTheFalling() {
        this.remove(RemovalReason.DISCARDED);
        BlockPos pos = new BlockPos(position().x, position().y, position().z);
        level.setBlock(pos, this.getBlockState(),2);
        BlockEntity entityTile = level.getBlockEntity(pos);
        if (entityTile instanceof CreatorEntity et) {
            et.setShape(shape);
            et.setSize(size);
            et.inventory.setStackInSlot(1,shapeStack);
            et.activated = true;
            et.foamStart = true;
        }
    }



}
