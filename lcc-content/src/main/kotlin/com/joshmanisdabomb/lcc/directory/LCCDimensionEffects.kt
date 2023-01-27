package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.world.render.dimension.RainbowDimensionEffects
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry
import net.minecraft.client.render.DimensionEffects
import net.minecraft.util.Identifier


object LCCDimensionEffects : BasicDirectory<DimensionEffects, Identifier?>() {

    val rainbow by entry(::initialiser) { RainbowDimensionEffects() }

    private fun <M : DimensionEffects> initialiser(input: M, context: DirectoryContext<Identifier?>, parameters: Unit) = input

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String): Identifier? = null

    override fun afterInitAll(initialised: List<DirectoryEntry<out DimensionEffects, out DimensionEffects>>, filter: (context: DirectoryContext<Identifier?>) -> Boolean) {
        initialised.forEach {
            DimensionRenderingRegistry.registerDimensionEffects(it.properties ?: it.id, it.entry)
        }
    }

}