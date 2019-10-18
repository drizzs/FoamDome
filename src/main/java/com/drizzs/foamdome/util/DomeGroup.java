package com.drizzs.foamdome.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DomeGroup extends ItemGroup
{

    public static final DomeGroup instance = new DomeGroup(ItemGroup.GROUPS.length, "foamdome");

    private DomeGroup(int index, String label)
    {
        super(index, label);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(DomeLib.foamicon);
    }

}
