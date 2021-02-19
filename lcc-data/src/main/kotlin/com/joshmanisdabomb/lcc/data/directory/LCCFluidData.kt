package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.FluidDataContainer
import com.joshmanisdabomb.lcc.data.factory.tag.FluidTagFactory
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCFluids

object LCCFluidData : BasicDirectory<FluidDataContainer, Unit>() {

    val oil_still by entry(::initialiser) { FluidDataContainer().add(FluidTagFactory()) }

    fun initialiser(input: FluidDataContainer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun <V : FluidDataContainer> afterInit(initialised: V, entry: DirectoryEntry<out FluidDataContainer, out V>, parameters: Unit) {
        all.forEach { (k, v) -> v.init(k, LCCFluids[k]) }

        val missing = LCCFluids.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCFluids[it].name; defaults().init(key, it) }
    }

    fun defaults() = FluidDataContainer()

}