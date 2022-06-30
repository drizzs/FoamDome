package com.drizzs.foamdome.blocks.base;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import com.drizzs.foamdome.entities.GravityEntity;
import com.drizzs.foamdome.common.containers.base.FoamContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class InventoryBlock  extends Block implements EntityBlock, Fallable {

    private ItemStack sizeItem = ItemStack.EMPTY;
    private ItemStack shapeItem = ItemStack.EMPTY;

    public InventoryBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5,6).requiresCorrectToolForDrops().sound(SoundType.METAL));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, BlockHitResult trace) {
        Direction direction = trace.getDirection();
        BlockEntity entity = world.getBlockEntity(pos);

        InteractionResult result = InteractionResult.PASS;


        if(!world.isClientSide() && entity instanceof CreatorEntity ie){
            if(!ie.activated) {
                ie.direction = direction;
                MenuProvider container = new SimpleMenuProvider(FoamContainer.getServerContainer(ie, pos),
                        new TranslatableComponent("container." + FoamDome.MOD_ID + "." + getType()));
                NetworkHooks.openGui((ServerPlayer) player, container, pos);
                result = InteractionResult.SUCCESS;
            }
        }

        return result;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((CreatorEntity) blockEntity).foamMovieMagic();
    }

    protected String getType(){
        return "basic_foam_container";
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof CreatorEntity tile){
            tile.getHandler().ifPresent(inventory -> {
                sizeItem = inventory.extractItem(0,1,false);
                shapeItem = inventory.extractItem(1,1,false);
                if(!sizeItem.isEmpty()) {
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, sizeItem));
                }
                if(!shapeItem.isEmpty()) {
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, shapeItem));
                }
            });
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

}
