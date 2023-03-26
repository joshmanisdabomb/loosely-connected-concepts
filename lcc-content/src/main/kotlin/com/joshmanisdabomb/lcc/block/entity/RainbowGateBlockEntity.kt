package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.IdolBlock
import com.joshmanisdabomb.lcc.block.RainbowGateBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.directory.tags.LCCBlockTags
import com.joshmanisdabomb.lcc.extensions.*
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.world.World
import net.minecraft.world.event.BlockPositionSource
import net.minecraft.world.event.GameEvent
import net.minecraft.world.event.listener.GameEventListener
import net.minecraft.world.explosion.Explosion
import kotlin.math.absoluteValue

class RainbowGateBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.rainbow_gate, pos, state), GameEventListener {

    private var main: BlockPos? = null
    private var others: List<BlockPos>? = null

    private var _direction: Direction? = null
    private var _offset: Byte? = null
    private var _sequence: Byte? = null
    private var idols: List<BlockPos>? = null
    private var code: ByteArray? = null

    private val source = BlockPositionSource(pos)
    val sequence get() = _sequence
    val direction get() = _direction
    val offset get() = _offset

    @Environment(EnvType.CLIENT)
    val shardOffsets = Array(6) { Array(4*9*4) { Vec3d(0.0, 0.0, 0.0) } }
    @Environment(EnvType.CLIENT)
    val shardLastOffsets = Array(6) { Array(4*9*4) { Vec3d(0.0, 0.0, 0.0) } }
    @Environment(EnvType.CLIENT)
    val shardRotations = Array(6) { Array(4*9*4) { Vec3f(0f, 0f, 0f) } }
    @Environment(EnvType.CLIENT)
    val shardLastRotations = Array(6) { Array(4*9*4) { Vec3f(0f, 0f, 0f) } }
    @Environment(EnvType.CLIENT)
    val shardDirections = Array(6) { Array(4*9*4) { Vec3d(0.0, 0.0, 0.0) } }

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
        _direction = nbt.getByteOrNull("direction")?.let { Direction.byId(it.toInt()) }
        _offset = nbt.getByteOrNull("offset")
        _sequence = nbt.getByteOrNull("sequence")

        idols = nbt.getListOrNull("idols", NBT_COMPOUND)?.mapNotNull { NbtHelper.toBlockPos(it as? NbtCompound ?: return@mapNotNull null) }
        if (world?.isClient == true) {
            this.setupShards()
        }
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putCompoundOrRemove("main", main?.let(NbtHelper::fromBlockPos))
        nbt.putListOrRemove("others", others?.map(NbtHelper::fromBlockPos)?.let { NbtList().apply { addAll(it) } })
        if (code != null) nbt.putByteArray("code", code)
        nbt.putByteOrRemove("direction", _direction?.id?.toByte())
        nbt.putByteOrRemove("offset", _offset)
        nbt.putByteOrRemove("sequence", _sequence)
        nbt.putListOrRemove("idols", idols?.map(NbtHelper::fromBlockPos)?.let { NbtList().apply { addAll(it) } })
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

    private fun setupShards() {
        val idols = idols ?: return
        val world = world ?: return
        for ((k, offset) in idols.withIndex()) {
            val vec = Vec3d.of(offset)
            shardOffsets[k] = Array(4*9*4) {
                Vec3d(vec.x, vec.y, vec.z).add(it.rem(4).times(0.0625).minus(0.125), it.div(4).rem(9).times(0.0625).minus(0.0625), it.div(4).div(9).rem(4).times(0.0625).minus(0.125))
            }
            shardLastOffsets[k] = shardOffsets[k].clone()

            shardDirections[k] = shardOffsets[k].map {
                val v = it.multiply(1.0, 0.0, 1.0).normalize()
                v.multiply(
                    world.random.nextDouble().times(0.3).plus(1.0),
                    1.0,
                    world.random.nextDouble().times(0.3).plus(1.0))
                .add(
                    world.random.nextDouble().minus(0.5).times(0.5),
                    world.random.nextDouble().times(0.4).plus(0.1),
                    world.random.nextDouble().minus(0.5).times(0.5)
                ).normalize().multiply(world.random.nextDouble().times(1.0).plus(1.0))
            }.toTypedArray()

            shardLastRotations[k] = shardRotations[k].clone()
            shardRotations[k] = Array(4*9*4) {
                Vec3f(
                    world.random.nextFloat().minus(0.5f).times(40f),
                    world.random.nextFloat().minus(0.5f).times(40f),
                    world.random.nextFloat().minus(0.5f).times(40f)
                )
            }
        }
    }

    private fun animateShards() {
        shardOffsets.indices.forEach { i ->
            shardOffsets[i].indices.forEach { j ->
                shardLastOffsets[i][j] = shardOffsets[i][j]
                val initial = shardDirections[i][j].multiply(0.75)
                val drag = shardOffsets[i][j].normalize().multiply(0.05.unaryMinus().times(sequence?.toInt() ?: 1))
                val total = initial.add(drag)
                val distance = shardOffsets[i][j].lengthSquared()
                if (distance < 1.0) {
                    shardOffsets[i][j] = shardOffsets[i][j].add(total.normalize().multiply(distance))
                } else {
                    shardOffsets[i][j] = shardOffsets[i][j].add(total)
                }

                shardLastRotations[i][j] = shardRotations[i][j]
                shardRotations[i][j].add(shardRotations[i][j].copy().apply(Vec3f::normalize))
            }
        }
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt() = NbtCompound().also {
        it.putByteOrRemove("direction", _direction?.id?.toByte())
        it.putByteOrRemove("offset", _offset)
        it.putByteOrRemove("sequence", _sequence)
        it.putListOrRemove("idols", idols?.map(NbtHelper::fromBlockPos)?.let { NbtList().apply { addAll(it) } })
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

        this._direction = direction
        this._offset = offset
        return true
    }

    private fun preparePortal(world: ServerWorld) {
        val direction = _direction ?: return
        val offset = _offset ?: return
        val center = getCenter(direction, offset)
        BlockPos.iterate(center.x - direction.offsetX.absoluteValue, center.y, center.z - direction.offsetZ.absoluteValue, center.x + direction.offsetX.absoluteValue, center.y + 2, center.z + direction.offsetZ.absoluteValue).forEach {
            world.breakBlock(it, true)
        }

        val perp = direction.rotateYClockwise()
        val idols = mutableMapOf<Block, BlockPos>()
        for (i in -2..2 step 4) {
            for (j in -2..2 step 2) {
                val pos2 = center.offset(perp, i).offset(direction, j)
                val state2 = world.getBlockState(pos2)
                if (state2.block !is IdolBlock || !state2[IdolBlock.pedestal]) continue
                idols[state2.block] = pos2.subtract(center.up())
                world.setBlockState(pos2, LCCBlocks.bifrost_pedestal.defaultState)
            }
        }
        this.idols = idols.toSortedMap(Comparator.comparingInt { LCCBlocks.all.values.indexOf(it) }).values.toList()

        world.setBlockState(pos, world.getBlockState(pos).with(Properties.LOCKED, true))
        others?.forEach {
            world.setBlockState(it, world.getBlockState(it).with(Properties.LOCKED, true))
        }
        _sequence = 0

        val explosion = Vec3d.ofCenter(center).add(0.0, 1.0, 0.0)
        world.createExplosion(null, LCCDamage.rainbow_gate, null, explosion.x, explosion.y, explosion.z, 4.0f, false, Explosion.DestructionType.NONE)
        world.chunkManager.markForUpdate(pos)
    }

    private fun openPortal(world: ServerWorld) {
        val direction = _direction ?: return
        val offset = _offset ?: return
        val center = getCenter(direction, offset)

        val explosion = Vec3d.ofCenter(center).add(0.0, 1.0, 0.0)
        world.createExplosion(null, LCCDamage.rainbow_gate, null, explosion.x, explosion.y, explosion.z, 4.0f, false, Explosion.DestructionType.NONE)
    }

    private fun getCenter(dir: Direction, offset: Byte) = this.pos!!.offset(dir, 2).add(0, offset.unaryMinus(), 0)

    companion object {
        fun clientTick(world: World, pos: BlockPos, state: BlockState, entity: RainbowGateBlockEntity) {
            val ticks = entity._sequence ?: return
            if (ticks.toInt() == 80) {
                entity._sequence = null
                entity.markDirty()
            } else if (ticks < 80) {
                entity.animateShards()
                entity._sequence = ticks.inc()
                entity.markDirty()
            }
        }

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RainbowGateBlockEntity) {
            val ticks = entity._sequence ?: return
            if (ticks.toInt() == 80) {
                entity.openPortal(world as ServerWorld)
                entity._sequence = null
                entity.markDirty()
            } else if (ticks < 80) {
                entity._sequence = ticks.inc()
                entity.markDirty()
            }
        }
    }

}
