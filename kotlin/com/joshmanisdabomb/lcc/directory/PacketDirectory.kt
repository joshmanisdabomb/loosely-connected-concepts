package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.PacketRegistry

abstract class PacketDirectory : ThingDirectory<PacketConsumer, Unit>() {

    protected abstract val _registry: PacketRegistry

    override fun registerAll(things: Map<String, PacketConsumer>, properties: Map<String, Unit>) {
        for ((k, v) in things) {
            _registry.register(LCC.id(k), v)
        }
    }

    fun <P : PacketDirectory> P.id(getter: P.() -> PacketConsumer) = LCC.id(this[getter(this)] ?: error("Unable to resolve name for packet consumer."))

}