package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCStructureFeatures
import com.joshmanisdabomb.lcc.extensions.NBT_LIST
import com.joshmanisdabomb.lcc.extensions.addFloats
import com.joshmanisdabomb.lcc.extensions.getFloatList
import com.joshmanisdabomb.lcc.extensions.sqrt
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class WastelandObeliskBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.wasteland_obelisk, pos, state) {

    var positions = mutableListOf<BlockPos?>()

    var yaw: List<Float>? = null
    var distance: List<Float>? = null

    var step = 0

    override fun readNbt(nbt: NbtCompound) {
        if (nbt.contains("Yaw", NBT_LIST)) yaw = nbt.getFloatList("Yaw")
        else yaw = null
        if (nbt.contains("Distance", NBT_LIST)) distance = nbt.getFloatList("Distance")
        else distance = null
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        super.writeNbt(nbt)

        yaw?.also { nbt.put("Yaw", NbtList().apply { addFloats(it) }) }
        distance?.also { nbt.put("Distance", NbtList().apply { addFloats(it) }) }

        return nbt
    }

    fun activate(world: ServerWorld, alternate: Boolean) {
        positions = mutableListOf(world.locateStructure(LCCStructureFeatures.wasteland_obelisk, pos, 100, false))
        step = 1
        cooldown = world.time
    }

    companion object {
        var cooldown = 0L

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: WastelandObeliskBlockEntity) {
            if (entity.step > 8) {
                val positions = entity.positions.filterNotNull().distinct()
                if (positions.isNotEmpty()) {
                    entity.distance = positions.map { pos.getSquaredDistance(it, true).sqrt().toFloat() }
                    entity.yaw = positions.map {
                        val vec = it.subtract(pos)
                        -MathHelper.atan2(vec.x.toDouble(), vec.z.toDouble()).toFloat() * 57.295776f
                    }
                }
                entity.positions.clear()
                entity.step = 0
            } else if (entity.step > 0) {
                val dist = if (entity.step % 2 == 0) 4000 else 40000
                val x = if (entity.step % 4 >= 2) 1 else -1
                val z = if (entity.step % 8 >= 4) 1 else -1
                val next = (world as ServerWorld).locateStructure(LCCStructureFeatures.wasteland_obelisk, pos.add(x.times(dist), 0, z.times(dist)), 100, false)
                if (next == null || entity.positions.contains(next)) {
                    entity.step += 1
                } else {
                    entity.positions.add(next)
                    entity.step = entity.step.div(2).plus(1).times(2)
                }
            }
        }
    }

}
