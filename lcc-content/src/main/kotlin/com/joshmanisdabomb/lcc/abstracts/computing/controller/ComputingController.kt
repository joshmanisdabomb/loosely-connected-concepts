package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSessionContext
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Formatting

abstract class ComputingController {

    val id get() = LCCRegistries.computer_controllers.getKey(this).orElseThrow(::RuntimeException).value

    abstract fun serverTick(session: ComputingSession, context: ComputingSessionContext)

    @Environment(EnvType.CLIENT)
    abstract fun getBackgroundColor(session: ComputingSession): Long

    @Environment(EnvType.CLIENT)
    abstract fun render(session: ComputingSession, matrices: MatrixStack, delta: Float, x: Int, y: Int)

}