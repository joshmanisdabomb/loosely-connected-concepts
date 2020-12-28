package com.joshmanisdabomb.lcc.directory

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

abstract class RegistryDirectory<V, P> : ThingDirectory<V, P>() {

    protected abstract val _registry: Registry<V>

    protected abstract fun id(path: String): Identifier

    override fun registerAll(things: Map<String, V>, properties: Map<String, P>) {
        things.forEach { (k, v) -> register(k, v, properties[k] ?: error("No property to go along with registering $k.")) }
    }

    protected open fun register(key: String, thing: V, properties: P): RegistryKey<V> {
        Registry.register(_registry, id(key), thing)
        return _registry.getKey(thing).orElseThrow(::RuntimeException)
    }

    fun getRegistryKey(thing: V): RegistryKey<V>? = _registry.getKey(thing).orElse(null)

}