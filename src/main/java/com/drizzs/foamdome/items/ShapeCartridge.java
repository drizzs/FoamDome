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
import java.util.Locale;

public class ShapeCartridge extends Item {

    private final String shape;

    public ShapeCartridge(Properties properties, String shape) {
        super(properties);
        this.shape = shape;
    }

    public String getShape() {
        return shape;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack item, @org.jetbrains.annotations.Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("Cartridge for Foam Creator of shape: " + shape.toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.LIGHT_PURPLE));
    }

}
