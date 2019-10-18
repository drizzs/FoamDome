package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.util.DomeLib;
import com.drizzs.foamdome.util.DomeConfigs;
import com.drizzs.foamdome.util.DomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class GlassDomeCreator extends Block {

    private final int size;

    public GlassDomeCreator(Properties properties) {
        super(properties);
        this.size = DomeConfigs.COMMON.GLASSDOMESIZE.get();
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
        for (int i = -size; i <= size; i++) {
            for (int k = -size; k <= size; k++) {
                for (int j = -size; j <= size; j++) {
                    if ((i * i) + (j * j) + (k * k) < (size * size) + size + 1) {
                        BlockPos posAll = pos.add(i, j, k);
                        BlockState targetState = world.getBlockState(posAll);
                        Block targetBlock = targetState.getBlock();
                        if (targetBlock.isIn(DomeTags.UNDERWATER) && !targetState.isSolid()) {
                            world.setBlockState(posAll, DomeLib.glassfoam.getDefaultState());
                        }
                    }
                }
            }
        }
    }



}
