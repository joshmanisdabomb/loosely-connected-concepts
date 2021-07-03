package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBiomes
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import java.util.*

class WastelandRustingBlock(settings: Settings, stages: () -> Array<Block>) : Block(settings) {

    val stages by lazy(stages)

    fun isFinalStage() = this == stages.last()

    override fun hasRandomTicks(state: BlockState) = !isFinalStage()

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        rust(state, world, pos, random)?.also { world.setBlockState(pos, it) }
    }

    fun rust(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random): BlockState? {
        if (random.nextInt(3) == 0) return null

        val block = state.block as? WastelandRustingBlock
        if (block?.isFinalStage() == true) return null

        var valid = 0
        var wet = false
        for (d in Direction.values()) {
            val pos2 = pos.offset(d)
            val state2 = world.getBlockState(pos2)
            val fluid = world.getFluidState(pos2)
            if (this.stages.contains(state2.block)) {
                valid += 1
            } else if (d != Direction.DOWN && state2.isOf(fluid.blockState.block) && fluid.isIn(FluidTags.WATER)) {
                valid += 1
                wet = true
            }
        }

        if (valid >= 3 && wet && LCCBiomes.entries[world.getBiomeKey(pos).orElse(null)?.value?.path]?.tags?.contains("wasteland") == true) {
            val stage = this.stages.indexOf(state.block)
            return if (stage >= this.stages.count() - 1) null
            else if (stage > -1) stages[stage + 1].defaultState
            else stages.first().defaultState
        }
        return null
    }

}