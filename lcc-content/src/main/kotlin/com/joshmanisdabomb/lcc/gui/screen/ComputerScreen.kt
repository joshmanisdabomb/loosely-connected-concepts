package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
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

class ComputerScreen(handler: ComputingScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<ComputingScreenHandler>(handler, inventory, title), PowerScreenUtils {

    private val half get() = (MinecraftClient.getInstance().world?.getBlockEntity(handler.pos) as? ComputingBlockEntity)?.getHalf(handler.top)

    val power: FunctionalButtonWidget by lazy { PowerButton(x + 23, y + 58) {
        run {
            val world = MinecraftClient.getInstance().world?.registryKey ?: return@run
            val pos = handler.pos
            val top = handler.top
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
        backgroundWidth = 176
        backgroundHeight = 178
        playerInventoryTitleX = 8
        playerInventoryTitleY = 84
        super.init()
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
        val half = half
        val module = half?.module as? ComputerComputerModule
        val code = module?.getCurrentErrorCode(half)
        if (code == 0) {
            power.ix = power.width.times(5)
        } else if (code != null) {
            power.ix = power.width.times(6)
        } else if (module?.generateErrorCode(half, power = handler.powerAmount()) == 0) {

        } else {
            power.active = false
        }
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        val half = half
        val energy = half?.module?.rawEnergyMaximum
        if (energy != null) {
            renderPower(matrices, handler.powerAmount(), energy, x+51, y+62)
        }

        val code = (half?.module as? ComputerComputerModule)?.getCurrentErrorCode(half) ?: 0
        if (code > 0 && System.currentTimeMillis().rem(2000) > 1000) {
            drawTexture(matrices, x + 138, y + 53, backgroundWidth, 12.times(code).plus(14), 22, 12)
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
        if (power.isHovered) power.renderTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null/* TODO */, mouseX, mouseY, x + 51..x + 62, y + 62..y + 76)

        val half = half
        val code = (half?.module as? ComputerComputerModule)?.getCurrentErrorCode(half) ?: 0
        if (code > 0 && mouseX in x+137..x+161 && mouseY in y+53..y+66) {
            renderOrderedTooltip(matrices, textRenderer.wrapLines(Text.translatable("container.lcc.computer.error.$code"), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    private fun renderButtonTooltip(matrices: MatrixStack, x: Int, y: Int) {
        val half = half
        val tooltip = if ((half?.module as? ComputerComputerModule)?.getCurrentErrorCode(half) == null) "activate" else "deactivate"
        this.renderOrderedTooltip(matrices, textRenderer.wrapLines(Text.translatable("gui.lcc.computer.$tooltip"), Int.MAX_VALUE), x, y)
    }

    companion object {
        val texture = LCC.id("textures/gui/container/computer.png")
    }

    private inner class PowerButton(x: Int, y: Int, pressed: () -> Int?) : FunctionalButtonWidget(x, y, 22, 22, 22, 22, Text.translatable("gui.lcc.computer.toggle"), ::renderButtonTooltip, pressed) {

        init {
            active = false
            texture = Companion.texture
            ix = width * 4
            sx = 0
            sy = backgroundHeight
        }

    }

}