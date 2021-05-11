package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.container.FluidDataContainer
import com.joshmanisdabomb.lcc.data.factory.tag.FluidTagFactory
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCFluids

object LCCFluidData : BasicDirectory<FluidDataContainer, Unit>() {

    val oil_still by entry(::initialiser) { data().add(FluidTagFactory()) }

    fun initialiser(input: FluidDataContainer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out FluidDataContainer, out FluidDataContainer>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.init(it.name, LCCFluids.getOrNull(it.name)) }

        val missing = LCCFluids.all.values.minus(initialised.flatMap { it.entry.affects })
        missing.forEach { val key = LCCFluids[it].name; defaults().init(key, it) }
    }

    private fun data() = FluidDataContainer(LCCData)
    private fun defaults() = data()

}