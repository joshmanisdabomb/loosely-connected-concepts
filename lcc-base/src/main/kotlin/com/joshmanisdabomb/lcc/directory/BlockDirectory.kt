package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.settings.BlockExtraSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView

abstract class BlockDirectory : RegistryDirectory<Block, BlockExtraSettings>() {

    override val _registry by lazy { Registry.BLOCK }

    override fun register(key: String, thing: Block, properties: BlockExtraSettings) = super.register(key, thing, properties).apply { properties.initBlock(thing) }

    fun initClient() {
        all.forEach { (k, v) -> allProperties[k]!!.initBlockClient(v) }
    }

    override fun getDefaultProperty() = BlockExtraSettings()

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos) = false

    protected fun never(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>) = false

}