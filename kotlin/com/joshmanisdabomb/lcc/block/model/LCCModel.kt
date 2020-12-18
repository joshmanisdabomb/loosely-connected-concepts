package com.joshmanisdabomb.lcc.block.model

import com.mojang.datafixers.util.Pair
import net.fabricmc.fabric.api.renderer.v1.RendererAccess
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.render.model.ModelBakeSettings
import net.minecraft.client.render.model.ModelLoader
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.client.texture.Sprite
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Function
import java.util.function.Supplier


abstract class LCCModel(spriteGetter: LCCModel.() -> Map<String, SpriteIdentifier>) : UnbakedModel, BakedModel, FabricBakedModel {

    open val folder = "block"

    protected val unbakedSprites by lazy { this.spriteGetter() }
    protected val bakedSprites get() = baked

    private lateinit var baked: Map<String, Sprite>

    override fun getModelDependencies() = emptyList<Identifier>()

    override fun getTextureDependencies(unbakedModelGetter: Function<Identifier, UnbakedModel>, unresolvedTextureReferences: MutableSet<Pair<String, String>>) = unbakedSprites.values

    override fun bake(loader: ModelLoader, textureGetter: Function<SpriteIdentifier, Sprite>, rotationContainer: ModelBakeSettings, modelId: Identifier): BakedModel {
        baked = unbakedSprites.mapValues { (_, v) -> textureGetter.apply(v) }
        return this
    }

    override fun getQuads(state: BlockState?, face: Direction?, random: Random) = null

    override fun useAmbientOcclusion() = true

    override fun hasDepth() = false

    override fun isSideLit() = false

    override fun isBuiltin() = false

    override fun getTransformation() = null

    override fun getOverrides() = null

    override fun isVanillaAdapter() = false

    abstract override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext)

    abstract override fun emitItemQuads(stack: ItemStack, random: Supplier<Random>, renderContext: RenderContext)

    protected fun emitFace(direction: Direction, renderContext: RenderContext, sprite: Sprite, pos1: Vec3f = Vec3f(0f, 0f, 0f), pos2: Vec3f = Vec3f(1f, 1f, 1f), faceTransform: (FloatArray) -> Unit = {}) {
        renderContext.emitter
            .cubeFace(direction, pos1, pos2, faceTransform)
            ?.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV)
            ?.lightmap(0, 15)?.lightmap(1, 15)?.lightmap(2, 15)?.lightmap(3, 15)
            ?.spriteColor(0, -1, -1, -1, -1)
            ?.material(RendererAccess.INSTANCE.renderer!!.materialFinder().find())
            ?.emit()
    }

    companion object {
        fun QuadEmitter.cubeFace(direction: Direction, pos1: Vec3f, pos2: Vec3f, faceTransform: (FloatArray) -> Unit = {}): QuadEmitter? {
            val left: Float
            val bottom: Float
            val right: Float
            val top: Float
            val depth: Float
            //sometimes hardcoding is the easiest way
            when (direction) {
                Direction.NORTH -> {
                    left = 1 - pos2.x
                    bottom = pos1.y
                    right = 1 - pos1.x
                    top = pos2.y
                    depth = pos1.z
                }
                Direction.EAST -> {
                    left = 1 - pos2.z
                    bottom = pos1.y
                    right = 1 - pos1.z
                    top = pos2.y
                    depth = 1 - pos2.x
                }
                Direction.SOUTH -> {
                    left = pos1.x
                    bottom = pos1.y
                    right = pos2.x
                    top = pos2.y
                    depth = 1 - pos2.z
                }
                Direction.WEST -> {
                    left = pos1.z
                    bottom = pos1.y
                    right = pos2.z
                    top = pos2.y
                    depth = pos1.x
                }
                Direction.UP -> {
                    left = pos1.x
                    bottom = 1 - pos2.z
                    right = pos2.x
                    top = 1 - pos1.z
                    depth = 1 - pos2.y
                }
                Direction.DOWN -> {
                    left = pos1.x
                    bottom = pos1.z
                    right = pos2.x
                    top = pos2.z
                    depth = pos1.y
                }
            }
            val parameters = floatArrayOf(left, bottom, right, top, depth).also(faceTransform)
            if (parameters[2] <= parameters[0] || parameters[3] <= parameters[1]) return null
            return square(direction, parameters[0], parameters[1], parameters[2], parameters[3], parameters[4])
        }
    }

}
