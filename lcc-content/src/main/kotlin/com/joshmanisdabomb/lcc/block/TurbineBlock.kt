package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.extensions.isHorizontal
import com.joshmanisdabomb.lcc.extensions.to
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.mob.SlimeEntity
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.tag.Tag
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.EntityView
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
        steamProviderRun(world, pos) { w, provider, p, below, pos2, distance -> lcc_content_createParticles(w, provider, p, below, pos2, distance, random) }
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

        val geothermals_block by lazy { mapOf<Tag<Block>, Float>(
            LCCTags.geothermal_warm to 0.001f,
            LCCTags.geothermal_hot to 0.03f,
            LCCTags.geothermal_heated to 0.1f,
            LCCTags.geothermal_soul_heated to 0.15f,
            LCCTags.geothermal_burning to 0.2f,
            LCCTags.geothermal_soul_burning to 0.25f,
            LCCTags.geothermal_flaming to 0.25f,
            LCCTags.geothermal_soul_flaming to 0.3f,
            LCCTags.geothermal_full to 0.5f
        ) }
        val geothermals_entity by lazy { mapOf<Tag<EntityType<*>>, Float>(
            LCCTags.geothermal_magma to 6f,
            LCCTags.geothermal_blaze to 40f
        ) }

        fun getGeothermalLevel(world: BlockView, pos: BlockPos, source: BlockState, block: Block): Float? {
            geothermals_block.forEach { (t, f) ->
                if (!source.isIn(t)) return@forEach
                return when (block) {
                    is CandleBlock -> if (!source[Properties.LIT]) null else f * source[Properties.CANDLES]
                    is CandleCakeBlock -> if (!source[Properties.LIT]) null else f
                    is CampfireBlock -> if (!source[Properties.LIT]) null else f + source[Properties.SIGNAL_FIRE].to(0.1f, 0f)
                    is FluidBlock -> f * block.getFluidState(source).let { if (it.isStill) 1f else if (it[Properties.FALLING]) return null else it[Properties.LEVEL_1_8].times(0.1f) }
                    else -> f
                }
            }
            if (world is EntityView) {
                val steam = world.getEntitiesByClass(Entity::class.java, Box(pos), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR).maxOfOrNull { e ->
                    val pos2 = e.blockPos
                    if (pos2.x != pos.x || pos2.z != pos.z) return@maxOfOrNull 0f
                    geothermals_entity.maxOfOrNull { (k, v) -> if (k.contains(e.type)) v else 0f } ?: 0f.let {
                        when (e) {
                            is SlimeEntity -> return it.times(e.size)
                            else -> return it
                        }
                    }
                }
                return steam?.let { if (it <= 0f) null else it }
            }
            return null
        }
    }

    enum class TurbineState : StringIdentifiable {
        UNPOWERED,
        POWERED,
        EXTRACTED;

        override fun asString() = name.toLowerCase()
    }

}