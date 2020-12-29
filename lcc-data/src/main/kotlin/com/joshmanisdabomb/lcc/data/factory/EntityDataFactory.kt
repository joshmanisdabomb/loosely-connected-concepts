package com.joshmanisdabomb.lcc.data.factory

import com.joshmanisdabomb.lcc.DataAccessor
import net.minecraft.entity.EntityType

interface EntityDataFactory {

    fun apply(data: DataAccessor, entry: EntityType<*>)

}