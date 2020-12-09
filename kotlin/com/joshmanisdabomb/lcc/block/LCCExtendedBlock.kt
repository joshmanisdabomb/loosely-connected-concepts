package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface LCCExtendedBlock {

    @JvmDefault
    fun lcc_onEntitySingleCollision(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) = Unit

    @JvmDefault
    fun lcc_onEntitySingleJumpOff(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: LivingEntity) = false

}
