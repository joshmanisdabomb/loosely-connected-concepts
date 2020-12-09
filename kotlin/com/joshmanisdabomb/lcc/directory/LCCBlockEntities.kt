package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry

object LCCBlockEntities : RegistryDirectory<BlockEntityType<*>, Unit>() {

    override val _registry = Registry.BLOCK_ENTITY_TYPE

    val bounce_pad by create { BlockEntityType.Builder.create(::BouncePadBlockEntity, LCCBlocks.bounce_pad).build(null) }

}
