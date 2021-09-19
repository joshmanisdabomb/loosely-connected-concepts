package com.joshmanisdabomb.lcc.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.generators.advancement.AdvancementData
import com.joshmanisdabomb.lcc.data.generators.lang.LangData
import com.joshmanisdabomb.lcc.data.generators.sound.SoundData
import com.joshmanisdabomb.lcc.data.json.recipe.RecipeStore
import joptsimple.OptionParser
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.Bootstrap
import net.minecraft.SharedConstants
import net.minecraft.client.MinecraftClient
import net.minecraft.data.DataProvider
import org.apache.logging.log4j.LogManager
import java.nio.file.Path
import java.util.*
import kotlin.system.exitProcess

abstract class DataLauncher(override val modid: String, protected val path: Path, val locales: List<String>) : PreLaunchEntrypoint, DataAccessor {

    open val lang_priority = 3000
    open val sound_priority = 2000
    open val advancement_priority = 1000

    protected val handler by lazy { DataGeneratorHandler.create(path) }
    override val dg get() = handler.dataGenerator

    override val lang by lazy { locales.map { it to LangData(this, it).apply { install(this, lang_priority) } }.toMap() }
    override val sounds by lazy { SoundData(this).apply { install(this, sound_priority) } }
    override val advancements by lazy { AdvancementData(this).apply { install(this, advancement_priority) } }

    override val modelStates by lazy { handler.modelStates }
    override val recipes by lazy { handler.recipes }
    override val lootTables by lazy { handler.lootTables }
    override val tags by lazy { handler.tags }
    override val worldGen by lazy { handler.worldGen }

    override val parser = JsonParser()
    override val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    override val logger = LogManager.getLogger()

    override val recipeStore = RecipeStore()

    private val installs = mutableMapOf<DataProvider, Int>()
    private val nextPriority get() = installs.values.maxOrNull()?.plus(1) ?: 0

    val optionParser = OptionParser()

    init {
        SharedConstants.createGameVersion()
        Bootstrap.initialize()
    }

    final override fun onPreLaunch() {
        beforeRun()

        installs.toList().sortedBy { (_, v) -> v }.forEach { (k, _) -> dg.install(k) }
        handler.run()
        afterRun()

        ClientLifecycleEvents.CLIENT_STARTED.register(::onMinecraftLoad)
    }

    open fun beforeRun() = Unit

    open fun afterRun() {
        exitProcess(0)
    }

    open fun onMinecraftLoad(client: MinecraftClient) = Unit

    fun install(gen: DataProvider, priority: Int = nextPriority) {
        installs[gen] = priority
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