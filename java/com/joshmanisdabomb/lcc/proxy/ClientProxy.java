package com.joshmanisdabomb.lcc.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
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
    public void refreshWorld() {
        Minecraft.getInstance().worldRenderer.loadRenderers();
    }

}
