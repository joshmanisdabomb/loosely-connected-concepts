package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory

object LCCPartitionTypes : BasicDirectory<PartitionType, Unit>(), RegistryDirectory<PartitionType, Unit, Unit> {

    override val registry = LCCRegistries.computer_partitions

    override fun regId(name: String) = LCC.id(name)

    val text by entry(::initialiser) { TextPartitionType() }
//    val item by entry(::initialiser) { ComputerComputerModule() }
//    val os_console by entry(::initialiser) { DriveComputerModule() }
//    val os_graphical by entry(::initialiser) { DriveComputerModule() }

    override fun defaultProperties(name: String) = Unit

}
