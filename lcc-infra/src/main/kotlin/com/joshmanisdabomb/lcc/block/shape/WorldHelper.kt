package com.joshmanisdabomb.lcc.block.shape

import net.minecraft.block.BlockState
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.VerticalBlockSample

object WorldHelper {

    fun getZones(column: VerticalBlockSample, generator: ChunkGenerator): MutableSet<WorldZone> {
        val set = mutableSetOf<WorldZone>()
        var start: Int = generator.minimumY
        var current: BlockState? = null
        val end = generator.worldHeight.plus(generator.minimumY)
        for (i in start until end) {
            val state = column.getState(i)
            if (state != current) {
                if (current != null) set.add(WorldZone(start, i.minus(1), current))
                current = state
                start = i
            }
        }
        if (current != null) set.add(WorldZone(start, end.minus(1), current))
        return set
    }

    data class WorldZone(val start: Int, val end: Int, val state: BlockState) {
        val height get() = end + 1 - start
    }

}