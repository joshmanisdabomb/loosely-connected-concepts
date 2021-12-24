package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.CasingComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.module.DriveComputerModule
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory

object LCCPartitions : BasicDirectory<Partition, Unit>(), RegistryDirectory<Partition, Unit, Unit> {

    override val registry = LCCRegistries.computer_partitions

    override fun regId(name: String) = LCC.id(name)

    val text by entry(::initialiser) { TextPartition() }
//    val item by entry(::initialiser) { ComputerComputerModule() }
//    val os_console by entry(::initialiser) { DriveComputerModule() }
//    val os_graphical by entry(::initialiser) { DriveComputerModule() }

    override fun defaultProperties(name: String) = Unit

}
