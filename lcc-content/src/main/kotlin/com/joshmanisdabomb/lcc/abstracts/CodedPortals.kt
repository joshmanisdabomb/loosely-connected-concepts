package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.block.RainbowGateBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.cartesian
import com.joshmanisdabomb.lcc.extensions.exp
import com.joshmanisdabomb.lcc.extensions.sqrt
import com.joshmanisdabomb.lcc.extensions.times
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

sealed class CodedPortals<E>(val parts: List<E>, val segments: Int, val spread: Int, val shuffle: Boolean = true) {

    object RainbowCodedPortals : CodedPortals<String>((0 until 8).flatMap { a -> (a until 8).map { b -> "$a$b" } }, 3, 4096) {

        fun calculateCode(gates: List<BlockPos>, world: World): String? = gates.groupBy { it.y }.mapValues { (k, v) -> v.map {
            val state2 = world.getBlockState(it)
            if (!state2.isOf(LCCBlocks.rainbow_gate) || state2[RainbowGateBlock.type] == RainbowGateBlock.RainbowGateState.INCOMPLETE) return null
            state2[RainbowGateBlock.symbol].minus(1)
        }.sorted().joinToString("") }.toSortedMap().values.joinToString("-")

    }

    val possible = parts.size.exp(segments)
    val possibleSq = possible.sqrt()

    val codes by lazy { (parts * segments).cartesian().map { it.joinToString("-") } }
    val positions by lazy {
        val a = possibleSq.div(2)
        ((-a until a) * (-a until a)).map { ChunkPos(it.first * spread, it.second * spread) }
    }

    fun getCodeMap(seed: Long): Map<String, ChunkPos> {
        var all = codes
        if (shuffle) all = all.shuffled(kotlin.random.Random(seed))

        return all.mapIndexed { index, pair -> pair to positions[index] }.toMap()
    }

    fun randomCode(random: Random) = Util.getRandom(codes, random)

}