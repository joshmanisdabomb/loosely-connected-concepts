package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.entity.SaltEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class SaltItem(val projectiles: Int, settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        world.playSound(null, user.x, user.y, user.z, LCCSounds.salt_throw, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f))
        user.itemCooldownManager.set(this, 30)
        if (!world.isClient) {
            repeat(projectiles) {
                SaltEntity(world, user).apply {
                    setItem(stack)
                    setVelocity(user, user.pitch, user.yaw, 0.0f, 0.45f, 22.0f)
                    world.spawnEntity(this)
                }
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this))
        if (!user.abilities.creativeMode) {
            stack.decrement(1)
        }
        return TypedActionResult.success(stack, world.isClient())
    }

}