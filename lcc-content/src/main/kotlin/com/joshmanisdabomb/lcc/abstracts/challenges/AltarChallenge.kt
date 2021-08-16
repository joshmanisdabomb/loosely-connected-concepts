package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.world.feature.SapphireAltarStructureFeature
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.World
import java.lang.RuntimeException
import java.util.*
import kotlin.Boolean
import kotlin.Int
import kotlin.Unit

abstract class AltarChallenge {

    val id get() = LCCRegistries.altar_challenges.getKey(this).orElseThrow(::RuntimeException).value

    abstract fun generateOptions(random: Random, nbt: NbtCompound = NbtCompound()): NbtCompound

    abstract fun generate(world: StructureWorldAccess, piece: SapphireAltarStructureFeature.Piece, yOffset: Int, boundingBox: BlockBox, options: NbtCompound, random: Random)

    open fun getAltarWidth(options: NbtCompound): Int? = options.getInt("Width") + 2

    open fun getAltarDepth(options: NbtCompound): Int? = options.getInt("Depth") + 2

    abstract fun start(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity, player: PlayerEntity): Boolean

    protected fun foreachPlane(origin: BlockPos, direction: Direction, width: Int, depth: Int, bp: BlockPos.Mutable = BlockPos.Mutable(), func: (pos: BlockPos, x: Int, y: Int) -> Unit) {
        val w = width.minus(1).div(2)
        val d1 = direction.rotateYCounterclockwise()
        val d2 = direction.rotateYClockwise()
        for (x in 0 until width) {
            for (y in 0 until depth) {
                func(bp.set(origin).move(direction, y).move(d1, w).move(d2, x), x, y)
            }
        }
    }

}