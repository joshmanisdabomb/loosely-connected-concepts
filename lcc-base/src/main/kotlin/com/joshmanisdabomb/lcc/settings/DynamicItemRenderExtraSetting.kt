package com.joshmanisdabomb.lcc.settings

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.block.Block
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.item.Item
import net.minecraft.resource.ResourceType

class DynamicItemRenderExtraSetting(val renderer: () -> (context: BlockEntityRendererFactory.Context?) -> BuiltinItemRendererRegistry.DynamicItemRenderer) : ExtraSetting {

    override fun initBlock(block: Block) = Unit

    override fun initBlockClient(block: Block) = Unit

    override fun initItem(item: Item) = Unit

    override fun initItemClient(item: Item) {
        val r = renderer()(null)
        BuiltinItemRendererRegistry.INSTANCE.register(item, r)
        (r as? IdentifiableResourceReloadListener)?.apply { ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(r) }
    }

    companion object {
        fun <T : ItemExtraSettings> T.dynamicItemRender(renderer: () -> (context: BlockEntityRendererFactory.Context?) -> BuiltinItemRendererRegistry.DynamicItemRenderer) = this.add(DynamicItemRenderExtraSetting(renderer)).let { this }
    }

}