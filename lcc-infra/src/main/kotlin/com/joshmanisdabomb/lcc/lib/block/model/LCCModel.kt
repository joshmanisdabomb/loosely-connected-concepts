package com.joshmanisdabomb.lcc.lib.block.model

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
import net.minecraft.resource.ResourceManager
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Matrix3f
import net.minecraft.util.math.Vec3f
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Consumer
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

    open fun loadExtraModels(manager: ResourceManager, loader: Consumer<Identifier>) = Unit

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

        fun quadRotate(x: BlockRotation = BlockRotation.NONE, y: BlockRotation = BlockRotation.NONE, z: BlockRotation = BlockRotation.NONE) : RenderContext.QuadTransform {
            return RenderContext.QuadTransform {
                val nominal = it.nominalFace()
                val nominalDir = nominal.unitVector
                nominalDir.rotate(Vec3f.NEGATIVE_X.getDegreesQuaternion(x.ordinal * 90.0f))
                nominalDir.rotate(Vec3f.NEGATIVE_Y.getDegreesQuaternion(y.ordinal * 90.0f))
                nominalDir.rotate(Vec3f.NEGATIVE_Z.getDegreesQuaternion(z.ordinal * 90.0f))
                it.nominalFace(Direction.getFacing(nominalDir.x, nominalDir.y, nominalDir.z))

                val cull = it.cullFace()
                if (cull != null) {
                    val cullDir = cull.unitVector
                    cullDir.rotate(Vec3f.NEGATIVE_X.getDegreesQuaternion(x.ordinal * 90.0f))
                    cullDir.rotate(Vec3f.NEGATIVE_Y.getDegreesQuaternion(y.ordinal * 90.0f))
                    cullDir.rotate(Vec3f.NEGATIVE_Z.getDegreesQuaternion(z.ordinal * 90.0f))
                    it.cullFace(Direction.getFacing(cullDir.x, cullDir.y, cullDir.z))
                }

                val positions = arrayOfNulls<Vec3f>(4)
                val normals = arrayOfNulls<Vec3f>(4)
                for (i in 0..3) {
                    val pos = it.copyPos(i, null)
                    pos.add(-0.5f, -0.5f, -0.5f)
                    pos.rotate(Vec3f.NEGATIVE_X.getDegreesQuaternion(x.ordinal * 90.0f))
                    pos.rotate(Vec3f.NEGATIVE_Y.getDegreesQuaternion(y.ordinal * 90.0f))
                    pos.rotate(Vec3f.NEGATIVE_Z.getDegreesQuaternion(z.ordinal * 90.0f))
                    pos.add(0.5f, 0.5f, 0.5f)
                    positions[(i)%4] = pos

                    val normal = it.copyNormal(i, null)
                    if (normal != null) {
                        normal.rotate(Vec3f.NEGATIVE_X.getDegreesQuaternion(x.ordinal * 90.0f))
                        normal.rotate(Vec3f.NEGATIVE_Y.getDegreesQuaternion(y.ordinal * 90.0f))
                        normal.rotate(Vec3f.NEGATIVE_Z.getDegreesQuaternion(z.ordinal * 90.0f))
                        normals[(i)%4] = normal
                    }
                }
                for (i in 0..3) {
                    //TODO if rotation applied on axis, rotate vertice indexes
                    it.pos(i, positions[i])
                    if (normals[i] != null) it.normal(i, normals[i])
                }
                true
            }
        }
    }

}
