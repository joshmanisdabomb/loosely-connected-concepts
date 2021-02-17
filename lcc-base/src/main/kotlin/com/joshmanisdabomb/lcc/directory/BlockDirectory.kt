package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.settings.BlockExtraSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView

abstract class BlockDirectory : BasicDirectory<Block, BlockExtraSettings>(), RegistryDirectory2<Block, BlockExtraSettings, Unit> {

    override val registry by lazy { Registry.BLOCK }

    fun initClient() = initClient { true }
    fun initClient(filter: (context: DirectoryContext<BlockExtraSettings, Unit>) -> Boolean) {
        val entries = entries.values.filter { filter(it.context) }
        entries.forEach { afterInitClient(it.entry, it) }
    }

    override fun <V : Block> afterInit(initialised: V, entry: DirectoryEntry<out Block, out V>) {
        entry.properties.initBlock(initialised)
    }

    fun <V : Block> afterInitClient(initialised: V, entry: DirectoryEntry<out Block, out V>) {
        entry.properties.initBlockClient(initialised)
    }

    override fun defaultProperties(name: String) = BlockExtraSettings()

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos) = false

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>) = false

}