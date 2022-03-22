package com.joshmanisdabomb.lcc.item

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.mob.Angerable
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Box
import net.minecraft.village.VillagerProfession
import net.minecraft.world.RaycastContext
import net.minecraft.world.World

class ForgetScrollItem(settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        user.setCurrentHand(hand)
        return TypedActionResult.consume(stack)
    }

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        val look = user.getRotationVec(1.0f)
        for (entity in world.getEntitiesByClass(LivingEntity::class.java, Box.from(user.eyePos).expand(8.0), Entity::isAlive)) {
            if (entity == user) continue
            val vec = entity.eyePos.relativize(user.eyePos).normalize().multiply(1.0, 1.0, 1.0)
            val result = world.raycast(RaycastContext(user.eyePos, entity.eyePos, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, entity))
            if (vec.dotProduct(look) < -0.4 && result.type == HitResult.Type.MISS) {
                forget(world, user, stack, entity)
            }
        }
        return stack
    }

    override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int) {

    }

    override fun getMaxUseTime(stack: ItemStack) = 35

    override fun getUseAction(stack: ItemStack) = UseAction.SPEAR

    fun forget(world: World, user: LivingEntity, stack: ItemStack, entity: LivingEntity) {
        entity.brain.clear()
        (entity as? Angerable)?.stopAnger()
        (entity as? MobEntity)?.target = null
        val nbt = NbtCompound()
        entity.writeNbt(nbt)
        val new = entity.type.create(world)
        if (new != null) {
            new.copyFrom(entity)
            when (new) {
                is VillagerEntity -> {
                    new.brain.clear()
                    if (!world.isClient) new.reinitializeBrain(world as ServerWorld)
                    new.releaseTicketFor(MemoryModuleType.HOME)
                    new.releaseTicketFor(MemoryModuleType.JOB_SITE)
                    new.releaseTicketFor(MemoryModuleType.POTENTIAL_JOB_SITE)
                    new.releaseTicketFor(MemoryModuleType.MEETING_POINT)
                    if (!world.isClient) new.brain.stopAllTasks(world as ServerWorld, new)
                    new.brain.forget(MemoryModuleType.HOME)
                    new.brain.forget(MemoryModuleType.JOB_SITE)
                    new.brain.forget(MemoryModuleType.POTENTIAL_JOB_SITE)
                    new.brain.forget(MemoryModuleType.MEETING_POINT)
                    new.brain.forget(MemoryModuleType.LAST_WORKED_AT_POI)
                    new.brain.forget(MemoryModuleType.SECONDARY_JOB_SITE)
                    new.villagerData = new.villagerData.withProfession(VillagerProfession.NONE).withLevel(0)
                    new.experience = 0
                }
            }
            new.copyPositionAndRotation(entity)
            new.prevX = entity.prevX
            new.prevY = entity.prevY
            new.prevZ = entity.prevZ
            new.prevHorizontalSpeed = entity.prevHorizontalSpeed
            new.prevYaw = entity.prevYaw
            new.prevPitch = entity.prevPitch
            new.headYaw = entity.headYaw
            entity.discard()
            world.spawnEntity(new)
        }
    }

}
