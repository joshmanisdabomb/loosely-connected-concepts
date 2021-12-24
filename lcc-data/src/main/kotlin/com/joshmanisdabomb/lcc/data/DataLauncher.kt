package com.joshmanisdabomb.lcc.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.batches.*
import com.joshmanisdabomb.lcc.data.generators.advancement.AdvancementData
import com.joshmanisdabomb.lcc.data.generators.lang.LangData
import com.joshmanisdabomb.lcc.data.generators.loot.LootTableData
import com.joshmanisdabomb.lcc.data.generators.model.ModelData
import com.joshmanisdabomb.lcc.data.generators.recipe.RecipeData
import com.joshmanisdabomb.lcc.data.generators.sound.SoundData
import com.joshmanisdabomb.lcc.data.generators.state.BlockStateData
import com.joshmanisdabomb.lcc.data.generators.tag.TagData
import com.joshmanisdabomb.lcc.data.storage.RecipeBatch
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.Bootstrap
import net.minecraft.SharedConstants
import net.minecraft.client.MinecraftClient
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import org.apache.logging.log4j.LogManager
import java.io.IOException
import java.nio.file.Path
import java.util.*
import kotlin.system.exitProcess

abstract class DataLauncher(override val modid: String, final override val path: Path) : PreLaunchEntrypoint, DataAccessor {

    open val model_priority = 1000
    open val state_priority = 1001
    open val recipe_priority = 2000
    open val loot_priority = 3000
    open val tags_priority = 4000
    open val lang_priority = 7000
    open val sound_priority = 6000
    open val advancement_priority = 5000

    protected val datagen by lazy { DataGenerator(path, emptyList()) }
    protected val delayedDatagen by lazy { WaitingDataGenerator(path, emptyList()) }

    override val models by lazy { ModelBatch().also { install(ModelData(it, this), model_priority) } }
    override val states by lazy { BlockStateBatch().also { install(BlockStateData(it, this), state_priority) } }
    override val recipes by lazy { RecipeBatch().also { install(RecipeData(it, this), recipe_priority) } }
    override val lootTables by lazy { LootTableBatch().also { install(LootTableData(it, this), loot_priority) } }
    override val tags by lazy { TagBatch().also { install(TagData(it, this), tags_priority) } }
    override val lang by lazy { LangBatch().also { install(LangData(it, this), lang_priority) } }
    override val sounds by lazy { SoundBatch().also { install(SoundData(it, this), sound_priority) } }
    override val advancements by lazy { AdvancementBatch().also { install(AdvancementData(it, this), advancement_priority) } }

    override val parser = JsonParser()
    override val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    override val logger = LogManager.getLogger()

    private val installs = mutableMapOf<DataProvider, Int>()
    private val delayedInstalls = mutableMapOf<DataProvider, Int>()

    init {
        SharedConstants.createGameVersion()
        Bootstrap.initialize()
    }

    final override fun onPreLaunch() {
        beforeRun()

        installs.toList().sortedBy { (_, v) -> v }.forEach { (k, _) -> datagen.addProvider(k) }
        delayedInstalls.toList().sortedBy { (_, v) -> v }.forEach { (k, _) -> delayedDatagen.install(k) }

        logger.info("Starting $modid data generator in path: $path")
        try {
            datagen.run()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        afterRun()

        if (delayedInstalls.isNotEmpty()) {
            ClientLifecycleEvents.CLIENT_STARTED.register(::onClientLaunch)
            ClientTickEvents.START_CLIENT_TICK.register(::onClientTick)
        }
        else exitProcess(0)
    }

    open fun beforeRun() = Unit

    open fun afterRun() = Unit

    open fun beforeDelayedRun() = Unit

    open fun afterDelayedRun() = Unit

    override fun install(gen: DataProvider, priority: Int) {
        installs[gen] = priority
    }

    override fun delayedInstall(gen: DataProvider, priority: Int) {
        delayedInstalls[gen] = priority
    }

    private fun onClientLaunch(client: MinecraftClient) {
        beforeDelayedRun()

        logger.info("Starting $modid delayed data generator in path: $path")
        try {
            delayedDatagen.run()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        afterDelayedRun()
    }

    private fun onClientTick(client: MinecraftClient) {
        beforeDelayedRun()

        try {
            if (delayedDatagen.run()) {
                exitProcess(0)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        afterDelayedRun()
    }

    companion object {
        fun readString(prompt: String, decision: (choice: String) -> Boolean = { true }): String {
            val input = Scanner(System.`in`)
            var answer: String
            do {
                println("\u001B[35m$prompt\u001B[0m")
                print("> ")
                answer = input.nextLine().trim()
            } while (decision(answer))
            return answer
        }

        fun readChar(prompt: String, decision: (choice: Char?) -> Boolean = { true }): Char? {
            val input = Scanner(System.`in`)
            var answer: Char?
            do {
                println("\u001B[35m$prompt\u001B[0m")
                print("> ")
                answer = input.nextLine().trim().toLowerCase().firstOrNull()
            } while (decision(answer))
            return answer
        }
    }

}