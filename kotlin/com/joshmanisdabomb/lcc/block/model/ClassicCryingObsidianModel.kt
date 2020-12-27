package com.joshmanisdabomb.lcc.block.model

import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.texture.Sprite
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Supplier

class ClassicCryingObsidianModel() : LCCModel({ mapOf(/*"inactive"*/) }) {

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        //MinecraftClient.getInstance().world.spaw
    }

    override fun emitItemQuads(stack: ItemStack, random: Supplier<Random>, renderContext: RenderContext) {

    }

    override fun getSprite(): Sprite {
        return bakedSprites["inactive"]!!
    }

}