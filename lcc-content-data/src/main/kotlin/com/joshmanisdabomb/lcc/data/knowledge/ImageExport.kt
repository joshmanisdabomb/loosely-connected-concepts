package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.stack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.DiffuseLighting
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.NativeImage
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.text.TranslatableText
import java.io.File
import kotlin.system.exitProcess

class ImageExport(items: List<ItemConvertible>, val folder: File, val test: Boolean = false) : DataProvider {

    val items = items.map(net.minecraft.item.ItemConvertible::asItem).distinctBy { it.identifier }

    override fun run(cache: DataCache) {
        MinecraftClient.getInstance().setScreen(ExportOutput())
        //TODO try wait for screen
    }

    override fun getName() = "LCC Wiki Image Export"

    inner class ExportOutput internal constructor() : Screen(TranslatableText("narrator.screen.title")) {
        var index = 0

        override fun init() {
            super.init()
            folder.mkdir()
            println("Starting export of ${items.count()} items to ${folder}. This may take a while.")
        }

        override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
            //Setup
            MinecraftClient.getInstance().textureManager.getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false)
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
            RenderSystem.enableBlend()
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA)
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
            val m = RenderSystem.getModelViewStack()
            m.push()
            m.translate(width.div(2.0), height.div(2.0), 200.0);
            m.scale(1.0f, -1.0f, 1.0f)
            m.scale(320.0f, 320.0f, 320.0f)
            RenderSystem.applyModelViewMatrix()
            val immediate = MinecraftClient.getInstance().bufferBuilders.entityVertexConsumers
            val buffer = MinecraftClient.getInstance().framebuffer
            itemRenderer.zOffset = 100f

            //Get Next Item and Filename
            val item = items[index]

            val id = item.identifier
            val folder2 = File(folder, id.namespace)
            folder2.mkdir()
            val file = File(folder2, id.path + ".png")

            //Render and Screenshot
            val stack = item.stack()
            val model = itemRenderer.getHeldItemModel(stack, null, MinecraftClient.getInstance().player, index)
            if (!model.isSideLit) DiffuseLighting.disableGuiDepthLighting()
            itemRenderer.renderItem(stack, ModelTransformation.Mode.GUI, false, matrices, immediate, 15728880, OverlayTexture.DEFAULT_UV, model)
            immediate.draw()
            RenderSystem.enableDepthTest()
            if (!model.isSideLit) DiffuseLighting.enableGuiDepthLighting()

            if (!test && item != Items.AIR) {
                val image = NativeImage(buffer.textureWidth, buffer.textureHeight, false)
                RenderSystem.bindTexture(buffer.colorAttachment)
                image.loadFromTextureImage(0, false)
                image.mirrorVertically()
                image.writeTo(file)
                image.close()
            }

            println("Exported ${item.identifier} to folder $folder2.")

            //Clear Screen
            if (!test) {
                buffer.setClearColor(0f, 0f, 0f, 0f)
                buffer.clear(false)
                buffer.setClearColor(1f, 1f, 1f, 1f)
            } else {
                Thread.sleep(250)
            }

            //Desetup
            m.pop()
            RenderSystem.applyModelViewMatrix()

            //Exit When Done
            if (index++ >= items.lastIndex) {
                println("Finished export of ${items.count()} items to ${folder}. Exiting successfully.")
                exitProcess(0)
            }
        }
    }

}