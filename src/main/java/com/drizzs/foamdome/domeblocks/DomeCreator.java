package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.util.DomeConfigs;
import com.drizzs.foamdome.util.DomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

import static com.drizzs.foamdome.util.DomeLib.*;
import static net.minecraft.block.FallingBlock.canFallThrough;

public class DomeCreator extends Block {

    public DomeCreator(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (this.getBlock().equals(gravitydomecreator)) {
            checkFallable(world, pos);
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    foamActivation(world, pos);
                }
            }, 4000);
        }
        return true;
    }

    private void foamActivation(World world, BlockPos pos) {
        int size = 5;
        if (this.getBlock().equals(glassdomecreator)) {
            size = DomeConfigs.COMMON.GLASSDOMESIZE.get();
        } else if (this.getBlock().equals(domecreator)) {
            size = DomeConfigs.COMMON.DOMESIZE.get();
        } else if (this.getBlock().equals(gravitydomecreator)) {
            size = DomeConfigs.COMMON.GRAVITYDOMESIZE.get();
        } else if (this.getBlock().equals(aciddomecreator)) {
            size = DomeConfigs.COMMON.ACIDDOMESIZE.get();
        }
        for (int x = -size; x <= size; x++) {
            for (int y = -size; y <= size; y++) {
                for (int z = -size; z <= size; z++) {
                    if ((x * x) + (y * y) + (z * z) < (size * size) + size + 1) {
                        foamMethodMagic(world, pos, x, y, z, size);
                    }
                }
            }
        }
    }

    private void foamMethodMagic(World world, BlockPos pos, int x, int y, int z, int size) {
        BlockPos posAll = pos.add(x, y, z);
        BlockState targetState = world.getBlockState(posAll);
        Block targetBlock = targetState.getBlock();
        if (this.getBlock().equals(domecreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, foam.getDefaultState());
            }
        } else if (this.getBlock().equals(glassdomecreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, glassfoam.getDefaultState());
            }
        } else if (this.getBlock().equals(aciddomecreator)) {
            world.setBlockState(posAll, acidfoam.getDefaultState());
        } else if (this.getBlock().equals(gravitydomecreator)) {
            if (targetBlock.isIn(DomeTags.GRAVITYDOME)) {

                if ((x * x) + (y * y) + (z * z) < (size * size) - 1) {
                    world.setBlockState(posAll, gravityfoam.getDefaultState());
                } else {
                    world.setBlockState(posAll, hardgravityfoam.getDefaultState());
                }
            }
        }
    }

    //Gravity
    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                worldIn.addEntity(fallingblockentity);
                this.onStartFalling(fallingblockentity);
            }
        }
    }

    //Spawns Dome in new position
    protected void onStartFalling(FallingBlockEntity fallingEntity) {
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
                BlockPos pos = new BlockPos(fallingEntity.lastTickPosX, fallingEntity.lastTickPosY, fallingEntity.lastTickPosZ);
                foamActivation(fallingEntity.world, pos);
            }
        }, timer);
    }

}
