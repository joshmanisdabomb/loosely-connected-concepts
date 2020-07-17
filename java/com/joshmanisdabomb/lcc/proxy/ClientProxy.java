package com.joshmanisdabomb.lcc.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ClientProxy extends Proxy {

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Container getClientOpenContainer() { return Minecraft.getInstance().player.openContainer; }

    @Override
    public void rainbowPortalRender(Entity entity, int portalTimer) {
        if (entity != Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getRenderViewEntity()) return;
        if (portalTimer < 0) {
            Minecraft.getInstance().gameRenderer.stopUseShader();
            return;
        }
        if (Minecraft.getInstance().gameRenderer.getShaderGroup() == null) {
            Minecraft.getInstance().gameRenderer.loadShader(new ResourceLocation("shaders/post/wobble.json"));
        }
        Minecraft.getInstance().gameRenderer.getShaderGroup().listShaders.get(0).getShaderManager().getShaderUniform("WobbleAmount").set(0.0F);
        Minecraft.getInstance().gameRenderer.getShaderGroup().listShaders.get(0).getShaderManager().getShaderUniform("Frequency").set(portalTimer / 3F, portalTimer);
    }

    @Override
    public void refreshWorld() {
        Minecraft.getInstance().worldRenderer.loadRenderers();
    }

}
