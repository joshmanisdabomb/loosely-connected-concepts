package com.joshmanisdabomb.lcc.data

import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.directory.*
import com.joshmanisdabomb.lcc.data.generators.LCCLangData
import com.joshmanisdabomb.lcc.data.generators.LCCLootData
import com.joshmanisdabomb.lcc.data.generators.commit.CommitData
import com.joshmanisdabomb.lcc.data.generators.file.WinscpFileExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.LCCDatabaseKnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.LangBatchKnowledgeExporter
import com.joshmanisdabomb.lcc.data.knowledge.ImageExport
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgePluralisations
import com.joshmanisdabomb.lcc.data.knowledge.LangExport
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.jetbrains.exposed.sql.Database
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

object LCCData : DataLauncher("lcc", Paths.get("../lcc-content/src/generated/resources")) {

    val environmentJson = System.getProperty("com.joshmanisdabomb.lcc.data.Environment")?.let { JsonParser.parseReader(FileReader(it)).asJsonObject }

    val commit: Char? = environmentJson?.getAsJsonPrimitive("commit")?.asString?.get(0)
    val dbExports: List<Pair<String, String?>>? = environmentJson?.getAsJsonArray("knowledge_db")?.map {
        val obj = it.asJsonObject
        obj.get("url").asString to obj.get("password")?.asString
    }
    val imageExport: String? = environmentJson?.getAsJsonPrimitive("knowledge_images")?.asString
    val langExport: String? = environmentJson?.getAsJsonPrimitive("knowledge_lang")?.asString
    val ftpExports: Map<String, List<Pair<String, String>>>? = environmentJson?.getAsJsonObject("knowledge_ftp")?.entrySet()?.associate { (k, v) ->
        k to v.asJsonArray.map {
            val obj = it.asJsonObject
            obj.get("from").asString to obj.get("to").asString
        }
    }

    override fun beforeRun() {
        println("Deleting generated folder.")
        path.toFile().deleteRecursively()

        println("Initialising content mod.")
        LCC.onInitialize()

        println("Setting up model templates and keys.")
        LCCModelTextureKeys.init()
        LCCModelTemplates.init()

        println("Preparing data factory to blocks, items, entities and tags.")
        lang.addLocale("en_us").addLocale("en_gb")
        LCCTagData.init()
        LCCBlockData.init()
        LCCItemData.init()
        LCCEntityData.init()

        println("Setting advancements, loot tables, lang files and sound files.")
        LCCAdvancementData.init()
        LCCLangData.init()
        LCCLootData.init()
        LCCSoundData.init()

        println("Setting tag handlers for recipe exporting.")
        recipes.addTagHandlerFilter(BlockTags.PLANKS) { it.asItem().identifier.path.endsWith("_planks") }
        recipes.addTagHandlerFilter(BlockTags.LEAVES) { it.asItem().identifier.path.endsWith("_leaves") }
        recipes.addTagHandlerFilter(BlockTags.SAPLINGS) { it.asItem().identifier.path.endsWith("_sapling") }
        recipes.addTagHandlerFilter(BlockTags.BUTTONS) { it.asItem().identifier.path.endsWith("_button") }
        recipes.addTagHandlerList(LCCItemTags.rubber_logs, LCCBlocks.natural_rubber_log, LCCBlocks.rubber_log, LCCBlocks.rubber_wood, LCCBlocks.sappy_stripped_rubber_log, LCCBlocks.stripped_rubber_log, LCCBlocks.stripped_rubber_wood)
        recipes.addTagHandlerFilter(BlockTags.WOODEN_STAIRS) { it.asItem().identifier.path.endsWith("_stairs") && Registry.ITEM.containsId(Identifier(it.asItem().identifier.namespace, it.asItem().identifier.path.replace("_stairs", "_planks"))) }
        recipes.addTagHandlerFilter(BlockTags.WOODEN_SLABS) { it.asItem().identifier.path.endsWith("_slab") && Registry.ITEM.containsId(Identifier(it.asItem().identifier.namespace, it.asItem().identifier.path.replace("_slab", "_planks"))) }
        recipes.addTagHandlerList(LCCItemTags.deadwood_logs, LCCBlocks.deadwood_log, LCCBlocks.deadwood, LCCBlocks.stripped_deadwood_log, LCCBlocks.stripped_deadwood)
        recipes.addTagHandlerFilter(ItemTags.MUSIC_DISCS) { it.asItem().identifier.path.startsWith("music_disc_") }
        recipes.addTagHandlerFilter(LCCItemTags.gold_blocks) { it.asItem().identifier.path.endsWith("gold_block") }
        recipes.addTagHandlerFilter(ItemTags.TERRACOTTA) { it.asItem().identifier.path.endsWith("_terracotta") && !it.asItem().identifier.path.endsWith("_glazed_terracotta") }
        recipes.addTagHandlerFilter(BlockTags.CORAL_BLOCKS) { it.asItem().identifier.path.endsWith("_coral_block") && !it.asItem().identifier.path.startsWith("dead_") }

        println("Setting up knowledge and exporters.")
        recipes.index()
        LCCKnowledgeData.init()
        val lpath = langExport ?: readString("Enter path to save lang export, leave blank to not run:") { false }
        if (lpath.isNotBlank()) {
            delayedInstall(LangExport(File(lpath)) { r, n, l -> r.resolve("$l/$n.json") }
                .addFromLangBatch("lcc", "en_us", lang)
                .addFromLangBatch("lcc", "en_gb", lang)
                .addFromResources("minecraft", "en_us")
                .addFromResources("minecraft", "en_gb")
            , 0)
        }
        install(LangBatchKnowledgeExporter(lang, this, LCCKnowledgeData.all.values), 2500)
        KnowledgeConstants.injectTranslations(lang)
        KnowledgePluralisations.injectTranslations(lang)
        setupDatabaseExports().forEach { install(it, 2501) }

        install(CommitData(path, Paths.get("../lcc-content/src/main/resources"), commit) { CommitData.defaultExcluder(it, LCC.modid, "fabric", "minecraft") }, 99999)

        println("Setting up image exporter.")
        val ipath = imageExport ?: readString("Enter path to save image export, leave blank to not run:") { false }
        if (ipath.isNotBlank()) {
            val items = Registry.BLOCK.filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" } + Registry.ITEM.filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" }
            val entities = Registry.ENTITY_TYPE.filter { val identifier = Registry.ENTITY_TYPE.getId(it); identifier.namespace == LCC.modid || identifier.namespace == "minecraft" }
            delayedInstall(ImageExport(items, entities, File(ipath)), 900)
        }

        println("Setting up FTP exporters.")
        setupFtpExports().forEach { delayedInstall(it, 1000) }
    }

    private fun setupDatabaseExports(): MutableList<KnowledgeExporter> {
        val exporters = mutableListOf<KnowledgeExporter>()
        if (dbExports == null) {
            do {
                var loop = true
                readString("${exporters.count()} database exports currently installed, enter connection URL or leave blank to finish:") { url ->
                    if (url.isBlank()) loop = false
                    else readString("Enter password for user 'lcc' on url '$url':") {
                        val exporter = LCCDatabaseKnowledgeExporter(Database.connect("jdbc:mysql://$url/lcc?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "lcc", password = it), this, LCCKnowledgeData.all.values)
                        exporters.add(exporter)
                        false
                    }
                    false
                }
            } while (loop)
        } else {
            println("Setting database exports from passed property.")
            dbExports.forEach {
                val password = it.second ?: readString("Enter password for user 'lcc' on url '${it.first}':") { false }
                val exporter = LCCDatabaseKnowledgeExporter(Database.connect("jdbc:mysql://${it.first}/lcc?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "lcc", password = password), this, LCCKnowledgeData.all.values)
                exporters.add(exporter)
            }
        }
        return exporters
    }

    private fun setupFtpExports(): MutableList<WinscpFileExporter> {
        val exporters = mutableListOf<WinscpFileExporter>()
        if (ftpExports == null) {
            do {
                var loop = true
                readString("${exporters.count()} FTP exports currently installed, enter site name or leave blank to finish:") { site ->
                    if (site.isBlank()) loop = false
                    else {
                        do {
                            var loop2 = true
                            readString("Enter source folder on local machine to copy to remote '$site' or leave blank to finish:") { from ->
                                if (site.isBlank()) loop2 = false
                                else readString("Enter destination folder on remote machine '$site' (source '$from'):") {
                                    exporters.add(WinscpFileExporter(site, from, it, this))
                                    false
                                }
                                false
                            }
                        } while (loop2)
                    }
                    false
                }
            } while (loop)
        } else {
            println("Setting FTP exports from passed property.")
            ftpExports.forEach { (site, copies) ->
                copies.forEach { (from, to) ->
                    exporters.add(WinscpFileExporter(site, from, to, this))
                }
            }
        }
        return exporters
    }

}