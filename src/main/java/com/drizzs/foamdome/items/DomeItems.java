package com.drizzs.foamdome.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

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
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (this.block.equals(BASIC_DOME_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Creates Underwater Obsidian Domes", 2).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        } else if (this.block.equals(BASIC_TUNNEL_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Creates Underwater Obsidian Tunnels", 2).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        } else if (this.block.equals(GLASS_DOME_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Creates Underwater Glass Domes", 2).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        } else if (this.block.equals(GLASS_TUNNEL_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Creates Underwater Glass Tunnels", 2).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        } else if (this.block.equals(GRAVITY_DOME_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Creates Glass Domes in the Multiple Scenarios, Affected by Gravity on Activated", 2).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        }else if (this.block.equals(GRAVITY_TUNNEL_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Creates Glass Tunnels in Multiple Scenarios", 1).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        }else if (this.block.equals(ACID_DOME_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Eats Away at the Solid Objects in an Area to Create a Dome", 1).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        }else if (this.block.equals(ACID_TUNNEL_CREATOR.get())) {
            tooltip.add(new TranslationTextComponent("Eats Away at the Solid Objects in an Area to Create a Tunnel", 1).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        }
    }
}
