package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.data.generators.LangData
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler

class DataAccessor(val modid: String, val handler: DataGeneratorHandler, val languages: List<String>) {

    val lang by lazy { languages.map { it to LangData(handler.dataGenerator, modid, it).apply { handler.install(this) } }.toMap() }

    val tags by lazy { handler.tags }

}