package com.drizzs.foamdome.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static com.drizzs.foamdome.util.DomeRegistry.FOAM_ICON;

public class DomeGroup extends ItemGroup
{

    public static final DomeGroup DOME_GROUP = new DomeGroup(ItemGroup.GROUPS.length, "foamdome");

    private DomeGroup(int index, String label)
    {
        super(index, label);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(FOAM_ICON.get());
    }

}
