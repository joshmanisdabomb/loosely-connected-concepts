package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.NuclearFiredGeneratorBlock
import com.joshmanisdabomb.lcc.directory.LCCPacketsToServer
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.NuclearFiredGeneratorScreenHandler
import com.joshmanisdabomb.lcc.utils.FunctionalButtonWidget
import com.mojang.blaze3d.systems.RenderSystem
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import kotlin.math.ceil
import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity.Companion as BlockEntity

class NuclearFiredGeneratorScreen(handler: NuclearFiredGeneratorScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<NuclearFiredGeneratorScreenHandler>(handler, inventory, title), PowerScreenUtils {

    val listener = ::onChanged
    val activate: FunctionalButtonWidget by lazy { ToggleButton(x + 12, y + 35) {
        run {
            val world = MinecraftClient.getInstance().world?.registryKey ?: return@run
            val pos = handler.pos ?: return@run
            ClientPlayNetworking.send(LCCPacketsToServer[LCCPacketsToServer::nuclear_generator_activate].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeIdentifier(world.value); writeBlockPos(pos) })
        }
        return@ToggleButton null
    } }

    private var _ticks = 0
    override val ticks get() = _ticks

    override val translationKey = "container.lcc.generator"

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override val action_v = 14
    override val power_v = 56

    val barSize = BlockEntity.maxSafeOutput.times(1.3f)

    val state get() = MinecraftClient.getInstance().world?.getBlockState(handler.pos)

    init {
        handler.inventory.addListener(listener)
    }

    override fun onClose() {
        handler.inventory.removeListener(listener)
        super.onClose()
    }

    override fun init() {
        super.init()
        backgroundWidth = 196
        backgroundHeight = 176
        playerInventoryTitleX = 18
        playerInventoryTitleY = 83
        addButton(activate)
        onChanged(handler.inventory)
    }

    override fun resize(client: MinecraftClient, width: Int, height: Int) {
        super.resize(client, width, height)
        activate.x = x + 12
        activate.y = y + 35
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::method_34542)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        renderPower(matrices, handler.powerAmount(), LooseEnergy.toStandard(BlockEntity.maxEnergy), x + 38, y + 39)

        renderAction(matrices, 0, handler.outputAmount().times(handler.waterAmount().div(3f)), BlockEntity.maxSafeOutput, x + 85, y + 18)

        renderBarY(matrices, handler.fuelAmount(), BlockEntity.maxFuel, x + 105, y + 66, backgroundWidth, 30, 14, 9)
        renderBarY(matrices, handler.coolantAmount(), BlockEntity.maxCoolant, x + 105, y + 39, backgroundWidth, 42, 14, 13)

        state?.also {
            val e = BlockEntity.approxEquilibrium(BlockEntity.maxFuel, handler.coolantAmount())
            val w = ceil(e.div(barSize).times(78)).toInt()
            if (it.block is NuclearFiredGeneratorBlock && !it[Properties.LIT]) {
                if (w >= 0) {
                    drawTexture(matrices, x + 107, y + 30, 0, backgroundHeight + 34, 78, 3)
                    renderBarX(matrices, e.coerceAtMost(barSize), barSize, x + 107, y + 30, 0, backgroundHeight + 22, 78, 3)
                    if (w < 77) {
                        drawTexture(matrices, x + 105 + w, y + 26, backgroundWidth, 70, 5, 3)
                    }
                } else {
                    drawTexture(matrices, x + 107, y + 30, 0, backgroundHeight + 22, 78, 3)
                }
            } else {
                if (w >= 0) {
                    renderBarX(matrices, e.coerceAtMost(barSize), barSize, x + 107, y + 30, 0, backgroundHeight + 28, 78, 3)
                    renderBarX(matrices, handler.outputAmount().coerceAtMost(barSize), barSize, x + 107, y + 30, 0, backgroundHeight + 31, 78, 3)

                    renderBarX(matrices, handler.outputAmount().coerceAtMost(e), e, x + 107, y + 30, 0, backgroundHeight + 25, w, 3)
                    if (w < 78) {
                        drawTexture(matrices, x + 105 + w, y + 26, backgroundWidth, 70, 5, 3)
                    }
                } else {
                    drawTexture(matrices, x + 107, y + 30, 0, backgroundHeight + 28, 78, 3)
                    renderBarX(matrices, handler.outputAmount().coerceAtMost(barSize), barSize, x + 107, y + 30, 0, backgroundHeight + 25, 78, 3)
                }
            }
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 38..x + 49, y + 39..y + 53)

        state?.also {
            if (it.block is NuclearFiredGeneratorBlock && it[Properties.LIT]) {
                renderSteamTooltip(matrices, handler.outputAmount(), BlockEntity.maxSafeOutput, handler.waterAmount(), mouseX, mouseY, x + 85..x + 99, y + 18..y + 32)
            }
        }

        for (widget in buttons) {
            if (widget.isHovered) widget.renderToolTip(matrices, mouseX, mouseY)
            break
        }
    }

    override fun tick() {
        super.tick()
        _ticks += 1

        val state = MinecraftClient.getInstance().world?.getBlockState(handler.pos)
        if (state?.block is NuclearFiredGeneratorBlock && !state[Properties.LIT]) {
            activate.active = BlockEntity.canStartup(LooseEnergy.fromStandard(handler.powerAmount()), handler.inventory)
        } else {
            activate.active = false
        }
    }

    fun onChanged(inventory: Inventory) {

    }

    companion object {
        val texture = LCC.gui("container/nuclear_generator")
    }

    private inner class ToggleButton(x: Int, y: Int, pressed: () -> Int?) : FunctionalButtonWidget(x, y, 22, 22, 22, 22, TranslatableText("gui.lcc.nuclear_generator.activate"), { matrices, x, y -> this@NuclearFiredGeneratorScreen.renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("gui.lcc.nuclear_generator.activate"), Int.MAX_VALUE), x, y) }, pressed) {

        init {
            active = false
            texture = Companion.texture
            ix = width * 4
            sx = 0
            sy = backgroundHeight
        }

    }

}