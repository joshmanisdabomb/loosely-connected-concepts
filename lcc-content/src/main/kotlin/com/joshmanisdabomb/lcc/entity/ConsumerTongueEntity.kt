package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.sound.ConsumerTongueSoundInstance
import com.joshmanisdabomb.lcc.trait.LCCEntityTrait
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class ConsumerTongueEntity(type: EntityType<out ProjectileEntity>, world: World) : ProjectileEntity(type, world), LCCEntityTrait {

    private var hookedEntity: Entity? = null
    val hooked get() = hookedEntity

    fun getTargetY(y: Double? = owner?.y, eyeHeight: Float = owner?.standingEyeHeight ?: 0f) = y?.plus(eyeHeight)?.minus(0.2)

    @Environment(EnvType.CLIENT)
    var sound: ConsumerTongueSoundInstance? = null

    init {
        ignoreCameraFrustum = true
    }

    constructor(world: World, owner: LivingEntity) : this(LCCEntities.consumer_tongue, world) {
        this.owner = owner
        this.setPosition(owner.x, getTargetY()!!, owner.z)
    }

    override fun initDataTracker() {
        dataTracker.startTracking(hooked_id, 0)
        dataTracker.startTracking(retract, false)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        if (data == hooked_id) {
            val id = dataTracker.get(hooked_id)
            this.hookedEntity = if (id > 0) world.getEntityById(id - 1) else null
        }
        super.onTrackedDataSet(data)
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {}

    override fun readCustomDataFromNbt(nbt: NbtCompound) {}

    override fun shouldRender(distance: Double) = distance < 4096.0

    override fun tick() {
        val entity = owner ?: return discard()
        if (entity.isRemoved) return discard()

        if (entity is PlayerEntity) {
            entity.itemCooldownManager.set(LCCItems.consumer_maw, if (entity.isSurvival) 90 else 10)
        }

        val entity2 = hookedEntity
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

        val ownerDist = this.squaredDistanceTo(Vec3d(entity.x, getTargetY()!!, entity.z))
        if (dataTracker.get(retract)) {
            if (ownerDist < 0.8) {
                discard()
                if (entity2 != null && entity2.squaredDistanceTo(this) < 10.0) {
                    entity2.setVelocity(0.0, 0.0, 0.0)
                    entity2.velocityModified = true
                    entity2.velocityDirty = true
                    if (entity is MobEntity) {
                        entity.swingHand(Hand.MAIN_HAND)
                        entity.tryAttack(entity2)
                    }
                }
                return
            } else {
                val e = entity.x - this.x
                val f = getTargetY()!! - this.y
                val g = entity.z - this.z
                this.setVelocity(e, f, g, tongueSpeed.div(2f), 0f)
                if (entity2 != null) {
                    val y = this.y + entity2.height.div(2.0)

                    val h = this.x - entity2.x
                    val i = y - entity2.y
                    val j = this.z - entity2.z
                    val hookDist = h*h + i*i + j*j

                    if (hookDist < 24.0) {
                        entity2.velocity = Vec3d(this.x - entity2.x, y - entity2.y, this.z - entity2.z)
                        entity2.isOnGround = false
                        entity2.fallDistance = 0f
                        entity2.velocityModified = true
                        entity2.velocityDirty = true
                        if (entity2 is MobEntity) {
                            entity2.addStatusEffect(StatusEffectInstance(LCCEffects.stun, 2, 0, true, false, true))
                        }
                    } else {
                        this.unhook()
                    }
                }
            }
        } else {
            if (ownerDist > 768.0) {
                dataTracker.set(retract, true)
            }
        }
        val vec3d = velocity
        ProjectileUtil.setRotationFromVelocity(this, 0.2f)
        this.setPosition(this.x + vec3d.x, this.y + vec3d.y, this.z + vec3d.z)
    }

    override fun canHit(entity: Entity): Boolean {
        return super.canHit(entity) && entity != owner && (entity as? TameableEntity)?.owner != owner
    }

    override fun onEntityHit(hit: EntityHitResult) {
        super.onEntityHit(hit)
        if (!world.isClient && dataTracker.get(hooked_id) <= 0) {
            this.hook(hit.entity)
        }
    }

    override fun onBlockHit(hit: BlockHitResult) {
        super.onBlockHit(hit)
        if (!world.isClient && !dataTracker.get(retract)) {
            dataTracker.set(retract, true)
        }
    }

    override fun onCollision(hitResult: HitResult) {
        super.onCollision(hitResult)
    }

    override fun getMoveEffect() = MoveEffect.NONE

    override fun canUsePortals() = false

    override fun createSpawnPacket() = lcc_createSpawnPacket {
        this.putInt("owner", owner?.id?.plus(1) ?: 0)
    }

    override fun lcc_readSpawnPacket(data: NbtCompound?) {
        super.lcc_readSpawnPacket(data)
        val owner = data?.getInt("owner") ?: return
        if (owner > 1) {
            val entity = world.getEntityById(owner - 1)
            this.owner = entity
            if (entity is ConsumerEntity) {
                entity.tongue = this
            }
        }
        sound = ConsumerTongueSoundInstance(this)
        MinecraftClient.getInstance().soundManager.playNextTick(sound)
    }

    private fun hook(entity: Entity) {
        hookedEntity = entity
        dataTracker.set(hooked_id, entity.id + 1)
        dataTracker.set(retract, true)
        this.setPosition(entity.x, entity.y + entity.height.div(2), entity.z)
        playSound(LCCSounds.consumer_tongue_attach, 1.0f, random.nextFloat().times(0.2f).plus(0.9f))
    }

    private fun unhook() {
        hookedEntity = null
        dataTracker.set(hooked_id, 0)
    }

    companion object {
        val hooked_id = DataTracker.registerData(ConsumerTongueEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        val retract = DataTracker.registerData(ConsumerTongueEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)

        val tongueSpeed = 1.3f
    }

}