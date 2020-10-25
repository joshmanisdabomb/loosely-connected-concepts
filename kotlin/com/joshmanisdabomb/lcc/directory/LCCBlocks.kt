package com.joshmanisdabomb.lcc.directory

import net.minecraft.block.Block
import net.minecraft.util.registry.Registry

object LCCBlocks : RegistryDirectory<Block>() {

    override val _registry by lazy { Registry.BLOCK }

}