package com.joshmanisdabomb.lcc.directory

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

interface RegistryDirectory<T, P, C> {

    val registry: Registry<T>

    fun regId(name: String): Identifier

    fun <R : T> initialiser(input: R, context: AdvancedDirectory<*, *, *, *>.DirectoryContext<P>, parameters: C): R {
        Registry.register(registry, context.id, input)
        return input
    }

    private fun cast() = this as AdvancedDirectory<*, T, P, C>

    fun getRegistryKey(entry: T) = registry.getKey(entry).orElseGet { error("Could not find the given entry in the registry.") }
    fun <V : T> getRegistryKey(entry: AdvancedDirectory<*, T, P, C>.DirectoryEntry<*, V>) = registry.getKey(entry.entry ?: error("This entry is null.")).orElseGet { error("Could not find the given entry in the registry.") }

}