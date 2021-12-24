package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory

object LCCComputerModules : BasicDirectory<ComputerModule, Unit>(), RegistryDirectory<ComputerModule, Unit, Unit> {

    override val registry = LCCRegistries.computer_modules

    override fun regId(name: String) = LCC.id(name)

    val computer_casing by entry(::initialiser) { CasingComputerModule() }
    val computer by entry(::initialiser) { ComputerComputerModule() }
    val floppy_drive by entry(::initialiser) { DriveComputerModule() }
    val cd_drive by entry(::initialiser) { DriveComputerModule() }
    val card_reader by entry(::initialiser) { DriveComputerModule() }
    val stick_reader by entry(::initialiser) { DriveComputerModule() }
    val drive_bay by entry(::initialiser) { DriveComputerModule() }

    override fun defaultProperties(name: String) = Unit

}
