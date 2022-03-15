package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.abstracts.types.IronRustType
import com.joshmanisdabomb.lcc.block.SpikesBlock
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.*
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleBlockInfoSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleChangelogSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.data.json.recipe.OverrideRecipeJsonProvider
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeContentExtensions
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeContentExtensions.addWastelandEffectivityLink
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeContentExtensions.addWastelandLink
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeContentExtensions.getRepairingSection
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeExtensions
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeExtensions.addLink
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeExtensions.boilerplate
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import com.joshmanisdabomb.lcc.directory.*
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.time.LocalDateTime

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
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.sapphire_altar))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.sapphire_altar))
            .boilerplate(LCCBlocks.sapphire_altar)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 5, 23, 14, 0), LocalDateTime.of(2021, 11, 12, 19, 35, 0))
            .tags("Wasteland", "Sapphire Altar", "Sapphire Pickaxe Required")
    }

    /*val lcc_fabric_0_5_0 by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCVersion.LCC_FABRIC_0_5_0.page, LCCVersion.LCC_FABRIC_0_5_0.label)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText(LCCVersion.LCC_FABRIC_0_5_0.getKnowledgeConstants.introduction())
                    addText("Loosely Connected Concepts 0.5.0")
                    addText("fifteenth")
                    addText("58th")
                    addLink(LCCVersion.LCC_FABRIC_0_4_4.label, LCCVersion.LCC_FABRIC_0_4_4.page.link)
                )
                .addParagraph {
                    addFormatText("The focus of LCC 0.5.x is the %s biome, a hostile and dismal expanse filled with vicious traps and difficult mobs.",
                    addLink("Wasteland", KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Gameplay")
                .addParagraph {
                    addFormatText("In this version, the Wasteland biome is split into two sub-biomes, %s and %s. The barrens are flat terrain where traps, resources and larger quantities of mobs are commonly found. The spiked regions are generally safer, but provide less rewards and are more challenging to navigate.",
                    addLink("Wasteland Barrens", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens).link)
                    addLink("Wasteland Spikes", KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_spikes).link)
                )
                .addParagraph {
                    addFormatText("Some blocks native to the Wasteland require Wasteland tools to mine, and most mobs native to the Wasteland deal increased damage through non-Wasteland %s and take reduced damage from non-Wasteland equipment. The tool progression starts at %s, a new wood type which can be found in clusters scattered across the barrens. A %s is required to mine %s which appears on the surface in the spikes sub-biome. Players must then convert their blocks of iron into %s by submerging them in water in any Wasteland sub-biome. When the iron has completely rusted, it can be mined with a %s and crafted into tools, armour and %s. These keys must be used to activate challenges posed by %s structures to obtain %s, the current final tier of Wasteland equipment.",
                    addText({ IncludedTranslatableText(it).translation("armor", "en_us").translation("armour", "en_gb") })
                    addLink(LCCBlocks.deadwood.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood_log).link)
                    addLink(LCCItems.deadwood_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.deadwood_pickaxe).link)
                    addLink(LCCBlocks.fortstone.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.fortstone).link)
                    addLink("rusted iron blocks", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()).link)
                    addLink(LCCItems.fortstone_pickaxe.name, KnowledgeArticleIdentifier.ofItem(LCCItems.fortstone_pickaxe).link)
                    addLink("Altar Challenge Keys", KnowledgeArticleIdentifier.ofItem(LCCItems.altar_challenge_key).link)
                    .insert/*Link*/("Sapphire Altar"/*, KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar).link*/)
                    addLink("sapphires", KnowledgeArticleIdentifier.ofItem(LCCItems.sapphire).link)
                )
                .addParagraph {
                    addFormatText("Players can also find treasure in the Wasteland. %s are structures that spawn in the barrens which often contain loot. Obtained items can range from sticks to %s, which can permanently increase your maximum life. '%s' can also be found in the barrens, grouped in clusters. Breaking these will sometimes drop gold pieces, iron pieces or the aforementioned heart containers.",
                    .insert/*Link*/("Tents"/*, KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.wasteland_tent).link*/)
                    addLink("heart containers", KnowledgeArticleIdentifier(LCC.id("concepts"), LCC.id("heart_containers")).link)
                    addLink("Deposits", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deposit).link)
                )
                .addParagraph {
                    addFormatText("The Wasteland is now home to three dangerous mobs. The %s is a baby version of a regular Minecraft skeleton that strafes and shoots faster and has a much smaller hitbox. %s are creatures that extends its tongue out to latch on to its prey. Finally, %s nests spawn in all parts of the Wasteland. Wasps are generally neutral but will vehemently defend their nests from perceived threats and can get randomly aggressive at nearby targets.",
                    addLink(LCCEntities.baby_skeleton.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.baby_skeleton).link)
                    addLink("Consumers", KnowledgeArticleIdentifier.ofEntity(LCCEntities.consumer).link)
                    addLink(LCCEntities.wasp.name, KnowledgeArticleIdentifier.ofEntity(LCCEntities.wasp).link)
                )
                .addParagraph {
                    addFormatText("%s traps and landmines hooked up to camouflaged %s are often placed in Wasteland Barrens. Spikes can sometimes spawn covered in %s or %s residue. Landmines are rigged up with an %s, which leaves behind a large firey crater.",
                    addLink("Spike", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.spikes).link)
                    addLink("pressure plates", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud_pressure_plate).link)
                    addLink("bloody", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.bleeding_spikes).link)
                    addLink("poisonous", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.poison_spikes).link)
                    addLink(LCCBlocks.improvised_explosive.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.improvised_explosive).link)
                )
                .addParagraph {
                    addFormatText("Oil refining has also been modified in this version. Oil, which now spawns more commonly in the wasteland as geysers or in pockets buried under %s, now refines into three products: %s, %s and %s. Fuel can be converted to energy using a %s, or refined further into %s which can be placed to cause chain reactions of explosions, create improvised explosives (landmines) or to %s. Refined oil can be further refined into %s or %s, both of which can have its %s customised with dyes and do not despawn when dropped. Flexible plastic can be crafted into %s, which hold 2 stacks of items. Tar balls are used to create %s.",
                    addLink("cracked mud", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.cracked_mud).link)
                    addLink("Fuel Buckets", KnowledgeArticleIdentifier.ofItem(LCCItems.fuel_bucket).link)
                    addLink("Refined Oil Buckets", KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket).link)
                    addLink("tar balls", KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball).link)
                    addLink(LCCBlocks.oil_generator.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.oil_generator).link)
                    addLink(LCCBlocks.explosive_paste.name, KnowledgeArticleIdentifier.ofBlock(LCCBlocks.explosive_paste).link)
                    addLink("construct a game of Minesweeper", KnowledgeArticleIdentifier.of(LCCRegistries.altar_challenges, LCCAltarChallenges.minesweeper).link)
                    addLink(LCCItems.rigid_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic).link)
                    addLink(LCCItems.flexible_plastic.name, KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic).link)
                    addText({ IncludedTranslatableText(it).translation("color", "en_us").translation("colour", "en_gb") })
                    addLink("plastic bags", KnowledgeArticleIdentifier.ofItem(LCCItems.plastic_bag).link)
                    addLink("roads", KnowledgeArticleIdentifier.ofBlock(LCCBlocks.road).link)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Technical")
                .addParagraph {
                    addFormatText("Similar to update %s, a lot of technical changes happened behind the scenes in addition to updated gameplay changes. The lcc-base module was split into lcc-infra and lcc-hooks. The infrastructure module provides helpers to other modules without making any changes to Minecraft with mixins and access transformers. In contrast, the hooks module makes those kind of changes and gives other modules access to interact with these hooks, using methods like trait interfaces or accessor mixins.",
                    addLink("0.2.0", LCCVersion.LCC_FABRIC_0_2_0.page.link)
                )
                .addParagraph {
                    addFormatText("The data generator module was also split up to separate code specific to the content of LCC. This allows other mods in future to use lcc-data as a module to generate data for themselves.",
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Commit Messages from BitBucket")
                .addParagraph {
                    addFormatText(LCCVersion.LCC_FABRIC_0_5_0.description))
            )
            .addSection(KnowledgeArticleVersionChangelogSectionBuilder(LCCVersion.LCC_FABRIC_0_5_0))
            .tags("LCC")
    }*/

    val block_explosive_paste by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.explosive_paste)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block introduced in %s that explodes shortly after being triggered by another explosion. It connects to other pieces of %s placed nearby, similar to %s.",
                        { addText(LCCBlocks.explosive_paste.name) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addText(LCCBlocks.explosive_paste.name) },
                        { addLink(Items.REDSTONE) }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Strength")
                .addParagraph {
                    addFormatText("The strength of the explosion caused by a piece of %s depends on the amount of connections to other adjacent pieces of %s.",
                        { addText(LCCBlocks.explosive_paste.name) },
                        { addText(LCCBlocks.explosive_paste.name) }
                    )
                }
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Adjacent Connections"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Strength"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Equivalent To"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("0"))
                        addCell(KnowledgeArticleTextFragmentBuilder("2.5"))
                        addCell(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEntity(EntityType.GHAST), "Ghast_fireball").addFragment(KnowledgeArticleTextFragmentBuilder("Ghast Fireball")))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("1"))
                        addCell(KnowledgeArticleTextFragmentBuilder("3.4"))
                        addCell(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER)).addFragment(KnowledgeArticleTextFragmentBuilder(EntityType.CREEPER)))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("4.3"))
                        addCell(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofBlock(Blocks.TNT)).addFragment(KnowledgeArticleTextFragmentBuilder(Blocks.TNT)))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("3"))
                        addCell(KnowledgeArticleTextFragmentBuilder("5.2"))
                        addCell(KnowledgeArticleParagraphFragmentBuilder()
                            .addLink(KnowledgeArticleIdentifier(Identifier("block"), Identifier("bed")), "Bed")
                            .addText("/")
                            .addLink(Blocks.RESPAWN_ANCHOR)
                        )
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("4"))
                        addCell(KnowledgeArticleTextFragmentBuilder("6.1"))
                        addCell(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER), "Charged_creeper").addFragment(KnowledgeArticleTextFragmentBuilder("Charged Creeper")))
                    }
                )
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.explosive_paste))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.explosive_paste))
            .boilerplate(LCCBlocks.explosive_paste)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 9, 12, 59, 0), LocalDateTime.of(2021, 11, 12, 2, 22, 0))
            .tags("Wasteland", "Materials", "Explosives")
    }

    val item_rigid_plastic by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.rigid_plastic)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item introduced in %s as a crafting material for plastic items. It currently has no uses, but is planned to be a key crafting ingredient for computers in the future. It can be obtained by refining %s, similarly to %s.",
                        { addText(LCCItems.rigid_plastic) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket), "refined oil") },
                        { addLink(LCCItems.flexible_plastic) }
                    )
                }
            )
            .apply { KnowledgeContentExtensions.generatePlasticArticle(this, LCCItems.rigid_plastic, Items.QUARTZ) }
            .boilerplate(LCCItems.rigid_plastic, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 18, 10, 57, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Materials", "Plastic")
    }

    val item_flexible_plastic by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.flexible_plastic)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item introduced in %s as a crafting material for plastic items such as the %s. It can be obtained by refining %s, similarly to %s.",
                        { addText(LCCItems.flexible_plastic) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(LCCItems.plastic_bag) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.refined_oil_bucket), "refined oil") },
                        { addLink(LCCItems.rigid_plastic) }
                    )
                }
            )
            .apply { KnowledgeContentExtensions.generatePlasticArticle(this, LCCItems.flexible_plastic, Items.BONE_MEAL) }
            .boilerplate(LCCItems.flexible_plastic, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 18, 19, 35, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Materials", "Plastic")
    }

    val item_plastic_bag by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.plastic_bag)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item introduced in %s that functions similarly to the vanilla %s, but stores 2 stacks worth of items instead of only one. It is crafted with %s.",
                        { addText(LCCItems.plastic_bag) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(Items.BUNDLE) },
                        { addLink(LCCItems.flexible_plastic) }
                    )
                }
            )
            .addSection(KnowledgeContentExtensions.generatePlasticDespawningSection(LCCItems.plastic_bag))
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.coloring)
                .addParagraph {
                    addFormatText("The %s of the %s used to craft the plastic bag determines its final %s. Multiple %s of plastic can be used and the resulting bag will be a mix (multiplication) of the provided %s.",
                        { addText(KnowledgeConstants.color) },
                        { addText(LCCItems.flexible_plastic.name) },
                        { addText(KnowledgeConstants.color) },
                        { addText(KnowledgeConstants.colors) },
                        { addText(KnowledgeConstants.colors) }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.plastic_bag))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.plastic_bag))
            .boilerplate(LCCItems.plastic_bag, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 19, 1, 33, 0), LocalDateTime.of(2022, 2, 10, 22, 34, 0))
            .tags("Tools", "Bags", "Plastic")
    }

    val item_oil_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.oil_bucket)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item originally introduced in %s and reintroduced in %s. This %s contains %s which is found in the %s biome in the form of either geysers spewing from the surface, or as pockets hidden slightly underneath %s.",
                        { addText(LCCItems.oil_bucket.name) },
                        { addLink(LCCVersion.YAM_4) },
                        { addLink(LCCVersion.LCC_FABRIC_0_1_0) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(Items.BUCKET), "bucket") },
                        { addLink(KnowledgeArticleIdentifier.ofFluid(LCCFluids.oil_still), "oil") },
                        { addWastelandLink() },
                        { addLink(LCCBlocks.cracked_mud) }
                    )
                }
                .addParagraph {
                    addFormatText("Oil is a key component of this mod, allowing the player to use a %s to craft %s, %s and %s from %s, and %s from %s.",
                        { addLink(LCCBlocks.refiner) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.fuel_bucket), "buckets of fuel") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic), "flexible") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic), "rigid plastic") },
                        { addPluralisedLink(LCCItems.refined_oil_bucket) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.asphalt_bucket), "asphalt") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball), "tar") }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Power")
                .addParagraph {
                    addFormatText("Buckets of oil can be placed in a %s to yield 2 LE/t over 600 seconds. It is much more efficient to first refine crude oil into fuel, which provides 14 LE/t over 200 seconds in the generator.",
                        { addLink(LCCBlocks.oil_generator) }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.oil_bucket))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.oil_bucket))
            .boilerplate(LCCItems.oil_bucket, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 19, 1, 33, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Oil", "Materials", "Bucket", "Wasteland")
    }

    val item_tar_ball by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.tar_ball)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a crafting material introduced in %s which can be used to create %s which eventually hardens into %s. It is one of the three products obtained by refining %s.",
                        { addText(LCCItems.tar_ball.name) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.asphalt_bucket), "asphalt") },
                        { addPluralisedLink(LCCBlocks.road) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket), "crude oil") }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.tar_ball))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.tar_ball))
            .boilerplate(LCCItems.tar_ball, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 21, 11, 50, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Oil", "Materials")
    }

    val item_asphalt_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.asphalt_bucket)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item introduced in %s but first made obtainable in %s. This %s contains %s which can be placed in the world. It is obtained by refining %s.",
                        { addText(LCCItems.asphalt_bucket) },
                        { addLink(LCCVersion.LCC_FABRIC_0_1_0) },
                        { addLink(LCCVersion.LCC_FABRIC_0_3_0) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(Items.BUCKET), "bucket") },
                        { addLink(KnowledgeArticleIdentifier.ofFluid(LCCFluids.asphalt_still), "asphalt") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.tar_ball), "tar") },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.asphalt_bucket))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.asphalt_bucket))
            .boilerplate(LCCItems.asphalt_bucket, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 21, 11, 50, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Oil", "Materials")
    }

    val item_fuel_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.fuel_bucket)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item introduced in %s. It is one of the three products obtained by refining %s. The contents of this %s represents a crafting material that cannot be placed in the world. Fuel can be used to generate power or to craft %s.",
                        { addText(LCCItems.fuel_bucket) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket), "crude oil") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(Items.BUCKET), "bucket") },
                        { addLink(LCCBlocks.explosive_paste) }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Power")
                .addParagraph {
                    addFormatText("Buckets of fuel can be placed in a %s to yield 14 LE/t over 200 seconds. It is much more efficient to use fuel than using crude oil directly, which provides 2 LE/t over 600 seconds in the generator.",
                        { addLink(LCCBlocks.oil_generator) }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.fuel_bucket))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.fuel_bucket))
            .boilerplate(LCCItems.fuel_bucket, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 21, 11, 50, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Oil", "Materials", "Power")
    }

    val item_refined_oil_bucket by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.refined_oil_bucket)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item introduced in %s. It is one of the three products obtained by refining %s. The contents of this %s represents a crafting material that cannot be placed in the world. Refined oil can be further refined to create %s and %s.",
                        { addText(LCCItems.refined_oil_bucket.name) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.oil_bucket), "crude oil") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(Items.BUCKET), "bucket") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.flexible_plastic), "flexible") },
                        { addLink(KnowledgeArticleIdentifier.ofItem(LCCItems.rigid_plastic), "rigid plastic") }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.refined_oil_bucket))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.refined_oil_bucket))
            .boilerplate(LCCItems.refined_oil_bucket, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 21, 11, 50, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Oil", "Materials", "Plastic")
    }

    val block_improvised_explosive by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.improvised_explosive)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block introduced in %s that can be triggered to create an explosion, similar to %s.",
                        { addText(LCCBlocks.improvised_explosive) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(Blocks.TNT) }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.obtaining)
                .addParagraph {
                    addFormatText("%s can be generated in the %s in the form of landmines, accompanied by a %s. When generated in the world, using a %s or higher has a 30% chance to drop the block. This chance increases by 10% for every level of %s. Mining with a %s has a 100% drop chance.",
                        { addPluralisedText(LCCBlocks.improvised_explosive) },
                        { addLink(KnowledgeArticleIdentifier.ofBiome(LCCBiomes.wasteland_barrens), "Wasteland Barrens") },
                        { addLink(LCCBlocks.cracked_mud_pressure_plate) },
                        { addLink(LCCItems.fortstone_pickaxe) },
                        { addLink(Enchantments.FORTUNE) },
                        { addLink(LCCItems.crowbar) }
                    )
                }
                .addParagraph {
                    addFormatText("They can also be crafted with a recipe involving %s.",
                        { addLink(LCCBlocks.explosive_paste) }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usage)
                .addParagraph {
                    addFormatText("%s can be triggered with a redstone signal, another explosion, or a projectile (e.g. an %s or a %s). When activated by redstone or a projectile, the block will begin to blink and beep slowly for 3-6 seconds. After that, the block will blink and beep rapidly for 1 second before exploding.",
                        { addPluralisedText(LCCBlocks.improvised_explosive) },
                        { addLink(Items.ARROW) },
                        { addLink(Items.TRIDENT) }
                    )
                }
                .addParagraph { addText("When activated by another explosion, the block blinks and beeps rapidly and is set to explode in 0.5-1.5 seconds.") }
                .addParagraph { addText("When an active explosive receives a block update and does not have a redstone signal applied to it (i.e. a redstone signal is removed from the explosive), the block will instantly explode.") }
                .addParagraph {
                    addFormatText("If an active explosive is right clicked with %s, the block will be defused and drop as if it was broken with a pickaxe.",
                        { addLink(Items.SHEARS) }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Explosion")
                .addParagraph {
                    addFormatText("The explosion created by an %s leaves behind %s similar to that of a %s or %s. The explosion strength is 6.0, which is the same strength as a %s.",
                        { addText(LCCBlocks.improvised_explosive) },
                        { addLink(Blocks.FIRE) },
                        { addLink(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("bed")), "Bed") },
                        { addLink(Blocks.RESPAWN_ANCHOR) },
                        { addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEntity(EntityType.CREEPER), "Charged_creeper").addFragment(KnowledgeArticleTextFragmentBuilder("Charged Creeper"))) }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.improvised_explosive))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.improvised_explosive))
            .boilerplate(LCCBlocks.improvised_explosive, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 24, 0, 5, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Explosives", "Salvageable")
    }

    val block_deadwood_log by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.deadwood_log)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block introduced in %s that can be found in the %s. It can be crafted into %s which lead to the first tier of %s tool progression.",
                        { addText(LCCBlocks.deadwood_log.name) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(LCCBiomes.wasteland_barrens) },
                        { addLink(LCCBlocks.deadwood_planks) },
                        { addWastelandLink() }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addParagraph {
                    addFormatText("%s are functionally equivalent to a vanilla %s in-game, allowing it to be placed on its side, used as fuel in a %s or %s, or stripped with an axe into %s.",
                        { addPluralisedText(LCCBlocks.deadwood_log) },
                        { addLink(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("log")), "wooden log") },
                        { addLink(Blocks.FURNACE) },
                        { addLink(LCCBlocks.coal_generator) },
                        { addPluralisedLink(LCCBlocks.stripped_deadwood_log) }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.deadwood_log))
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
                .addParagraph {
                    addFormatText("Can be used with %s that accepts any Log.",
                        { addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("log")), "Crafting_ingredient").addFragment(KnowledgeArticleTextFragmentBuilder("any recipe"))) }
                    )
                }
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.deadwood_log).map { it.provider } })
            )
            .boilerplate(LCCBlocks.deadwood_log, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 24, 22, 3, 0), LocalDateTime.of(2022, 2, 10, 22, 59, 9))
            .about(LCCBlocks.deadwood)
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.deadwood))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Axe Recommended", "Deadwood", "Wood")
    }

    val block_stripped_deadwood_log by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.stripped_deadwood_log)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block introduced in %s. It is obtained by using any %s on a %s.",
                        { addText(LCCBlocks.stripped_deadwood_log.name) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier(Registry.ITEM.key.value, Identifier("axe")), "axe") },
                        { addLink(LCCBlocks.deadwood_log) }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addParagraph {
                    addFormatText("%s are functionally equivalent to a vanilla %s in-game, allowing it to be placed on its side or used as fuel in a %s or %s.",
                        { addPluralisedText(LCCBlocks.stripped_deadwood_log) },
                        { addLink(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("log")), "stripped wooden log") },
                        { addLink(Blocks.FURNACE) },
                        { addLink(LCCBlocks.coal_generator) }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.stripped_deadwood_log))
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
                .addParagraph {
                    addFormatText("Can be used with %s that accepts any Stripped Log.",
                        { addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("log")), "Crafting_ingredient").addFragment(KnowledgeArticleTextFragmentBuilder("any recipe"))) }
                    )
                }
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.stripped_deadwood_log).map { it.provider } })
            )
            .boilerplate(LCCBlocks.stripped_deadwood_log, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 25, 14, 28, 0), LocalDateTime.of(2022, 2, 11, 17, 1, 22))
            .about(LCCBlocks.stripped_deadwood)
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.stripped_deadwood), LCCBlocks.stripped_deadwood.name)
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Axe Recommended", "Deadwood", "Wood")
    }

    val block_deadwood_planks by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.deadwood_planks)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block introduced in %s. It can be crafted from %s and used to craft the first tier of %s tool progression.",
                        { addText(LCCBlocks.deadwood_planks) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addPluralisedLink(LCCBlocks.deadwood_log) },
                        { addWastelandLink() }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Wood")
                .addParagraph {
                    addFormatText("%s are functionally equivalent to wooden planks in-game, allowing it to be used to repair tools in an %s, or as fuel in a %s or %s.",
                        { addText(LCCBlocks.deadwood_planks) },
                        { addLink(Blocks.ANVIL) },
                        { addLink(Blocks.FURNACE) },
                        { addLink(LCCBlocks.coal_generator) }
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.deadwood_planks))
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
                .addParagraph {
                    addFormatText("Can be used with %s that accepts any Overworld Planks.",
                        { addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("planks")), "Crafting_ingredient").addFragment(KnowledgeArticleTextFragmentBuilder("any recipe"))) }
                    )
                }
                .addFragment(KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_sword.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_SWORD)))
                .addFragment(KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_pickaxe.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_PICKAXE)))
                .addFragment(KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_shovel.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_SHOVEL)))
                .addFragment(KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_axe.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_AXE)))
                .addFragment(KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_hoe.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_HOE)))
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.deadwood_planks).filter { a ->
                    a.id != LCCItems.deadwood_sword.identifier && a.id != LCCItems.deadwood_pickaxe.identifier && a.id != LCCItems.deadwood_shovel.identifier && a.id != LCCItems.deadwood_axe.identifier && a.id != LCCItems.deadwood_hoe.identifier
                }.map { it.provider } })
            )
            .boilerplate(LCCBlocks.stripped_deadwood_log, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 9, 25, 14, 28, 0), LocalDateTime.of(2022, 2, 11, 17, 28, 33))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Axe Recommended", "Deadwood", "Wood")
    }

    val entity_consumer by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCEntities.consumer)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("A %s is a short hostile mob introduced in %s. It spawns in the %s and pulls in players and mobs with its ranged tongue attack.",
                        { addText(LCCEntities.consumer) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addWastelandLink() }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addParagraph {
                    addFormatText("%s have 14.5 hearts of health and are fast mobs that can mostly keep up with sprinting players. They deal 4.125 hearts of damage on Easy difficulty, 6.75 hearts on Normal and 10.125 hearts on Hard with their bite attack.",
                        { addPluralisedText(LCCEntities.consumer) }
                    )
                }
                .addParagraph {
                    addFormatText("When %ss are out of close combat range, they will advance slowly and frequently extend their tongue at their target. This tongue will latch on to the first entity it hits and the %s will retract its tongue, pulling the attached mob or player back with it. Once the tongue is fully retracted, the %s will also land a bite attack.",
                        { addPluralisedText(LCCEntities.consumer) },
                        { addText(LCCEntities.consumer) },
                        { addText(LCCEntities.consumer) }
                    )
                }
                .addParagraph {
                    addFormatText("Because %s are from the Wasteland, they deal extra damage that pierces through %s without %s. Any damage dealt to them with a weapon that doesn't provide %s is greatly reduced.",
                        { addPluralisedText(LCCEntities.consumer) },
                        { addText(KnowledgeConstants.armor) },
                        { addWastelandEffectivityLink("Wasteland Protection") },
                        { addWastelandEffectivityLink("Wasteland Damage") }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Drops")
                .addFragment(KnowledgeArticleLootFragmentBuilder { listOf(it.da.lootTables[LCCEntities.consumer]!!) })
                .addFragment(KnowledgeArticleLootFragmentBuilder(
                    KnowledgeArticleParagraphFragmentBuilder().addFormatText("Can only drop when the %s's tongue is extended.", { addText(LCCEntities.consumer) })
                ) { listOf(it.da.lootTables[LCCEntities.consumer_tongue]!!) })
            )
            .boilerplate(LCCEntities.consumer)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 1, 19, 45, 0), LocalDateTime.of(2021, 11, 12, 2, 22, 0))
            .about(LCCEntities.consumer_tongue)
            .redirectsHere(KnowledgeArticleIdentifier.ofEntity(LCCEntities.consumer_tongue), LCCEntities.consumer_tongue.name)
            .tags("Wasteland", "Wasteland Effective", "Wasteland Combat", "Wasteland Damage", "Wasteland Protection", "Hostile Mobs")
    }

    val item_deadwood_sword by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandSwordArticle(
        LCCItems.deadwood_sword,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_sword.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_SWORD))
    ) }

    val item_deadwood_pickaxe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandPickaxeArticle(
        LCCItems.deadwood_pickaxe,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        { addLink(LCCBlocks.fortstone) },
        { addText(LCCBlocks.fortstone) },
        recipe = KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_pickaxe.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_PICKAXE))
    ) }

    val item_deadwood_shovel by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandShovelArticle(
        LCCItems.deadwood_shovel,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_shovel.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_SHOVEL))
    ) }

    val item_deadwood_axe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandAxeArticle(
        LCCItems.deadwood_axe,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_axe.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_AXE))
    ) }

    val item_deadwood_hoe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandHoeArticle(
        LCCItems.deadwood_hoe,
        LCCBlocks.deadwood_planks,
        "first",
        "a wooden",
        "Deadwood",
        recipe = KnowledgeContentExtensions.getRecipe(LCCItems.deadwood_hoe.identifier, KnowledgeContentExtensions.getDeadwoodToolNote(Items.WOODEN_HOE))
    ) }

    val effectivity_wasteland by entry(::initialiser) {
        KnowledgeArticleBuilder(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")), "Wasteland Effectivity")
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("Wasteland Effectivity is a concept introduced in %s that aims to self-contain the progression of players in the %s and prevent them from steamrolling through the biome with powerful gear obtained elsewhere.",
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addWastelandLink() }
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Mining")
                .addParagraph {
                    addText("Most blocks in the Wasteland require Wasteland Effective tools to be mined. Below is a list of blocks that will not drop without a Wasteland-tier tool:")
                }
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Required")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
                .addParagraph {
                    addFormatText("Many blocks in the Wasteland do not require these tools to drop, but can be mined faster with a Wasteland Effective tool. Below is a list of these blocks:")
                }
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Optimal")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Combat")
                .addParagraph {
                    addFormatText("Any damage dealt to Wasteland-based mobs with a weapon that doesn't provide Wasteland Damage is greatly reduced, while these mobs also deal extra damage that pierces through %s without Wasteland Protection. Below is a list of mobs that follow these combat rules:",
                        { addText(KnowledgeConstants.armor) }
                    )
                }
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Combat")
                    .addRegistryCriteria(Identifier("entity"))
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Equipment")
                .addParagraph {
                    addText("Below is a list of tools marked as Wasteland equipment:")
                }
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Wasteland Effective")
                    .addRegistryCriteria(Registry.ITEM.key.value)
                )
            )
            .boilerplate()
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 10, 22, 52, 0), LocalDateTime.of(2021, 10, 27, 17, 32, 0))
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
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block introduced in %s that can be found in the %s. It drops %s when mined with a %s or higher, which leads to the second tier of %s tool progression.",
                        { addText(LCCBlocks.fortstone) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(LCCBiomes.wasteland_spikes) },
                        { addLink(LCCBlocks.cobbled_fortstone) },
                        { addLink(LCCItems.deadwood_pickaxe) },
                        { addWastelandLink() },
                    )
                }
                .addParagraph {
                    addFormatText("%s can be silk touched to drop itself. It has a high blast resistance.",
                        { addText(LCCBlocks.fortstone) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.fortstone))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.fortstone))
            .boilerplate(LCCBlocks.fortstone, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 11, 0, 54, 0), LocalDateTime.of(2022, 2, 11, 18, 30, 54))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Deadwood Pickaxe Required", "Fortstone")
    }

    val block_cobbled_fortstone by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.cobbled_fortstone)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a blast-resistant block introduced in %s that can be obtained by mining %s in the %s with a %s. It is used to craft the second tier of %s tool progression.",
                        { addText(LCCBlocks.cobbled_fortstone) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(LCCBlocks.fortstone) },
                        { addLink(LCCBiomes.wasteland_spikes) },
                        { addLink(LCCItems.deadwood_pickaxe) },
                        { addWastelandLink() },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.cobbled_fortstone))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.cobbled_fortstone))
            .boilerplate(LCCBlocks.cobbled_fortstone, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 11, 0, 54, 0), LocalDateTime.of(2022, 2, 11, 18, 30, 54))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Deadwood Pickaxe Required", "Fortstone")
    }

    val item_fortstone_sword by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandSwordArticle(
        LCCItems.fortstone_sword,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone",
        "dealing 2.5 hearts of damage. However, it has a slower attack speed than other swords."
    ) }

    val item_fortstone_pickaxe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandPickaxeArticle(
        LCCItems.fortstone_pickaxe,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone",
        { addLink(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks.values.last()), "rusted iron blocks") },
        { addText("rusted iron blocks") }
    ) }

    val item_fortstone_shovel by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandShovelArticle(
        LCCItems.fortstone_shovel,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone"
    ) }

    val item_fortstone_axe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandAxeArticle(
        LCCItems.fortstone_axe,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone"
    ) }

    val item_fortstone_hoe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandHoeArticle(
        LCCItems.fortstone_hoe,
        LCCBlocks.cobbled_fortstone,
        "second",
        "a stone",
        "Fortstone"
    ) }

    val block_rusty_iron by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.rusted_iron_blocks.values.last())
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a resource block introduced in %s that can be obtained by surrounding a regular %s in %s in the %s biome. It can be broken down into 9 %s which is used to craft the third tier of %s tools as well as %s %s. It must be mined with a %s or higher.",
                        { addText(LCCBlocks.rusted_iron_blocks.values.last()) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(Blocks.IRON_BLOCK) },
                        { addLink(Blocks.WATER) },
                        { addWastelandLink() },
                        { addLink(LCCItems.iron_oxide) },
                        { addText("Wasteland") },
                        { addText("Wasteland") },
                        { addText(KnowledgeConstants.armor) },
                        { addLink(LCCItems.fortstone_pickaxe) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Stages")
                .addParagraph {
                    addText("An iron block in any Wasteland biome will slowly become rusted if it meets all the following criteria:")
                }
                .addFragment(KnowledgeArticleBulletedFragmentBuilder()
                    .add(KnowledgeArticleTextFragmentBuilder("Has at least one orthogonally adjacent block of water."))
                    .add(KnowledgeArticleTextFragmentBuilder("Has at least three orthogonally adjacent blocks of either water or a rusted iron block of any stage."))
                )
                .addParagraph {
                    addText("Water sources, flowing water and waterlogged blocks are all considered as water, but any water below the iron block is not counted as it does not directly touch the block.")
                }
                .addParagraph {
                    addText("Below is an ordered list of the rusting stages of an iron block:")
                }
                .addFragment(KnowledgeArticleListFragmentBuilder()
                    .add(*LCCBlocks.rusted_iron_blocks.values.map(KnowledgeArticleIdentifier::ofBlock).toTypedArray(), reroute = false, link = false)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.recipes)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.rusted_iron_blocks.values.flatMap { b -> it.da.recipes.findRecipes(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.rusted_iron_blocks.values.flatMap { b -> it.da.recipes.findUsages(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 12, 1, 24, 0), LocalDateTime.of(2021, 11, 12, 2, 22, 0))
            .about(*LCCBlocks.rusted_iron_blocks.values.toTypedArray())
            .apply { IronRustType.values().forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.rusted_iron_blocks[it]!!), LCCBlocks.rusted_iron_blocks[it]!!.name) } }
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Rusted Iron", "Resources")
    }

    val item_iron_oxide by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.iron_oxide)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a resource introduced in %s. It can be crafted in groups of 9 by placing a %s in a crafting table. It is used to craft the third tier of %s tools as well as %s %s.",
                        { addText(LCCItems.iron_oxide) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(LCCBlocks.rusted_iron_blocks.values.last()) },
                        { addWastelandLink() },
                        { addText("Wasteland") },
                        { addText(KnowledgeConstants.armor) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.iron_oxide))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.iron_oxide))
            .boilerplate(LCCItems.iron_oxide, renewable = true)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 13, 2, 24, 0), LocalDateTime.of(2021, 11, 12, 2, 22, 0))
            .tags("Wasteland", "Materials", "Rusted Iron", "Resources")
    }

    val item_rusty_iron_sword by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandSwordArticle(
        LCCItems.rusty_iron_sword,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this sword has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_pickaxe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandPickaxeArticle(
        LCCItems.rusty_iron_pickaxe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        { addLink(LCCBlocks.sapphire_altar_brick) },
        { addText(LCCBlocks.sapphire_altar_brick) },
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this pickaxe has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_shovel by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandShovelArticle(
        LCCItems.rusty_iron_shovel,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this shovel has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_axe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandAxeArticle(
        LCCItems.rusty_iron_axe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this axe has a much lower durability than one made of pure iron."))
    } }

    val item_rusty_iron_hoe by entry(::initialiser) { KnowledgeContentExtensions.generateWastelandHoeArticle(
        LCCItems.rusty_iron_hoe,
        LCCItems.iron_oxide,
        "third",
        "an iron",
        "Rusted Iron",
        renewable = true
    ) { addFragment(
        KnowledgeArticleTextFragmentBuilder("Being made out of a worn material, this hoe has a much lower durability than one made of pure iron."))
    } }

    val item_crowbar by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.crowbar)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("The %s is a multi-purpose tool introduced in %s. It can be used for combat, shattering different %s blocks, and salvaging blocks that generate in the %s. It can be crafted with %s, or found as loot in %s structures.",
                        { addText(LCCItems.crowbar) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(Blocks.GLASS) },
                        { addWastelandLink() },
                        { addLink(LCCItems.iron_oxide) },
                        { addText/*Link*/("tent"/*, KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.wasteland_tent).link*/) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.combat)
                .addParagraph {
                    addFormatText("%s deal 1.5 hearts of damage, which limits its usefulness as a weapon. However, when a critical hit is landed on a mob, it is %s for 0.7 seconds. This gives a window of opportunity to perform another critical hit and chain many stuns together.",
                        { addPluralisedText(LCCItems.crowbar) },
                        { addLink(KnowledgeArticleIdentifier.ofEffect(LCCEffects.stun), "stunned") },
                    )
                }
                .addParagraph {
                    addText("This stun is also applied against other players in PvP, but the stun is reduced to 0.4 seconds.")
                }
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.mining)
                .addParagraph {
                    addFormatText("A select list of blocks marked as salvageable will generate naturally in the Wasteland. As they have not been placed down by the player, they only have a chance (usually 30% + 10% per level of %s) to be picked up when mined with their respective tool. When a %s is used, this drop chance increases to 100%.",
                        { addLink(Enchantments.FORTUNE) },
                        { addText(LCCItems.crowbar) },
                    )
                }
                .addParagraph {
                    addText("Below is a list of salvageable blocks:")
                }
                .addFragment(KnowledgeArticleQueryFragmentBuilder()
                    .addTagCriteria("Salvageable")
                    .addRegistryCriteria(Registry.BLOCK.key.value)
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Shattering")
                .addParagraph {
                    addFormatText("%s, %s and all %s of %s can be shattered into %s by right clicking with a %s in hand. %s and %s can also be shattered into %s.",
                        { addLink(Blocks.GLASS) },
                        { addLink(Blocks.TINTED_GLASS) },
                        { addText(KnowledgeConstants.colors) },
                        { addText("Stained Glass") },
                        { addLink(LCCBlocks.shattered_glass) },
                        { addText(LCCItems.crowbar) },
                        { addPluralisedLink(Blocks.GLASS_PANE) },
                        { addText("Stained Glass Panes") },
                        { addPluralisedLink(LCCBlocks.shattered_glass_pane) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Durability")
                .addParagraph {
                    addFormatText("%s have ${LCCItems.crowbar.maxDamage} durability points. Attacking mobs or shattering glass costs 1 durability, while mining a block costs 2 durability.",
                        { addPluralisedText(LCCItems.crowbar) },
                    )
                }
            )
            .addSection(getRepairingSection(LCCItems.crowbar, LCCItems.iron_oxide))
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.crowbar))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.crowbar))
            .boilerplate(LCCItems.crowbar, renewable = true)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 20, 21, 32, 0), LocalDateTime.of(2022, 2, 11, 19, 7, 45))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Wasteland Optimal", "Salvageable", "Rusted Iron", "Tools")
    }

    val block_shattered_glass by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.shattered_glass)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a transparent block introduced in %s that is obtained by smashing (right clicking) %s, %s and all %s of %s with a %s. It is extremely fragile and breaks easily when stepped on or shot with a projectile.",
                        { addText(LCCBlocks.shattered_glass.name) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(Blocks.GLASS) },
                        { addLink(Blocks.TINTED_GLASS) },
                        { addText(KnowledgeConstants.colors) },
                        { addLink(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("stained_glass")), "Stained Glass") },
                        { addLink(LCCItems.crowbar) },
                    )
                }
                .addParagraph {
                    addFormatText("This is the full block version of %s.",
                        { addPluralisedLink(LCCBlocks.shattered_glass_pane) }
                    )
                }
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
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.recipes)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.shattered_glass).map { it.provider } })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.shattered_tinted_glass).map { it.provider } })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass.values.flatMap { b -> it.da.recipes.findRecipes(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.shattered_glass).map { it.provider } })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.shattered_tinted_glass).map { it.provider } })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass.values.flatMap { b -> it.da.recipes.findUsages(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .about(LCCBlocks.shattered_tinted_glass, *LCCBlocks.stained_shattered_glass.values.toTypedArray())
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 20, 22, 5, 0), LocalDateTime.of(2022, 3, 14, 21, 28, 17))
            .redirectsHere(KnowledgeArticleIdentifier.ofBlock(LCCBlocks.shattered_tinted_glass), LCCBlocks.shattered_tinted_glass.name)
            .apply { LCCBlocks.stained_shattered_glass.values.forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(it), it.name) } }
            .tags("Shattered Glass", "Colored", "Glass")
    }

    val block_shattered_glass_pane by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.shattered_glass_pane)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a transparent block introduced in %s that is obtained by smashing (right clicking) %s and all %s of %s with a %s. It is extremely fragile and breaks easily when stepped on or shot with a projectile.",
                        { addText(LCCBlocks.shattered_glass_pane.name) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addPluralisedLink(Blocks.GLASS_PANE) },
                        { addText(KnowledgeConstants.colors) },
                        { addLink(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("stained_glass_pane")), "Stained Glass Pane") },
                        { addLink(LCCItems.crowbar) },
                    )
                }
                .addParagraph {
                    addFormatText("This is the glass pane version of %s.",
                        { addLink(LCCBlocks.shattered_glass) },
                    )
                }
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
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.recipes)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.shattered_glass_pane).map { it.provider } })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass_pane.values.flatMap { b -> it.da.recipes.findRecipes(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(LCCBlocks.shattered_glass_pane).map { it.provider } })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.stained_shattered_glass_pane.values.flatMap { b -> it.da.recipes.findUsages(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .about(*LCCBlocks.stained_shattered_glass_pane.values.toTypedArray())
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 21, 2, 56, 0), LocalDateTime.of(2022, 3, 14, 21, 28, 17))
            .apply { LCCBlocks.stained_shattered_glass_pane.values.forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(it), it.name) } }
            .tags("Shattered Glass", "Colored", "Glass", "Glass Panes")
    }

    val block_cracked_mud by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.cracked_mud)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block originally introduced in %s and reintroduced in %s that covers the surface of the %s. %s can be hydrated into %s, a process which can be reversed.",
                        { addText(LCCBlocks.cracked_mud) },
                        { addLink(LCCVersion.YAM_1) },
                        { addLink(LCCVersion.LCC_FABRIC_0_1_0) },
                        { addWastelandLink() },
                        { addText(LCCBlocks.cracked_mud) },
                        { addLink(LCCBlocks.mud) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Interactions")
                .addParagraph {
                    addFormatText("In %s, %s can be converted into %s by being placed in or adjacent to %s.",
                        { addLink(LCCVersion.LCC_FABRIC_0_5_1) },
                        { addText(LCCBlocks.cracked_mud) },
                        { addText(LCCBlocks.mud) },
                        { addLink(Blocks.WATER) },
                    )
                }
                .addParagraph {
                    addFormatText("To slowly dry %s back into %s, it can be left in the %s without adjacent %s. %s can be quickly dried by smelting it in a %s or %s.",
                        { addText(LCCBlocks.mud) },
                        { addText(LCCBlocks.cracked_mud) },
                        { addText("Wasteland") },
                        { addText(Blocks.WATER) },
                        { addText(LCCBlocks.mud) },
                        { addLink(Blocks.FURNACE) },
                        { addLink(LCCBlocks.kiln) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.cracked_mud))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.cracked_mud))
            .boilerplate(LCCBlocks.cracked_mud, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 21, 2, 56, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Pickaxe Recommended", "Mud")
    }

    val block_mud by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.mud)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a block originally introduced in %s and reintroduced in %s which drastically changes the movement of mobs and players walking on it. %s can be dried into %s, a process which can be reversed.",
                        { addText(LCCBlocks.mud) },
                        { addLink(LCCVersion.YAM_1) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_1) },
                        { addText(LCCBlocks.mud) },
                        { addLink(LCCBlocks.cracked_mud) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Movement")
                .addParagraph {
                    addFormatText("In %s, %s is a slippery block similar to %s. The speed of an entity walking on this block changes over time, giving the effect of wading through the block.",
                        { addText(LCCVersion.LCC_FABRIC_0_5_1.label) },
                        { addText(LCCBlocks.mud) },
                        { addLink(Blocks.ICE) },
                    )
                }
                .addParagraph {
                    addFormatText("Previously in %s, %s was a ridiculously slippery block, allowing entities walking on it to wildly accelerate and travel large distances very quickly.",
                        { addText(LCCVersion.YAM_1.label) },
                        { addText(LCCBlocks.mud) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Interactions")
                .addParagraph {
                    addFormatText("When left in the %s without adjacent %s, %s will slowly dry into %s. %s can also be quickly dried by smelting it in a %s or %s.",
                        { addWastelandLink() },
                        { addLink(Blocks.WATER) },
                        { addText(LCCBlocks.mud) },
                        { addText(LCCBlocks.cracked_mud) },
                        { addText(LCCBlocks.mud) },
                        { addLink(Blocks.FURNACE) },
                        { addLink(LCCBlocks.kiln) },
                    )
                }
                .addParagraph {
                    addFormatText("%s can be rehydrated into %s by being placed in or adjacent to %s.",
                        { addText(LCCBlocks.cracked_mud) },
                        { addText(LCCBlocks.mud) },
                        { addLink(Blocks.WATER) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("1.19")
                .addParagraph {
                    addFormatText("This block will likely be merged with the Mud Block from Minecraft 1.19 once released. It is planned to migrate the functionality of drying into %s to this new block.",
                        { addText(LCCBlocks.cracked_mud) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.mud))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.mud))
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = false).alterStats { it.put(it.keys.elementAt(5), listOf(KnowledgeArticleTextFragmentBuilder("Varies"))) })
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 21, 2, 56, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Shovel Recommended", "Mud", "Movement")
    }

    val block_cracked_mud_pressure_plate by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.cracked_mud_pressure_plate)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("The %s is a pressure plate introduced in %s. It is activated when sprinted on or landed on from a height, and will deactivate once there are no entities on it.",
                        { addText(LCCBlocks.cracked_mud_pressure_plate) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Obtaining")
                .addParagraph {
                    addFormatText("%s can be generated in the %s as the activation mechanism of landmines, which will immediately activate the %s that generates below. If the pressure plate is deactivated, the %s will instantly detonate.",
                        { addPluralisedText(LCCBlocks.cracked_mud_pressure_plate) },
                        { addWastelandLink() },
                        { addLink(LCCBlocks.improvised_explosive) },
                        { addText(LCCBlocks.improvised_explosive) },
                    )
                }
                .addParagraph {
                    addFormatText("It can also be crafted with 2 %s blocks.",
                        { addLink(LCCBlocks.cracked_mud) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.cracked_mud_pressure_plate))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.cracked_mud_pressure_plate))
            .boilerplate(LCCBlocks.cracked_mud_pressure_plate, renewable = false)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 27, 17, 32, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", "Deadwood Pickaxe Recommended", "Mud", "Pressure Plate")
    }

    val item_iron_oxide_nugget by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.iron_oxide_nugget)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s are a crafting material introduced in %s. It can be crafted in groups of 9 by placing a %s in a crafting table.",
                        { addPluralisedText(LCCItems.iron_oxide_nugget) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(LCCItems.iron_oxide) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.iron_oxide_nugget))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.iron_oxide_nugget))
            .boilerplate(LCCItems.iron_oxide_nugget, renewable = true)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 27, 17, 32, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Wasteland", "Materials", "Rusted Iron", "Resources")
    }

    val block_spikes by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.spikes)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s are a set of blocks introduced in %s. They can be placed on a block to face in any direction and will deal damage to entities touching the spiked side whilst slowing their movement speed.",
                        { addText(LCCBlocks.spikes) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.obtaining)
                .addParagraph {
                    addFormatText("All %s can be found in the %s in the form of spike traps. They are square holes that are easy to spot on the surface, with a bed of a random type of %s at the bottom. Plain %s are the most common to find in spike traps.",
                        { addText(LCCBlocks.spikes) },
                        { addLink(LCCBiomes.wasteland_barrens) },
                        { addText(LCCBlocks.spikes) },
                        { addText(LCCBlocks.spikes) },
                    )
                }
                .addParagraph {
                    addFormatText("Regular %s can be crafted with %s and %s, and then upgraded to another type with their respective ingredient:",
                        { addText(LCCBlocks.spikes) },
                        { addLink(LCCItems.iron_oxide) },
                        { addLink(LCCItems.iron_oxide_nugget) },
                    )
                }
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Spike Type"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Crafting Ingredient"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleStackFragmentBuilder(LCCBlocks.bleeding_spikes.stack()))
                        addCell(KnowledgeArticleStackFragmentBuilder(LCCItems.heart_half[HeartType.RED]!!.stack()))
                    }
                    .addRow {
                        addCell(KnowledgeArticleStackFragmentBuilder(LCCBlocks.poison_spikes.stack()))
                        addCell(KnowledgeArticleStackFragmentBuilder(LCCItems.stinger.stack()))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Damage")
                .addParagraph {
                    addFormatText("All %s will constantly deal damage to an entity while it is in motion. The exact amount of damage received starts at half a heart and increases exponentially depending on the entity's current speed.",
                        { addText(LCCBlocks.spikes) },
                    )
                }
                .addParagraph {
                    addFormatText("%s deal increased damage to entities by modifying the calculated damage to the power of 1.2 and then doubling it.",
                        { addText(LCCBlocks.bleeding_spikes) },
                    )
                }
                .addParagraph {
                    addFormatText("When mobs and players are damaged by %s, a poison effect is applied for 8.5 seconds.",
                        { addText(LCCBlocks.poison_spikes) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Types")
                .addFragment(KnowledgeArticleListFragmentBuilder()
                    .add(
                        *LCCBlocks.all.values.filterIsInstance<SpikesBlock>().map(KnowledgeArticleIdentifier::ofBlock).toTypedArray(),
                        reroute = false, link = false
                    )
                )
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.recipes)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.all.values.filterIsInstance<SpikesBlock>().flatMap { b -> it.da.recipes.findRecipes(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { LCCBlocks.all.values.filterIsInstance<SpikesBlock>().flatMap { b -> it.da.recipes.findUsages(b).map { it.provider } } })
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = true))
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 27, 17, 32, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .about(*LCCBlocks.all.values.filterIsInstance<SpikesBlock>().toTypedArray())
            .apply { LCCBlocks.all.values.filterIsInstance<SpikesBlock>().forEach { redirectsHere(KnowledgeArticleIdentifier.ofBlock(it), it.name) } }
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Rusted Iron")
    }

    val block_rusted_iron_bars by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.rusted_iron_bars)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a decorative block introduced in %s. They are the %s equivalent of %s.",
                        { addText(LCCBlocks.rusted_iron_bars) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(LCCItems.iron_oxide) },
                        { addLink(Blocks.IRON_BARS) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCBlocks.rusted_iron_bars))
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.rusted_iron_bars))
            .boilerplate(LCCBlocks.rusted_iron_bars, renewable = true)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 27, 17, 32, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Fortstone Pickaxe Required", "Rusted Iron", "Iron Bars")
    }

    val item_tongue_tissue by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.tongue_tissue)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is a material introduced in %s. They are rarely dropped by %s when their tongue is extended.",
                        { addText(LCCItems.tongue_tissue) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addPluralisedLink(LCCEntities.consumer) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.tongue_tissue))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.tongue_tissue))
            .boilerplate(LCCItems.tongue_tissue, renewable = true)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 27, 17, 32, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Wasteland", "Materials", "Mob Drops", "Rare Mob Drops")
    }

    val item_altar_challenge_key by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCItems.altar_challenge_key)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("%s is an item introduced in %s. It allows the player to start a %s challenge, which give %s upon completion.",
                        { addText(LCCItems.altar_challenge_key) },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addText("Sapphire Altar") }, //TODO
                        { addPluralisedLink(LCCItems.sapphire) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Usage")
                .addParagraph {
                    addFormatText("Once a %s block is filled with sapphires that the player wishes to wager, the block can be right clicked with an %s to start the challenge. The key is consumed in the process.",
                        { addLink(LCCBlocks.sapphire_altar) },
                        { addText(LCCItems.altar_challenge_key) },
                    )
                }
            )
            .addSection(KnowledgeExtensions.craftingRecipes(LCCItems.altar_challenge_key))
            .addSection(KnowledgeExtensions.craftingUsages(LCCItems.altar_challenge_key))
            .boilerplate(LCCItems.altar_challenge_key, renewable = true)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 27, 17, 32, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Wasteland", "Rusted Iron", "Sapphire Altar")
    }

    //TODO
    val structure_sapphire_altar by entry(::initialiser) {
        KnowledgeArticleBuilder(/*KnowledgeArticleIdentifier.ofStructure(LCCStructureFeatures.sapphire_altar)*/KnowledgeArticleIdentifier(Identifier("structure"), LCC.id("sapphire_altar")), "Sapphire Altar")
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("The Sapphire Altar is a structure introduced in %s. It is a common structure found in all %s biomes that presents a random challenge to players. Completing this challenge gives %s as a reward, used to make the final tier of %s tools and %s.",
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addWastelandLink() },
                        { addPluralisedLink(LCCItems.sapphire) },
                        { addText("Wasteland") },
                        { addText(KnowledgeConstants.armor) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Structure")
                .addParagraph {
                    addFormatText("The base of the structure is comprised of %s, with a staircase of %s on one side leading to the top of the structure.",
                        { addLink(LCCBlocks.sapphire_altar_brick) },
                        { addLink(LCCBlocks.sapphire_altar_brick_stairs) },
                    )
                }
                .addParagraph {
                    addFormatText("The top of the structure is entirely dependent on the challenge posed by the altar, but they will all generate with one %s block. This block provides an interface to the structure itself.",
                        { addLink(LCCBlocks.sapphire_altar) },
                    )
                }
            )
            .addSection(KnowledgeArticleChangelogSectionBuilder())
            .addSection(KnowledgeArticleSectionBuilder("Image").setLayout( "info")
                .addFragment(KnowledgeArticleImageFragmentBuilder()
                    .addStatic("structure/sapphire_altar", KnowledgeArticleParagraphFragmentBuilder()
                        .addFormatText("A Sapphire Altar generated in the %s.",
                            { addLink(LCCBiomes.wasteland_spikes) },
                        )
                    )
                )
            )
            .tags("Wasteland", "Sapphire Altar")
    }

    val block_bounce_pad by entry(::initialiser) {
        KnowledgeArticleBuilder(LCCBlocks.bounce_pad)
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.introduction)
                .addParagraph {
                    addFormatText("The %s is a block originally introduced in %s and reintroduced in %s that launches players and other entities in the direction it is placed. %s combine the bouncing mechanics of the %s with the propulsion properties of the %s used to craft them.",
                        { addText(LCCBlocks.bounce_pad) },
                        { addLink(LCCVersion.YAM_1) },
                        { addLink(LCCVersion.LCC_FABRIC_0_1_0) },
                        { addPluralisedText(LCCBlocks.bounce_pad) },
                        { addLink(LCCBlocks.rubber_piston) },
                        { addLink(LCCBlocks.soaking_soul_sand) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder("Movement")
                .addParagraph {
                    addFormatText("When an entity enters the activation zone, which is the size of a full block, they are snapped into the center of the %s and all previous momentum is removed. The entity is then launched in the direction the pad was placed.",
                        { addText(LCCBlocks.bounce_pad) },
                    )
                }
                .addParagraph {
                    addFormatText("The speed of the launch depends on the red notch on the side of the %s. This setting has a range of 1 to 5 and can be changed by right clicking the %s. Below is a table showing the strength of each setting:",
                        { addText(LCCBlocks.bounce_pad) },
                        { addText(LCCBlocks.bounce_pad) },
                    )
                }
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Setting"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Velocity"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Approximate Height (in m)"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("1"))
                        addCell(KnowledgeArticleTextFragmentBuilder("1.0"))
                        addCell(KnowledgeArticleTextFragmentBuilder("6.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("1.4"))
                        addCell(KnowledgeArticleTextFragmentBuilder("11.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("3"))
                        addCell(KnowledgeArticleTextFragmentBuilder("1.8"))
                        addCell(KnowledgeArticleTextFragmentBuilder("17.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("4"))
                        addCell(KnowledgeArticleTextFragmentBuilder("2.2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("24.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("5"))
                        addCell(KnowledgeArticleTextFragmentBuilder("2.6"))
                        addCell(KnowledgeArticleTextFragmentBuilder("32.0"))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder("Legacy")
                .addParagraph {
                    addFormatText("In %s, there were five %s varieties of %s ranging from blue to red. They could only be placed facing upwards. Strength values for these are shown below:",
                        { addText(LCCVersion.YAM_1.label) },
                        { addText(KnowledgeConstants.colored) },
                        { addText(LCCBlocks.bounce_pad) },
                    )
                }
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Color").addTranslation("Colour", "en_gb"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Velocity"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Approximate Height (in m)"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("Blue"))
                        addCell(KnowledgeArticleTextFragmentBuilder("0.8"))
                        addCell(KnowledgeArticleTextFragmentBuilder("5.5"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("Green"))
                        addCell(KnowledgeArticleTextFragmentBuilder("1.2"))
                        addCell(KnowledgeArticleTextFragmentBuilder("9.0"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("Yellow"))
                        addCell(KnowledgeArticleTextFragmentBuilder("1.6"))
                        addCell(KnowledgeArticleTextFragmentBuilder("14.0"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("Orange"))
                        addCell(KnowledgeArticleTextFragmentBuilder("2.0"))
                        addCell(KnowledgeArticleTextFragmentBuilder("20.0"))
                    }
                    .addRow {
                        addCell(KnowledgeArticleTextFragmentBuilder("Red"))
                        addCell(KnowledgeArticleTextFragmentBuilder("3.0"))
                        addCell(KnowledgeArticleTextFragmentBuilder("39.0"))
                    }
                )
                .addParagraph {
                    addFormatText("In %s, a %s could be placed directionally and had a GUI with a single slot that could be accessed by right clicking. The GUI also contained a slider that adjusted the power of the thrust and two others sliders underneath which controlled the lean in the other two directions. The range of these sliders were dictated by the amount of %s placed in the inventory of the %s.",
                        { addLink(LCCVersion.AIMAGG_ALPHA_1_6) },
                        { addText(LCCBlocks.bounce_pad) },
                        { addLink(KnowledgeArticleIdentifier(Registry.BLOCK.key.value, Identifier("aimagg", "iron_spring")), "Iron Springs") },
                        { addText(LCCBlocks.bounce_pad) },
                    )
                }
                .addParagraph {
                    addFormatText("In %s, %s function similarly to their Fabric renditions. However, they can only be placed facing upwards.",
                        { addLink(LCCVersion.LCC_FORGE_ALPHA_1_0) },
                        { addPluralisedText(LCCBlocks.bounce_pad) },
                    )
                }
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.recipes)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(LCCBlocks.bounce_pad).map { it.provider } })
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { e ->
                    listOf(OverrideRecipeJsonProvider.fromFactory(RecipeSerializer.SHAPED, ShapedRecipeJsonBuilder.create(LCCBlocks.bounce_pad, 6)
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
                        /*val items = arrayOf(Items.REPEATER, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK, Blocks.PISTON, LCCBlocks.soaking_soul_sand)
                        it.add("translations", e.translator.itemTranslationsJson(*items))
                        it.add("links", e.linker.itemLinksJson(*items))*/
                    })
                }.markObsolete().setNote(
                    KnowledgeArticleParagraphFragmentBuilder()
                        .addText("Recipe before ")
                        .addLink(LCCVersion.LCC_FABRIC_0_5_1)
                        .addText(".")
                ))
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { e ->
                    listOf(OverrideRecipeJsonProvider.fromFactory(RecipeSerializer.SHAPED, ShapedRecipeJsonBuilder.create(LCCBlocks.bounce_pad)
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
                        /*val items = arrayOf(Items.REPEATER, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK, Blocks.PISTON, LCCBlocks.soaking_soul_sand)
                        it.add("translations", e.translator.itemTranslationsJson(*items))
                        it.add("links", e.linker.itemLinksJson(*items))*/
                    })
                }.markObsolete().setNote(
                    KnowledgeArticleParagraphFragmentBuilder()
                        .addText("Recipe before ")
                        .addLink(LCCVersion.LCC_FABRIC_0_4_2)
                        .addText(".")
                ))
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { e ->
                    listOf(OverrideRecipeJsonProvider.fromFactory(RecipeSerializer.SHAPED, ShapedRecipeJsonBuilder.create(LCCBlocks.bounce_pad, 8)
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
                        /*val items = arrayOf(Items.REPEATER, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK, Blocks.PISTON, LCCBlocks.soaking_soul_sand)
                        it.add("translations", e.translator.itemTranslationsJson(*items))
                        it.add("links", e.linker.itemLinksJson(*items))*/
                    })
                }.markObsolete().setNote(
                    KnowledgeArticleParagraphFragmentBuilder()
                        .addText("Recipe before ")
                        .addLink(LCCVersion.LCC_FABRIC_0_2_0)
                        .addText(".")
                ))
            )
            .addSection(KnowledgeExtensions.craftingUsages(LCCBlocks.bounce_pad))
            .boilerplate(LCCBlocks.bounce_pad, renewable = true)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2022, 1, 25, 20, 45, 0), LocalDateTime.of(2022, 2, 8, 19, 52, 0))
            .tags("Movement", "Rubber", "Soaking Soul Sand", "Tools")
    }

    fun initialiser(input: KnowledgeArticleBuilder, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out KnowledgeArticleBuilder, out KnowledgeArticleBuilder>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.afterInit() }
    }

    override fun defaultProperties(name: String) = Unit

    fun getArticlesAbout(topic: Any) = this.all.filter { (k, v) -> v.about.contains(topic) }

}
