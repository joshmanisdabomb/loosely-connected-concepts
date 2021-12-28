package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.challenges.AltarChallenge
import com.joshmanisdabomb.lcc.abstracts.computing.medium.DigitalMedium
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.joshmanisdabomb.lcc.abstracts.computing.controller.ComputingController
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.util.registry.MutableRegistry

object LCCRegistries : AdvancedDirectory<FabricRegistryBuilder<out Any, out MutableRegistry<out Any>>, MutableRegistry<out Any>, Unit, Unit>() {

    val altar_challenges by entry(::initialiser) { FabricRegistryBuilder.createSimple(AltarChallenge::class.java, id).attribute(RegistryAttribute.SYNCED) }
    val computer_modules by entry(::initialiser) { FabricRegistryBuilder.createDefaulted(ComputerModule::class.java, id, LCC.id("casing")).attribute(RegistryAttribute.SYNCED) }
    val computer_mediums by entry(::initialiser) { FabricRegistryBuilder.createSimple(DigitalMedium::class.java, id).attribute(RegistryAttribute.SYNCED) }
    val computer_controllers by entry(::initialiser) { FabricRegistryBuilder.createDefaulted(ComputingController::class.java, id, LCC.id("bios")).attribute(RegistryAttribute.SYNCED) }
    val computer_partitions by entry(::initialiser) { FabricRegistryBuilder.createSimple(PartitionType::class.java, id).attribute(RegistryAttribute.SYNCED) }

    fun <T : Any, R : MutableRegistry<T>> initialiser(input: FabricRegistryBuilder<T, R>, context: DirectoryContext<Unit>, parameters: Unit) = input.buildAndRegister()

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit

    override fun defaultContext() = Unit

}