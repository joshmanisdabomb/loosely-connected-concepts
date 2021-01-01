package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.FluidDataContainer
import com.joshmanisdabomb.lcc.data.factory.tag.FluidTagFactory
import com.joshmanisdabomb.lcc.directory.LCCFluids
import com.joshmanisdabomb.lcc.directory.ThingDirectory

object LCCFluidData : ThingDirectory<FluidDataContainer, Unit>() {

    val oil_still by createWithName { FluidDataContainer().add(FluidTagFactory()) }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        all.forEach { (k, v) -> v.init(k, LCCFluids[k]) }

        val missing = LCCFluids.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCFluids[it]!!; defaults().init(key, it) }
    }

    fun defaults() = FluidDataContainer()

}