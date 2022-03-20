package com.joshmanisdabomb.lcc.recipe.imbuing

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class ImbuingSimpleRecipe(id: Identifier, protected val _group: String, protected val input: Ingredient, protected val _effects: List<StatusEffectInstance>, protected val hits: Int) : ImbuingRecipe(id) {

    override fun getGroup() = _group

    override fun getOutput() = ItemStack.EMPTY

    override fun getIngredients() = DefaultedList.copyOf(Ingredient.EMPTY, input)

    override fun matches(inventory: Inventory, world: World) = inventory.getStack(0).isIn(LCCItemTags.imbuable) && input.test(inventory.getStack(1))

    override fun craft(inventory: Inventory): ItemStack {
        val stack = inventory.getStack(0).copy()
        stack.getOrCreateSubNbt("lcc-imbue").putInt(id.toString(), hits)
        return stack
    }

    override fun getMaxHits(stack: ItemStack) = hits

    override fun getEffects() = _effects

    override fun getSerializer() = LCCRecipeSerializers.imbuing

    class Serializer : RecipeSerializer<ImbuingSimpleRecipe> {

        override fun read(id: Identifier, json: JsonObject): ImbuingSimpleRecipe {
            val group = JsonHelper.getString(json, "group", "")!!
            val hits = JsonHelper.getInt(json, "hits", 0)
            val input = if (JsonHelper.hasArray(json, "ingredient")) {
                Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"))
            } else {
                Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"))
            }
            val effects = JsonHelper.getArray(json, "effects").map {
                val effect = it.asJsonObject
                val type = Registry.STATUS_EFFECT.get(Identifier(effect.get("effect").asString))
                val duration = if (effect.has("duration")) effect.get("duration").asInt else 600
                val amplifier = if (effect.has("amplifier")) effect.get("amplifier").asInt else 0
                val ambient = if (effect.has("ambient")) effect.get("ambient").asBoolean else false
                val particles = if (effect.has("particles")) effect.get("particles").asBoolean else true
                val icon = if (effect.has("icon")) effect.get("icon").asBoolean else particles
                StatusEffectInstance(type, duration, amplifier, ambient, particles, icon)
            }
            return ImbuingSimpleRecipe(id, group, input, effects, hits)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): ImbuingSimpleRecipe {
            val group = buf.readString()
            val input = Ingredient.fromPacket(buf)
            val effects = buf.readList { StatusEffectInstance(Registry.STATUS_EFFECT.get(Identifier(buf.readString())), buf.readInt(), buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean()) }
            val hits = buf.readInt()
            return ImbuingSimpleRecipe(id, group, input, effects, hits)
        }

        override fun write(buf: PacketByteBuf, recipe: ImbuingSimpleRecipe) {
            buf.writeString(recipe.group)
            recipe.input.write(buf)
            buf.writeCollection(recipe._effects) { b, e ->
                b.writeString(Registry.STATUS_EFFECT.getId(e.effectType).toString())
                b.writeInt(e.duration)
                b.writeInt(e.amplifier)
                b.writeBoolean(e.isAmbient)
                b.writeBoolean(e.shouldShowParticles())
                b.writeBoolean(e.shouldShowIcon())
            }
            buf.writeInt(recipe.hits)
        }

    }

}
