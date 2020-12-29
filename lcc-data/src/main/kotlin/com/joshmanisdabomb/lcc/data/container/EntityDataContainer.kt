package com.joshmanisdabomb.lcc.data.container

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import net.minecraft.entity.EntityType

class EntityDataContainer : DataContainer<EntityType<*>, EntityDataFactory>() {

    override fun add(factory: EntityDataFactory) = super.add(factory).let { this }

    override fun apply(factory: EntityDataFactory, entry: EntityType<*>) {
        factory.apply(LCCData.accessor, entry)
    }

}
