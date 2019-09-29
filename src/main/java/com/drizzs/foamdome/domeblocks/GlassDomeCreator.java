package com.drizzs.foamdome.domeblocks;


import com.drizzs.foamdome.domeregistry.DomeBlocks;
import com.drizzs.foamdome.util.FoamTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class GlassDomeCreator extends Block {

    public GlassDomeCreator(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {

        int size = 5;

        for(int i = -size; i <= size; i++){
            for(int k = -size; k <= size; k++){
                for(int j = -size; j <= size; j++){
                    if(((i*i) + (j*j) + (k*k)/2) - size < size + 1) {
                        BlockPos posAll = pos.add(i, j, k);
                        if (world.getBlockState(posAll).getBlock().isIn(FoamTags.UNDERWATER)){
                            world.setBlockState(posAll, DomeBlocks.glassfoam.getDefaultState());
                        }
                    }

                }
            }
        }
        return true;
    }










}
