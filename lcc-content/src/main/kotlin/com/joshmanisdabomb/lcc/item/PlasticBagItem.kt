package com.joshmanisdabomb.lcc.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.StackReference
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text
import net.minecraft.util.ClickType
import net.minecraft.util.Hand
import net.minecraft.world.World

class PlasticBagItem(override val size: Int, settings: Settings) : PlasticItem(settings), BagItem {

    override fun canBagStore(stack: ItemStack) = true

    override fun onStackClicked(stack: ItemStack, slot: Slot, clickType: ClickType, player: PlayerEntity) = onStackClickedWithBag(stack, slot, clickType, player)

    override fun onClicked(stack: ItemStack, otherStack: ItemStack, slot: Slot, clickType: ClickType, player: PlayerEntity, cursorStackReference: StackReference) = onBagClicked(stack, otherStack, slot, clickType, player, cursorStackReference)

    override fun use(world: World, user: PlayerEntity, hand: Hand) = useBag(world, user, hand)

    override fun isItemBarVisible(stack: ItemStack) = isBagBarVisible(stack)

    override fun getItemBarStep(stack: ItemStack) = getBagBarStep(stack)

    override fun getItemBarColor(stack: ItemStack) = BagItem.barColor

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) = appendBagTooltip(stack, world, tooltip, context)

    override fun getTooltipData(stack: ItemStack) = getBagTooltipData(stack)

}