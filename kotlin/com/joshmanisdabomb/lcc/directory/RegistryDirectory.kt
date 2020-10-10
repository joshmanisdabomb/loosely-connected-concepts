package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.minecraft.util.registry.Registry

abstract class RegistryDirectory<V, P> : ThingDirectory<V, P>() {

    protected abstract val _registry: Registry<V>

    override fun registerAll(things: Map<String, V>, properties: Map<String, P>) {
        things.forEach { k, v -> Registry.register(_registry, LCC.id(k), v) }
    }

}