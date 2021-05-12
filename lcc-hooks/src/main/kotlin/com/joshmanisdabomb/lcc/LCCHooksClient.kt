package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.networking.TraitSpawnPacket
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object LCCHooksClient : ClientModInitializer {

    override fun onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(TraitSpawnPacket.id, TraitSpawnPacket.Handler)
    }

}