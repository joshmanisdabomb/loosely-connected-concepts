package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class AlarmBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.alarm, pos, state) {

    @Environment(EnvType.CLIENT)
    var ringerValue = 0f
    @Environment(EnvType.CLIENT)
    var ringerVelocity = 0.0001f

    companion object {
        fun clientTick(world: World, pos: BlockPos, state: BlockState, entity: AlarmBlockEntity) {
            if (state[Properties.POWERED]) {
                entity.ringerVelocity *= 100f
            }
        }
    }

}