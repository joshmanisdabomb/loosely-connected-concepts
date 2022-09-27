package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCPacketsToClient
import com.joshmanisdabomb.lcc.directory.LCCParticles
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class DiscipleDustEntity : ProjectileEntity {

    var ticks = 0
    var lifetime = 60

    constructor(type: EntityType<out DiscipleDustEntity>, world: World) : super(type, world)

    constructor(world: World, owner: LivingEntity) : this(LCCEntities.disciple_dust, world) {
        this.owner = owner
        this.setPosition(owner.x, owner.y + owner.standingEyeHeight, owner.z)
    }

    override fun initDataTracker() {

    }

    override fun readCustomDataFromNbt(tag: NbtCompound) {
        ticks = tag.getShort("Age").toInt()
        lifetime = tag.getShort("Lifetime").run { if (this > 0) this else 60 }.toInt()
    }

    override fun writeCustomDataToNbt(tag: NbtCompound) {
        tag.putShort("Age", ticks.toShort())
        tag.putShort("Lifetime", lifetime.toShort())
    }

    override fun tick() {
        val owner = owner
        if (owner?.isRemoved != false) return discard()
        if (world.isClient && !world.isChunkLoaded(blockPos)) {
            discard()
            return
        }

        super.tick()
        val hitResult = ProjectileUtil.getCollision(this, ::canHit)
        if (hitResult.type != HitResult.Type.MISS) {
            onCollision(hitResult)
        }
        checkBlockCollision()

        val vec3d = velocity
        ProjectileUtil.setRotationFromVelocity(this, 0.2f)
        this.setPosition(this.x + vec3d.x, this.y + vec3d.y, this.z + vec3d.z)

        if (world.isClient) {
            repeat(6 - MinecraftClient.getInstance().options.particles.value.id.times(2)) {
                val dir = velocity.normalize().multiply(0.07)
                val pos = Vec3d(world.random.nextDouble().minus(0.5), world.random.nextDouble().minus(0.5), world.random.nextDouble().minus(0.5)).normalize().multiply(0.3).add(pos)
                world.addParticle(LCCParticles.disciple_dust, pos.x, pos.y, pos.z, -dir.x, -dir.y, -dir.z)
            }
        } else {
            ticks += 1
            if (ticks > lifetime || velocity == Vec3d.ZERO) {
                explode()
                return
            }
        }

        var vel = velocity
        val l = vel.lengthSquared()
        if (l < 0.75) {
            val add = (0.75 - l).times(0.4f).coerceAtLeast(0.0).plus(1.0)
            vel = vel.multiply(add, add, add)
        }

        val homing = (owner as? DiscipleEntity)?.healTarget
        if (homing != null) {
            val to = Vec3d(homing.x - x, homing.y.plus(homing.standingEyeHeight.times(0.5)) - y, homing.z - z)
            val l2 = to.lengthSquared()
            val correction = 2.0.div(l2).times(l2.coerceAtMost(1.0))
            vel = vel.normalize().add(to.normalize().multiply(correction)).multiply(vel.length().div(correction.plus(1.0)))
        }
        velocity = vel
    }

    fun explode() {
        if (!world.isClient) {
            world.getEntitiesByClass(HostileEntity::class.java, Box.of(pos, 4.0, 4.0, 4.0), EntityPredicates.EXCEPT_SPECTATOR).forEach {
                HeartType.TEMPORARY.addHealth(it, 4.0f)
            }
            PlayerLookup.tracking(this).forEach {
                ServerPlayNetworking.send(it, LCCPacketsToClient[LCCPacketsToClient::disciple_dust_blast_particle].first().id, PacketByteBuf(Unpooled.buffer()).also { it.writeBoolean(true).writeDouble(pos.x).writeDouble(pos.y).writeDouble(pos.z).writeDouble(0.0).writeDouble(0.0).writeDouble(0.0).writeFloat(4.0f) })
            }
            discard()
        }
    }

    override fun onEntityHit(entityHitResult: EntityHitResult) {
        if (entityHitResult.entity !is DiscipleEntity) {
            explode()
        }
    }

    override fun onBlockHit(blockHitResult: BlockHitResult) {
        explode()
    }

}