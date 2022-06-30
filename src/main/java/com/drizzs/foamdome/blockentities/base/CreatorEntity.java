package com.drizzs.foamdome.blockentities.base;

import com.drizzs.foamdome.items.FoamCartridge;
import com.drizzs.foamdome.items.ShapeCartridge;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistry.BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeRegistry.DISOLVABLE_BASIC_FOAM;
import static com.drizzs.foamdome.util.DomeTags.*;

public class CreatorEntity extends InventoryEntity {
    public List<BlockPos> outsidePos = new ArrayList<>();
    public java.util.List<BlockPos> insidePos = new ArrayList<>();
    public boolean activated = false;
    public boolean foamStart = false;
    public Direction direction;
    protected int size = 0;
    protected String shape = "";

    public CreatorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, 2,1, pos, state);
        this.direction = Direction.WEST;
    }

    public int getSize(Item item) {
        int size = 0;
        if (item instanceof FoamCartridge fc) {
            size = fc.getDomeSize();
        }
        return size;
    }

    public String getShape(Item item) {
        String shape = "None";
        if (item instanceof ShapeCartridge sc) {
            shape = sc.getShape();
        }
        return shape;
    }

    public void foamMovieMagic() {
        tick();
        if(foamStart){
            if (outsidePos.isEmpty() && insidePos.isEmpty() && !shape.isEmpty() && size > 0) {
                getShapePos();
            }
            if (!insidePos.isEmpty()) {
                BlockPos pos = insidePos.get(0);
                assert level != null;
                level.setBlock(pos, getFoam2(),2);
                insidePos.remove(0);
            }
            if (!outsidePos.isEmpty()) {
                BlockPos pos = outsidePos.get(0);
                assert level != null;
                level.setBlock(pos, getFoam(),2);
                outsidePos.remove(0);
            }
            if(outsidePos.isEmpty() && insidePos.isEmpty()){
                foamStart = false;
                activated = false;
            }
        }
    }

    public BlockState getFoam() {
        return BASIC_FOAM.get().defaultBlockState();
    }
    public BlockState getFoam2() {
        return DISOLVABLE_BASIC_FOAM.get().defaultBlockState();
    }

    public TagKey<Block> getTag() {
        return UNDERWATER;
    }

    public void addPosIfValid(BlockPos pos, List<BlockPos> list) {
        assert level != null;
        BlockState state = level.getBlockState(pos);
        boolean isNotCreator = !state.equals(this.getBlockState());
        if(!insidePos.contains(pos) && isNotCreator){
            if(this.getTag() == ACID){
                list.add(pos);
            }else if(this.getTag() == GRAVITYDOME){
                if(state.is(getTag()) || state.isAir()) {
                    list.add(pos);
                }
            }else if(this.getTag() == SKY){
                if(state.isAir()){
                    list.add(pos);
                }
            }
            if(state.is(getTag())) {
                list.add(pos);
            }
        }
    }

    protected boolean hasExtraRequirements(){
        return true;
    }

    protected void FoamStart(){
        activated = true;
        foamStart = true;
        requiresUpdate = true;
    }

    public void InitializeCreation(){
        if(hasExtraRequirements()) {
            handler.ifPresent(inventory -> {
                    ItemStack item = inventory.getStackInSlot(0);
                    ItemStack item2 = inventory.getStackInSlot(1);

                    if(!item.isEmpty() && !item2.isEmpty()){
                        shape = getShape(item2.getItem());
                        size = getSize(item.getItem());
                        inventory.extractItem(0,1,false);
                        FoamStart();
                    }
            });
        }
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setSize(int size) {
        this.size = size;
    }

    protected void getShapePos(){
        switch (shape) {
            case "cube" -> getCubeBlockPos();
            case "pyramid" -> getPyramidBlockPos();
            case "half" -> getHalfDomeBlockPos();
            case "tunnel" -> getTunnelBlockPos();
            default -> getDomeBlockPos();
        }
    }

    protected void getCubeBlockPos(){
        BlockPos topCorner = getBlockPos().offset(size,size,size);
        BlockPos bottomCorner = getBlockPos().offset(-size,-size,-size);
        for (int x = -size; x < size + 1; ++x) {
            for (int y = -size; y < size + 1; ++y) {
                for (int z = -size; z < size + 1; ++z) {
                    BlockPos targetPos = getBlockPos().offset(x, y, z);
                    if(cubeOuterEdge(targetPos,topCorner,bottomCorner)){
                        addPosIfValid(targetPos, outsidePos);
                    }else{
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        }
    }

    private boolean cubeOuterEdge(BlockPos targetPos, BlockPos topCorner,BlockPos bottomCorner){
        return targetPos.getY() == topCorner.getY() || targetPos.getY() == bottomCorner.getY()
                || targetPos.getZ() == topCorner.getZ() || targetPos.getZ() == bottomCorner.getZ()
                || targetPos.getX() == topCorner.getX() || targetPos.getX() == bottomCorner.getX();
    }

    protected void getDomeBlockPos(){
        for (int x = -size; x < size; ++x) {
            for (int y = -size; y < size; ++y) {
                for (int z = -size; z < size; ++z) {
                    double xp = Math.pow(x, 2);
                    double yp = Math.pow(y, 2);
                    double zp = Math.pow(z, 2);
                    if (xp + yp + zp < (Math.pow(size, 2))) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if (xp + yp + zp < (Math.pow((size - 1), 2))) {
                            addPosIfValid(targetPos, insidePos);
                        } else {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        }
    }

    protected void getHalfDomeBlockPos(){
        for (int x = -size; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                for (int z = -size; z < size; ++z) {
                    double xp = Math.pow(x, 2);
                    double yp = Math.pow(y, 2);
                    double zp = Math.pow(z, 2);
                    if (xp + yp + zp < (Math.pow(size, 2))) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if (y != 0 && xp + yp + zp < (Math.pow((size - 1), 2))) {
                            addPosIfValid(targetPos, insidePos);
                        } else {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        }
    }

    protected void getPyramidBlockPos(){
        int width = size;
        for (int y = 0; y < size; ++y) {
            for (int x = -width; x < width; ++x) {
                for (int z = -width; z < width; ++z) {
                    BlockPos targetPos = getBlockPos().offset(x, y, z);
                    if (y == 0 || z == width - 1 || x == width -1 || z == -width || x == -width ) {
                        addPosIfValid(targetPos, outsidePos);
                    } else {
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
            width = width - 1;
        }
    }

    protected void getTunnelBlockPos(){
        int newSize = (size+3);

        int y0 = 0,y1,x0 = 0,x1 = 0,z0 = 0,z1 = 0,yX,yX2,x7,x8,x9,x10;

        y1 = 3;
        yX = 3;
        x7 = -1;
        x8 = 1;

        if (direction == Direction.SOUTH) {
            x0 = -1;
            x1 = 1;
            z0 = -newSize;
        } else if (direction == Direction.NORTH) {
            x0 = -1;
            x1 = 1;
            z1 = newSize;
        } else if (direction == Direction.EAST) {
            x0 = -1;
            x1 = 1;
            z0 = -newSize;
        } else if (direction == Direction.WEST) {
            x0 = -1;
            x1 = 1;
            z1 = newSize;
        } else if (direction == Direction.DOWN) {
            y0 = 1;
            y1 = newSize;
            z0 = -1;
            z1 = 1;
            x0 = -1;
            x1 = 1;

            yX = 30;
            x7 = 50;
            x8 = 50;
        } else if (direction == Direction.UP) {
            y0 = -newSize;
            y1 = 1;
            z0 = -1;
            z1 = 1;
            x0 = -1;
            x1 = 1;

            yX = 30;
            x7 = 50;
            x8 = 50;
        }

        for (int y = y0; y <= y1; ++y) {
            for (int x = x0; x <= x1; ++x) {
                for (int z = z0; z <= z1; ++z) {
                    if (y == yX ) {

                            if (x == x7 || x == x8) {
                              break;
                            }

                    }
                    BlockPos targetPos;
                    if(direction == Direction.EAST || direction == Direction.WEST) {
                        targetPos = getBlockPos().offset(z, y, x);
                    }else{
                        targetPos = getBlockPos().offset(x, y, z);
                    }
                    addPosIfValid(targetPos, insidePos);
                }
            }
        }

        newSize = (size+4);

        y0 = -1;
        y1 = 4;

        yX = 3;
        x7 = -2;
        x8 = 2;
        yX2 = 4;
        x9 = -1;
        x10 = 1;

        if (direction == Direction.SOUTH) {
            x0 = -2;
            x1 = 2;
            z0 = -newSize;
            z1 = 0;

        } else if (direction == Direction.NORTH) {
            x0 = -2;
            x1 = 2;
            z0 = 0;
            z1 = newSize;

        } else if (direction == Direction.EAST) {
            x0 = -2;
            x1 = 2;
            z0 = -newSize;
            z1 = 0;

        } else if (direction == Direction.WEST) {
            x0 = -2;
            x1 = 2;
            z0 = 0;
            z1 = newSize;

        } else if (direction == Direction.DOWN) {
            y0 = 0;
            y1 = newSize;
            z0 = -2;
            z1 = 2;
            x0 = -2;
            x1 = 2;

            yX = 30;
            x7 = 55;
            x8 = 60;
            yX2 = 30;
            x9 = 30;
            x10 = 30;
        } else if (direction == Direction.UP) {
            y0 = -newSize;
            y1 = 0;
            z0 = -2;
            z1 = 2;
            x0 = -2;
            x1 = 2;

            yX = 30;
            x7 = 55;
            x8 = 60;
            yX2 = 30;
            x9 = 30;
            x10 = 30;
        }

        for (int y = y0; y <= y1; ++y) {
            for (int x = x0; x <= x1; ++x) {
                for (int z = z0; z <= z1; ++z) {
                    if (y == yX || y == yX2) {
                            if (x == x7 || x == x8) {
                                break;
                            }
                            if(yX2 == y){
                                if (x == x9 || x == x10) {
                                    break;
                                }
                            }

                    }
                    BlockPos targetPos;
                    if(direction == Direction.EAST || direction == Direction.WEST) {
                        targetPos = getBlockPos().offset(z, y, x);
                    }else{
                        targetPos = getBlockPos().offset(x, y, z);
                    }
                    if(!insidePos.contains(targetPos)) {
                        addPosIfValid(targetPos, outsidePos);
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("activated",activated);
        tag.putBoolean("foamStart",foamStart);
        if(tag.contains("direction")) {
            tag.putString("direction", direction.getName());
        }

        tag.putInt("size",size);
        tag.putString("shape",shape);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);

        activated = nbt.getBoolean("activated");
        activated = nbt.getBoolean("foamStart");
        if(direction != null) {
            direction = Direction.byName(nbt.getString("direction"));
        }
        size = nbt.getInt("size");
        shape = nbt.getString("shape");
    }
}
