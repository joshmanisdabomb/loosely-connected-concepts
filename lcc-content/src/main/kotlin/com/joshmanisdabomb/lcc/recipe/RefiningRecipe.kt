package com.joshmanisdabomb.lcc.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry
import java.util.*
import kotlin.properties.Delegates

abstract class RefiningRecipe(protected val _id: Identifier, protected val _group: String, protected val ingredients: DefaultedList<Pair<Ingredient, Int>>, protected val _output: DefaultedList<Pair<ItemStack, OutputFunction?>>, val blocks: Array<Block>, val lang: String, val icon: Int, val state: RefiningBlock.RefiningProcess, val energy: Float, val ticks: Int, val gain: Float, val maxGain: Float) : Recipe<RefiningInventory> {

    private val ingredientList by lazy { DefaultedList.copyOf(Ingredient.EMPTY, *ingredients.map { it.first }.toTypedArray()) }

    override fun getType() = LCCRecipeTypes.refining

    override fun getRecipeKindIcon() = blocks.first().asItem().defaultStack

    override fun getId() = _id

    override fun getGroup() = _group

    override fun getPreviewInputs() = ingredientList

    override fun getOutput() = _output.map { it.first }.firstOrNull() ?: ItemStack.EMPTY

    override fun craft(inv: RefiningInventory) = output.copy()

    abstract fun input(inv: RefiningInventory): Boolean

    fun generate(random: Random) = _output.map { var stack = it.first.copy(); it.second?.run { stack = apply(stack, random) }; stack }

    val maximum by lazy { _output.map { var stack = it.first.copy(); it.second?.run { stack = applyMaximum(stack) }; stack } }

    sealed class OutputFunction(val identifier: Identifier) {

        abstract fun apply(stack: ItemStack, random: Random): ItemStack?

        abstract fun applyMaximum(stack: ItemStack): ItemStack?

        abstract fun read(json: JsonObject): OutputFunction?

        abstract fun read(buf: PacketByteBuf)

        abstract fun write(json: JsonObject)

        abstract fun write(buf: PacketByteBuf)

        companion object {
            fun get(id: Identifier) = when (id) {
                LCC.id("chance") -> ChanceOutputFunction(id)
                LCC.id("range") -> RangeOutputFunction(id)
                else -> null
            }
        }

        class ChanceOutputFunction(id: Identifier) : OutputFunction(id) {

            constructor(id: Identifier, chance: Float) : this(id) {
                c = chance
            }

            private var c by Delegates.notNull<Float>()
            val chance get() = c

            override fun apply(stack: ItemStack, random: Random) = stack.run { if (random.nextFloat() < c) this else null }

            override fun applyMaximum(stack: ItemStack) = stack

            override fun read(json: JsonObject): ChanceOutputFunction? {
                c = try {
                    JsonHelper.getFloat(json, "chance")
                } catch (ex: Exception) {
                    return null
                }
                return this
            }

            override fun read(buf: PacketByteBuf) {
                c = buf.readFloat()
            }

            override fun write(json: JsonObject) {
                json.addProperty("chance", c)
            }

            override fun write(buf: PacketByteBuf) {
                buf.writeFloat(c)
            }

        }

        class RangeOutputFunction(id: Identifier) : OutputFunction(id) {

            constructor(id: Identifier, max: Int) : this(id) {
                m = max
            }

            private var m by Delegates.notNull<Int>()
            val max get() = m

            override fun apply(stack: ItemStack, random: Random) = stack.apply { increment(random.nextInt(max.minus(count).plus(1))) }

            override fun applyMaximum(stack: ItemStack) = stack.apply { increment(max.minus(count)) }

            override fun read(json: JsonObject): RangeOutputFunction? {
                m = try {
                    JsonHelper.getInt(json, "max")
                } catch (ex: Exception) {
                    return null
                }
                return this
            }

            override fun read(buf: PacketByteBuf) {
                m = buf.readInt()
            }

            override fun write(json: JsonObject) {
                json.addProperty("max", m)
            }

            override fun write(buf: PacketByteBuf) {
                buf.writeInt(m)
            }

        }

    }

    class Metadata() {

        lateinit var blocks: Array<Block?>
        lateinit var lang: String
        var icon by Delegates.notNull<Int>()
        lateinit var state: RefiningBlock.RefiningProcess
        var energy by Delegates.notNull<Float>()
        var ticks by Delegates.notNull<Int>()
        var gain by Delegates.notNull<Float>()
        var maxGain by Delegates.notNull<Float>()

        constructor(blocks: Array<Block>, lang: String, icon: Int, state: RefiningBlock.RefiningProcess, energy: Float, ticks: Int, gain: Float, maxGain: Float) : this() {
            this.blocks = blocks.toList().toTypedArray()
            this.lang = lang
            this.icon = icon
            this.state = state
            this.energy = energy
            this.ticks = ticks
            this.gain = gain
            this.maxGain = maxGain
        }

        constructor(recipe: RefiningRecipe) : this(recipe.blocks, recipe.lang, recipe.icon, recipe.state, recipe.energy, recipe.ticks, recipe.gain, recipe.maxGain)

        fun read(json: JsonObject): Metadata {
            val b = JsonHelper.getArray(json, "blocks")
            blocks = b.map { it.asString }.mapNotNull { Registry.BLOCK[Identifier.tryParse(it)] }.toTypedArray()
            lang = JsonHelper.getString(json, "lang")
            icon = JsonHelper.getInt(json, "icon")
            state = RefiningBlock.RefiningProcess.find(JsonHelper.getString(json, "state")) ?: throw JsonSyntaxException("State $state is not a valid refining process.")
            if (!blocks.all { (it as? RefiningBlock)?.processes?.values?.contains(state) == true }) throw JsonSyntaxException("State $state cannot be given to a refining block that can use this recipe.")
            energy = JsonHelper.getFloat(json, "energy")
            ticks = JsonHelper.getInt(json, "ticks")
            gain = JsonHelper.getFloat(json, "gain")
            maxGain = JsonHelper.getFloat(json, "maxGain")
            return this
        }

        fun read(buf: PacketByteBuf): Metadata {
            blocks = Array(buf.readInt()) { null }
            for (i in blocks.indices) {
                blocks[i] = Registry.BLOCK[buf.readIdentifier()]
            }
            lang = buf.readString()
            icon = buf.readInt()
            state = RefiningBlock.RefiningProcess.find(buf.readString())!!
            energy = buf.readFloat()
            ticks = buf.readInt()
            gain = buf.readFloat()
            maxGain = buf.readFloat()
            return this
        }

        fun write(buf: PacketByteBuf) {
            buf.writeInt(blocks.size)
            blocks.filterNotNull().forEach { buf.writeIdentifier(it.identifier) }
            buf.writeString(lang)
            buf.writeInt(icon)
            buf.writeString(state.asString())
            buf.writeFloat(energy)
            buf.writeInt(ticks)
            buf.writeFloat(gain)
            buf.writeFloat(maxGain)
        }

        operator fun component1() = blocks.filterNotNull().toTypedArray()
        operator fun component2() = lang
        operator fun component3() = icon
        operator fun component4() = state
        operator fun component5() = energy
        operator fun component6() = ticks
        operator fun component7() = gain
        operator fun component8() = maxGain

    }

}