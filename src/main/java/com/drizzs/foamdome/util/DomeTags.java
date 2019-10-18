package com.drizzs.foamdome.util;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import static com.drizzs.foamdome.FoamDome.MOD_ID;

public class DomeTags {

    public static final Tag<Block> UNDERWATER = new BlockTags.Wrapper(new ResourceLocation("foamdome:underwater"));
    public static final Tag<Block> GRAVITYDOME = new BlockTags.Wrapper(new ResourceLocation("foamdome:gravitydome"));

}
