package com.joshmanisdabomb.lcc.directory.tags

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import net.minecraft.entity.EntityType
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LCCEntityTags : AdvancedDirectory<Identifier?, TagKey<EntityType<*>>, Unit, Unit>() {

    val wasteland_offense by entry(::initialiser) { null }
    val wasteland_defense by entry(::initialiser) { null }

    val temperature_scalding by entry(::initialiser) { null }
    val temperature_red_hot by entry(::initialiser) { null }

    val salt_weakness by entry(::initialiser) { null }

    fun initialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = TagKey.of(Registry.ENTITY_TYPE_KEY, input ?: context.id)

    override fun defaultProperties(name: String) = Unit

    override fun defaultContext() = Unit

    override fun id(name: String) = LCC.id(name)

}