package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.abstracts.computing.controller.ComputingController
import com.joshmanisdabomb.lcc.abstracts.computing.controller.ConsoleComputingController
import net.minecraft.util.Formatting

class SystemPartitionType(val controller: ComputingController, val size: Int, override val nameColor: Formatting) : PartitionType() {

    override val noFreeSpace = true

}
