package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.*
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.data.json.recipe.OverrideRecipeJsonProvider
import com.joshmanisdabomb.lcc.data.json.recipe.RefiningShapelessRecipeJsonFactory
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants.introduction
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeExtensions.addLink
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeExtensions.boilerplate
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import com.joshmanisdabomb.lcc.recipe.refining.special.PolymerRefiningRecipe
import net.minecraft.block.Blocks
import net.minecraft.item.DyeItem
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.SwordItem
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import java.time.LocalDateTime

object KnowledgeContentExtensions {

    fun KnowledgeArticleParagraphFragmentBuilder.addWastelandLink(label: String = "Wasteland") = this.addLink(KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")), label)

    fun KnowledgeArticleParagraphFragmentBuilder.addWastelandEffectivityLink(label: String = "Wasteland Effectivity") = this.addLink(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")), label)

    fun generatePlasticDespawningSection(topic: Item) = KnowledgeArticleSectionBuilder(KnowledgeConstants.despawning)
        .addParagraph {
            addFormatText("%s, like all plastic-based items, do not despawn after 5 minutes when dropped in the world.",
                { addText(topic) }
            )
        }

    fun generatePlasticArticle(article: KnowledgeArticleBuilder, topic: Item, plasticiser: Item) {
        article
            .addSection(generatePlasticDespawningSection(topic))
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.coloring)
                .addParagraph {
                    addFormatText("%s and %s can be crafted into different %s by adding dyes to their refining recipe. Similar to dyeing leather %s, multiple dyes can be applied and the resulting %s will be a mix (average) of the provided dyes. However, dyes apply a much more vibrant and saturated %s to plastic. You can view %s codes in the table below:",
                        { addText(LCCItems.flexible_plastic) },
                        { addText(LCCItems.rigid_plastic) },
                        { addText(KnowledgeConstants.colors) },
                        { addText(KnowledgeConstants.armor) },
                        { addText(KnowledgeConstants.color) },
                        { addText(KnowledgeConstants.color) },
                        { addText(KnowledgeConstants.color) }
                    )
                }
                .addFragment(KnowledgeArticleTableFragmentBuilder()
                    .addRow {
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder("Item"))
                        addHeadingCell(KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.colorCode))
                    }
                    .addRows(*Registry.ITEM.filterIsInstance<DyeItem>().toTypedArray()) {
                        addCell(KnowledgeArticleStackFragmentBuilder(it.stack()))
                        addCell(KnowledgeArticleColorFragmentBuilder((it.color as LCCExtendedDyeColor).plasticColor))
                    }
                )
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.recipes)
                .addFragment(KnowledgeArticleRecipeFragmentBuilder { listOf(plasticRecipe(it, topic, plasticiser)) })
            )
            .addSection(KnowledgeExtensions.craftingUsages(topic))
    }

    fun plasticRecipe(exporter: KnowledgeExporter, output: Item, plasticiser: Item) = OverrideRecipeJsonProvider.fromFactory(LCCRecipeSerializers.polymerization, RefiningShapelessRecipeJsonFactory()
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
        it.add("translations", KnowledgeArticleRecipeFragmentBuilder.getTranslationTree(exporter, *items))
        it.add("links", KnowledgeArticleRecipeFragmentBuilder.getLinkTree(exporter, *items))
        it.remove("tags")
    }

    fun legacyOilRecipe() = KnowledgeArticleRecipeFragmentBuilder { e ->
        listOf(OverrideRecipeJsonProvider(LCCRecipeSerializers.refining_shapeless, e.da.recipes.findRecipes(LCCItems.asphalt_bucket).first().provider) {
            val ingredients = it.get("ingredients").asJsonArray
            val tar = ingredients[0].asJsonObject
            tar.addProperty("item", LCCItems.oil_bucket.identifier.toString())
            ingredients.set(0, tar)
            ingredients.remove(3)
            it.add("ingredients", ingredients)
            /*val items = arrayOf(LCCItems.oil_bucket, Items.SAND, Items.GRAVEL, LCCItems.asphalt_bucket)
            it.add("translations", e.translator.itemTranslationsJson(*items))
            it.add("links", e.linker.itemLinksJson(*items))*/
        })
    }.markObsolete().setNote(
        KnowledgeArticleParagraphFragmentBuilder()
            .addText("Recipe before ")
            .addLink(LCCVersion.LCC_FABRIC_0_5_0)
            .addText(".")
    )

    fun getRecipe(recipe: Identifier, note: KnowledgeArticleFragmentBuilder? = null, obsolete: Boolean = false) = KnowledgeArticleRecipeFragmentBuilder {
        listOf(it.da.recipes[recipe]!!)
    }.apply {
        if (note != null) setNote(note)
        if (obsolete) markObsolete()
    }

    fun getDeadwoodToolNote(fallback: Item) = KnowledgeArticleParagraphFragmentBuilder().addFormatText("If not all planks provided are %s, a standard %s will be crafted instead.",
        { addText(LCCBlocks.deadwood_planks) },
        { addLink(fallback) },
    )

    fun getRepairingSection(tool: Item, repair: ItemConvertible) = KnowledgeArticleSectionBuilder(KnowledgeConstants.repairing)
        .addParagraph {
            addFormatText("%s can be repaired with %s in an %s. Like all tools, two %s can also be fused in crafting or a %s.",
                { addPluralisedText(tool) },
                { addText(repair.asItem()) },
                { addLink(Blocks.ANVIL) },
                { addPluralisedText(tool) },
                { addLink(Blocks.GRINDSTONE) }
            )
        }

    fun generateWastelandSwordArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, damage: String = "dealing ${(item as SwordItem).attackDamage.div(2.0).plus(0.5).decimalFormat(1)} hearts of damage.", recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item).map { it.provider } }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addParagraph {
                    addFormatText("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent sword, $damage",
                        { addText(item) },
                        { addLink(KnowledgeArticleIdentifier(Registry.ITEM.key.value, Identifier("sword")), "sword") },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")), "Wasteland") },
                        { addLink(ingredient.asItem()) }
                    )
                }
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.combat)
                .addParagraph {
                    addFormatText("%s deal %s, which deal full damage against Wasteland-based creatures.",
                        { addPluralisedText(item) },
                        { addLink(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")), "Wasteland Damage") },
                    )
                }
            )
            .addSection(getRepairingSection(item, ingredient.asItem()))
            .addSection(KnowledgeExtensions.craftingRecipes(item))
            .addSection(KnowledgeExtensions.craftingUsages(item))
            .boilerplate(item, renewable = renewable)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 11, 0, 54, 0), LocalDateTime.of(2022, 11, 12, 2, 22, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Combat", "Wasteland Damage", tag, "Swords", "Tools")

    fun generateWastelandPickaxeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, example: KnowledgeArticleParagraphFragmentBuilder.() -> Unit, example2: KnowledgeArticleParagraphFragmentBuilder.() -> Unit, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item).map { it.provider } }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addParagraph {
                    addFormatText("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent pickaxe, but can mine %s.",
                        { addText(item) },
                        { addLink(KnowledgeArticleIdentifier(Registry.ITEM.key.value, Identifier("pickaxe")), "pickaxe") },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")), "Wasteland") },
                        { addLink(ingredient.asItem()) },
                        example
                    )
                }
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.mining)
                .addParagraph {
                    addFormatText("%s are %s, allowing them to harvest blocks that require a Wasteland pickaxe to mine, such as %s.",
                        { addPluralisedText(item) },
                        { addLink(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")), "Wasteland Effective") },
                        example2
                    )
                }
                .addParagraph {
                    addFormatText("Some blocks, such as %s, break faster with a Wasteland Effective pickaxe but do not require one.",
                        { addLink(LCCBlocks.cracked_mud) }
                    )
                }
            )
            .addSection(getRepairingSection(item, ingredient.asItem()))
            .addSection(KnowledgeExtensions.craftingRecipes(item))
            .addSection(KnowledgeExtensions.craftingUsages(item))
            .boilerplate(item, renewable = renewable)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 11, 0, 54, 0), LocalDateTime.of(2022, 11, 12, 2, 22, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Required", "Wasteland Optimal", tag, "Pickaxes", "Tools")

    fun generateWastelandShovelArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item).map { it.provider } }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addParagraph {
                    addFormatText("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent shovel.",
                        { addText(item) },
                        { addLink(KnowledgeArticleIdentifier(Registry.ITEM.key.value, Identifier("shovel")), "shovel") },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")), "Wasteland") },
                        { addLink(ingredient.asItem()) }
                    )
                }
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.mining)
                .addParagraph {
                    addFormatText("%s are %s, allowing them to break blocks that require a Wasteland shovel, such as %s, faster.",
                        { addPluralisedText(item) },
                        { addLink(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")), "Wasteland Effective") },
                        { addPluralisedLink(LCCBlocks.deposit) }
                    )
                }
            )
            .addSection(getRepairingSection(item, ingredient.asItem()))
            .addSection(KnowledgeExtensions.craftingRecipes(item))
            .addSection(KnowledgeExtensions.craftingUsages(item))
            .boilerplate(item, renewable = renewable)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 11, 0, 54, 0), LocalDateTime.of(2022, 11, 12, 2, 22, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Shovels", "Tools")

    fun generateWastelandAxeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item).map { it.provider } }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addParagraph {
                    addFormatText("%s is an %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent axe.",
                        { addText(item) },
                        { addLink(KnowledgeArticleIdentifier(Registry.ITEM.key.value, Identifier("axe")), "axe") },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")), "Wasteland") },
                        { addLink(ingredient.asItem()) }
                    )
                }
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.mining)
                .addParagraph {
                    addFormatText("%s are %s, allowing them to break blocks that require a Wasteland axe, such as %s, faster.",
                        { addPluralisedText(item) },
                        { addLink(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")), "Wasteland Effective") },
                        { addLink(LCCBlocks.deadwood) }
                    )
                }
            )
            .addSection(getRepairingSection(item, ingredient.asItem()))
            .addSection(KnowledgeExtensions.craftingRecipes(item))
            .addSection(KnowledgeExtensions.craftingUsages(item))
            .boilerplate(item, renewable = renewable)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 11, 0, 54, 0), LocalDateTime.of(2022, 11, 12, 2, 22, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Axes", "Tools")

    fun generateWastelandHoeArticle(item: Item, ingredient: ItemConvertible, tier: String, equivalent: String, tag: String, recipe: KnowledgeArticleRecipeFragmentBuilder = KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item).map { it.provider } }, renewable: Boolean = false, introAdd: KnowledgeArticleSectionBuilder.() -> Unit = {}) =
        KnowledgeArticleBuilder(item)
            .addSection(KnowledgeArticleSectionBuilder(introduction)
                .addParagraph {
                    addFormatText("%s is a %s introduced in %s. It is one of the tools in the $tier tier of %s tool progression, crafted with %s. It functions similarly to $equivalent hoe.",
                        { addText(item) },
                        { addLink(KnowledgeArticleIdentifier(Registry.ITEM.key.value, Identifier("hoe")), "hoe") },
                        { addLink(LCCVersion.LCC_FABRIC_0_5_0) },
                        { addLink(KnowledgeArticleIdentifier(BuiltinRegistries.BIOME.key.value, LCC.id("wasteland")), "Wasteland") },
                        { addLink(ingredient.asItem()) }
                    )
                }
                .apply(introAdd)
            )
            .addSection(KnowledgeArticleSectionBuilder(KnowledgeConstants.mining)
                .addParagraph {
                    addFormatText("%s are %s, allowing them to break blocks that require a Wasteland hoe, such as %s, faster.",
                        { addPluralisedText(item) },
                        { addLink(KnowledgeArticleIdentifier(LCC.id("effectivity"), LCC.id("wasteland")), "Wasteland Effective") },
                        { addLink(LCCBlocks.papercomb_block) }
                    )
                }
            )
            .addSection(getRepairingSection(item, ingredient.asItem()))
            .addSection(KnowledgeExtensions.craftingRecipes(item))
            .addSection(KnowledgeExtensions.craftingUsages(item))
            .boilerplate(item, renewable = renewable)
            .meta(KnowledgeConstants.me, LocalDateTime.of(2021, 10, 11, 0, 54, 0), LocalDateTime.of(2022, 11, 12, 2, 22, 0))
            .tags("Wasteland", "Wasteland Effective", "Wasteland Optimal", tag, "Hoes", "Tools")

}