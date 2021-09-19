package com.joshmanisdabomb.lcc.settings

import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry
import net.minecraft.block.Block
import net.minecraft.client.item.ModelPredicateProvider
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class ModelPredicateExtraSetting(val id: Identifier, val provider: (item: Item) -> () -> ModelPredicateProvider) : ExtraSetting {

    override fun initBlock(block: Block) = Unit

    override fun initBlockClient(block: Block) = Unit

    override fun initItem(item: Item) = Unit

    override fun initItemClient(item: Item) {
        FabricModelPredicateProviderRegistry.register(item, id, provider(item)())
    }

    companion object {
        fun <T : ItemExtraSettings> T.modelPredicate(id: Identifier, provider: (item: Item) -> () -> ModelPredicateProvider) = this.add(ModelPredicateExtraSetting(id, provider)).let { this }
    }

}