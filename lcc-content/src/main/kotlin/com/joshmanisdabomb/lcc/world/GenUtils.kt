package com.joshmanisdabomb.lcc.world

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import kotlin.math.max
import kotlin.math.min

object GenUtils {

    fun areaMatches(stateProvider: (pos: BlockPos) -> BlockState, x1: Int, y1: Int, z1: Int, x2: Int = x1, y2: Int = y1, z2: Int = z1, expand: Int = 0, ex: Int = 0, ey: Int = 0, ez: Int = 0, height: Int = 0, test: (match: Boolean?) -> Boolean? = ::all, match: (state: BlockState, pos: BlockPos) -> Boolean = { state, pos -> state.isAir }): Boolean {
        val pos = BlockPos.Mutable()
        for (x in min(x1, x2).minus(expand).minus(ex) .. max(x1, x2).plus(expand).plus(ex)) {
            for (y in min(y1, y2).minus(expand).minus(ey) .. max(y1, y2).plus(expand).plus(ey).plus(height)) {
                for (z in min(z1, z2).minus(expand).minus(ez) .. max(z1, z2).plus(expand).plus(ez)) {
                    val p = pos.set(x, y, z).toImmutable()
                    return test(match(stateProvider(p), p)) ?: continue
                }
            }
        }
        return test(null)!!
    }

    fun all(match: Boolean?) = when (match) {
        true -> null
        false -> false
        null -> true
    }

    fun any(match: Boolean?) = when (match) {
        true -> true
        false -> null
        null -> false
    }

}