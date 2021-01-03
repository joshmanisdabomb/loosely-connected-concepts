package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.utils.ItemStackUtils
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.BundleTooltipData
import net.minecraft.client.item.ModelPredicateProvider
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.BundleItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ClickType
import net.minecraft.util.Formatting
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import java.util.*
import java.util.stream.Stream
import kotlin.math.min

class BagItem(val size: Int, settings: Settings, val predicate: (stack: ItemStack) -> Boolean = { true }) : BundleItem(settings) {

    override fun onStackClicked(stack: ItemStack, slot: Slot, clickType: ClickType, playerInventory: PlayerInventory): Boolean {
        if (clickType != ClickType.RIGHT) return false
        val its = slot.stack
        if (its.isEmpty) {
            addStack(stack).ifPresent { addToBundle(stack, slot.method_32756(it)) }
        } else if (its.item.hasStoredInventory()) {
            val i = (size - getBundleOccupancy(stack)) / getItemOccupancy(its)
            addToBundle(stack, slot.method_32753(its.count, i, playerInventory.player))
        }
        return true
    }

    override fun onClicked(stack: ItemStack, otherStack: ItemStack, slot: Slot, clickType: ClickType, playerInventory: PlayerInventory): Boolean {
        return if (clickType == ClickType.RIGHT && slot.method_32754(playerInventory.player)) {
            if (otherStack.isEmpty) {
                addStack(stack).ifPresent { playerInventory.cursorStack = it }
            } else {
                otherStack.decrement(addToBundle(stack, otherStack))
            }
            true
        } else {
            false
        }
    }

    fun getFirstStack(stack: ItemStack, listTag: ListTag): Optional<CompoundTag> {
        return if (stack.item is BagItem) {
            Optional.empty()
        } else {
            listTag.stream().filter { CompoundTag::class.java.isInstance(it) }.map { CompoundTag::class.java.cast(it) }.filter { ItemStack.canCombine(ItemStackUtils.fromTagIntCount(it), stack) }.findFirst()
        }
    }

    @Environment(EnvType.CLIENT)
    override fun isItemBarVisible(stack: ItemStack) = getBundleOccupancy(stack) > 0

    @Environment(EnvType.CLIENT)
    override fun getItemBarStep(stack: ItemStack) = min(1 + 12 * getBundleOccupancy(stack) / size, 13)

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(TranslatableText("item.minecraft.bundle.fullness", getBundleOccupancy(stack), size).formatted(Formatting.GRAY))
    }

    @Environment(EnvType.CLIENT)
    fun predicate() = ModelPredicateProvider { stack, _, _, _ -> getFillPercent(stack) }

    @Environment(EnvType.CLIENT)
    fun getFillPercent(stack: ItemStack) = getBundleOccupancy(stack).toFloat().div(size)

    fun getBundleOccupancy(stack: ItemStack) = getBundledStacks(stack).mapToInt { getItemOccupancy(it) * it.count }.sum()

    fun getBundledStacks(stack: ItemStack): Stream<ItemStack> {
        val compoundTag = stack.tag ?: return Stream.empty()
        val listTag = compoundTag.getList("Items", 10)
        return listTag.stream().map { obj -> CompoundTag::class.java.cast(obj) }.map { tag -> ItemStackUtils.fromTagIntCount(tag) }
    }

    fun getItemOccupancy(stack: ItemStack): Int {
        if (stack.item is BagItem) return (4 + getBundleOccupancy(stack))
        return 64 / stack.maxCount
    }

    private fun addStack(stack: ItemStack): Optional<ItemStack> {
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

    private fun addToBundle(bundle: ItemStack, stack: ItemStack): Int {
        return if (!stack.isEmpty && stack.item.hasStoredInventory()) {
            val compoundTag = bundle.orCreateTag
            if (!compoundTag.contains("Items")) {
                compoundTag.put("Items", ListTag())
            }
            val i = getBundleOccupancy(bundle)
            val j = getItemOccupancy(stack)
            val k = min(stack.count, (size - i) / j)
            if (k == 0) {
                0
            } else {
                val listTag = compoundTag.getList("Items", 10)
                val optional = getFirstStack(stack, listTag)
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

    @Environment(EnvType.CLIENT)
    override fun getTooltipData(stack: ItemStack): Optional<TooltipData> {
        val defaultedList = DefaultedList.of<ItemStack>()
        getBundledStacks(stack).forEach { defaultedList.add(it) }
        return Optional.of(BundleTooltipData(defaultedList, getBundleOccupancy(stack).minus(size).plus(64)))
    }

}
