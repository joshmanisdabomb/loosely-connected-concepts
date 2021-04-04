package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.extensions.isHorizontal
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
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

    override fun getEnergy(world: BlockView, pos: BlockPos) = steamProviderRun(world, pos, LCCExtendedBlockContent::lcc_content_getSteam) ?: 0f

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        if (context.side != null && context.side != Direction.UP) return 0f
        return super.removeEnergy(target, amount, unit, context)
    }

    override fun extractEnergy(world: ModifiableWorld, pos: BlockPos, state: BlockState) {
        world.setBlockState(pos, state.with(turbine_state, TurbineState.EXTRACTED), 7)
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
        steamProviderRun(world, pos) { w, provider, p, below, pos2, distance -> lcc_content_steamProviderParticles(w, provider, p, below, pos2, distance, random) }
    }

    protected fun <T> steamProviderRun(world: BlockView, pos: BlockPos, callback: LCCExtendedBlockContent.(world: BlockView, provider: BlockState, pos: BlockPos, below: BlockState, pos2: BlockPos, turbineDist: Int) -> T): T? {
        for (i in 1..5) {
            val pos2 = pos.down(i)
            val state = world.getBlockState(pos2)
            if (state.isAir) continue
            (state.block as? LCCExtendedBlockContent)?.also {
                val pos3 = pos2.down()
                val state2 = world.getBlockState(pos3)
                return it.callback(world, state, pos2, state2, pos3, i)
            }
            if (!state.getCollisionShape(world, pos).isEmpty) return null
        }
        return null
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