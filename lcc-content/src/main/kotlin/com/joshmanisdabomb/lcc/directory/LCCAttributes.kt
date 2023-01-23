package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.minecraft.entity.attribute.ClampedEntityAttribute
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.util.registry.Registry

object LCCAttributes : BasicDirectory<EntityAttribute, Unit>(), RegistryDirectory<EntityAttribute, Unit, Unit> {

    override val registry by lazy { Registry.ATTRIBUTE }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_damage by entry(::initialiser) { ClampedEntityAttribute("attribute.lcc.name.wasteland.damage", 0.0, 0.0, 1.0).setTracked(true) }
    val wasteland_protection by entry(::initialiser) { ClampedEntityAttribute("attribute.lcc.name.wasteland.protection", 0.0, 0.0, 1.0).setTracked(true) }
    val rainbow_damage by entry(::initialiser) { ClampedEntityAttribute("attribute.lcc.name.rainbow.damage", 0.0, 0.0, 1.0).setTracked(true) }
    val rainbow_protection by entry(::initialiser) { ClampedEntityAttribute("attribute.lcc.name.rainbow.protection", 0.0, 0.0, 1.0).setTracked(true) }

    override fun defaultProperties(name: String) = Unit

}