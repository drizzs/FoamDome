package com.drizzs.foamdome.blocks.dome;

import com.drizzs.foamdome.blocks.base.InventoryBlock;
import com.drizzs.foamdome.util.DomeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AcidDomeCreatorBlock extends InventoryBlock {
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return DomeRegistry.ACID_DOME_TILE.get().create(pos,state);
    }

    @Override
    protected String getType() {
        return "acid_foam_container";
    }

}
