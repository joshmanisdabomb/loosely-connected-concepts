package com.joshmanisdabomb.lcc.abstracts.computing.session

import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

interface ComputingSessionViewContextProvider {

    fun getView(player: ServerPlayerEntity, world: ServerWorld): ComputingSessionViewContext

    companion object {
        fun getCurrentView(player: ServerPlayerEntity, world: ServerWorld) = (player.currentScreenHandler as? ComputingSessionViewContextProvider)?.getView(player, world)
    }

}
