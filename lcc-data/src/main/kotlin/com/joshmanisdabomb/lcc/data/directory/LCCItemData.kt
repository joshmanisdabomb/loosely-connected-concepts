package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.container.ItemDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.item.DynamicItemAssetFactory
import com.joshmanisdabomb.lcc.data.factory.asset.item.GeneratedItemAssetFactory
import com.joshmanisdabomb.lcc.data.factory.asset.item.HandheldItemAssetFactory
import com.joshmanisdabomb.lcc.data.factory.recipe.*
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.LiteralTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.TransformTranslationFactory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.ArmorItem
import net.minecraft.item.Items
import net.minecraft.item.ToolItem
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.tag.ItemTags

object LCCItemData : ThingDirectory<ItemDataContainer, Unit>() {

    val ruby by createWithName { ItemDataContainer().defaultLang().defaultItemAsset().add(SmeltFromItemRecipeFactory(LCCBlocks.ruby_ore, RecipeSerializer.BLASTING)).add(RiftFromItemRecipeFactory(Items.EMERALD)) }
    val uranium by createWithName { ItemDataContainer().defaultLang().defaultItemAsset().add(SmeltFromItemRecipeFactory(LCCBlocks.uranium_ore, RecipeSerializer.BLASTING)) }
    val uranium_nugget by createWithName { ItemDataContainer().defaultLang().defaultItemAsset().add(Nugget9RecipeFactory(LCCItems.uranium)) }
    val enriched_uranium_nugget by createWithName { ItemDataContainer().defaultLang().defaultItemAsset().add(Nugget9RecipeFactory(LCCItems.enriched_uranium)) }

    val ruby_equipment by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.split('_').first() == "ruby" }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCItems.ruby)) }
    val topaz_equipment by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.split('_').first() == "topaz" }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCItems.topaz_shard)) }
    val emerald_equipment by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.split('_').first() == "emerald" }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(Items.EMERALD)) }
    val sapphire_equipment by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.split('_').first() == "sapphire" }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCItems.sapphire)) }
    val amethyst_equipment by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.split('_').first() == "amethyst" }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(Items.AMETHYST_SHARD)) }
    val ruby_armor by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.split('_').first() == "ruby" }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.ruby)) }
    val topaz_armor by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.split('_').first() == "topaz" }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.topaz_shard)) }
    val emerald_armor by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.split('_').first() == "emerald" }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(Items.EMERALD)) }
    val sapphire_armor by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.split('_').first() == "sapphire" }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.sapphire)) }
    val amethyst_armor by createWithName { ItemDataContainer().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.split('_').first() == "amethyst" }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(Items.AMETHYST_SHARD)) }

    val simulation_fabric by createWithName { ItemDataContainer().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonFactory.create(i)
            .input(Blocks.STONE).input(Blocks.ANDESITE).input(Blocks.GRANITE).input(Blocks.DIORITE).input(Blocks.COBBLESTONE).input(Blocks.MOSSY_COBBLESTONE).input(Blocks.STONE_BRICKS).input(Blocks.BRICKS)
            .input(Blocks.DIRT).input(Blocks.GRASS_BLOCK).input(Blocks.SAND).input(Blocks.GRAVEL).input(Blocks.CLAY)
            .input(Blocks.OAK_LOG).input(Blocks.SPRUCE_LOG).input(Blocks.BIRCH_LOG).input(Blocks.JUNGLE_LOG).input(Blocks.ACACIA_LOG).input(Blocks.DARK_OAK_LOG)
            .input(Blocks.WHITE_WOOL).input(Items.COAL).input(Items.DIAMOND).input(Items.EMERALD)
            .input(Blocks.OBSIDIAN).input(Blocks.NETHERRACK).input(Blocks.SOUL_SAND).input(Blocks.GLOWSTONE)
            .input(Items.FEATHER).input(Items.ARROW).input(Items.STRING).input(Items.GUNPOWDER).input(Items.PORKCHOP).input(Items.COD).input(Items.GOLDEN_APPLE)
            .input(Blocks.CRAFTING_TABLE).input(Blocks.FURNACE).input(Blocks.CHEST).input(Blocks.JUKEBOX).input(Blocks.TNT).input(Blocks.RED_BED)
            .input(Items.COMPASS).input(Items.CLOCK).input(ItemTags.MUSIC_DISCS)
            .input(Items.CACTUS).input(Items.SUGAR_CANE).input(Items.WHEAT_SEEDS).input(Items.DANDELION).input(Items.POPPY)
            .apply { hasCriterion(this, LCCItems.simulation_fabric) }
            .apply { offer(this, d, override = LCCRecipeSerializers.spawner_table_shapeless) }
    }) }

    val full_hearts by createWithName { ItemDataContainer().affects(LCCItems.heart_full.values.toList()).defaultLang().defaultItemAsset().add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace(" Full", "") }) }

    val gauntlet by createWithName { ItemDataContainer().add(DynamicItemAssetFactory).add(LiteralTranslationFactory("Doom Gauntlet")).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("       br ")
            .pattern("biiiggggbt")
            .pattern("nb  ggdgbe")
            .pattern(" b   ggbs ")
            .pattern("  biigba  ")
            .input('b', Blocks.GOLD_BLOCK)
            .input('i', Items.IRON_INGOT)
            .input('g', Items.GOLD_INGOT)
            .input('n', Items.NETHER_STAR)
            .input('r', LCCBlocks.ruby_block)
            .input('t', LCCBlocks.topaz_block)
            .input('e', Blocks.EMERALD_BLOCK)
            .input('d', Blocks.DIAMOND_BLOCK)
            .input('s', LCCBlocks.sapphire_block)
            .input('a', Blocks.AMETHYST_BLOCK)
            .apply { hasCriterion(this, Items.NETHER_STAR) }
            .apply { offer(this, d, override = LCCRecipeSerializers.spawner_table_shaped) }
    }) }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        all.forEach { (k, v) -> v.init(k, LCCItems[k]) }

        val missing = LCCItems.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCItems[it]!!; defaults().init(key, it) }
    }

    fun defaults() = ItemDataContainer().defaultLang().defaultItemAsset()

    private fun ItemDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun ItemDataContainer.defaultItemAsset() = add(GeneratedItemAssetFactory)

}