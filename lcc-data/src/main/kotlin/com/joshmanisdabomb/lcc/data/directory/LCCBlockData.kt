package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.block.*
import com.joshmanisdabomb.lcc.data.factory.asset.item.*
import com.joshmanisdabomb.lcc.data.factory.loot.block.*
import com.joshmanisdabomb.lcc.data.factory.recipe.*
import com.joshmanisdabomb.lcc.data.factory.tag.BlockTagFactory
import com.joshmanisdabomb.lcc.data.factory.tag.ItemTagFactory
import com.joshmanisdabomb.lcc.data.factory.translation.*
import com.joshmanisdabomb.lcc.data.json.recipe.RefiningShapelessRecipeJsonFactory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.recipe.RefiningRecipe
import net.minecraft.block.Blocks
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Items
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags

object LCCBlockData : ThingDirectory<BlockDataContainer, Unit>() {

    val test_block_2 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(HorizontalBlockAssetFactory) }
    val test_block_3 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(DirectionalBlockAssetFactory) }
    val test_block_4 by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory) }
    val test_block_5 by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(CustomModelBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block")) }) }

    val ruby_ore by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(OreBlockLootFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_ORE)) }
    val ruby_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_BLOCK)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val topaz_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.topaz_shard, from = false)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val budding_topaz by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val topaz_clusters by createWithName { BlockDataContainer().affects(LCCBlocks.budding_topaz.crystals.toList()).defaultLang().add(ClusterBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(ClusterBlockLootFactory(LCCItems.topaz_shard)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val sapphire_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.sapphire)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.uranium)) }
    val enriched_uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.enriched_uranium)) }
    val heavy_uranium_block by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.heavy_uranium)) }

    val cracked_mud by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory).add(BlockTagFactory(LCCTags.wasteland_effective)).add(BlockTagFactory(BlockTags.ENDERMAN_HOLDABLE)) }

    val oil by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("block/oil_still"))) }
    val asphalt by createWithName { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("block/asphalt_still"))) }
    val road by createWithName { BlockDataContainer().defaultLang().add(RoadBlockAssetFactory) } //TODO maybe drop something

    val pumice by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory(x = (0..3).toList(), y = (0..3).toList())) }
    val rhyolite by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(MirroredBlockAssetFactory) }

    val soaking_soul_sand by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonFactory.create(i, 8)
            .input(Blocks.WET_SPONGE)
            .input(Blocks.SOUL_SAND, 8)
            .apply { hasCriterionShapeless(this, Blocks.SPONGE) }
            .apply { offerShapeless(this, d) }
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
            .apply { hasCriterionShaped(this, LCCBlocks.soaking_soul_sand) }
            .apply { offerShaped(this, d) }
    }) }

    val time_rift by createWithName { BlockDataContainer().defaultLang().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")).add(ParticleBlockAssetFactory).add(DynamicItemAssetFactory(DynamicItemAssetFactory.block)).add(SilkBlockLootFactory(LCCItems.simulation_fabric)).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("cdc")
            .pattern("dsd")
            .pattern("cdc")
            .input('c', Items.CLOCK)
            .input('d', Blocks.ANCIENT_DEBRIS)
            .input('s', LCCItems.simulation_fabric)
            .apply { hasCriterionShaped(this, LCCItems.simulation_fabric) }
            .apply { offerShaped(this, d) }
    }) }
    val spawner_table by createWithName { BlockDataContainer().defaultItemAsset().add(LiteralTranslationFactory("Arcane Table")).add(DungeonTableBlockAssetFactory).add(DungeonTableBlockLootFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("rgp")
            .pattern("ooo")
            .input('r', Items.REDSTONE)
            .input('g', Items.GLOWSTONE_DUST)
            .input('p', Items.GUNPOWDER)
            .input('o', Blocks.OBSIDIAN)
            .apply { hasCriterionShaped(this, Items.GLOWSTONE_DUST) }
            .apply { offerShaped(this, d) }
    }).add(BlockTagFactory(BlockTags.DRAGON_IMMUNE)) }

    val classic_grass_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(ClassicGrassBlockAssetFactory).add(SilkBlockLootFactory(Blocks.DIRT)).add(RiftFromItemRecipeFactory(Blocks.GRASS_BLOCK)) }
    val classic_cobblestone by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE)).add(BlockTagFactory(LCCTags.nether_reactor_shell)) }
    val classic_planks by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromTagRecipeFactory(ItemTags.PLANKS)) }
    val classic_leaves by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(LeavesBlockLootFactory(LCCBlocks.classic_sapling)).add(RiftFromTagRecipeFactory(ItemTags.LEAVES)).add(BlockTagFactory(BlockTags.LEAVES)) }
    val classic_sapling by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromTagRecipeFactory(ItemTags.SAPLINGS)).add(BlockTagFactory(BlockTags.SAPLINGS)) }
    val potted_classic_sapling by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_rose by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.POPPY)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_classic_rose by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val cyan_flower by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.BLUE_ORCHID)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_cyan_flower by createWithName { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_gravel by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(ChanceAlternativeBlockLootFactory(Items.FLINT, 0.1f, 0.14285715f, 0.25f, 1.0f)).add(RiftFromItemRecipeFactory(Blocks.GRAVEL)) }
    val classic_sponge by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.SPONGE)) }
    val classic_glass by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(SilkBlockLootFactory).add(RiftFromItemRecipeFactory(Blocks.GLASS)).add(BlockTagFactory(BlockTags.IMPERMEABLE)) }
    val classic_bricks by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.BRICKS)) }
    val classic_mossy_cobblestone by createWithName { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE)) }
    val classic_iron_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.IRON_BLOCK)) }
    val classic_gold_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.GOLD_BLOCK)).add(BlockTagFactory(LCCTags.nether_reactor_base)).add(ItemTagFactory(LCCTags.gold_blocks)) }
    val classic_diamond_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.DIAMOND_BLOCK)) }
    val alpha_iron_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val alpha_gold_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }).add(BlockTagFactory(LCCTags.nether_reactor_base)).add(ItemTagFactory(LCCTags.gold_blocks)) }
    val alpha_diamond_block by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val classic_tnt by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Tnt", "TNT") }).add(RiftFromItemRecipeFactory(Blocks.TNT)) }
    val pocket_stonecutter by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StonecutterBlockAssetFactory).add(RiftFromItemRecipeFactory(Blocks.STONECUTTER)) }
    val classic_chest by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(ClassicChestBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelOrientable(d, i, texture = loc(i, folder = "block")) }).add(RiftFromItemRecipeFactory(Blocks.CHEST)) }
    val nether_reactor by createWithName { BlockDataContainer().defaultLang().add(NetherReactorBlockAssetFactory).add(ParentBlockItemAssetFactory(LCC.id("block/nether_reactor_ready"))).add(NetherReactorBlockLootFactory).add(RiftFromItemRecipeFactory(Items.NETHER_STAR)) }
    val classic_crying_obsidian by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(CustomModelBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block") { it.plus("_static") }) }).add(RiftFromItemRecipeFactory(Blocks.CRYING_OBSIDIAN)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val cog by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(GeneratedBlockItemAssetFactory).add(CogBlockAssetFactory).add(RiftFromItemRecipeFactory(Items.REDSTONE)) }

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

    val refiner by createWithName { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RefiningBlockAssetFactory).add(ConcreteRefiningRecipeFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("ccc")
            .pattern("cpc")
            .pattern("iii")
            .input('c', Items.COPPER_INGOT)
            .input('p', Blocks.PISTON)
            .input('i', Items.IRON_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }).add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(Items.ROTTEN_FLESH)
            .addOutput(Items.LEATHER, 1, RefiningRecipe.OutputFunction.ChanceOutputFunction(0.3f))
            .addOutput(Items.IRON_NUGGET, 1, RefiningRecipe.OutputFunction.ChanceOutputFunction(0.03f))
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.treating", 2, RefiningBlock.RefiningProcess.TREATING)
            .energyPerTick(LooseEnergy.fromCoals(0.25f).div(40f))
            .speed(40, 0.04f, 100f)
            .apply { hasCriterionInterface(this, LCCBlocks.refiner) }
            .apply { offerInterface(this, d, suffix(Items.LEATHER.identifier, "from_refiner")) }
    }) }
    val power_cable by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(Cable4BlockAssetFactory).add(Cable4ItemAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 3)
            .pattern("ccc")
            .input('c', Items.COPPER_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }) }

    val coal_generator by createWithName { BlockDataContainer().defaultLootTable().defaultItemAsset().add(LiteralTranslationFactory("Furnace Generator", "en_us")).add(FiredGeneratorBlockAssetFactory) }
    val oil_generator by createWithName { BlockDataContainer().defaultLootTable().defaultItemAsset().add(LiteralTranslationFactory("Combustion Generator", "en_us")).add(FiredGeneratorBlockAssetFactory) }

    val solar_panel by createWithName { BlockDataContainer().defaultLang().defaultLootTable().add(CustomModelBlockAssetFactory).add(CustomItemAssetFactory { d, i -> ModelTemplates.template_solar_panel.upload(loc(i), Texture().put(TextureKey.TOP, loc(i, folder = "block")).put(TextureKey.SIDE, loc(i, folder = "block") { it.plus("_side") }).put(TextureKey.BOTTOM, loc(i, folder = "block") { it.plus("_bottom") }), d.modelStates::addModel) }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("qqq")
            .pattern("lll")
            .pattern("ici")
            .input('q', Items.QUARTZ)
            .input('l', Items.LAPIS_LAZULI)
            .input('i', Items.IRON_INGOT)
            .input('c', Items.COPPER_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }) }

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