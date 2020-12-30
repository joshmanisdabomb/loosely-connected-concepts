package com.joshmanisdabomb.lcc.data

import com.joshmanisdabomb.lcc.data.generators.LangData
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler

class DataAccessor(val modid: String, val handler: DataGeneratorHandler, val languages: List<String>) {

    val lang by lazy { languages.map { it to LangData(handler.dataGenerator, modid, it).apply { handler.install(this) } }.toMap() }

    val modelStates by lazy { handler.modelStates }
    val recipes by lazy { handler.recipes }
    val lootTables by lazy { handler.lootTables }
    val tags by lazy { handler.tags }
    val worldGen by lazy { handler.worldGen }

}