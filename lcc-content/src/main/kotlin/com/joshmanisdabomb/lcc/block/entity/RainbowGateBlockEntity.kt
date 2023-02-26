package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.RainbowGateBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.*
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraft.world.event.BlockPositionSource
import net.minecraft.world.event.GameEvent
import net.minecraft.world.event.listener.GameEventListener

class RainbowGateBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.rainbow_gate, pos, state), GameEventListener {

    private var main: BlockPos? = null
    private var others: List<BlockPos>? = null

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
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putCompoundOrRemove("main", main?.let(NbtHelper::fromBlockPos))
        nbt.putListOrRemove("others", others?.map(NbtHelper::fromBlockPos)?.let { NbtList().apply { addAll(it) } })
    }

    private fun calculatePortalPosition(): ChunkPos? {
        val others = others ?: return null
        val world = world ?: return null
        val map = others.plus(pos).sorted().associateWith {
            val state2 = world.getBlockState(it)
            if (!state2.isOf(LCCBlocks.rainbow_gate) || state2[RainbowGateBlock.type] == RainbowGateBlock.RainbowGateState.INCOMPLETE) return null
            state2[RainbowGateBlock.symbol].minus(1)
        }
        val code = map.values.toIntArray()
        println(code.toList())
        val x = (code[0] + code[5].times(8) + code[4].times(8.square())).minus(255).times(7324)
        val z = (code[1] + code[3].times(8) + code[2].times(8.square())).minus(255).times(7324)
        return ChunkPos(x, z)
    }

    override fun shouldListenImmediately() = true

    override fun getPositionSource() = source

    override fun getRange() = 5

    override fun listen(world: ServerWorld, event: GameEvent.Message): Boolean {
        val others = others ?: return false
        if (event.event != GameEvent.LIGHTNING_STRIKE) return false

        val x = (others.minOfOrNull { it.x.toFloat() } ?: return false) .. (others.maxOfOrNull { it.x.toFloat().plus(0.999f) } ?: return false)
        val y = (others.minOfOrNull { it.y.toFloat() } ?: return false) .. (others.maxOfOrNull { it.y.toFloat().plus(1f) } ?: return false)
        val z = (others.minOfOrNull { it.z.toFloat() } ?: return false) .. (others.maxOfOrNull { it.z.toFloat().plus(0.999f) } ?: return false)
        if (event.emitterPos.x in x && event.emitterPos.y in y && event.emitterPos.z in z) {
            println(calculatePortalPosition())
            println("hello")
        }
        return true
    }

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RainbowGateBlockEntity) {

        }
    }

}
