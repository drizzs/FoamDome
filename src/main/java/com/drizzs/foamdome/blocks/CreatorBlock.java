package com.drizzs.foamdome.blocks;

import com.drizzs.foamdome.blocks.domecreators.tile.GravityDomeCreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

import static net.minecraft.block.FallingBlock.canFallThrough;

public class CreatorBlock extends Block {

    public CreatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        Direction direction = trace.getFace();
        TileEntity entity = world.getTileEntity(pos);
        if (!player.isSneaking()) {
            if (entity instanceof CreatorTile) {
                ((CreatorTile) entity).extractInsertItemMethod(player, hand);
            }
        } else {
            if (entity instanceof CreatorTile) {
                if(entity instanceof GravityDomeCreatorTile){
                    checkFallable(world, pos, (CreatorTile) entity);
                }else {
                    ((CreatorTile) entity).direction = direction;
                    ((CreatorTile) entity).activated = true;
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity playerEntity) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof CreatorTile) {
            ((CreatorTile) entity).handler.ifPresent(inventory -> {
                ItemStack stack = inventory.extractItem(0,1,false);
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
            });
        }
        super.onBlockHarvested(world, pos, state, playerEntity);
    }

    private void checkFallable(World worldIn, BlockPos pos, CreatorTile newentity) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                worldIn.addEntity(fallingblockentity);
                this.onStartFalling(fallingblockentity, worldIn);
            }
        }
    }

    protected void onStartFalling(FallingBlockEntity fallingEntity, World world) {
        int random = fallingEntity.world.rand.nextInt(5);
        int timer = 1000;
        if (random == 0) {
            timer = 2000;
        } else if (random == 1) {
            timer = 1500;
        } else if (random == 2) {
            timer = 1300;
        } else if (random == 3) {
            timer = 2200;
        } else if (random == 4) {
            timer = 1800;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                BlockState state = fallingEntity.getBlockState();
                BlockPos pos = fallingEntity.getPosition();
                world.setBlockState(pos,state);
                TileEntity entity = world.getTileEntity(pos);
                if(entity instanceof CreatorTile) {
                    ((CreatorTile) entity).activated = true;
                }
            }
        }, timer);
    }
}
