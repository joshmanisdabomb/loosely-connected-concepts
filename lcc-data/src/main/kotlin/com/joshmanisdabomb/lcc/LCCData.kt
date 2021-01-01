package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.LCCLangData
import com.joshmanisdabomb.lcc.data.directory.*
import com.joshmanisdabomb.lcc.data.generators.CommitData
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import java.nio.file.Paths
import kotlin.system.exitProcess

object LCCData : PreLaunchEntrypoint {

    val path = Paths.get("../lcc-content/src/generated/resources")
    val accessor = DataAccessor("lcc", DataGeneratorHandler.create(path), listOf("en_us", "en_gb"))

    override fun onPreLaunch() {
        LCC.onInitialize()

        LCCTagData.init()
        LCCBlockData.init()
        LCCItemData.init()
        LCCEntityData.init()

        ModelTemplates.init()
        LCCAdvancementData.init()
        LCCLangData(accessor.lang)

        accessor.handler.install(CommitData(path, Paths.get("../lcc-content/src/main/resources")) { CommitData.defaultExcluder(it, LCC.modid) })

        accessor.handler.run()
        exitProcess(0)
    }

}