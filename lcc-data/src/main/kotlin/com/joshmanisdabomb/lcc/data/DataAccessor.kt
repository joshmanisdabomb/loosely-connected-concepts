package com.joshmanisdabomb.lcc.data

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.generators.advancement.AdvancementData
import com.joshmanisdabomb.lcc.data.generators.lang.LangData
import com.joshmanisdabomb.lcc.data.generators.sound.SoundData
import me.shedaniel.cloth.api.datagen.v1.*
import net.minecraft.data.DataGenerator
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

    val parser: JsonParser
    val gson: Gson
    val logger: Logger

}