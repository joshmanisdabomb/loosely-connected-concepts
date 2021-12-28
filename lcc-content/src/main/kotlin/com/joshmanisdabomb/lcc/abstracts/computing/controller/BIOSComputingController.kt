package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSessionContext
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack

class BIOSComputingController : ComputingController() {

    override fun serverTick(session: ComputingSession, context: ComputingSessionContext) {

    }

    @Environment(EnvType.CLIENT)
    override fun getBackgroundColor(session: ComputingSession) = 0xFFFFFFFF

    @Environment(EnvType.CLIENT)
    override fun render(session: ComputingSession, matrices: MatrixStack, delta: Float, x: Int, y: Int) {
        println("rendering session ${session.id} at tick ${session.ticks + delta}")
    }

}