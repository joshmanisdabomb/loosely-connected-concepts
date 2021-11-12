package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.sqrt
import com.joshmanisdabomb.lcc.extensions.stack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.Framebuffer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.recipebook.ClientRecipeBook
import net.minecraft.client.render.Camera
import net.minecraft.client.render.DiffuseLighting
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.NativeImage
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.world.World

import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.network.ClientConnection
import net.minecraft.network.NetworkSide
import net.minecraft.stat.StatHandler
import net.minecraft.tag.BlockTags
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.Vec3f
import net.minecraft.util.registry.Registry
import net.minecraft.world.Difficulty
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.dimension.DimensionType.OVERWORLD_ID
import java.io.File
import java.util.*
import kotlin.math.atan
import kotlin.math.max
import kotlin.math.pow
import kotlin.system.exitProcess

class ImageExport(items: List<ItemConvertible>, entities: List<EntityType<*>>, val folder: File, val test: Long? = null) : DataProvider {

    val items = items.map(ItemConvertible::asItem).distinctBy { it.identifier }
    val entities = entities.distinctBy { Registry.ENTITY_TYPE.getId(it) }
    var index = 0

    override fun run(cache: DataCache) {
        MinecraftClient.getInstance().setScreen(ExportOutput())
        //TODO try wait for screen
    }

    override fun getName() = "LCC Wiki Image Export"

    inner class ExportOutput internal constructor() : Screen(TranslatableText("narrator.screen.title")) {

        override fun init() {
            super.init()
            folder.mkdir()
            println("Starting export of ${items.count()} items and ${entities.count()} entities to ${folder}. This may take a while.")
        }

        override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
            //Setup
            val m = RenderSystem.getModelViewStack()
            m.push()

            val buffer = MinecraftClient.getInstance().framebuffer

            //Get Next Item and Filename
            if (index >= items.count()) {
                renderEntity(entities[index - items.count()], m, buffer)
            } else {
                renderItem(items[index], m, matrices, buffer)
            }

            //Clear Screen
            if (test == null) {
                buffer.setClearColor(0f, 0f, 0f, 0f)
                buffer.clear(false)
                buffer.setClearColor(1f, 1f, 1f, 1f)
            } else {
                Thread.sleep(test)
            }

            //Desetup
            m.pop()
            RenderSystem.applyModelViewMatrix()

            //Exit When Done
            if (++index >= items.count() + entities.count()) {
                println("Finished export of ${items.count()} items and ${entities.count()} entities to ${folder}. Exiting successfully.")
                exitProcess(0)
            }
        }

        private fun renderItem(item: Item, matrices: MatrixStack, matrices2: MatrixStack, buffer: Framebuffer) {
            MinecraftClient.getInstance().textureManager.getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false)
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
            RenderSystem.enableBlend()
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA)
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
            matrices.push()
            matrices.translate(width.div(2.0), height.div(2.0), 200.0)
            matrices.scale(1.0f, -1.0f, 1.0f)
            matrices.scale(320.0f, 320.0f, 320.0f)
            RenderSystem.applyModelViewMatrix()
            val immediate = MinecraftClient.getInstance().bufferBuilders.entityVertexConsumers
            itemRenderer.zOffset = 100f

            val id = item.identifier
            val file = getPath(id.namespace, if (item is BlockItem) "block" else "item", id.path)

            //Render and Screenshot
            val stack = item.stack()
            val model = itemRenderer.getModel(stack, null, MinecraftClient.getInstance().player, index)
            if (!model.isSideLit) DiffuseLighting.disableGuiDepthLighting()
            itemRenderer.renderItem(stack, ModelTransformation.Mode.GUI, false, matrices2, immediate, 15728880, OverlayTexture.DEFAULT_UV, model)
            immediate.draw()
            RenderSystem.enableDepthTest()
            if (!model.isSideLit) DiffuseLighting.enableGuiDepthLighting()

            if (test == null && item != Items.AIR) {
                screenshot(buffer, file)
            }

            matrices.pop()

            println("Exported ${item.identifier} to folder ${file.parent}.")
        }

        private fun renderEntity(entity: EntityType<*>, matrices: MatrixStack, buffer: Framebuffer) {
            val id = Registry.ENTITY_TYPE.getId(entity)
            val file = getPath(id.namespace, "entity", id.path)

            //Render and Screenshot
            val instance = entity.create(World)

            if (instance != null) {
                val f = atan((0 / 40.0f).toDouble()).toFloat()
                val g = atan((0 / 40.0f).toDouble()).toFloat()
                val matrixStack = RenderSystem.getModelViewStack()
                matrixStack.push()
                matrixStack.translate(width.div(2.0), height.minus(100.0), 1050.0)
                matrixStack.scale(1.0f, 1.0f, -1.0f)
                RenderSystem.applyModelViewMatrix()
                val matrixStack2 = MatrixStack()
                matrixStack2.translate(0.0, 0.0, 1000.0)
                println(instance)
                println(instance.boundingBox)
                val size = max(max(instance.boundingBox.xLength, instance.boundingBox.yLength), instance.boundingBox.zLength).pow(1.6).sqrt().coerceAtLeast(0.5).toFloat()
                matrixStack2.scale(170f / size, 170f / size, 170f / size)
                val quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0f)
                val quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0f)
                quaternion.hamiltonProduct(quaternion2)
                matrixStack2.multiply(quaternion)
                matrixStack2.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(30.0f))
                matrixStack2.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(135.0f))
                var h: Float? = null
                val i: Float = instance.yaw
                val j: Float = instance.pitch
                var k: Float? = null
                val l: Float = instance.headYaw
                instance.yaw = 0f
                instance.pitch = 0f
                instance.headYaw = instance.yaw
                if (instance is LivingEntity) {
                    h = instance.bodyYaw
                    k = instance.prevHeadYaw
                    instance.bodyYaw = 0f
                    instance.prevHeadYaw = instance.yaw
                }
                DiffuseLighting.method_34742()
                val entityRenderDispatcher = MinecraftClient.getInstance().entityRenderDispatcher
                quaternion2.conjugate()
                entityRenderDispatcher.rotation = quaternion2
                entityRenderDispatcher.setRenderShadows(false)
                val immediate = MinecraftClient.getInstance().bufferBuilders.entityVertexConsumers
                RenderSystem.runAsFancy {
                    entityRenderDispatcher.camera = Camera()
                    try {
                        MinecraftClient.getInstance().player = Player
                        MinecraftClient.getInstance().world = World
                        entityRenderDispatcher.render(instance, 0.0, 0.0, 0.0, 0.0f, 1.0f, matrixStack2, immediate, 15728880)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        MinecraftClient.getInstance().player = null
                        MinecraftClient.getInstance().world = null
                    }
                }
                immediate.draw()
                entityRenderDispatcher.setRenderShadows(true)
                instance.yaw = i
                instance.pitch = j
                instance.headYaw = l
                if (instance is LivingEntity) {
                    instance.bodyYaw = h!!
                    instance.prevHeadYaw = k!!
                }
                matrixStack.pop()
                RenderSystem.applyModelViewMatrix()
                DiffuseLighting.enableGuiDepthLighting()
            }

            if (test == null) {
                screenshot(buffer, file)
            }

            println("Exported $id to folder ${file.parent}.")
        }

        private fun getPath(namespace: String, type: String, path: String): File {
            val folder2 = File(folder, namespace)
            folder2.mkdir()
            val folder3 = File(folder2, type)
            folder3.mkdir()
            return File(folder3, "$path.png")
        }

        private fun screenshot(buffer: Framebuffer, file: File) {
            val image = NativeImage(buffer.textureWidth, buffer.textureHeight, false)
            RenderSystem.bindTexture(buffer.colorAttachment)
            image.loadFromTextureImage(0, false)
            image.mirrorVertically()
            image.writeTo(file)
            image.close()
        }

    }

    private object NetworkHandler : ClientPlayNetworkHandler(MinecraftClient.getInstance(), null, ClientConnection(NetworkSide.CLIENTBOUND), MinecraftClient.getInstance().session.profile, null) {

    }

    private object World : ClientWorld(NetworkHandler, Properties(Difficulty.EASY, false, false), null, DimensionType.create(OptionalLong.empty(), true, false, false, false, 1.0, false, false, false, false, false, -16, 32, 32, BlockTags.INFINIBURN_OVERWORLD.id, OVERWORLD_ID, 1f), 0, 0, { null }, null, false, 0L) {

    }

    private object Player : ClientPlayerEntity(MinecraftClient.getInstance(), World, NetworkHandler, StatHandler(), ClientRecipeBook(), false, false) {

    }

}