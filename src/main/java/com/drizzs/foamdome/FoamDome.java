package com.drizzs.foamdome;

import com.drizzs.foamdome.util.DomeConfigs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.drizzs.foamdome.FoamDome.MOD_ID;


@Mod(MOD_ID)
public class FoamDome
{
    public static final String MOD_ID = "foamdome";

    public static FoamDome instance;

    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public FoamDome() {


        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, DomeConfigs.COMMON_SPEC);

        MinecraftForge.EVENT_BUS.register(this);
    }

}
