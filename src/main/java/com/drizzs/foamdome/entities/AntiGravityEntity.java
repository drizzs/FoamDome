package com.drizzs.foamdome.entities;

import com.drizzs.foamdome.common.interfaces.Floatable;
import com.drizzs.foamdome.util.DomeRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class AntiGravityEntity extends Entity {

    private final Random rand = new Random();
    private static final Logger LOGGER = LogUtils.getLogger();
    private BlockState blockState;
    public int time;
    public boolean dropItem;
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int squishDamageMax;
    private float squishDamagePerDistance;
    @Nullable
    public CompoundTag blockData;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS;

    public int size = 0;
    public String shape = "";
    public ItemStack shapeStack = ItemStack.EMPTY;

    public AntiGravityEntity(EntityType<? extends Entity> et, Level level) {
        super(et, level);
        this.blockState = Blocks.AIR.defaultBlockState();
        this.dropItem = true;
    }

    public AntiGravityEntity(Level level, double x, double y, double z, BlockState state) {
        this(DomeRegistry.ANTI_GRAVITY_ENTITY.get(), level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
    }

    public static AntiGravityEntity raise(Level level, BlockPos pos, BlockState state) {
        AntiGravityEntity antiGrav = new AntiGravityEntity(level, (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, false) : state);
        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(antiGrav);
        return antiGrav;
    }

    public void setStartPos(BlockPos pos) {
        this.entityData.set(DATA_START_POS, pos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    public void setValues(String shape, int size,ItemStack shapeStack){
        this.shape = shape;
        this.size = size;
        this.shapeStack = shapeStack;
    }

    public void tick() {
        if (this.blockState.isAir()) {
            this.discard();
        } else {
            Block $$0 = this.blockState.getBlock();
            ++this.time;
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.04, 0.0));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos $$1 = this.blockPosition();
                double $$4 = this.getDeltaMovement().lengthSqr();
                if ($$4 > 1.0) {
                    BlockHitResult $$5 = this.level.clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), net.minecraft.world.level.ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                    if ($$5.getType() != HitResult.Type.MISS && this.level.getFluidState($$5.getBlockPos()).is(FluidTags.WATER)) {
                        $$1 = $$5.getBlockPos();
                    }
                }

                if (!this.onGround) {
                    if (!this.level.isClientSide && (this.time > 100 && ($$1.getY() <= this.level.getMaxBuildHeight() || $$1.getY() > this.level.getMinBuildHeight()) || this.time > 600)) {
                        if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.spawnAtLocation($$0);
                        }

                        this.discard();
                    }
                } else {
                    BlockState $$6 = this.level.getBlockState($$1);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, 0.5, 0.7));
                    if (!$$6.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean $$7 = $$6.canBeReplaced(new DirectionalPlaceContext(this.level, $$1, Direction.UP, ItemStack.EMPTY, Direction.DOWN));
                            boolean $$9 = this.blockState.canSurvive(this.level, $$1);
                            if ($$7 && $$9) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState($$1).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, true);
                                }

                                if (this.level.setBlock($$1, this.blockState, 3)) {
                                    ((ServerLevel)this.level).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket($$1, this.level.getBlockState($$1)));
                                    this.discard();
                                    if ($$0 instanceof Floatable) {
                                        ((Floatable)$$0).onFloatStop(this.level, $$1, this.blockState, $$6, this);
                                    }

                                    if (this.blockData != null && this.blockState.hasBlockEntity()) {
                                        BlockEntity $$10 = this.level.getBlockEntity($$1);
                                        if ($$10 != null) {
                                            CompoundTag $$11 = $$10.saveWithoutMetadata();

                                            for (String $$12 : this.blockData.getAllKeys()) {
                                                $$11.put($$12, Objects.requireNonNull(this.blockData.get($$12)).copy());
                                            }

                                            try {
                                                $$10.load($$11);
                                            } catch (Exception var15) {
                                              LOGGER.error("Failed to load block entity from falling block", var15);
                                            }

                                            $$10.setChanged();
                                        }
                                    }
                                } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall($$0, $$1);
                                    this.spawnAtLocation($$0);
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.callOnBrokenAfterFall($$0, $$1);
                                    this.spawnAtLocation($$0);
                                }
                            }
                        } else {
                            this.discard();
                            this.callOnBrokenAfterFall($$0, $$1);
                        }
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }

        int fallingTime;
        int random = rand.nextInt(5);
        if (random == 0) {
            fallingTime = 45;
        } else if (random == 1) {
            fallingTime = 25;
        }else if (random == 2) {
            fallingTime = 35;
        }else if (random == 3) {
            fallingTime = 40;
        }else {
            fallingTime = 30;
        }
        if (time >= fallingTime || onGround) {
            Block $$0 = this.blockState.getBlock();
            if ($$0 instanceof Floatable) {
                ((Floatable)$$0).onFloatStop(this.level, this.blockPosition(), this.blockState, this.level.getBlockState(this.blockPosition()), this);
            }
        }
    }

    public void callOnBrokenAfterFall(Block block, BlockPos pos) {
        if (block instanceof Floatable) {
            ((Floatable)block).onBrokenAfterFloatStop(this.level, pos, this);
        }

    }

    protected void addAdditionalSaveData(CompoundTag p_31973_) {
        p_31973_.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        p_31973_.putInt("Time", this.time);
        p_31973_.putBoolean("DropItem", this.dropItem);
        p_31973_.putBoolean("HurtEntities", this.hurtEntities);
        p_31973_.putFloat("SquishHurtAmount", this.squishDamagePerDistance);
        p_31973_.putInt("SquishHurtMax", this.squishDamageMax);
        if (this.blockData != null) {
            p_31973_.put("TileEntityData", this.blockData);
        }

    }

    protected void readAdditionalSaveData(CompoundTag p_31964_) {
        this.blockState = NbtUtils.readBlockState(p_31964_.getCompound("BlockState"));
        this.time = p_31964_.getInt("Time");
        if (p_31964_.contains("HurtEntities", 99)) {
            this.hurtEntities = p_31964_.getBoolean("HurtEntities");
            this.squishDamagePerDistance = p_31964_.getFloat("SquishHurtAmount");
            this.squishDamageMax = p_31964_.getInt("SquishHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (p_31964_.contains("DropItem", 99)) {
            this.dropItem = p_31964_.getBoolean("DropItem");
        }

        if (p_31964_.contains("TileEntityData", 10)) {
            this.blockData = p_31964_.getCompound("TileEntityData");
        }

        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }

    }

    public void setHurtsEntities(float p_149657_, int p_149658_) {
        this.hurtEntities = true;
        this.squishDamagePerDistance = p_149657_;
        this.squishDamageMax = p_149658_;
    }

    public boolean displayFireAnimation() {
        return false;
    }

    public void fillCrashReportCategory(CrashReportCategory p_31962_) {
        super.fillCrashReportCategory(p_31962_);
        p_31962_.setDetail("Immitating BlockState", this.blockState.toString());
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public void recreateFromPacket(ClientboundAddEntityPacket p_149654_) {
        super.recreateFromPacket(p_149654_);
        this.blockState = Block.stateById(p_149654_.getData());
        this.blocksBuilding = true;
        double $$1 = p_149654_.getX();
        double $$2 = p_149654_.getY();
        double $$3 = p_149654_.getZ();
        this.setPos($$1, $$2, $$3);
        this.setStartPos(this.blockPosition());
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    static {
        DATA_START_POS = SynchedEntityData.defineId(AntiGravityEntity.class, EntityDataSerializers.BLOCK_POS);
    }
}
