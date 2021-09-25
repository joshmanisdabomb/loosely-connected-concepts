package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.directory.LCCKnowledgeData
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleLinkBuilder.Companion.link
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.util.Identifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class LCCVersion(val modVersion: String, val mcVersion: String, val code: String, val group: LCCVersionGroup, val order: Short, val released: LocalDateTime, val title: String? = null) {

    YAM_1("Update 1", "1.7.2", "u1", LCCVersionGroup.YAM, 0, LocalDateTime.of(2015, 2, 15, 19, 9, 2)) {
        override val description = "First version uploaded, reports itself as Beta 1.3."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_1_1("Update 1.1", "1.7.2", "u1.1", LCCVersionGroup.YAM, 10, LocalDateTime.of(2015, 2, 15, 21, 14, 56)) {
        override val description = "Removed Mars and Mercury dimensions, replaced with 'Asmia' dimension(?)"

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_2("Update 2", "1.7.2", "u2", LCCVersionGroup.YAM, 100, LocalDateTime.of(2015, 2, 16, 17, 34, 32)) {
        override val description = "Added the tick mob to the wasteland, changed sounds of Amplislimes, spawn group size of Psycho Pig decreased."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_3("Update 3", "1.7.2", "u3", LCCVersionGroup.YAM, 200, LocalDateTime.of(2015, 2, 22, 19, 24, 51)) {
        override val description = "Changed nuke mechanics, wasteland spawn rate reduced, added psychomeat."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_3_1("Update 3.1", "1.7.2", "u3.1", LCCVersionGroup.YAM, 210, LocalDateTime.of(2015, 2, 22, 20, 41, 1)) {
        override val description = "Reworked nuclear missile model, plans to add more missile strikes."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_4("Update 4", "1.7.2", "u4", LCCVersionGroup.YAM, 300, LocalDateTime.of(2015, 2, 28, 11, 31, 57)) {
        override val description = "Pills almost work, Missile Launch Pads render properly with GUI, Oil Buckets."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> {
            map[LCCKnowledgeData.item_oil_bucket] = KnowledgeArticleTextFragmentBuilder("Introduced as Oil Bucket.")
            return map.toSortedMap()
        }
    },
    YAM_5("Update 5", "1.7.2", "u5", LCCVersionGroup.YAM, 400, LocalDateTime.of(2015, 3, 1, 20, 32, 8)) {
        override val description = "Adding computer functionality, new parts including motherboard."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_6("Update 6", "1.7.2", "u6", LCCVersionGroup.YAM, 500, LocalDateTime.of(2015, 3, 8, 10, 45, 50)) {
        override val description = "Updated graphics, hiatus on missiles and computers, new mobs in the works."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_7("Update 7", "1.7.2", "u7", LCCVersionGroup.YAM, 600, LocalDateTime.of(2015, 3, 27, 7, 55, 17)) {
        override val description = "Added Aerstone with different subtypes, added Sparkling Dragon, added rope blocks."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_8("Update 8", "1.7.2", "u8", LCCVersionGroup.YAM, 700, LocalDateTime.of(2015, 12, 19, 21, 10, 14)) {
        override val description = "Added more potion effects and pill effects, added luck stat, fixed infinite arrow bug with aerstone repeater."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_9("Update 9", "1.7.2", "u9", LCCVersionGroup.YAM, 800, LocalDateTime.of(2015, 12, 22, 16, 44, 20)) {
        override val description = "Added 2 mobs and bloodwood to the wasteland, new light affinity biome WIP, fly swatter & poop harvester, nod to kyle, hellfire. poison spikes."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_9_1("Update 9.1", "1.7.2", "u9.1", LCCVersionGroup.YAM, 810, LocalDateTime.of(2015, 12, 27, 18, 47, 50)) {
        override val description = "Quick nerf to hellfire."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_9_2("Update 9.2", "1.7.2", "u9.2", LCCVersionGroup.YAM, 820, LocalDateTime.of(2015, 12, 27, 21, 57, 40)) {
        override val description = "Texture change to light leaves, classic sounds fixed."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_10("Update 10", "1.7.2", "u10", LCCVersionGroup.YAM, 900, LocalDateTime.of(2016, 1, 10, 16, 44, 13)) {
        override val description = "Light aura now contains light stone right off the bat, custom saplings and leaves now work, mobs die and leave light shards in light aura."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_11("Update 11", "1.7.2", "u11", LCCVersionGroup.YAM, 1000, LocalDateTime.of(2016, 1, 23, 17, 14, 16)) {
        override val description = "Light biomes no longer emit light due to lighting issues, different spawn eggs for different types of mobs (no longer limited to rainbow spawn eggs), work on winged creeper."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_11_1("Update 11.1", "1.7.2", "u11.1", LCCVersionGroup.YAM, 1010, LocalDateTime.of(2016, 1, 23, 20, 19, 36)) {
        override val description = "Added aura conversion enum system."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    YAM_12("Update 12", "1.7.2", "u12", LCCVersionGroup.YAM, 1100, LocalDateTime.of(2016, 1, 24, 17, 48, 48)) {
        override val description = "Added more light blocks. Changed some light block textures."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_PREALPHA("Pre-Alpha", "1.10.2", "prealpha", LCCVersionGroup.AIMAGG, 0, LocalDateTime.of(2016, 10, 26, 22, 16, 51)) {
        override val description = "Added a creative tab with a custom sort system, and a directional facing test block. Starting work on a spreader constructor, which will allow you to build spreaders that infect the world."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_0_1("Alpha 0.1", "1.10.2", "a0.1", LCCVersionGroup.AIMAGG, 100, LocalDateTime.of(2016, 11, 9, 14, 44, 42)) {
        override val description = "Missile movement works, item and entity model for missiles."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_0_1_1("Alpha 0.1.1", "1.10.2", "a0.1.1", LCCVersionGroup.AIMAGG, 110, LocalDateTime.of(2016, 11, 19, 17, 51, 54)) {
        override val description = "Missile polish, trying to add particle effects. Spreader blocks suspended, work begun on Billie Blocks. Changed upgrade system to card system, launch pad now renders further away, nuclear explosions less laggy, fire missile model and nuclear waste model added."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_0_2("Alpha 0.2", "1.12", "a0.2", LCCVersionGroup.AIMAGG, 200, LocalDateTime.of(2017, 7, 26, 4, 35, 20)) {
        override val description = "My first successful port, for 1.12. Added pills and making custom models look nice in all situations."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_0_0("Alpha 1.0.0", "1.12", "a1.0.0", LCCVersionGroup.AIMAGG, 1000, LocalDateTime.of(2017, 7, 27, 3, 22, 2)) {
        override val description = "Everything renders correctly apart from launch pad."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_0_1("Alpha 1.0.1", "1.12", "a1.0.1", LCCVersionGroup.AIMAGG, 1010, LocalDateTime.of(2017, 7, 27, 7, 47, 37)) {
        override val description = """Added particles to modelled blocks and updated rotations in GUI.
No more modelling errors in logs.
Updated unrefined uranium texture."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_0_2("Alpha 1.0.2", "1.12", "a1.0.2", LCCVersionGroup.AIMAGG, 1020, LocalDateTime.of(2017, 7, 27, 10, 35, 33)) {
        override val description = """Missile launch pad now has shift click support.
Client and server communicate so missile can be seen.
Missile renders on client."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_1_0("Alpha 1.1.0", "1.12", "a1.1.0", LCCVersionGroup.AIMAGG, 1100, LocalDateTime.of(2017, 8, 2, 22, 21, 13), title = "All These Little Things") {
        override val description = """Added mud, quicksand, 2 types of hearts that render in-game, 3 types of heart consumables, crafting materials.
Added metadata sensitive block hardness, resistance, map colours, block sounds, harvest types and levels.
Added drop types and silk touch support.
Added enum support in favour of just knowing what the numbers mean.
Added shift click support for containers. They still need loads of work."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_1_1("Alpha 1.1.1", "1.12", "a1.1.1", LCCVersionGroup.AIMAGG, 1110, LocalDateTime.of(2017, 8, 2, 22, 26, 5)) {
        override val description = "Quick language file fix."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_2("Alpha 1.2", "1.12", "a1.2", LCCVersionGroup.AIMAGG, 1200, LocalDateTime.of(2017, 8, 31, 21, 30, 17), title = "Connected Textures") {
        override val description = """Overhauled sorting system, with categories, upper and lower sorting values.
Added different basic types of blocks, including connected texture blocks. (not finished yet)
Added pill effect GUI overlay and more pill effects work.
Starting work on rainbow dimension."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_3("Alpha 1.3", "1.12", "a1.3", LCCVersionGroup.AIMAGG, 1300, LocalDateTime.of(2017, 9, 11, 21, 49, 56), title = "Touch the Rainbow") {
        override val description = """Currently in process of adding different rainbow dimension blocks.
Officially added the rainbow dimension and have working world gen.
Added spike blocks.
Changed unlocalized name formats."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_3_1("Alpha 1.3.1", "1.12", "a1.3.1", LCCVersionGroup.AIMAGG, 1310, LocalDateTime.of(2017, 9, 12, 21, 20, 7)) {
        override val description = """Chocolate blocks given model and localised name.
Rainbow dimension has custom caves."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_3_2("Alpha 1.3.2", "1.12", "a1.3.2", LCCVersionGroup.AIMAGG, 1320, LocalDateTime.of(2017, 9, 12, 22, 15, 35)) {
        override val description = "More reasonable gemstone ore generation."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_4("Alpha 1.4", "1.12", "a1.4", LCCVersionGroup.AIMAGG, 1400, LocalDateTime.of(2017, 9, 22, 16, 16, 3), title = "The Block Update") {
        override val description = """Added illuminant tiles, scaffolding blocks, fortstone, candy canes, refined candy cane blocks, jelly.
Overhauled blockstates, block models and item models to forge's system, deleting 35+ model files."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_4_1("Alpha 1.4.1", "1.12", "a1.4.1", LCCVersionGroup.AIMAGG, 1410, LocalDateTime.of(2017, 9, 23, 2, 33, 31)) {
        override val description = """Overhauled spreader textures and models. Saved about 3 MB of space!
Starting work on neon ore."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_5("Alpha 1.5", "1.12.2", "a1.5", LCCVersionGroup.AIMAGG, 1500, LocalDateTime.of(2017, 10, 10, 14, 58, 8), title = "New Meets Old") {
        override val description = """Added gemstone and neon tools and armour, with recipes! The mod is starting to become more survival friendly.
Added rainbow core ore and lollipop stick.
Added classic grass, porkchops, and two sets of wool.
Started work on computing."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_6("Alpha 1.6", "1.12.2", "a1.6", LCCVersionGroup.AIMAGG, 1600, LocalDateTime.of(2017, 10, 23, 23, 1, 7), title = "Survival Spark") {
        override val description = """Began work on the wasteland biome. Added cracked mud for the biome.
Added bounce pads and spring items.
Added computer cases and computer cables.
Added classic blocks, including classic saplings and leaves.
Multiplayer servers now run and should continue to run as well!
Using predicates instead of comparing one block state to another.
Added loads of crafting recipes!
Changed text colours of item tooltips.
Updated textures of neon equipment."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    AIMAGG_ALPHA_1_6_1("Alpha 1.6.1", "1.12.2", "a1.6.1", LCCVersionGroup.AIMAGG, 1610, LocalDateTime.of(2017, 10, 25, 18, 30, 32)) {
        override val description = """Fixed connected texture blocks, not touching them again for at least seven years.
Updated textures for fortstone and rainbow pads."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_PREALPHA("Pre-Alpha", "1.13.2", "prealpha", LCCVersionGroup.LCC_FORGE, 0, LocalDateTime.of(2019, 2, 17, 20, 28, 13)) {
        override val description = "Pretty empty with nothing added."

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_ALPHA_0_1("Alpha 0.1", "1.13.2", "a0.1", LCCVersionGroup.LCC_FORGE, 100, LocalDateTime.of(2019, 2, 18, 19, 4, 1)) {
        override val description = """Registers blocks, itemblocks and items.
Language file.
Textures, models and blockstates working!
Implemented all test blocks apart from connected."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_ALPHA_0_2("Alpha 0.2", "1.13.2", "a0.2", LCCVersionGroup.LCC_FORGE, 200, LocalDateTime.of(2019, 2, 19, 21, 20, 23)) {
        override val description = """Implemented creative tab.
Resources textured and modelled.
Added entity loading and a nuclear explosion entity.
Updated all textures to fit 1.14 style. Added enriched uranium resource."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_ALPHA_0_3("Alpha 0.3", "1.13.2", "a0.3", LCCVersionGroup.LCC_FORGE, 300, LocalDateTime.of(2019, 2, 20, 21, 34, 13)) {
        override val description = """Implemented nuclear explosion semi-functionality and optimisation.
Added nuclear debris and fire to nuclear main explosion.
Generating ores in the world."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_ALPHA_0_4("Alpha 0.4", "1.14.3", "a0.4", LCCVersionGroup.LCC_FORGE, 400, LocalDateTime.of(2019, 7, 20, 0, 12, 9)) {
        override val description = """Initial refactoring for 1.14.3.
Added the gauntlet with arm rendering technology.
Added capability structure, added gauntlet functionality.
Added gauntlet UI overlay to show cooldowns and actions.
Added stun potion effect, not fully working yet.
Added gem modifiers to the gauntlet and implemented temporary hearts and structure for other hearts.
Added millions of item models for gem tools and armour.
Better way to sort and add empty space to item group.
Implemented heart items and containers. Sync heart data using packets and fixed heart using desync."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_ALPHA_0_5("Alpha 0.5", "1.14.4", "a0.5", LCCVersionGroup.LCC_FORGE, 500, LocalDateTime.of(2019, 8, 17, 1, 29, 29)) {
        override val description = """Updated to 1.14.4 and a ton of refactoring!
Added survival crafting recipes and loot tables.
Complete renaming files to 1.14 naming conventions.
Adding survival-based block properties.
Small changes to nuclear explosion and fire.
Gauntlet functionality now plays nice on multiplayer.
Added nuclear explosion model (needs work)
Added particle effect packet handler ready for gauntlet client effects.
Added particle effects and sounds to gauntlet.
Finished connected texture blocks.
Welcome spreader blocks and spreader interface to the mod. Spreader interface now change the world's global capability."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_ALPHA_1_0("Alpha 1.0", "1.14.4", "a1.0", LCCVersionGroup.LCC_FORGE, 1000, LocalDateTime.of(2019, 10, 4, 2, 14, 37)) {
        override val description = """Added basic multipart block interface, added cog dynamic model.
Added block networks for cogs and computers later.
Crying Obsidian test with respawning in different dimension.
Adding nostalgic items to the mod.
The glove finally fits.
Added the time rift, capable of nostalgic crafting.
Added road block and updated connected texture methods to support different states and block heights. Road blocks now speed up entities and boats.
Added bounce pads and updated working for multiplayer. Particles trigger in singleplayer. Code cleanup and spreader interface texture update.
Added hydrated soul sand and rewrote my tab sorting system from 3 years ago!"""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FORGE_BETA_1_0("Beta 1.0", "1.15.2", "b1.0", LCCVersionGroup.LCC_FORGE, 2000, LocalDateTime.of(2020, 5, 8, 12, 48, 31)) {
        override val description = """Beginning creation of computers. Added digital storage items.
Adding the wasteland biome. Added oil to wasteland biome. Oil interactions with other liquids.
Adding atomic bomb shell.
Added rainbow dimension. Added rainbow dimension biomes, rainbow grasses, sparkling dirt, twilight stone, candy canes. Added small and large candy canes.
Added Rainbow Gate. Added Rainbow Portal block and fixed rendering issues with rainbow gate.
Working on channelite. Star plating block and tag and loot table updates.
Added flammable and vulnerable potion effects.
Stun now locks mouse movement.
Fix to double classic chest and hopper interaction.
Added loot table, lang, block state, item model, tag, advancement data generator."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> {
            map[LCCKnowledgeData.item_oil_bucket] = KnowledgeArticleTextFragmentBuilder("Reintroduced.")
            return map.toSortedMap()
        }
    },
    LCC_FABRIC_0_1_0("0.1.0", "20w51a", "0.1.0", LCCVersionGroup.LCC_FABRIC, 0, LocalDateTime.of(2020, 12, 21, 1, 20, 11)) {
        override val description = """Added iron, crystal and temporary hearts.
Starting wasteland biome.
Added test blocks and cracked mud.
Added oil buckets.
Test block 5 connected texture.
Added tools and armour real quick like.
Implementing roads made from cooled asphalt.
Soaking soul sand with fizzy bubbles.
Added arcane table block, block entity, container and screen.
Added bounce pads.
Gauntlet work.
To allow running in real environments: No longer use reflection for thing directories.
Time rift functionality and ruby recipes."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> {
            map[LCCKnowledgeData.item_oil_bucket] = KnowledgeArticleTextFragmentBuilder("Reintroduced.")
            map[LCCKnowledgeData.item_asphalt_bucket] = KnowledgeArticleTextFragmentBuilder(introduced)
            return map.toSortedMap()
        }
    },
    LCC_FABRIC_0_1_1("0.1.1", "20w51a", "0.1.1", LCCVersionGroup.LCC_FABRIC, 10, LocalDateTime.of(2020, 12, 22, 20, 9, 35)) {
        override val description = """Infrastructure for item rendering. Time rift rendering.
Added topaz geodes naturally spawning in some lava lakes.
Reworked gem system, rubies and diamonds tier 3, emerald and sapphire tier 2, amethyst and topaz tier 1.
Retextured amethyst equipment and topaz shards.
Time rift model.
Added topaz and reworked gems into tiers."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_1_2("0.1.2", "20w51a", "0.1.2", LCCVersionGroup.LCC_FABRIC, 20, LocalDateTime.of(2020, 12, 24, 15, 20, 33)) {
        override val description = """Fixed topaz clusters not dropping crystals.
Added pumice and rhyolite to topaz geode casing.
Added textures and functionality to many classic blocks previously added."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_2_0("0.2.0", "20w51a", "0.2.0", LCCVersionGroup.LCC_FABRIC, 100, LocalDateTime.of(2021, 1, 2, 4, 21, 19)) {
        override val description = """Internally modularised the modification using the MD5 protocol.
Added classic chest, nether reactor, crying obsidian.
Fixed topaz geode casing not generating enough topaz."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_2_1("0.2.1", "20w51a", "0.2.1", LCCVersionGroup.LCC_FABRIC, 110, LocalDateTime.of(2021, 1, 3, 14, 35, 23)) {
        override val description = """Added classic foods and the quiver.
Fixed bug with spawning mixin for classic crying obsidian.
Time rift no longer instantly breaks.
Fixed language file and data generation issues.
Changed texture for rhyolite."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_2_2("0.2.2", "20w51a", "0.2.2", LCCVersionGroup.LCC_FABRIC, 120, LocalDateTime.of(2021, 1, 8, 13, 18, 29)) {
        override val description = """Added cogs, classic leather armors and classic fish raw and cooked.
Fixed server crash with client packets such as bounce pad extensions.
Fixed furnace recipes for smelting items."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_3_0("0.3.0", "20w51a", "0.3.0", LCCVersionGroup.LCC_FABRIC, 200, LocalDateTime.of(2021, 1, 13, 20, 30, 36)) {
        override val description = """Added refiner, power cables and solar panels.
Added heavy uranium."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> {
            map[LCCKnowledgeData.item_oil_bucket] = KnowledgeArticleTextFragmentBuilder("Can be inserted into a %s to generate 6 LE/t\nCan now be refined into %s.")
                .insertLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator).link)
                .insertLink(LCCItems.asphalt_bucket.name, KnowledgeArticleIdentifier.ofItem(LCCItems.asphalt_bucket).link)
            map[LCCKnowledgeData.item_asphalt_bucket] = KnowledgeArticleTextFragmentBuilder("Now craftable by refining %s.")
                .insertLink("oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
            return map.toSortedMap()
        }
    },
    LCC_FABRIC_0_3_1("0.3.1", "20w51a", "0.3.1", LCCVersionGroup.LCC_FABRIC, 210, LocalDateTime.of(2021, 1, 15, 0, 2, 42)) {
        override val description = """Fixed refiner numbers displaying incorrectly on multiplayer servers.
Quiver now stores picked up arrow items and projectiles.
Fixed space of bundle required by quiver.
Cogs now check if location is valid when surroundings change.
Cogs now drop correct amount of items when broken by the game.
Update to latest Fabric."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_3_2("0.3.2", "21w03a", "0.3.2", LCCVersionGroup.LCC_FABRIC, 220, LocalDateTime.of(2021, 1, 25, 18, 8, 51)) {
        override val description = """Added furnace and combustion generators.
Added silicon for solar panel recipe and geothermal power.
Added energy bank and batteries for storing power."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_4_0("0.4.0", "21w06a", "0.4.0", LCCVersionGroup.LCC_FABRIC, 300, LocalDateTime.of(2021, 2, 15, 13, 29, 2)) {
        override val description = """Added atomic bomb and radiation sickness.
Using nightly Cardinal Components."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_4_1("0.4.1", "21w06a", "0.4.1", LCCVersionGroup.LCC_FABRIC, 310, LocalDateTime.of(2021, 2, 16, 21, 25, 52)) {
        override val description = """Atomic bomb particles.
Achievement for atomic bomb.
Radiation increase when within epicenter of atomic bomb.
Atomic bombs and nuclear explosions now load chunks.
Fix for server crash on radiation increase."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_4_2("0.4.2", "21w08b", "0.4.2", LCCVersionGroup.LCC_FABRIC, 320, LocalDateTime.of(2021, 3, 5, 17, 16, 53)) {
        override val description = """Hazmat suit now blocks new status effects when oxygenated full set.
Hazmat helmet blocks eating food. Update to 21w08b and latest Kotlin.
Added oxygen tank and oxygen extractor. Hazmat suit recipes bar chestplate. LCC Base can display multiple bars for items.
Adding rubber wood, rubber tree blocks and treetap and latex gathering.
Migrated heart trackers to components. Ready to migrate from gauntlet trackers to components.
Rewriting gauntlet functionality for components, uppercut done - punch next.
Blocks, items and nullable blockitems now working with new directory system.
Race advancement for nuclear detonation. Fixed bug with nuclear explosion log not displaying name of player."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_4_3("0.4.3", "21w11a", "0.4.3", LCCVersionGroup.LCC_FABRIC, 330, LocalDateTime.of(2021, 3, 29, 1, 30, 5)) {
        override val description = """Added nuclear power generation and heavy uranium shielding to protect against nuclear explosions.
Added radiation and contained armour HUD elements.
Added graphics and tooltip to oxygen extractor.
Hazmat suits expend more oxygen when player is active and protects against more hazards.
Added kiln block for quicker smelting of non-blastables and non-smokables.
Added slabs and stairs for rhyolite and classic blocks.
Nerfed latex and solar power production.
Added custom heart type damage sounds and fixed custom heart type damage overlay.
Updated to 21w11a."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_4_4("0.4.4", "21w14a", "0.4.4", LCCVersionGroup.LCC_FABRIC, 340, LocalDateTime.of(2021, 4, 29, 15, 37, 7)) {
        override val description = """Added salt to gauge block temperatures and to throw at mobs and players.
Added tungsten ore in wasteland, raw tungsten item and block, tungsten ingot and block, cut tungsten, stairs and slabs.
Added radar and alarm.
Added radiation detector to detect nuclear winter level, nearby uranium and other radioactive sources.
Added rubber sign and rubber boat.
Added rubber block.
Nuclear generators now spawn nuclear waste below themselves that must be handled by the player.
Nuclear waste now piles up by horizontally sliding when landing.
Uranium ore now almost exclusively spawns exposed to air.
Added radiation and nuclear winter command.
Nuclear winters now spawn more mobs with effects per level, and causes weather issues.
Added advancements.
Fixed bug with radiation not healing and large amounts not carrying over to the next life.
Update to 21w14a."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> = map
    },
    LCC_FABRIC_0_5_0("0.5.0", "21w14a", "0.5.0", LCCVersionGroup.LCC_FABRIC, 400, LocalDateTime.of(2021, 8, 19, 13, 52, 40)) {
        override val description = """Added sapphire altar block, structure, brick blocks with stair, slab and wall variants.
Added iron oxide nuggets used for making challenge keys.
Added dull sapphires exclusively for charging into regular sapphires.
Added structure rotation and mirror support for directional blocks.
Added altar challenge registry for loading challenges. Added minesweeper altar challenge. Added bomb board blocks.
Added crowbar that can stun on critical hits.
Deadwood logs now spawn in the wasteland.
Fixed oil spawning in exposed locations, now grouped in clusters scattered around the wasteland.
Added tent structures to wasteland with random loot.
Added shattered glass which crumbles under impact.
Spike traps now spawn in the wasteland barrens.
Landmines now spawn in the wasteland.
Added consumers that fire their tongues at entities.
Oil refining now provides tar for asphalt, refined oil buckets for plastic, and fuel for explosive paste (currently).
Products from distillation can also be stepped up with cracking recipes.
Plastic which gets vibrant colour from dyes in refining recipe, doesn\'t despawn.
Added plastic bag to store 128 items without despawning.
Can now define complex refining recipes.
Added rubber piston block and textures.
Added explosive paste which allows chain reaction explosions.
Fixed bug with custom recipes only giving one output for all recipes.
Added rusted iron blocks to wasteland. Added rusted iron bars.
Added polished fortstone with connected texture.
Added fortstone, cobbled fortstone and generation.
Separated wasteland spikes and barrens biomes.
Added baby skeleton entity.
Added wasp entity, renderer and model.
Added deposit blocks for wasteland.
Added wasteland spawn eggs for baby skeleton, wasp and consumer.
Split lcc-hooks into separate module.
Content datagen now launching and matches 0.4.4 datagen."""

        override fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder> {
            map[LCCKnowledgeData.block_sapphire_altar] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.block_explosive_paste] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.item_rigid_plastic] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.item_flexible_plastic] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.item_plastic_bag] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.item_oil_bucket] = KnowledgeArticleTextFragmentBuilder("Name changed to Crude Oil Bucket.\nNo longer directly refines into %s. Now refines into %s, %s and %s; this recipe requires 2 buckets of oil.\nNow only generates 2 LE/t in a %s.")
                .insertLink(LCCItems.asphalt_bucket.name, KnowledgeArticleIdentifier.ofItem(LCCItems.asphalt_bucket).link)
                .insertLink(LCCItems.fuel_bucket.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fuel_bucket).link)
                .insertLink(LCCItems.refined_oil_bucket.name, KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket).link)
                .insertLink(LCCItems.tar_ball.name, KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball).link)
                .insert(LCCBlocks.oil_generator.name)
            map[LCCKnowledgeData.item_tar_ball] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.item_asphalt_bucket] = KnowledgeArticleTextFragmentBuilder("Recipe now requires %s instead of using %s directly.")
                .insertLink(LCCItems.tar_ball.name, KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball).link)
                .insertLink("crude oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
            map[LCCKnowledgeData.item_fuel_bucket] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.item_refined_oil_bucket] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.block_improvised_explosive] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.block_deadwood_log] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.block_stripped_deadwood_log] = KnowledgeArticleTextFragmentBuilder(introduced)
            map[LCCKnowledgeData.block_deadwood_planks] = KnowledgeArticleTextFragmentBuilder(introduced)
            return map.toSortedMap()
        }
    };

    abstract val description: String

    val changelog by lazy { generateChangelog(mutableMapOf()) }
    abstract fun generateChangelog(map: MutableMap<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>): Map<KnowledgeArticleBuilder, KnowledgeArticleFragmentBuilder>

    val shortname get() = (group.shortname?.plus(" ") ?: "").plus(modVersion)
    val page = KnowledgeArticleIdentifier(LCC.id("versions"), Identifier(group.namespace, code))

    fun getIntroduction(): String {
        val sentence1 = "%s is the %s private release of Loosely Connected Concepts for Fabric, and the %s build of this mod under all its names. It was released on " + released.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, y")) + " at " + released.format(DateTimeFormatter.ofPattern("h:mm:ss a")) + "."
        var sentence2: String? = null
        if (ordinal > 1) {
            sentence2 = " The previous release was %s"
        }
        if (ordinal < values().lastIndex) {
            sentence2 = (sentence2?.plus(", while the following release is ") ?: " The following release is ").plus("%s.")
        } else {
            sentence2 = sentence2?.plus(".")
        }
        return sentence1 + (sentence2 ?: "")
    }

    companion object {
        private val introduced = "Introduced."
    }

}
