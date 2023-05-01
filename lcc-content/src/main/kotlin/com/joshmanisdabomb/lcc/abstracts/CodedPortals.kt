package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.extensions.cartesian
import com.joshmanisdabomb.lcc.extensions.exp
import com.joshmanisdabomb.lcc.extensions.times
import net.minecraft.util.math.ChunkPos
import kotlin.random.Random

sealed class CodedPortals(val codeLength: Byte, val codeHeight: Byte, val spread: Int, val shuffle: Boolean = true) {

    object RainbowCodedPortals : CodedPortals(6, 8, 4096)

    val codes by lazy { Array(codeLength.toInt()) { (0 until codeHeight).toSet() }.cartesian() }
    val permutations = codeHeight.toInt().exp(codeLength.toInt())

    val min = ByteArray(codeLength.toInt()) { 0 }
    val max = ByteArray(codeLength.toInt()) { codeHeight.dec() }

    fun getCodeMap(seed: Long): Map<ByteArray, ChunkPos> {
        var all = codes
        if (shuffle) all = all.shuffled(Random(seed))

        val x1 = getChunkX(min)
        val x2 = getChunkX(max)
        val z1 = getChunkZ(min)
        val z2 = getChunkZ(max)
        val positions = ((x1..x2 step spread) * (z1..z2 step spread))
        return positions.mapIndexed { index, pair -> all[index].map(Int::toByte).toByteArray() to ChunkPos(pair.first, pair.second) }.toMap()
    }

    private fun getCoord(segment: List<Int>): Int = (segment.reduceIndexed { index, acc, next -> acc.plus(next * codeHeight.toInt().exp(index)) } - (codeHeight.toInt().exp(segment.size)/2)).times(spread)

    fun getChunkX(code: ByteArray) = getCoord(code.toList().chunked(2) { it.first().toInt() })

    fun getChunkZ(code: ByteArray) = getCoord(code.toList().chunked(2) { it.last().toInt() })

}