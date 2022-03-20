package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.entity.render.FlyEntityRenderer
import com.joshmanisdabomb.lcc.extensions.getStringUuidOrNull
import com.joshmanisdabomb.lcc.extensions.putStringUuid
import com.joshmanisdabomb.lcc.extensions.putStringUuidOrRemove
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.EntityType
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class FlySpawnEggItem(type: EntityType<out MobEntity>, settings: Settings) : VariableTintSpawnEggItem(type, settings, 0x222222, 0xBDBBA0, 0xCCE0B4) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        return hijackOwner(user, stack) { super.use(world, user, hand) }
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val player = context.player ?: return super.useOnBlock(context)
        return hijackOwner(player, context.stack) { super.useOnBlock(context) }
    }

    @Environment(EnvType.CLIENT)
    override fun getColor(num: Int): Int {
        val player = MinecraftClient.getInstance().player
        if (player?.isSneaking == true && num == 2) return FlyEntityRenderer.getFlyColor(player) ?: 0x00BFFF
        return super.getColor(num)
    }

    fun <T> hijackOwner(player: PlayerEntity, stack: ItemStack, run: () -> T): T {
        if (!player.isSneaking) return run()

        val previous = stack.getSubNbt("EntityTag")
        val previousOwner = previous?.getStringUuidOrNull("Owner")
        stack.getOrCreateSubNbt("EntityTag").putStringUuid("Owner", player.uuid)

        val ret = run()

        if (previous != null) {
            previous.putStringUuidOrRemove("Owner", previousOwner)
            stack.setSubNbt("EntityTag", previous)
        } else {
            stack.removeSubNbt("EntityTag")
        }

        return ret
    }

}
