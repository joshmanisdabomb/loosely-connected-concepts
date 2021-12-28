package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSessionContext

class BIOSComputingController : ComputingController() {

    override fun serverTick(session: ComputingSession, context: ComputingSessionContext) {
        println("the BIOS is online")
        println(session.id)
        println(session.ticks)
        println(context.getAccessibleDisks())
    }

}