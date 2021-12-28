package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.extensions.build
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.WorldProperties
import java.util.*

class ComputingStorageComponent(private val properties: WorldProperties) : ComponentV3 {

    private val stackDictionary = mutableMapOf<ItemStack, Long>()
    private val stackDictionaryFlip = mutableMapOf<Long, ItemStack>()
    private var stackDictionaryLast = 0L

    private val partitions = mutableMapOf<UUID, Unit>()

    fun getDictionaryStackId(stack: ItemStack): Long {
        val entry = stack.copy().apply { count = 1 }
        if (!stackDictionary.containsKey(entry)) {
            stackDictionary[stack] = ++stackDictionaryLast
            stackDictionaryFlip[stackDictionaryLast] = stack
        }
        return stackDictionary.get(entry) ?: error("Failed to determine dictionary ID for stack $stack.")
    }

    fun getDictionaryStack(id: Long): ItemStack? {
        return stackDictionaryFlip[id]
    }

    override fun readFromNbt(tag: NbtCompound) {
        stackDictionary.clear()
        stackDictionaryFlip.clear()
        stackDictionaryLast = 0L
        tag.getCompound("ItemDictionary").apply {
            keys.forEach {
                val id = it.toLong(36)
                val stack = ItemStack.fromNbt(getCompound(it).also { it.putByte("Count", 1) })
                stackDictionary[stack] = id
                stackDictionaryFlip[id] = stack
                if (id > stackDictionaryLast) stackDictionaryLast = id
            }
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.build("ItemDictionary", NbtCompound()) {
            stackDictionary.forEach { (k, v) -> put(v.toString(36), k.writeNbt(NbtCompound()).also { it.remove("Count") }) }
        }
    }

}