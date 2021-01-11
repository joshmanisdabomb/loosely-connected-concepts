package com.joshmanisdabomb.lcc.network

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class FullBlockNetwork(val traversable: (world: BlockView, pos: BlockPos, state: BlockState, direction: Direction) -> Boolean, val node: (world: BlockView, pos: BlockPos, state: BlockState, direction: Direction) -> Array<String>, distance: Int) : BlockNetwork<BlockPos>(distance) {

    override fun traverse(world: BlockView, current: BlockPos, nodes: MutableMap<String, MutableSet<BlockPos>>): Set<BlockPos> {
        val positions = mutableSetOf<BlockPos>()
        for (d in directions) {
            val pos = current.offset(d)
            val state = world.getBlockState(pos)
            if (traversable(world, pos, state, d)) positions.add(pos)
            node(world, pos, state, d).forEach { from(nodes, it).add(pos) }
        }
        return positions
    }

    override fun toPosition(traversable: BlockPos) = traversable

}