package com.drizzs.foamdome.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
    public void appendHoverText(@NotNull ItemStack item, @org.jetbrains.annotations.Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("Creates a dome of radius " + domeSize + " or a Tunnel with a length of " + (domeSize+4) +" blocks" ).withStyle(ChatFormatting.LIGHT_PURPLE));
    }

}
