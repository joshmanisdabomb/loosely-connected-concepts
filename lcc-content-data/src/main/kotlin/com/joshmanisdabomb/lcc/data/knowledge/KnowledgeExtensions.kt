package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleParagraphFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleRecipeFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleBlockInfoSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleChangelogSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleItemInfoSectionBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.data.storage.RecipeBatch
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.RecipeType

object KnowledgeExtensions {

    fun KnowledgeArticleParagraphFragmentBuilder.addLink(version: LCCVersion) : KnowledgeArticleParagraphFragmentBuilder {
        addLink(version.page, version.label)
        return this
    }

    fun craftingRecipes(item: ItemConvertible) = KnowledgeArticleSectionBuilder(KnowledgeConstants.recipes)
        .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findRecipes(item).map(RecipeBatch.RecipeBatchAnalysis::provider) })
    fun craftingUsages(item: ItemConvertible) = KnowledgeArticleSectionBuilder(KnowledgeConstants.usages)
        .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findUsages(item).map(RecipeBatch.RecipeBatchAnalysis::provider) })
    fun allRecipes(vararg types: RecipeType<*>) = KnowledgeArticleSectionBuilder(KnowledgeConstants.alLRecipes)
        .addFragment(KnowledgeArticleRecipeFragmentBuilder { it.da.recipes.findWithTypes(*types).map(RecipeBatch.RecipeBatchAnalysis::provider) })

    fun KnowledgeArticleBuilder.boilerplate(subject: Any? = null, renewable: Boolean = false) : KnowledgeArticleBuilder {
        this.addSection(KnowledgeArticleChangelogSectionBuilder())
        if (subject is Block) this.addSection(KnowledgeArticleBlockInfoSectionBuilder(renewable = renewable))
        else if (subject is Item) this.addSection(KnowledgeArticleItemInfoSectionBuilder(renewable = renewable))
        return this
    }

}