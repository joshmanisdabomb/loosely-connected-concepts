package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.PacketRegistry
import net.minecraft.util.Identifier

abstract class PacketDirectory : ThingDirectory<PacketConsumer, Unit>() {

    protected abstract val _registry: PacketRegistry

    protected abstract fun id(path: String): Identifier

    override fun registerAll(things: Map<String, PacketConsumer>, properties: Map<String, Unit>) {
        for ((k, v) in things) {
            _registry.register(id(k), v)
        }
    }

    fun <P : PacketDirectory> P.id(getter: P.() -> PacketConsumer) = id(this[getter(this)] ?: error("Unable to resolve name for packet consumer."))

}