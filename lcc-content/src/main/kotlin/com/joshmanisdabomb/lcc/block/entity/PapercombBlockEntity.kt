package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.PapercombBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.entity.WaspEntity
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import net.minecraft.block.BlockState
import net.minecraft.block.FireBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class PapercombBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.papercomb, pos, state) {

    val entries = mutableListOf<WaspEntry>()

    override fun readNbt(nbt: NbtCompound) {
        entries.clear()
        for (it in nbt.getList("Wasps", NBT_COMPOUND)) {
            if (it !is NbtCompound) continue
            entries += WaspEntry(it.getCompound("EntityData"), it.getInt("TicksInHive"))
        }
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        val list = NbtList()
        for (wasp in entries) {
            val w = NbtCompound()
            w.put("EntityData", wasp.nbt)
            w.putInt("TicksInHive", wasp.ticks)
            list.add(w)
        }
        nbt.put("Wasps", list)
    }

    fun canEnter(wasp: WaspEntity) = !isNearFire() && !isFull() && wasp.world.isNight

    fun enter(wasp: WaspEntity, ticks: Int = 0) {
        wasp.stopRiding()
        wasp.removeAllPassengers()
        val nbt = NbtCompound()
        wasp.saveNbt(nbt)
        entries.add(WaspEntry(nbt, ticks))
        world?.also {
            it.playSound(null, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F)
        }
        wasp.discard()
    }

    fun spawn(ticks: Int = 599, nbt: NbtCompound = NbtCompound()) {
        nbt.putString("id", Registry.ENTITY_TYPE.getId(LCCEntities.wasp).toString())
        entries.add(WaspEntry(nbt, 599))
    }

    fun isNearFire(): Boolean {
        //TODO wasp network check
        for (bp in BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            if (world?.getBlockState(bp)?.block is FireBlock) return true
        }
        return false
    }

    fun isFull(): Boolean {
        return entries.count() >= 1
    }

    fun isClose(wasp: WaspEntity): Boolean {
        if (wasp.blockPos.isWithinDistance(pos, 2.0)) return true
        val path = wasp.navigation.currentPath
        return path != null && path.target == pos && path.reachesTarget() && path.isFinished
    }

    fun emergency(aggressor: LivingEntity?): List<WaspEntity> {
        val list2 = mutableListOf<WaspEntity>()
        val list = entries.filter {
            val wasp = release(it, true) ?: return@filter false
            if (aggressor != null && wasp.pos.squaredDistanceTo(aggressor.pos) <= 1024) {
                wasp.target = aggressor
            }
            list2.add(wasp)
            true
        }
        entries.removeAll(list)
        return list2
    }

    private fun release(data: WaspEntry, emergency: Boolean): WaspEntity? {
        val world = world ?: return null
        if (!emergency && (world.isNight || world.isRaining)) return null
        data.nbt.remove("Passengers")
        data.nbt.remove("Leash")
        data.nbt.remove("UUID")
        for (d in Direction.values().apply { shuffle() }) {
            val pos2 = pos.offset(d)
            val state = world.getBlockState(pos2)
            val collision = state.block !is PapercombBlock && !state.getCollisionShape(world, pos2).isEmpty
            if (collision && !emergency) continue
            val wasp = EntityType.loadEntityWithPassengers(data.nbt, world) { it } as? WaspEntity ?: return null
            if (wasp.breedingAge < 0) {
                wasp.breedingAge = wasp.breedingAge.plus(data.ticks).coerceAtMost(0)
            } else if (wasp.breedingAge > 0) {
                wasp.breedingAge = wasp.breedingAge.minus(data.ticks).coerceAtLeast(0)
            }
            wasp.loveTicks = wasp.loveTicks.minus(data.ticks).coerceAtLeast(0)

            val f = if (collision) 0.0 else 0.5 + (wasp.width / 2.0f)
            val x = pos.x + 0.5 + f.times(d.offsetX)
            val y = pos.y + 0.5 + (f + (wasp.height / 2.0f)).times(d.offsetY)
            val z = pos.z + 0.5 + f.times(d.offsetZ)
            wasp.refreshPositionAndAngles(x, y, z, wasp.yaw, wasp.pitch)

            if (world.spawnEntity(wasp)) {
                world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f)
                return wasp
            }
        }
        return null
    }

    data class WaspEntry(val nbt: NbtCompound, var ticks: Int)

    companion object {

        fun tick(world: World, pos: BlockPos, state: BlockState, entity: PapercombBlockEntity) {
            entity.entries.removeIf {
                if (it.ticks++ > 600) {
                    if (entity.release(it, false) != null) {
                        return@removeIf true
                    }
                }
                false
            }
        }

    }

}
