package com.drizzs.foamdome.util;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.domeblocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FoamDome.MOD_ID ,bus= Mod.EventBusSubscriber.Bus.MOD)
public class DomeRegistry
{

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(30.0F, 600.0F).sound(SoundType.STONE)).setRegistryName("notfoam"));
        event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(30.0F, 600.0F).sound(SoundType.STONE)).setRegistryName("alsonotfoam"));
        event.getRegistry().register(new Foam(Block.Properties.create(Material.SNOW).noDrops().tickRandomly().hardnessAndResistance(0).sound(SoundType.SNOW)).setRegistryName("foam"));
        event.getRegistry().register(new GlassFoam(Block.Properties.create(Material.SNOW).noDrops().tickRandomly().hardnessAndResistance(0).sound(SoundType.SNOW)).setRegistryName("glassfoam"));
        event.getRegistry().register(new GravityFoam(Block.Properties.create(Material.SNOW).noDrops().tickRandomly().hardnessAndResistance(0).sound(SoundType.SNOW)).setRegistryName("gravityfoam"));
        event.getRegistry().register(new GlassDomeCreator(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(6).sound(SoundType.METAL)).setRegistryName("glassdomecreator"));
        event.getRegistry().register(new DomeCreator(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(6).sound(SoundType.METAL)).setRegistryName("domecreator"));
        event.getRegistry().register(new GravityDomeCreator(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(6).sound(SoundType.METAL)).setRegistryName("gravitydomecreator"));
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new Item(new Item.Properties()).setRegistryName("foamicon"));
        event.getRegistry().register(new BlockItem(DomeLib.domecreator ,new Item.Properties().group(DomeGroup.instance)).setRegistryName("domecreator"));
        event.getRegistry().register(new BlockItem(DomeLib.glassdomecreator ,new Item.Properties().group(DomeGroup.instance)).setRegistryName("glassdomecreator"));
        event.getRegistry().register(new BlockItem(DomeLib.gravitydomecreator ,new Item.Properties().group(DomeGroup.instance)).setRegistryName("gravitydomecreator"));
    }

}
