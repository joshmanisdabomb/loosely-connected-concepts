package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.trait.LCCItemTrait
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.inventory.CommandItemSlot
import net.minecraft.item.ArrowItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import net.minecraft.tag.ItemTags
import net.minecraft.text.Text
import net.minecraft.util.ClickType
import net.minecraft.util.Hand
import net.minecraft.world.World

class QuiverItem(override val size: Int, settings: Settings) : Item(settings), LCCItemTrait, BagItem {

    override fun canBagStore(stack: ItemStack) = stack.isIn(ItemTags.ARROWS)

    override fun lcc_getArrow(stack: ItemStack) = if (getBagTotalOccupancy(stack) > 0) stack else null

    override fun lcc_createArrow(world: World, stack: ItemStack, shooter: LivingEntity): PersistentProjectileEntity? {
        val projectile = getBaggedStacks(stack).findFirst().orElse(null) ?: return null
        if (projectile.isEmpty) return null
        val item = projectile.item

        (item as? ArrowItem)?.createArrow(world, projectile, shooter)?.apply { return this }
        (item as? LCCItemTrait)?.lcc_createArrow(world, projectile, shooter)?.apply { return this }

        return null
    }

    override fun lcc_fireStackBow(stack: ItemStack): Boolean {
        retrieveBagStack(stack).ifPresent { it.decrement(1); transferBagStack(stack, it) }
        return true
    }

    override fun lcc_fireStackCrossbow(stack: ItemStack): ItemStack? {
        val arrow = retrieveBagStack(stack).orElse(null) ?: return null
        if (arrow.isEmpty) return null

        val ret = arrow.split(1)
        transferBagStack(stack, arrow)
        return ret
    }

    override fun lcc_pickupItemListen(world: World, stack: ItemStack, slot: Int, acquired: ItemStack, player: PlayerEntity, entity: ItemEntity): Boolean? {
        if (!stack.isEmpty && !acquired.isEmpty && canBagStore(acquired) && acquired.item.canBeNested()) {
            acquired.decrement(transferBagStack(stack, acquired))
            return if (!acquired.isEmpty) null else true
        }
        return null
    }

    override fun lcc_pickupProjectileListen(world: World, stack: ItemStack, slot: Int, acquired: ItemStack, player: PlayerEntity, entity: PersistentProjectileEntity): Boolean? {
        if (!stack.isEmpty && !acquired.isEmpty && canBagStore(acquired) && acquired.item.canBeNested()) {
            acquired.decrement(transferBagStack(stack, acquired))
            return if (!acquired.isEmpty) null else true
        }
        return null
    }

    override fun onStackClicked(stack: ItemStack, slot: Slot, clickType: ClickType, player: PlayerEntity) = onStackClickedWithBag(stack, slot, clickType, player)

    override fun onClicked(stack: ItemStack, otherStack: ItemStack, slot: Slot, clickType: ClickType, player: PlayerEntity, commandItemSlot: CommandItemSlot) = onBagClicked(stack, otherStack, slot, clickType, player, commandItemSlot)

    override fun use(world: World, user: PlayerEntity, hand: Hand) = useBag(world, user, hand)

    override fun isItemBarVisible(stack: ItemStack) = isBagBarVisible(stack)

    override fun getItemBarStep(stack: ItemStack) = getBagBarStep(stack)

    override fun getItemBarColor(stack: ItemStack) = BagItem.barColor

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) = appendBagTooltip(stack, world, tooltip, context)

    override fun getTooltipData(stack: ItemStack) = getBagTooltipData(stack)

}
