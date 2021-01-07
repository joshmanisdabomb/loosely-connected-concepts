package com.joshmanisdabomb.lcc.network

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class BlockNetwork<T>(val distance: Int) {

    fun discover(world: World, start: T): NetworkResult {
        val traversables = mutableListOf(start)
        val nodes = mutableMapOf<String, MutableSet<T>>()
        val startPos = toPosition(start)
        var i = 0
        while (i < traversables.size) {
            val t = traversables[i]
            val others = traverse(world, t, nodes)
            for (other in others) {
                val bp = toPosition(other)
                if (!traversables.contains(other) && (startPos == bp || startPos.isWithinDistance(bp, distance.toDouble()))) {
                    traversables.add(other)
                }
            }
            i++
        }
        return result(traversables.distinct().toSet(), nodes)
    }

    protected abstract fun traverse(world: World, current: T, nodes: MutableMap<String, MutableSet<T>>): Set<T>

    protected abstract fun toPosition(traversable: T): BlockPos

    protected open fun result(traversables: Set<T>, nodes: Map<String, Set<T>>) = NetworkResult(traversables, nodes)

    protected fun from(nodes: MutableMap<String, MutableSet<T>>, type: String) = nodes.computeIfAbsent(type) { mutableSetOf() }

    inner class NetworkResult internal constructor(private val _traversables: Set<T>, private val _nodes: Map<String, Set<T>>) {

        override fun toString() = "${this::class.simpleName}: ${traversables.size} traversables, ${nodes.size} nodes"

        fun toPosition(traversable: T) = this@BlockNetwork.toPosition(traversable)

        val traversables get() = _traversables.toList()
        val nodes get() = _nodes.toMap()

        val traversablesAssoc get() = traversables.groupBy(this::toPosition).mapValues { (_, v) -> v.distinct() }
        val nodesAssoc get() = nodes.mapValues { (_, v) -> v.groupBy(this::toPosition).mapValues { (_, v) -> v.distinct() } }

    }

}