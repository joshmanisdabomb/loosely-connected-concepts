package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletDirectory
import com.joshmanisdabomb.lcc.abstracts.gauntlet.PunchGauntletAction
import com.joshmanisdabomb.lcc.abstracts.gauntlet.UppercutGauntletAction
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCPacketsToServer
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.gui.utils.GauntletProgressRenderer
import com.mojang.blaze3d.systems.RenderSystem
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.LiteralText
import net.minecraft.util.math.MathHelper.lerp
import net.minecraft.util.math.Vec3f

class GauntletScreen() : Screen(LiteralText("Gauntlet")), GauntletProgressRenderer {

    private val texture = LCC.gui("gauntlet_ui")

    override val size = 58
    val spacing = 5
    override val loc = -size-spacing

    private val sw get() = MinecraftClient.getInstance().window.scaledWidth
    private val sh get() = MinecraftClient.getInstance().window.scaledHeight

    var ticks = 0

    init {
        passEvents = true
    }

    override fun tick() {
        ticks++
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        val camera = (client!!.getCameraEntity() as? PlayerEntity) ?: return client!!.setScreen(null)
        if (camera.mainHandStack.item != LCCItems.gauntlet) return client!!.setScreen(null)

        val hovered = hovered(mouseX, mouseY)

        if (!client!!.options.attackKey.isPressed) {
            if (hovered != null) {
                GauntletAction.putInTag(hovered, camera.mainHandStack.orCreateNbt)
                ClientPlayNetworking.send(LCCPacketsToServer[LCCPacketsToServer::gauntlet_switch].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeString(GauntletDirectory[hovered].name) })
            }
            client!!.setScreen(null)
        }

        val current = GauntletAction.getFromTag(camera.mainHandStack.nbt)
        val alpha = (0x10.times(ticks) + lerp(client!!.tickDelta, 0x00.toFloat(), 0x10.toFloat())).toInt().coerceAtMost(0x90) shl 24
        this.fillGradient(matrix, 0, 0, width, height, 0x00101010 + alpha, 0x10101010 + alpha)

        when (hovered) {
            UppercutGauntletAction -> this.fillGradient(matrix, 0, 0, width.div(2), height.div(2), 0x901080A0.toInt(), 0xC01080A0.toInt())
            PunchGauntletAction -> this.fillGradient(matrix, width.div(2), 0, width, height.div(2), 0x901080A0.toInt(), 0xC01080A0.toInt())
        }

        RenderSystem.setShaderTexture(0, texture)
        renderAttack(matrix, camera, UppercutGauntletAction, current, ticks, delta, 0f)
        renderAttack(matrix, camera, PunchGauntletAction, current, ticks, delta, 90f)
        renderAttack(matrix, camera, UppercutGauntletAction, current, ticks, delta, 180f)
        renderAttack(matrix, camera, UppercutGauntletAction, current, ticks, delta, 270f)
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE)

        super.render(matrix, mouseX, mouseY, delta)
    }

    fun renderAttack(matrix: MatrixStack, camera: PlayerEntity, action: GauntletAction<*>, current: GauntletAction<*>?, ticks: Int, delta: Float, angle: Float) {
        val u = action.hasInfo(camera).transformInt()
        val v = (current == action).not().transformInt()

        matrix.push()
        matrix.translate(sw.div(2).toDouble(), 0.0, 0.0)
        matrix.translate(0.0, sh.div(2).toDouble(), 0.0)
        matrix.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(angle))

        this.draw(matrix, loc, loc, u.times(size), v.times(size), size, size)
        val info = action.getInfo(camera)
        if (info?.isCooldown == true) {
            renderProgress(matrix, camera, info.cooldownPercent, false, 2, v, angle, delta)
            renderLine(matrix, camera, info.cooldownPercent, false, 3, v, angle, delta)
        }

        matrix.pop()
    }

    override fun shouldPause() = false

    fun hovered(mouseX: Int, mouseY: Int): GauntletAction<*>? {
        if (mouseX in sw.div(2).minus(7)..sw.div(2).plus(7) && mouseY in sh.div(2).minus(7)..sh.div(2).plus(7)) {
            return null
        } else if (mouseX < sw.div(2) && mouseY < sh.div(2)) {
            return UppercutGauntletAction
        } else if (mouseX >= sw.div(2) && mouseY < sh.div(2)) {
            return PunchGauntletAction
        } else if (mouseX < sw.div(2) && mouseY >= sh.div(2)) {
            return null
        } else if (mouseX >= sw.div(2) && mouseY >= sh.div(2)) {
            return null
        } else {
            return null
        }
    }

    fun open(): Screen {
        val client = MinecraftClient.getInstance()
        client.mouse.unlockCursor()
        init(client, client.window.scaledWidth, client.window.scaledHeight)
        return this
    }

}