package com.drizzs.foamdome.common.interfaces;

import com.drizzs.foamdome.entities.AntiGravityEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public interface Floatable {

    default void onFloatStop(Level level, BlockPos pos, BlockState state, BlockState state2, AntiGravityEntity entity) {
    }

    default void onBrokenAfterFloatStop(Level level, BlockPos pos, AntiGravityEntity entity) {
    }

    default DamageSource getRaiseDamageSource() {
        return new DamageSource("You got Squashed");
    }

    default Predicate<Entity> getHurtsEntitySelector() {
        return EntitySelector.NO_SPECTATORS;
    }

}
