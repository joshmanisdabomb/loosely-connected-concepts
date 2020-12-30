package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.BlockData
import com.joshmanisdabomb.lcc.data.directory.EntityData
import com.joshmanisdabomb.lcc.data.directory.ItemData
import com.joshmanisdabomb.lcc.data.directory.ModelTemplates
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import java.nio.file.Paths
import kotlin.system.exitProcess

object LCCData : PreLaunchEntrypoint {

    val accessor = DataAccessor("lcc", DataGeneratorHandler.create(Paths.get("../lcc-content/src/generated/resources")), listOf("en_us", "en_gb"))

    override fun onPreLaunch() {
        LCC.onInitialize()

        BlockData.init()
        ItemData.init()
        EntityData.init()
        ModelTemplates.init()

        accessor.handler.run()
        exitProcess(0)
    }

}