package com.joshmanisdabomb.lcc.network

import com.joshmanisdabomb.lcc.block.ComputerCableBlock
import com.joshmanisdabomb.lcc.block.ComputingBlock
import com.joshmanisdabomb.lcc.block.ExplosivePasteBlock.Companion.up
import com.joshmanisdabomb.lcc.block.TerminalBlock
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.enums.SlabType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class ComputingNetwork(distance: Int = 64, protected val cables: Boolean) : BlockNetwork<Pair<BlockPos, Boolean?>>(distance) {

    override fun traverse(world: BlockView, current: Pair<BlockPos, Boolean?>, nodes: MutableMap<String, MutableSet<Pair<BlockPos, Boolean?>>>): Set<Pair<BlockPos, Boolean?>> {
        val pos = toPosition(current)
        val positions = mutableSetOf<Pair<BlockPos, Boolean?>>()
        val state = world.getBlockState(pos)
        val top = current.second
        if (top != null) {
            if (state.block !is ComputingBlock) return emptySet()
            val be = world.getBlockEntity(pos) as? ComputingBlockEntity ?: return emptySet()
            val half = be.getHalf(top) ?: return emptySet()

            val me = pos to top
            addComputerNodes(nodes, half, me)

            //check connected modules
            val above = half.connectsAbove()
            if (above != null) {
                val pair = above.be.pos to above.top
                positions.add(pair)
                addComputerNodes(nodes, above, pair)
            }
            val below = half.connectsBelow()
            if (below != null) {
                val pair = below.be.pos to below.top
                positions.add(pair)
                addComputerNodes(nodes, below, pair)
            }

            //check for terminals and cables (when wired) above and below
            if (top) {
                val up = pos.up()
                val stateAbove = world.getBlockState(up)
                when (stateAbove.block) {
                    is TerminalBlock -> from(nodes, "terminal").add(up to null)
                    is ComputerCableBlock -> if (cables) positions.add(up to null)
                }
            } else {
                val down = pos.down()
                val stateBelow = world.getBlockState(down)
                when (stateBelow.block) {
                    is TerminalBlock -> from(nodes, "terminal").add(down to null)
                    is ComputerCableBlock -> if (cables) positions.add(down to null)
                }
            }

            //check for cables on sides when wired
            if (cables) {
                for (d in horizontalDirections) {
                    val pos2 = pos.offset(d)
                    val state2 = world.getBlockState(pos2)
                    when (state2.block) {
                        is ComputerCableBlock -> if (cables) positions.add(pos2 to null)
                    }
                }
            }

        } else if (state.block is TerminalBlock) {
            val me = pos to top
            from(nodes, "terminal").add(me)

            //check computer modules above and below
            val up = pos.up()
            val halfAbove = (world.getBlockEntity(up) as? ComputingBlockEntity)?.getHalf(false)
            if (halfAbove != null) {
                val pair = up to false
                positions.add(pair)
                addComputerNodes(nodes, halfAbove, pair)
            }
            val down = pos.down()
            val halfBelow = (world.getBlockEntity(down) as? ComputingBlockEntity)?.getHalf(true)
            if (halfBelow != null) {
                val pair = down to true
                positions.add(pair)
                addComputerNodes(nodes, halfBelow, pair)
            }

            //check for cables on all sides when wired
            if (cables) {
                for (d in Direction.values()) {
                    val pos2 = pos.offset(d)
                    val state2 = world.getBlockState(pos2)
                    when (state2.block) {
                        is ComputerCableBlock -> if (cables) positions.add(pos2 to null)
                    }
                }
            }
        } else if (cables && state.block is ComputerCableBlock) {
            for (d in Direction.values()) {
                val pos2 = pos.offset(d)
                val state2 = world.getBlockState(pos2)
                when (state2.block) {
                    is ComputerCableBlock -> positions.add(pos2 to null)
                    is TerminalBlock -> {
                        positions.add(pos2 to null)
                        from(nodes, "terminal").add(pos2 to null)
                    }
                    is ComputingBlock -> {
                        val halves = (world.getBlockEntity(pos2) as? ComputingBlockEntity)?.getHalves(when (d) {
                            Direction.UP -> SlabType.BOTTOM
                            Direction.DOWN -> SlabType.TOP
                            else -> SlabType.DOUBLE
                        })
                        if (halves?.isNotEmpty() == true) {
                            for (half in halves) {
                                val pair = pos2 to half.top
                                positions.add(pair)
                                addComputerNodes(nodes, half, pair)
                            }
                        }
                    }
                }
            }
        }
        return positions
    }

    private fun addComputerNodes(nodes: MutableMap<String, MutableSet<Pair<BlockPos, Boolean?>>>, half: ComputingBlockEntity.ComputingHalf, loc: Pair<BlockPos, Boolean>) {
        from(nodes, half.module.id.toString()).add(loc)
        for (node in half.module.getNetworkNodeTags(half)) {
            from(nodes, node).add(loc)
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