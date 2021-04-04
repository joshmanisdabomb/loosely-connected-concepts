package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.ExplodingNuclearFiredGeneratorBlock
import com.joshmanisdabomb.lcc.block.NuclearFiredGeneratorBlock
import com.joshmanisdabomb.lcc.directory.LCCPacketsToServer
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import com.joshmanisdabomb.lcc.extensions.transformInt
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
import net.minecraft.util.math.MathHelper
import kotlin.math.ceil
import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity.Companion as BlockEntity

class NuclearFiredGeneratorScreen(handler: NuclearFiredGeneratorScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<NuclearFiredGeneratorScreenHandler>(handler, inventory, title), PowerScreenUtils {

    val listener = ::onChanged
    val activate: FunctionalButtonWidget by lazy { ToggleButton(field_2776 + 12, field_2800 + 35) {
        run {
            val world = MinecraftClient.getInstance().world?.registryKey ?: return@run
            val pos = handler.pos ?: return@run
            ClientPlayNetworking.send(LCCPacketsToServer[LCCPacketsToServer::nuclear_generator_activate].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeIdentifier(world.value); writeBlockPos(pos) })
        }
        return@ToggleButton null
    } }

    private var _ticks = 0
    override val ticks get() = _ticks

    override val translationKey = "container.lcc.nuclear_generator"

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
        activate.x = field_2776 + 12
        activate.y = field_2800 + 35
    }

    private val blink get() = System.currentTimeMillis().rem(600) < 300

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, field_2776, field_2800, 0, 0, backgroundWidth, backgroundHeight)

        renderPower(matrices, handler.powerAmount(), LooseEnergy.toStandard(BlockEntity.maxEnergy), field_2776 + 38, field_2800 + 39)

        renderAction(matrices, 0, handler.outputAmount().times(handler.waterAmount().div(3f)), BlockEntity.maxSafeOutput, field_2776 + 85, field_2800 + 18)

        renderBarY(matrices, handler.fuelAmount(), BlockEntity.maxFuel, field_2776 + 105, field_2800 + 66, backgroundWidth, 30, 14, 9)
        renderBarY(matrices, handler.coolantAmount(), BlockEntity.maxCoolant, field_2776 + 105, field_2800 + 39, backgroundWidth, 42, 14, 13)

        state?.also {
            val e = BlockEntity.approxEquilibrium(BlockEntity.maxFuel, handler.coolantAmount())
            val w = ceil(e.div(barSize).times(78)).toInt()
            if (it.block is NuclearFiredGeneratorBlock) {
                if (!it[Properties.LIT]) {
                    if (w >= 0) {
                        drawTexture(matrices, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 34, 78, 3)
                        renderBarX(matrices, e.coerceAtMost(barSize), barSize, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 22, 78, 3)
                        if (w < 77) {
                            drawTexture(matrices, field_2776 + 105 + w, field_2800 + 26, backgroundWidth, 70, 5, 3)
                        }
                    } else {
                        drawTexture(matrices, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 22, 78, 3)
                    }
                } else {
                    if (w >= 0) {
                        renderBarX(matrices, e.coerceAtMost(barSize), barSize, field_2776 + 107, field_2776 + 30, 0, backgroundHeight + 28, 78, 3)
                        renderBarX(matrices, handler.outputAmount().coerceAtMost(barSize), barSize, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 31, 78, 3)

                        renderBarX(matrices, handler.outputAmount().coerceAtMost(e), e, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 25, w, 3)
                        if (w < 78) {
                            drawTexture(matrices, field_2776 + 105 + w, field_2800 + 26, backgroundWidth, 70, 5, 3)
                        }
                    } else {
                        drawTexture(matrices, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 28, 78, 3)
                        renderBarX(matrices, handler.outputAmount().coerceAtMost(barSize), barSize, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 25, 78, 3)
                    }
                }
            } else if (it.block is ExplodingNuclearFiredGeneratorBlock) {
                drawTexture(matrices, field_2776 + 107, field_2800 + 30, 0, backgroundHeight + 37 + blink.transformInt(3, 0), 78, 3)
            }
        }
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        super.drawForeground(matrices, mouseX, mouseY)
        state?.also {
            if (it.block is NuclearFiredGeneratorBlock) {
                textRenderer.draw(matrices, LooseEnergy.displayWithUnits(handler.outputAmount()) + "/t", 107f, 18f, if (handler.outputAmount() > BlockEntity.maxSafeOutput) {
                    0xf01800
                } else if (handler.outputAmount() > 175f) {
                    0xc98600
                } else {
                    4210752
                })
                textRenderer.draw(matrices, handler.coolantAmount().decimalFormat(force = true), 123f, 43f, 4210752)
                textRenderer.draw(matrices, handler.fuelAmount().decimalFormat(force = true), 123f, 68f, 4210752)
            } else if (it.block is ExplodingNuclearFiredGeneratorBlock) {
                textRenderer.draw(matrices, TranslatableText("container.lcc.nuclear_generator.meltdown", ceil(BlockEntity.maxMeltdownTicks.minus(handler.meltdownTicks()).div(20f)).toInt().coerceAtLeast(0)), 107f, 18f, blink.transformInt(0xf01800, 0xc98600))
            }
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, field_2776 + 38..field_2776 + 49, field_2800 + 39..field_2800 + 53)

        state?.also {
            if (it.block is NuclearFiredGeneratorBlock && it[Properties.LIT]) {
                val e = BlockEntity.approxEquilibrium(BlockEntity.maxFuel, handler.coolantAmount())
                if (mouseX in field_2776 + 85..field_2776 + 185 && mouseY in field_2800 + 15..field_2800 + 35) {
                    renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.nuclear_generator.output", LooseEnergy.displayWithUnits(handler.outputAmount().times(handler.waterAmount().div(3f))), LooseEnergy.displayWithUnits(handler.outputAmount()), LooseEnergy.displayWithUnits(BlockEntity.maxSafeOutput), if (e > 0f && e <= 10000f) LooseEnergy.displayWithUnits(e) else "∞ LE", handler.waterAmount().div(3f).times(100f).decimalFormat(force = true)), Int.MAX_VALUE), mouseX, mouseY)
                }
                if (mouseX in field_2776 + 105..field_2776 + 119 && mouseY in field_2800 + 39..field_2800 + 53) {
                    val rate = handler.outputAmount().times(0.0003f).plus(0.01f)
                    renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.nuclear_generator.coolant", handler.coolantAmount().decimalFormat(force = true), rate.decimalFormat(4, force = true)), Int.MAX_VALUE), mouseX, mouseY)
                }
                if (mouseX in field_2776 + 105..field_2776 + 119 && mouseY in field_2800 + 64..field_2800 + 80) {
                    val rate = MathHelper.sqrt(BlockEntity.maxFuel - handler.fuelAmount()).times(0.0022f).plus(0.005f)
                    val value = run { BlockEntity.getFuelValue(client?.world ?: return@run 1f, handler.pos ?: return@run 1f) }
                    renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.nuclear_generator.fuel", handler.fuelAmount().decimalFormat(force = true), value, rate.decimalFormat(4, force = true)), Int.MAX_VALUE), mouseX, mouseY)
                }
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