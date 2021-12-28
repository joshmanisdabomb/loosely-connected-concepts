package com.joshmanisdabomb.lcc.network

import com.joshmanisdabomb.lcc.block.CogBlock
import com.joshmanisdabomb.lcc.block.CogBlock.Companion.cog_states
import com.joshmanisdabomb.lcc.block.ComputingBlock
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.perpendiculars
import net.minecraft.block.SideShapeType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class ComputingNetwork(distance: Int = 64, protected val cables: Boolean) : BlockNetwork<Pair<BlockPos, Boolean?>>(distance) {

    override fun traverse(world: BlockView, current: Pair<BlockPos, Boolean?>, nodes: MutableMap<String, MutableSet<Pair<BlockPos, Boolean?>>>): Set<Pair<BlockPos, Boolean?>> {
        val pos = toPosition(current)
        val state = world.getBlockState(pos)
        val top = current.second
        if (top != null) {
            if (state.block !is ComputingBlock) return emptySet()
            val be = world.getBlockEntity(pos) as? ComputingBlockEntity ?: return emptySet()
            val half = be.getHalf(top) ?: return emptySet()

            val me = pos to top

            from(nodes, half.module.id.toString()).add(me)

            //check connected modules
            val positions = mutableSetOf<Pair<BlockPos, Boolean?>>()
            val above = half.connectsAbove()
            if (above != null) {
                val pair = above.be.pos to above.top
                positions.add(pair)
                from(nodes, above.module.id.toString()).add(pair)
                for (node in above.module.getNetworkNodeTags(above)) {
                    from(nodes, node).add(pair)
                }
            }
            val below = half.connectsBelow()
            if (below != null) {
                val pair = below.be.pos to below.top
                positions.add(pair)
                from(nodes, below.module.id.toString()).add(pair)
                for (node in below.module.getNetworkNodeTags(below)) {
                    from(nodes, node).add(pair)
                }
            }

            return positions
        } else if (cables) {
            //TODO cables
            return emptySet()
        } else {
            return emptySet()
        }
    }

    override fun toPosition(traversable: Pair<BlockPos, Boolean?>): BlockPos = traversable.first

    companion object {
        val local = ComputingNetwork(64, false)
        val wired = ComputingNetwork(64, true)

        fun retrieveHalves(world: BlockView, map: Map<BlockPos, List<Pair<BlockPos, Boolean?>>>) = map.flatMap { (k, v) ->
            val other = world.getBlockEntity(k) as? ComputingBlockEntity ?: return@flatMap emptyList()
            return@flatMap v.mapNotNull {
                val top = it.second ?: return@mapNotNull null
                other.getHalf(top)
            }
        }
    }

}