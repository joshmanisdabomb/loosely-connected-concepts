package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.medium.LCCDigitalMediums
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory

object LCCComputerModules : BasicDirectory<ComputerModule, Unit>(), RegistryDirectory<ComputerModule, Unit, Unit> {

    override val registry = LCCRegistries.computer_modules

    override fun regId(name: String) = LCC.id(name)

    val computer_casing by entry(::initialiser) { CasingComputerModule() }
    val computer by entry(::initialiser) { ComputerComputerModule() }
    val floppy_drive by entry(::initialiser) { DriveComputerModule(LCCDigitalMediums.floppy_disk) }
    val cd_drive by entry(::initialiser) { DriveComputerModule(LCCDigitalMediums.compact_disc, slotY = 20) }
    val card_reader by entry(::initialiser) { DriveComputerModule(LCCDigitalMediums.memory_card) }
    val stick_reader by entry(::initialiser) { DriveComputerModule(LCCDigitalMediums.memory_stick) }
    val drive_bay by entry(::initialiser) { DriveComputerModule(LCCDigitalMediums.hard_disk_drive, LCCDigitalMediums.solid_state_drive) }

    override fun defaultProperties(name: String) = Unit

}
