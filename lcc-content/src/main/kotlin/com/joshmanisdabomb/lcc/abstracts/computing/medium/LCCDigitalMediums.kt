package com.joshmanisdabomb.lcc.abstracts.computing.medium

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.CasingComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.module.DriveComputerModule
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory

object LCCDigitalMediums : BasicDirectory<DigitalMedium, Unit>(), RegistryDirectory<DigitalMedium, Unit, Unit> {

    override val registry = LCCRegistries.computer_mediums

    override fun regId(name: String) = LCC.id(name)

    val floppy_disk by entry(::initialiser) { DigitalMedium(144, typeCost = 0, typeLimit = 1, durability = 40) }
    val compact_disc by entry(::initialiser) { DigitalMedium(700, typeCost = 20, typeLimit = 10, durability = 80) }
    val memory_card by entry(::initialiser) { DigitalMedium(100, 10000, typeCost = 0, typeLimit = 64) { it.plus(20) } }
    val memory_stick by entry(::initialiser) { DigitalMedium(100, 10000, typeCost = 80) { it.plus(20) } }
    val hard_disk_drive by entry(::initialiser) { DigitalMedium(1000, 100000, typeCost = 250, typeLimit = 100, durability = 2000) { it.plus(1000) } }
    val solid_state_drive by entry(::initialiser) { DigitalMedium(1000, 100000, amountCost = 64, amountLimit = 64, typeCost = 1, durability = 500) { it.plus(100) } }
    val m2 by entry(::initialiser) { DigitalMedium(1000, 100000, typeCost = 1, durability = 200) { it.plus(100) } }

    override fun defaultProperties(name: String) = Unit

}
