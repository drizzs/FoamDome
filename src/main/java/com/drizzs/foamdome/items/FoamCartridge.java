package com.drizzs.foamdome.items;

import net.minecraft.item.Item;

public class FoamCartridge extends Item {

    private final int domeSize;

    public FoamCartridge(Properties properties, int domeSize) {
        super(properties);
        this.domeSize = domeSize;
    }

    public int getDomeSize() {
        return domeSize;
    }
}
