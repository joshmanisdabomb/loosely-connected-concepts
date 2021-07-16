package com.joshmanisdabomb.lcc.recipe

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.extensions.getShapelessIngredientsWithCount
import com.joshmanisdabomb.lcc.extensions.getStack
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class RefiningShapelessRecipe(_id: Identifier, _group: String, ingredients: DefaultedList<Pair<Ingredient, Int>>, _output: DefaultedList<Pair<ItemStack, OutputFunction?>>, blocks: Array<Block>, lang: String, icon: Int, state: RefiningBlock.RefiningProcess, energy: Float, ticks: Int, gain: Float, maxGain: Float) : RefiningSimpleRecipe(_id, _group, ingredients, _output, blocks, lang, icon, state, energy, ticks, gain, maxGain) {

    override fun matches(inv: RefiningInventory, world: World): Boolean {
        val stackMap = getInputStackMap(inv) { it == refiningIngredients.size } ?: return false

        refiningIngredients.forEach { ing ->
            (stackMap.firstOrNull { ing.first.test(it) && it.count >= ing.second } ?: return false).decrement(ing.second)
        }

        return true
    }

    override fun input(inv: RefiningInventory): List<ItemStack>? {
        val consumed = mutableListOf<ItemStack>()
        next@for (ing in refiningIngredients) {
            var debt = ing.second
            for (j in 0 until inv.width*inv.height) {
                val stack = inv.getStack(j)
                if (!stack.isEmpty && ing.first.test(stack)) {
                    val difference = stack.count.coerceAtMost(debt)
                    val stackConsumed = stack.split(difference)

                    val stackMatch = consumed.firstOrNull { ItemStack.canCombine(it, stackConsumed) }
                    if (stackMatch == null) consumed.add(stack.copy())
                    else stackMatch.increment(stack.count)

                    debt -= difference
                }
                if (debt <= 0) continue@next
            }
            return null
        }
        return consumed
    }

    override fun fits(width: Int, height: Int) = width * height >= refiningIngredients.size

    override fun getSerializer() = LCCRecipeSerializers.refining_shapeless

    class Serializer : RecipeSerializer<RefiningShapelessRecipe> {

        override fun read(id: Identifier, json: JsonObject): RefiningShapelessRecipe {
            val group = JsonHelper.getString(json, "group", "")
            val (blocks, lang, icon, state, energy, ticks, gain, maxGain) = Metadata().read(json)
            val ingredients = getShapelessIngredientsWithCount(JsonHelper.getArray(json, "ingredients"))
            val results = JsonHelper.getArray(json, "results")
            val defaultedList = DefaultedList.of<Pair<ItemStack, OutputFunction?>>()
            for (i in 0 until results.size()) {
                val output = getStack(results[i].asJsonObject)
                if (output.isEmpty) continue
                val oid = try {
                    JsonHelper.getString(results[i].asJsonObject, "function")
                } catch (ex: Exception) {
                    null
                }
                val function = OutputFunction.get(Identifier.tryParse(oid) ?: Identifier("minecraft", "empty"))?.read(results[i].asJsonObject)
                defaultedList += output to function
            }
            return RefiningShapelessRecipe(id, group, ingredients, defaultedList, blocks, lang, icon, state, energy, ticks, gain, maxGain)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): RefiningShapelessRecipe {
            val group = buf.readString()
            val (blocks, lang, icon, state, energy, ticks, gain, maxGain) = Metadata().read(buf)
            val ingredients = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY to 0)
            for (i in ingredients.indices) {
                ingredients[i] = Ingredient.fromPacket(buf) to buf.readInt()
            }
            val outputs = DefaultedList.ofSize<Pair<ItemStack, OutputFunction?>>(buf.readInt(), ItemStack.EMPTY to null)
            for (i in outputs.indices) {
                val stack = buf.readItemStack()
                val function = OutputFunction.get(buf.readIdentifier())
                function?.read(buf)
                outputs[i] = stack to function
            }
            return RefiningShapelessRecipe(id, group, ingredients, outputs, blocks, lang, icon, state, energy, ticks, gain, maxGain)
        }

        override fun write(buf: PacketByteBuf, recipe: RefiningShapelessRecipe) {
            buf.writeString(recipe._group)
            Metadata(recipe).write(buf)
            buf.writeInt(recipe.refiningIngredients.size)
            recipe.refiningIngredients.forEach { it.first.write(buf); buf.writeInt(it.second) }
            buf.writeInt(recipe._output.size)
            recipe._output.forEach { buf.writeItemStack(it.first); buf.writeIdentifier(it.second?.identifier ?: Identifier("minecraft", "empty")); it.second?.write(buf) }
        }

    }

}