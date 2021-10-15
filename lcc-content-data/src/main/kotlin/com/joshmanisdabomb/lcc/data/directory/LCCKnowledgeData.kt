package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.challenges.LCCAltarChallenges
import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.abstracts.types.IronRustType
import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.*
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleLinkBuilder.Companion.link
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleWebLinkBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.*
import com.joshmanisdabomb.lcc.data.json.recipe.OverrideRecipeJsonProvider
import com.joshmanisdabomb.lcc.data.json.recipe.RefiningShapelessRecipeJsonFactory
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import com.joshmanisdabomb.lcc.directory.*
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import com.joshmanisdabomb.lcc.recipe.refining.special.PolymerRefiningRecipe
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EntityType
import net.minecraft.item.*
import net.minecraft.recipe.Ingredient
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry

object LCCKnowledgeData : BasicDirectory<KnowledgeArticleBuilder, Unit>() {

    val block_sapphire_altar by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.sapphire_altar)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that allows players to interface with the challenge of a %s. It breaks into %s, even with the Silk Touch enchantment, making this block obtainable only in Creative Mode.")
                    .insert(LCCBlocks.sapphire_altar.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Sapphire Altar (Structure)", KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar).link)
                    .insertLink(LCCBlocks.sapphire_altar_brick.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar_brick).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Usage")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Every altar block spawns with exactly one %s already placed in the middle slot. Players may sacrifice more sapphires or %s into the other four empty slots. Sapphires cannot be retrieved once placed in the altar.")
                    .insertLink("sapphire", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire).link)
                    .insertLink("dull sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.dull_sapphire).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("An %s must then be used on the block to start the challenge. If the structure of the altar is malformed or has been tampered with before the key is used, a notification will be displayed and the challenge will not start.")
                    .insertLink(LCCItems.altar_challenge_key.name, KnowledgeArticleIdentifier.ofItem(LCCItems.altar_challenge_key).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("If the challenge is beaten successfully, the altar block breaks and drops its contents. Regular sapphires placed in the altar slots are doubled when dropped, while dull sapphires are converted into regular sapphires 1 to 1. For example, if the challenge was beaten with the sapphire that initially spawned in the middle slot, the sapphire altar explodes into 2 sapphires.")
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("If the challenge is failed, the altar block breaks and drops nothing. All sapphires and dull sapphires placed in the altar are lost. Additionally, if the challenge is failed due to being tampered with (e.g. the minesweeper board is broken to reveal mine locations) then the sapphire altar explodes violently.")
                )
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland")
    }

    val lcc_fabric_0_5_0 by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCVersion.LCC_FABRIC_0_5_0.page, "LCC 0.5.0")
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder(LCCVersion.LCC_FABRIC_0_5_0.getIntroduction())
                    .insert("Loosely Connected Concepts 0.5.0")
                    .insert("fifteenth")
                    .insert("58th")
                    .insertLink("LCC 0.4.4", LCCVersion.LCC_FABRIC_0_4_4.page.link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("The focus of LCC 0.5.x is the %s biome, a hostile and dismal expanse filled with vicious traps and difficult mobs.")
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Gameplay")
                .addFragment(KnowledgeArticleTextFragmentBuilder("In this version, the Wasteland biome is split into two sub-biomes, %s and %s. The barrens are flat terrain where traps, resources and larger quantities of mobs are commonly found. The spiked regions are generally safer, but provide less rewards and are more challenging to navigate.")
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Some blocks native to the Wasteland require Wasteland tools to mine, and most mobs native to the Wasteland deal increased damage through non-Wasteland %s and take reduced damage from non-Wasteland equipment. The tool progression starts at %s, a new wood type which can be found in clusters scattered across the barrens. A %s is required to mine %s which appears on the surface in the spikes sub-biome. Players must then convert their blocks of iron into %s by submerging them in water in any Wasteland sub-biome. When the iron has completely rusted, it can be mined with a %s and crafted into tools, armour and %s. These keys must be used to activate challenges posed by %s structures to obtain %s, the current final tier of Wasteland equipment.")
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                    .insertLink(LCCBlocks.deadwood.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                    .insertLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe).link)
                    .insertLink(LCCBlocks.fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.fortstone).link)
                    .insertLink("rusted iron blocks", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()).link)
                    .insertLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe).link)
                    .insertLink("Altar Challenge Keys", KnowledgeArticleIdentifier.ofItem(LCCItems.altar_challenge_key).link)
                    .insertLink("Sapphire Altar", KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar).link)
                    .insertLink("sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Players can also find treasure in the Wasteland. %s are structures that spawn in the barrens which often contain loot. Obtained items can range from sticks to %s, which can permanently increase your maximum life. '%s' can also be found in the barrens, grouped in clusters. Breaking these will sometimes drop gold pieces, iron pieces or the aforementioned heart containers.")
                    .insertLink("Tents", KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.wasteland_tent).link)
                    .insertLink("heart containers", KnowledgeArticleIdentifier(LCC.id("concepts"), LCC.id("heart_containers")).link)
                    .insertLink("Deposits", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deposit).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("The Wasteland is now home to three dangerous mobs. The %s is a baby version of a regular Minecraft skeleton that strafes and shoots faster and has a much smaller hitbox. %s are creatures that extends its tongue out to latch on to its prey. Finally, %s nests spawn in all parts of the Wasteland. Wasps are generally neutral but will vehemently defend their nests from perceived threats and can get randomly aggressive at nearby targets.")
                    .insertLink(LCCEntities.baby_skeleton.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.baby_skeleton).link)
                    .insertLink("Consumers", KnowledgeArticleIdentifier.ofEntity(LCCEntities.consumer).link)
                    .insertLink(LCCEntities.wasp.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.wasp).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s traps and landmines hooked up to camouflaged %s are often placed in Wasteland Barrens. Spikes can sometimes spawn covered in %s or %s residue. Landmines are rigged up with an %s, which leaves behind a large firey crater.")
                    .insertLink("Spike", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.spikes).link)
                    .insertLink("pressure plates", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud_pressure_plate).link)
                    .insertLink("bloody", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.bleeding_spikes).link)
                    .insertLink("poisonous", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.poison_spikes).link)
                    .insertLink(LCCBlocks.improvised_explosive.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.improvised_explosive).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Oil refining has also been modified in this version. Oil, which now spawns more commonly in the wasteland as geysers or in pockets buried under %s, now refines into three products: %s, %s and %s. Fuel can be converted to energy using a %s, or refined further into %s which can be placed to cause chain reactions of explosions, create improvised explosives (landmines) or to %s. Refined oil can be further refined into %s or %s, both of which can have its %s customised with dyes and do not despawn when dropped. Flexible plastic can be crafted into %s, which hold 2 stacks of items. Tar balls are used to create %s.")
                    .insertLink("cracked mud", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                    .insertLink("Fuel Buckets", KnowledgeArticleIdentifier.ofItem(LCCItems.fuel_bucket).link)
                    .insertLink("Refined Oil Buckets", KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket).link)
                    .insertLink("tar balls", KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball).link)
                    .insertLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator).link)
                    .insertLink(LCCBlocks.explosive_paste.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.explosive_paste).link)
                    .insertLink("construct a game of Minesweeper", KnowledgeArticleIdentifier.of(LCCRegistries.altar_challenges, LCCAltarChallenges.minesweeper).link)
                    .insertLink(LCCItems.rigid_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic).link)
                    .insertLink(LCCItems.flexible_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    .insertLink("plastic bags", KnowledgeArticleIdentifier.ofItem(LCCItems.plastic_bag).link)
                    .insertLink("roads", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.road).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Technical")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Similar to update %s, a lot of technical changes happened behind the scenes in addition to updated gameplay changes. The lcc-base module was split into lcc-infra and lcc-hooks. The infrastructure module provides helpers to other modules without making any changes to Minecraft with mixins and access transformers. In contrast, the hooks module makes those kind of changes and gives other modules access to interact with these hooks, using methods like trait interfaces or accessor mixins.")
                    .insertLink("0.2.0", LCCVersion.LCC_FABRIC_0_2_0.page.link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("The data generator module was also split up to separate code specific to the content of LCC. This allows other mods in future to use lcc-data as a module to generate data for themselves.")
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Commit Messages from BitBucket")
                .addFragment(KnowledgeArticleTextFragmentBuilder(LCCVersion.LCC_FABRIC_0_5_0.description))
            )
            .addSection(KnowledgeArticleVersionChangelogSectionBuilder(LCCVersion.LCC_FABRIC_0_5_0))
            .tags("LCC")
    }

    val block_explosive_paste by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.explosive_paste)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that explodes shortly after being triggered by another explosion. It connects to other pieces of %s placed nearby, similar to %s.")
                    .insert(LCCBlocks.explosive_paste.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insert(LCCBlocks.explosive_paste.name)
                    .insertLink(Items.REDSTONE.name, KnowledgeArticleIdentifier.ofItem(Items.REDSTONE).link)
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
                        addCell(KnowledgeArticleTextFragmentBuilder("%s").insertLink("Ghast Fireball", { (it.linker.generateLink(KnowledgeArticleIdentifier.ofEntity(EntityType.GHAST)) as KnowledgeArticleWebLinkBuilder).target("Ghast_fireball") }))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("1"))
                        addCell(KnowledgeArticleTextFragmentBuilder("3.4"))
                        addCell(KnowledgeArticleTextFragmentBuilder("%s").insertLink(TranslatableText(EntityType.CREEPER.translationKey), KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER).link))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("4.3"))
                        addCell(KnowledgeArticleTextFragmentBuilder("%s").insertLink(Blocks.TNT.name, KnowledgeArticleIdentifier.ofBlock(Blocks.TNT).link))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("3"))
                        addCell(KnowledgeArticleTextFragmentBuilder("5.2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("%s/%s").insertLink("Bed", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/bed" } }).insertLink(Blocks.RESPAWN_ANCHOR.name, KnowledgeArticleIdentifier.ofBlock(Blocks.RESPAWN_ANCHOR).link))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("4"))
                        addCell(KnowledgeArticleTextFragmentBuilder("6.1"))
                        addCell(KnowledgeArticleTextFragmentBuilder("%s").insertLink("Charged Creeper", { (it.linker.generateLink(KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER)) as KnowledgeArticleWebLinkBuilder).target("Charged_creeper") }))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCBlocks.explosive_paste) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.explosive_paste) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Materials", "Explosives")
    }

    val item_rigid_plastic by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.rigid_plastic)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an item introduced in %s as a crafting material for plastic items. It currently has no uses, but is planned to be a key crafting ingredient for computers in the future. It can be obtained by refining %s, similarly to %s.")
                    .insert(LCCItems.rigid_plastic.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("refined oil", KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket).link)
                    .insertLink(LCCItems.flexible_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                )
            )
            .apply { generatePlasticArticle(this, LCCItems.rigid_plastic, Items.QUARTZ) }
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Materials", "Plastic")
    }

    val item_flexible_plastic by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.flexible_plastic)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an item introduced in %s as a crafting material for plastic items such as the %s. It can be obtained by refining %s, similarly to %s.")
                    .insert(LCCItems.flexible_plastic.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(LCCItems.plastic_bag.name, KnowledgeArticleIdentifier.ofItem(LCCItems.plastic_bag).link)
                    .insertLink("refined oil", KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket).link)
                    .insertLink(LCCItems.rigid_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic).link)
                )
            )
            .apply { generatePlasticArticle(this, LCCItems.flexible_plastic, Items.BONE_MEAL) }
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Materials", "Plastic")
    }

    val item_plastic_bag by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.plastic_bag)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an item introduced in %s that functions similarly to the vanilla %s, but stores 2 stacks of items instead of only one. It is crafted with %s.")
                    .insert(LCCItems.plastic_bag.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Items.BUNDLE.name, KnowledgeArticleIdentifier.ofItem(Items.BUNDLE).link)
                    .insertLink(LCCItems.flexible_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                )
            )
            .addSection(generatePlasticDespawningSection(LCCItems.plastic_bag))
            .addSection(KnowledgeArticleSectionBuilder({ IncludedTranslatableText(it).translation("Coloring", "en_us").translation("Colouring", "en_gb") })
                .addFragment(KnowledgeArticleTextFragmentBuilder("The %s of the %s used to craft the plastic bag determines its final %s. Multiple %s of plastic can be used and the resulting bag will be a mix (multiplication) of the provided %s.")
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    .insert(LCCItems.flexible_plastic.name)
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCItems.plastic_bag) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCItems.plastic_bag) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Tools", "Bags", "Plastic")
    }

    val item_oil_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.oil_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an item originally introduced in %s and reintroduced in %s. This %s contains %s which is found in the %s biome in the form of either geysers spewing from the surface, or as pockets hidden slightly underneath %s.")
                    .insert(LCCItems.oil_bucket.name)
                    .insertLink("YAM Update 4", LCCVersion.YAM_4.page.link)
                    .insertLink("LCC 0.1.0", LCCVersion.LCC_FABRIC_0_1_0.page.link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink("oil", KnowledgeArticleIdentifier.ofFluid(LCCFluids.oil_still).link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink("cracked mud", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Oil is a key component of this mod, allowing the player to use a %s to craft %s, %s and %s from %s, and %s from %s.")
                    .insertLink(LCCBlocks.refiner.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.refiner).link)
                    .insertLink("buckets of fuel", KnowledgeArticleIdentifier.ofItem(LCCItems.fuel_bucket).link)
                    .insertLink("flexible", KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                    .insertLink("rigid plastic", KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic).link)
                    .insertLink("refined oil buckets", KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket).link)
                    .insertLink("asphalt", KnowledgeArticleIdentifier.ofItem(LCCItems.asphalt_bucket).link)
                    .insertLink("tar", KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Power")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Buckets of oil can be placed in a %s to yield 2 LE/t over 600 seconds. It is much more efficient to first refine crude oil into fuel, which provides 14 LE/t over 200 seconds in the generator.")
                    .insertLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCItems.oil_bucket) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCItems.oil_bucket) })
                .addFragment(legacyOilRecipe())
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials", "Bucket", "Wasteland")
    }

    val item_tar_ball by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.tar_ball)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a crafting material introduced in %s which can be used to create %s which eventually hardens into %s. It is one of the three products obtained by refining %s.")
                    .insert(LCCItems.tar_ball.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("asphalt", KnowledgeArticleIdentifier.ofItem(LCCItems.asphalt_bucket).link)
                    .insertLink("road blocks", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.road).link)
                    .insertLink("crude oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCItems.tar_ball) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCItems.tar_ball) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials")
    }

    val item_asphalt_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.asphalt_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an item introduced in %s but first made obtainable in %s. This %s contains %s which can be placed in the world. It is obtained by refining %s.")
                    .insert(LCCItems.asphalt_bucket.name)
                    .insertLink("LCC 0.1.0", LCCVersion.LCC_FABRIC_0_1_0.page.link)
                    .insertLink("LCC 0.3.0", LCCVersion.LCC_FABRIC_0_3_0.page.link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink("asphalt", KnowledgeArticleIdentifier.ofFluid(LCCFluids.asphalt_still).link)
                    .insertLink("tar", KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCItems.asphalt_bucket) })
                .addFragment(legacyOilRecipe())
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCItems.asphalt_bucket) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials")
    }

    val item_fuel_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.fuel_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an item introduced in %s. It is one of the three products obtained by refining %s. The contents of this %s represents a crafting material that cannot be placed in the world. Fuel can be used to generate power or to craft %s.")
                    .insert(LCCItems.fuel_bucket.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("crude oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink(LCCBlocks.explosive_paste.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.explosive_paste).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Power")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Buckets of fuel can be placed in a %s to yield 14 LE/t over 200 seconds. It is much more efficient to use fuel than using crude oil directly, which provides 2 LE/t over 600 seconds in the generator.")
                    .insertLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCItems.fuel_bucket) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCItems.fuel_bucket) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials", "Power")
    }

    val item_refined_oil_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.refined_oil_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an item introduced in %s. It is one of the three products obtained by refining %s. The contents of this %s represents a crafting material that cannot be placed in the world. Refined oil can be further refined to create %s and %s.")
                    .insert(LCCItems.refined_oil_bucket.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("crude oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink("flexible", KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                    .insertLink("rigid plastic", KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCItems.refined_oil_bucket) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { listOf(plasticRecipe(it, LCCItems.flexible_plastic, Items.BONE_MEAL), plasticRecipe(it, LCCItems.rigid_plastic, Items.QUARTZ)) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCItems.refined_oil_bucket) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials", "Plastic")
    }

    val block_improvised_explosive by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.improvised_explosive)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that can be triggered to create an explosion, similar to %s.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Blocks.TNT.name, KnowledgeArticleIdentifier.ofBlock(Blocks.TNT).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Obtaining")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss can be generated in the %s in the form of landmines, accompanied by a %s. When generated in the world, using a %s or higher has a 30%% chance to drop the block. This chance increases by 10%% for every level of %s. Mining with a %s has a 100%% drop chance.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    .insertLink(LCCBlocks.cracked_mud_pressure_plate.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud_pressure_plate).link)
                    .insertLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe).link)
                    .insertLink(TranslatableText(Enchantments.LOOTING.translationKey), KnowledgeArticleIdentifier.ofEnchant(Enchantments.LOOTING).link)
                    .insertLink(LCCItems.crowbar.name, KnowledgeArticleIdentifier.ofItem(LCCItems.crowbar).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("They can also be crafted with a recipe involving %s.")
                    .insertLink(LCCBlocks.explosive_paste.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.explosive_paste).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Usage")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss can be triggered with a redstone signal, another explosion, or a projectile (e.g. an %s or a %s). When activated by redstone or a projectile, the block will begin to blink and beep slowly for 3-6 seconds. After that, the block will blink and beep rapidly for 1 second before exploding.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink("arrow", KnowledgeArticleIdentifier.ofItem(Items.ARROW).link)
                    .insertLink("trident", KnowledgeArticleIdentifier.ofItem(Items.TRIDENT).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("When activated by another explosion, the block blinks and beeps rapidly and is set to explode in 0.5-1.5 seconds."))
                .addFragment(KnowledgeArticleTextFragmentBuilder("When an active explosive receives a block update and does not have a redstone signal applied to it (i.e. a redstone signal is removed from the explosive), the block will instantly explode."))
            )
            .addSection(KnowledgeArticleSectionBuilder("Explosion")
                .addFragment(KnowledgeArticleTextFragmentBuilder("The explosion created by an %s leaves behind %s similar to that of a %s or %s. The explosion strength is 6.0, which is the same strength as a %s.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink(Blocks.FIRE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FIRE).link)
                    .insertLink("Bed", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/bed" } })
                    .insertLink(Blocks.RESPAWN_ANCHOR.name, KnowledgeArticleIdentifier.ofBlock(Blocks.RESPAWN_ANCHOR).link)
                    .insertLink("Charged Creeper", { (it.linker.generateLink(KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER)) as KnowledgeArticleWebLinkBuilder).target("Charged_creeper") })
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCBlocks.improvised_explosive) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.improvised_explosive) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Explosives")
    }

    val block_deadwood_log by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.deadwood_log)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that can be found in the %s. It can be crafted into %s which lead to the first tier of Wasteland tools.")
                    .insert(LCCBlocks.deadwood_log.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    .insertLink(LCCBlocks.deadwood_planks.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_planks).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss are functionally equivalent to a %s in-game, allowing it to be placed on its side, used as fuel in a %s or %s, or stripped with an axe into %s.")
                    .insert(LCCBlocks.deadwood_log.name)
                    .insertLink("wooden log", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/log" } })
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.coal_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.coal_generator).link)
                    .insertLink("Stripped Deadwood Logs", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.stripped_deadwood_log).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Can be used with %s that accepts any Log.")
                    .insertLink("any recipe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/Log" }.target("Crafting_ingredient") })
                )
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCBlocks.deadwood_log) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.deadwood_log) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .about(LCCBlocks.deadwood)
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Axe Recommended", "Deadwood", "Wood")
    }

    val block_stripped_deadwood_log by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.stripped_deadwood_log)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s. It is obtained by using any %s on a %s.")
                    .insert(LCCBlocks.stripped_deadwood_log.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("axe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/axe" } })
                    .insertLink(LCCBlocks.deadwood_log.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss are functionally equivalent to a stripped %s in-game, allowing it to be placed on its side or used as fuel in a %s or %s.")
                    .insert(LCCBlocks.stripped_deadwood_log.name)
                    .insertLink("wooden log", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/log" } })
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.coal_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.coal_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCBlocks.stripped_deadwood_log) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Can be used with %s that accepts any Stripped Log.")
                    .insertLink("any recipe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/log" }.target("Crafting_ingredient") })
                )
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.stripped_deadwood_log) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .about(LCCBlocks.stripped_deadwood)
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.stripped_deadwood), LCCBlocks.stripped_deadwood.name)
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Axe Recommended", "Deadwood", "Wood")
    }

    val block_deadwood_planks by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.deadwood_planks)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s. It can be crafted from %s and used to craft the first tier of Wasteland tools.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Deadwood Logs", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s are functionally equivalent to wooden planks in-game, allowing it to be used to repair tools in an %s, or as fuel in a %s or %s.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink("wooden log", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/planks" } })
                    .insertLink(Blocks.ANVIL.name, KnowledgeArticleIdentifier.ofBlock(Blocks.ANVIL).link)
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.coal_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.coal_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCBlocks.deadwood_planks) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Can be used with %s that accepts any Overworld Planks.")
                    .insertLink("any recipe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/planks" }.target("Crafting_ingredient") })
                )
                .addFragment(getRecipe(LCCItems.deadwood_sword.identifier, getDeadwoodToolNote(Items.WOODEN_SWORD)))
                .addFragment(getRecipe(LCCItems.deadwood_pickaxe.identifier, getDeadwoodToolNote(Items.WOODEN_PICKAXE)))
                .addFragment(getRecipe(LCCItems.deadwood_shovel.identifier, getDeadwoodToolNote(Items.WOODEN_SHOVEL)))
                .addFragment(getRecipe(LCCItems.deadwood_axe.identifier, getDeadwoodToolNote(Items.WOODEN_AXE)))
                .addFragment(getRecipe(LCCItems.deadwood_hoe.identifier, getDeadwoodToolNote(Items.WOODEN_HOE)))
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.deadwood_planks).filter { r ->
                    r.recipeId != LCCItems.deadwood_sword.identifier && r.recipeId != LCCItems.deadwood_pickaxe.identifier && r.recipeId != LCCItems.deadwood_shovel.identifier && r.recipeId != LCCItems.deadwood_axe.identifier && r.recipeId != LCCItems.deadwood_hoe.identifier
                } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Axe Recommended", "Deadwood", "Wood")
    }

    val entity_consumer by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCEntities.consumer)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("A %s is a short hostile mob introduced in %s. It spawns in the %s and pulls in players and mobs with its ranged tongue attack.")
                    .insert(LCCEntities.consumer.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss have 14.5 hearts of health and are fast mobs that can mostly keep up with sprinting players. They deal 4.125 hearts of damage on Easy difficulty, 6.75 hearts on Normal and 10.125 hearts on Hard with their bite attack.")
                    .insert(LCCEntities.consumer.name)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("When %ss are out of close combat range, they will advance slowly and frequently extend their tongue at their target. This tongue will latch on to the first entity it hits and the %s will retract its tongue, pulling the attached mob or player back with it. Once the tongue is fully retracted, the %s will also land a bite attack.")
                    .insert(LCCEntities.consumer.name)
                    .insert(LCCEntities.consumer.name)
                    .insert(LCCEntities.consumer.name)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Because %ss are from the Wasteland, they deal extra damage that pierces through %s without %s. Any damage dealt to them with a weapon that doesn't provide %s is greatly reduced.")
                    .insert(LCCEntities.consumer.name)
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                    .insertLink("Wasteland Protection", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                    .insertLink("Wasteland Damage", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Drops")
                .addFragment(KnowledgeArticleLootFragmentBuilder { listOf(it.da.lootTableStore[LCCEntities.consumer]!!) }
                )
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .about(LCCEntities.consumer_tongue)
            .redirectsHere(KnowledgeArticleIdentifier.ofEntity(LCCEntities.consumer_tongue), LCCEntities.consumer_tongue.name)
            .tags("Wasteland", "Wasteland Effective", "Wasteland Combat", "Wasteland Damage", "Wasteland Protection", "Hostile Mobs")
    }

    val item_deadwood_sword by entry(::initialiser) { generateWastelandSwordArticle(
        LCCItems.deadwood_sword,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = getRecipe(LCCItems.deadwood_sword.identifier, getDeadwoodToolNote(Items.WOODEN_SWORD))
    ) }

    val item_deadwood_pickaxe by entry(::initialiser) { generateWastelandPickaxeArticle(
        LCCItems.deadwood_pickaxe,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        { insertLink(LCCBlocks.fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.fortstone).link) },
        { insert(LCCBlocks.fortstone.name) },
        recipe = getRecipe(LCCItems.deadwood_pickaxe.identifier, getDeadwoodToolNote(Items.WOODEN_PICKAXE))
    ) }

    val item_deadwood_shovel by entry(::initialiser) { generateWastelandShovelArticle(
        LCCItems.deadwood_shovel,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = getRecipe(LCCItems.deadwood_shovel.identifier, getDeadwoodToolNote(Items.WOODEN_SHOVEL))
    ) }

    val item_deadwood_axe by entry(::initialiser) { generateWastelandAxeArticle(
        LCCItems.deadwood_axe,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = getRecipe(LCCItems.deadwood_axe.identifier, getDeadwoodToolNote(Items.WOODEN_AXE))
    ) }

    val item_deadwood_hoe by entry(::initialiser) { generateWastelandHoeArticle(
        LCCItems.deadwood_hoe,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = getRecipe(LCCItems.deadwood_hoe.identifier, getDeadwoodToolNote(Items.WOODEN_HOE))
    ) }

    val effectivity_wasteland by entry(::initialiser) {
        KnowledgeArticleBuilder(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland"))) { IncludedTranslatableText(it).translation("Wasteland Effectivity", "en_us") }
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("Wasteland Effectivity is a concept introduced in %s that aims to self-contain the progression of players in the %s and prevent them from steamrolling through the biome with powerful gear obtained elsewhere.")
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Most blocks in the Wasteland require Wasteland Effective tools to be mined. Below is a list of blocks that will not drop without a Wasteland-tier tool:")
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Required")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Many blocks in the Wasteland do not require these tools to drop, but can be mined faster with a Wasteland Effective tool. Below is a list of these blocks:")
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Optimal")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Any damage dealt to Wasteland-based mobs with a weapon that doesn't provide Wasteland Damage is greatly reduced, while these mobs also deal extra damage that pierces through %s without Wasteland Protection. Below is a list of mobs that follow these combat rules:")
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Combat")
                    .addRegistryCriteria(Identifier("entity"))
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Equipment")
                .addFragment(KnowledgeArticleTextFragmentBuilder("Below is a list of tools marked as Wasteland equipment:")
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Effective")
                    .addRegistryCriteria(Registry.ITEM.key.value)
                )
            )
            .redirectsHere(KnowledgeArticleIdentifier(LCC.id("effective"), LCC.id("wasteland")), LiteralText("Wasteland Effective"))
            .redirectsHere(KnowledgeArticleIdentifier(LCC.id("required"), LCC.id("wasteland")), LiteralText("Wasteland Required"))
            .redirectsHere(KnowledgeArticleIdentifier(LCC.id("optimal"), LCC.id("wasteland")), LiteralText("Wasteland Optimal"))
            .redirectsHere(KnowledgeArticleIdentifier(LCC.id("combat"), LCC.id("wasteland")), LiteralText("Wasteland Combat"))
            .redirectsHere(KnowledgeArticleIdentifier(LCC.id("damage"), LCC.id("wasteland")), LiteralText("Wasteland Damage"))
            .redirectsHere(KnowledgeArticleIdentifier(LCC.id("protection"), LCC.id("wasteland")), LiteralText("Wasteland Protection"))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Wasteland Optimal", "Wasteland Combat", "Wasteland Damage", "Wasteland Protection")
    }

    val block_fortstone by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.fortstone)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a block introduced in %s that can be found in the %s. It drops %s when mined with a %s or higher, which leads to the second tier of Wasteland tools.")
                    .insert(LCCBlocks.fortstone.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                    .insertLink(LCCBlocks.cobbled_fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cobbled_fortstone).link)
                    .insertLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe).link)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s can be silk touched to drop itself. It has a high blast resistance.")
                    .insert(LCCBlocks.fortstone.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCBlocks.fortstone) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.fortstone) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Deadwood Pickaxe Required", "Fortstone")
    }

    val block_cobbled_fortstone by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.cobbled_fortstone)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a blast-resistant block introduced in %s that can be obtained by mining %s in the %s with a %s. It is used to craft the second tier of Wasteland tools.")
                    .insert(LCCBlocks.cobbled_fortstone.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(LCCBlocks.fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.fortstone).link)
                    .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                    .insertLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCBlocks.cobbled_fortstone) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.cobbled_fortstone) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Deadwood Pickaxe Required", "Fortstone")
    }

    val item_fortstone_sword by entry(::initialiser) { generateWastelandSwordArticle(
        LCCItems.fortstone_sword,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone",
        "It deals 2.5 hearts of damage, but has a slower attack speed than other swords."
    ) }

    val item_fortstone_pickaxe by entry(::initialiser) { generateWastelandPickaxeArticle(
        LCCItems.fortstone_pickaxe,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone",
        { insertLink("rusted iron blocks", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()).link) },
        { insert("rusted iron blocks") }
    ) }

    val item_fortstone_shovel by entry(::initialiser) { generateWastelandShovelArticle(
        LCCItems.fortstone_shovel,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone"
    ) }

    val item_fortstone_axe by entry(::initialiser) { generateWastelandAxeArticle(
        LCCItems.fortstone_axe,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone"
    ) }

    val item_fortstone_hoe by entry(::initialiser) { generateWastelandHoeArticle(
        LCCItems.fortstone_hoe,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone"
    ) }

    val block_rusty_iron by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.rusted_iron_blocks.values.last())
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a resource block introduced in %s that can be obtained by surrounding a regular %s in %s in the %s biome. It can be broken down into 9 %s which is used to craft the third tier of Wasteland tools. It must be mined with a %s or higher.")
                    .insert(LCCBlocks.rusted_iron_blocks.values.last().name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Blocks.IRON_BLOCK.name, KnowledgeArticleIdentifier.ofBlock(Blocks.IRON_BLOCK).link)
                    .insertLink(Blocks.WATER.name, KnowledgeArticleIdentifier.ofBlock(Blocks.WATER).link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(LCCItems.iron_oxide.name, KnowledgeArticleIdentifier.ofItem(LCCItems.iron_oxide).link)
                    .insertLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Stages")
                .addFragment(KnowledgeArticleTextFragmentBuilder("An iron block in any Wasteland biome will slowly become rusted if it meets all the following criteria:"))
                .addFragment(KnowledgeArticleBulletedFragmentBuilder()
                    .add(KnowledgeArticleTextFragmentBuilder("Has at least one orthogonally adjacent block of water."))
                    .add(KnowledgeArticleTextFragmentBuilder("Has at least three orthogonally adjacent blocks of either water or a rusted iron block of any stage."))
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Water sources, flowing water and waterlogged blocks are all considered as water, but any water below the iron block is not counted as it does not directly touch the block."))
                .addFragment(KnowledgeArticleTextFragmentBuilder("Below is an ordered list of the rusting stages of an iron block:"))
                .addFragment(KnowledgeArticleListFragmentBuilder()
                    .add(*LCCBlocks.rusted_iron_blocks.values.map(KnowledgeArticleIdentifier::ofBlock).toTypedArray(), reroute = false, link = false)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.rusted_iron_blocks.values.flatMap { b -> it.da.recipeStore.findRecipes(b) } })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.rusted_iron_blocks.values.flatMap { b -> it.da.recipeStore.findUsages(b) } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .about(*LCCBlocks.rusted_iron_blocks.values.toTypedArray())
            .apply { IronRustType.values().forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks[it]!!), LCCBlocks.rusted_iron_blocks[it]!!.name) } }
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Rusted Iron", "Resources")
    }

    val item_iron_oxide by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.iron_oxide)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a resource introduced in %s. It can be crafted in groups of 9 by placing a %s in a crafting table. It is used to craft the third tier of %s tools.")
                    .insert(LCCItems.iron_oxide.name)
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(LCCBlocks.rusted_iron_blocks.values.last().name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()).link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(LCCItems.iron_oxide) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCItems.iron_oxide) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = true))
            .tags("Wasteland", "Materials", "Rusted Iron", "Resources")
    }

    val item_rusty_iron_sword by entry(::initialiser) { generateWastelandSwordArticle(
        LCCItems.rusty_iron_sword,
        LCCItems.iron_oxide,
        "third",
        "Rusted Iron",
        "an iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this sword has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_pickaxe by entry(::initialiser) { generateWastelandPickaxeArticle(
        LCCItems.rusty_iron_pickaxe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        { insertLink(LCCBlocks.sapphire_altar_brick.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar_brick).link) },
        { insert(LCCBlocks.sapphire_altar_brick.name) },
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this pickaxe has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_shovel by entry(::initialiser) { generateWastelandShovelArticle(
        LCCItems.rusty_iron_shovel,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this shovel has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_axe by entry(::initialiser) { generateWastelandAxeArticle(
        LCCItems.rusty_iron_axe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this axe has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_hoe by entry(::initialiser) { generateWastelandHoeArticle(
        LCCItems.rusty_iron_hoe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this hoe has a much lower durability than one made of pure iron."))
    } }

    fun initialiser(input: KnowledgeArticleBuilder, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out KnowledgeArticleBuilder, out KnowledgeArticleBuilder>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.afterInit() }
    }

    override fun defaultProperties(name: String) = Unit

    fun getArticlesAbout(topic: Any) = this.all.filter { (k, v) -> v.about.contains(topic) }

    //Shared Code

    private val introduction = "Introduction"

    private fun generatePlasticDespawningSection(topic: Item) = KnowledgeArticleSectionBuilder("Despawning")
        .addFragment(KnowledgeArticleTextFragmentBuilder("%s, like all plastic-based items, do not despawn after 5 minutes when dropped in the world.")
            .insert(topic.name)
        )

    private fun generatePlasticArticle(article: KnowledgeArticleBuilder, topic: Item, plasticiser: Item) {
        article
            .addSection(generatePlasticDespawningSection(topic))
            .addSection(KnowledgeArticleSectionBuilder({ IncludedTranslatableText(it).translation("Coloring", "en_us").translation("Colouring", "en_gb") })
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s and %s can be crafted into different %s by adding dyes to their refining recipe. Similar to dyeing leather %s, multiple dyes can be applied and the resulting %s will be a mix (average) of the provided dyes. However, dyes apply a much more vibrant and saturated %s to plastic. You can view %s codes in the table below:")
                    .insert(LCCItems.flexible_plastic.name)
                    .insert(LCCItems.rigid_plastic.name)
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                )
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Item"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder { IncludedTranslatableText(it).translation("Color Code", "en_us").translation("Colour Code", "en_gb") })
                    }
                    .addRows(*Registry.ITEM.filterIsInstance<DyeItem>().toTypedArray()) {
                        addCell(KnowledgeArticleStackFragmentBuilder(it.stack()))
                        addCell(KnowledgeArticleColorFragmentBuilder((it.color as LCCExtendedDyeColor).plasticColor))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { listOf(plasticRecipe(it, topic, plasticiser)) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(topic) })
            )
    }

    private fun plasticRecipe(exporter: KnowledgeExporter, output: Item, plasticiser: Item) = OverrideRecipeJsonProvider.fromFactory(LCCRecipeSerializers.polymerization, RefiningShapelessRecipeJsonFactory()
        .addInput(LCCItems.refined_oil_bucket)
        .addInput(plasticiser)
        .addInput(Ingredient.ofItems(*Registry.ITEM.filterIsInstance<DyeItem>().toTypedArray()))
        .addOutput(output, 3)
        .with(*PolymerRefiningRecipe.blocks)
        .meta(PolymerRefiningRecipe.lang, PolymerRefiningRecipe.icon, PolymerRefiningRecipe.state)
        .speed(PolymerRefiningRecipe.speed, PolymerRefiningRecipe.speedGainPerTick, PolymerRefiningRecipe.maxSpeedGainPerTick)
        .energyPerTick(PolymerRefiningRecipe.energyPerTick)
    , RefiningShapelessRecipeJsonFactory::offerTo) {
        val ingredients = it.get("ingredients").asJsonArray
        val dyeStack = ingredients[2].asJsonArray
        dyeStack.forEachIndexed { k, v ->
            val stack = v.asJsonObject
            stack.addProperty("vararg", true)
            dyeStack.set(k, v)
        }
        ingredients.set(2, dyeStack)
        it.add("ingredients", ingredients)
        val items = arrayOf(LCCItems.refined_oil_bucket, plasticiser, *Registry.ITEM.filterIsInstance<DyeItem>().toTypedArray(), output)
        it.add("translations", exporter.translator.itemTranslationsJson(*items))
        it.add("links", exporter.linker.itemLinksJson(*items))
    }

    private fun legacyOilRecipe() = KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleTextFragmentBuilder("Recipe before %s.")
        .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link), obsolete = true) { e ->
            listOf(OverrideRecipeJsonProvider(LCCRecipeSerializers.refining_shapeless, e.da.recipeStore.findRecipes(LCCItems.asphalt_bucket).first()) {
                val ingredients = it.get("ingredients").asJsonArray
                val tar = ingredients[0].asJsonObject
                tar.addProperty("item", LCCItems.oil_bucket.identifier.toString())
                ingredients.set(0, tar)
                ingredients.remove(3)
                it.add("ingredients", ingredients)
                val items = arrayOf(LCCItems.oil_bucket, Items.SAND, Items.GRAVEL, LCCItems.asphalt_bucket)
                it.add("translations", e.translator.itemTranslationsJson(*items))
                it.add("links", e.linker.itemLinksJson(*items))
            })
        }

    private fun getRecipe(recipe: Identifier, note: KnowledgeArticleFragmentBuilder? = null, obsolete: Boolean = false) = KnowledgeArticleRecipeFragmentBuilder(note, obsolete) {
        listOf(it.da.recipeStore.get(recipe)!!)
    }

    private fun getDeadwoodToolNote(fallback: Item) = KnowledgeArticleTextFragmentBuilder("If not all planks provided are %s, a standard %s will be crafted instead.")
        .insert(LCCBlocks.deadwood_planks.name)
        .insertLink(fallback.name, KnowledgeArticleIdentifier.ofItem(fallback).link)

    private fun getRepairingSection(tool: Item, repair: Text) = KnowledgeArticleSectionBuilder("Repairing")
        .addFragment(KnowledgeArticleTextFragmentBuilder("%ss can be repaired with %s in an %s. Like all tools, two %ss can also be fused in crafting or a %s.")
            .insert(tool.name)
            .insert(repair)
            .insertLink(Blocks.ANVIL.name, KnowledgeArticleIdentifier.ofBlock(Blocks.ANVIL).link)
            .insert(tool.name)
            .insertLink(Blocks.GRINDSTONE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.GRINDSTONE).link)
        )

    private fun generateWastelandSwordArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, damage: String = "It deals ${(item as SwordItem).attackDamage.div(2.0)}", recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent sword, $damage")
                    .insert(item.name)
                    .insertLink("sword", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/sword" } })
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss deal %s, which deal full damage against Wasteland-based creatures.")
                    .insert(item.name)
                    .insertLink("Wasteland Damage", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                )
            )
            .addSection(getRepairingSection(item, ingredient.asItem().name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(recipe)
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Combat", "Wasteland Damage", tag, "Swords", "Tools")

    private fun generateWastelandPickaxeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, example: KnowledgeArticleTextFragmentBuilder.() -> Unit, example2: KnowledgeArticleTextFragmentBuilder.() -> Unit, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent pickaxe, but can mine %s.")
                    .insert(item.name)
                    .insertLink("pickaxe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/pickaxe" } })
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                    .apply(example)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss are %s, allowing them to harvest blocks that require a Wasteland pickaxe to mine, such as %s.")
                    .insert(item.name)
                    .insertLink("Wasteland Effective", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                    .apply(example2)
                )
                .addFragment(KnowledgeArticleTextFragmentBuilder("Some blocks, such as %s, break faster with a Wasteland Effective pickaxe but do not require one.")
                    .insertLink(LCCBlocks.cracked_mud.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                )
            )
            .addSection(getRepairingSection(item, ingredient.asItem().name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(recipe)
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Wasteland Optimal", tag, "Pickaxes", "Tools")

    private fun generateWastelandShovelArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent shovel.")
                    .insert(item.name)
                    .insertLink("shovel", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/shovel" } })
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss are %s, allowing them to break blocks that require a Wasteland shovel, such as %s, faster.")
                    .insert(item.name)
                    .insertLink("Wasteland Effective", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                    .insertLink(LCCBlocks.mud.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.mud).link)
                )
            )
            .addSection(getRepairingSection(item, ingredient.asItem().name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(recipe)
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Shovels", "Tools")

    private fun generateWastelandAxeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is an %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent axe.")
                    .insert(item.name)
                    .insertLink("axe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/axe" } })
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss are %s, allowing them to break blocks that require a Wasteland axe, such as %s, faster.")
                    .insert(item.name)
                    .insertLink("Wasteland Effective", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                    .insertLink(LCCBlocks.deadwood.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                )
            )
            .addSection(getRepairingSection(item, ingredient.asItem().name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(recipe)
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Axes", "Tools")

    private fun generateWastelandHoeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleTextFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent hoe.")
                    .insert(item.name)
                    .insertLink("hoe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/hoe" } })
                    .insertLink("LCC 0.5.0", LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleTextFragmentBuilder("%ss are %s, allowing them to break blocks that require a Wasteland hoe, such as %s, faster.")
                    .insert(item.name)
                    .insertLink("Wasteland Effective", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                    .insertLink("papercomb", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.papercomb_block).link)
                )
            )
            .addSection(getRepairingSection(item, ingredient.asItem().name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(recipe)
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Hoes", "Tools")

}
