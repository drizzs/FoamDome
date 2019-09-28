package com.drizzs.foamdome.domeregistry;

import com.drizzs.foamdome.FoamDome;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FoamDome.MOD_ID ,bus= Mod.EventBusSubscriber.Bus.MOD)
public class DomeItems
{
    public static Item foamicon;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        foamicon = registerItem(new Item(new Item.Properties()), "foamicon");

    }

    public static Item registerItem(Item item, String name)
    {
        item.setRegistryName(name);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }



}
