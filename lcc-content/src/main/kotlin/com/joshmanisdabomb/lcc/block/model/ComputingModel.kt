package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.LCCComputerModules
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.prefix
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.lib.block.model.LCCModel
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.block.enums.SlabType
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.block.BlockModels
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.state.property.Properties
import net.minecraft.tag.RequiredTagListRegistry.forEach
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import kotlin.text.Typography.half

class ComputingModel : LCCModel({ mapOf("particle" to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/computer_casing"))) }) {

    private val particle by lazy { bakedSprites["particle"]!! }

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        val be = renderView.getBlockEntity(pos) as? ComputingBlockEntity
        val rand = random.get()

        if (state[Properties.SLAB_TYPE] != SlabType.BOTTOM) {
            val cUp = false //TODO connections
            val cDown = false
            val half = be?.getHalf(true) ?: topDefault
            val modelId = half.module.id.prefix("block/", "").suffix("top")

            val model = BakedModelManagerHelper.getModel(MinecraftClient.getInstance().bakedModelManager, modelId)
            (model as? FabricBakedModel)?.emitBlockQuads(renderView, state, pos, random, renderContext)
        }

        if (state[Properties.SLAB_TYPE] != SlabType.TOP) {
            val cUp = false //TODO connections
            val cDown = false
            val half = be?.getHalf(false) ?: bottomDefault
            val modelId = half.module.id.prefix("block/", "")

            val model = BakedModelManagerHelper.getModel(MinecraftClient.getInstance().bakedModelManager, modelId)
            (model as? FabricBakedModel)?.emitBlockQuads(renderView, state, pos, random, renderContext)
        }
    }

    override fun emitItemQuads(stack: ItemStack, random: Supplier<Random>, renderContext: RenderContext) {

    }

    override fun getParticleSprite(): Sprite {
        return particle
    }

    override fun loadExtraModels(manager: ResourceManager, loader: Consumer<Identifier>) {
        LCCRegistries.computer_modules.entries.forEach { (k, v) ->
            val id = k.value.prefix("block/", "")
            loader.accept(id)
            loader.accept(id.suffix("up"))
            loader.accept(id.suffix("down"))
            loader.accept(id.suffix("both"))
            loader.accept(id.suffix("top"))
            loader.accept(id.suffix("top_up"))
            loader.accept(id.suffix("top_down"))
            loader.accept(id.suffix("top_both"))
        }
    }

    companion object {
        val topDefault = ComputingBlockEntity.ComputingHalf(LCCComputerModules.computer_casing, Direction.NORTH, 0xFFF7EE, true)
        val bottomDefault = ComputingBlockEntity.ComputingHalf(LCCComputerModules.computer_casing, Direction.NORTH, 0xFFF7EE, false)
    }

}