package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.world.render.sky.RainbowSkyRenderer
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.World


object LCCSkyRenderers : BasicDirectory<DimensionRenderingRegistry.SkyRenderer, RegistryKey<World>>() {

    val rainbow by entry(::initialiser) { RainbowSkyRenderer() }
        .setProperties(RegistryKey.of(Registry.WORLD_KEY, LCC.id("rainbow")))

    private fun <M : DimensionRenderingRegistry.SkyRenderer> initialiser(input: M, context: DirectoryContext<RegistryKey<World>>, parameters: Unit) = input

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String): RegistryKey<World> = error("No default properties available for this directory.")

    override fun afterInitAll(initialised: List<DirectoryEntry<out DimensionRenderingRegistry.SkyRenderer, out DimensionRenderingRegistry.SkyRenderer>>, filter: (context: DirectoryContext<RegistryKey<World>>) -> Boolean) {
        initialised.forEach {
            DimensionRenderingRegistry.registerSkyRenderer(it.properties, it.entry)
        }
    }

}