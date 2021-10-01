package com.joshmanisdabomb.lcc.data.storage

import com.joshmanisdabomb.lcc.mixin.data.common.ItemEntryAccessor
import com.joshmanisdabomb.lcc.mixin.data.common.LootPoolAccessor
import com.joshmanisdabomb.lcc.mixin.data.common.LootTableAccessor
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.loot.LootTable
import net.minecraft.loot.context.LootContextType
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.util.Identifier

class LootTableStore {

    private val map = mutableMapOf<Identifier, LootTable.Builder>()
    private val builds = mutableMapOf<Identifier, LootTable>()

    private val items = mutableMapOf<Identifier, List<Item>>()

    fun add(type: LootContextType, id: Identifier, table: LootTable.Builder) {
        map[id] = table
        val build = table.build()
        builds[id] = build

        val entries = (build as LootTableAccessor).pools.flatMap { (it as LootPoolAccessor).poolEntries.toList() }
        items[id] = entries.filterIsInstance<ItemEntry>().map { (it as ItemEntryAccessor).item }
    }

    operator fun get(id: Identifier) = map[id]
    operator fun get(block: Block) = get(block.lootTableId)
    operator fun get(entity: EntityType<*>) = get(entity.lootTableId)

    operator fun get(table: LootTable.Builder) = map.filterValues { it == table }.keys.firstOrNull()

    fun getTable(id: Identifier) = builds[id]
    fun getTable(table: LootTable.Builder) = get(table)?.let { getTable(it) }
    fun getTable(block: Block) = getTable(block.lootTableId)
    fun getTable(entity: EntityType<*>) = getTable(entity.lootTableId)

    fun getItemsOf(id: Identifier) = items[id] ?: emptyList()

}