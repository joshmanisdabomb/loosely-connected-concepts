package com.joshmanisdabomb.lcc.data.container

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import net.minecraft.entity.EntityType

class EntityDataContainer(accessor: DataAccessor) : DataContainer<EntityType<*>, EntityDataFactory>(accessor) {

    override fun affects(entry: EntityType<*>) = super.affects(entry).let { this }

    override fun affects(entries: List<EntityType<*>>) = super.affects(entries).let { this }

    override fun add(factory: EntityDataFactory) = super.add(factory).let { this }

    override fun apply(factory: EntityDataFactory, entry: EntityType<*>) {
        factory.apply(accessor, entry)
    }

}
