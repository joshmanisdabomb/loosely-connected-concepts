package com.joshmanisdabomb.lcc.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.generators.advancement.AdvancementData
import com.joshmanisdabomb.lcc.data.generators.lang.LangData
import com.joshmanisdabomb.lcc.data.generators.sound.SoundData
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.Bootstrap
import net.minecraft.SharedConstants
import net.minecraft.data.DataProvider
import org.apache.logging.log4j.LogManager
import java.nio.file.Path
import kotlin.system.exitProcess

abstract class DataLauncher(override val modid: String, protected val path: Path, val locales: List<String>) : PreLaunchEntrypoint, DataAccessor {

    protected val handler by lazy { DataGeneratorHandler.create(path) }
    override val dg get() = handler.dataGenerator

    override val lang by lazy { locales.map { it to LangData(this, it).apply { install(this) } }.toMap() }
    override val sounds by lazy { SoundData(this).apply { install(this) } }
    override val advancements by lazy { AdvancementData(this).apply { install(this) } }

    override val modelStates by lazy { handler.modelStates }
    override val recipes by lazy { handler.recipes }
    override val lootTables by lazy { handler.lootTables }
    override val tags by lazy { handler.tags }
    override val worldGen by lazy { handler.worldGen }

    override val parser = JsonParser()
    override val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    override val logger = LogManager.getLogger()

    init {
        SharedConstants.createGameVersion()
        Bootstrap.initialize()
    }

    final override fun onPreLaunch() {
        onLaunchStart()

        handler.run()
        exitProcess(0)
    }

    open fun onLaunchStart() = Unit

    fun install(gen: DataProvider) = handler.install(gen)

}