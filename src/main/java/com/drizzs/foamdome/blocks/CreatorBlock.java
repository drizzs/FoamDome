package com.drizzs.foamdome.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

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
                ((CreatorTile) entity).activated = true;
                ((CreatorTile) entity).direction = direction;
            }
        }
        return true;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity playerEntity) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof CreatorTile) {
            ((CreatorTile) entity).handler.ifPresent(inventory -> {
                LOGGER.info("checks inventory on harvest");
                ItemStack stack = inventory.getStackInSlot(0);
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                world.addEntity(itemEntity);
            });
        }
        super.onBlockHarvested(world, pos, state, playerEntity);
    }

}
