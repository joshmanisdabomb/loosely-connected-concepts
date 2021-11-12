package com.joshmanisdabomb.lcc.data

import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.directory.*
import com.joshmanisdabomb.lcc.data.generators.LCCLangData
import com.joshmanisdabomb.lcc.data.generators.LCCLootData
import com.joshmanisdabomb.lcc.data.generators.commit.CommitData
import com.joshmanisdabomb.lcc.data.generators.kb.export.DatabaseKnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeLinker
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeTranslator
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleWebLinkBuilder
import com.joshmanisdabomb.lcc.data.knowledge.ImageExport
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersionGroup
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.Bootstrap
import net.minecraft.SharedConstants
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.jetbrains.exposed.sql.Database
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

object LCCData : DataLauncher("lcc", Paths.get("../lcc-content/src/generated/resources")) {

    private val exporters = mutableListOf<KnowledgeExporter>()

    val commit: Char? = System.getProperty("com.joshmanisdabomb.lcc.data.Commit")?.firstOrNull()
    val dbExports: List<Pair<String, String?>>? = System.getProperty("com.joshmanisdabomb.lcc.data.DbExports")?.let {
        val array = JsonParser().parse(it).asJsonArray
        array.map {
            val obj = it.asJsonObject
            obj.get("url").asString to obj.get("password")?.asString
        }
    }
    val imageExport: String? = System.getProperty("com.joshmanisdabomb.lcc.data.ImageExport")

    override fun beforeRun() {
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
        recipes.addTagHandlerList(LCCTags.rubber_logs_i, LCCBlocks.natural_rubber_log, LCCBlocks.rubber_log, LCCBlocks.rubber_wood, LCCBlocks.sappy_stripped_rubber_log, LCCBlocks.stripped_rubber_log, LCCBlocks.stripped_rubber_wood)
        recipes.addTagHandlerFilter(BlockTags.WOODEN_STAIRS) { it.asItem().identifier.path.endsWith("_stairs") && Registry.ITEM.containsId(Identifier(it.asItem().identifier.namespace, it.asItem().identifier.path.replace("_stairs", "_planks"))) }
        recipes.addTagHandlerFilter(BlockTags.WOODEN_SLABS) { it.asItem().identifier.path.endsWith("_slab") && Registry.ITEM.containsId(Identifier(it.asItem().identifier.namespace, it.asItem().identifier.path.replace("_slab", "_planks"))) }
        recipes.addTagHandlerList(LCCTags.deadwood_logs_i, LCCBlocks.deadwood_log, LCCBlocks.deadwood, LCCBlocks.stripped_deadwood_log, LCCBlocks.stripped_deadwood)
        recipes.addTagHandlerFilter(ItemTags.MUSIC_DISCS) { it.asItem().identifier.path.startsWith("music_disc_") }
        recipes.addTagHandlerFilter(LCCTags.gold_blocks) { it.asItem().identifier.path.endsWith("gold_block") }

        println("Setting up knowledge and exporters.")
        recipes.compile()
        LCCKnowledgeData.init()
        setupExports()
        exporters.forEach { install(it, 2500) }

        install(CommitData(path, Paths.get("../lcc-content/src/main/resources"), commit) { CommitData.defaultExcluder(it, LCC.modid, "fabric", "minecraft") }, 99999)

        println("Setting up image exporter.")
        val path = imageExport ?: readString("Enter path to save image export, leave blank to not run:") { false }
        if (path.isNotBlank()) {
            val items = Registry.BLOCK.filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" } + Registry.ITEM.filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" }
            val entities = Registry.ENTITY_TYPE.filter { val identifier = Registry.ENTITY_TYPE.getId(it); identifier.namespace == LCC.modid || identifier.namespace == "minecraft" }
            delayedInstall(ImageExport(items, entities, File(path)), 0);
        }
    }

    private fun setupExports() {
        val translator = KnowledgeTranslator().addLangBatchSource(lang, "en_us").addI18nSource()
        val linker = KnowledgeLinker()
            .apply { LCCVersionGroup.values().forEach { this.addSelfProvider(it.namespace) } }
            .addProvider { a -> if (a.registry.namespace == "minecraft" && a.key.namespace == "minecraft") KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/${a.key.path}" } else null }
        if (dbExports == null) {
            do {
                var loop = true
                readString("${exporters.count()} database exports currently installed, enter connection URL or leave blank to finish:") { url ->
                    if (url.isBlank()) loop = false
                    else readString("Enter password for user 'lcc' on url '$url':") {
                        val exporter = DatabaseKnowledgeExporter(Database.connect("jdbc:mysql://$url/lcc?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "lcc", password = it), this, LCCKnowledgeData.all.values, translator, linker)
                        exporters.add(exporter)
                        false
                    }
                    false
                }
            } while (loop)
        } else {
            println("Setting database exports from passed property array.")
            dbExports.forEach {
                val password = it.second ?: readString("Enter password for user 'lcc' on url '${it.first}':") { false }
                val exporter = DatabaseKnowledgeExporter(Database.connect("jdbc:mysql://${it.first}/lcc?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "lcc", password = password), this, LCCKnowledgeData.all.values, translator, linker)
                exporters.add(exporter)
            }
        }
    }

}