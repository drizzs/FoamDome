package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.util.DomeConfigs;
import com.drizzs.foamdome.util.DomeLib;
import com.drizzs.foamdome.util.DomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.drizzs.foamdome.util.DomeLib.*;
import static net.minecraft.block.FallingBlock.canFallThrough;

public class DomeCreator extends Block {


    public DomeCreator(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (this.getBlock().equals(gravitydomecreator)) {
            destroyGravityFoam(world, pos, random);
        }
        super.tick(state, world, pos, random);
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                foamActivation(world, pos);
            }
        }, 4000);
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
                        foamMethodMagic(world, pos, x, y, z);
                    }
                }
            }
        }
    }

    private void foamMethodMagic(World world, BlockPos pos, int x, int y, int z) {
        BlockPos posAll = pos.add(x, y, z);
        BlockState targetState = world.getBlockState(posAll);
        Block targetBlock = targetState.getBlock();
        if (this.getBlock().equals(tunnelcreator) || this.getBlock().equals(domecreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, foam.getDefaultState());
            }
        } else if (this.getBlock().equals(glassdomecreator) || this.getBlock().equals(glasstunnelcreator)) {
            if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                world.setBlockState(posAll, glassfoam.getDefaultState());
            }
        } else if (this.getBlock().equals(aciddomecreator) || this.getBlock().equals(acidtunnelcreator)) {
            world.setBlockState(posAll, acidfoam.getDefaultState());
        } else if (this.getBlock().equals(gravitydomecreator) || this.getBlock().equals(airtunnelcreator)) {
            if (targetBlock.isIn(DomeTags.GRAVITYDOME))
                world.setBlockState(posAll, gravityfoam.getDefaultState());
        }
    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                worldIn.addEntity(fallingblockentity);
            }

        }
    }

    private void destroyGravityFoam(World world, BlockPos pos, Random random) {
        int size = DomeConfigs.COMMON.GRAVITYDOMESIZE.get();
        for (int i = -size; i <= size; i++) {
            for (int k = -size; k <= size; k++) {
                for (int j = -size; j <= size; j++) {
                    if ((i * i) + (j * j) + (k * k) < (size * size) + size) {
                        BlockPos posAll = pos.add(i, j, k);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (targetBlock == gravityfoam) {
                            int rand = random.nextInt(200);
                            if (rand == 0) {
                                world.destroyBlock(posAll, false);
                            } else {
                                break;
                            }

                        }
                    }
                }
            }
        }
    }

}
