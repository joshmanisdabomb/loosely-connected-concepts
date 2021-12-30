package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.joshmanisdabomb.lcc.abstracts.computing.partition.TextPartitionType
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory

object LCCSessionControllers : BasicDirectory<ComputingController, Unit>(), RegistryDirectory<ComputingController, Unit, Unit> {

    override val registry = LCCRegistries.computer_controllers

    override fun regId(name: String) = LCC.id(name)

    val bios by entry(::initialiser) { BIOSComputingController() }
    val console by entry(::initialiser) { ConsoleComputingController() }
//    val graphical by entry(::initialiser) { GraphicalComputingController() }

    override fun defaultProperties(name: String) = Unit

}
