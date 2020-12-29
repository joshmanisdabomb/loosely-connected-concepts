package com.joshmanisdabomb.lcc.data.factory.translation

import com.joshmanisdabomb.lcc.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

interface TranslationFactory : BlockDataFactory, ItemDataFactory, EntityDataFactory {

    fun translate(data: DataAccessor, key: String, path: String, locale: String): String?

    fun apply(data: DataAccessor, key: String, path: String) {
        data.lang.forEach { (k, v) -> translate(data, key, path, k)?.run { v[key] = this } }
    }

    override fun apply(data: DataAccessor, entry: Block) {
        apply(data, entry.translationKey, Registry.BLOCK.getId(entry).path)
    }

    override fun apply(data: DataAccessor, entry: Item) {
        apply(data, entry.translationKey, Registry.ITEM.getId(entry).path)
    }

    override fun apply(data: DataAccessor, entry: EntityType<*>) {
        apply(data, entry.translationKey, Registry.ENTITY_TYPE.getId(entry).path)
    }

}