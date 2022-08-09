package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import net.minecraft.block.AbstractFireBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.AGE_25
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class NuclearFireBlock(settings: Settings) : AbstractFireBlock(settings, 0.0f), LCCBlockTrait, LCCContentBlockTrait {

    init {
        defaultState = stateManager.defaultState.with(AGE_25, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(AGE_25).let {}

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos) = if (canPlaceAt(state, world, pos)) state else state_air

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        return world.getBlockState(pos.down()).isOf(LCCBlocks.nuclear_waste)
    }

    override fun isFlammable(state: BlockState) = true

    override fun lcc_content_nukeIgnore() = true

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        super.randomDisplayTick(state, world, pos, random)
    }

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        NuclearUtil.addRadiation(entity as? LivingEntity ?: return, 12, 3)
        if (!entity.isFireImmune()) {
            entity.setFireTicks(Short.MAX_VALUE - 4)
        }
        entity.damage(LCCDamage.radiation, 3.0f)
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (!oldState.isOf(state.block) && !state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false)
        }
    }

    override fun hasRandomTicks(state: BlockState) = true

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        with (BlockPos.Mutable()) {
            for (i in -3..3) {
                for (j in -1..1) {
                    for (k in -3..3) {
                        this.set(pos).move(i, j, k)
                        if (i != 0 || j != 0 || k != 0) {
                            val state2 = world.getBlockState(this)
                            if (random.nextInt(40) == 0 && (state2.isAir || state2.getHardness(world, this) >= 0f)) {
                                world.setBlockState(this, state_waste)
                                if (random.nextInt(2) == 0 && world.getBlockState(this.move(0, 1, 0)).isAir) {
                                    world.setBlockState(this, state.with(AGE_25, state[AGE_25].plus(random.nextInt(3)).coerceAtMost(25)))
                                }
                            }
                        }
                    }
                }
            }
        }
        if (state[AGE_25] >= 25) {
            world.setBlockState(pos, state_air)
        } else {
            world.setBlockState(pos, state.cycle(AGE_25))
        }
    }

    override fun lcc_onEntityNearby(world: World, state: BlockState, pos: BlockPos, entity: Entity, distSq: Double) {
        NuclearUtil.addRadiation(entity as? LivingEntity ?: return, 2, 2)
    }

    companion object {
        val state_air by lazy { Blocks.AIR.defaultState }
        val state_waste by lazy { LCCBlocks.nuclear_waste.defaultState }
    }

}
