package com.drizzs.foamdome.items;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static com.drizzs.foamdome.util.DomeRegistry.*;


public class DomeItems extends BlockItem {

    public Block block;

    public DomeItems(Block block, Properties prop) {
        super(block, prop);
        this.block = block;
    }



    //Adds Tooltips
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (this.block.equals(BASIC_DOME_CREATOR.get())) {
            tooltip.add(new TranslatableComponent("Creates Underwater Obsidian Domes").withStyle(ChatFormatting.LIGHT_PURPLE));
        } else if (this.block.equals(GLASS_DOME_CREATOR.get())) {
            tooltip.add(new TranslatableComponent("Creates Underwater Glass Domes").withStyle(ChatFormatting.LIGHT_PURPLE));
        } else if (this.block.equals(GRAVITY_DOME_CREATOR.get())) {
            tooltip.add(new TranslatableComponent("Creates Glass Domes in the Multiple Scenarios, Affected by Gravity on Activated").withStyle(ChatFormatting.LIGHT_PURPLE));
        }else if (this.block.equals(ACID_DOME_CREATOR.get())) {
            tooltip.add(new TranslatableComponent("Eats Away at the Solid Objects in an Area to Create a Dome").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }
}
