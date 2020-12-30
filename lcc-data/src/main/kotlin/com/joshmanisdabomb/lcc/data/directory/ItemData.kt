package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.ItemDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.item.DynamicItemAssetFactory
import com.joshmanisdabomb.lcc.data.factory.asset.item.GeneratedItemAssetFactory
import com.joshmanisdabomb.lcc.data.factory.asset.item.HandheldItemAssetFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.LiteralTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.TransformTranslationFactory
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import net.minecraft.item.ArmorItem
import net.minecraft.item.ToolItem

object ItemData : ThingDirectory<ItemDataContainer, Unit>() {

    val equipment by createWithName { ItemDataContainer().affects(LCCItems.all.values.filter { it is ToolItem || it is ArmorItem }.toList()).defaultLang().add(HandheldItemAssetFactory) }

    val full_hearts by createWithName { ItemDataContainer().affects(LCCItems.heart_full.values.toList()).defaultLang().defaultItemAsset().add(TransformTranslationFactory("en_us", "en_gb") { it.replace(" Full", "") }) }

    val gauntlet by createWithName { ItemDataContainer().add(DynamicItemAssetFactory).add(LiteralTranslationFactory("Doom Gauntlet")) }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        all.forEach { (k, v) -> v.init(k, LCCItems[k]) }

        val missing = LCCItems.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCItems[it]!!; defaults().init(key, it) }
    }

    fun defaults() = ItemDataContainer().defaultLang().defaultItemAsset()

    private fun ItemDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun ItemDataContainer.defaultItemAsset() = add(GeneratedItemAssetFactory)

}