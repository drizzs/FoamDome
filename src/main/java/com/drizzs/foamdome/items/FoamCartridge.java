package com.drizzs.foamdome.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class FoamCartridge extends Item {

    private final int domeSize;

    public FoamCartridge(Properties properties, int domeSize) {
        super(properties);
        this.domeSize = domeSize;
    }

    public int getDomeSize() {
        return domeSize;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("Creates a dome of radius " + domeSize + " or a Tunnel with a length of " + (domeSize+4) +" blocks" ,1).applyTextStyle(TextFormatting.LIGHT_PURPLE));
    }
}
