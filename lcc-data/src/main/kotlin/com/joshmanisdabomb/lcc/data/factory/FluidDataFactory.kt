package com.joshmanisdabomb.lcc.data.factory

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.container.FluidDataContainer
import net.minecraft.fluid.Fluid

interface FluidDataFactory {

    fun init(container: FluidDataContainer) = this

    fun apply(data: DataAccessor, entry: Fluid)

}