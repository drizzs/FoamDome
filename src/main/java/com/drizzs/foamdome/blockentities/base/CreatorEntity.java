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
    public Direction direction = Direction.WEST;
    protected int size = 0;
    protected String shape = "";

    public CreatorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, 2,1, pos, state);
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
        if(requiresUpdate){
            requiresUpdate = false;
            updateEntity();
        }
        if(activated){
            if (outsidePos.isEmpty() && insidePos.isEmpty() && !shape.isEmpty() && size > 0) {
                getShapePos();
            }
            if (!insidePos.isEmpty()) {
                BlockPos pos = insidePos.get(0);
                assert level != null;
                level.setBlock(pos, getFoam2(),1);
                insidePos.remove(0);
            }
            if (!outsidePos.isEmpty()) {
                BlockPos pos = outsidePos.get(0);
                assert level != null;
                level.setBlock(pos, getFoam(),1);
                outsidePos.remove(0);
            }
            if(outsidePos.isEmpty() && insidePos.isEmpty()){
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
                        FoamStart();
                    }
            });
        }
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

        if (direction == Direction.SOUTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -newSize; z < 0; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < newSize; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -newSize; z < 0; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.WEST) {
            for (int y = 0; y < 3; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = 1; z < newSize; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 1; y < newSize; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = -newSize; y < 1; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        addPosIfValid(targetPos, insidePos);
                    }
                }
            }
        }

        newSize = (size+4);
        if (direction == Direction.SOUTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -newSize; z < 1; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if(!insidePos.contains(targetPos)) {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        } else if (direction == Direction.NORTH) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z < newSize; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if(!insidePos.contains(targetPos)) {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        } else if (direction == Direction.EAST) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -newSize; z < 1; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if(!insidePos.contains(targetPos)) {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        } else if (direction == Direction.WEST) {
            for (int y = -1; y < 4; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = 0; z < newSize; ++z) {
                        if (y == 3) {
                            if (x == -2 || x == 2) {
                                break;
                            }
                        }
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if(!insidePos.contains(targetPos)) {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = 0; y < newSize; ++y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if(!insidePos.contains(targetPos)) {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        } else if (direction == Direction.UP) {
            for (int y = 0; y > -newSize; --y) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        BlockPos targetPos = getBlockPos().offset(x, y, z);
                        if(!insidePos.contains(targetPos)) {
                            addPosIfValid(targetPos, outsidePos);
                        }
                    }
                }
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putBoolean("activated",activated);
        tag.putBoolean("foamStart",foamStart);
        tag.putString("direction", direction.getName());

        tag.putInt("size",size);
        tag.putString("shape",shape);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);

        activated = nbt.getBoolean("activated");
        activated = nbt.getBoolean("foamStart");
        direction = Direction.byName(nbt.getString("direction"));
        size = nbt.getInt("size");
        shape = nbt.getString("shape");
    }


}
