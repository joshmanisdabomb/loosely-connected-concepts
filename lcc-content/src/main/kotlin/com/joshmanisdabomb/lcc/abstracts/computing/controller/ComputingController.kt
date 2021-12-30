package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.server.network.ServerPlayerEntity

abstract class ComputingController {

    val id get() = LCCRegistries.computer_controllers.getKey(this).orElseThrow(::RuntimeException).value

    abstract fun serverTick(session: ComputingSession, context: ComputingSessionExecuteContext)

    abstract fun onClose(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext)
    abstract fun keyPressed(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int)
    abstract fun keyReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int)
    abstract fun mouseClicked(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int)
    abstract fun mouseReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int)
    abstract fun charTyped(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, char: Char)

    @Environment(EnvType.CLIENT)
    abstract fun getBackgroundColor(session: ComputingSession, view: ComputingSessionViewContext): Long

    @Environment(EnvType.CLIENT)
    abstract fun render(session: ComputingSession, view: ComputingSessionViewContext, matrices: MatrixStack, delta: Float, x: Int, y: Int)

}