package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.challenges.LCCAltarChallenges
import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.*
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleLinkBuilder.Companion.link
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleWebLinkBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleChangelogSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleVersionChangelogSectionBuilder
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
import net.minecraft.item.DyeItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.text.TranslatableText
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry

object LCCKnowledgeData: BasicDirectory<KnowledgeArticleBuilder, Unit>() {

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
            .tags("Wasteland", "Crafting Materials", "Explosives")
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
            .tags("Wasteland", "Explosives")
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
            .about(LCCBlocks.deadwood)
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood))
            .tags("Wasteland", "Deadwood", "Wood")
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
            .about(LCCBlocks.stripped_deadwood)
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.stripped_deadwood), LCCBlocks.stripped_deadwood.name)
            .tags("Wasteland", "Deadwood", "Wood")
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
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleTextFragmentBuilder("If not all planks provided are %s, a standard %s will be crafted instead.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink(Items.WOODEN_SWORD.name, KnowledgeArticleIdentifier.ofItem(Items.WOODEN_SWORD).link)
                ) {
                    listOf(it.da.recipeStore.get(LCCItems.deadwood_sword.identifier)!!)
                })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleTextFragmentBuilder("If not all planks provided are %s, a standard %s will be crafted instead.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink(Items.WOODEN_PICKAXE.name, KnowledgeArticleIdentifier.ofItem(Items.WOODEN_PICKAXE).link)
                ) {
                    listOf(it.da.recipeStore.get(LCCItems.deadwood_pickaxe.identifier)!!)
                })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleTextFragmentBuilder("If not all planks provided are %s, a standard %s will be crafted instead.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink(Items.WOODEN_SHOVEL.name, KnowledgeArticleIdentifier.ofItem(Items.WOODEN_SHOVEL).link)
                ) {
                    listOf(it.da.recipeStore.get(LCCItems.deadwood_shovel.identifier)!!)
                })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleTextFragmentBuilder("If not all planks provided are %s, a standard %s will be crafted instead.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink(Items.WOODEN_AXE.name, KnowledgeArticleIdentifier.ofItem(Items.WOODEN_AXE).link)
                ) {
                    listOf(it.da.recipeStore.get(LCCItems.deadwood_axe.identifier)!!)
                })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleTextFragmentBuilder("If not all planks provided are %s, a standard %s will be crafted instead.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink(Items.WOODEN_HOE.name, KnowledgeArticleIdentifier.ofItem(Items.WOODEN_HOE).link)
                ) {
                    listOf(it.da.recipeStore.get(LCCItems.deadwood_hoe.identifier)!!)
                })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipeStore.findUsages(LCCBlocks.deadwood_planks).filter { r ->
                    r.recipeId != LCCItems.deadwood_sword.identifier && r.recipeId != LCCItems.deadwood_pickaxe.identifier && r.recipeId != LCCItems.deadwood_shovel.identifier && r.recipeId != LCCItems.deadwood_axe.identifier && r.recipeId != LCCItems.deadwood_hoe.identifier
                } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .tags("Wasteland", "Deadwood", "Wood")
    }

    private val introduction = "Introduction"

    fun initialiser(input: KnowledgeArticleBuilder, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out KnowledgeArticleBuilder, out KnowledgeArticleBuilder>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.afterInit() }
    }

    override fun defaultProperties(name: String) = Unit

    fun getArticlesAbout(topic: Any) = this.all.filter { (k, v) -> v.about.contains(topic) }

    //Shared Code

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

}
