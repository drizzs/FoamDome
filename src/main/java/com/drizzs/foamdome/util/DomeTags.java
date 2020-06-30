package com.drizzs.foamdome.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

import static net.minecraft.tags.ItemTags.makeWrapperTag;


public class DomeTags {

    public static final ITag.INamedTag<Item> CARTRIDGE = makeWrapperTag("foamdome:foamcartridge");

    public static final ITag.INamedTag<Block> UNDERWATER = BlockTags.makeWrapperTag("foamdome:underwater");
    public static final ITag.INamedTag<Block> GRAVITYDOME = BlockTags.makeWrapperTag("foamdome:gravitydome");
    public static final ITag.INamedTag<Block> ACID = BlockTags.makeWrapperTag("foamdome:acid");


}
