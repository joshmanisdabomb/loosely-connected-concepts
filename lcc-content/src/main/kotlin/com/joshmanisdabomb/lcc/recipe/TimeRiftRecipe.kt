package com.joshmanisdabomb.lcc.recipe

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.CuttingRecipe
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class TimeRiftRecipe(id: Identifier, group: String, input: Ingredient, output: ItemStack) : CuttingRecipe(LCCRecipeTypes.time_rift, LCCRecipeSerializers.time_rift, id, group, input, output) {

    override fun matches(inv: Inventory, world: World) = input.test(inv.getStack(0))

    override fun getRecipeKindIcon() = LCCBlocks.time_rift.asItem().defaultStack

    class Serializer : RecipeSerializer<TimeRiftRecipe> {

        override fun read(id: Identifier, json: JsonObject): TimeRiftRecipe {
            val group = JsonHelper.getString(json, "group", "")
            val input = if (JsonHelper.hasArray(json, "ingredient")) {
                Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"))
            } else {
                Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"))
            }
            val output = ItemStack(Registry.ITEM[Identifier(JsonHelper.getString(json, "result"))], JsonHelper.getInt(json, "count"))
            return TimeRiftRecipe(id, group, input, output)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): TimeRiftRecipe {
            val group = buf.readString()
            val input = Ingredient.fromPacket(buf)
            val output = buf.readItemStack()
            return TimeRiftRecipe(id, group, input, output)
        }

        override fun write(buf: PacketByteBuf, recipe: TimeRiftRecipe) {
            buf.writeString(recipe.group)
            recipe.input.write(buf)
            buf.writeItemStack(recipe.output)
        }

    }

}
