package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.ClassicCryingObsidianBlockEntity
import com.joshmanisdabomb.lcc.lib.block.model.LCCModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Supplier

class ClassicCryingObsidianModel() : LCCModel({ mapOf("inactive" to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/classic_crying_obsidian_off")), "active" to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/classic_crying_obsidian_on")), "static" to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/classic_crying_obsidian_static"))) }) {

    private val inactive by lazy { bakedSprites["inactive"]!! }
    private val active by lazy { bakedSprites["active"]!! }
    private val static by lazy { bakedSprites["static"]!! }

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        var flag = false
        run {
            val be = renderView.getBlockEntity(pos) as? ClassicCryingObsidianBlockEntity ?: return@run
            flag = be.isActive(MinecraftClient.getInstance().player ?: return@run)
        }

        for (d in Direction.values()) {
            emitFace(d, renderContext, if (flag) active else inactive)
        }
    }

    override fun emitItemQuads(stack: ItemStack, random: Supplier<Random>, renderContext: RenderContext) {

    }

    override fun getParticleSprite(): Sprite {
        return static
    }

}