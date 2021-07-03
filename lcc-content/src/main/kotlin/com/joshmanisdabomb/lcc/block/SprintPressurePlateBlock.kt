package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.transformInt
import net.minecraft.block.AbstractPressurePlateBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.POWERED
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class SprintPressurePlateBlock(settings: Settings) : AbstractPressurePlateBlock(settings) {

    init {
        defaultState = stateManager.defaultState.with(POWERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(POWERED).let {}

    override fun playPressSound(world: WorldAccess, pos: BlockPos) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3f, 0.6f)
    }

    override fun playDepressSound(world: WorldAccess, pos: BlockPos) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3f, 0.5f)
    }

    override fun getRedstoneOutput(world: World, pos: BlockPos): Int {
        val state = world.getBlockState(pos)
        if (!state.isOf(this)) return 0
        if (state[POWERED]) {
            return world.getOtherEntities(null, BOX.offset(pos), EntityPredicates.EXCEPT_SPECTATOR).any().transformInt(15)
        }
        return world.getOtherEntities(null, BOX.offset(pos), EntityPredicates.EXCEPT_SPECTATOR.and { (it.isSprinting || it.prevY - it.y > 0) && !it.canAvoidTraps() }).any().transformInt(15)
    }

    override fun getRedstoneOutput(state: BlockState) = state[POWERED].transformInt(15)

    override fun setRedstoneOutput(state: BlockState, redstone: Int) = state.with(POWERED, redstone > 0)

}
