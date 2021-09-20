package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.utils.ItemStackUtils
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.BundleTooltipData
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.TooltipData
import net.minecraft.client.item.UnclampedModelPredicateProvider
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.StackReference
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
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
            retrieveBagStack(stack).ifPresent { transferBagStack(stack, slot.insertStack(it)) }
        } else if (its.item.canBeNested()) {
            if (!canBagStore(its)) return false
            val i = (size - getBagTotalOccupancy(stack)) / getBagItemOccupancy(its)
            transferBagStack(stack, slot.takeStackRange(its.count, i, player))
        }
        return true
    }

    @JvmDefault
    fun onBagClicked(stack: ItemStack, otherStack: ItemStack, slot: Slot, clickType: ClickType, player: PlayerEntity, cursorStackReference: StackReference): Boolean {
        return if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty) {
                retrieveBagStack(stack).ifPresent(cursorStackReference::set)
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
    fun getBagPredicate() = UnclampedModelPredicateProvider { stack, _, _, _ -> getBagFillPercent(stack) }

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
        val nbtCompound = stack.nbt ?: return Stream.empty()
        val nbtList = nbtCompound.getList("Items", 10)
        return nbtList.stream().map { obj -> NbtCompound::class.java.cast(obj) }.map { tag -> ItemStackUtils.fromTagIntCount(tag) }
    }

    @JvmDefault
    fun getBagItemOccupancy(stack: ItemStack): Int {
        if (stack.item is BagItem) return (4 + getBagTotalOccupancy(stack))
        return 64 / stack.maxCount
    }

    @JvmDefault
    fun dropFromBag(stack: ItemStack, player: PlayerEntity): Boolean {
        val nbtCompound = stack.orCreateNbt
        if (!nbtCompound.contains("Items")) return false

        if (player is ServerPlayerEntity) {
            val nbtList = nbtCompound.getList("Items", 10)
            for (i in nbtList.indices) {
                val nbtCompound2 = nbtList.getCompound(i)
                val itemStack = ItemStackUtils.fromTagIntCount(nbtCompound2)
                while (!itemStack.isEmpty) {
                    player.dropItem(itemStack.split(64), true)
                }
            }
        }
        stack.removeSubNbt("Items")
        return true
    }

    @JvmDefault
    fun bagCombineCheck(stack: ItemStack, nbtList: NbtList): Optional<NbtCompound> {
        return if (stack.item is BagItem) {
            Optional.empty()
        } else {
            nbtList.stream().filter { NbtCompound::class.java.isInstance(it) }.map { NbtCompound::class.java.cast(it) }.filter { ItemStack.canCombine(ItemStackUtils.fromTagIntCount(it), stack) }.findFirst()
        }
    }

    @JvmDefault
    fun retrieveBagStack(stack: ItemStack): Optional<ItemStack> {
        val nbtCompound = stack.orCreateNbt
        return if (!nbtCompound.contains("Items")) {
            Optional.empty()
        } else {
            val nbtList = nbtCompound.getList("Items", 10)
            if (nbtList.isEmpty()) {
                Optional.empty()
            } else {
                val its = ItemStackUtils.fromTagIntCount(nbtList.getCompound(0))
                nbtList.removeAt(0)
                Optional.of(its)
            }
        }
    }

    @JvmDefault
    fun transferBagStack(bundle: ItemStack, stack: ItemStack): Int {
        return if (!stack.isEmpty && stack.item.canBeNested()) {
            val nbtCompound = bundle.orCreateNbt
            if (!nbtCompound.contains("Items")) {
                nbtCompound.put("Items", NbtList())
            }
            val i = getBagTotalOccupancy(bundle)
            val j = getBagItemOccupancy(stack)
            val k = min(stack.count, (size - i) / j)
            if (k == 0) {
                0
            } else {
                val nbtList = nbtCompound.getList("Items", 10)
                val optional = bagCombineCheck(stack, nbtList)
                if (optional.isPresent) {
                    val nbtCompound2 = optional.get()
                    val itemStack = ItemStackUtils.fromTagIntCount(nbtCompound2)
                    itemStack.increment(k)
                    ItemStackUtils.toTagIntCount(itemStack, nbtCompound2)
                    nbtList.remove(nbtCompound2)
                    nbtList.add(0, nbtCompound2)
                } else {
                    val itemStack2 = stack.copy()
                    itemStack2.count = k
                    val nbtCompound3 = NbtCompound()
                    ItemStackUtils.toTagIntCount(itemStack2, nbtCompound3)
                    nbtList.add(0, nbtCompound3)
                }
                k
            }
        } else {
            0
        }
    }

}
