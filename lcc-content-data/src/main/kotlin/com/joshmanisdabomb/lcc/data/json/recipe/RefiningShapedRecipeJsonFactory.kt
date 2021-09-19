package com.joshmanisdabomb.lcc.data.json.recipe

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.block.entity.RefiningBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.recipe.refining.RefiningSimpleRecipe
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import kotlin.properties.Delegates

class RefiningShapedRecipeJsonFactory : JsonFactoryAccess {

    private val outputs = mutableListOf<ItemStack>()
    private val outputFunctions = mutableListOf<RefiningSimpleRecipe.OutputFunction?>()
    private val pattern = mutableListOf<String>()
    private val inputs = mutableMapOf<Char, Ingredient>()
    private val inputCounts = mutableMapOf<Char, Int>()
    private val builder = Advancement.Task.create()
    private var group: String? = null

    private val blocks = mutableListOf<RefiningBlock>()
    private lateinit var lang: String
    private var icon by Delegates.notNull<Int>()
    private lateinit var state: RefiningBlock.RefiningProcess
    private var energy by Delegates.notNull<Float>()
    private var ticks by Delegates.notNull<Int>()
    private var gain by Delegates.notNull<Float>()
    private var maxGain by Delegates.notNull<Float>()

    fun addInput(c: Char, input: ItemConvertible, count: Int = 1) = addInput(c, Ingredient.ofItems(input), count)

    fun addInput(c: Char, input: Ingredient, count: Int = 1): RefiningShapedRecipeJsonFactory {
        return if (inputs.containsKey(c)) {
            throw IllegalArgumentException("Symbol '$c' is already defined!")
        } else if (c == ' ') {
            throw IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined")
        } else {
            inputs[c] = input
            inputCounts[c] = count
            this
        }
    }

    fun addOutput(output: ItemConvertible, count: Int = 1, function: RefiningSimpleRecipe.OutputFunction? = null): RefiningShapedRecipeJsonFactory {
        outputs.add(ItemStack(output, count))
        outputFunctions.add(function)
        return this
    }

    fun pattern(str: String): RefiningShapedRecipeJsonFactory {
        return if (!pattern.isEmpty() && str.length != pattern[0].length) {
            throw java.lang.IllegalArgumentException("Pattern must be the same width on every line!")
        } else {
            pattern.add(str)
            this
        }
    }

    override fun criterion(criterionName: String, conditions: CriterionConditions): RefiningShapedRecipeJsonFactory {
        builder.criterion(criterionName, conditions)
        return this
    }

    fun group(group: String): RefiningShapedRecipeJsonFactory {
        this.group = group
        return this
    }

    fun with(vararg blocks: RefiningBlock): RefiningShapedRecipeJsonFactory {
        this.blocks.addAll(blocks)
        return this
    }

    fun meta(lang: String, icon: Int, state: RefiningBlock.RefiningProcess): RefiningShapedRecipeJsonFactory {
        this.lang = lang
        this.icon = icon
        this.state = state
        return this
    }

    fun energyPerTick(energy: Float): RefiningShapedRecipeJsonFactory {
        this.energy = energy

        return this
    }

    fun speed(ticks: Int, gain: Float, maxGain: Float): RefiningShapedRecipeJsonFactory {
        this.ticks = ticks
        this.gain = gain
        this.maxGain = maxGain
        return this
    }

    fun energyPerOperation(energy: Float) = energyPerTick(energy.div(ticks))

    override fun offerTo(exporter: (RecipeJsonProvider) -> Unit): RefiningShapedRecipeJsonFactory {
        this.offerAs(exporter, Registry.ITEM.getId(outputs.first().item))
        return this
    }

    fun offerAsString(exporter: (RecipeJsonProvider) -> Unit, recipeIdStr: String): RefiningShapedRecipeJsonFactory {
        val identifier = Registry.ITEM.getId(outputs.first().item)
        check(Identifier(recipeIdStr) != identifier) { "Shaped Recipe $recipeIdStr should remove its 'save' argument" }
        this.offerAs(exporter, Identifier(recipeIdStr))
        return this
    }

    override fun offerAs(exporter: (RecipeJsonProvider) -> Unit, recipeId: Identifier): RefiningShapedRecipeJsonFactory {
        builder.parent(Identifier("recipes/root")).criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(CriterionMerger.OR)
        exporter(RefiningShapedRecipeJsonProvider(recipeId, group ?: "", pattern, inputs, builder, Identifier(recipeId.namespace, "recipes/" + outputs.first().item.group?.name + "/" + recipeId.path), outputs, outputFunctions, inputCounts, blocks, lang, icon, state, energy, ticks, gain, maxGain))
        return this
    }

    class RefiningShapedRecipeJsonProvider(private val recipeId: Identifier, private val group: String, private val pattern: List<String>, private val inputs: Map<Char, Ingredient>, private val builder: Advancement.Task, private val advancementId: Identifier, private val outputs: MutableList<ItemStack>, private val outputFunctions: MutableList<RefiningSimpleRecipe.OutputFunction?>, private val inputCounts: MutableMap<Char, Int>, private val blocks: MutableList<RefiningBlock>, private val lang: String, private val icon: Int, private val state: RefiningBlock.RefiningProcess, private val energy: Float, private val ticks: Int, private val gain: Float, private val maxGain: Float) : RecipeJsonProvider {

        override fun serialize(json: JsonObject) {

            check(energy <= RefiningBlockEntity.energyPerTick) { "Recipe $recipeId has energy requirement $energy which exceeds the energy per tick limit of ${RefiningBlockEntity.energyPerTick}." }

            if (group.isNotEmpty()) json.addProperty("group", group)

            val jsonPattern = JsonArray()
            pattern.forEach { jsonPattern.add(it) }
            json.add("pattern", jsonPattern)

            val jsonKey = JsonObject()
            inputs.entries.forEach { (k, v) ->
                val ing = v.toJson()
                if (ing.isJsonObject) {
                    jsonKey.add(k.toString(), ing.asJsonObject.apply { addProperty("count", inputCounts[k]) })
                } else {
                    val ingArr = ing.asJsonArray
                    ingArr.forEachIndexed { k2, v2 ->
                        ingArr.set(k2, v2.asJsonObject.apply { addProperty("count", inputCounts[k]) })
                    }
                    jsonKey.add(k.toString(), ingArr)
                }
            }
            json.add("key", jsonKey)

            val jsonOutputs = JsonArray()
            outputs.forEachIndexed { i, s ->
                val obj = JsonObject()
                obj.addProperty("item", Registry.ITEM.getId(s.item).toString())
                obj.addProperty("count", s.count)
                obj.addProperty("function", outputFunctions[i]?.identifier?.toString() ?: "minecraft:empty")
                outputFunctions[i]?.write(obj)
                jsonOutputs.add(obj)
            }
            json.add("results", jsonOutputs)

            val jsonBlocks = JsonArray()
            blocks.forEach { jsonBlocks.add(it.identifier.toString()) }
            json.add("blocks", jsonBlocks)

            json.addProperty("lang", lang)
            json.addProperty("icon", icon)
            json.addProperty("state", state.asString())
            json.addProperty("energy", energy)
            json.addProperty("ticks", ticks)
            json.addProperty("gain", gain)
            json.addProperty("maxGain", maxGain)

        }

        override fun getSerializer() = LCCRecipeSerializers.refining_shaped

        override fun getRecipeId() = recipeId

        override fun toAdvancementJson() = builder.toJson()

        override fun getAdvancementId() = advancementId

    }

}