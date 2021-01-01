package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.*
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import java.nio.file.Paths
import kotlin.system.exitProcess

object LCCData : PreLaunchEntrypoint {

    val accessor = DataAccessor("lcc", DataGeneratorHandler.create(Paths.get("../lcc-content/src/generated/resources")), listOf("en_us", "en_gb"))

    override fun onPreLaunch() {
        LCC.onInitialize()

        LCCTagData.init()
        LCCBlockData.init()
        LCCItemData.init()
        LCCEntityData.init()
        ModelTemplates.init()
        LCCAdvancementData.init()

        accessor.handler.run()
        exitProcess(0)
    }

}