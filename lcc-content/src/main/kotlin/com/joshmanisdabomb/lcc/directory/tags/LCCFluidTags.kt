package com.joshmanisdabomb.lcc.directory.tags

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import net.minecraft.fluid.Fluid
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LCCFluidTags : AdvancedDirectory<Identifier?, TagKey<Fluid>, Unit, Unit>() {

    val oil by entry(::initialiser) { null }
    val asphalt by entry(::initialiser) { null }

    fun initialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = TagKey.of(Registry.FLUID_KEY, input ?: context.id)

    override fun defaultProperties(name: String) = Unit

    override fun defaultContext() = Unit

    override fun id(name: String) = LCC.id(name)

}