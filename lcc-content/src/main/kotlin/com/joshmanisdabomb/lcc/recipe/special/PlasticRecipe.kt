package com.joshmanisdabomb.lcc.recipe.special

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.extensions.getShapedIngredients
import com.joshmanisdabomb.lcc.extensions.getShapedKeys
import com.joshmanisdabomb.lcc.extensions.getShapedPattern
import com.joshmanisdabomb.lcc.extensions.getStack
import com.joshmanisdabomb.lcc.item.ColoredItem
import com.joshmanisdabomb.lcc.item.PlasticItem
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList

class PlasticRecipe(private val id: Identifier, group: String, width: Int, height: Int, input: DefaultedList<Ingredient>, output: ItemStack) : ShapedRecipe(id, group, width, height, input, output) {

    override fun getId() = id

    override fun isIgnoredInRecipeBook() = true

    override fun craft(craftingInventory: CraftingInventory): ItemStack {
        val colors = mutableListOf<Int>()
        for (i in 0..craftingInventory.width) {
            for (j in 0..craftingInventory.height) {
                val stack = craftingInventory.getStack(i + j.times(width))
                if (stack.isIn(LCCTags.plastic)) {
                    colors += (stack.item as? ColoredItem)?.getTintColor(stack) ?: continue
                }
            }
        }
        if (colors.isEmpty()) error("Plastic recipe with no plastic ingredients, a regular shaped recipe should be used instead.")
        val color = PlasticItem.getColorMix(colors.distinct())
        return super.craft(craftingInventory).apply { putSubTag("display", NbtCompound().apply { putInt("color", color) }) }
    }

    override fun getSerializer() = LCCRecipeSerializers.plastic_shaped

    class Serializer : RecipeSerializer<PlasticRecipe> {
        override fun read(id: Identifier, json: JsonObject): PlasticRecipe {
            val group = JsonHelper.getString(json, "group", "")
            val keys = getShapedKeys(JsonHelper.getObject(json, "key"))
            val pattern = getShapedPattern(JsonHelper.getArray(json, "pattern"), 3, 3)
            val w = pattern[0].length
            val h = pattern.size
            val ingredients = getShapedIngredients(keys, pattern, w, h)
            val output = getStack(JsonHelper.getObject(json, "result"))
            return PlasticRecipe(id, group, w, h, ingredients, output)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): PlasticRecipe {
            val w = buf.readInt()
            val h = buf.readInt()
            val group = buf.readString()
            val ingredients = DefaultedList.ofSize(w * h, Ingredient.EMPTY)
            for (i in ingredients.indices) ingredients[i] = Ingredient.fromPacket(buf)
            val output = buf.readItemStack()
            return PlasticRecipe(id, group, w, h, ingredients, output)
        }

        override fun write(buf: PacketByteBuf, recipe: PlasticRecipe) {
            buf.writeInt(recipe.width)
            buf.writeInt(recipe.height)
            buf.writeString(recipe.group)
            recipe.ingredients.forEach { it.write(buf) }
            buf.writeItemStack(recipe.output)
        }
    }

}
