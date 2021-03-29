package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntity
import com.joshmanisdabomb.lcc.directory.*
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.nbt.NbtCompound
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.util.Unit
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.Heightmap
import net.minecraft.world.World
import java.util.*

class NuclearExplosionEntity(type: EntityType<out NuclearExplosionEntity>, world: World) : Entity(type, world), LCCExtendedEntity {

    constructor(world: World, x: Double, y: Double, z: Double, by: LivingEntity?) : this(LCCEntities.nuclear_explosion, world) {
        updatePosition(x, y, z)
        prevX = x
        prevY = y
        prevZ = z
        causedBy = by
    }

    var causedBy: LivingEntity? = null

    var ticks = 0
    var lifetime = 0
    var radius = 0

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
    private val step by lazy { 1.0.div(3) }

    override fun isAttackable() = false

    override fun tick() {
        if (lifetime < 1 || radius < 1) return
        super.tick()
        if (!world.isClient) {
            if (ticks == 0) {
                NuclearUtil.strike(world, this)
                LOGGER.info("A nuclear explosion occurred at $blockPos of radius $radius, detonated by ${causedBy?.name?.asString()}.")
                world.playSound(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, LCCSounds.nuclear_explosion_explode, SoundCategory.BLOCKS, 20.0F, 1.0F)
                (world as? ServerWorld)?.also {
                    //Change spawn if too close to blast.
                    val spawn = it.spawnPos
                    if (blockPos.isWithinDistance(spawn, radius+25.0)) {
                        val new = it.getTopPosition(Heightmap.Type.MOTION_BLOCKING, BlockPos(Vec3d(random.nextDouble() - 0.5, 0.0, random.nextDouble() - 0.5).normalize().multiply(radius+25.0).add(spawn.x.toDouble(), 0.0, spawn.z.toDouble())))
                        it.setSpawnPos(new, it.spawnAngle)
                        LOGGER.info("The world spawnpoint was moved from $spawn to $new due to being too close to the nuclear explosion.")
                    }
                    //Advancement trigger.
                    (causedBy as? ServerPlayerEntity)?.also { LCCCriteria.nuke.trigger(it, NuclearUtil.getUraniumFromExplosionRadius(radius).toInt()) }
                }
            }
            (world as ServerWorld).chunkManager.addTicket(LCCChunkTickets.nuclear, chunkPos, 17, Unit.INSTANCE)
            val percent = ticks.toFloat().div(lifetime)
            val innerSqDistance = sqradius_d.times(percent)
            val outerSqDistance = innerSqDistance.plus(tickCoverage)
            for (j in y_range) {
                if (world.isOutOfHeightLimit(pos.y + j)) continue
                for (i in x_range) {
                    for (k in z_range) {
                        bp.set(pos).move(i, j, k)
                        val squaredDistance = bp.getSquaredDistance(pos)
                        if (squaredDistance <= outerSqDistance && squaredDistance > innerSqDistance) {
                            this.raycast(world, vec, toVec(bp), squaredDistance, ::alterBlocks)
                        }
                    }
                }
            }
            val harmRadius = outerSqDistance.times(1.3)
            val list = world.getOtherEntities(this, Box(pos).expand(radius.times(1.3)), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)
            for (e in list) {
                if (!e.isAlive) continue
                val sqdist = e.squaredDistanceTo(this)
                val h = e.height.times(0.5)
                for (i in 0..2) {
                    if (!this.raycast(world, vec, e.pos.add(0.0, h.times(i), 0.0), sqdist, ::checkBlocks)) {
                        if (sqdist <= outerSqDistance) {
                            e.damage(LCCDamage.nuke(causedBy), sqradius_d.minus(sqdist).times(0.18).toFloat())
                            e.fireTicks = Short.MAX_VALUE - 4
                            (e as? LivingEntity)?.also { NuclearUtil.addRadiation(it, 200, 4) }
                        } else if (sqdist <= harmRadius) {
                            e.damage(LCCDamage.nuke(causedBy), 1f)
                            (e as? LivingEntity)?.also { NuclearUtil.addRadiation(it, 100, 0) }
                        }
                        (e as? LivingEntity)?.hurtTime = 0
                        e.timeUntilRegen = 0
                        break
                    }
                }
            }
            if (ticks++ >= lifetime) {
                this.discard()
                return
            }
            dataTracker.set(tick_data, ticks)
            dataTracker.set(radius_data, radius)
            dataTracker.set(lifetime_data, lifetime)
        }
    }

    private fun toVec(pos: BlockPos) = Vec3d(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5)

    private fun raycast(world: World, start: Vec3d, end: Vec3d, sqDist: Double, func: (world: World, target: BlockPos, state: BlockState, block: Block, above: BlockPos, mod: Double, mod2: Double, mod_waste: Double, mod_fire: Double) -> Boolean, step: Double = this.step): Boolean {
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

            if (func(world, bp2, state, block, bp3, mod, mod2, mod_waste, mod_fire)) return true

            current = current.add(step)
        }
        return false
    }

    private fun alterBlocks(world: World, target: BlockPos, state: BlockState, block: Block, above: BlockPos, mod: Double, mod2: Double, mod_waste: Double, mod_fire: Double): Boolean {
        val resistance = (block as? LCCExtendedBlockContent)?.lcc_content_nukeResistance(state, target, fast_rand) ?: block.blastResistance
        val r = resistance.times(resistance).times(resistance).div(240.0).coerceIn(0.01, 0.9).times(mod)

        if (block is FluidBlock) {
            world.setBlockState(target, state_air, flags, depth)
            return false
        } else if (resistance > 2000 || fast_rand.nextDouble() <= mod2.plus(r)) {
            return true
        } else {
            if (fast_rand.nextDouble() > mod_waste) {
                world.setBlockState(target, state_air, flags, depth)
                return false
            } else {
                world.setBlockState(target, state_waste, flags, depth)
                if (fast_rand.nextDouble() < mod_fire && world.getBlockState(above).isAir) {
                    world.setBlockState(above, state_fire, flags, depth)
                }
                return false
            }
        }
    }

    private fun checkBlocks(world: World, target: BlockPos, state: BlockState, block: Block, above: BlockPos, mod: Double, mod2: Double, mod_waste: Double, mod_fire: Double): Boolean {
        return block !is FluidBlock
    }

    override fun initDataTracker() {
        dataTracker.startTracking(tick_data, ticks)
        dataTracker.startTracking(lifetime_data, lifetime)
        dataTracker.startTracking(radius_data, radius)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        if (data == tick_data) ticks = dataTracker.get(tick_data)
        if (data == lifetime_data) lifetime = dataTracker.get(lifetime_data)
        if (data == radius_data) radius = dataTracker.get(radius_data)
        super.onTrackedDataSet(data)
    }

    override fun readCustomDataFromNbt(tag: NbtCompound) {
        tag.getShort("Age").toInt().apply { ticks = this; dataTracker.set(tick_data, ticks) }
        tag.getShort("Lifetime").run { if (this > 0) this else 30 }.toInt().apply { lifetime = this; dataTracker.set(lifetime_data, lifetime) }
        tag.getShort("Radius").run { if (this > 0) this else 20 }.toInt().apply { radius = this; dataTracker.set(radius_data, radius) }
    }

    override fun writeCustomDataToNbt(tag: NbtCompound) {
        tag.putShort("Age", ticks.toShort())
        tag.putShort("Lifetime", lifetime.toShort())
        tag.putShort("Radius", radius.toShort())
    }

    override fun createSpawnPacket() = lcc_createSpawnPacket()

    override fun remove(reason: RemovalReason) {
        if (!world.isClient) (world as? ServerWorld)?.chunkManager?.removeTicket(LCCChunkTickets.nuclear, chunkPos, 17, Unit.INSTANCE)
        super.remove(reason)
    }

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