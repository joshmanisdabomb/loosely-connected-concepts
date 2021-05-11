package com.joshmanisdabomb.lcc.data

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.directory.*
import com.joshmanisdabomb.lcc.data.generators.CommitData
import com.joshmanisdabomb.lcc.data.generators.LCCLangData
import java.nio.file.Paths

object LCCData : DataLauncher("lcc", Paths.get("../lcc-content/src/generated/resources"), listOf("en_us", "en_gb")) {

    override fun onLaunchStart() {
        LCC.onInitialize()

        LCCModelTextureKeys.init()
        LCCModelTemplates.init()

        LCCTagData.init()
        LCCBlockData.init()
        LCCItemData.init()
        LCCEntityData.init()

        LCCAdvancementData.init()
        LCCLangData.init()

        LCCSoundData.init()

        install(CommitData(path, Paths.get("../lcc-content/src/main/resources")) { CommitData.defaultExcluder(it, LCC.modid, "fabric", "minecraft") })
    }

}