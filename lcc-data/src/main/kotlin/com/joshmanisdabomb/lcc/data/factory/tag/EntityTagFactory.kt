package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import net.minecraft.entity.EntityType
import net.minecraft.tag.TagKey

class EntityTagFactory(vararg val tags: TagKey<EntityType<*>>) : EntityDataFactory {

    override fun apply(data: DataAccessor, entry: EntityType<*>) {
        tags.forEach { data.tags.entity(it).attach(entry) }
    }

}
