package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*

class NuclearExplosionEntity(type: EntityType<out NuclearExplosionEntity>, world: World) : Entity(type, world), LCCExtendedEntity {

    private var ticks = 0
    private var lifetime = 0
    private var radius = 0

    private val radius_d by lazy { radius.toDouble() }
    private val sqradius_d by lazy { radius_d*radius_d }
    private val tickCoverage by lazy { sqradius_d.div(lifetime) }

    private val bp by lazy { BlockPos.Mutable() }
    private val bp2 by lazy { BlockPos.Mutable() }
    private val bp3 by lazy { BlockPos.Mutable() }
    private val pos by lazy { blockPos }
    private val vec by lazy { toVec(pos) }
    private val x_range by lazy { -radius..radius }
    private val y_range by lazy { -radius..radius }
    private val z_range by lazy { -radius..radius }

    override fun tick() {
        if (lifetime < 1 || radius < 1) return this.discard()
        super.tick()
        if (!world.isClient) {
            val percent = ticks.toFloat().div(lifetime)
            val innerSqDistance = sqradius_d.times(percent)
            val outerSqDistance = innerSqDistance.plus(tickCoverage)
            for (i in x_range) {
                for (j in y_range) {
                    for (k in z_range) {
                        bp.set(pos).move(i, j, k)
                        val squaredDistance = bp.getSquaredDistance(pos)
                        if (squaredDistance <= outerSqDistance && squaredDistance > innerSqDistance) {
                            raycast(world, vec, toVec(bp), squaredDistance)
                        }
                    }
                }
            }
            if (ticks++ >= lifetime) {
                this.discard()
                return
            }
            dataTracker.set(tick_data, ticks)
        }
    }

    private fun toVec(pos: BlockPos) = Vec3d(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5)

    private fun raycast(world: World, start: Vec3d, end: Vec3d, sqDist: Double, step: Double = 1.0.div(3)) {
        val dir = end.subtract(start)
        val norm = dir.normalize()
        val step = norm.multiply(step)
        val step0 = step.lengthSquared() <= 0.0
        var current = step
        val mod = sqDist.div(sqradius_d)
        val mod_fire = mod.times(1.2)
        val mod2 = mod*mod
        val mod4 = mod2*mod2
        val mod_waste = mod4.times(0.5)
        var x: Int? = null
        var y: Int? = null
        var z: Int? = null
        while (current.lengthSquared() <= dir.lengthSquared() && !step0) {
            val pos = start.add(current)
            val nx = pos.x.toInt(); val ny = pos.y.toInt(); val nz = pos.z.toInt()
            if (x == nx && y == ny && z == ny) {
                current = current.add(step)
                continue
            }
            x = nx; y = ny; z = nz;
            bp2.set(x, y, z)
            bp3.set(bp2).move(0, 1, 0)

            val state = world.getBlockState(bp2)
            val block = state.block
            if (state.isAir || (block as? LCCExtendedBlockContent)?.lcc_content_nukeIgnore() == true) {
                current = current.add(step)
                continue
            }

            val resistance = (block.blastResistance*block.blastResistance*block.blastResistance).div(240.0).coerceIn(0.01, 0.9).times(mod)

            if (block is FluidBlock) {
                world.setBlockState(bp2, state_air, flags, depth)
            } else if (block.blastResistance > 2000 || fast_rand.nextDouble() <= mod2.plus(resistance)) {
                return
            } else {
                alter(world, bp2, state, block, mod_waste, mod_fire, bp3)
            }

            current = current.add(step)
        }
    }

    private fun alter(world: World, pos: BlockPos, state: BlockState, block: Block, mod_waste: Double, mod_fire: Double, above: BlockPos) {
        if (fast_rand.nextDouble() > mod_waste) {
            world.setBlockState(pos, state_air, flags, depth)
        } else {
            world.setBlockState(pos, state_waste, flags, depth)
            if (fast_rand.nextDouble() < mod_fire && world.getBlockState(above).isAir) {
                world.setBlockState(above, state_fire, flags, depth)
            }
        }
    }

    override fun initDataTracker() {
        dataTracker.startTracking(tick_data, 0)
        dataTracker.startTracking(lifetime_data, 0)
        dataTracker.startTracking(radius_data, 0)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        if (data == tick_data) ticks = dataTracker.get(tick_data)
        if (data == lifetime_data) lifetime = dataTracker.get(lifetime_data)
        if (data == radius_data) radius = dataTracker.get(radius_data)
        super.onTrackedDataSet(data)
    }

    override fun readCustomDataFromTag(tag: CompoundTag) {
        tag.getShort("Age").toInt().apply { ticks = this; dataTracker.set(tick_data, ticks) }
        tag.getShort("Lifetime").run { if (this > 0) this else 30 }.toInt().apply { lifetime = this; dataTracker.set(lifetime_data, lifetime) }
        tag.getShort("Radius").run { if (this > 0) this else 20 }.toInt().apply { radius = this; dataTracker.set(radius_data, radius) }
    }

    override fun writeCustomDataToTag(tag: CompoundTag) {
        tag.putShort("Age", ticks.toShort())
        tag.putShort("Lifetime", lifetime.toShort())
        tag.putShort("Radius", radius.toShort())
    }

    override fun createSpawnPacket() = lcc_createSpawnPacket()

    companion object {
        private val fast_rand = SplittableRandom()

        private const val flags = 7
        private const val depth = 2

        private val state_air by lazy { Blocks.AIR.defaultState }
        private val state_waste by lazy { LCCBlocks.nuclear_waste.defaultState }
        private val state_fire by lazy { LCCBlocks.nuclear_fire.defaultState }

        val tick_data = DataTracker.registerData(NuclearExplosionEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        val lifetime_data = DataTracker.registerData(NuclearExplosionEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        val radius_data = DataTracker.registerData(NuclearExplosionEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
    }

}