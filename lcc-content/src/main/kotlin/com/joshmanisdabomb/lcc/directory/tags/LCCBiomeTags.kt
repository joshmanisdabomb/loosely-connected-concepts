package com.joshmanisdabomb.lcc.directory.tags

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome

object LCCBiomeTags : AdvancedDirectory<Identifier?, TagKey<Biome>, Unit, Unit>() {

    val wasteland by entry(::initialiser) { null }

    val has_wasteland_tent by entry(::initialiser) { LCC.id("has_structure/wasteland_tent") }
    val has_sapphire_altar by entry(::initialiser) { LCC.id("has_structure/sapphire_altar") }

    fun initialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = TagKey.of(Registry.BIOME_KEY, input ?: context.id)

    override fun defaultProperties(name: String) = Unit

    override fun defaultContext() = Unit

    override fun id(name: String) = LCC.id(name)

}