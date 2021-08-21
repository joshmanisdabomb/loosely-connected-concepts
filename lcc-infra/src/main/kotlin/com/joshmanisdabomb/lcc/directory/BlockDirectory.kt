package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.settings.BlockExtraSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.MapColor
import net.minecraft.entity.EntityType
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView

abstract class BlockDirectory : BasicDirectory<Block, BlockExtraSettings>(), RegistryDirectory<Block, BlockExtraSettings, Unit> {

    override val registry by lazy { Registry.BLOCK }

    fun initClient(parameters: Unit = defaultContext(), filter: (context: DirectoryContext<BlockExtraSettings>) -> Boolean = { true }) {
        val entries = entries.values.filter { filter(it.context) }
        entries.forEach { afterInitClient(it.entry, it, parameters) }
    }

    override fun <V : Block> afterInit(initialised: V, entry: DirectoryEntry<out Block, out V>, parameters: Unit) {
        entry.properties.initBlock(initialised)
    }

    fun <V : Block> afterInitClient(initialised: V, entry: DirectoryEntry<out Block, out V>, parameters: Unit) {
        entry.properties.initBlockClient(initialised)
    }

    override fun defaultProperties(name: String) = BlockExtraSettings()

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos) = false

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>) = false

    protected fun always(state: BlockState, world: BlockView, pos: BlockPos) = true

    protected fun always(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>) = true

    protected fun pillarMapColorProvider(top: MapColor, side: MapColor): (state: BlockState) -> MapColor = { if (it[Properties.AXIS] == Direction.Axis.Y) top else side }

}