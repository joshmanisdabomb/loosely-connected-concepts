package com.joshmanisdabomb.lcc.abstracts.computing.controller.console

import com.joshmanisdabomb.lcc.abstracts.computing.controller.ConsoleComputingController
import com.joshmanisdabomb.lcc.abstracts.computing.controller.LCCSessionControllers
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

data class ConsoleCommandSource(val session: ComputingSession, val context: ComputingSessionExecuteContext, val view: UUID, val player: ServerPlayerEntity) {

    val controller = LCCSessionControllers.console

}
