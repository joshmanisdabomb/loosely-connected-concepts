package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.concepts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.concepts.gauntlet.GauntletAction.Companion.ability
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCPackets
import com.joshmanisdabomb.lcc.directory.LCCPackets.id
import com.joshmanisdabomb.lcc.gui.utils.GauntletProgressRenderer
import com.joshmanisdabomb.lcc.toInt
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.LiteralText
import net.minecraft.util.math.MathHelper.lerp

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
        val camera = (client!!.getCameraEntity() as? PlayerEntity) ?: return client!!.openScreen(null)
        if (camera.mainHandStack.item != LCCItems.gauntlet) return client!!.openScreen(null)

        val hovered = hovered(mouseX, mouseY)

        if (!client!!.options.keyAttack.isPressed) {
            if (hovered != null) {
                camera.mainHandStack.orCreateTag.ability = hovered
                ClientSidePacketRegistry.INSTANCE.sendToServer(LCCPackets::gauntletSwitch.id(), PacketByteBuf(Unpooled.buffer()).apply { writeByte(hovered.ordinal.plus(1)) })
            }
            client!!.openScreen(null)
        }

        val current = camera.mainHandStack.orCreateTag.ability
        val alpha = (0x10.times(ticks) + lerp(client!!.tickDelta, 0x00.toFloat(), 0x10.toFloat())).toInt().coerceAtMost(0x90) shl 24
        this.fillGradient(matrix, 0, 0, width, height, 0x00101010 + alpha, 0x10101010 + alpha)

        when (hovered) {
            GauntletAction.UPPERCUT -> this.fillGradient(matrix, 0, 0, width.div(2), height.div(2), 0x901080A0.toInt(), 0xC01080A0.toInt())
            GauntletAction.PUNCH -> this.fillGradient(matrix, width.div(2), 0, width, height.div(2), 0x901080A0.toInt(), 0xC01080A0.toInt())
        }

        client!!.textureManager.bindTexture(texture)
        renderAttack(matrix, camera, GauntletAction.UPPERCUT, current, ticks, delta, 0f)
        renderAttack(matrix, camera, GauntletAction.PUNCH, current, ticks, delta, 90f)
        renderAttack(matrix, camera, GauntletAction.UPPERCUT, current, ticks, delta, 180f)
        renderAttack(matrix, camera, GauntletAction.UPPERCUT, current, ticks, delta, 270f)
        client!!.textureManager.bindTexture(GUI_ICONS_TEXTURE)

        super.render(matrix, mouseX, mouseY, delta)
    }

    fun renderAttack(matrix: MatrixStack, camera: PlayerEntity, action: GauntletAction, current: GauntletAction?, ticks: Int, delta: Float, angle: Float) {
        val u = action.isActing(camera).toInt(1)
        val v = (current == action).not().toInt()

        matrix.push()
        matrix.translate(sw.div(2).toDouble(), 0.0, 0.0)
        matrix.translate(0.0, sh.div(2).toDouble(), 0.0)
        matrix.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(angle))

        this.drawTexture(matrix, loc, loc, u.times(size), v.times(size), size, size)
        if (action.isCooldown(camera)) {
            renderProgress(matrix, camera, action::cooldownPercentage, false, 2, v, angle, delta)
            renderLine(matrix, camera, action::cooldownPercentage, false, 3, v, angle, delta)
        }

        matrix.pop()
    }

    override fun isPauseScreen() = false

    fun hovered(mouseX: Int, mouseY: Int): GauntletAction? {
        if (mouseX in sw.div(2).minus(7)..sw.div(2).plus(7) && mouseY in sh.div(2).minus(7)..sh.div(2).plus(7)) {
            return null
        } else if (mouseX < sw.div(2) && mouseY < sh.div(2)) {
            return GauntletAction.UPPERCUT
        } else if (mouseX >= sw.div(2) && mouseY < sh.div(2)) {
            return GauntletAction.PUNCH
        } else if (mouseX < sw.div(2) && mouseY >= sh.div(2)) {
            return GauntletAction.UPPERCUT
        } else if (mouseX >= sw.div(2) && mouseY >= sh.div(2)) {
            return GauntletAction.UPPERCUT
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