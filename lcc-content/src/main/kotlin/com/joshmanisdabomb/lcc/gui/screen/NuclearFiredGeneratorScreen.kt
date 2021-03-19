package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCItems
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
import net.minecraft.item.Items
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class NuclearFiredGeneratorScreen(handler: NuclearFiredGeneratorScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<NuclearFiredGeneratorScreenHandler>(handler, inventory, title), PowerScreenUtils {

    val listener = ::onChanged
    val toggle: FunctionalButtonWidget by lazy { ToggleButton(x + 12, y + 35) {
        run {
            val world = MinecraftClient.getInstance().world?.registryKey ?: return@run
            val pos = handler.pos ?: return@run
            ClientPlayNetworking.send(LCCPacketsToServer[LCCPacketsToServer::nuclear_generator_toggle].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeIdentifier(world.value); writeBlockPos(pos) })
        }
        this.onClose()
        return@ToggleButton null
    } }

    private var _ticks = 0
    override val ticks get() = _ticks

    override val translationKey = "container.lcc.generator"

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override val power_v = 56

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
        addButton(toggle)
        onChanged(handler.inventory)
    }

    override fun resize(client: MinecraftClient, width: Int, height: Int) {
        super.resize(client, width, height)
        toggle.x = x + 12
        toggle.y = y + 35
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::method_34542)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        renderPower(matrices, handler.powerAmount(), LooseEnergy.toStandard(200f), x + 38, y + 39)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 38..x + 49, y + 39..y + 53)

        for (widget in buttons) {
            if (widget.isHovered) widget.renderToolTip(matrices, mouseX, mouseY)
            break
        }
    }

    override fun tick() {
        super.tick()
        _ticks += 1;
        toggle.active = handler.powerAmount() >= LooseEnergy.toStandard(200f) && handler.inventory[0].isOf(Items.TNT) && handler.inventory[1].isOf(LCCItems.enriched_uranium_nugget)
    }

    fun onChanged(inventory: Inventory) {

    }

    companion object {
        val texture = LCC.gui("container/nuclear_generator")
    }

    private inner class ToggleButton(x: Int, y: Int, pressed: () -> Int?) : FunctionalButtonWidget(x, y, 22, 22, 22, 22, TranslatableText("gui.lcc.nuclear_generator.toggle"), { matrices, x, y -> this@NuclearFiredGeneratorScreen.renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("gui.lcc.nuclear_generator.activate"), Int.MAX_VALUE), x, y) }, pressed) {

        init {
            active = false
            texture = Companion.texture
            ix = width * 4
            sx = 0
            sy = backgroundHeight
        }

    }

}