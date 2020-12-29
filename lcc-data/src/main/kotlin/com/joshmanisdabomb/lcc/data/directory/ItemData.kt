package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.ItemDataContainer
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.LiteralTranslationFactory
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.ThingDirectory

object ItemData : ThingDirectory<ItemDataContainer, Unit>() {

    val gauntlet by createWithName { defaults().add(LiteralTranslationFactory("Doom Gauntlet")) }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        (all.plus(LCCItems.all.mapValues { (k, v) -> all[k] ?: defaults() })).forEach { (k, v) -> v.init(k, LCCItems[k]) }
    }

    fun defaults() = ItemDataContainer().add(BasicTranslationFactory).add(BritishTranslationFactory())

}