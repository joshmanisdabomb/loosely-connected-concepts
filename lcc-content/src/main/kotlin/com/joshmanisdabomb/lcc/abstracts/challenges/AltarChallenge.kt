package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.block.SapphireAltarBlock
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.world.feature.SapphireAltarStructureFeature
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion
import java.lang.RuntimeException
import java.util.*
import kotlin.Boolean
import kotlin.Int
import kotlin.Unit
import kotlin.repeat

abstract class AltarChallenge {

    val id get() = LCCRegistries.altar_challenges.getKey(this).orElseThrow(::RuntimeException).value

    abstract fun initialData(random: Random, nbt: NbtCompound = NbtCompound()): NbtCompound

    abstract fun generate(world: StructureWorldAccess, piece: SapphireAltarStructureFeature.Piece, yOffset: Int, boundingBox: BlockBox, data: NbtCompound, random: Random)

    open fun getAltarWidth(data: NbtCompound): Int? = data.getInt("Width") + 2

    open fun getAltarDepth(data: NbtCompound): Int? = data.getInt("Depth") + 2

    abstract fun start(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity, player: PlayerEntity): Boolean

    abstract fun verify(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState

    abstract fun verifyTick(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState

    open fun handleState(cstate: ChallengeState, world: ServerWorld, pos: BlockPos, state: BlockState, entity: SapphireAltarBlockEntity): Boolean {
        when (cstate) {
            ChallengeState.COMPLETED -> {
                val reward = cstate.getRewards(state)
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0f, 1.0f)
                world.breakBlock(pos, false)
                repeat(reward) {
                    Block.dropStack(world, pos, LCCItems.sapphire.stack())
                }
                return true
            }
            ChallengeState.FAILED, ChallengeState.FAILED_AGGRESSIVE -> {
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.BLOCKS, 1.0f, 1.0f)
                world.breakBlock(pos, false)
                if (cstate == ChallengeState.FAILED_AGGRESSIVE) world.createExplosion(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, 6.0f, true, Explosion.DestructionType.DESTROY)
                return true
            }
            else -> return false
        }
    }

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

    enum class ChallengeState {
        ACTIVE,
        COMPLETED {
            override fun getRewards(state: BlockState) = SapphireAltarBlock.sapphireProperties.sumOf { state[it].reward }
        },
        FAILED,
        FAILED_AGGRESSIVE;

        open fun getRewards(state: BlockState) = 0
    }

}