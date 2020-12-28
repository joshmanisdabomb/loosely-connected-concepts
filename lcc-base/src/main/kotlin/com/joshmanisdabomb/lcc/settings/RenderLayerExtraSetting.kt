package com.joshmanisdabomb.lcc.settings

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.Block
import net.minecraft.client.render.RenderLayer
import net.minecraft.item.Item

enum class RenderLayerExtraSetting(val layer: () -> () -> RenderLayer) : ExtraSetting {

    CUTOUT({ RenderLayer::getCutout }),
    CUTOUT_MIPPED({ RenderLayer::getCutoutMipped }),
    TRANSLUCENT({ RenderLayer::getTranslucent });

    override fun initBlock(block: Block) = Unit

    override fun initBlockClient(block: Block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, layer()())
    }

    override fun initItem(item: Item) = Unit

    override fun initItemClient(item: Item) = Unit

    companion object {
        fun <T : BlockExtraSettings> T.renderType(layer: RenderLayerExtraSetting) = this.add(layer).let { this }

        fun <T : BlockExtraSettings> T.cutout() = this.add(CUTOUT).let { this }
        fun <T : BlockExtraSettings> T.cutoutMipped() = this.add(CUTOUT_MIPPED).let { this }
        fun <T : BlockExtraSettings> T.translucent() = this.add(TRANSLUCENT).let { this }
    }

}