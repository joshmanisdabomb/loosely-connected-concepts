package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class RadarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.radar, pos, state) {

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RadarBlockEntity) {

        }
    }

}