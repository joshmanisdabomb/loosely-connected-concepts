package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.*
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.ModelAccess
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.data.factory.asset.block.*
import com.joshmanisdabomb.lcc.data.factory.asset.item.*
import com.joshmanisdabomb.lcc.data.factory.loot.block.*
import com.joshmanisdabomb.lcc.data.factory.recipe.*
import com.joshmanisdabomb.lcc.data.factory.tag.BlockTagFactory
import com.joshmanisdabomb.lcc.data.factory.tag.ItemTagFactory
import com.joshmanisdabomb.lcc.data.factory.translation.*
import com.joshmanisdabomb.lcc.data.json.recipe.RefiningShapelessRecipeJsonFactory
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.recipe.RefiningSimpleRecipe
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.block.Blocks
import net.minecraft.data.client.model.Models
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Items
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier

object LCCBlockData : BasicDirectory<BlockDataContainer, Unit>(), ModelAccess {

    val test_block_2 by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(HorizontalBlockAssetFactory) }
    val test_block_3 by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(DirectionalBlockAssetFactory) }
    val test_block_4 by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory) }
    val test_block_5 by entry(::initialiser) { data().defaultLang().defaultLootTable().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory(mi.cubeAll { idi.loc(it, folder = "block") })) }

    val ruby_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(OreBlockLootFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_ORE)) }
    val ruby_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_BLOCK)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val topaz_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.topaz_shard, from = false)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val budding_topaz by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val topaz_clusters by entry(::initialiser) { data().affects(LCCBlocks.budding_topaz.crystals.toList()).defaultLang().add(ClusterBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(ClusterBlockLootFactory(LCCItems.topaz_shard)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val sapphire_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.sapphire)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val uranium_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(OreBlockLootFactory(LCCItems.uranium)).add(BlockTagFactory(LCCTags.radioactive)) }
    val deepslate_uranium_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(OreBlockLootFactory(LCCItems.uranium)).add(BlockTagFactory(LCCTags.radioactive)) }
    val uranium_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.uranium)).add(BlockTagFactory(LCCTags.radioactive)) }
    val enriched_uranium_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.enriched_uranium)).add(ItemTagFactory(LCCTags.enriched_uranium)).add(BlockTagFactory(LCCTags.radioactive)) }
    val heavy_uranium_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.heavy_uranium)).add(BlockTagFactory(LCCTags.radioactive)) }

    val tungsten_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(GenerousOreBlockLootFactory(LCCItems.raw_tungsten, UniformLootNumberProvider.create(2.0F, 3.0F))) }
    val deepslate_tungsten_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(GenerousOreBlockLootFactory(LCCItems.raw_tungsten, UniformLootNumberProvider.create(2.0F, 3.0F))) }
    val raw_tungsten_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.raw_tungsten)) }
    val tungsten_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.tungsten_ingot)) }
    val cut_tungsten by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i, 4)
            .pattern("tt")
            .pattern("tt")
            .input('t', LCCBlocks.tungsten_block)
            .apply { hasCriterionShaped(this, LCCBlocks.tungsten_block) }
            .apply { offerShaped(this, d) }
    }).add(StonecutterItemRecipeFactory(LCCBlocks.tungsten_block)) }
    val cut_tungsten_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.cut_tungsten.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.cut_tungsten)).add(StonecutterItemRecipeFactory(LCCBlocks.tungsten_block, name = LCC.id("cut_tungsten_stairs_from_tungsten_block_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cut_tungsten)) }
    val cut_tungsten_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.cut_tungsten.identifierLoc(), full = LCCBlocks.cut_tungsten.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.cut_tungsten)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.tungsten_block, 2, name = LCC.id("cut_tungsten_slab_from_tungsten_block_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cut_tungsten, 2)) }

    val cracked_mud by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory).add(BlockTagFactory(LCCTags.wasteland_effective)).add(BlockTagFactory(BlockTags.ENDERMAN_HOLDABLE)) }
    val nuclear_waste by entry(::initialiser) { data().defaultLang ().defaultItemAsset().add(RotationBlockAssetFactory).add(BlockTagFactory(LCCTags.radioactive)) }

    val oil by entry(::initialiser) { data().defaultLang().add(ParticleBlockAssetFactory(LCC.block("oil_still"))) }
    val asphalt by entry(::initialiser) { data().defaultLang().add(ParticleBlockAssetFactory(LCC.block("asphalt_still"))) }
    val road by entry(::initialiser) { data().defaultLang().add(RoadBlockAssetFactory) } //TODO maybe drop something

    val pumice by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory(x = (0..3).toList(), y = (0..3).toList())) }
    val rhyolite by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(MirroredBlockAssetFactory) }

    val soaking_soul_sand by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonFactory.create(i, 8)
            .input(Blocks.WET_SPONGE, 4)
            .input(Blocks.SOUL_SAND, 4)
            .apply { hasCriterionShapeless(this, Blocks.SPONGE) }
            .apply { offerShapeless(this, d) }
    }) }
    val bounce_pad by entry(::initialiser) { data().defaultLang().defaultLootTable().add(BouncePadBlockAssetFactory).add(BouncePadItemAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i, 6)
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

    val time_rift by entry(::initialiser) { data().defaultLang().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")).add(ParticleBlockAssetFactory).add(DynamicItemAssetFactory(DynamicItemAssetFactory.block)).add(SilkBlockLootFactory(LCCItems.simulation_fabric)).add(CustomRecipeFactory { d, i ->
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
    val spawner_table by entry(::initialiser) { data().defaultItemAsset().add(LiteralTranslationFactory("Arcane Table")).add(DungeonTableBlockAssetFactory).add(DungeonTableBlockLootFactory).add(CustomRecipeFactory { d, i ->
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

    val classic_grass_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ClassicGrassBlockAssetFactory).add(SilkBlockLootFactory(Blocks.DIRT)).add(RiftFromItemRecipeFactory(Blocks.GRASS_BLOCK)) }
    val classic_cobblestone by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE)).add(BlockTagFactory(LCCTags.nether_reactor_shell)) }
    val classic_planks by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromTagRecipeFactory(ItemTags.PLANKS)) }
    val classic_leaves by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(LeavesBlockLootFactory(LCCBlocks.classic_sapling)).add(RiftFromTagRecipeFactory(ItemTags.LEAVES)).add(BlockTagFactory(BlockTags.LEAVES)) }
    val classic_sapling by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromTagRecipeFactory(ItemTags.SAPLINGS)).add(BlockTagFactory(BlockTags.SAPLINGS)) }
    val potted_classic_sapling by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_rose by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.POPPY)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_classic_rose by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val cyan_flower by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.BLUE_ORCHID)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_cyan_flower by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_gravel by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(ChanceAlternativeBlockLootFactory(Items.FLINT, 0.1f, 0.14285715f, 0.25f, 1.0f)).add(RiftFromItemRecipeFactory(Blocks.GRAVEL)) }
    val classic_sponge by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.SPONGE)) }
    val classic_glass by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().add(SilkBlockLootFactory).add(RiftFromItemRecipeFactory(Blocks.GLASS)).add(BlockTagFactory(BlockTags.IMPERMEABLE)) }
    val classic_bricks by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.BRICKS)) }
    val classic_mossy_cobblestone by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE)) }
    val classic_iron_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.IRON_BLOCK)) }
    val classic_gold_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.GOLD_BLOCK)).add(BlockTagFactory(LCCTags.nether_reactor_base)).add(ItemTagFactory(LCCTags.gold_blocks)) }
    val classic_diamond_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.DIAMOND_BLOCK)) }
    val alpha_iron_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val alpha_gold_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }).add(BlockTagFactory(LCCTags.nether_reactor_base)).add(ItemTagFactory(LCCTags.gold_blocks)) }
    val alpha_diamond_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val classic_tnt by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(TransformTranslationFactory(*LCCData.locales.toTypedArray()) { it.replace("Tnt", "TNT") }).add(RiftFromItemRecipeFactory(Blocks.TNT)) }
    val pocket_stonecutter by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StonecutterBlockAssetFactory).add(RiftFromItemRecipeFactory(Blocks.STONECUTTER)) }
    val classic_chest by entry(::initialiser) { data().defaultLang().defaultLootTable().add(ClassicChestBlockAssetFactory).add(CustomItemAssetFactory(mi.orientable({ idi.loc(it, folder = "block") }))).add(RiftFromItemRecipeFactory(Blocks.CHEST)) }
    val nether_reactor by entry(::initialiser) { data().defaultLang().add(NetherReactorBlockAssetFactory).add(ParentBlockItemAssetFactory(LCCBlocks.nether_reactor.identifierLocSuffix("ready"))).add(NetherReactorBlockLootFactory).add(RiftFromItemRecipeFactory(Items.NETHER_STAR)) }
    val classic_crying_obsidian by entry(::initialiser) { data().defaultLang().defaultLootTable().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory(mi.cubeAll { idi.locSuffix(it, "static", folder = "block") })).add(RiftFromItemRecipeFactory(Blocks.CRYING_OBSIDIAN)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val cog by entry(::initialiser) { data().defaultLang().defaultLootTable().add(GeneratedBlockItemAssetFactory).add(CogBlockAssetFactory).add(RiftFromItemRecipeFactory(Items.REDSTONE)) }

    val red_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.RED_WOOL)) }
    val orange_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.ORANGE_WOOL)) }
    val yellow_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.YELLOW_WOOL)) }
    val lime_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIME_WOOL)) }
    val light_blue_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIGHT_BLUE_WOOL)) }
    val purple_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.PURPLE_WOOL)) }
    val magenta_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.MAGENTA_WOOL)) }
    val gray_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.GRAY_WOOL)) }
    val light_gray_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIGHT_GRAY_WOOL)) }
    val white_classic_cloth by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.WHITE_WOOL)) }

    val machine_enclosure by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("ccc")
            .pattern("cpc")
            .pattern("iii")
            .input('c', Items.COPPER_INGOT)
            .input('p', LCCBlocks.power_cable)
            .input('i', Items.IRON_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }) }

    val refiner by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(RefiningBlockAssetFactory).add(ConcreteRefiningRecipeFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("rcr")
            .pattern("smb")
            .pattern("rpr")
            .input('m', LCCBlocks.machine_enclosure)
            .input('c', Blocks.CRAFTING_TABLE)
            .input('s', Blocks.SMOKER)
            .input('b', Blocks.BLAST_FURNACE)
            .input('p', Blocks.PISTON)
            .input('r', Items.REDSTONE)
            .apply { hasCriterionShaped(this, LCCBlocks.machine_enclosure) }
            .apply { offerShaped(this, d) }
    }).add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(Items.ROTTEN_FLESH)
            .addOutput(Items.LEATHER, 1, RefiningSimpleRecipe.OutputFunction.ChanceOutputFunction(0.3f))
            .addOutput(Items.IRON_NUGGET, 1, RefiningSimpleRecipe.OutputFunction.ChanceOutputFunction(0.03f))
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.treating", 2, RefiningBlock.RefiningProcess.TREATING)
            .speed(200, 0.04f, 100f)
            .energyPerOperation(LooseEnergy.fromCoals(1f))
            .apply { hasCriterionInterface(this, LCCBlocks.refiner) }
            .apply { offerInterface(this, d, Items.LEATHER.identifier.run { LCC.id(path) }.suffix("from_refiner")) }
    }) }
    val power_cable by entry(::initialiser) { data().defaultLang().defaultLootTable().add(Cable4BlockAssetFactory).add(Cable4ItemAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 3)
            .pattern("ccc")
            .input('c', Items.COPPER_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }) }

    val coal_generator by entry(::initialiser) { data().defaultLootTable().defaultItemAsset().add(LiteralTranslationFactory("Furnace Generator", "en_us")).add(FiredGeneratorBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("e")
            .pattern("m")
            .pattern("f")
            .input('e', Items.IRON_BARS)
            .input('m', LCCBlocks.machine_enclosure)
            .input('f', Blocks.FURNACE)
            .apply { hasCriterionShaped(this, LCCBlocks.machine_enclosure) }
            .apply { offerShaped(this, d) }
    }).add(ItemTagFactory(LCCTags.generators)) }
    val oil_generator by entry(::initialiser) { data().defaultLootTable().defaultItemAsset().add(LiteralTranslationFactory("Combustion Generator", "en_us")).add(FiredGeneratorBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("e")
            .pattern("m")
            .pattern("f")
            .input('e', Items.IRON_BARS)
            .input('m', LCCBlocks.machine_enclosure)
            .input('f', Items.FLINT_AND_STEEL)
            .apply { hasCriterionShaped(this, LCCBlocks.machine_enclosure) }
            .apply { offerShaped(this, d) }
    }).add(ItemTagFactory(LCCTags.generators)) }

    val solar_panel by entry(::initialiser) { data().defaultLang().defaultLootTable().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.template_solar_panel.upload(i(t) ?: idi.loc(t), Texture().put(TextureKey.TOP, idi.loc(t, folder = "block")).put(TextureKey.SIDE, idi.locSuffix(t, "side", folder = "block")).put(TextureKey.BOTTOM, idi.locSuffix(t, "bottom", folder = "block")), d.modelStates::addModel) }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("sss")
            .pattern("lll")
            .pattern("ici")
            .input('s', LCCItems.silicon)
            .input('l', Items.LAPIS_LAZULI)
            .input('i', Items.IRON_INGOT)
            .input('c', Items.COPPER_INGOT)
            .apply { hasCriterionShaped(this, LCCItems.silicon) }
            .apply { offerShaped(this, d) }
    }) }

    val turbine by entry(::initialiser) { data().defaultLang().defaultLootTable().add(LiteralTranslationFactory("Steam Turbine", "en_us")).add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.template_solar_panel.upload(i(t) ?: idi.loc(t), Texture().put(TextureKey.TOP, idi.loc(t, folder = "block")).put(TextureKey.SIDE, LCCBlocks.solar_panel.identifierLocSuffix("side", folder = "block")).put(TextureKey.BOTTOM, LCCBlocks.solar_panel.identifierLocSuffix("bottom", folder = "block")), d.modelStates::addModel) }).add(TurbineBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern(" t ")
            .pattern("ici")
            .input('t', LCCItems.turbine_blades)
            .input('i', Items.IRON_INGOT)
            .input('c', Items.COPPER_INGOT)
            .apply { hasCriterionShaped(this, LCCItems.turbine_blades) }
            .apply { offerShaped(this, d) }
    }) }

    val energy_bank by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(DirectionalBlockAssetFactory(model = mb.cubeBottomTop({ LCCBlocks.machine_enclosure.identifierLoc() }, { idb.loc(it) }))).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("bib")
            .pattern("bmb")
            .pattern("bib")
            .input('m', LCCBlocks.machine_enclosure)
            .input('i', Items.IRON_INGOT)
            .input('b', LCCItems.redstone_battery)
            .apply { hasCriterionShaped(this, LCCItems.redstone_battery) }
            .apply { offerShaped(this, d) }
    }) }

    val nuclear_fire by entry(::initialiser) { data().defaultLang().add(StaticFireBlockAssetFactory).add(BlockTagFactory(LCCTags.radioactive)) }
    val atomic_bomb by entry(::initialiser) { data().defaultLang().add(AtomicBombBlockAssetFactory).add(AtomicBombBlockLootFactory).add(CustomItemAssetFactory { d, t, i ->
        LCCModelTemplates.template_atomic_bomb_item.upload(idi.loc(t), Texture()
            .put(LCCModelTextureKeys.t1, idi.locSuffix(t, "tail_side", folder = "block"))
            .put(LCCModelTextureKeys.t2, idi.locSuffix(t, "tail", folder = "block"))
            .put(LCCModelTextureKeys.t3, idi.locSuffix(t, "fin", folder = "block"))
            .put(LCCModelTextureKeys.t4, idi.locSuffix(t, "core", folder = "block"))
            .put(LCCModelTextureKeys.t5, idi.locSuffix(t, "head", folder = "block"))
        , d.modelStates::addModel) })
    .add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("ccc")
            .pattern("bdc")
            .pattern("ccc")
            .input('c', Blocks.IRON_BLOCK)
            .input('b', ItemTags.BUTTONS)
            .input('d', Blocks.DISPENSER)
            .criterion("has_enriched_uranium", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCTags.enriched_uranium).build()))
            .apply { offerShaped(this, d) }
    }) }

    val natural_rubber_log by entry(::initialiser) { data().defaultLang().add(CustomItemAssetFactory(mi.orientableBottom({ LCCBlocks.rubber_log.identifierLoc() }, textureFront = { LCCBlocks.rubber_log.identifierLocSuffix("tapped") }, textureSide = { LCCBlocks.rubber_log.identifierLoc() }, textureBottom = { LCCBlocks.rubber_log.identifierLocSuffix("top") }))).add(BooleanHorizontalSideBlockAssetFactory(LCCBlocks.rubber_log.identifierLocSuffix("tapped"), LCCBlocks.rubber_log.identifierLoc(), LCCBlocks.rubber_log.identifierLocSuffix("top"))).add(SimpleBlockLootFactory(LCCBlocks.rubber_log)).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.rubber_log.identifierLoc(), textureEnd = LCCBlocks.rubber_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_wood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.rubber_log.identifierLoc(), textureEnd = LCCBlocks.rubber_log.identifierLoc())).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(BarkRecipeFactory(LCCBlocks.rubber_log)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val sappy_stripped_rubber_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SapBurstBlockAssetFactory(texture = LCCBlocks.stripped_rubber_log.identifierLoc())).add(SimpleBlockLootFactory(LCCBlocks.stripped_rubber_log)).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val stripped_rubber_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_rubber_log.identifierLoc(), textureEnd = LCCBlocks.stripped_rubber_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val stripped_rubber_wood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_rubber_log.identifierLoc(), textureEnd = LCCBlocks.stripped_rubber_log.identifierLoc())).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(BarkRecipeFactory(LCCBlocks.stripped_rubber_log)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_planks by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().add(BlockTagFactory(BlockTags.PLANKS)).add(ItemTagFactory(ItemTags.PLANKS)).add(SingleShapelessFromTagRecipeFactory(LCCTags.rubber_logs_i, "has_rubber_logs" to InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCTags.rubber_logs_i).build()), outputCount = 4, group = "planks")) }
    val rubber_sapling by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(BlockTagFactory(BlockTags.SAPLINGS)).add(ItemTagFactory(ItemTags.SAPLINGS)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val potted_rubber_sapling by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val rubber_leaves by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(LeavesBlockLootFactory(LCCBlocks.rubber_sapling, saplingChance = floatArrayOf(0.067F, 0.09F, 0.12F, 0.15F))).add(BlockTagFactory(BlockTags.LEAVES)).add(TintBlockAssetFactory).add(BlockTagFactory(BlockTags.LEAVES)).add(ItemTagFactory(ItemTags.LEAVES)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.WOODEN_STAIRS)).add(StairsRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_stairs")) }
    val rubber_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc(), full = LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.WOODEN_SLABS)).add(SlabRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_slab")).add(SlabLootFactory) }
    val rubber_door by entry(::initialiser) { data().defaultLang().add(DoorBlockAssetFactory).add(GeneratedItemAssetFactory).add(DoorBlockLootFactory).add(BlockTagFactory(BlockTags.WOODEN_DOORS)).add(ItemTagFactory(ItemTags.WOODEN_DOORS)).add(DoorRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_door")) }
    val rubber_pressure_plate by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(PressurePlateBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_PRESSURE_PLATES)).add(ItemTagFactory(ItemTags.WOODEN_PRESSURE_PLATES)).add(PressurePlateRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_pressure_plate")) }
    val rubber_button by entry(::initialiser) { data().defaultLang().defaultLootTable().add(CustomItemAssetFactory { d, t, i -> Models.BUTTON_INVENTORY.upload(i(t) ?: idi.loc(t), Texture.texture(LCCBlocks.rubber_planks.identifierLoc()), d.modelStates::addModel) }).add(ButtonBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_BUTTONS)).add(ItemTagFactory(ItemTags.WOODEN_BUTTONS)).add(SingleShapelessFromItemRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_button")) }
    val rubber_fence by entry(::initialiser) { data().defaultLang().defaultLootTable().add(CustomItemAssetFactory { d, t, i -> Models.FENCE_INVENTORY.upload(i(t) ?: idi.loc(t), Texture.texture(LCCBlocks.rubber_planks.identifierLoc()), d.modelStates::addModel) }).add(FenceBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_FENCES)).add(ItemTagFactory(ItemTags.WOODEN_FENCES)).add(FenceRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence")) }
    val rubber_fence_gate by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(FenceGateBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.FENCE_GATES)).add(FenceGateRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence_gate")) }
    val rubber_trapdoor by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(TrapdoorBlockAssetFactory).add(BlockTagFactory(BlockTags.WOODEN_TRAPDOORS)).add(ItemTagFactory(ItemTags.WOODEN_TRAPDOORS)).add(TrapdoorRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_trapdoor")) }
    val rubber_sign by entry(::initialiser) { data().defaultLang().defaultLootTable().add(ParticleBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(GeneratedItemAssetFactory).add(BlockTagFactory(BlockTags.STANDING_SIGNS)).add(ItemTagFactory(ItemTags.SIGNS)).add(SignRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK), group = "wooden_sign")) }
    val rubber_wall_sign by entry(::initialiser) { data().add(ParticleBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WALL_SIGNS)) }
    val treetap_bowl by entry(::initialiser) { data().defaultLang().add(TreetapStorageBlockAssetFactory(AbstractTreetapBlock.TreetapContainer.BOWL, LCCModelTemplates.template_treetap_bowl_1, LCCModelTemplates.template_treetap_bowl_2, LCCModelTemplates.template_treetap_bowl_3, LCCModelTemplates.template_treetap_bowl_dried) { d, t, i -> LCCModelTemplates.template_treetap_bowl.upload(i(t) ?: idb.loc(t), Texture().put(LCCModelTextureKeys.t0, LCCBlocks.treetap.identifierLoc()).put(LCCModelTextureKeys.t1, idb.loc(t)), d.modelStates::addModel) }).add(TreetapContainerBlockLootFactory(LCCBlocks.treetap, Items.BOWL)) }
    val treetap by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(TreetapBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 2)
            .pattern("i  ")
            .pattern("iii")
            .input('i', Items.IRON_INGOT)
            .criterion("has_rubber_tree", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCTags.rubber_tree).build()))
            .apply { offerShaped(this, d) }
    }).add(TreetapBlockLootFactory<TreetapBlock.TreetapState, TreetapBlock.TreetapState>(LCCBlocks.treetap, TreetapBlock.tap, { it.container?.item })) }
    val dried_treetap by entry(::initialiser) { data().defaultLang().add(TreetapDriedBlockAssetFactory).add(TreetapBlockLootFactory(LCCBlocks.treetap, DriedTreetapBlock.container, AbstractTreetapBlock.TreetapContainer::item, DriedTreetapBlock.liquid, { it.dryProduct?.item })) }
    val oxygen_extractor by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(CustomBlockAssetFactory(mb.cubeBottomTop(textureBottom = { LCCBlocks.machine_enclosure.identifierLocSuffix("bottom") }))).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("i i")
            .pattern("i i")
            .pattern("bmb")
            .input('m', LCCBlocks.machine_enclosure)
            .input('i', Items.IRON_INGOT)
            .input('b', Items.BUCKET)
            .apply { hasCriterionShaped(this, LCCItems.oxygen_tank) }
            .apply { offerShaped(this, d) }
    }) }

    val kiln by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(FurnaceBlockAssetFactory(textureTop = LCCBlocks.kiln.identifierLocSuffix("side"), textureBottom = LCCBlocks.kiln.identifierLocSuffix("side"))).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("bbb")
            .pattern("bfb")
            .pattern("bbb")
            .input('b', Blocks.BRICKS)
            .input('f', Blocks.FURNACE)
            .apply { hasCriterionShaped(this, Blocks.BRICKS) }
            .apply { offerShaped(this, d) }
    }) }

    val dirt_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(Identifier("minecraft:block/dirt"), full = Identifier("minecraft:block/dirt"))).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(RiftFromItemRecipeFactory(Blocks.DIRT, 2)).add(SlabLootFactory) }
    val classic_wooden_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.classic_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_planks)).add(RiftFromTagRecipeFactory(ItemTags.WOODEN_STAIRS)) }
    val classic_wooden_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.classic_planks.identifierLoc(), full = LCCBlocks.classic_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_planks)).add(RiftFromTagRecipeFactory(ItemTags.WOODEN_SLABS)).add(SlabLootFactory) }
    val classic_cobblestone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.classic_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE_STAIRS)).add(StonecutterItemRecipeFactory(LCCBlocks.classic_cobblestone)) }
    val classic_cobblestone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.classic_cobblestone.identifierLoc(), full = LCCBlocks.classic_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE_SLAB)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.classic_cobblestone, 2)) }
    val classic_brick_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.classic_bricks.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_bricks)).add(RiftFromItemRecipeFactory(Blocks.BRICK_STAIRS)).add(StonecutterItemRecipeFactory(LCCBlocks.classic_bricks)) }
    val classic_brick_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.classic_bricks.identifierLoc(), full = LCCBlocks.classic_bricks.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_bricks)).add(RiftFromItemRecipeFactory(Blocks.BRICK_SLAB)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.classic_bricks, 2)) }
    val classic_mossy_cobblestone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.classic_mossy_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_mossy_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE_STAIRS)).add(StonecutterItemRecipeFactory(LCCBlocks.classic_mossy_cobblestone)) }
    val classic_mossy_cobblestone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.classic_mossy_cobblestone.identifierLoc(), full = LCCBlocks.classic_mossy_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_mossy_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE_SLAB)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.classic_mossy_cobblestone, 2)) }
    val rhyolite_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.rhyolite.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.rhyolite)).add(StonecutterItemRecipeFactory(LCCBlocks.rhyolite)) }
    val rhyolite_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.rhyolite.identifierLoc(), full = LCCBlocks.rhyolite.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.rhyolite)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.rhyolite, 2)) }

    val heavy_uranium_shielding by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(Blocks.SAND, 4)
            .addInput(Blocks.GRAVEL, 4)
            .addInput(LCCItems.heavy_uranium_nugget, 4)
            .addOutput(i, 12)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.ducrete_mixing", 0, RefiningBlock.RefiningProcess.MIXING)
            .speed(2000, 0.04f, 100f)
            .energyPerOperation(LooseEnergy.fromCoals(5f))
            .apply { hasCriterionInterface(this, LCCItems.heavy_uranium_nugget) }
            .apply { offerInterface(this, d) }
    }) }
    val nuclear_generator by entry(::initialiser) { data().defaultLang().defaultLootTable().add(NuclearFiredGeneratorBlockAssetFactory).add(CustomItemAssetFactory { d, t, i ->
        LCCModelTemplates.template_nuclear_generator_item.upload(idi.loc(t), Texture()
            .put(LCCModelTextureKeys.t0, LCC.block("generator"))
            .put(LCCModelTextureKeys.t1, idi.locSuffix(t, "bottom_side", folder = "block"))
            .put(LCCModelTextureKeys.t2, idi.locSuffix(t, "top_side", folder = "block"))
            .put(LCCModelTextureKeys.t3, LCCBlocks.machine_enclosure.asItem().identifierLocSuffix("bottom", folder = "block"))
            .put(LCCModelTextureKeys.t4, LCCBlocks.machine_enclosure.asItem().identifierLocSuffix("top", folder = "block"))
            .put(LCCModelTextureKeys.t5, idi.locSuffix(t, "redstone", folder = "block"))
            .put(LCCModelTextureKeys.t6, idi.locSuffix(t, "bottom", folder = "block"))
            .put(LCCModelTextureKeys.t7, idi.locSuffix(t, "level", folder = "block"))
            .put(LCCModelTextureKeys.t8, idi.locSuffix(t, "level", folder = "block"))
            .put(LCCModelTextureKeys.t9, LCCBlocks.heavy_uranium_shielding.identifierLoc(folder = "block"))
        , d.modelStates::addModel) }
    ).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern("ses")
            .pattern("bmr")
            .pattern("sds")
            .input('s', LCCBlocks.heavy_uranium_shielding)
            .input('e', Items.IRON_BARS)
            .input('m', LCCBlocks.machine_enclosure)
            .input('d', Blocks.DISPENSER)
            .input('b', ItemTags.BUTTONS)
            .input('r', Items.REDSTONE)
            .apply { hasCriterionShaped(this, LCCItems.heavy_uranium_nugget) }
            .apply { offerShaped(this, d) }
    }) }
    val failing_nuclear_generator by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ExplodingNuclearFiredGeneratorBlockAssetFactory).add(BlockTagFactory(LCCTags.temperature_nuclear)) }

    val rock_salt by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(RotationBlockAssetFactory((0..3).toList(), (0..3).toList(), mb.cubeAll())) }
    val scattered_salt by entry(::initialiser) { data().defaultLang().add(SaltBlockLootFactory).add(SaltBlockAssetFactory).add(CustomItemAssetFactory { d, t, i ->
        LCCModelTemplates.template_salt_item.upload(i(t) ?: idi.loc(t), Texture.texture(LCC.block("salt")), d.modelStates::addModel)
    }) }
    val salt_block by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(RotationBlockAssetFactory((0..3).toList(), (0..3).toList(), mb.cubeAll { LCC.block("salt") })).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("sss")
            .pattern("sss")
            .pattern("sss")
            .input('s', LCCItems.salt)
            .apply { hasCriterionShaped(this, LCCItems.salt) }
            .apply { offerShaped(this, d) }
    }) }
    val alarm by entry(::initialiser) { data().defaultLang().defaultLootTable().add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.template_alarm_item.upload(i(t) ?: idi.loc(t), Texture.texture(idi.loc(t, folder = "block")), d.modelStates::addModel) }).add(HorizontalBlockAssetFactory({ d, t, i -> LCCModelTemplates.template_alarm.upload(i(t) ?: idb.loc(t), Texture.texture(idb.loc(t)), d.modelStates::addModel) })).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 2)
            .pattern("bib")
            .pattern("nln")
            .input('b', Blocks.BELL)
            .input('i', Items.IRON_INGOT)
            .input('l', Blocks.LEVER)
            .input('n', Blocks.NOTE_BLOCK)
            .apply { hasCriterionShaped(this, Blocks.BELL) }
            .apply { offerShaped(this, d) }
    }) }
    val radar by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(RadarBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        //TODO temp recipe before sculks
        ShapedRecipeJsonFactory(i, 1)
            .pattern("r r")
            .pattern("pcp")
            .pattern("ete")
            .input('r', Blocks.REDSTONE_TORCH)
            .input('p', Items.ENDER_PEARL)
            .input('e', Items.ENDER_EYE)
            .input('c', Blocks.COPPER_BLOCK)
            .input('t', LCCBlocks.tungsten_block)
            .apply { hasCriterionShaped(this, LCCItems.tungsten_ingot) }
            .apply { hasCriterionShaped(this, Items.ENDER_EYE) }
            .apply { offerShaped(this, d) }
    }) }
    val rubber_block by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(MultipleBlockAssetFactory(y = (0..3).toList(), List(5) {
        mb.cubeAll { t -> idb.locSuffix(t, it.plus(1).toString()) }
    })).add(Storage9RecipeFactory(LCCItems.heavy_duty_rubber)) }

    val deposits by entry(::initialiser) { data().affects(LCCBlocks.all.values.filterIsInstance<DepositBlock>()).defaultLang().defaultItemAsset().add(DepositBlockAssetFactory).add(DepositBlockLootFactory) }

    val rubber_piston by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(PistonBlockAssetFactory).add(CustomItemAssetFactory(mi.cubeBottomTop(texture = { idi.loc(it, folder = "block") }, textureBottom = { Identifier("block/piston_bottom") }))).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i, 1)
            .pattern("rrr")
            .pattern("cic")
            .pattern("cdc")
            .input('r', LCCItems.heavy_duty_rubber)
            .input('c', Blocks.COBBLESTONE)
            .input('i', Items.IRON_INGOT)
            .input('d', Items.REDSTONE)
            .apply { hasCriterionShaped(this, LCCItems.heavy_duty_rubber) }
            .apply { offerShaped(this, d) }
    }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i, 1)
            .pattern("rrr")
            .pattern(" p ")
            .input('r', LCCItems.heavy_duty_rubber)
            .input('p', Blocks.PISTON)
            .apply { hasCriterionShaped(this, LCCItems.heavy_duty_rubber) }
            .apply { offerShaped(this, d, i.identifier.suffix("upgrade")) }
    }) }
    val rubber_piston_head by entry(::initialiser) { data().add(PistonHeadBlockAssetFactory(idb.locSuffix(LCCBlocks.rubber_piston, "top"))) }

    val cracked_mud_pressure_plate by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(PressurePlateBlockAssetFactory(LCCBlocks.cracked_mud.identifierLoc())).add(BlockTagFactory(BlockTags.PRESSURE_PLATES)).add(PressurePlateRecipeFactory(LCCBlocks.cracked_mud)) }

    val explosive_paste by entry(::initialiser) { data().defaultLang().defaultLootTable().add(GeneratedItemAssetFactory).add(WireBlockAssetFactory(
        { d, t, i -> LCCModelTemplates.template_redstone_dust_dot.upload(i(t) ?: idb.loc(t), Texture.texture(i(t) ?: idb.loc(t)).put(TextureKey.PARTICLE, idb.loc(t)).put(LCCModelTextureKeys.overlay, Identifier("block/redstone_dust_overlay")), d.modelStates::addModel) },
        { s, l -> ModelProvider.ModelFactory { d, t, i -> s.transform(LCCModelTemplates.template_redstone_dust_side_alt, LCCModelTemplates.template_redstone_dust_side).upload(i(t) ?: idb.loc(t), Texture.texture(idb.locSuffix(t, "line".plus(l.toString()))).put(TextureKey.PARTICLE, idb.loc(t)).put(LCCModelTextureKeys.overlay, Identifier("block/redstone_dust_overlay")), d.modelStates::addModel) } },
        { d, t, i -> LCCModelTemplates.template_redstone_dust_up.upload(i(t) ?: idb.loc(t), Texture.texture(idb.locSuffix(t, "up")).put(TextureKey.PARTICLE, idb.loc(t)).put(LCCModelTextureKeys.overlay, Identifier("block/redstone_dust_overlay")), d.modelStates::addModel) },
        true
    )).add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(Items.GUNPOWDER, 1)
            .addInput(Items.BLAZE_POWDER, 1)
            .addInput(LCCItems.fuel_bucket, 1)
            .addInput(Items.SLIME_BALL, 1)
            .addOutput(LCCBlocks.explosive_paste, 10)
            .addOutput(Items.BUCKET, 1)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.paste_mixing", 0, RefiningBlock.RefiningProcess.MIXING)
            .speed(4000, 0.005f, 100f)
            .energyPerOperation(LooseEnergy.fromCoals(8f))
            .apply { hasCriterionInterface(this, LCCItems.oil_bucket) }
            .apply { offerInterface(this, d) }
    }) }

    val rusted_iron_block by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().defaultBlockAsset().add(Storage9RecipeFactory(LCCItems.iron_oxide)) }
    val rusted_iron_bars by entry(::initialiser) { data().defaultLang().defaultLootTable().add(GeneratedBlockItemAssetFactory).add(BarsBlockAssetFactory(
        { d, t, i -> LCCModelTemplates.template_iron_bars_post.upload(i(t) ?: idb.loc(t), Texture().put(LCCModelTextureKeys.bars, idb.loc(t)), d.modelStates::addModel) },
        { d, t, i -> LCCModelTemplates.template_iron_bars_post_ends.upload(i(t) ?: idb.loc(t), Texture().put(TextureKey.EDGE, idb.loc(t)), d.modelStates::addModel) },
        { d, t, i -> LCCModelTemplates.template_iron_bars_cap.upload(i(t) ?: idb.loc(t), Texture().put(LCCModelTextureKeys.bars, idb.loc(t)), d.modelStates::addModel) },
        { d, t, i -> LCCModelTemplates.template_iron_bars_cap_alt.upload(i(t) ?: idb.loc(t), Texture().put(LCCModelTextureKeys.bars, idb.loc(t)), d.modelStates::addModel) },
        { d, t, i -> LCCModelTemplates.template_iron_bars_side.upload(i(t) ?: idb.loc(t), Texture().put(LCCModelTextureKeys.bars, idb.loc(t)).put(TextureKey.EDGE, idb.loc(t)), d.modelStates::addModel) },
        { d, t, i -> LCCModelTemplates.template_iron_bars_side_alt.upload(i(t) ?: idb.loc(t), Texture().put(LCCModelTextureKeys.bars, idb.loc(t)).put(TextureKey.EDGE, idb.loc(t)), d.modelStates::addModel) }
    )).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i, 16)
            .pattern("iii")
            .pattern("iii")
            .input('i', LCCItems.iron_oxide)
            .apply { hasCriterionShaped(this, LCCItems.iron_oxide) }
            .apply { offerShaped(this, d) }
    }) }

    val improvised_explosive by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(VariantBlockAssetFactory(ImprovisedExplosiveBlock.ie_state, { s -> mb.cubeBottomTop(textureSide = { idb.locSuffix(it, s.asString()) }) })).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i, 1)
            .pattern("iei")
            .pattern("ltl")
            .pattern("iei")
            .input('i', LCCItems.iron_oxide)
            .input('e', LCCBlocks.explosive_paste)
            .input('l', Blocks.REDSTONE_LAMP)
            .input('t', Blocks.TNT)
            .apply { hasCriterionShaped(this, LCCBlocks.explosive_paste) }
            .apply { offerShaped(this, d) }
    }) }

    val fortstone by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().add(SilkBlockLootFactory(LCCBlocks.cobbled_fortstone)).add(SmeltFromItemRecipeFactory(LCCBlocks.cobbled_fortstone, RecipeSerializer.SMELTING)) }
    val polished_fortstone by entry(::initialiser) { data().defaultLang().defaultLootTable().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory(mi.cubeAll { idi.loc(it, folder = "block") })).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i, 4)
            .pattern("ff")
            .pattern("ff")
            .input('f', LCCBlocks.fortstone)
            .apply { hasCriterionShaped(this, LCCBlocks.fortstone) }
            .apply { offerShaped(this, d) }
    }).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone)) }
    val fortstone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.fortstone)).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone)) }
    val fortstone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.fortstone.identifierLoc(), full = LCCBlocks.fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.fortstone)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone, 2)) }
    val cobbled_fortstone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.cobbled_fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.cobbled_fortstone)).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone, name = LCC.id("fortstone_stairs_from_fortstone_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cobbled_fortstone)) }
    val cobbled_fortstone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.cobbled_fortstone.identifierLoc(), full = LCCBlocks.cobbled_fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.cobbled_fortstone)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone, 2, name = LCC.id("fortstone_slabs_from_fortstone_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cobbled_fortstone, 2)) }

    val deadwood_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.deadwood_log.identifierLoc(), textureEnd = LCCBlocks.deadwood_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCTags.deadwood_logs)).add(ItemTagFactory(LCCTags.deadwood_logs_i)).add(ItemTagFactory(LCCTags.deadwood_tree)) }
    val deadwood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.deadwood_log.identifierLoc(), textureEnd = LCCBlocks.deadwood_log.identifierLoc())).add(BlockTagFactory(LCCTags.deadwood_logs)).add(ItemTagFactory(LCCTags.deadwood_logs_i)).add(BarkRecipeFactory(LCCBlocks.deadwood_log)).add(ItemTagFactory(LCCTags.deadwood_tree)) }
    val sappy_stripped_deadwood_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SapBurstBlockAssetFactory(texture = LCCBlocks.stripped_deadwood_log.identifierLoc())).add(SimpleBlockLootFactory(LCCBlocks.stripped_deadwood_log)).add(BlockTagFactory(LCCTags.deadwood_logs)).add(ItemTagFactory(LCCTags.deadwood_logs_i)).add(ItemTagFactory(LCCTags.deadwood_tree)) }
    val stripped_deadwood_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_deadwood_log.identifierLoc(), textureEnd = LCCBlocks.stripped_deadwood_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCTags.deadwood_logs)).add(ItemTagFactory(LCCTags.deadwood_logs_i)).add(ItemTagFactory(LCCTags.deadwood_tree)) }
    val stripped_deadwood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_deadwood_log.identifierLoc(), textureEnd = LCCBlocks.stripped_deadwood_log.identifierLoc())).add(BlockTagFactory(LCCTags.deadwood_logs)).add(ItemTagFactory(LCCTags.deadwood_logs_i)).add(BarkRecipeFactory(LCCBlocks.stripped_deadwood_log)).add(ItemTagFactory(LCCTags.deadwood_tree)) }
    val deadwood_planks by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().add(BlockTagFactory(BlockTags.PLANKS)).add(ItemTagFactory(ItemTags.PLANKS)).add(SingleShapelessFromTagRecipeFactory(LCCTags.deadwood_logs_i, "has_deadwood_logs" to InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCTags.deadwood_logs_i).build()), outputCount = 4, group = "planks")) }
    val deadwood_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.WOODEN_STAIRS)).add(StairsRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_stairs")) }
    val deadwood_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc(), full = LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.WOODEN_SLABS)).add(SlabRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_slab")).add(SlabLootFactory) }
    val deadwood_door by entry(::initialiser) { data().defaultLang().add(DoorBlockAssetFactory).add(GeneratedItemAssetFactory).add(DoorBlockLootFactory).add(BlockTagFactory(BlockTags.WOODEN_DOORS)).add(ItemTagFactory(ItemTags.WOODEN_DOORS)).add(DoorRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_door")) }
    val deadwood_pressure_plate by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().add(PressurePlateBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_PRESSURE_PLATES)).add(ItemTagFactory(ItemTags.WOODEN_PRESSURE_PLATES)).add(PressurePlateRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_pressure_plate")) }
    val deadwood_button by entry(::initialiser) { data().defaultLang().defaultLootTable().add(CustomItemAssetFactory { d, t, i -> Models.BUTTON_INVENTORY.upload(i(t) ?: idi.loc(t), Texture.texture(LCCBlocks.deadwood_planks.identifierLoc()), d.modelStates::addModel) }).add(ButtonBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_BUTTONS)).add(ItemTagFactory(ItemTags.WOODEN_BUTTONS)).add(SingleShapelessFromItemRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_button")) }
    val deadwood_fence by entry(::initialiser) { data().defaultLang().defaultLootTable().add(CustomItemAssetFactory { d, t, i -> Models.FENCE_INVENTORY.upload(i(t) ?: idi.loc(t), Texture.texture(LCCBlocks.deadwood_planks.identifierLoc()), d.modelStates::addModel) }).add(FenceBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_FENCES)).add(ItemTagFactory(ItemTags.WOODEN_FENCES)).add(FenceRecipeFactory(LCCBlocks.deadwood_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence")) }
    val deadwood_fence_gate by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(FenceGateBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.FENCE_GATES)).add(FenceGateRecipeFactory(LCCBlocks.deadwood_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence_gate")) }
    val deadwood_trapdoor by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().add(TrapdoorBlockAssetFactory).add(BlockTagFactory(BlockTags.WOODEN_TRAPDOORS)).add(ItemTagFactory(ItemTags.WOODEN_TRAPDOORS)).add(TrapdoorRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_trapdoor")) }
    val deadwood_sign by entry(::initialiser) { data().defaultLang().defaultLootTable().add(ParticleBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(GeneratedItemAssetFactory).add(BlockTagFactory(BlockTags.STANDING_SIGNS)).add(ItemTagFactory(ItemTags.SIGNS)).add(SignRecipeFactory(LCCBlocks.deadwood_planks, Ingredient.ofItems(Items.STICK), group = "wooden_sign")) }
    val deadwood_wall_sign by entry(::initialiser) { data().add(ParticleBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WALL_SIGNS)) }

    fun initialiser(input: BlockDataContainer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out BlockDataContainer, out BlockDataContainer>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.init(it.name, LCCBlocks.getOrNull(it.name)) }

        val missing = LCCBlocks.all.values.minus(initialised.flatMap { it.entry.affects })
        missing.forEach { val key = LCCBlocks[it].name; defaults().init(key, it) }
    }

    private fun data() = BlockDataContainer(LCCData)
    private fun defaults() = data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable()

    private fun BlockDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun BlockDataContainer.defaultBlockAsset() = add(SimpleBlockAssetFactory)
    private fun BlockDataContainer.defaultItemAsset() = add(ParentBlockItemAssetFactory)
    private fun BlockDataContainer.defaultLootTable() = add(SelfBlockLootFactory)

}