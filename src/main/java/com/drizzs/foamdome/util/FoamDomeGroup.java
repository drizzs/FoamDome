package com.drizzs.foamdome.util;

import com.drizzs.foamdome.domeregistry.DomeItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FoamDomeGroup extends ItemGroup
{

    public static final FoamDomeGroup instance = new FoamDomeGroup(ItemGroup.GROUPS.length, "foamdome");

    private FoamDomeGroup(int index, String label)
    {
        super(index, label);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(DomeItems.foamicon);
    }

}
