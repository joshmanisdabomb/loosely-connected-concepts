package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.directory.LCCPacketsToServer
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.lib.gui.FunctionalButtonWidget
import com.mojang.blaze3d.systems.RenderSystem
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import kotlin.text.Typography.half

class ComputerScreen(handler: ComputingScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<ComputingScreenHandler>(handler, inventory, title), PowerScreenUtils {

    val listener = ::onChanged
    val power: FunctionalButtonWidget by lazy { PowerButton(x + 23, y + 58) {
        run {
            val world = MinecraftClient.getInstance().world?.registryKey ?: return@run
            val pos = handler.half.be.pos ?: return@run
            val top = handler.half.top
            ClientPlayNetworking.send(LCCPacketsToServer[LCCPacketsToServer::computer_power].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeIdentifier(world.value); writeBlockPos(pos); writeBoolean(top) })
        }
        this.onClose()
        return@PowerButton null
    } }

    private var _ticks = 0
    override val ticks get() = _ticks

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override val translationKey = "container.lcc.computer"

    init {
        handler.half.inventory?.addListener(listener)
    }

    override fun init() {
        super.init()
        backgroundWidth = 176
        backgroundHeight = 178
        playerInventoryTitleX = 8
        playerInventoryTitleY = 84
        addDrawableChild(power)
        onChanged(handler.half.inventory!!)
    }

    override fun resize(client: MinecraftClient, width: Int, height: Int) {
        super.resize(client, width, height)
        power.x = x + 23
        power.y = y + 58
    }

    override fun onClose() {
        handler.half.inventory?.removeListener(listener)
        super.onClose()
    }

    override fun handledScreenTick() {
        super.handledScreenTick()
        _ticks += 1
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        renderPower(matrices, handler.powerAmount(), handler.half.rawEnergyMaximum!!, x + 51, y + 62)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
        if (power.isHovered) power.renderTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 51..x + 62, y + 62..y + 76)
    }

    private fun renderButtonTooltip(matrices: MatrixStack, x: Int, y: Int) {
        this.renderOrderedTooltip(matrices,
            textRenderer.wrapLines(TranslatableText("gui.lcc.computer.activate"), Int.MAX_VALUE)
        , x, y)
    }

    fun onChanged(inventory: Inventory) {
        power.active = ComputerComputerModule.canPower(handler.half.inventory!!)
    }

    companion object {
        val texture = LCC.id("textures/gui/container/computer.png")
    }

    private inner class PowerButton(x: Int, y: Int, pressed: () -> Int?) : FunctionalButtonWidget(x, y, 22, 22, 22, 22, TranslatableText("gui.lcc.computer.activate"), ::renderButtonTooltip, pressed) {

        init {
            active = false
            texture = Companion.texture
            ix = width * 4
            sx = 0
            sy = backgroundHeight
        }

    }

}