package com.joshmanisdabomb.lcc.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.joshmanisdabomb.lcc.block.HeartCondenserBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.lib.recipe.LCCRecipe
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class HeartCondenserRecipe(protected val _id: Identifier, protected val _group: String, protected val values: Map<Ingredient, Int>, protected val _output: ItemStack, val type: HeartCondenserBlock.HeartState) : Recipe<Inventory>, LCCRecipe {

    override fun matches(inventory: Inventory, world: World): Boolean {
        val stack = inventory.getStack(0)
        return values.keys.any { it.test(stack) }
    }

    override fun fits(width: Int, height: Int) = true

    override fun craft(inventory: Inventory) = output

    override fun getIngredients() = DefaultedList.copyOf(Ingredient.EMPTY, *values.keys.toTypedArray())

    fun getHealth(heart: ItemStack) = values.firstNotNullOfOrNull { (k, v) -> v.takeIf { k.test(heart) } }

    override fun getOutput() = _output

    override fun getAllOutputs() = listOf(output)

    override fun getId() = _id

    override fun getType() = LCCRecipeTypes.heart_condenser

    override fun createIcon() = LCCBlocks.heart_condenser.stack()

    override fun getSerializer() = LCCRecipeSerializers.heart_condenser

    class Serializer : RecipeSerializer<HeartCondenserRecipe> {

        override fun read(id: Identifier, json: JsonObject): HeartCondenserRecipe {
            val group = JsonHelper.getString(json, "group", "")!!
            val values = JsonHelper.getArray(json, "ingredients").associate {
                val element = it.asJsonObject
                val value = JsonHelper.getInt(element, "value")
                val input = Ingredient.fromJson(element)
                return@associate input to value
            }
            val output = ItemStack(Registry.ITEM[Identifier(JsonHelper.getString(json, "result"))], JsonHelper.getInt(json, "count"))
            val state = HeartCondenserBlock.HeartState.find(JsonHelper.getString(json, "state")) ?: throw JsonSyntaxException("State is not a valid heart condenser block state type.")
            return HeartCondenserRecipe(id, group, values, output, state)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): HeartCondenserRecipe {
            val group = buf.readString()
            val ingredients = buf.readList { Ingredient.fromPacket(buf) }
            val ints = buf.readIntArray().toList()
            val values = ingredients.zip(ints).toMap()
            val output = buf.readItemStack()
            val state = HeartCondenserBlock.HeartState.find(buf.readString())!!
            return HeartCondenserRecipe(id, group, values, output, state)
        }

        override fun write(buf: PacketByteBuf, recipe: HeartCondenserRecipe) {
            buf.writeString(recipe.group)
            buf.writeCollection(recipe.values.keys) { b, i ->
                i.write(buf)
            }
            buf.writeIntArray(recipe.values.values.toIntArray())
            buf.writeItemStack(recipe.output)
            buf.writeString(recipe.type.asString())
        }

    }

}
