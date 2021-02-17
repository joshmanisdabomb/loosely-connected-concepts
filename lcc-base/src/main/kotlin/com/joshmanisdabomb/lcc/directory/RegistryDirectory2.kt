package com.joshmanisdabomb.lcc.directory

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

interface RegistryDirectory2<T, P, C> {

    val registry: Registry<T>

    fun id(name: String): Identifier

    fun <R : T> initialiser(input: R, context: AdvancedDirectory.DirectoryContext<P, C>): R {
        Registry.register(registry, id(context.name), input)
        return input
    }

}