package com.joshmanisdabomb.lcc.recipe.refining

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry
import java.util.*
import kotlin.properties.Delegates

abstract class RefiningSimpleRecipe(protected val _id: Identifier, protected val _group: String, protected val refiningIngredients: DefaultedList<Pair<Ingredient, Int>>, protected val _output: DefaultedList<Pair<ItemStack, OutputFunction?>>, override val blocks: Array<Block>, override val lang: String, override val icon: Int, override val state: RefiningBlock.RefiningProcess, protected val energy: Float, protected val ticks: Int, protected val gain: Float, protected val maxGain: Float) : RefiningRecipe() {

    private val ingredientList by lazy { DefaultedList.copyOf(Ingredient.EMPTY, *refiningIngredients.map { it.first }.toTypedArray()) }

    private val maximum by lazy { _output.map { var stack = it.first.copy(); it.second?.run { stack = applyMaximum(stack) }; stack } }

    override fun getId() = _id

    override fun getGroup() = _group

    override fun getIngredients() = ingredientList

    override fun getOutput() = _output.map { it.first }.firstOrNull() ?: ItemStack.EMPTY

    override fun getEnergyPerTick() = energy

    override fun getSpeed() = ticks

    override fun getSpeedGainPerTick() = gain

    override fun getMaxSpeedGainPerTick() = maxGain

    override fun generate(consumed: List<ItemStack>, inventory: RefiningInventory, random: Random) = _output.map { var stack = it.first.copy(); it.second?.run { stack = apply(stack, random) }; stack }

    override fun generateMaximum(inventory: RefiningInventory) = maximum

    override fun craft(inv: RefiningInventory) = output.copy()

    sealed class OutputFunction {

        abstract val identifier: Identifier

        abstract fun apply(stack: ItemStack, random: Random): ItemStack

        abstract fun applyMaximum(stack: ItemStack): ItemStack

        abstract fun read(json: JsonObject): OutputFunction?

        abstract fun read(buf: PacketByteBuf)

        abstract fun write(json: JsonObject)

        abstract fun write(buf: PacketByteBuf)

        companion object {
            fun get(id: Identifier) = when (id) {
                ChanceOutputFunction.identifier -> ChanceOutputFunction()
                RangeOutputFunction.identifier -> RangeOutputFunction()
                else -> null
            }
        }

        class ChanceOutputFunction internal constructor() : OutputFunction() {

            constructor(chance: Float) : this() {
                c = chance
            }

            private var c by Delegates.notNull<Float>()
            val chance get() = c

            override val identifier = Companion.identifier

            override fun apply(stack: ItemStack, random: Random) = stack.run { if (random.nextFloat() < c) this else ItemStack.EMPTY }

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

            companion object {
                val identifier = LCC.id("chance")
            }

        }

        class RangeOutputFunction internal constructor() : OutputFunction() {

            constructor(max: Int) : this() {
                m = max
            }

            private var m by Delegates.notNull<Int>()
            val max get() = m

            override val identifier = Companion.identifier

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

            companion object {
                val identifier = LCC.id("range")
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

        constructor(recipe: RefiningSimpleRecipe) : this(recipe.blocks, recipe.lang, recipe.icon, recipe.state, recipe.energy, recipe.ticks, recipe.gain, recipe.maxGain)

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