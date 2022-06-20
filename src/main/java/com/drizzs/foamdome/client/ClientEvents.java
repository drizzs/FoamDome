package com.drizzs.foamdome.client;

import com.drizzs.foamdome.client.screen.FoamScreen;
import com.drizzs.foamdome.util.DomeRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.drizzs.foamdome.FoamDome.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(DomeRegistry.FOAM_CONTAINER.get(), FoamScreen::new);
    }

}
