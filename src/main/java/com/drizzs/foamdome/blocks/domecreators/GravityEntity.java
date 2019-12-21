package com.drizzs.foamdome.blocks.domecreators;

import com.drizzs.foamdome.blocks.CreatorTile;
import com.drizzs.foamdome.util.FoamVariables;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GravityEntity extends FallingBlockEntity {

    public GravityEntity(EntityType<? extends FallingBlockEntity> p_i50218_1_, World p_i50218_2_) {
        super(p_i50218_1_, p_i50218_2_);
    }

    public GravityEntity(World worldIn, double x, double y, double z, BlockState fallingBlockState) {
        super(worldIn, x, y, z, fallingBlockState);
    }

    @Override
    public void tick() {
        super.tick();
        int fallingTime = 10;
        int random = rand.nextInt(5);
        if (random == 0) {
            fallingTime = 40;
        } else if (random == 1) {
            fallingTime = 40;
        }else if (random == 2) {
            fallingTime = 40;
        }else if (random == 3) {
            fallingTime = 40;
        }else if (random == 4) {
            fallingTime = 40;
        }
           if (fallTime == fallingTime || onGround) {
            stopTheFalling();
        }
    }

    public void stopTheFalling() {
        this.remove();
        BlockPos pos = new BlockPos(posX, posY, posZ);
        world.setBlockState(pos, this.getBlockState());
        TileEntity entityTile = world.getTileEntity(pos);
        if (entityTile instanceof CreatorTile) {
            ((CreatorTile) entityTile).activated = true;
            ((CreatorTile) entityTile).handler.ifPresent(inventory ->{
                inventory.insertItem(0, FoamVariables.item, true);
            });
        }
    }
}
