package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.block.*
import com.joshmanisdabomb.lcc.data.factory.asset.item.*
import com.joshmanisdabomb.lcc.data.factory.translation.*
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.ThingDirectory

object BlockData : ThingDirectory<BlockDataContainer, Unit>() {

    val test_block_2 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(HorizontalBlockAssetFactory) }
    val test_block_3 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(DirectionalBlockAssetFactory) }
    val test_block_4 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(ColumnBlockAssetFactory) }
    val test_block_5 by createWithName { BlockDataContainer().defaultLang().add(CustomModelBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block")) }) }

    val ruby_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(StorageTranslationFactory) }
    val topaz_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(StorageTranslationFactory) }
    val topaz_clusters by createWithName { BlockDataContainer().affects(LCCBlocks.budding_topaz.crystals.toList()).defaultLang().add(ClusterBlockAssetFactory).add(GeneratedBlockItemAssetFactory) }
    val sapphire_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(StorageTranslationFactory) }
    val uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(StorageTranslationFactory) }
    val enriched_uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(StorageTranslationFactory) }

    val cracked_mud by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(RotationBlockAssetFactory) }

    val oil by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("oil_still"))) }
    val asphalt by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("asphalt_still"))) }
    val road by createWithName { BlockDataContainer().defaultLang().add(RoadBlockAssetFactory) }

    val pumice by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(RotationBlockAssetFactory(x = (0..3).toList(), y = (0..3).toList())) }
    val rhyolite by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(MirroredBlockAssetFactory) }

    val bounce_pad by createWithName { BlockDataContainer().defaultLang().add(BouncePadBlockAssetFactory).add(BouncePadBlockItemFactory) }

    val time_rift by createWithName { BlockDataContainer().defaultLang().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")).add(ParticleBlockAssetFactory).add(DynamicItemAssetFactory) }
    val spawner_table by createWithName { BlockDataContainer().defaultItemAsset().add(LiteralTranslationFactory("Arcane Table")).add(DungeonTableBlockAssetFactory) }

    val classic_grass_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(ClassicGrassBlockAssetFactory) }
    val classic_sapling by createWithName { BlockDataContainer().defaultLang().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory) }
    val potted_classic_sapling by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory) }
    val classic_rose by createWithName { BlockDataContainer().defaultLang().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory) }
    val potted_classic_rose by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory) }
    val cyan_flower by createWithName { BlockDataContainer().defaultLang().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory) }
    val potted_cyan_flower by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory) }
    val classic_iron_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory("en_us", "en_gb") { it.replace("Block of Classic", "Classic Block") }) }
    val classic_gold_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory("en_us", "en_gb") { it.replace("Block of Classic", "Classic Block") }) }
    val classic_diamond_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory("en_us", "en_gb") { it.replace("Block of Classic", "Classic Block") }) }
    val alpha_iron_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().add(StorageTranslationFactory).add(TransformTranslationFactory("en_us", "en_gb") { it.replace("Block of Alpha", "Alpha Block") }) }
    val alpha_gold_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().add(StorageTranslationFactory).add(TransformTranslationFactory("en_us", "en_gb") { it.replace("Block of Alpha", "Alpha Block") }) }
    val alpha_diamond_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().add(StorageTranslationFactory).add(TransformTranslationFactory("en_us", "en_gb") { it.replace("Block of Alpha", "Alpha Block") }) }
    val classic_tnt by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(TransformTranslationFactory("en_us", "en_gb") { it.replace("Tnt", "TNT") }) }
    val pocket_stonecutter by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(StonecutterBlockAssetFactory) }
    val classic_chest by createWithName { BlockDataContainer().defaultLang().add(ClassicChestBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelOrientable(d, i, texture = loc(i, folder = "block")) }) }
    val nether_reactor by createWithName { BlockDataContainer().defaultLang().add(NetherReactorBlockAssetFactory).add(ParentBlockItemAssetFactory(LCC.id("block/nether_reactor_ready"))) }
    val classic_crying_obsidian by createWithName { BlockDataContainer().defaultLang().add(CustomModelBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block") { it.plus("_static") }) }) }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        all.forEach { (k, v) -> v.init(k, LCCBlocks[k]) }

        val missing = LCCBlocks.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCBlocks[it]!!; defaults().init(key, it) }
    }

    fun defaults() = BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset()

    private fun BlockDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun BlockDataContainer.defaultBlockAsset() = add(SimpleBlockAssetFactory)
    private fun BlockDataContainer.defaultItemAsset() = add(ParentBlockItemAssetFactory)

}