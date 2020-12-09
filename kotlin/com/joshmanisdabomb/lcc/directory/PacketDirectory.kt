package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.PacketRegistry
import net.minecraft.util.Identifier
import kotlin.reflect.KProperty0

abstract class PacketDirectory : ThingDirectory<PacketConsumer, Unit>() {

    protected abstract val _registry: PacketRegistry

    val KProperty0<PacketConsumer>.id: Identifier get() = LCC.id(name)

    override fun registerAll(things: Map<String, PacketConsumer>, properties: Map<String, Unit>) {
        for ((k, v) in things) {
            _registry.register(LCC.id(k), v)
        }
    }

}