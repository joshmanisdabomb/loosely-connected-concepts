package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCConfiguredFeatures
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SaplingBlock
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.AGE_7
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class ClassicSaplingBlock(settings: Settings) : SaplingBlock(ClassicSaplingBlock, settings) {

    init {
        defaultState = this.stateManager.defaultState.with(STAGE, 0).with(AGE_7, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(STAGE, AGE_7).let {}

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(5) == 0) {
            if (state[AGE_7] < 7) {
                world.setBlockState(pos, state.cycle(AGE_7), 3)
            } else if (state[STAGE] < 1) {
                world.setBlockState(pos, state.with(AGE_7, 0).cycle(STAGE), 3)
            } else {
                grow(world, random, pos, state)
            }
        }
    }

    override fun canGrow(world: World, random: Random, pos: BlockPos, state: BlockState) = true

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        generate(world, pos, state.with(STAGE, 1), random)
    }

    companion object : SaplingGenerator() {

        override fun getTreeFeature(random: Random, bl: Boolean) = LCCConfiguredFeatures.classic_tree

    }

}