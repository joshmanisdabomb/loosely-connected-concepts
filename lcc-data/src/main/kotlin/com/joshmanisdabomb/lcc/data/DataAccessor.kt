package com.joshmanisdabomb.lcc.data

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.generators.advancement.AdvancementData
import com.joshmanisdabomb.lcc.data.generators.lang.LangData
import com.joshmanisdabomb.lcc.data.generators.sound.SoundData
import com.joshmanisdabomb.lcc.data.storage.LootTableStore
import com.joshmanisdabomb.lcc.data.storage.RecipeStore
import me.shedaniel.cloth.api.datagen.v1.*
import net.minecraft.block.Block
import net.minecraft.data.DataGenerator
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.entity.EntityType
import net.minecraft.loot.LootTable
import net.minecraft.loot.context.LootContextType
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.util.Identifier
import org.apache.logging.log4j.Logger

interface DataAccessor {

    val modid: String

    val dg: DataGenerator

    val lang: Map<String, LangData>
    val advancements: AdvancementData
    val sounds: SoundData

    val modelStates: ModelStateData
    val recipes: RecipeData
    val lootTables: LootTableData
    val tags: TagData
    val worldGen: WorldGenData

    val recipeStore: RecipeStore
    val lootTableStore: LootTableStore

    val parser: JsonParser
    val gson: Gson
    val logger: Logger

    @JvmDefault
    fun acceptRecipe(provider: RecipeJsonProvider) {
        recipes.accept(provider)
        recipeStore.add(provider)
    }

    @JvmDefault
    fun acceptLootTable(type: LootContextType, id: Identifier, table: LootTable.Builder) {
        lootTables.register(type, id, table)
        lootTableStore.add(type, id, table)
    }

    @JvmDefault
    fun acceptLootTable(block: Block, table: LootTable.Builder) {
        acceptLootTable(LootContextTypes.BLOCK, block.lootTableId, table)
    }

    @JvmDefault
    fun acceptLootTable(entry: EntityType<*>, table: LootTable.Builder) {
        acceptLootTable(LootContextTypes.ENTITY, entry.lootTableId, table)
    }

}