package com.joshmanisdabomb.lcc.data

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.directory.*
import com.joshmanisdabomb.lcc.data.generators.LCCLangData
import com.joshmanisdabomb.lcc.data.generators.LCCLootData
import com.joshmanisdabomb.lcc.data.generators.commit.CommitData
import com.joshmanisdabomb.lcc.data.generators.kb.export.DatabaseKnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import org.jetbrains.exposed.sql.Database
import java.nio.file.Paths
import java.util.*

object LCCData : DataLauncher("lcc", Paths.get("../lcc-content/src/generated/resources"), listOf("en_us", "en_gb")) {

    private val exporters = mutableListOf<KnowledgeExporter>()

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
        LCCLootData.init()

        LCCSoundData.init()

        LCCKnowledgeData.init()

        do {
            println("${exporters.count()} database exports currently installed, enter connection URL or leave blank to finish:")
            val url = Scanner(System.`in`).nextLine().trim()
            if (url.isBlank()) break

            println("Enter password for user 'lcc':")
            val password = Scanner(System.`in`).next().trim()

            val exporter = DatabaseKnowledgeExporter(Database.connect("jdbc:mysql://$url/lcc?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "lcc", password = password), this, LCCKnowledgeData.all.values)
            exporters.add(exporter)
            install(exporter)
        } while (true)

        install(CommitData(path, Paths.get("../lcc-content/src/main/resources")) { CommitData.defaultExcluder(it, LCC.modid, "fabric", "minecraft") })
    }

}