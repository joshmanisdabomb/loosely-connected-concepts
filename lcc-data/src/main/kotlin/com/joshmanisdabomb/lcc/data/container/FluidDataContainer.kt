package com.joshmanisdabomb.lcc.data.container

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.FluidDataFactory
import net.minecraft.fluid.Fluid

class FluidDataContainer(accessor: DataAccessor) : DataContainer<Fluid, FluidDataFactory>(accessor) {

    override fun affects(entry: Fluid) = super.affects(entry).let { this }

    override fun affects(entries: List<Fluid>) = super.affects(entries).let { this }

    override fun add(factory: FluidDataFactory) = super.add(factory).let { this }

    override fun apply(factory: FluidDataFactory, entry: Fluid) {
        factory.apply(accessor, entry)
    }

}
