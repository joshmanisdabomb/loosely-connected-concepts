package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.directory.LCCPacketsToServer
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.lib.gui.FunctionalButtonWidget
import com.mojang.blaze3d.systems.RenderSystem
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class ComputerScreen(handler: ComputingScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<ComputingScreenHandler>(handler, inventory, title), PowerScreenUtils {

    private val module: ComputerComputerModule get() = handler.half.module as ComputerComputerModule

    val power: FunctionalButtonWidget by lazy { PowerButton(x + 23, y + 58) {
        run {
            val world = MinecraftClient.getInstance().world?.registryKey ?: return@run
            val pos = handler.half.be.pos ?: return@run
            val top = handler.half.top
            ClientPlayNetworking.send(LCCPacketsToServer[LCCPacketsToServer::computer_power].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeIdentifier(world.value); writeBlockPos(pos); writeBoolean(top) })
        }
        return@PowerButton null
    } }

    private var _ticks = 0
    override val ticks get() = _ticks

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override val translationKey = "container.lcc.computer"

    override fun init() {
        super.init()
        backgroundWidth = 176
        backgroundHeight = 178
        playerInventoryTitleX = 8
        playerInventoryTitleY = 84
        addDrawableChild(power)
    }

    override fun resize(client: MinecraftClient, width: Int, height: Int) {
        super.resize(client, width, height)
        power.x = x + 23
        power.y = y + 58
    }

    override fun handledScreenTick() {
        super.handledScreenTick()
        _ticks += 1

        power.ix = power.width.times(4)
        power.active = true
        val code = module.getCurrentErrorCode(handler.half)
        if (code == 0) {
            power.ix = power.width.times(5)
        } else if (code != null) {
            power.ix = power.width.times(6)
        } else if (module.generateErrorCode(handler.half.inventory!!, handler.powerAmount()) == 0) {

        } else {
            power.active = false
        }
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        renderPower(matrices, handler.powerAmount(), handler.half.rawEnergyMaximum!!, x + 51, y + 62)

        val code = module.getCurrentErrorCode(handler.half) ?: 0
        if (code > 0 && System.currentTimeMillis().rem(2000) > 1000) {
            drawTexture(matrices, x + 138, y + 53, backgroundWidth, 12.times(code).plus(14), 22, 12)
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
        if (power.isHovered) power.renderTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 51..x + 62, y + 62..y + 76)

        val code = module.getCurrentErrorCode(handler.half) ?: 0
        if (code > 0 && mouseX in x+137..x+161 && mouseY in y+53..y+66) {
            renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.computer.error.$code"), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    private fun renderButtonTooltip(matrices: MatrixStack, x: Int, y: Int) {
        val tooltip = if (module.getCurrentErrorCode(handler.half) == null) "activate" else "deactivate"
        this.renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("gui.lcc.computer.$tooltip"), Int.MAX_VALUE), x, y)
    }

    companion object {
        val texture = LCC.id("textures/gui/container/computer.png")
    }

    private inner class PowerButton(x: Int, y: Int, pressed: () -> Int?) : FunctionalButtonWidget(x, y, 22, 22, 22, 22, TranslatableText("gui.lcc.computer.toggle"), ::renderButtonTooltip, pressed) {

        init {
            active = false
            texture = Companion.texture
            ix = width * 4
            sx = 0
            sy = backgroundHeight
        }

    }

}