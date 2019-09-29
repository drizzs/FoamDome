package com.drizzs.foamdome.domeregistry;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.domeblocks.DomeCreator;
import com.drizzs.foamdome.domeblocks.Foam;
import com.drizzs.foamdome.domeblocks.NotFoam;
import com.drizzs.foamdome.util.FoamDomeGroup;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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


    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        domecreator = registerBlock(new DomeCreator((Block.Properties.create(Material.IRON).hardnessAndResistance(6).sound(SoundType.METAL))), "domecreator");
        foam = registerBlock(new Foam((Block.Properties.create(Material.SNOW).tickRandomly().hardnessAndResistance(1.9F).sound(SoundType.SNOW))), "foam");
        notfoam = registerBlock(new NotFoam((Block.Properties.create(Material.ROCK).hardnessAndResistance(15).sound(SoundType.STONE))), "notfoam");

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
