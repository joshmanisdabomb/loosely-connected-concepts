package com.joshmanisdabomb.lcc.data

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.directory.*
import com.joshmanisdabomb.lcc.data.generators.LCCLangData
import com.joshmanisdabomb.lcc.data.generators.LCCLootData
import com.joshmanisdabomb.lcc.data.generators.commit.CommitData
import com.joshmanisdabomb.lcc.data.generators.kb.export.DatabaseKnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeTranslator
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.jetbrains.exposed.sql.Database
import java.nio.file.Paths
import java.util.*

object LCCData : DataLauncher("lcc", Paths.get("../lcc-content/src/generated/resources"), listOf("en_us", "en_gb")) {

    private val exporters = mutableListOf<KnowledgeExporter>()

    override fun beforeRun() {
        println("Initialising content mod.")
        LCC.onInitialize()

        println("Setting up model templates and keys.")
        LCCModelTextureKeys.init()
        LCCModelTemplates.init()

        println("Preparing data factory to blocks, items, entities and tags.")
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
        recipeStore.addTagHandlerFilter(BlockTags.PLANKS) { it.asItem().identifier.path.endsWith("_planks") }
        recipeStore.addTagHandlerFilter(BlockTags.LEAVES) { it.asItem().identifier.path.endsWith("_leaves") }
        recipeStore.addTagHandlerFilter(BlockTags.SAPLINGS) { it.asItem().identifier.path.endsWith("_sapling") }
        recipeStore.addTagHandlerFilter(BlockTags.BUTTONS) { it.asItem().identifier.path.endsWith("_button") }
        recipeStore.addTagHandlerList(LCCTags.rubber_logs_i, LCCBlocks.natural_rubber_log, LCCBlocks.rubber_log, LCCBlocks.rubber_wood, LCCBlocks.sappy_stripped_rubber_log, LCCBlocks.stripped_rubber_log, LCCBlocks.stripped_rubber_wood)
        recipeStore.addTagHandlerFilter(BlockTags.WOODEN_STAIRS) { it.asItem().identifier.path.endsWith("_stairs") && Registry.ITEM.containsId(Identifier(it.asItem().identifier.namespace, it.asItem().identifier.path.replace("_stairs", "_planks"))) }
        recipeStore.addTagHandlerFilter(BlockTags.WOODEN_SLABS) { it.asItem().identifier.path.endsWith("_slab") && Registry.ITEM.containsId(Identifier(it.asItem().identifier.namespace, it.asItem().identifier.path.replace("_slab", "_planks"))) }
        recipeStore.addTagHandlerList(LCCTags.deadwood_logs_i, LCCBlocks.deadwood_log, LCCBlocks.deadwood, LCCBlocks.stripped_deadwood_log, LCCBlocks.stripped_deadwood)
        recipeStore.addTagHandlerFilter(ItemTags.MUSIC_DISCS) { it.asItem().identifier.path.startsWith("music_disc_") }
        recipeStore.addTagHandlerFilter(LCCTags.gold_blocks) { it.asItem().identifier.path.endsWith("gold_block") }

        println("Setting knowledge ready for exporting.")
        LCCKnowledgeData.init()

        do {
            println("\u001B[35m${exporters.count()} database exports currently installed, enter connection URL or leave blank to finish:\u001B[0m")
            print("> ")
            val url = Scanner(System.`in`).nextLine().trim()
            if (url.isBlank()) break

            println("\u001B[35mEnter password for user 'lcc':\u001B[0m")
            print("> ")
            val password = Scanner(System.`in`).next().trim()

            val exporter = DatabaseKnowledgeExporter(Database.connect("jdbc:mysql://$url/lcc?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "lcc", password = password), this, LCCKnowledgeData.all.values, KnowledgeTranslator().addLangDataSource(lang["en_us"]!!).addI18nSource())
            exporters.add(exporter)
            install(exporter, 2500)
        } while (true)

        install(CommitData(path, Paths.get("../lcc-content/src/main/resources")) { CommitData.defaultExcluder(it, LCC.modid, "fabric", "minecraft") }, 99999)
    }

}