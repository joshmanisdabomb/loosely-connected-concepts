package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCPacketsToServer
import com.joshmanisdabomb.lcc.inventory.container.AtomicBombScreenHandler
import com.joshmanisdabomb.lcc.utils.FunctionalButtonWidget
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.MathHelper
import kotlin.math.cos

class AtomicBombScreen(handler: AtomicBombScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<AtomicBombScreenHandler>(handler, inventory, title) {

    val listener = ::onChanged
    val detonate: FunctionalButtonWidget by lazy { DetonateButton(x + 77, y + 47) {
        run {
            val world = MinecraftClient.getInstance().world?.registryKey ?: return@run
            val pos = handler.pos ?: return@run
            ClientSidePacketRegistry.INSTANCE.sendToServer(LCCPacketsToServer[LCCPacketsToServer::atomic_bomb_detonate].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeIdentifier(world.value); writeBlockPos(pos) })
        }
        this.onClose()
        return@DetonateButton null
    } }

    init {
        handler.inventory.addListener(listener)
    }

    override fun init() {
        super.init()
        backgroundWidth = 176
        backgroundHeight = 171
        playerInventoryTitleX = 8
        playerInventoryTitleY = 77
        addButton(detonate)
        onChanged(handler.inventory)
    }

    override fun resize(client: MinecraftClient, width: Int, height: Int) {
        super.resize(client, width, height)
        detonate.x = x + 77
        detonate.y = y + 47
    }

    override fun onClose() {
        handler.inventory.removeListener(listener)
        super.onClose()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::method_34542)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
        for (widget in buttons) {
            if (widget.isHovered) widget.renderToolTip(matrices, mouseX, mouseY)
            break
        }
    }

    fun onChanged(inventory: Inventory) {
        detonate.active = handler.inventory.canDetonate
    }

    companion object {
        val texture = LCC.id("textures/gui/container/atomic_bomb.png")
    }

    private inner class DetonateButton(x: Int, y: Int, pressed: () -> Int?) : FunctionalButtonWidget(x, y, 22, 22, 22, 22, TranslatableText("gui.lcc.atomic_bomb.detonate"), { matrices, x, y -> this@AtomicBombScreen.renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("gui.lcc.atomic_bomb.detonate"), Int.MAX_VALUE) + textRenderer.wrapLines(TranslatableText("gui.lcc.atomic_bomb.detonate.power", handler.inventory.uraniumCount), Int.MAX_VALUE), x, y) }, pressed) {

        init {
            texture = Companion.texture
            ix = width * 4
            sx = 0
            sy = backgroundHeight
        }

        override fun onRenderButton(matrices: MatrixStack) {
            if (!disabled && active && isHovered) {
                val color = MathHelper.hsvToRgb(cos(System.currentTimeMillis().rem(2000).div(2000F).times(Math.PI.toFloat()).times(2)).times(0.03F) + 0.11F, 0.8F, 1.0F)
                val red = (color shr 16 and 255).toFloat() / 255.0f
                val green = (color shr 8 and 255).toFloat() / 255.0f
                val blue = (color and 255).toFloat() / 255.0f
                RenderSystem.setShaderColor(red, green, blue, 1f)
            } else {
                super.onRenderButton(matrices)
            }
        }

    }

}