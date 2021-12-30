package com.joshmanisdabomb.lcc.abstracts.computing.session

import com.joshmanisdabomb.lcc.abstracts.computing.controller.ComputingController
import com.joshmanisdabomb.lcc.abstracts.computing.controller.LCCSessionControllers
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.build
import dev.onyxstudios.cca.api.v3.level.LevelComponents
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.world.WorldProperties
import java.util.*

class ComputingSession(id: UUID) {

    private var _id = id
    val id get() = _id

    private var controller: ComputingController = LCCSessionControllers.bios
    private var _ticks = 0
    val ticks get() = _ticks

    private var data = NbtCompound()
    private var serverData = NbtCompound()
    private var viewData = mutableMapOf<UUID, NbtCompound>()

    private var sync = false

    fun clientTick(context: ComputingSessionExecuteContext) {
        _ticks += 1
    }

    fun serverTick(context: ComputingSessionExecuteContext) {
        val world = context.getWorldFromContext() as? ServerWorld ?: return
        controller.serverTick(this, context)
        if (sync) { syncToAllWatching(world.server); sync = false }
        _ticks += 1
    }

    fun getSharedData() = data

    fun getServerData() = serverData

    fun getViewData(id: UUID) = viewData.getOrPut(id) { NbtCompound() }

    fun onClose(player: ServerPlayerEntity, view: ComputingSessionViewContext) {
        controller.onClose(this, player, view)
        if (sync) { syncToAllWatching(player.server); sync = false }
    }

    fun keyPressed(player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) {
        controller.keyPressed(this, player, view, keyCode)
        if (sync) { syncToAllWatching(player.server); sync = false }
    }

    fun keyReleased(player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) {
        controller.keyReleased(this, player, view, keyCode)
        if (sync) { syncToAllWatching(player.server); sync = false }
    }

    fun mouseClicked(player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) {
        controller.mouseClicked(this, player, view, mouseX, mouseY, button)
        if (sync) { syncToAllWatching(player.server); sync = false }
    }

    fun mouseReleased(player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) {
        controller.mouseReleased(this, player, view, mouseX, mouseY, button)
        if (sync) { syncToAllWatching(player.server); sync = false }
    }

    fun charTyped(player: ServerPlayerEntity, view: ComputingSessionViewContext, char: Char) {
        controller.charTyped(this, player, view, char)
    }

    fun sync() { sync = true }

    private fun syncToAllWatching(server: MinecraftServer) {
        val sessions = LCCComponents.computing_sessions.maybeGet(server.saveProperties.mainWorldProperties).orElse(null) ?: return
        LevelComponents.sync(LCCComponents.computing_sessions, server, sessions.syncSingle(id), sessions.playersViewing(id))
    }

    fun readNbt(nbt: NbtCompound) {
        _id = nbt.getUuid("Id")
        controller = LCCRegistries.computer_controllers[Identifier(nbt.getString("Controller"))]
        _ticks = nbt.getInt("Ticks")

        data = nbt.getCompound("Data")
        serverData = nbt.getCompound("ServerData")
        viewData.clear()
        nbt.getCompound("ViewData").apply {
            keys.forEach { id ->
                try {
                    val uuid = UUID.fromString(id)
                    viewData[uuid] = getCompound(id)
                } catch (e: IllegalArgumentException) {
                    return@forEach
                }
            }
        }
    }

    fun writeNbt(nbt: NbtCompound, terminal: UUID? = null) {
        nbt.putUuid("Id", _id)
        nbt.putString("Controller", controller.id.toString())
        nbt.putInt("Ticks", ticks)

        nbt.put("Data", data)
        nbt.put("ServerData", serverData)
        nbt.build("ViewData", NbtCompound()) {
            if (terminal != null) {
                viewData[terminal]?.also { put(terminal.toString(), it) }
            } else {
                for ((k, v) in viewData) put(k.toString(), v)
            }
        }
    }

    @Environment(EnvType.CLIENT)
    fun getBackgroundColor(view: ComputingSessionViewContext) = controller.getBackgroundColor(this, view).toInt()

    @Environment(EnvType.CLIENT)
    fun render(view: ComputingSessionViewContext, matrices: MatrixStack, delta: Float, x: Int, y: Int) = controller.render(this, view, matrices, delta, x, y)

}