package com.drizzs.foamdome.client.screen;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.common.containers.base.FoamContainer;
import com.drizzs.foamdome.common.network.PacketHandler;
import com.drizzs.foamdome.common.network.StartFoamPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

public class FoamScreen extends AbstractContainerScreen<FoamContainer> {

    private int titleDistance;

    public FoamScreen(FoamContainer container, Inventory inventory, Component title) {
        super(container, inventory,  title);
        this.leftPos = 0;
        this.topPos = 0;
        if(title.getString().length() == 16){
            titleDistance = 45;
        }if(title.getString().length() == 17){
            titleDistance = 45;
        }if(title.getString().length() == 22){
            titleDistance = 31;
        }if(title.getString().length() == 20){
            titleDistance = 36;
        }else{
            titleDistance = 40;
        }
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
    //    this.font.draw(stack, this.title, this.leftPos + titleDistance, this.topPos + 5, 0x404040);
      //  this.font.draw(stack, this.playerInventoryTitle, this.leftPos + 8, this.topPos + 75, 0x404040);
    }

    @Override
    protected void renderBg(@NotNull PoseStack stack, float mouseX, int mouseY, int partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        RenderSystem.setShaderTexture(0,new ResourceLocation(FoamDome.MOD_ID,"textures/gui/foam_gui.png"));

        blit(stack,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new ExtendedButton(leftPos + 81 ,topPos + 50,15,15, new TextComponent(""), btn -> startFoam()));
    }

    protected void startFoam(){
        PacketHandler.INSTANCE.sendToServer(new StartFoamPacket());
    }



}
