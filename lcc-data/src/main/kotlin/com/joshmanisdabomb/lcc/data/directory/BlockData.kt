package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.LiteralTranslationFactory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.ThingDirectory

object BlockData : ThingDirectory<BlockDataContainer, Unit>() {

    val time_rift by createWithName { defaults().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")) }
    val spawner_table by createWithName { defaults().add(LiteralTranslationFactory("Arcane Table")) }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        (all.plus(LCCBlocks.all.mapValues { (k, v) -> all[k] ?: defaults() })).forEach { (k, v) -> v.init(k, LCCBlocks[k]) }
    }

    fun defaults() = BlockDataContainer().add(BasicTranslationFactory).add(BritishTranslationFactory())

}