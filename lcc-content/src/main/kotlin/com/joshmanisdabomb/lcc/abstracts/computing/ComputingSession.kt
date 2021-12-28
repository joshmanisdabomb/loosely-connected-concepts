package com.joshmanisdabomb.lcc.abstracts.computing

import com.joshmanisdabomb.lcc.abstracts.computing.controller.BIOSComputingController
import com.joshmanisdabomb.lcc.abstracts.computing.controller.ComputingController
import com.joshmanisdabomb.lcc.abstracts.computing.controller.LCCSessionControllers
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import java.util.*

class ComputingSession(id: UUID) {

    private var _id = id
    val id get() = _id

    var controller: ComputingController = LCCSessionControllers.bios
    var ticks = 0

    fun serverTick(context: ComputingSessionContext) {
        controller.serverTick(this, context)
        ticks += 1
    }

    fun readNbt(nbt: NbtCompound) {
        _id = nbt.getUuid("Id")
        controller = LCCRegistries.computer_controllers[Identifier(nbt.getString("Controller"))]
        ticks = nbt.getInt("Ticks")
    }

    fun writeNbt(nbt: NbtCompound) {
        nbt.putUuid("Id", _id)
        nbt.putString("Controller", controller.id.toString())
        nbt.putInt("Ticks", ticks)
    }

    @Environment(EnvType.CLIENT)
    fun getBackgroundColor() = controller.getBackgroundColor(this).toInt()

    @Environment(EnvType.CLIENT)
    fun render(matrices: MatrixStack, delta: Float, x: Int, y: Int) = controller.render(this, matrices, delta, x, y)

}