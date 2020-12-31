package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.block.*
import com.joshmanisdabomb.lcc.data.factory.asset.item.*
import com.joshmanisdabomb.lcc.data.factory.loot.block.*
import com.joshmanisdabomb.lcc.data.factory.recipe.*
import com.joshmanisdabomb.lcc.data.factory.tag.BlockTagFactory
import com.joshmanisdabomb.lcc.data.factory.translation.*
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Items
import net.minecraft.tag.ItemTags

object LCCBlockData : ThingDirectory<BlockDataContainer, Unit>() {

    val test_block_2 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(HorizontalBlockAssetFactory) }
    val test_block_3 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(DirectionalBlockAssetFactory) }
    val test_block_4 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory) }
    val test_block_5 by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(CustomModelBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block")) }) }

    val ruby_ore by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(OreBlockLootFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_ORE)) }
    val ruby_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_BLOCK)) }
    val topaz_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.topaz_shard, from = false)) }
    val topaz_clusters by createWithName { BlockDataContainer().affects(LCCBlocks.budding_topaz.crystals.toList()).defaultLang().add(ClusterBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(ClusterBlockLootFactory(LCCItems.topaz_shard)) }
    val sapphire_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.sapphire)) }
    val uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.uranium)) }
    val enriched_uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.enriched_uranium)) }

    val cracked_mud by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory).add(BlockTagFactory(LCCTags.wasteland_effective)) }

    val oil by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("oil_still"))) }
    val asphalt by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("asphalt_still"))) }
    val road by createWithName { BlockDataContainer().defaultLang().add(RoadBlockAssetFactory) } //TODO maybe drop something

    val pumice by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory(x = (0..3).toList(), y = (0..3).toList())) }
    val rhyolite by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(MirroredBlockAssetFactory) }

    val soaking_soul_sand by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonFactory.create(i, 8)
            .input(Blocks.WET_SPONGE)
            .input(Blocks.SOUL_SAND, 8)
            .apply { hasCriterion(this, Blocks.SPONGE) }
            .apply { offer(this, d) }
    }) }
    val bounce_pad by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(BouncePadBlockAssetFactory).add(BouncePadItemAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("rwr")
            .pattern("ipi")
            .pattern("sss")
            .input('r', Items.REPEATER)
            .input('w', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
            .input('i', Blocks.IRON_BLOCK)
            .input('p', Blocks.PISTON)
            .input('s', LCCBlocks.soaking_soul_sand)
            .apply { hasCriterion(this, LCCBlocks.soaking_soul_sand) }
            .apply { offer(this, d) }
    }) }

    val time_rift by createWithName { BlockDataContainer().defaultLang().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")).add(ParticleBlockAssetFactory).add(DynamicItemAssetFactory).add(SilkBlockLootFactory(LCCItems.simulation_fabric)).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("cdc")
            .pattern("dsd")
            .pattern("cdc")
            .input('c', Items.CLOCK)
            .input('d', Blocks.ANCIENT_DEBRIS)
            .input('s', LCCItems.simulation_fabric)
            .apply { hasCriterion(this, LCCItems.simulation_fabric) }
            .apply { offer(this, d) }
    }) }
    val spawner_table by createWithName { BlockDataContainer().defaultItemAsset().add(LiteralTranslationFactory("Arcane Table")).add(DungeonTableBlockAssetFactory).add(DungeonTableBlockLootFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("rgp")
            .pattern("ooo")
            .input('r', Items.REDSTONE)
            .input('g', Items.GLOWSTONE_DUST)
            .input('p', Items.GUNPOWDER)
            .input('o', Blocks.OBSIDIAN)
            .apply { hasCriterion(this, Items.GLOWSTONE_DUST) }
            .apply { offer(this, d) }
    }) }

    val classic_grass_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(ClassicGrassBlockAssetFactory).add(SilkBlockLootFactory(Blocks.DIRT)).add(RiftFromItemRecipeFactory(Blocks.GRASS_BLOCK)) }
    val classic_cobblestone by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE)).add(BlockTagFactory(LCCTags.nether_reactor_shell)) }
    val classic_planks by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromTagRecipeFactory(ItemTags.PLANKS)) }
    val classic_leaves by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(LeavesBlockLootFactory(LCCBlocks.classic_sapling)).add(RiftFromTagRecipeFactory(ItemTags.LEAVES)) }
    val classic_sapling by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromTagRecipeFactory(ItemTags.SAPLINGS)) }
    val potted_classic_sapling by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory) }
    val classic_rose by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.POPPY)) }
    val potted_classic_rose by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory) }
    val cyan_flower by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.BLUE_ORCHID)) }
    val potted_cyan_flower by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory) }
    val classic_gravel by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(ChanceAlternativeBlockLootFactory(Items.FLINT, 0.1f, 0.14285715f, 0.25f, 1.0f)).add(RiftFromItemRecipeFactory(Blocks.GRAVEL)) }
    val classic_sponge by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.SPONGE)) }
    val classic_glass by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(SilkBlockLootFactory).add(RiftFromItemRecipeFactory(Blocks.GLASS)) }
    val classic_bricks by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.BRICKS)) }
    val classic_mossy_cobblestone by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE)) }
    val classic_iron_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block") }).add(RiftFromItemRecipeFactory(Blocks.IRON_BLOCK)) }
    val classic_gold_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block") }).add(RiftFromItemRecipeFactory(Blocks.GOLD_BLOCK)).add(BlockTagFactory(LCCTags.nether_reactor_base)) }
    val classic_diamond_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block") }).add(RiftFromItemRecipeFactory(Blocks.DIAMOND_BLOCK)) }
    val alpha_iron_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block") }) }
    val alpha_gold_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block") }).add(BlockTagFactory(LCCTags.nether_reactor_base)) }
    val alpha_diamond_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block") }) }
    val classic_tnt by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Tnt", "TNT") }).add(RiftFromItemRecipeFactory(Blocks.TNT)) }
    val pocket_stonecutter by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StonecutterBlockAssetFactory).add(RiftFromItemRecipeFactory(Blocks.STONECUTTER)) }
    val classic_chest by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(ClassicChestBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelOrientable(d, i, texture = loc(i, folder = "block")) }).add(RiftFromItemRecipeFactory(Blocks.CHEST)) }
    val nether_reactor by createWithName { BlockDataContainer().defaultLang().add(NetherReactorBlockAssetFactory).add(ParentBlockItemAssetFactory(LCC.id("block/nether_reactor_ready"))).add(NetherReactorBlockLootFactory).add(RiftFromItemRecipeFactory(Items.NETHER_STAR)) }
    val classic_crying_obsidian by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(CustomModelBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block") { it.plus("_static") }) }).add(RiftFromItemRecipeFactory(Blocks.CRYING_OBSIDIAN)) }

    val red_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.RED_WOOL)) }
    val orange_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.ORANGE_WOOL)) }
    val yellow_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.YELLOW_WOOL)) }
    val lime_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIME_WOOL)) }
    val light_blue_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIGHT_BLUE_WOOL)) }
    val purple_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.PURPLE_WOOL)) }
    val magenta_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.MAGENTA_WOOL)) }
    val gray_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.GRAY_WOOL)) }
    val light_gray_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIGHT_GRAY_WOOL)) }
    val white_classic_cloth by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.WHITE_WOOL)) }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        all.forEach { (k, v) -> v.init(k, LCCBlocks[k]) }

        val missing = LCCBlocks.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCBlocks[it]!!; defaults().init(key, it) }
    }

    fun defaults() = BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable()

    private fun BlockDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun BlockDataContainer.defaultBlockAsset() = add(SimpleBlockAssetFactory)
    private fun BlockDataContainer.defaultItemAsset() = add(ParentBlockItemAssetFactory)
    private fun BlockDataContainer.defaultLootTable() = add(SelfBlockLootFactory)

}