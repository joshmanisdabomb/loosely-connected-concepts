package com.joshmanisdabomb.lcc.recipe.arcane

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.extensions.getShapelessIngredients
import com.joshmanisdabomb.lcc.extensions.getStack
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeMatcher
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class DungeonTableShapelessRecipe(private val _id: Identifier, private val _group: String, private val ingredients: DefaultedList<Ingredient>, private val _output: ItemStack) : DungeonTableRecipe {

    override fun matches(inv: Inventory, world: World): Boolean {
        val recipeFinder = RecipeMatcher()
        var i = 0

        for (j in 0 until inv.size()) {
            val stack = inv.getStack(j)
            if (!stack.isEmpty) {
                ++i
                recipeFinder.addInput(stack, 1)
            }
        }

        return i == ingredients.size && recipeFinder.match(this, null)
    }

    override fun craft(inv: Inventory) = output.copy()

    override fun fits(width: Int, height: Int) = true

    override fun getId() = _id

    override fun getGroup() = _group

    override fun getIngredients() = ingredients

    override fun getOutput() = _output

    override fun getSerializer() = LCCRecipeSerializers.spawner_table_shapeless

    class Serializer : RecipeSerializer<DungeonTableShapelessRecipe> {

        override fun read(id: Identifier, json: JsonObject): DungeonTableShapelessRecipe {
            val group = JsonHelper.getString(json, "group", "")!!
            val ingredients = getShapelessIngredients(JsonHelper.getArray(json, "ingredients"))
            val output = getStack(JsonHelper.getObject(json, "result"))
            return DungeonTableShapelessRecipe(id, group, ingredients, output)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): DungeonTableShapelessRecipe {
            val group = buf.readString()
            val ingredients = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY)
            for (i in ingredients.indices) ingredients[i] = Ingredient.fromPacket(buf)
            val output = buf.readItemStack()
            return DungeonTableShapelessRecipe(id, group, ingredients, output)
        }

        override fun write(buf: PacketByteBuf, recipe: DungeonTableShapelessRecipe) {
            buf.writeString(recipe._group)
            buf.writeInt(recipe.ingredients.size)
            recipe.ingredients.forEach { it.write(buf) }
            buf.writeItemStack(recipe._output)
        }

    }

}