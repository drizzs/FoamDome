package com.drizzs.foamdome.common.network;

import com.drizzs.foamdome.FoamDome;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FoamDome.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private PacketHandler() {
    }

    public static void init() {
        int index = 0;
        INSTANCE.messageBuilder(StartFoamPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(StartFoamPacket::encode).decoder(StartFoamPacket::new)
                .consumer(StartFoamPacket::handle).add();
        FoamDome.LOGGER.info("Registered {} packets for mod '{}'", index, FoamDome.MOD_ID);
    }

}
