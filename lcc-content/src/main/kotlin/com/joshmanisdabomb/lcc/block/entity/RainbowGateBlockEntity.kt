package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.IdolBlock
import com.joshmanisdabomb.lcc.block.RainbowGateBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.tags.LCCBlockTags
import com.joshmanisdabomb.lcc.extensions.*
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.event.BlockPositionSource
import net.minecraft.world.event.GameEvent
import net.minecraft.world.event.listener.GameEventListener
import kotlin.math.absoluteValue

class RainbowGateBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.rainbow_gate, pos, state), GameEventListener {

    private var main: BlockPos? = null
    private var others: List<BlockPos>? = null

    private var direction: Direction? = null
    private var offset: Byte? = null
    private var code: ByteArray? = null

    private val source = BlockPositionSource(pos)

    fun main(others: List<BlockPos>) {
        this.others = others
        markDirty()
    }

    fun secondary(pos: BlockPos) {
        main = pos
        markDirty()
    }

    fun reset(cascade: Boolean = true, positions: MutableList<BlockPos> = mutableListOf()): List<BlockPos> {
        if (main != null) {
            positions.add(main!!)
            if (cascade) (world?.getBlockEntity(main) as? RainbowGateBlockEntity)?.reset(positions = positions)
            main = null
        }
        if (others != null) {
            positions.addAll(others!!)
            others?.forEach {
                if (cascade) (world?.getBlockEntity(it) as? RainbowGateBlockEntity)?.reset(false, positions = positions)
            }
            others = null
        }
        markDirty()
        return positions.distinct()
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        main = nbt.getCompoundOrNull("main")?.let(NbtHelper::toBlockPos)
        others = nbt.getListOrNull("others", NBT_COMPOUND)?.mapNotNull { NbtHelper.toBlockPos(it as? NbtCompound ?: return@mapNotNull null) }
        code = nbt.getByteArray("code").takeIf { it.size == 6 }
        direction = nbt.getByteOrNull("direction")?.let { Direction.byId(it.toInt()) }
        offset = nbt.getByteOrNull("offset")
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putCompoundOrRemove("main", main?.let(NbtHelper::fromBlockPos))
        nbt.putListOrRemove("others", others?.map(NbtHelper::fromBlockPos)?.let { NbtList().apply { addAll(it) } })
        if (code != null) nbt.putByteArray("code", code)
        nbt.putByteOrRemove("direction", direction?.id?.toByte())
        nbt.putByteOrRemove("offset", offset)
    }

    private fun calculateCode(): ByteArray? {
        val others = others ?: return null
        val world = world ?: return null
        val map = others.plus(pos).sorted().associateWith {
            val state2 = world.getBlockState(it)
            if (!state2.isOf(LCCBlocks.rainbow_gate) || state2[RainbowGateBlock.type] == RainbowGateBlock.RainbowGateState.INCOMPLETE) return null
            state2[RainbowGateBlock.symbol].minus(1)
        }
        return map.values.map(Int::toByte).toByteArray()
        /*val x = (code[0] + code[5].times(8) + code[4].times(8.square())).minus(255).times(7324)
        val z = (code[1] + code[3].times(8) + code[2].times(8.square())).minus(255).times(7324)
        return ChunkPos(x, z)*/
    }

    override fun shouldListenImmediately() = true

    override fun getPositionSource() = source

    override fun getRange() = 5

    override fun listen(world: ServerWorld, event: GameEvent.Message): Boolean {
        if (event.event != GameEvent.LIGHTNING_STRIKE) return false

        if (!checkRequirements(world, event)) return false
        preparePortal(world)

        code = calculateCode()
        markDirty()
        return true
    }

    private fun checkRequirements(world: ServerWorld, event: GameEvent.Message): Boolean {
        val others = others ?: return false

        val x1 = others.minOfOrNull { it.x } ?: return false
        val x2 = others.maxOfOrNull { it.x } ?: return false
        val y1 = others.minOfOrNull { it.y } ?: return false
        val y2 = others.maxOfOrNull { it.y } ?: return false
        val z1 = others.minOfOrNull { it.z } ?: return false
        val z2 = others.maxOfOrNull { it.z } ?: return false
        if (event.emitterPos.x < x1 || event.emitterPos.x >= x2.plus(1)) return false
        if (event.emitterPos.y < y1 || event.emitterPos.y > y2.plus(1)) return false
        if (event.emitterPos.z < z1 || event.emitterPos.z >= z2.plus(1)) return false
        val direction = when {
            x1 < pos.x -> Direction.WEST
            x2 > pos.x -> Direction.EAST
            z2 > pos.z -> Direction.SOUTH
            else -> Direction.NORTH
        }
        val offset = pos.y.minus(y1).toByte()
        val center = getCenter(direction, offset)
        val perp = direction.rotateYClockwise()

        val pos = BlockPos.Mutable()
        var portal = 0
        for (x in x1..x2) {
            for (y in y1..y2) {
                for (z in z1..z2) {
                    val state = world.getBlockState(pos.set(x, y, z))
                    if (state.isOf(LCCBlocks.rainbow_gate)) {
                        portal++
                        continue
                    }
                    if (!state.isAir && !state.isIn(BlockTags.FIRE) && !state.isOf(Blocks.LIGHTNING_ROD)) return false
                }
            }
        }
        if (portal != 6) return false

        val idols = mutableListOf<IdolBlock>()
        for (i in -2..2 step 4) {
            val dir = (i > 0).transform(perp, perp.opposite)
            for (j in -2..2 step 2) {
                val pos2 = center.offset(perp, i).offset(direction, j)
                val state2 = world.getBlockState(pos2)
                val idol = state2.block as? IdolBlock ?: return false
                if (!state2.isIn(LCCBlockTags.rainbow_gate_idols) || !state2[IdolBlock.pedestal]) return false
                if (!dir.opposite.facing(state2[Properties.ROTATION], j.absoluteValue)) return false
                idols.add(idol)
            }
        }
        if (idols.distinct().size != 6) return false

        this.direction = direction
        this.offset = offset
        return true
    }

    private fun preparePortal(world: ServerWorld) {
        val direction = direction ?: return
        val offset = offset ?: return
        val center = getCenter(direction, offset)
        BlockPos.iterate(center.x - direction.offsetX.absoluteValue, center.y, center.z - direction.offsetZ.absoluteValue, center.x + direction.offsetX.absoluteValue, center.y + 2, center.z + direction.offsetZ.absoluteValue).forEach {
            world.breakBlock(it, true)
        }

        val perp = direction.rotateYClockwise()
        for (i in -2..2 step 4) {
            for (j in -2..2 step 2) {
                val pos2 = center.offset(perp, i).offset(direction, j)
                val state2 = world.getBlockState(pos2)
                if (state2.block !is IdolBlock || !state2[IdolBlock.pedestal]) continue
                world.setBlockState(pos2, LCCBlocks.bifrost_pedestal.defaultState)
            }
        }

        world.setBlockState(this.pos, world.getBlockState(this.pos).with(Properties.LOCKED, true))
        others?.forEach {
            world.setBlockState(it, world.getBlockState(it).with(Properties.LOCKED, true))
        }
    }

    private fun getCenter(dir: Direction, offset: Byte) = this.pos!!.offset(dir, 2).add(0, offset.unaryMinus(), 0)

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RainbowGateBlockEntity) {

        }
    }

}
