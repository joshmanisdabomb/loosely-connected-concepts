package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.utils.ItemStackUtils
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.BundleTooltipData
import net.minecraft.client.item.ModelPredicateProvider
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.CommandItemSlot
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ClickType
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import java.util.*
import java.util.stream.Stream
import kotlin.math.min

interface BagItem {

    val size: Int

    companion object {
        val barColor = MathHelper.packRgb(0.4f, 0.4f, 1.0f)
    }

    fun canBagStore(stack: ItemStack): Boolean

    @JvmDefault
    fun onStackClickedWithBag(stack: ItemStack, slot: Slot, clickType: ClickType, player: PlayerEntity): Boolean {
        if (clickType != ClickType.RIGHT) return false
        val its = slot.stack
        if (its.isEmpty) {
            retrieveBagStack(stack).ifPresent { transferBagStack(stack, slot.method_32756(it)) }
        } else if (its.item.hasStoredInventory()) {
            if (!canBagStore(its)) return false
            val i = (size - getBagTotalOccupancy(stack)) / getBagItemOccupancy(its)
            transferBagStack(stack, slot.method_32753(its.count, i, player))
        }
        return true
    }

    @JvmDefault
    fun onBagClicked(stack: ItemStack, otherStack: ItemStack, slot: Slot, clickType: ClickType, player: PlayerEntity, commandItemSlot: CommandItemSlot): Boolean {
        return if (clickType == ClickType.RIGHT && slot.method_32754(player)) {
            if (otherStack.isEmpty) {
                retrieveBagStack(stack).ifPresent(commandItemSlot::set)
            } else {
                if (!canBagStore(otherStack)) return false
                otherStack.decrement(transferBagStack(stack, otherStack))
            }
            true
        } else {
            false
        }
    }

    @JvmDefault
    fun useBag(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        return if (dropFromBag(itemStack, user)) TypedActionResult.success(itemStack, world.isClient()) else TypedActionResult.fail(itemStack)
    }

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun isBagBarVisible(stack: ItemStack) = getBagTotalOccupancy(stack) > 0

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun getBagBarStep(stack: ItemStack) = min(1 + 12 * getBagTotalOccupancy(stack) / size, 13)

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun appendBagTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(TranslatableText("item.minecraft.bundle.fullness", getBagTotalOccupancy(stack), size).formatted(Formatting.GRAY))
    }

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun getBagPredicate() = ModelPredicateProvider { stack, _, _, _ -> getBagFillPercent(stack) }

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun getBagFillPercent(stack: ItemStack) = getBagTotalOccupancy(stack).toFloat().div(size)

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun getBagTooltipData(stack: ItemStack): Optional<TooltipData> {
        val defaultedList = DefaultedList.of<ItemStack>()
        getBaggedStacks(stack).forEach { defaultedList.add(it) }
        return Optional.of(BundleTooltipData(defaultedList, getBagTotalOccupancy(stack).minus(size).plus(64)))
    }

    @JvmDefault
    fun getBagTotalOccupancy(stack: ItemStack) = getBaggedStacks(stack).mapToInt { getBagItemOccupancy(it) * it.count }.sum()

    @JvmDefault
    fun getBaggedStacks(stack: ItemStack): Stream<ItemStack> {
        val compoundTag = stack.tag ?: return Stream.empty()
        val listTag = compoundTag.getList("Items", 10)
        return listTag.stream().map { obj -> CompoundTag::class.java.cast(obj) }.map { tag -> ItemStackUtils.fromTagIntCount(tag) }
    }

    @JvmDefault
    fun getBagItemOccupancy(stack: ItemStack): Int {
        if (stack.item is BagItem) return (4 + getBagTotalOccupancy(stack))
        return 64 / stack.maxCount
    }

    @JvmDefault
    fun dropFromBag(stack: ItemStack, player: PlayerEntity): Boolean {
        val compoundTag = stack.orCreateTag
        if (!compoundTag.contains("Items")) return false

        if (player is ServerPlayerEntity) {
            val listTag = compoundTag.getList("Items", 10)
            for (i in listTag.indices) {
                val compoundTag2 = listTag.getCompound(i)
                val itemStack = ItemStackUtils.fromTagIntCount(compoundTag2)
                while (!itemStack.isEmpty) {
                    player.dropItem(itemStack.split(64), true)
                }
            }
        }
        stack.removeSubTag("Items")
        return true
    }

    @JvmDefault
    fun bagCombineCheck(stack: ItemStack, listTag: ListTag): Optional<CompoundTag> {
        return if (stack.item is BagItem) {
            Optional.empty()
        } else {
            listTag.stream().filter { CompoundTag::class.java.isInstance(it) }.map { CompoundTag::class.java.cast(it) }.filter { ItemStack.canCombine(ItemStackUtils.fromTagIntCount(it), stack) }.findFirst()
        }
    }

    @JvmDefault
    fun retrieveBagStack(stack: ItemStack): Optional<ItemStack> {
        val compoundTag = stack.orCreateTag
        return if (!compoundTag.contains("Items")) {
            Optional.empty()
        } else {
            val listTag = compoundTag.getList("Items", 10)
            if (listTag.isEmpty()) {
                Optional.empty()
            } else {
                val its = ItemStackUtils.fromTagIntCount(listTag.getCompound(0))
                listTag.removeAt(0)
                Optional.of(its)
            }
        }
    }

    @JvmDefault
    fun transferBagStack(bundle: ItemStack, stack: ItemStack): Int {
        return if (!stack.isEmpty && stack.item.hasStoredInventory()) {
            val compoundTag = bundle.orCreateTag
            if (!compoundTag.contains("Items")) {
                compoundTag.put("Items", ListTag())
            }
            val i = getBagTotalOccupancy(bundle)
            val j = getBagItemOccupancy(stack)
            val k = min(stack.count, (size - i) / j)
            if (k == 0) {
                0
            } else {
                val listTag = compoundTag.getList("Items", 10)
                val optional = bagCombineCheck(stack, listTag)
                if (optional.isPresent) {
                    val compoundTag2 = optional.get()
                    val itemStack = ItemStackUtils.fromTagIntCount(compoundTag2)
                    itemStack.increment(k)
                    ItemStackUtils.toTagIntCount(itemStack, compoundTag2)
                    listTag.remove(compoundTag2)
                    listTag.add(0, compoundTag2)
                } else {
                    val itemStack2 = stack.copy()
                    itemStack2.count = k
                    val compoundTag3 = CompoundTag()
                    ItemStackUtils.toTagIntCount(itemStack2, compoundTag3)
                    listTag.add(0, compoundTag3)
                }
                k
            }
        } else {
            0
        }
    }

}
