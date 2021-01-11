package com.joshmanisdabomb.lcc.subblock

import net.minecraft.block.BlockState
import net.minecraft.util.Identifier
import net.minecraft.util.shape.VoxelShape

open class Subblock(val id: Identifier, val shape: VoxelShape, val single: BlockState)