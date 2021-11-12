package com.joshmanisdabomb.lcc.data

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.batches.*
import com.joshmanisdabomb.lcc.data.storage.RecipeBatch
import net.minecraft.data.DataProvider
import org.apache.logging.log4j.Logger
import java.nio.file.Path

interface DataAccessor {

    val modid: String
    val path: Path

    val models: ModelBatch
    val states: BlockStateBatch
    val recipes: RecipeBatch
    val lootTables: LootTableBatch
    val tags: TagBatch
    val lang: LangBatch
    val advancements: AdvancementBatch
    val sounds: SoundBatch

    val parser: JsonParser
    val gson: Gson
    val logger: Logger

    fun install(gen: DataProvider, priority: Int)
    fun delayedInstall(gen: DataProvider, priority: Int)

}