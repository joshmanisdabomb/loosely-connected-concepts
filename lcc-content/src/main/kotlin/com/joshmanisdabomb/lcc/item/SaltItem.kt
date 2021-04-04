package com.joshmanisdabomb.lcc.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.SnowballEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class SaltItem(val projectiles: Int, settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        //TODO custom sound?
        world.playSound(null, user.x, user.y, user.z, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f))
        if (!world.isClient) {
            repeat(projectiles) {
                val snowballEntity = SnowballEntity(world, user)
                snowballEntity.setItem(stack)
                snowballEntity.setProperties(user, user.pitch, user.yaw, 0.0f, 0.6f, 13.0f)
                world.spawnEntity(snowballEntity)
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this))
        if (!user.abilities.creativeMode) {
            stack.decrement(1)
        }
        return TypedActionResult.success(stack, world.isClient())
    }

}