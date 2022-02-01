package com.joshmanisdabomb.lcc.recipe

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.recipe.LCCRecipe
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

class EnhancingRecipe(protected val _id: Identifier, protected val _group: String, protected val input: Ingredient, protected val pyre: Ingredient, protected val _output: ItemStack) : Recipe<LCCInventory>, LCCRecipe {

    override fun getType() = LCCRecipeTypes.enhancing

    override fun createIcon() = LCCBlocks.enhancing_chamber.stack()

    override fun getId() = _id

    override fun getGroup() = _group

    override fun getOutput() = _output

    override fun getAllOutputs() = listOf(output)

    override fun getIngredients() = DefaultedList.copyOf(Ingredient.EMPTY, input, pyre)

    override fun fits(width: Int, height: Int) = true

    override fun matches(inventory: LCCInventory, world: World) = input.test(inventory.getStack(0)) && pyre.test(inventory.getStack(1))

    override fun craft(inventory: LCCInventory) = this.output.copy()

    override fun getSerializer() = LCCRecipeSerializers.enhancing

    class Serializer : RecipeSerializer<EnhancingRecipe> {

        override fun read(id: Identifier, json: JsonObject): EnhancingRecipe {
            val group = JsonHelper.getString(json, "group", "")!!
            val input = if (JsonHelper.hasArray(json, "ingredient")) {
                Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"))
            } else {
                Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"))
            }
            val pyre = if (JsonHelper.hasArray(json, "pyre")) {
                Ingredient.fromJson(JsonHelper.getArray(json, "pyre"))
            } else {
                Ingredient.fromJson(JsonHelper.getObject(json, "pyre"))
            }
            val output = ItemStack(Registry.ITEM[Identifier(JsonHelper.getString(json, "result"))], JsonHelper.getInt(json, "count"))
            return EnhancingRecipe(id, group, input, pyre, output)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): EnhancingRecipe {
            val group = buf.readString()
            val input = Ingredient.fromPacket(buf)
            val pyre = Ingredient.fromPacket(buf)
            val output = buf.readItemStack()
            return EnhancingRecipe(id, group, input, pyre, output)
        }

        override fun write(buf: PacketByteBuf, recipe: EnhancingRecipe) {
            buf.writeString(recipe.group)
            recipe.input.write(buf)
            recipe.pyre.write(buf)
            buf.writeItemStack(recipe.output)
        }

    }

}
