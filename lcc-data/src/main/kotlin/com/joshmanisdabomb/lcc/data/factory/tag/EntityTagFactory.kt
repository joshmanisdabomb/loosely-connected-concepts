package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import net.minecraft.entity.EntityType
import net.minecraft.tag.Tag

class EntityTagFactory(vararg val tags: Tag<EntityType<*>>) : EntityDataFactory {

    override fun apply(data: DataAccessor, entry: EntityType<*>) {
        tags.forEach { data.tags.entity(it as Tag.Identified<EntityType<*>>).append(entry) }
    }

}
