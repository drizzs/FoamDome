package com.drizzs.foamdome.domeregistry;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.domeblocks.*;
import com.drizzs.foamdome.util.FoamDomeGroup;
import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FoamDome.MOD_ID ,bus= Mod.EventBusSubscriber.Bus.MOD)
public class DomeBlocks
{

    public static Block domecreator;
    public static Block foam;
    public static Block notfoam;
    public static Block glassdomecreator;
    public static Block glassfoam;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        domecreator = registerBlock(new DomeCreator((Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(6).sound(SoundType.METAL))), "domecreator");
        foam = registerBlock(new Foam((Block.Properties.create(Material.SNOW).noDrops().tickRandomly().hardnessAndResistance(0).sound(SoundType.SNOW))), "foam");
        notfoam = registerBlock(new NotFoam((Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(30.0F, 600.0F).sound(SoundType.STONE))), "notfoam");
        glassdomecreator = registerBlock(new GlassDomeCreator((Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(6).sound(SoundType.METAL))), "glassdomecreator");
        glassfoam = registerBlock(new GlassFoam((Block.Properties.create(Material.SNOW).noDrops().tickRandomly().hardnessAndResistance(0).sound(SoundType.SNOW))), "glassfoam");

    }

    public static Block registerBlock(Block block, String name)
    {
        BlockItem itemBlock = new BlockItem(block, new Item.Properties().group(FoamDomeGroup.instance));
        block.setRegistryName(name);
        itemBlock.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemBlock);
        return block;
    }

}
