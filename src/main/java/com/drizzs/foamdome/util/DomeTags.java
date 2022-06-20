package com.drizzs.foamdome.util;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class DomeTags {

    public static final TagKey<Item> SIZE = ItemTags.create(new ResourceLocation("foamdome:sizecartridge"));
    public static final TagKey<Item> SHAPE = ItemTags.create(new ResourceLocation("foamdome:shapecartridge"));
    public static final TagKey<Block> UNDERWATER = BlockTags.create(new ResourceLocation("foamdome:underwater"));
    public static final TagKey<Block> GRAVITYDOME = BlockTags.create(new ResourceLocation("foamdome:gravitydome"));
    public static final TagKey<Block> ACID = BlockTags.create(new ResourceLocation("foamdome:acid"));
    public static final TagKey<Block> SKY = BlockTags.create(new ResourceLocation("foamdome:sky"));

}
