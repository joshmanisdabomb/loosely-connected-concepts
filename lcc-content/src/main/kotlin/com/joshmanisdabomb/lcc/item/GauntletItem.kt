package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction2
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedItem
import com.joshmanisdabomb.lcc.gui.screen.GauntletScreen
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class GauntletItem(settings: Settings) : Item(settings), LCCExtendedItem {

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity) = false

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float = Float.MAX_VALUE

    override fun getMaxUseTime(stack: ItemStack) = GauntletAction2.getFromTag(stack.orCreateTag).maxChargeTime

    override fun getUseAction(stack: ItemStack) = GauntletAction2.getFromTag(stack.orCreateTag).chargeAction

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand) ?: return TypedActionResult.fail(ItemStack.EMPTY)
        val ability = GauntletAction2.getFromTag(stack.orCreateTag)
        if (ability.canCharge) {
            if (!ability.hasInfo(user)) {
                user.setCurrentHand(hand)
            }
        } else {
            if (ability.act(user)) {
                user.incrementStat(Stats.USED.getOrCreateStat(this))
            }
        }
        return TypedActionResult.fail(stack)
    }

    override fun usageTick(world: World, user: LivingEntity, stack: ItemStack, remainingUseTicks: Int) {
        if (user.isSneaking) {
            user.clearActiveItem()
            GauntletAction2.getFromTag(stack.orCreateTag).cancel(user as? PlayerEntity ?: return, remainingUseTicks)
        } else {
            GauntletAction2.getFromTag(stack.orCreateTag).chargeTick(user as? PlayerEntity ?: return, remainingUseTicks)
        }
    }

    override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int) {
        val ability = GauntletAction2.getFromTag(stack.orCreateTag)
        if (user is PlayerEntity && ability.canCharge) {
            if (ability.act(user, remainingUseTicks)) {
                user.incrementStat(Stats.USED.getOrCreateStat(this))
            }
        }
    }

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        onStoppedUsing(stack, world, user, 0)
        return stack
    }

    override fun lcc_onEntitySwing(stack: ItemStack, entity: LivingEntity): Boolean {
        if (entity.world.isClient) {
            MinecraftClient.getInstance().currentScreen = GauntletScreen().open()
        }
        return true
    }

}