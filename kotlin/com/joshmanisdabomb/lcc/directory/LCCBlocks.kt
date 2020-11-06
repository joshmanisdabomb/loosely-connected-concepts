package com.joshmanisdabomb.lcc.directory

import net.minecraft.block.Block
import net.minecraft.util.registry.Registry

object LCCBlocks : RegistryDirectory<Block, Unit>() {

    override val _registry by lazy { Registry.BLOCK }

}