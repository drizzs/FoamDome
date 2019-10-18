package com.drizzs.foamdome.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;

public class DomeLib {
    @ObjectHolder("foamdome:domecreator")
    public static Block domecreator;

    @ObjectHolder("foamdome:foam")
    public static Block foam;

    @ObjectHolder("foamdome:notfoam")
    public static Block notfoam;

    @ObjectHolder("foamdome:glassdomecreator")
    public static Block glassdomecreator;

    @ObjectHolder("foamdome:glassfoam")
    public static Block glassfoam;

    @ObjectHolder("foamdome:gravityfoam")
    public static Block gravityfoam;

    @ObjectHolder("foamdome:gravitydomecreator")
    public static Block gravitydomecreator;

    @ObjectHolder("foamdome:foamicon")
    public static Item foamicon;

}
