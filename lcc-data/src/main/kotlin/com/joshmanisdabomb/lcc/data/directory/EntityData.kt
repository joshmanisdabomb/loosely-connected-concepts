package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.EntityDataContainer
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.ThingDirectory

object EntityData : ThingDirectory<EntityDataContainer, Unit>() {

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        all.forEach { (k, v) -> v.init(k, LCCEntities[k]) }

        val missing = LCCEntities.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCEntities[it]!!; defaults().init(key, it) }
    }

    fun defaults() = EntityDataContainer().defaultLang()

    private fun EntityDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)

}