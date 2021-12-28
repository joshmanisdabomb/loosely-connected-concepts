package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSessionContext
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import net.minecraft.util.Formatting

abstract class ComputingController {

    val id get() = LCCRegistries.computer_controllers.getKey(this).orElseThrow(::RuntimeException).value

    abstract fun serverTick(session: ComputingSession, context: ComputingSessionContext)

}