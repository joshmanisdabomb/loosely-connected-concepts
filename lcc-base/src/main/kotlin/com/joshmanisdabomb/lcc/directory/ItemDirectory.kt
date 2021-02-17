package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.settings.BlockExtraSettings
import com.joshmanisdabomb.lcc.settings.ItemExtraSettings
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView

abstract class ItemDirectory : BasicDirectory<Item, ItemExtraSettings>(), RegistryDirectory2<Item, ItemExtraSettings, Unit> {

    override val registry by lazy { Registry.ITEM }

    fun initClient() = initClient { true }
    fun initClient(filter: (context: DirectoryContext<ItemExtraSettings, Unit>) -> Boolean) {
        val entries = entries.values.filter { filter(it.context) }
        entries.forEach { afterInitClient(it.entry, it) }
    }

    override fun <V : Item> afterInit(initialised: V, entry: DirectoryEntry<out Item, out V>) {
        entry.properties.initItem(initialised)
    }

    fun <V : Item> afterInitClient(initialised: V, entry: DirectoryEntry<out Item, out V>) {
        entry.properties.initItemClient(initialised)
    }

    override fun defaultProperties(name: String) = BlockExtraSettings()

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos) = false

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>) = false

}