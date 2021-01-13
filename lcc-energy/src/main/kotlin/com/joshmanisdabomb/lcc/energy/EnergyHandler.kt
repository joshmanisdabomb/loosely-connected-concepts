package com.joshmanisdabomb.lcc.energy

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

interface EnergyHandler {

    fun addEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float

    fun removeEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float

    fun insert(target: EnergyHandler, amount: Float, unit: EnergyUnit, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        val extracted = this.removeEnergy(amount, unit, target, world, home, away, side)
        if (extracted == 0f) return 0f
        val added = target.addEnergy(extracted, unit, this, world, away, home, side?.opposite)
        return added
    }

    fun extract(target: EnergyHandler, amount: Float, unit: EnergyUnit, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        if (amount <= 0f) return 0f
        val space = getEnergySpace(unit, side)
        space?.also { if (it <= 0f) return 0f }
        val extracted = target.removeEnergy(amount.run { coerceAtMost(space ?: return@run this) }, unit, this, world, away, home, side?.opposite)
        if (extracted == 0f) return 0f
        val added = this.addEnergy(extracted, unit, target, world, home, away, side)
        return added
    }

    fun getMinimumEnergy(unit: EnergyUnit): Float? = null
    fun getMaximumEnergy(unit: EnergyUnit): Float? = null
    fun getEnergyBounds(unit: EnergyUnit): ClosedRange<Float>? = null
    fun getEnergySpace(unit: EnergyUnit, side: Direction?): Float? = null

    companion object {
        fun getHandlerAt(world: BlockView, pos: BlockPos): EnergyHandler? {
            val state = world.getBlockState(pos)
            val block = state.block
            if (block is EnergyHandler) return block
            val be = world.getBlockEntity(pos)
            if (be is EnergyHandler) return be
            return null
        }

        fun worldInsert(handler: EnergyHandler, world: BlockView, pos: BlockPos, state: BlockState, side: Direction, amount: Float, unit: EnergyUnit): Float? {
            val pos2 = pos.offset(side)
            getHandlerAt(world, pos2)?.apply { return handler.insert(this, amount, unit, world, pos, pos2, side) }
            return null
        }

        fun worldExtract(handler: EnergyHandler, world: BlockView, pos: BlockPos, state: BlockState, side: Direction, amount: Float, unit: EnergyUnit): Float? {
            val pos2 = pos.offset(side)
            getHandlerAt(world, pos2)?.apply { return handler.extract(this, amount, unit, world, pos, pos2, side) }
            return null
        }

        fun send(handler: EnergyHandler, world: World, pos: BlockPos, state: BlockState, amount: Float, unit: EnergyUnit, vararg sides: Direction): Float {
            var sent = 0f
            for (d in sides) {
                sent += worldInsert(handler, world, pos, state, d, amount - sent, unit) ?: 0f
                if (sent >= amount) break
            }
            return sent
        }

        fun request(handler: EnergyHandler, world: World, pos: BlockPos, state: BlockState, amount: Float, unit: EnergyUnit, vararg sides: Direction): Float {
            var received = 0f
            for (d in sides) {
                if (received >= amount) break
                received += worldExtract(handler, world, pos, state, d, amount - received, unit) ?: 0f
            }
            return received
        }
    }

}