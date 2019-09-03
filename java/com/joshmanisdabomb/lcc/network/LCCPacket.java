package com.joshmanisdabomb.lcc.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface LCCPacket {

    @OnlyIn(Dist.CLIENT)
    default void handleClient() {

    }

    default void handleLogicalServer() {

    }

}