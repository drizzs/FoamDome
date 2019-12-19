package com.drizzs.foamdome.blocks.domecreators.tile;

import com.drizzs.foamdome.blocks.CreatorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.drizzs.foamdome.util.DomeRegistryNew.*;
import static com.drizzs.foamdome.util.DomeTags.*;
import static net.minecraft.block.FallingBlock.canFallThrough;

public class GravityDomeCreatorTile extends CreatorTile {

    public List<BlockPos> outsidePos;
    public List<BlockPos> insidePos;

    public boolean build = false;

    public GravityDomeCreatorTile() {
        super(GRAVITY_DOME_TILE.get());
    }

    @Override
    public void tick() {
        foamMovieMagic();
    }

    @Override
    public void foamMovieMagic() {
        if (activated) {
            handler.ifPresent(inventory -> {
                ItemStack item = inventory.getStackInSlot(0);
                if (!item.isEmpty()) {
                    checkFallable(world, pos);
                    int size = getSize(item.getItem());
                    if (insidePos.isEmpty() && outsidePos.isEmpty() && build) {
                        possibleTargets(direction, size);
                        build = false;
                    }
                    BlockPos pos = insidePos.get(0);
                    BlockPos pos2 = outsidePos.get(0);
                    world.setBlockState(pos, getFoam());
                    world.setBlockState(pos2, getFoam2());
                    insidePos.remove(0);
                    outsidePos.remove(0);
                    if (insidePos.isEmpty() && outsidePos.isEmpty()) {
                        item.shrink(1);
                        activated = false;
                    }
                } else {
                    activated = false;
                }
            });
        }
    }

    public void addPosIfValid(BlockPos pos, List<BlockPos> list) {
            BlockState state = world.getBlockState(pos);
            if (state.isIn(getTag())) {
                list.add(pos);
            }
    }

    @Override
    public Tag<Block> getTag() {
        return UNDERWATER;
    }

    @Override
    public BlockState getFoam() {
        return DISOLVABLE_GRAVITY_FOAM.get().getDefaultState();
    }

    public BlockState getFoam2() {
        return HARD_GRAVITY_FOAM.get().getDefaultState();
    }

    @Override
    public void possibleTargets(Direction direction, int size) {
        for (int x = -size; x < size; ++x) {
            for (int y = -size; y < size; ++y) {
                for (int z = -size; z < size; ++z) {
                    double xp = Math.pow(x, 2);
                    double yp = Math.pow(y, 2);
                    double zp = Math.pow(z, 2);
                    if (xp + yp + zp < (Math.pow(size, 2))){
                        BlockPos pos = this.pos.add(x, y, z);
                        if (xp + yp + zp < (Math.pow(size, 2) - 1)) {
                            addPosIfValid(pos, this.insidePos);
                        } else{
                            addPosIfValid(pos, this.outsidePos);
                        }
                    }
                }
            }
        }
    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                worldIn.addEntity(fallingblockentity);
                this.onStartFalling(fallingblockentity);
            }
        }
    }

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
                build = true;
            }
        }, timer);
    }

}
