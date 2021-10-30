package com.joshmanisdabomb.lcc.data.factory.loot.entity

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import net.minecraft.entity.EntityType
import net.minecraft.loot.LootTable

class CustomEntityLootFactory(val loot: LootTable.Builder) : EntityDataFactory {

    override fun apply(data: DataAccessor, entry: EntityType<*>) {
        data.lootTables.addEntity(entry, loot)
    }

}
