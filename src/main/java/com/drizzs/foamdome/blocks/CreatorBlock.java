package com.drizzs.foamdome.blocks;

import com.drizzs.foamdome.blocks.domecreators.GravityEntity;
import com.drizzs.foamdome.blocks.domecreators.tile.GravityDomeCreatorTile;
import com.drizzs.foamdome.util.FoamVariables;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static net.minecraft.block.FallingBlock.canFallThrough;

public class CreatorBlock extends Block {

    public CreatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        Direction direction = trace.getFace();
        TileEntity entity = world.getTileEntity(pos);
        if (!player.func_225608_bj_()) {
            if (entity instanceof CreatorTile) {
                ((CreatorTile) entity).extractInsertItemMethod(player, hand);
            }
        } else {
            if (entity instanceof CreatorTile) {
                if(entity instanceof GravityDomeCreatorTile){
                    ((CreatorTile) entity).handler.ifPresent(inventory -> {
                        FoamVariables.item = inventory.getStackInSlot(0);
                    });
                    if (!FoamVariables.item.isEmpty()) {
                        checkFallable(world, pos);
                    }
                }else {
                    ((CreatorTile) entity).direction = direction;
                    ((CreatorTile) entity).activated = true;
                }
            }
        }
        return ActionResultType.PASS;
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

    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            FallingBlockEntity fallingblockentity = new GravityEntity(worldIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
            if (!worldIn.isRemote) {
                worldIn.addEntity(fallingblockentity);
            }
        }
    }
}
