package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.challenges.AltarChallenge
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.util.registry.MutableRegistry

object LCCRegistries : AdvancedDirectory<FabricRegistryBuilder<out Any, out MutableRegistry<out Any>>, MutableRegistry<out Any>, Unit, Unit>() {

    val altar_challenges by entry(::initialiser) { FabricRegistryBuilder.createSimple(AltarChallenge::class.java, id).attribute(RegistryAttribute.SYNCED) }

    fun <T : Any, R : MutableRegistry<T>> initialiser(input: FabricRegistryBuilder<T, R>, context: DirectoryContext<Unit>, parameters: Unit) = input.buildAndRegister()

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit

    override fun defaultContext() = Unit

}