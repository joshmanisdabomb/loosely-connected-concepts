package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.block.*
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.LiteralTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.StorageTranslationFactory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.ThingDirectory

object BlockData : ThingDirectory<BlockDataContainer, Unit>() {

    val test_block_2 by createWithName { BlockDataContainer().defaultLang().add(HorizontalBlockAssetFactory) }
    val test_block_3 by createWithName { BlockDataContainer().defaultLang().add(DirectionalBlockAssetFactory) }
    val test_block_4 by createWithName { BlockDataContainer().defaultLang().add(ColumnBlockAssetFactory) }
    val test_block_5 by createWithName { BlockDataContainer().defaultLang().add(CustomModelBlockAssetFactory) }

    val ruby_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().add(StorageTranslationFactory) }
    val topaz_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().add(StorageTranslationFactory) }
    val topaz_clusters by createWithName { BlockDataContainer().affects(LCCBlocks.budding_topaz.crystals.toList()).defaultLang().add(ClusterBlockAssetFactory) }
    val sapphire_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().add(StorageTranslationFactory) }
    val uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().add(StorageTranslationFactory) }
    val enriched_uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().add(StorageTranslationFactory) }

    val cracked_mud by createWithName { BlockDataContainer().defaultLang().add(RotationBlockAssetFactory) }

    val oil by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("oil_still"))) }
    val asphalt by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("asphalt_still"))) }
    val road by createWithName { BlockDataContainer().defaultLang().add(RoadBlockAssetFactory) }

    val time_rift by createWithName { BlockDataContainer().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")).add(ParticleBlockAssetFactory) }
    val spawner_table by createWithName { BlockDataContainer().add(LiteralTranslationFactory("Arcane Table")).defaultBlockAsset() }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        all.forEach { (k, v) -> println(LCCBlocks[k]); v.init(k, LCCBlocks[k]) }

        val missing = LCCBlocks.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCBlocks[it]!!; defaults().init(key, it) }
    }

    fun defaults() = BlockDataContainer().defaultLang().defaultBlockAsset()

    private fun BlockDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun BlockDataContainer.defaultBlockAsset() = add(SimpleBlockAssetFactory)

}