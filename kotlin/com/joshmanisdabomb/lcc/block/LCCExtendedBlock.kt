package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface LCCExtendedBlock {

    @JvmDefault
    fun onEntitySingleCollision(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) = Unit

}
