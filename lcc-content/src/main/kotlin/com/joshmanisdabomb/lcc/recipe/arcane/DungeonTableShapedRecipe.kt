package com.joshmanisdabomb.lcc.recipe.arcane

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.extensions.getShapedIngredients
import com.joshmanisdabomb.lcc.extensions.getShapedKeys
import com.joshmanisdabomb.lcc.extensions.getShapedPattern
import com.joshmanisdabomb.lcc.extensions.getStack
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class DungeonTableShapedRecipe(private val _id: Identifier, private val _group: String, private val width: Int, private val height: Int, private val ingredients: DefaultedList<Ingredient>, private val _output: ItemStack) : DungeonTableRecipe {

    override fun matches(inv: Inventory, world: World): Boolean {
        for (i in 0..10 - width) {
            for (j in 0..6 - height) {
                if (match(inv, i, j, true)) return true
                if (match(inv, i, j, false)) return true
            }
        }
        return false
    }

    private fun match(inv: Inventory, x: Int, y: Int, flip: Boolean): Boolean {
        val indexes = mutableSetOf<Int>()
        for (i in 0 until width) {
            for (j in 0 until height) {
                val index = slotIndexes[y + j][x + if (flip) width.minus(1).minus(i) else i]
                indexes.add(index)
                val stack = if (index >= 0) inv.getStack(index) else ItemStack.EMPTY
                val ingredient = ingredients[i + (j * width)]
                if (!ingredient.test(stack)) return false
            }
        }
        for (i in 0 until inv.size()) {
            if (!indexes.contains(i) && !inv.getStack(i).isEmpty) return false
        }
        return true
    }

    override fun craft(inv: Inventory) = output.copy()

    override fun fits(width: Int, height: Int) = true

    override fun getId() = _id

    override fun getGroup() = _group

    override fun getIngredients() = ingredients

    override fun getOutput() = _output

    override fun getSerializer() = LCCRecipeSerializers.spawner_table_shaped

    class Serializer : RecipeSerializer<DungeonTableShapedRecipe> {

        override fun read(id: Identifier, json: JsonObject): DungeonTableShapedRecipe {
            val group = JsonHelper.getString(json, "group", "")
            val keys = getShapedKeys(JsonHelper.getObject(json, "key"))
            val pattern = getShapedPattern(JsonHelper.getArray(json, "pattern"), 10, 6)
            val w = pattern[0].length
            val h = pattern.size
            val ingredients = getShapedIngredients(keys, pattern, w, h)
            val output = getStack(JsonHelper.getObject(json, "result"))
            return DungeonTableShapedRecipe(id, group, w, h, ingredients, output)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): DungeonTableShapedRecipe {
            val w = buf.readInt()
            val h = buf.readInt()
            val group = buf.readString()
            val ingredients = DefaultedList.ofSize(w * h, Ingredient.EMPTY)
            for (i in ingredients.indices) ingredients[i] = Ingredient.fromPacket(buf)
            val output = buf.readItemStack()
            return DungeonTableShapedRecipe(id, group, w, h, ingredients, output)
        }

        override fun write(buf: PacketByteBuf, recipe: DungeonTableShapedRecipe) {
            buf.writeInt(recipe.width)
            buf.writeInt(recipe.height)
            buf.writeString(recipe._group)
            recipe.ingredients.forEach { it.write(buf) }
            buf.writeItemStack(recipe._output)
        }

    }

    companion object {
        private val slotIndexes = arrayOf(
            (0 until 10).toList().map { it - 2 }.toIntArray().apply { fill(-1, 0, 2); fill(-1, 8, 10) },
            (0 until 10).toList().map { it + 5 }.toIntArray().apply { fill(-1, 0, 1); fill(-1, 9, 10) },
            (0 until 10).toList().map { it + 14 }.toIntArray(),
            (0 until 10).toList().map { it + 24 }.toIntArray(),
            (0 until 10).toList().map { it + 33 }.toIntArray().apply { fill(-1, 0, 1); fill(-1, 9, 10) },
            (0 until 10).toList().map { it + 40 }.toIntArray().apply { fill(-1, 0, 2); fill(-1, 8, 10) }
        )
    }

}