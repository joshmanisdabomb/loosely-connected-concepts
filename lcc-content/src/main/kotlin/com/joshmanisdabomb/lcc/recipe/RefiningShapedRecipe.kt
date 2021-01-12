package com.joshmanisdabomb.lcc.recipe

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.extensions.getShapedIngredientsWithCount
import com.joshmanisdabomb.lcc.extensions.getShapedKeysWithCount
import com.joshmanisdabomb.lcc.extensions.getShapedPattern
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

class RefiningShapedRecipe(_id: Identifier, _group: String, private val width: Int, private val height: Int, ingredients: DefaultedList<Pair<Ingredient, Int>>, _output: DefaultedList<Pair<ItemStack, OutputFunction?>>, blocks: Array<Block>, lang: String, icon: Int, state: RefiningBlock.RefiningProcess, energy: Float, ticks: Int, gain: Float, maxGain: Float) : RefiningRecipe(_id, _group, ingredients, _output, blocks, lang, icon, state, energy, ticks, gain, maxGain) {

    override fun matches(inv: RefiningInventory, world: World): Boolean {
        for (i in 0..inv.width - width) {
            for (j in 0..inv.height - height) {
                if (match(inv, i, j, true)) return true
                if (match(inv, i, j, false)) return true
            }
        }
        return false
    }

    private fun match(inv: RefiningInventory, x: Int, y: Int, flip: Boolean): Boolean {
        val indexes = mutableSetOf<Int>()
        for (i in 0 until width) {
            for (j in 0 until height) {
                val index = (x + (if (flip) width.minus(1).minus(i) else i)) + (y + j).times(width)
                indexes.add(index)
                val stack = inv.getStack(index)
                val ingredient = ingredients[i + (j * width)]
                if (!ingredient.first.test(stack) && stack.count >= ingredient.second) return false
            }
        }
        for (i in 0 until inv.size()) {
            if (!indexes.contains(i) && !inv.getStack(i).isEmpty) return false
        }
        return true
    }

    override fun fits(width: Int, height: Int) = width >= this.width && height >= this.height

    override fun getSerializer() = LCCRecipeSerializers.refining_shaped

    class Serializer : RecipeSerializer<RefiningShapedRecipe> {

        override fun read(id: Identifier, json: JsonObject): RefiningShapedRecipe {
            val group = JsonHelper.getString(json, "group", "")
            val (blocks, lang, icon, state, energy, ticks, gain, maxGain) = Metadata().read(json)
            val keys = getShapedKeysWithCount(JsonHelper.getObject(json, "key"))
            val pattern = getShapedPattern(JsonHelper.getArray(json, "pattern"), 3, 3)
            val w = pattern[0].length
            val h = pattern.size
            val ingredients = getShapedIngredientsWithCount(keys, pattern, w, h)
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
            return RefiningShapedRecipe(id, group, w, h, ingredients, defaultedList, blocks, lang, icon, state, energy, ticks, gain, maxGain)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): RefiningShapedRecipe {
            val w = buf.readInt()
            val h = buf.readInt()
            val group = buf.readString()
            val (blocks, lang, icon, state, energy, ticks, gain, maxGain) = Metadata().read(buf)
            val ingredients = DefaultedList.ofSize(w * h, Ingredient.EMPTY to 0)
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
            return RefiningShapedRecipe(id, group, w, h, ingredients, outputs, blocks, lang, icon, state, energy, ticks, gain, maxGain)
        }

        override fun write(buf: PacketByteBuf, recipe: RefiningShapedRecipe) {
            buf.writeInt(recipe.width)
            buf.writeInt(recipe.height)
            buf.writeString(recipe._group)
            Metadata(recipe).write(buf)
            recipe.ingredients.forEach { it.first.write(buf); buf.writeInt(it.second) }
            buf.writeInt(recipe._output.size)
            recipe._output.forEach { buf.writeItemStack(it.first); buf.writeIdentifier(it.second?.identifier ?: Identifier("minecraft", "empty")); it.second?.write(buf) }
        }

    }

}