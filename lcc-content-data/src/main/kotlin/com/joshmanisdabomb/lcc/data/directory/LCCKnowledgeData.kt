package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.challenges.LCCAltarChallenges
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTableFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleChangelogSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleVersionChangelogSectionBuilder
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import com.joshmanisdabomb.lcc.directory.*
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.item.Items
import net.minecraft.util.registry.BuiltinRegistries

object LCCKnowledgeData: BasicDirectory<KnowledgeArticleBuilder, Unit>() {

    val block_sapphire_altar by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.sapphire_altar)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that allows players to interface with the challenge of a %s. It breaks into %s, even with the Silk Touch enchantment, making this block obtainable only in Creative Mode.")
                    .insert(LCCBlocks.sapphire_altar.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page)
                    .insertLink("Sapphire Altar (Structure)", KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar))
                    .insertLink(LCCBlocks.sapphire_altar_brick.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar_brick))
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Usage")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Every altar block spawns with exactly one %s already placed in the middle slot. Players may sacrifice more sapphires or %s into the other four empty slots. Sapphires cannot be retrieved once placed in the altar.")
                    .insertLink("sapphire", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire))
                    .insertLink("dull sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.dull_sapphire))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("An %s must then be used on the block to start the challenge. If the structure of the altar is malformed or has been tampered with before the key is used, a notification will be displayed and the challenge will not start.")
                    .insertLink(LCCItems.altar_challenge_key.name, KnowledgeArticleIdentifier.ofItem(LCCItems.altar_challenge_key))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("If the challenge is beaten successfully, the altar block breaks and drops its contents. Regular sapphires placed in the altar slots are doubled when dropped, while dull sapphires are converted into regular sapphires 1 to 1. For example, if the challenge was beaten with the sapphire that initially spawned in the middle slot, the sapphire altar explodes into 2 sapphires.")
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("If the challenge is failed, the altar block breaks and drops nothing. All sapphires and dull sapphires placed in the altar are lost. Additionally, if the challenge is failed due to being tampered with (e.g. the minesweeper board is broken to reveal mine locations) then the sapphire altar explodes violently.")
                )
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
    }

    val lcc_fabric_0_5_0 by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCVersion.LCC_FABRIC_0_5_0.page, "LCC 0.5.0")
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder(LCCVersion.LCC_FABRIC_0_5_0.getIntroduction())
                    .insert("Loosely Connected Concepts 0.5.0")
                    .insert("fifteenth")
                    .insert("58th")
                    .insertLink("LCC 0.4.4", LCCVersion.LCC_FABRIC_0_4_4.page)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("The focus of LCC 0.5.x is the %s biome, a hostile and dismal expanse filled with vicious traps and difficult mobs.")
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")))
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Gameplay")
                .addFragment(KnowledgeArticleTextFragmentBuilder("In this version, the Wasteland biome is split into two sub-biomes, %s and %s. The barrens are flat terrain where traps, resources and larger quantities of mobs are commonly found. The spiked regions are generally safer, but provide less rewards and are more challenging to navigate.")
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens))
                    .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Some blocks native to the Wasteland require Wasteland tools to mine, and most mobs native to the Wasteland deal increased damage through non-Wasteland armor and take reduced damage from non-Wasteland equipment. The tool progression starts at %s, a new wood type which can be found in clusters scattered across the barrens. A %s is required to mine %s which appears on the surface in the spikes sub-biome. Players must then convert their blocks of iron into %s by submerging them in water in any Wasteland sub-biome. When the iron has completely rusted, it can be mined with a %s and crafted into tools, armour and %s. These keys must be used to activate challenges posed by %s structures to obtain %s, the current final tier of Wasteland equipment.")
                    .insertLink(LCCBlocks.deadwood.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log))
                    .insertLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe))
                    .insertLink(LCCBlocks.fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.fortstone))
                    .insertLink("rusted iron blocks", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()))
                    .insertLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe))
                    .insertLink("Altar Challenge Keys", KnowledgeArticleIdentifier.ofItem(LCCItems.altar_challenge_key))
                    .insertLink("Sapphire Altar", KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar))
                    .insertLink("sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Players can also find treasure in the Wasteland. %s are structures that spawn in the barrens which often contain loot. Obtained items can range from sticks to %s, which can permanently increase your maximum life. '%s' can also be found in the barrens, grouped in clusters. Breaking these will sometimes drop gold pieces, iron pieces or the aforementioned heart containers.")
                    .insertLink("Tents", KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.wasteland_tent))
                    .insertLink("heart containers", KnowledgeArticleIdentifier(LCC.id("concepts"), LCC.id("heart_containers")))
                    .insertLink("Deposits", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deposit))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("The Wasteland is now home to three dangerous mobs. The %s is a baby version of a regular Minecraft skeleton that strafes and shoots faster and has a much smaller hitbox. %s are creatures that extends its tongue out to latch on to its prey. Finally, %s nests spawn in all parts of the Wasteland. Wasps are generally neutral but will vehemently defend their nests from perceived threats and can get randomly aggressive at nearby targets.")
                    .insertLink(LCCEntities.baby_skeleton.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.baby_skeleton))
                    .insertLink("Consumers", KnowledgeArticleIdentifier.ofEntity(LCCEntities.consumer))
                    .insertLink(LCCEntities.wasp.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.wasp))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s traps and landmines hooked up to camouflaged %s are often placed in Wasteland Barrens. Spikes can sometimes spawn covered in %s or %s residue. Landmines are rigged up with an %s, which leaves behind a large firey crater.")
                    .insertLink("Spike", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.spikes))
                    .insertLink("pressure plates", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud_pressure_plate))
                    .insertLink("bloody", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.bleeding_spikes))
                    .insertLink("poisonous", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.poison_spikes))
                    .insertLink(LCCBlocks.improvised_explosive.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.improvised_explosive))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Oil refining has also been modified in this version. Oil, which now spawns more commonly in the wasteland as geysers or in pockets buried under %s, now refines into three products: %s, %s and %s. Fuel can be converted to energy using a %s, or refined further into %s which can be placed to cause chain reactions of explosions, create improvised explosives (landmines) or to %s. Refined oil can be further refined into %s or %s, both of which can have its color customised with dyes and do not despawn when dropped. Flexible plastic can be crafted into %s, which hold 2 stacks of items. Tar balls are used to create %s.")
                    .insertLink("cracked mud", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud))
                    .insertLink("Fuel Buckets", KnowledgeArticleIdentifier.ofItem(LCCItems.fuel_bucket))
                    .insertLink("Refined Oil Buckets", KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket))
                    .insertLink("tar balls", KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball))
                    .insertLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator))
                    .insertLink(LCCBlocks.explosive_paste.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.explosive_paste))
                    .insertLink("construct a game of Minesweeper", KnowledgeArticleIdentifier.of(LCCRegistries.altar_challenges, LCCAltarChallenges.minesweeper))
                    .insertLink(LCCItems.rigid_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic))
                    .insertLink(LCCItems.flexible_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic))
                    .insertLink("plastic bags", KnowledgeArticleIdentifier.ofItem(LCCItems.plastic_bag))
                    .insertLink("roads", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.road))
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Technical")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Similar to update %s, a lot of technical changes happened behind the scenes in addition to updated gameplay changes. The lcc-base module was split into lcc-infra and lcc-hooks. The infrastructure module provides helpers to other modules without making any changes to Minecraft with mixins and access transformers. In contrast, the hooks module makes those kind of changes and gives other modules access to interact with these hooks, using methods like trait interfaces or accessor mixins.")
                    .insertLink("0.2.0", LCCVersion.LCC_FABRIC_0_2_0.page)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("The data generator module was also split up to separate code specific to the content of LCC. This allows other mods in future to use lcc-data as a module to generate data for themselves.")
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Commit Messages from BitBucket")
                .addFragment(KnowledgeArticleTextFragmentBuilder(LCCVersion.LCC_FABRIC_0_5_0.description))
            )
            .addSection(KnowledgeArticleVersionChangelogSectionBuilder(LCCVersion.LCC_FABRIC_0_5_0))
    }

    val block_explosive_paste by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.explosive_paste)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that explodes shortly after being triggered by another explosion. It connects to other pieces of %s placed nearby, similar to %s.")
                    .insert(LCCBlocks.explosive_paste.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page)
                    .insert(LCCBlocks.explosive_paste.name)
                    .insert(Items.REDSTONE.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Strength")
                .addFragment(KnowledgeArticleTextFragmentBuilder("The strength of the explosion caused by a piece of %s depends on the amount of connections to other adjacent pieces of %s.")
                    .insert(LCCBlocks.explosive_paste.name)
                    .insert(LCCBlocks.explosive_paste.name)
                )
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Adjacent Connections"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Strength"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Equivalent To"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("0"))
                        addCell(KnowledgeArticleTextFragmentBuilder("2.5"))
                        addCell(KnowledgeArticleTextFragmentBuilder("Ghast Fireball"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("1"))
                        addCell(KnowledgeArticleTextFragmentBuilder("3.4"))
                        addCell(KnowledgeArticleTextFragmentBuilder("Creeper"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("4.3"))
                        addCell(KnowledgeArticleTextFragmentBuilder("TNT"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("3"))
                        addCell(KnowledgeArticleTextFragmentBuilder("5.2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("Bed"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("4"))
                        addCell(KnowledgeArticleTextFragmentBuilder("6.1"))
                        addCell(KnowledgeArticleTextFragmentBuilder("Charged Creeper"))
                    }
                )
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
    }

    private val introduction = "Introduction"

    fun initialiser(input: KnowledgeArticleBuilder, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out KnowledgeArticleBuilder, out KnowledgeArticleBuilder>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.afterInit() }
    }

    override fun defaultProperties(name: String) = Unit

}
