package com.joshmanisdabomb.lcc.data.batches

import com.joshmanisdabomb.lcc.mixin.data.common.ItemEntryAccessor
import com.joshmanisdabomb.lcc.mixin.data.common.LootPoolAccessor
import com.joshmanisdabomb.lcc.mixin.data.common.LootTableAccessor
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.loot.LootTable
import net.minecraft.loot.context.LootContextType
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.util.Identifier

class LootTableBatch {

    private val map = mutableMapOf<Identifier, LootTable.Builder>()
    private val builds = mutableMapOf<Identifier, LootTable>()
    private val items = mutableMapOf<Identifier, List<Item>>()

    fun add(type: LootContextType, id: Identifier, table: LootTable.Builder) {
        map[id] = table
        val build = table.type(type).build()
        builds[id] = build

        items[id] = getItemsOf(build)
    }

    fun addBlock(id: Identifier, table: LootTable.Builder) = add(LootContextTypes.BLOCK, id, table)
    fun addBlock(block: Block, table: LootTable.Builder) = add(LootContextTypes.BLOCK, block.lootTableId, table)

    fun addEntity(id: Identifier, table: LootTable.Builder) = add(LootContextTypes.ENTITY, id, table)
    fun addEntity(entity: EntityType<*>, table: LootTable.Builder) = add(LootContextTypes.ENTITY, entity.lootTableId, table)

    operator fun get(id: Identifier) = map[id]
    operator fun get(block: Block) = get(block.lootTableId)
    operator fun get(entity: EntityType<*>) = get(entity.lootTableId)

    operator fun get(table: LootTable.Builder) = map.filterValues { it == table }.keys.firstOrNull()

    fun getAll() = map.toMap()

    fun getTable(id: Identifier) = builds[id]
    fun getTable(table: LootTable.Builder) = get(table)?.let { getTable(it) }
    fun getTable(block: Block) = getTable(block.lootTableId)
    fun getTable(entity: EntityType<*>) = getTable(entity.lootTableId)

    fun getTables() = builds.toMap()

    fun getItemsOf(id: Identifier) = items[id] ?: emptyList()
    fun getItemsOf(table: LootTable): List<Item> {
        val entries = (table as LootTableAccessor).pools.flatMap { (it as LootPoolAccessor).poolEntries.toList() }
        return entries.filterIsInstance<ItemEntry>().map { (it as ItemEntryAccessor).item }
    }

}