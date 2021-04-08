package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.LCCLangData
import com.joshmanisdabomb.lcc.data.directory.*
import com.joshmanisdabomb.lcc.data.generators.CommitData
import com.joshmanisdabomb.lcc.data.generators.SoundData
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.SharedConstants
import java.nio.file.Paths
import kotlin.system.exitProcess

object LCCData : PreLaunchEntrypoint {

    init {
        SharedConstants.method_36208()
    }

    val path = Paths.get("../lcc-content/src/generated/resources")
    val accessor = DataAccessor("lcc", DataGeneratorHandler.create(path), listOf("en_us", "en_gb"))

    val sounds = SoundData(accessor.handler.dataGenerator, "lcc")

    override fun onPreLaunch() {
        LCC.onInitialize()

        LCCModelTextureKeys.init()
        LCCModelTemplates.init()

        LCCTagData.init()
        LCCBlockData.init()
        LCCItemData.init()
        LCCEntityData.init()

        LCCAdvancementData.init()
        LCCLangData(accessor.lang)

        LCCSoundData.init()
        accessor.handler.install(sounds)

        accessor.handler.install(CommitData(path, Paths.get("../lcc-content/src/main/resources")) { CommitData.defaultExcluder(it, LCC.modid, "fabric", "minecraft") })

        accessor.handler.run()
        exitProcess(0)
    }

}