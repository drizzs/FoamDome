package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.util.DomeLib;
import com.drizzs.foamdome.util.DomeConfigs;
import com.drizzs.foamdome.util.DomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GravityDomeCreator extends FallingBlock {

    private final int size;

    public GravityDomeCreator(Properties properties)
    {
        super(properties);
        this.size = DomeConfigs.COMMON.LAVADOMESIZE.get();
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            this.checkFallable(world, pos);
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                foamActivation(world, pos);
            }
        }, 4000);
        return true;
    }


    private void foamActivation(World world, BlockPos pos) {
        for (int i = -size; i <= size; i++) {
            for (int k = -size; k <= size; k++) {
                for (int j = -size; j <= size; j++) {
                    if ((i * i) + (j * j) + (k * k) < (size * size) + size + 1) {
                        BlockPos posAll = pos.add(i, j - (size/2) - 2, k);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (targetBlock.isIn(DomeTags.GRAVITYDOME) && !targetState.isSolid()) {
                            world.setBlockState(posAll, DomeLib.foam.getDefaultState());
                        }
                    }

                }
            }
        }
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {

    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                this.onStartFalling(fallingblockentity);
                worldIn.addEntity(fallingblockentity);
            }

        }
    }

}
