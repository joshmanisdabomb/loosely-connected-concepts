package com.joshmanisdabomb.lcc.recipe.refining

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import com.joshmanisdabomb.lcc.knowledge.KnowledgeRecipeTranslationHandler
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeMatcher
import java.util.*

abstract class RefiningRecipe : Recipe<RefiningInventory>, KnowledgeRecipeTranslationHandler {

    abstract val blocks: Array<Block>
    abstract val lang: String
    abstract val icon: Int
    abstract val state: RefiningBlock.RefiningProcess

    override fun getType() = LCCRecipeTypes.refining

    override fun createIcon() = blocks.first().asItem().defaultStack

    abstract fun getEnergyPerTick(): Float

    abstract fun getSpeed(): Int

    abstract fun getSpeedGainPerTick(): Float

    abstract fun getMaxSpeedGainPerTick(): Float

    abstract fun input(inv: RefiningInventory): List<ItemStack>?

    abstract fun generate(consumed: List<ItemStack>, inventory: RefiningInventory, random: Random): List<ItemStack>

    abstract fun generateMaximum(inventory: RefiningInventory): List<ItemStack>

    abstract fun getOutputs(): List<ItemStack>

    override fun getExtraTranslations(): Map<String, String> {
        return mapOf("action" to lang, *getOutputs().map { it.item.identifier.toString() to it.item.translationKey }.toTypedArray())
    }

    fun getInputStackMap(inv: RefiningInventory, sizeCheck: (Int) -> Boolean): List<ItemStack>? {
        val recipeFinder = RecipeMatcher()
        var i = 0

        val stackMap = mutableListOf<ItemStack>()
        for (j in 0 until inv.width*inv.height) {
            val stack = inv.getStack(j)
            if (!stack.isEmpty) {
                val stack2 = stackMap.firstOrNull { ItemStack.canCombine(it, stack) }
                if (stack2 == null) stackMap.add(stack.copy())
                else stack2.increment(stack.count)
            }
        }

        stackMap.forEach {
            if (!it.isEmpty) {
                ++i
                recipeFinder.addInput(it.copy(), it.count)
            }
        }

        if (sizeCheck(i) && recipeFinder.match(this, null)) {
            return stackMap
        }
        return null
    }

}