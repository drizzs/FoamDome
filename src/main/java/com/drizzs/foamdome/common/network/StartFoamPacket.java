package com.drizzs.foamdome.common.network;

import com.drizzs.foamdome.blockentities.base.CreatorEntity;
import com.drizzs.foamdome.common.containers.base.FoamContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StartFoamPacket {

    public StartFoamPacket(){
    }

    public StartFoamPacket(FriendlyByteBuf buffer) {

    }

    public void encode(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
            ctx.get().enqueueWork(() -> {
                Player player = ctx.get().getSender();

                if(player.containerMenu instanceof FoamContainer fc){
                    assert player != null;
                    BlockEntity te = player.level.getBlockEntity(fc.getPos());
                    if(te instanceof CreatorEntity ce){
                        ce.InitializeCreation();
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }

}
