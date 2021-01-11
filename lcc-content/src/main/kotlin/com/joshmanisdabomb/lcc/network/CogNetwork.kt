package com.joshmanisdabomb.lcc.network

import com.joshmanisdabomb.lcc.block.CogBlock
import com.joshmanisdabomb.lcc.block.CogBlock.Companion.cog_states
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.perpendiculars
import net.minecraft.block.SideShapeType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class CogNetwork(distance: Int = 64) : BlockNetwork<Pair<BlockPos, Direction?>>(distance) {

    override fun traverse(world: BlockView, current: Pair<BlockPos, Direction?>, nodes: MutableMap<String, MutableSet<Pair<BlockPos, Direction?>>>): Set<Pair<BlockPos, Direction?>> {
        val pos = toPosition(current)
        val state = world.getBlockState(pos)
        val d = current.second
        if (state.block !is CogBlock) return emptySet()
        if (d == null) return directions.mapNotNull { if (state.get(cog_states[it]).exists) pos to it else null }.toSet()

        val me = pos to d
        var toClockwise = false
        if (from(nodes, "ccw").contains(me)) toClockwise = true

        //when powered from behind, add node in network result
        if (LCCBlocks.cog.isPowered(state, world, pos, d) == true) {
            from(nodes, "powered").add(me)
            //from(nodes, "ccw").remove(me)
        }

        //check connected cogs
        val positions = mutableSetOf<Pair<BlockPos, Direction?>>()
        for (d2 in d.perpendiculars) {
            if (state[cog_states[d2]].exists) {
                val pair = pos to d2
                positions.add(pair)
                if (!toClockwise) from(nodes, "ccw").add(pair)
            }

            val p1 = pos.offset(d2)
            val c1 = world.getBlockState(p1)
            if (c1.block is CogBlock && c1[cog_states[d]].exists) {
                val pair = p1 to d
                positions.add(pair)
                if (!toClockwise) from(nodes, "ccw").add(pair)
            }

            if (!c1.isSideSolid(world, pos.offset(d2), d.opposite, SideShapeType.FULL)) {
                val p2 = pos.offset(d2).offset(d)
                val c2 = world.getBlockState(p2)
                if (c2.block is CogBlock && c2[cog_states[d2.opposite]].exists) {
                    val pair = p2 to d2.opposite
                    positions.add(pair)
                    if (!toClockwise) from(nodes, "ccw").add(pair)
                }
            }
        }
        return positions
    }

    override fun toPosition(traversable: Pair<BlockPos, Direction?>): BlockPos = traversable.first

}