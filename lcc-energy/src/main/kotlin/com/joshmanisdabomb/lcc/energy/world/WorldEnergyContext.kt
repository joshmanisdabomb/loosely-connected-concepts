package com.joshmanisdabomb.lcc.energy.world

import com.joshmanisdabomb.lcc.energy.base.EnergyContext
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

data class WorldEnergyContext(val world: BlockView?, val home: BlockPos?, val away: BlockPos?, val side: Direction?) : EnergyContext() {

    val pos get() = home

    val state: BlockState? get() { return world?.getBlockState(home ?: return null) }
    val stateAway: BlockState? get() { return world?.getBlockState(away ?: return null) }

    override fun defaultOther() = copy(home = away, away = home, side = side?.opposite)

}