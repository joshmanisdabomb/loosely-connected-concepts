package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleBlockInfoSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleChangelogSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeExtensions.addLink
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems

object LCCKnowledgeData : BasicDirectory<KnowledgeArticleBuilder, Unit>() {

    val block_sapphire_altar by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.sapphire_altar)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addText(LCCBlocks.sapphire_altar)
                    addText(" is a block introduced in ")
                    addLink(LCCVersion.LCC_FABRIC_0_5_0)
                    addText(" that allows players to interface with the challenge of a ")
                    addText("Sapphire Altar (Structure)") //TODO link to structure page
                    addText(". It breaks into ")
                    addLink(LCCBlocks.sapphire_altar_brick)
                    addText(", even with the Silk Touch enchantment, making this block obtainable only in Creative Mode.")
                }
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usage)
                .addParagraph {
                    addFormatText("Every altar block spawns with exactly one %s already placed in the middle slot. Players may sacrifice more sapphires or %s into the other four empty slots. Sapphires cannot be retrieved once placed in the altar.",
                        { addLink(LCCItems.sapphire) },
                        { addPluralisedLink(LCCItems.dull_sapphire) }
                    )
                }
                .addParagraph {
                    addText("An ")
                    addLink(LCCItems.altar_challenge_key)
                    addText(" must then be used on the block to start the challenge. If the structure of the altar is malformed or has been tampered with before the key is used, a notification will be displayed and the challenge will not start.")
                }
                .addParagraph {
                    addText("If the challenge is beaten successfully, the altar block breaks and drops its contents. Regular sapphires placed in the altar slots are doubled when dropped, while dull sapphires are converted into regular sapphires 1 to 1. For example, if the challenge was beaten with the sapphire that initially spawned in the middle slot, the sapphire altar explodes into 2 sapphires.")
                }
                .addParagraph {
                    addText("If the challenge is failed, the altar block breaks and drops nothing. All sapphires and dull sapphires placed in the altar are lost. Additionally, if the challenge is failed due to being tampered with (e.g. the minesweeper board is broken to reveal mine locations) then the sapphire altar explodes violently.")
                }
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Sapphire Altar", "Sapphire Pickaxe Required")
    }

    /*val lcc_fabric_0_5_0 by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCVersion.LCC_FABRIC_0_5_0.page, LCCVersion.LCC_FABRIC_0_5_0.label)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder(LCCVersion.LCC_FABRIC_0_5_0.getIntroduction())
                    .insert("Loosely Connected Concepts 0.5.0")
                    .insert("fifteenth")
                    .insert("58th")
                    .insertLink(LCCVersion.LCC_FABRIC_0_4_4.label, LCCVersion.LCC_FABRIC_0_4_4.page.link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The focus of LCC 0.5.x is the %s biome, a hostile and dismal expanse filled with vicious traps and difficult mobs.")
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Gameplay")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("In this version, the Wasteland biome is split into two sub-biomes, %s and %s. The barrens are flat terrain where traps, resources and larger quantities of mobs are commonly found. The spiked regions are generally safer, but provide less rewards and are more challenging to navigate.")
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Some blocks native to the Wasteland require Wasteland tools to mine, and most mobs native to the Wasteland deal increased damage through non-Wasteland %s and take reduced damage from non-Wasteland equipment. The tool progression starts at %s, a new wood type which can be found in clusters scattered across the barrens. A %s is required to mine %s which appears on the surface in the spikes sub-biome. Players must then convert their blocks of iron into %s by submerging them in water in any Wasteland sub-biome. When the iron has completely rusted, it can be mined with a %s and crafted into tools, armour and %s. These keys must be used to activate challenges posed by %s structures to obtain %s, the current final tier of Wasteland equipment.")
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                    .insertLink(LCCBlocks.deadwood.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                    .insertLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe).link)
                    .insertLink(LCCBlocks.fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.fortstone).link)
                    .insertLink("rusted iron blocks", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()).link)
                    .insertLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe).link)
                    .insertLink("Altar Challenge Keys", KnowledgeArticleIdentifier.ofItem(LCCItems.altar_challenge_key).link)
                    .insert/*Link*/("Sapphire Altar"/*, KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar).link*/)
                    .insertLink("sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Players can also find treasure in the Wasteland. %s are structures that spawn in the barrens which often contain loot. Obtained items can range from sticks to %s, which can permanently increase your maximum life. '%s' can also be found in the barrens, grouped in clusters. Breaking these will sometimes drop gold pieces, iron pieces or the aforementioned heart containers.")
                    .insert/*Link*/("Tents"/*, KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.wasteland_tent).link*/)
                    .insertLink("heart containers", KnowledgeArticleIdentifier(LCC.id("concepts"), LCC.id("heart_containers")).link)
                    .insertLink("Deposits", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deposit).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The Wasteland is now home to three dangerous mobs. The %s is a baby version of a regular Minecraft skeleton that strafes and shoots faster and has a much smaller hitbox. %s are creatures that extends its tongue out to latch on to its prey. Finally, %s nests spawn in all parts of the Wasteland. Wasps are generally neutral but will vehemently defend their nests from perceived threats and can get randomly aggressive at nearby targets.")
                    .insertLink(LCCEntities.baby_skeleton.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.baby_skeleton).link)
                    .insertLink("Consumers", KnowledgeArticleIdentifier.ofEntity(LCCEntities.consumer).link)
                    .insertLink(LCCEntities.wasp.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.wasp).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s traps and landmines hooked up to camouflaged %s are often placed in Wasteland Barrens. Spikes can sometimes spawn covered in %s or %s residue. Landmines are rigged up with an %s, which leaves behind a large firey crater.")
                    .insertLink("Spike", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.spikes).link)
                    .insertLink("pressure plates", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud_pressure_plate).link)
                    .insertLink("bloody", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.bleeding_spikes).link)
                    .insertLink("poisonous", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.poison_spikes).link)
                    .insertLink(LCCBlocks.improvised_explosive.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.improvised_explosive).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Oil refining has also been modified in this version. Oil, which now spawns more commonly in the wasteland as geysers or in pockets buried under %s, now refines into three products: %s, %s and %s. Fuel can be converted to energy using a %s, or refined further into %s which can be placed to cause chain reactions of explosions, create improvised explosives (landmines) or to %s. Refined oil can be further refined into %s or %s, both of which can have its %s customised with dyes and do not despawn when dropped. Flexible plastic can be crafted into %s, which hold 2 stacks of items. Tar balls are used to create %s.")
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Similar to update %s, a lot of technical changes happened behind the scenes in addition to updated gameplay changes. The lcc-base module was split into lcc-infra and lcc-hooks. The infrastructure module provides helpers to other modules without making any changes to Minecraft with mixins and access transformers. In contrast, the hooks module makes those kind of changes and gives other modules access to interact with these hooks, using methods like trait interfaces or accessor mixins.")
                    .insertLink("0.2.0", LCCVersion.LCC_FABRIC_0_2_0.page.link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The data generator module was also split up to separate code specific to the content of LCC. This allows other mods in future to use lcc-data as a module to generate data for themselves.")
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Commit Messages from BitBucket")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder(LCCVersion.LCC_FABRIC_0_5_0.description))
            )
            .addSection(KnowledgeArticleVersionChangelogSectionBuilder(LCCVersion.LCC_FABRIC_0_5_0))
            .tags("LCC")
    }

    val block_explosive_paste by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.explosive_paste)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block introduced in %s that explodes shortly after being triggered by another explosion. It connects to other pieces of %s placed nearby, similar to %s.")
                    .insert(LCCBlocks.explosive_paste.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insert(LCCBlocks.explosive_paste.name)
                    .insertLink(Items.REDSTONE.name, KnowledgeArticleIdentifier.ofItem(Items.REDSTONE).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Strength")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The strength of the explosion caused by a piece of %s depends on the amount of connections to other adjacent pieces of %s.")
                    .insert(LCCBlocks.explosive_paste.name)
                    .insert(LCCBlocks.explosive_paste.name)
                )
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Adjacent Connections"))
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Strength"))
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Equivalent To"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("0"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("2.5"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("%s").insertLink("Ghast Fireball", { (it.linker.generateLink(KnowledgeArticleIdentifier.ofEntity(EntityType.GHAST)) as KnowledgeArticleWebLinkBuilder).target("Ghast_fireball") }))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("1"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("3.4"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("%s").insertLink(TranslatableText(EntityType.CREEPER.translationKey), KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER).link))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("2"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("4.3"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("%s").insertLink(Blocks.TNT.name, KnowledgeArticleIdentifier.ofBlock(Blocks.TNT).link))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("3"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("5.2"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("%s/%s").insertLink("Bed", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/bed" } }).insertLink(Blocks.RESPAWN_ANCHOR.name, KnowledgeArticleIdentifier.ofBlock(Blocks.RESPAWN_ANCHOR).link))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("4"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("6.1"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("%s").insertLink("Charged Creeper", { (it.linker.generateLink(KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER)) as KnowledgeArticleWebLinkBuilder).target("Charged_creeper") }))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.explosive_paste) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.explosive_paste) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Materials", "Explosives")
    }

    val item_rigid_plastic by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.rigid_plastic)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item introduced in %s as a crafting material for plastic items. It currently has no uses, but is planned to be a key crafting ingredient for computers in the future. It can be obtained by refining %s, similarly to %s.")
                    .insert(LCCItems.rigid_plastic.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item introduced in %s as a crafting material for plastic items such as the %s. It can be obtained by refining %s, similarly to %s.")
                    .insert(LCCItems.flexible_plastic.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item introduced in %s that functions similarly to the vanilla %s, but stores 2 stacks of items instead of only one. It is crafted with %s.")
                    .insert(LCCItems.plastic_bag.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Items.BUNDLE.name, KnowledgeArticleIdentifier.ofItem(Items.BUNDLE).link)
                    .insertLink(LCCItems.flexible_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                )
            )
            .addSection(generatePlasticDespawningSection(LCCItems.plastic_bag))
            .addSection(KnowledgeArticleSectionBuilder({ IncludedTranslatableText(it).translation("Coloring", "en_us").translation("Colouring", "en_gb") })
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The %s of the %s used to craft the plastic bag determines its final %s. Multiple %s of plastic can be used and the resulting bag will be a mix (multiplication) of the provided %s.")
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    .insert(LCCItems.flexible_plastic.name)
                    .insert({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.plastic_bag) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.plastic_bag) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Tools", "Bags", "Plastic")
    }

    val item_oil_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.oil_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item originally introduced in %s and reintroduced in %s. This %s contains %s which is found in the %s biome in the form of either geysers spewing from the surface, or as pockets hidden slightly underneath %s.")
                    .insert(LCCItems.oil_bucket.name)
                    .insertLink(LCCVersion.YAM_4.label, LCCVersion.YAM_4.page.link)
                    .insertLink(LCCVersion.LCC_FABRIC_0_1_0.label, LCCVersion.LCC_FABRIC_0_1_0.page.link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink("oil", KnowledgeArticleIdentifier.ofFluid(LCCFluids.oil_still).link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink("cracked mud", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Oil is a key component of this mod, allowing the player to use a %s to craft %s, %s and %s from %s, and %s from %s.")
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Buckets of oil can be placed in a %s to yield 2 LE/t over 600 seconds. It is much more efficient to first refine crude oil into fuel, which provides 14 LE/t over 200 seconds in the generator.")
                    .insertLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.oil_bucket) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.oil_bucket) })
                .addFragment(legacyOilRecipe())
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials", "Bucket", "Wasteland")
    }

    val item_tar_ball by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.tar_ball)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a crafting material introduced in %s which can be used to create %s which eventually hardens into %s. It is one of the three products obtained by refining %s.")
                    .insert(LCCItems.tar_ball.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("asphalt", KnowledgeArticleIdentifier.ofItem(LCCItems.asphalt_bucket).link)
                    .insertLink("road blocks", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.road).link)
                    .insertLink("crude oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.tar_ball) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.tar_ball) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials")
    }

    val item_asphalt_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.asphalt_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item introduced in %s but first made obtainable in %s. This %s contains %s which can be placed in the world. It is obtained by refining %s.")
                    .insert(LCCItems.asphalt_bucket.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_1_0.label, LCCVersion.LCC_FABRIC_0_1_0.page.link)
                    .insertLink(LCCVersion.LCC_FABRIC_0_3_0.label, LCCVersion.LCC_FABRIC_0_3_0.page.link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink("asphalt", KnowledgeArticleIdentifier.ofFluid(LCCFluids.asphalt_still).link)
                    .insertLink("tar", KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.asphalt_bucket) })
                .addFragment(legacyOilRecipe())
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.asphalt_bucket) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials")
    }

    val item_fuel_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.fuel_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item introduced in %s. It is one of the three products obtained by refining %s. The contents of this %s represents a crafting material that cannot be placed in the world. Fuel can be used to generate power or to craft %s.")
                    .insert(LCCItems.fuel_bucket.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("crude oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink(LCCBlocks.explosive_paste.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.explosive_paste).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Power")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Buckets of fuel can be placed in a %s to yield 14 LE/t over 200 seconds. It is much more efficient to use fuel than using crude oil directly, which provides 2 LE/t over 600 seconds in the generator.")
                    .insertLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.fuel_bucket) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.fuel_bucket) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials", "Power")
    }

    val item_refined_oil_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.refined_oil_bucket)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item introduced in %s. It is one of the three products obtained by refining %s. The contents of this %s represents a crafting material that cannot be placed in the world. Refined oil can be further refined to create %s and %s.")
                    .insert(LCCItems.refined_oil_bucket.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("crude oil", KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket).link)
                    .insertLink("bucket", KnowledgeArticleIdentifier.ofItem(Items.BUCKET).link)
                    .insertLink("flexible", KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                    .insertLink("rigid plastic", KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.refined_oil_bucket) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { listOf(plasticRecipe(it, LCCItems.flexible_plastic, Items.BONE_MEAL), plasticRecipe(it, LCCItems.rigid_plastic, Items.QUARTZ)) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.refined_oil_bucket) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = false))
            .tags("Oil", "Materials", "Plastic")
    }

    val block_improvised_explosive by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.improvised_explosive)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block introduced in %s that can be triggered to create an explosion, similar to %s.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Blocks.TNT.name, KnowledgeArticleIdentifier.ofBlock(Blocks.TNT).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Obtaining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss can be generated in the %s in the form of landmines, accompanied by a %s. When generated in the world, using a %s or higher has a 30%% chance to drop the block. This chance increases by 10%% for every level of %s. Mining with a %s has a 100%% drop chance.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    .insertLink(LCCBlocks.cracked_mud_pressure_plate.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud_pressure_plate).link)
                    .insertLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe).link)
                    .insertLink(TranslatableText(Enchantments.FORTUNE.translationKey), KnowledgeArticleIdentifier.ofEnchant(Enchantments.FORTUNE).link)
                    .insertLink(LCCItems.crowbar.name, KnowledgeArticleIdentifier.ofItem(LCCItems.crowbar).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("They can also be crafted with a recipe involving %s.")
                    .insertLink(LCCBlocks.explosive_paste.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.explosive_paste).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Usage")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss can be triggered with a redstone signal, another explosion, or a projectile (e.g. an %s or a %s). When activated by redstone or a projectile, the block will begin to blink and beep slowly for 3-6 seconds. After that, the block will blink and beep rapidly for 1 second before exploding.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink("arrow", KnowledgeArticleIdentifier.ofItem(Items.ARROW).link)
                    .insertLink("trident", KnowledgeArticleIdentifier.ofItem(Items.TRIDENT).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("When activated by another explosion, the block blinks and beeps rapidly and is set to explode in 0.5-1.5 seconds."))
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("When an active explosive receives a block update and does not have a redstone signal applied to it (i.e. a redstone signal is removed from the explosive), the block will instantly explode."))
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("If an active explosive is right clicked with %s, the block will be defused and drop as if it was broken with a pickaxe.")
                    .insertLink(Items.SHEARS.name, KnowledgeArticleIdentifier.ofItem(Items.SHEARS).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Explosion")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The explosion created by an %s leaves behind %s similar to that of a %s or %s. The explosion strength is 6.0, which is the same strength as a %s.")
                    .insert(LCCBlocks.improvised_explosive.name)
                    .insertLink(Blocks.FIRE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FIRE).link)
                    .insertLink("Bed", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/bed" } })
                    .insertLink(Blocks.RESPAWN_ANCHOR.name, KnowledgeArticleIdentifier.ofBlock(Blocks.RESPAWN_ANCHOR).link)
                    .insertLink("Charged Creeper", { (it.linker.generateLink(KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER)) as KnowledgeArticleWebLinkBuilder).target("Charged_creeper") })
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.improvised_explosive) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.improvised_explosive) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Explosives", "Salvageable")
    }

    val block_deadwood_log by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.deadwood_log)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block introduced in %s that can be found in the %s. It can be crafted into %s which lead to the first tier of Wasteland tools.")
                    .insert(LCCBlocks.deadwood_log.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    .insertLink(LCCBlocks.deadwood_planks.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_planks).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss are functionally equivalent to a %s in-game, allowing it to be placed on its side, used as fuel in a %s or %s, or stripped with an axe into %s.")
                    .insert(LCCBlocks.deadwood_log.name)
                    .insertLink("wooden log", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/log" } })
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.coal_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.coal_generator).link)
                    .insertLink("Stripped Deadwood Logs", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.stripped_deadwood_log).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Can be used with %s that accepts any Log.")
                    .insertLink("any recipe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/Log" }.target("Crafting_ingredient") })
                )
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.deadwood_log) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.deadwood_log) })
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block introduced in %s. It is obtained by using any %s on a %s.")
                    .insert(LCCBlocks.stripped_deadwood_log.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("axe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/axe" } })
                    .insertLink(LCCBlocks.deadwood_log.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss are functionally equivalent to a stripped %s in-game, allowing it to be placed on its side or used as fuel in a %s or %s.")
                    .insert(LCCBlocks.stripped_deadwood_log.name)
                    .insertLink("wooden log", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/log" } })
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.coal_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.coal_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.stripped_deadwood_log) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Can be used with %s that accepts any Stripped Log.")
                    .insertLink("any recipe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/log" }.target("Crafting_ingredient") })
                )
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.stripped_deadwood_log) })
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block introduced in %s. It can be crafted from %s and used to craft the first tier of Wasteland tools.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Deadwood Logs", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s are functionally equivalent to wooden planks in-game, allowing it to be used to repair tools in an %s, or as fuel in a %s or %s.")
                    .insert(LCCBlocks.deadwood_planks.name)
                    .insertLink("wooden log", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/planks" } })
                    .insertLink(Blocks.ANVIL.name, KnowledgeArticleIdentifier.ofBlock(Blocks.ANVIL).link)
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.coal_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.coal_generator).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.deadwood_planks) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Can be used with %s that accepts any Overworld Planks.")
                    .insertLink("any recipe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/planks" }.target("Crafting_ingredient") })
                )
                .addFragment(getRecipe(LCCItems.deadwood_sword.identifier, getDeadwoodToolNote(Items.WOODEN_SWORD)))
                .addFragment(getRecipe(LCCItems.deadwood_pickaxe.identifier, getDeadwoodToolNote(Items.WOODEN_PICKAXE)))
                .addFragment(getRecipe(LCCItems.deadwood_shovel.identifier, getDeadwoodToolNote(Items.WOODEN_SHOVEL)))
                .addFragment(getRecipe(LCCItems.deadwood_axe.identifier, getDeadwoodToolNote(Items.WOODEN_AXE)))
                .addFragment(getRecipe(LCCItems.deadwood_hoe.identifier, getDeadwoodToolNote(Items.WOODEN_HOE)))
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.deadwood_planks).filter { r ->
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("A %s is a short hostile mob introduced in %s. It spawns in the %s and pulls in players and mobs with its ranged tongue attack.")
                    .insert(LCCEntities.consumer.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss have 14.5 hearts of health and are fast mobs that can mostly keep up with sprinting players. They deal 4.125 hearts of damage on Easy difficulty, 6.75 hearts on Normal and 10.125 hearts on Hard with their bite attack.")
                    .insert(LCCEntities.consumer.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("When %ss are out of close combat range, they will advance slowly and frequently extend their tongue at their target. This tongue will latch on to the first entity it hits and the %s will retract its tongue, pulling the attached mob or player back with it. Once the tongue is fully retracted, the %s will also land a bite attack.")
                    .insert(LCCEntities.consumer.name)
                    .insert(LCCEntities.consumer.name)
                    .insert(LCCEntities.consumer.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Because %ss are from the Wasteland, they deal extra damage that pierces through %s without %s. Any damage dealt to them with a weapon that doesn't provide %s is greatly reduced.")
                    .insert(LCCEntities.consumer.name)
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                    .insertLink("Wasteland Protection", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                    .insertLink("Wasteland Damage", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Drops")
                .addFragment(KnowledgeArticleLootFragmentBuilder { listOf(it.da.lootTables[LCCEntities.consumer]!!) }
                )
                .addFragment(KnowledgeArticleLootFragmentBuilder(KnowledgeArticleParagraphFragmentBuilder("Can only drop when the %s's tongue is extended.").insert(LCCEntities.consumer.name)) { listOf(it.da.lootTables[LCCEntities.consumer_tongue]!!) }
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Wasteland Effectivity is a concept introduced in %s that aims to self-contain the progression of players in the %s and prevent them from steamrolling through the biome with powerful gear obtained elsewhere.")
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Most blocks in the Wasteland require Wasteland Effective tools to be mined. Below is a list of blocks that will not drop without a Wasteland-tier tool:")
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Required")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Many blocks in the Wasteland do not require these tools to drop, but can be mined faster with a Wasteland Effective tool. Below is a list of these blocks:")
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Optimal")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Any damage dealt to Wasteland-based mobs with a weapon that doesn't provide Wasteland Damage is greatly reduced, while these mobs also deal extra damage that pierces through %s without Wasteland Protection. Below is a list of mobs that follow these combat rules:")
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Combat")
                    .addRegistryCriteria(Identifier("entity"))
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Equipment")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Below is a list of tools marked as Wasteland equipment:")
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block introduced in %s that can be found in the %s. It drops %s when mined with a %s or higher, which leads to the second tier of Wasteland tools.")
                    .insert(LCCBlocks.fortstone.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                    .insertLink(LCCBlocks.cobbled_fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cobbled_fortstone).link)
                    .insertLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s can be silk touched to drop itself. It has a high blast resistance.")
                    .insert(LCCBlocks.fortstone.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.fortstone) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.fortstone) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Deadwood Pickaxe Required", "Fortstone")
    }

    val block_cobbled_fortstone by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.cobbled_fortstone)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a blast-resistant block introduced in %s that can be obtained by mining %s in the %s with a %s. It is used to craft the second tier of Wasteland tools.")
                    .insert(LCCBlocks.cobbled_fortstone.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(LCCBlocks.fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.fortstone).link)
                    .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                    .insertLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.cobbled_fortstone) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.cobbled_fortstone) })
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
        "dealing 2.5 hearts of damage. However, it has a slower attack speed than other swords."
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a resource block introduced in %s that can be obtained by surrounding a regular %s in %s in the %s biome. It can be broken down into 9 %s which is used to craft the third tier of %s tools as well as %s %s. It must be mined with a %s or higher.")
                    .insert(LCCBlocks.rusted_iron_blocks.values.last().name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Blocks.IRON_BLOCK.name, KnowledgeArticleIdentifier.ofBlock(Blocks.IRON_BLOCK).link)
                    .insertLink(Blocks.WATER.name, KnowledgeArticleIdentifier.ofBlock(Blocks.WATER).link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(LCCItems.iron_oxide.name, KnowledgeArticleIdentifier.ofItem(LCCItems.iron_oxide).link)
                    .insert("Wasteland")
                    .insert("Wasteland")
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                    .insertLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Stages")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("An iron block in any Wasteland biome will slowly become rusted if it meets all the following criteria:"))
                .addFragment(KnowledgeArticleBulletedFragmentBuilder()
                    .add(KnowledgeArticleParagraphFragmentBuilder("Has at least one orthogonally adjacent block of water."))
                    .add(KnowledgeArticleParagraphFragmentBuilder("Has at least three orthogonally adjacent blocks of either water or a rusted iron block of any stage."))
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Water sources, flowing water and waterlogged blocks are all considered as water, but any water below the iron block is not counted as it does not directly touch the block."))
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Below is an ordered list of the rusting stages of an iron block:"))
                .addFragment(KnowledgeArticleListFragmentBuilder()
                    .add(*LCCBlocks.rusted_iron_blocks.values.map(KnowledgeArticleIdentifier::ofBlock).toTypedArray(), reroute = false, link = false)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.rusted_iron_blocks.values.flatMap { b -> it.da.recipes.findRecipes(b) } })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.rusted_iron_blocks.values.flatMap { b -> it.da.recipes.findUsages(b) } })
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
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a resource introduced in %s. It can be crafted in groups of 9 by placing a %s in a crafting table. It is used to craft the third tier of %s tools as well as %s %s.")
                    .insert(LCCItems.iron_oxide.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(LCCBlocks.rusted_iron_blocks.values.last().name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()).link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insert("Wasteland")
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.iron_oxide) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.iron_oxide) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = true))
            .tags("Wasteland", "Materials", "Rusted Iron", "Resources")
    }

    val item_rusty_iron_sword by entry(::initialiser) { generateWastelandSwordArticle(
        LCCItems.rusty_iron_sword,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleParagraphFragmentBuilder("Being made out of a worn material, this sword has a much lower durability than one made of pure iron."))
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
        KnowledgeArticleParagraphFragmentBuilder("Being made out of a worn material, this pickaxe has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_shovel by entry(::initialiser) { generateWastelandShovelArticle(
        LCCItems.rusty_iron_shovel,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleParagraphFragmentBuilder("Being made out of a worn material, this shovel has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_axe by entry(::initialiser) { generateWastelandAxeArticle(
        LCCItems.rusty_iron_axe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleParagraphFragmentBuilder("Being made out of a worn material, this axe has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_hoe by entry(::initialiser) { generateWastelandHoeArticle(
        LCCItems.rusty_iron_hoe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleParagraphFragmentBuilder("Being made out of a worn material, this hoe has a much lower durability than one made of pure iron."))
    } }

    val item_crowbar by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.crowbar)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The %s is a multi-purpose tool introduced in %s. It can be used for combat, shattering different %s blocks, and salvaging blocks that generate in the %s. It can be crafted with %s, or found as loot in %s structures.")
                    .insert(LCCItems.crowbar.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Blocks.GLASS.name, KnowledgeArticleIdentifier.ofBlock(Blocks.GLASS).link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(LCCItems.iron_oxide.name, KnowledgeArticleIdentifier.ofItem(LCCItems.iron_oxide).link)
                    .insert/*Link*/("tent"/*, KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.wasteland_tent).link*/)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss deal 1.5 hearts of damage, which limits its usefulness as a weapon. However, when a critical hit is landed on a mob, it is %s for 0.7 seconds. This gives a window of opportunity to perform another critical hit and chain many stuns together.")
                    .insert(LCCItems.crowbar.name)
                    .insertLink("stunned", KnowledgeArticleIdentifier.ofEffect(LCCEffects.stun).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("This stun is also applied against other players in PvP, but the stun is reduced to 0.4 seconds.")
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("A select list of blocks marked as salvageable will generate naturally in the Wasteland. As they have not been placed down by the player, they only have a chance (usually 30%% + 10%% per level of %s) to be picked up when mined with their respective tool. When a %s is used, this drop chance increases to 100%%.")
                    .insertLink(TranslatableText(Enchantments.FORTUNE.translationKey), KnowledgeArticleIdentifier.ofEnchant(Enchantments.FORTUNE).link)
                    .insert(LCCItems.crowbar.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Below is a list of salvageable blocks:")
                )
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Salvageable")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Shattering")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s, %s and all %s of %s can be shattered into %s by right clicking with a %s in hand. %s and %s can also be shattered into %s.")
                    .insert(Blocks.GLASS.name)
                    .insertLink(Blocks.TINTED_GLASS.name, KnowledgeArticleIdentifier.ofBlock(Blocks.TINTED_GLASS).link)
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                    .insert("Stained Glass")
                    .insertLink(LCCBlocks.shattered_glass.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_glass).link)
                    .insert(LCCItems.crowbar.name)
                    .insertLink("Glass Panes", KnowledgeArticleIdentifier.ofBlock(Blocks.GLASS_PANE).link)
                    .insert("Stained Glass Panes")
                    .insertLink("Shattered Glass Panes", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_glass_pane).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Durability")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss have ${LCCItems.crowbar.maxDamage} durability points. Attacking or shattering glass costs 1 durability, while mining a block costs 2 durability.")
                    .insert(LCCItems.crowbar.name)
                )
            )
            .addSection(getRepairingSection(LCCItems.crowbar, LCCItems.iron_oxide.name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.crowbar) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.crowbar) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = true))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Wasteland Optimal", "Salvageable", "Rusted Iron", "Tools")
    }

    val block_shattered_glass by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.shattered_glass)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a transparent block introduced in %s that is obtained by smashing (right clicking) %s, %s and all %s of %s with a %s. It is extremely fragile and breaks easily when stepped on or shot with a projectile.")
                    .insert(LCCBlocks.shattered_glass.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(Blocks.GLASS.name, KnowledgeArticleIdentifier.ofBlock(Blocks.GLASS).link)
                    .insertLink(Blocks.TINTED_GLASS.name, KnowledgeArticleIdentifier.ofBlock(Blocks.TINTED_GLASS).link)
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                    .insert("Stained Glass")
                    .insertLink(LCCItems.crowbar.name, KnowledgeArticleIdentifier.ofItem(LCCItems.crowbar).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("This is the full block version of %s.")
                    .insertLink("Shattered Glass Panes", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_glass_pane).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Types")
                .addFragment(KnowledgeArticleListFragmentBuilder()
                    .add(
                        KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_glass),
                        KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_tinted_glass),
                        *LCCBlocks.stained_shattered_glass.values.map(KnowledgeArticleIdentifier::ofBlock).toTypedArray(),
                        reroute = false, link = false
                    )
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.shattered_glass) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.shattered_tinted_glass) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass.values.flatMap { b -> it.da.recipes.findRecipes(b) } })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.shattered_glass) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.shattered_tinted_glass) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass.values.flatMap { b -> it.da.recipes.findUsages(b) } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .about(LCCBlocks.shattered_tinted_glass, *LCCBlocks.stained_shattered_glass.values.toTypedArray())
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_tinted_glass), LCCBlocks.shattered_tinted_glass.name)
            .apply { LCCBlocks.stained_shattered_glass.values.forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(it), it.name) } }
            .tags("Shattered Glass", "Colored", "Glass")
    }

    val block_shattered_glass_pane by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.shattered_glass_pane)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a transparent block introduced in %s that is obtained by smashing (right clicking) %s and all %s of %s with a %s. It is extremely fragile and breaks easily when stepped on or shot with a projectile.")
                    .insert(LCCBlocks.shattered_glass_pane.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Glass Panes", KnowledgeArticleIdentifier.ofBlock(Blocks.GLASS_PANE).link)
                    .insert({ IncludedTranslatableText(it).translation("colors", "en_us").translation("colours", "en_gb") })
                    .insert("Stained Glass Pane")
                    .insertLink(LCCItems.crowbar.name, KnowledgeArticleIdentifier.ofItem(LCCItems.crowbar).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("This is the glass pane version of %s.")
                    .insertLink(LCCBlocks.shattered_glass.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_glass).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Types")
                .addFragment(KnowledgeArticleListFragmentBuilder()
                    .add(
                        KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_glass_pane),
                        *LCCBlocks.stained_shattered_glass_pane.values.map(KnowledgeArticleIdentifier::ofBlock).toTypedArray(),
                        reroute = false, link = false
                    )
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.shattered_glass_pane) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass_pane.values.flatMap { b -> it.da.recipes.findRecipes(b) } })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.shattered_glass_pane) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass_pane.values.flatMap { b -> it.da.recipes.findUsages(b) } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .about(*LCCBlocks.stained_shattered_glass_pane.values.toTypedArray())
            .apply { LCCBlocks.stained_shattered_glass_pane.values.forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(it), it.name) } }
            .tags("Shattered Glass", "Colored", "Glass", "Glass Panes")
    }

    val block_cracked_mud by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.cracked_mud)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block originally introduced in %s and reintroduced in %s that covers the surface of the %s. %s can be hydrated into %s, a process which can be reversed.")
                    .insert(LCCBlocks.cracked_mud.name)
                    .insertLink(LCCVersion.YAM_1.label, LCCVersion.YAM_1.page.link)
                    .insertLink(LCCVersion.LCC_FABRIC_0_1_0.label, LCCVersion.LCC_FABRIC_0_1_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insert(LCCBlocks.cracked_mud.name)
                    .insertLink(LCCBlocks.mud.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.mud).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Interactions")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("In %s, %s can be converted into %s by being placed in or adjacent to %s.")
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_1.label, LCCVersion.LCC_FABRIC_0_5_1.page.link)
                    .insert(LCCBlocks.cracked_mud.name)
                    .insert(LCCBlocks.mud.name)
                    .insertLink(Blocks.WATER.name, KnowledgeArticleIdentifier.ofBlock(Blocks.WATER).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("To slowly dry %s back into %s, it can be left in the %s without adjacent %s. %s can be quickly dried by smelting it in a %s or %s.")
                    .insert(LCCBlocks.mud.name)
                    .insert(LCCBlocks.cracked_mud.name)
                    .insert("Wasteland")
                    .insert(Blocks.WATER.name)
                    .insert(LCCBlocks.mud.name)
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.kiln.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.kiln).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.cracked_mud) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.cracked_mud) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Pickaxe Recommended", "Mud")
    }

    val block_mud by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.mud)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a block originally introduced in %s and reintroduced in %s which drastically changes the movement of mobs and players walking on it. %s can be dried into %s, a process which can be reversed.")
                    .insert(LCCBlocks.mud.name)
                    .insertLink(LCCVersion.YAM_1.label, LCCVersion.YAM_1.page.link)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_1.label, LCCVersion.LCC_FABRIC_0_5_1.page.link)
                    .insert(LCCBlocks.mud.name)
                    .insertLink(LCCBlocks.cracked_mud.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Movement")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("In %s, %s is a slippery block similar to %s. The speed of an entity walking on this block changes over time, giving the effect of wading through the block.")
                    .insert(LCCVersion.LCC_FABRIC_0_5_1.label)
                    .insert(LCCBlocks.mud.name)
                    .insertLink(Blocks.ICE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.ICE).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Previously in %s, %s was a ridiculously slippery block, allowing entities walking on it to wildly accelerate and travel huge distances very quickly.")
                    .insert(LCCVersion.YAM_1.label)
                    .insert(LCCBlocks.mud.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Interactions")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("When left in the %s without adjacent %s, %s will slowly dry into %s. %s can also be quickly dried by smelting it in a %s or %s.")
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(Blocks.WATER.name, KnowledgeArticleIdentifier.ofBlock(Blocks.WATER).link)
                    .insert(LCCBlocks.mud.name)
                    .insert(LCCBlocks.cracked_mud.name)
                    .insert(LCCBlocks.mud.name)
                    .insertLink(Blocks.FURNACE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.FURNACE).link)
                    .insertLink(LCCBlocks.kiln.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.kiln).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s can be rehydrated into %s by being placed in or adjacent to %s.")
                    .insert(LCCBlocks.cracked_mud.name)
                    .insert(LCCBlocks.mud.name)
                    .insertLink(Blocks.WATER.name, KnowledgeArticleIdentifier.ofBlock(Blocks.WATER).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("1.19")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("This block will likely be merged with the Mud Block from Minecraft 1.19 once released. It is planned to migrate the functionality of drying into %s to this new block.")
                    .insert(LCCBlocks.cracked_mud.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.mud) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.mud) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false).alterStats { it.put(it.keys.elementAt(5), listOf(KnowledgeArticleParagraphFragmentBuilder("Varies"))) })
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Shovel Recommended", "Mud", "Movement")
    }

    val block_cracked_mud_pressure_plate by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.cracked_mud_pressure_plate)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The %s is a pressure plate introduced in %s. It is activated when sprinted on or landed on from a height, and will deactivate once there are no entities on it.")
                    .insert(LCCBlocks.cracked_mud_pressure_plate.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Obtaining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss can be generated in the %s as the activation mechanism of landmines, which will immediately activate the %s that generates below. If the pressure plate is deactivated, the %s will instantly detonate.")
                    .insert(LCCBlocks.cracked_mud_pressure_plate.name)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(LCCBlocks.improvised_explosive.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.improvised_explosive).link)
                    .insert(LCCBlocks.improvised_explosive.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("It can also be crafted with 2 %s blocks.")
                    .insertLink(LCCBlocks.cracked_mud.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.cracked_mud_pressure_plate) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.cracked_mud_pressure_plate) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Pickaxe Recommended", "Mud", "Pressure Plate")
    }

    val item_iron_oxide_nugget by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.iron_oxide_nugget)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss are a crafting material introduced in %s. It can be crafted in groups of 9 by placing a %s in a crafting table.")
                    .insert(LCCItems.iron_oxide_nugget.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(LCCItems.iron_oxide.name, KnowledgeArticleIdentifier.ofItem(LCCItems.iron_oxide).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.iron_oxide_nugget) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.iron_oxide_nugget) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = true))
            .tags("Wasteland", "Materials", "Rusted Iron", "Resources")
    }

    val block_spikes by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.spikes)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s are a set of blocks introduced in %s. They can be placed on a block to face in any direction and will deal damage to entities touching the spiked side whilst slowing their movement speed.")
                    .insert(LCCBlocks.spikes.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Obtaining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("All %s can be found in the %s in the form of spike traps. They are square holes that are easy to spot on the surface, with a bed of a random type of %s at the bottom. Plain %s are the most common to find in spike traps.")
                    .insert(LCCBlocks.spikes.name)
                    .insertLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    .insert(LCCBlocks.spikes.name)
                    .insert(LCCBlocks.spikes.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Regular %s can be crafted with %s and %s, and then upgraded to another type with their respective ingredient:")
                    .insert(LCCBlocks.spikes.name)
                    .insertLink(LCCItems.iron_oxide.name, KnowledgeArticleIdentifier.ofItem(LCCItems.iron_oxide).link)
                    .insertLink(LCCItems.iron_oxide_nugget.name, KnowledgeArticleIdentifier.ofItem(LCCItems.iron_oxide_nugget).link)
                )
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Spike Type"))
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Crafting Ingredient"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleStackFragmentBuilder(ItemStack(LCCBlocks.bleeding_spikes)))
                        addCell(KnowledgeArticleStackFragmentBuilder(ItemStack(LCCItems.heart_half[HeartType.RED])))
                    }
                    .addRow {
                        addCell(KnowledgeArticleStackFragmentBuilder(ItemStack(LCCBlocks.poison_spikes)))
                        addCell(KnowledgeArticleStackFragmentBuilder(ItemStack(LCCItems.stinger)))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Damage")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("All %s will constantly deal damage to an entity while it is in motion. The exact amount of damage received starts at half a heart and increases exponentially depending on the entity's current speed.")
                    .insert(LCCBlocks.spikes.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s deal increased damage to entities by modifying the calculated damage to the power of 1.2 and then doubling it.")
                    .insert(LCCBlocks.bleeding_spikes.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("When mobs and players are damaged by %s, a poison effect is applied for 8.5 seconds.")
                    .insert(LCCBlocks.poison_spikes.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Types")
                .addFragment(KnowledgeArticleListFragmentBuilder()
                    .add(
                        *LCCBlocks.all.values.filterIsInstance<SpikesBlock>().map(KnowledgeArticleIdentifier::ofBlock).toTypedArray(),
                        reroute = false, link = false
                    )
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.all.values.filterIsInstance<SpikesBlock>().flatMap { b -> it.da.recipes.findRecipes(b) } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .about(*LCCBlocks.all.values.filterIsInstance<SpikesBlock>().toTypedArray())
            .apply { LCCBlocks.all.values.filterIsInstance<SpikesBlock>().forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(it), it.name) } }
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Rusted Iron")
    }

    val block_rusted_iron_bars by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.rusted_iron_bars)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a decorative block introduced in %s. They are the %s equivalent of %s.")
                    .insert(LCCBlocks.rusted_iron_bars.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink(LCCItems.iron_oxide.name, KnowledgeArticleIdentifier.ofItem(LCCItems.iron_oxide).link)
                    .insertLink(Blocks.IRON_BARS.name, KnowledgeArticleIdentifier.ofBlock(Blocks.IRON_BARS).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.rusted_iron_bars) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.rusted_iron_bars) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Rusted Iron", "Iron Bars")
    }

    val item_tongue_tissue by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.tongue_tissue)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a material introduced in %s. They are rarely dropped by %s when their tongue is extended.")
                    .insert(LCCItems.tongue_tissue.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Consumers", KnowledgeArticleIdentifier.ofEntity(LCCEntities.consumer).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.tongue_tissue) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.tongue_tissue) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = true))
            .tags("Wasteland", "Materials", "Mob Drops", "Rare Mob Drops")
    }

    val item_altar_challenge_key by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.altar_challenge_key)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an item introduced in %s. It allows the player to start a %s challenge, which give %s upon completion.")
                    .insert(LCCItems.altar_challenge_key.name)
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insert/*Link*/("Sapphire Altar"/*, KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar).link*/)
                    .insertLink("sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Usage")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Once a %s block is filled with sapphires that the player wishes to wager, the block can be right clicked with an %s to start the challenge. The key is consumed in the process.")
                    .insertLink(LCCBlocks.sapphire_altar.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar).link)
                    .insert(LCCItems.altar_challenge_key.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCItems.altar_challenge_key) })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCItems.altar_challenge_key) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = true))
            .tags("Wasteland", "Rusted Iron", "Sapphire Altar")
    }

    //TODO
    val structure_sapphire_altar by entry(::initialiser) {
        KnowledgeArticleBuilder(/*KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar)*/KnowledgeArticleIdentifier(Identifier("structure"), LCC.id("sapphire_altar")), "Sapphire Altar")
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The Sapphire Altar is a structure introduced in %s. It is a common structure found in all %s biomes that presents a random challenge to players. Completing this challenge gives %s as a reward, used to make the final tier of %s tools and %s.")
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink("sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire).link)
                    .insert("Wasteland")
                    .insert({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Structure")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The base of the structure is comprised of %s, with a staircase of %s on one side leading to the top of the structure.")
                    .insertLink(LCCBlocks.sapphire_altar_brick.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar_brick).link)
                    .insertLink(LCCBlocks.sapphire_altar_brick_stairs.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar_brick_stairs).link)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The top of the structure is entirely dependent on the challenge posed by the altar, but they will all generate with one %s block. This block provides an interface to the structure itself.")
                    .insertLink(LCCBlocks.sapphire_altar.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.sapphire_altar).link)
                )
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleSectionBuilder("Image", "info")
                .addFragment(KnowledgeArticleImageFragmentBuilder()
                    .addStatic("structure/sapphire_altar", KnowledgeArticleParagraphFragmentBuilder("A Sapphire Altar generated in the %s.")
                        .insertLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                    )
                )
            )
            .tags("Wasteland", "Sapphire Altar")
    }

    val block_bounce_pad by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.bounce_pad)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The %s is a block originally introduced in %s and reintroduced in %s that launches players and other entities in the direction it is placed. %ss combine the bouncing mechanics of the %s with the propulsion properties of the %s used to craft them.")
                    .insert(LCCBlocks.bounce_pad.name)
                    .insertLink(LCCVersion.YAM_1.label, LCCVersion.YAM_1.page.link)
                    .insertLink(LCCVersion.LCC_FABRIC_0_1_0.label, LCCVersion.LCC_FABRIC_0_1_0.page.link)
                    .insert(LCCBlocks.bounce_pad.name)
                    .insertLink(LCCBlocks.rubber_piston.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rubber_piston).link)
                    .insertLink(LCCBlocks.soaking_soul_sand.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.soaking_soul_sand).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Movement")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("When an entity enters the activation zone, which is the size of a full block, they are snapped into the center of the %s and all previous momentum is removed. The entity is then launched in the direction the pad was placed.")
                    .insert(LCCBlocks.bounce_pad.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("The speed of the launch depends on the red notch on the side of the %s. This setting has a range of 1 to 5 and can be changed by right clicking the %s. Below is a table showing the strength of each setting:")
                    .insert(LCCBlocks.bounce_pad.name)
                    .insert(LCCBlocks.bounce_pad.name)
                )
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Setting"))
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Velocity"))
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Approximate Height (in m)"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("1"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("1.0"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("6.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("2"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("1.4"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("11.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("3"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("1.8"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("17.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("4"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("2.2"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("24.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("5"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("2.6"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("32.0"))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Legacy")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("In %s, there were five %s varieties of %s ranging from blue to red. They could only be placed facing upwards. Strength values for these are shown below:")
                    .insert(LCCVersion.YAM_1.label)
                    .insert({ IncludedTranslatableText(it).translation("colored", "en_us").translation("coloured", "en_gb") })
                    .insert(LCCBlocks.bounce_pad.name)
                )
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("%s")
                            .insert({ IncludedTranslatableText(it).translation("Color", "en_us").translation("Colour", "en_gb") })
                        )
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Velocity"))
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Approximate Height (in m)"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("Blue"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("0.8"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("5.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("Green"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("1.2"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("9.0"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("Yellow"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("1.6"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("14.0"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("Orange"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("2.0"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("20.0"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleParagraphFragmentBuilder("Red"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("3.0"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder("39.0"))
                    }
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("In %s, a %s could be placed directionally and had a GUI with a single slot that could be accessed by right clicking. The GUI also contained a slider that adjusted the power of the thrust and two others sliders underneath which controlled the lean in the other two directions. The range of these sliders were dictated by the amount of %s placed in the inventory of the %s.")
                    .insertLink(LCCVersion.AIMAGG_ALPHA_1_6.label, LCCVersion.AIMAGG_ALPHA_1_6.page.link)
                    .insert(LCCBlocks.bounce_pad.name)
                    .insertLink("Iron Springs", KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("aimagg", "iron_spring")).link)
                    .insert(LCCBlocks.bounce_pad.name)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("In %s, %ss function similarly to their Fabric renditions. However, they can only be placed facing upwards.")
                    .insertLink(LCCVersion.LCC_FORGE_ALPHA_1_0.label, LCCVersion.LCC_FORGE_ALPHA_1_0.page.link)
                    .insert(LCCBlocks.bounce_pad.name)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.bounce_pad) })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleParagraphFragmentBuilder("Recipe before %s.")
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_1.label, LCCVersion.LCC_FABRIC_0_5_1.page.link), obsolete = true) { e ->
                        listOf(OverrideRecipeJsonProvider.fromFactory(RecipeSerializer.SHAPED, ShapedRecipeJsonFactory.create(LCCBlocks.bounce_pad, 6)
                            .pattern("rwr")
                            .pattern("ipi")
                            .pattern("sss")
                            .input('r', Items.REPEATER)
                            .input('w', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                            .input('i', Blocks.IRON_BLOCK)
                            .input('p', Blocks.PISTON)
                            .input('s', LCCBlocks.soaking_soul_sand)
                            .criterion("has_soaking_soul_sand", InventoryChangedCriterion.Conditions.items(LCCBlocks.soaking_soul_sand))
                        , { offerTo(it) }) {
                            val items = arrayOf(Items.REPEATER, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK, Blocks.PISTON, LCCBlocks.soaking_soul_sand)
                            it.add("translations", e.translator.itemTranslationsJson(*items))
                            it.add("links", e.linker.itemLinksJson(*items))
                        })
                })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleParagraphFragmentBuilder("Recipe before %s.")
                    .insertLink(LCCVersion.LCC_FABRIC_0_4_2.label, LCCVersion.LCC_FABRIC_0_4_2.page.link), obsolete = true) { e ->
                        listOf(OverrideRecipeJsonProvider.fromFactory(RecipeSerializer.SHAPED, ShapedRecipeJsonFactory.create(LCCBlocks.bounce_pad)
                            .pattern("rwr")
                            .pattern("ipi")
                            .pattern("sss")
                            .input('r', Items.REPEATER)
                            .input('w', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                            .input('i', Blocks.IRON_BLOCK)
                            .input('p', Blocks.PISTON)
                            .input('s', LCCBlocks.soaking_soul_sand)
                            .criterion("has_soaking_soul_sand", InventoryChangedCriterion.Conditions.items(LCCBlocks.soaking_soul_sand))
                        , { offerTo(it) }) {
                            val items = arrayOf(Items.REPEATER, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK, Blocks.PISTON, LCCBlocks.soaking_soul_sand)
                            it.add("translations", e.translator.itemTranslationsJson(*items))
                            it.add("links", e.linker.itemLinksJson(*items))
                        })
                    })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleParagraphFragmentBuilder("Recipe before %s.")
                    .insertLink(LCCVersion.LCC_FABRIC_0_2_0.label, LCCVersion.LCC_FABRIC_0_2_0.page.link), obsolete = true) { e ->
                        listOf(OverrideRecipeJsonProvider.fromFactory(RecipeSerializer.SHAPED, ShapedRecipeJsonFactory.create(LCCBlocks.bounce_pad, 8)
                            .pattern("rwr")
                            .pattern("ipi")
                            .pattern("sss")
                            .input('r', Items.REPEATER)
                            .input('w', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                            .input('i', Blocks.IRON_BLOCK)
                            .input('p', Blocks.PISTON)
                            .input('s', LCCBlocks.soaking_soul_sand)
                            .criterion("has_soaking_soul_sand", InventoryChangedCriterion.Conditions.items(LCCBlocks.soaking_soul_sand))
                        , { offerTo(it) }) {
                            val items = arrayOf(Items.REPEATER, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK, Blocks.PISTON, LCCBlocks.soaking_soul_sand)
                            it.add("translations", e.translator.itemTranslationsJson(*items))
                            it.add("links", e.linker.itemLinksJson(*items))
                        })
                    })
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.bounce_pad) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .tags("Movement", "Rubber", "Soaking Soul Sand", "Tools")
    }*/

    fun initialiser(input: KnowledgeArticleBuilder, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out KnowledgeArticleBuilder, out KnowledgeArticleBuilder>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.afterInit() }
    }

    override fun defaultProperties(name: String) = Unit

    fun getArticlesAbout(topic: Any) = this.all.filter { (k, v) -> v.about.contains(topic) }

    //Shared Code

    /*private val introduction = "Introduction"

    private fun generatePlasticDespawningSection(topic: Item) = KnowledgeArticleSectionBuilder("Despawning")
        .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s, like all plastic-based items, do not despawn after 5 minutes when dropped in the world.")
            .insert(topic.name)
        )

    private fun generatePlasticArticle(article: KnowledgeArticleBuilder, topic: Item, plasticiser: Item) {
        article
            .addSection(generatePlasticDespawningSection(topic))
            .addSection(KnowledgeArticleSectionBuilder({ IncludedTranslatableText(it).translation("Coloring", "en_us").translation("Colouring", "en_gb") })
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s and %s can be crafted into different %s by adding dyes to their refining recipe. Similar to dyeing leather %s, multiple dyes can be applied and the resulting %s will be a mix (average) of the provided dyes. However, dyes apply a much more vibrant and saturated %s to plastic. You can view %s codes in the table below:")
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
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder("Item"))
                        addHeadingCell(KnowledgeArticleParagraphFragmentBuilder { IncludedTranslatableText(it).translation("Color Code", "en_us").translation("Colour Code", "en_gb") })
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
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(topic) })
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

    private fun legacyOilRecipe() = KnowledgeArticleRecipeFragmentBuilder(KnowledgeArticleParagraphFragmentBuilder("Recipe before %s.")
        .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link), obsolete = true) { e ->
            listOf(OverrideRecipeJsonProvider(LCCRecipeSerializers.refining_shapeless, e.da.recipes.findRecipes(LCCItems.asphalt_bucket).first()) {
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
        listOf(it.da.recipes.get(recipe)!!)
    }

    private fun getDeadwoodToolNote(fallback: Item) = KnowledgeArticleParagraphFragmentBuilder("If not all planks provided are %s, a standard %s will be crafted instead.")
        .insert(LCCBlocks.deadwood_planks.name)
        .insertLink(fallback.name, KnowledgeArticleIdentifier.ofItem(fallback).link)

    private fun getRepairingSection(tool: Item, repair: Text) = KnowledgeArticleSectionBuilder("Repairing")
        .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss can be repaired with %s in an %s. Like all tools, two %ss can also be fused in crafting or a %s.")
            .insert(tool.name)
            .insert(repair)
            .insertLink(Blocks.ANVIL.name, KnowledgeArticleIdentifier.ofBlock(Blocks.ANVIL).link)
            .insert(tool.name)
            .insertLink(Blocks.GRINDSTONE.name, KnowledgeArticleIdentifier.ofBlock(Blocks.GRINDSTONE).link)
        )

    private fun generateWastelandSwordArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, damage: String = "dealing ${(item as SwordItem).attackDamage.div(2.0).plus(0.5).decimalFormat(1)} hearts of damage.", recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent sword, $damage")
                    .insert(item.name)
                    .insertLink("sword", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/sword" } })
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss deal %s, which deal full damage against Wasteland-based creatures.")
                    .insert(item.name)
                    .insertLink("Wasteland Damage", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                )
            )
            .addSection(getRepairingSection(item, ingredient.asItem().name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(recipe)
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Combat", "Wasteland Damage", tag, "Swords", "Tools")

    private fun generateWastelandPickaxeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, example: KnowledgeArticleParagraphFragmentBuilder.() -> Unit, example2: KnowledgeArticleParagraphFragmentBuilder.() -> Unit, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent pickaxe, but can mine %s.")
                    .insert(item.name)
                    .insertLink("pickaxe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/pickaxe" } })
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                    .apply(example)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss are %s, allowing them to harvest blocks that require a Wasteland pickaxe to mine, such as %s.")
                    .insert(item.name)
                    .insertLink("Wasteland Effective", KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")).link)
                    .apply(example2)
                )
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("Some blocks, such as %s, break faster with a Wasteland Effective pickaxe but do not require one.")
                    .insertLink(LCCBlocks.cracked_mud.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                )
            )
            .addSection(getRepairingSection(item, ingredient.asItem().name))
            .addSection(KnowledgeArticleSectionBuilder("Crafting Recipes")
                .addFragment(recipe)
            )
            .addSection(KnowledgeArticleSectionBuilder("Crafting Usages")
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Wasteland Optimal", tag, "Pickaxes", "Tools")

    private fun generateWastelandShovelArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent shovel.")
                    .insert(item.name)
                    .insertLink("shovel", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/shovel" } })
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss are %s, allowing them to break blocks that require a Wasteland shovel, such as %s, faster.")
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
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Shovels", "Tools")

    private fun generateWastelandAxeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is an %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent axe.")
                    .insert(item.name)
                    .insertLink("axe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/axe" } })
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss are %s, allowing them to break blocks that require a Wasteland axe, such as %s, faster.")
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
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Axes", "Tools")

    private fun generateWastelandHoeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item) }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent hoe.")
                    .insert(item.name)
                    .insertLink("hoe", { KnowledgeArticleWebLinkBuilder { "https://minecraft.fandom.com/wiki/hoe" } })
                    .insertLink(LCCVersion.LCC_FABRIC_0_5_0.label, LCCVersion.LCC_FABRIC_0_5_0.page.link)
                    .insertLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                    .insertLink(ingredient.asItem().name, KnowledgeArticleIdentifier.ofItem(ingredient.asItem()).link)
                )
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addFragment(KnowledgeArticleParagraphFragmentBuilder("%ss are %s, allowing them to break blocks that require a Wasteland hoe, such as %s, faster.")
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
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(item) })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Hoes", "Tools")*/

}
