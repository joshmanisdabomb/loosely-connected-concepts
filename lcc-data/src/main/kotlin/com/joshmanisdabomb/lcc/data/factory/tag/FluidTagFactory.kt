package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.FluidDataFactory
import net.minecraft.fluid.Fluid
import net.minecraft.tag.Tag

class FluidTagFactory(vararg val tags: Tag<Fluid>) : FluidDataFactory {

    override fun apply(data: DataAccessor, entry: Fluid) {
        tags.forEach { data.tags.fluid(it).attach(entry) }
    }

}
