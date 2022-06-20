package com.drizzs.foamdome;

import com.drizzs.foamdome.util.DomeRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.drizzs.foamdome.FoamDome.MOD_ID;


@Mod(MOD_ID)
public class FoamDome
{
    public static final String MOD_ID = "foamdome";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public FoamDome() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        DomeRegistry.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }


}
