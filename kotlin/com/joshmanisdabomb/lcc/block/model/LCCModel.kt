package com.joshmanisdabomb.lcc.block.model

import com.mojang.datafixers.util.Pair
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

}
