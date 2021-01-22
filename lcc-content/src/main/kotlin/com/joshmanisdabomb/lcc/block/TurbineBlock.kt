package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.energy.EnergyHandler
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.extensions.isHorizontal
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.ModifiableWorld
import net.minecraft.world.World
import java.util.*

class TurbineBlock(settings: Settings) : SimpleEnergyBlock(settings) {

    override val network = FullBlockNetwork({ _, _, state, side -> if (side.isHorizontal) state.isOf(this) else false }, { _, _, state, side -> if (side?.isHorizontal != false && state.isOf(this) && state[turbine_state] == TurbineState.POWERED) arrayOf("powered") else emptyArray() }, 64)

    init {
        defaultState = stateManager.defaultState.with(turbine_state, TurbineState.UNPOWERED)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(turbine_state).let {}

    override fun getEnergy(world: BlockView, pos: BlockPos) = getEnergyAndPosition(world, pos)?.second ?: 0f

    override fun removeEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        if (side != null && side != Direction.UP) return 0f
        return super.removeEnergy(amount, unit, target, world, home, away, side)
    }

    override fun extractEnergy(world: ModifiableWorld, pos: BlockPos, state: BlockState) {
        world.setBlockState(pos, state.with(turbine_state, TurbineState.EXTRACTED), 7)
    }

    fun getEnergyAndPosition(world: BlockView, pos: BlockPos): Pair<BlockPos, Float>? {
        for (i in 1..5) {
            val pos2 = pos.down(i)
            val state = world.getBlockState(pos2)
            if (state.isAir) continue
            if (state.isOf(Blocks.WATER_CAULDRON)) {
                val pos3 = pos2.down()
                val state2 = world.getBlockState(pos3)
                val block = state2.block
                if (block !is FiredGeneratorBlock) return null
                val steam = block.getSteam(world, pos3, state2)
                if (steam <= 0f) return null
                return pos2 to steam
            }
            if (!state.getCollisionShape(world, pos).isEmpty) return null
        }
        return null
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val turbine = state[turbine_state]
        val current = turbine == TurbineState.POWERED
        val to = getEnergy(world, pos) > 0f
        if (turbine == TurbineState.EXTRACTED || current != to) world.setBlockState(pos, state.with(turbine_state, if (to) TurbineState.POWERED else TurbineState.UNPOWERED), 7)
        super.scheduledTick(state, world, pos, random)
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        for (i in 1..5) {
            val pos2 = pos.down(i)
            val state2 = world.getBlockState(pos2)
            if (state2.isAir) continue
            if (state2.isOf(Blocks.WATER_CAULDRON)) {
                val pos3 = pos2.down()
                val state3 = world.getBlockState(pos3)
                val block = state3.block
                if (block !is FiredGeneratorBlock || !state3[Properties.LIT]) return
                for (i in 1..20) {
                    world.addParticle(LCCParticles.steam, false, pos2.x.plus(0.4).plus(random.nextDouble().times(0.2)), pos2.y.plus(0.7), pos2.z.plus(0.4).plus(random.nextDouble().times(0.2)), 0.0, random.nextDouble().times(0.1), 0.0)
                }
            }
            if (!state.getCollisionShape(world, pos).isEmpty) return
        }
    }

    companion object {
        val turbine_state = EnumProperty.of("turbine", TurbineState::class.java)
        val shape = createCuboidShape(0.0, 11.0, 0.0, 16.0, 16.0, 16.0)
    }

    enum class TurbineState : StringIdentifiable {
        UNPOWERED,
        POWERED,
        EXTRACTED;

        override fun asString() = name.toLowerCase()
    }

}