package com.drizzs.foamdome.util;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DomeGroup extends CreativeModeTab
{

    public static final DomeGroup DOME_GROUP = new DomeGroup(CreativeModeTab.getGroupCountSafe(), "foamdome");

    private DomeGroup(int index, String label)
    {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(DomeRegistry.FOAM_ICON.get());
    }

}
