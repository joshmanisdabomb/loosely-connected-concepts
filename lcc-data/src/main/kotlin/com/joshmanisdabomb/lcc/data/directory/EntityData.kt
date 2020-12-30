package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.EntityDataContainer
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.ThingDirectory

object EntityData : ThingDirectory<EntityDataContainer, Unit>() {

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        (all.plus(LCCEntities.all.mapValues { (k, v) -> all[k] ?: defaults() })).forEach { (k, v) -> v.init(k, LCCEntities[k]) }
    }

    fun defaults() = EntityDataContainer()

}