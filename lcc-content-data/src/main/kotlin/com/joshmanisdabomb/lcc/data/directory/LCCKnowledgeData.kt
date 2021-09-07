package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleChangelogSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCStructureFeatures
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.text.LiteralText

object LCCKnowledgeData: BasicDirectory<KnowledgeArticleBuilder, Unit>() {

    val block_sapphire_altar by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.sapphire_altar)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that allows players to interface with the challenge of a %s. It breaks into %s, even with the Silk Touch enchantment, making this block obtainable only in Creative Mode.")
                    .insert(LCCBlocks.sapphire_altar.name)
                    .insertLink(LiteralText("LCC 0.5.0"), LCCVersion.LCC_FABRIC_0_5_0.page)
                    .insertLink(LiteralText("Sapphire Altar (Structure)"), KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar))
                    .insertLink(LCCBlocks.sapphire_altar_brick.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar_brick))
                )
            )
            .addSection(KnowledgeArticleSectionBuilder(LiteralText("Usage"))
                .addFragment(KnowledgeArticleTextFragmentBuilder("Every altar block spawns with exactly one %s already placed in the middle slot. Players may sacrifice more sapphires or %s into the other four empty slots. Sapphires cannot be retrieved once placed in the altar.")
                    .insertLink(LiteralText("sapphire"), KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire))
                    .insertLink(LiteralText("dull sapphires"), KnowledgeArticleIdentifier.ofItem(LCCItems.dull_sapphire))
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

    private val introduction = LiteralText("Introduction")

    fun initialiser(input: KnowledgeArticleBuilder, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out KnowledgeArticleBuilder, out KnowledgeArticleBuilder>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.afterInit() }
    }

    override fun defaultProperties(name: String) = Unit

}
