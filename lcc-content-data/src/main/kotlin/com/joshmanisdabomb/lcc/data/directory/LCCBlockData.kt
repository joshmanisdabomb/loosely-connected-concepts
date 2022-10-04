package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
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
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.tags.LCCBlockTags
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.recipe.refining.RefiningSimpleRecipe
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.block.Blocks
import net.minecraft.data.client.Models
import net.minecraft.data.client.TextureKey
import net.minecraft.data.client.TextureMap
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
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

    val ruby_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineablePickaxe().withIronTool().add(OreBlockLootFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_ORE)) }
    val ruby_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withIronTool().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_BLOCK)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val topaz_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.topaz_shard, from = false)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val budding_topaz by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().mineablePickaxe().add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val topaz_clusters by entry(::initialiser) { data().affects(LCCBlocks.budding_topaz.crystals.toList()).defaultLang().mineablePickaxe().add(ClusterBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(ClusterBlockLootFactory(LCCItems.topaz_shard)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val sapphire_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineablePickaxe().withIronTool().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.sapphire)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val uranium_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineablePickaxe().withDiamondTool().add(OreBlockLootFactory(LCCItems.uranium)).add(BlockTagFactory(LCCBlockTags.radioactive)) }
    val deepslate_uranium_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineablePickaxe().withDiamondTool().add(OreBlockLootFactory(LCCItems.uranium)).add(BlockTagFactory(LCCBlockTags.radioactive)) }
    val uranium_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withDiamondTool().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.uranium)).add(BlockTagFactory(LCCBlockTags.radioactive)) }
    val enriched_uranium_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withDiamondTool().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.enriched_uranium)).add(ItemTagFactory(LCCItemTags.enriched_uranium)).add(BlockTagFactory(LCCBlockTags.radioactive)) }
    val heavy_uranium_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withDiamondTool().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.heavy_uranium)).add(BlockTagFactory(LCCBlockTags.radioactive)) }

    val tungsten_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineablePickaxe().withStoneTool().add(GenerousOreBlockLootFactory(LCCItems.raw_tungsten, UniformLootNumberProvider.create(2.0F, 3.0F))) }
    val deepslate_tungsten_ore by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineablePickaxe().withStoneTool().add(GenerousOreBlockLootFactory(LCCItems.raw_tungsten, UniformLootNumberProvider.create(2.0F, 3.0F))) }
    val raw_tungsten_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.raw_tungsten)) }
    val tungsten_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.tungsten_ingot)) }
    val cut_tungsten by entry(::initialiser) {
        data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 4)
                .pattern("tt")
                .pattern("tt")
                .input('t', LCCBlocks.tungsten_block)
                .apply { hasCriterionShaped(this, LCCBlocks.tungsten_block) }
                .apply { offerShaped(this, d) }
        }).add(StonecutterItemRecipeFactory(LCCBlocks.tungsten_block))
    }
    val cut_tungsten_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(StairsBlockAssetFactory(LCCBlocks.cut_tungsten.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.cut_tungsten)).add(StonecutterItemRecipeFactory(LCCBlocks.tungsten_block, name = LCC.id("cut_tungsten_stairs_from_tungsten_block_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cut_tungsten)) }
    val cut_tungsten_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().withStoneTool().add(SlabBlockAssetFactory(LCCBlocks.cut_tungsten.identifierLoc(), full = LCCBlocks.cut_tungsten.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.cut_tungsten)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.tungsten_block, 2, name = LCC.id("cut_tungsten_slab_from_tungsten_block_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cut_tungsten, 2)) }

    val cracked_mud by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(RotationBlockAssetFactory).add(BlockTagFactory(LCCBlockTags.wasteland_effective)).add(BlockTagFactory(BlockTags.ENDERMAN_HOLDABLE)).add(BlockTagFactory(LCCBlockTags.wasteland_effective)).add(SmeltFromItemRecipeFactory(Blocks.MUD, RecipeSerializer.SMELTING, experience = 0.1f)) }
    val nuclear_waste by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RotationBlockAssetFactory).add(BlockTagFactory(LCCBlockTags.radioactive)) }

    val oil by entry(::initialiser) { data().defaultLang().add(ParticleBlockAssetFactory(LCC.block("oil_still"))) }
    val asphalt by entry(::initialiser) { data().defaultLang().add(ParticleBlockAssetFactory(LCC.block("asphalt_still"))) }
    val road by entry(::initialiser) { data().defaultLang().mineablePickaxe().add(RoadBlockAssetFactory) } //TODO maybe drop something

    val pumice by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(RotationBlockAssetFactory(x = (0..3).toList(), y = (0..3).toList())) }
    val rhyolite by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(MirroredBlockAssetFactory) }

    val soaking_soul_sand by entry(::initialiser) {
        data().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().mineableShovel().add(CustomRecipeFactory { d, i ->
            ShapelessRecipeJsonBuilder.create(i, 8)
                .input(Blocks.WET_SPONGE, 4)
                .input(Blocks.SOUL_SAND, 4)
                .apply { hasCriterionShapeless(this, Blocks.SPONGE) }
                .apply { offerShapeless(this, d) }
        })
    }
    val bounce_pad by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().withStoneTool().add(BouncePadBlockAssetFactory).add(BouncePadItemAssetFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 2)
                .pattern("ipi")
                .pattern("rsr")
                .input('r', Items.REPEATER)
                .input('i', Items.IRON_INGOT)
                .input('p', LCCBlocks.rubber_piston)
                .input('s', LCCBlocks.soaking_soul_sand)
                .apply { hasCriterionShaped(this, LCCBlocks.soaking_soul_sand) }
                .apply { offerShaped(this, d) }
        })
    }

    val time_rift by entry(::initialiser) {
        data().defaultLang().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")).add(ParticleBlockAssetFactory).add(DynamicItemAssetFactory(DynamicItemAssetFactory.block)).add(SilkBlockLootFactory(LCCItems.simulation_fabric)).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i)
                .pattern("cdc")
                .pattern("dsd")
                .pattern("cdc")
                .input('c', Items.CLOCK)
                .input('d', Blocks.ANCIENT_DEBRIS)
                .input('s', LCCItems.simulation_fabric)
                .apply { hasCriterionShaped(this, LCCItems.simulation_fabric) }
                .apply { offerShaped(this, d) }
        })
    }
    val spawner_table by entry(::initialiser) {
        data().defaultItemAsset().mineablePickaxe().withDiamondTool().add(LiteralTranslationFactory("Arcane Table")).add(DungeonTableBlockAssetFactory).add(DungeonTableBlockLootFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i)
                .pattern("rgp")
                .pattern("ooo")
                .input('r', Items.REDSTONE)
                .input('g', Items.GLOWSTONE_DUST)
                .input('p', Items.GUNPOWDER)
                .input('o', Blocks.OBSIDIAN)
                .apply { hasCriterionShaped(this, Items.GLOWSTONE_DUST) }
                .apply { offerShaped(this, d) }
        }).add(BlockTagFactory(BlockTags.DRAGON_IMMUNE))
    }

    val classic_grass_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineableShovel().add(ClassicGrassBlockAssetFactory).add(SilkBlockLootFactory(Blocks.DIRT)).add(RiftFromItemRecipeFactory(Blocks.GRASS_BLOCK)) }
    val classic_cobblestone by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE)).add(BlockTagFactory(LCCBlockTags.nether_reactor_shell)) }
    val classic_planks by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineableAxe().add(RiftFromTagRecipeFactory(ItemTags.PLANKS)) }
    val classic_leaves by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineableHoe().add(LeavesBlockLootFactory(LCCBlocks.classic_sapling)).add(RiftFromTagRecipeFactory(ItemTags.LEAVES)).add(BlockTagFactory(BlockTags.LEAVES)) }
    val classic_sapling by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromTagRecipeFactory(ItemTags.SAPLINGS)).add(BlockTagFactory(BlockTags.SAPLINGS)) }
    val potted_classic_sapling by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_rose by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.POPPY)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_classic_rose by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val cyan_flower by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.BLUE_ORCHID)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_cyan_flower by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_gravel by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineableShovel().add(ChanceAlternativeBlockLootFactory(Items.FLINT, 0.1f, 0.14285715f, 0.25f, 1.0f)).add(RiftFromItemRecipeFactory(Blocks.GRAVEL)) }
    val classic_sponge by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineableHoe().add(RiftFromItemRecipeFactory(Blocks.SPONGE)) }
    val classic_glass by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().mineablePickaxe().add(SilkBlockLootFactory).add(RiftFromItemRecipeFactory(Blocks.GLASS)).add(BlockTagFactory(BlockTags.IMPERMEABLE)) }
    val classic_bricks by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().add(RiftFromItemRecipeFactory(Blocks.BRICKS)) }
    val classic_mossy_cobblestone by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE)) }
    val classic_iron_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.IRON_BLOCK)) }
    val classic_gold_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withIronTool().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.GOLD_BLOCK)).add(BlockTagFactory(LCCBlockTags.nether_reactor_base)).add(ItemTagFactory(LCCItemTags.gold_blocks)) }
    val classic_diamond_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withIronTool().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.DIAMOND_BLOCK)) }
    val alpha_iron_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val alpha_gold_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().mineablePickaxe().withIronTool().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }).add(BlockTagFactory(LCCBlockTags.nether_reactor_base)).add(ItemTagFactory(LCCItemTags.gold_blocks)) }
    val alpha_diamond_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().mineablePickaxe().withIronTool().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val classic_tnt by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace("Tnt", "TNT") }).add(RiftFromItemRecipeFactory(Blocks.TNT)) }
    val pocket_stonecutter by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StonecutterBlockAssetFactory).add(RiftFromItemRecipeFactory(Blocks.STONECUTTER)) }
    val classic_chest by entry(::initialiser) { data().defaultLang().defaultLootTable().mineableAxe().add(ClassicChestBlockAssetFactory).add(CustomItemAssetFactory(mi.orientable({ idi.loc(it, folder = "block") }))).add(RiftFromItemRecipeFactory(Blocks.CHEST)) }
    val nether_reactor by entry(::initialiser) { data().defaultLang().mineablePickaxe().add(NetherReactorBlockAssetFactory).add(ParentBlockItemAssetFactory(LCCBlocks.nether_reactor.identifierLocSuffix("ready"))).add(NetherReactorBlockLootFactory).add(RiftFromItemRecipeFactory(Items.NETHER_STAR)) }
    val classic_crying_obsidian by entry(::initialiser) { data().defaultLang().defaultLootTable().mineablePickaxe().withDiamondTool().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory(mi.cubeAll { idi.locSuffix(it, "static", folder = "block") })).add(RiftFromItemRecipeFactory(Blocks.CRYING_OBSIDIAN)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val cog by entry(::initialiser) { data().defaultLang().defaultLootTable().add(GeneratedBlockItemAssetFactory).add(CogBlockAssetFactory).add(RiftFromItemRecipeFactory(Items.REDSTONE)) }
    val glowing_obsidian by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineablePickaxe().withDiamondTool() }

    val classic_cloth by entry(::initialiser) { data().affects(LCCBlocks.classic_cloth.values.toList()).defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineableShears() }
    val red_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.RED_WOOL)) }
    val orange_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.ORANGE_WOOL)) }
    val yellow_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.YELLOW_WOOL)) }
    val lime_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.LIME_WOOL)) }
    val light_blue_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.LIGHT_BLUE_WOOL)) }
    val purple_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.PURPLE_WOOL)) }
    val magenta_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.MAGENTA_WOOL)) }
    val gray_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.GRAY_WOOL)) }
    val light_gray_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.LIGHT_GRAY_WOOL)) }
    val white_classic_cloth by entry(::initialiser) { data().add(RiftFromItemRecipeFactory(Blocks.WHITE_WOOL)) }

    val machine_enclosure by entry(::initialiser) {
        data().defaultLang().defaultLootTable().defaultItemAsset().mineablePickaxe().withStoneTool().add(SideBottomTopBlockAssetFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
                .pattern("ccc")
                .pattern("cpc")
                .pattern("iii")
                .input('c', Items.COPPER_INGOT)
                .input('p', LCCBlocks.power_cable)
                .input('i', Items.IRON_INGOT)
                .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
                .apply { offerShaped(this, d) }
        })
    }

    val refiner by entry(::initialiser) {
        data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(RefiningBlockAssetFactory).add(ConcreteRefiningRecipeFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
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
        })
    }
    val power_cable by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().withStoneTool().add(Cable4BlockAssetFactory).add(Cable4ItemAssetFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 3)
                .pattern("ccc")
                .input('c', Items.COPPER_INGOT)
                .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
                .apply { offerShaped(this, d) }
        })
    }

    val coal_generator by entry(::initialiser) {
        data().defaultLootTable().defaultItemAsset().mineablePickaxe().withStoneTool().add(LiteralTranslationFactory("Furnace Generator", "en_us")).add(FiredGeneratorBlockAssetFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
                .pattern("e")
                .pattern("m")
                .pattern("f")
                .input('e', Items.IRON_BARS)
                .input('m', LCCBlocks.machine_enclosure)
                .input('f', Blocks.FURNACE)
                .apply { hasCriterionShaped(this, LCCBlocks.machine_enclosure) }
                .apply { offerShaped(this, d) }
        }).add(ItemTagFactory(LCCItemTags.generators))
    }
    val oil_generator by entry(::initialiser) {
        data().defaultLootTable().defaultItemAsset().mineablePickaxe().withStoneTool().add(LiteralTranslationFactory("Combustion Generator", "en_us")).add(FiredGeneratorBlockAssetFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
                .pattern("e")
                .pattern("m")
                .pattern("f")
                .input('e', Items.IRON_BARS)
                .input('m', LCCBlocks.machine_enclosure)
                .input('f', Items.FLINT_AND_STEEL)
                .apply { hasCriterionShaped(this, LCCBlocks.machine_enclosure) }
                .apply { offerShaped(this, d) }
        }).add(ItemTagFactory(LCCItemTags.generators))
    }

    val solar_panel by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().withStoneTool().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.template_solar_panel.upload(i(t) ?: idi.loc(t), TextureMap().put(TextureKey.TOP, idi.loc(t, folder = "block")).put(TextureKey.SIDE, idi.locSuffix(t, "side", folder = "block")).put(TextureKey.BOTTOM, idi.locSuffix(t, "bottom", folder = "block")), d.models) }).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i)
                .pattern("sss")
                .pattern("lll")
                .pattern("ici")
                .input('s', LCCItems.silicon)
                .input('l', Items.LAPIS_LAZULI)
                .input('i', Items.IRON_INGOT)
                .input('c', Items.COPPER_INGOT)
                .apply { hasCriterionShaped(this, LCCItems.silicon) }
                .apply { offerShaped(this, d) }
        })
    }

    val turbine by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().withStoneTool().add(LiteralTranslationFactory("Steam Turbine", "en_us")).add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.template_solar_panel.upload(i(t) ?: idi.loc(t), TextureMap().put(TextureKey.TOP, idi.loc(t, folder = "block")).put(TextureKey.SIDE, LCCBlocks.solar_panel.identifierLocSuffix("side", folder = "block")).put(TextureKey.BOTTOM, LCCBlocks.solar_panel.identifierLocSuffix("bottom", folder = "block")), d.models) }).add(TurbineBlockAssetFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i)
                .pattern(" t ")
                .pattern("ici")
                .input('t', LCCItems.turbine_blades)
                .input('i', Items.IRON_INGOT)
                .input('c', Items.COPPER_INGOT)
                .apply { hasCriterionShaped(this, LCCItems.turbine_blades) }
                .apply { offerShaped(this, d) }
        })
    }

    val energy_bank by entry(::initialiser) {
        data().defaultLang().defaultLootTable().defaultItemAsset().mineablePickaxe().withStoneTool().add(DirectionalBlockAssetFactory(model = mb.cubeBottomTop({ LCCBlocks.machine_enclosure.identifierLoc() }, { idb.loc(it) }))).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
                .pattern("bib")
                .pattern("bmb")
                .pattern("bib")
                .input('m', LCCBlocks.machine_enclosure)
                .input('i', Items.IRON_INGOT)
                .input('b', LCCItems.redstone_battery)
                .apply { hasCriterionShaped(this, LCCItems.redstone_battery) }
                .apply { offerShaped(this, d) }
        })
    }

    val nuclear_fire by entry(::initialiser) { data().defaultLang().add(StaticFireBlockAssetFactory).add(BlockTagFactory(LCCBlockTags.radioactive)) }
    val atomic_bomb by entry(::initialiser) {
        data().defaultLang().mineablePickaxe().withStoneTool().add(AtomicBombBlockAssetFactory).add(AtomicBombBlockLootFactory).add(CustomItemAssetFactory { d, t, i ->
            LCCModelTemplates.template_atomic_bomb_item.upload(idi.loc(t), TextureMap()
                .put(LCCModelTextureKeys.t1, idi.locSuffix(t, "tail_side", folder = "block"))
                .put(LCCModelTextureKeys.t2, idi.locSuffix(t, "tail", folder = "block"))
                .put(LCCModelTextureKeys.t3, idi.locSuffix(t, "fin", folder = "block"))
                .put(LCCModelTextureKeys.t4, idi.locSuffix(t, "core", folder = "block"))
                .put(LCCModelTextureKeys.t5, idi.locSuffix(t, "head", folder = "block")), d.models)
        })
            .add(CustomRecipeFactory { d, i ->
                ShapedRecipeJsonBuilder(i, 1)
                    .pattern("ccc")
                    .pattern("bdc")
                    .pattern("ccc")
                    .input('c', Blocks.IRON_BLOCK)
                    .input('b', ItemTags.BUTTONS)
                    .input('d', Blocks.DISPENSER)
                    .criterion("has_enriched_uranium", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.enriched_uranium).build()))
                    .apply { offerShaped(this, d) }
            })
    }

    val natural_rubber_log by entry(::initialiser) { data().defaultLang().mineableAxe().add(CustomItemAssetFactory(mi.orientableBottom({ LCCBlocks.rubber_log.identifierLoc() }, textureFront = { LCCBlocks.rubber_log.identifierLocSuffix("tapped") }, textureSide = { LCCBlocks.rubber_log.identifierLoc() }, textureBottom = { LCCBlocks.rubber_log.identifierLocSuffix("top") }))).add(BooleanHorizontalSideBlockAssetFactory(LCCBlocks.rubber_log.identifierLocSuffix("tapped"), LCCBlocks.rubber_log.identifierLoc(), LCCBlocks.rubber_log.identifierLocSuffix("top"))).add(SimpleBlockLootFactory(LCCBlocks.rubber_log)).add(BlockTagFactory(LCCBlockTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val rubber_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.rubber_log.identifierLoc(), textureEnd = LCCBlocks.rubber_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCBlockTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val rubber_wood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.rubber_log.identifierLoc(), textureEnd = LCCBlocks.rubber_log.identifierLoc())).add(BlockTagFactory(LCCBlockTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_logs)).add(BarkRecipeFactory(LCCBlocks.rubber_log)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val sappy_stripped_rubber_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineableAxe().add(SapBurstBlockAssetFactory(texture = LCCBlocks.stripped_rubber_log.identifierLoc())).add(SimpleBlockLootFactory(LCCBlocks.stripped_rubber_log)).add(BlockTagFactory(LCCBlockTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val stripped_rubber_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_rubber_log.identifierLoc(), textureEnd = LCCBlocks.stripped_rubber_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCBlockTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val stripped_rubber_wood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_rubber_log.identifierLoc(), textureEnd = LCCBlocks.stripped_rubber_log.identifierLoc())).add(BlockTagFactory(LCCBlockTags.rubber_logs)).add(ItemTagFactory(LCCItemTags.rubber_logs)).add(BarkRecipeFactory(LCCBlocks.stripped_rubber_log)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val rubber_planks by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().mineableAxe().add(BlockTagFactory(BlockTags.PLANKS)).add(ItemTagFactory(ItemTags.PLANKS)).add(SingleShapelessFromTagRecipeFactory(LCCItemTags.rubber_logs, "has_rubber_logs" to InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.rubber_logs).build()), outputCount = 4, group = "planks")) }
    val rubber_sapling by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(BlockTagFactory(BlockTags.SAPLINGS)).add(ItemTagFactory(ItemTags.SAPLINGS)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val potted_rubber_sapling by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val rubber_leaves by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineableHoe().add(LeavesBlockLootFactory(LCCBlocks.rubber_sapling, saplingChance = floatArrayOf(0.067F, 0.09F, 0.12F, 0.15F))).add(BlockTagFactory(BlockTags.LEAVES)).add(TintBlockAssetFactory).add(BlockTagFactory(BlockTags.LEAVES)).add(ItemTagFactory(ItemTags.LEAVES)).add(ItemTagFactory(LCCItemTags.rubber_tree)) }
    val rubber_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(StairsBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.WOODEN_STAIRS)).add(StairsRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_stairs")) }
    val rubber_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineableAxe().add(SlabBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc(), full = LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.WOODEN_SLABS)).add(SlabRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_slab")).add(SlabLootFactory) }
    val rubber_door by entry(::initialiser) { data().defaultLang().mineableAxe().add(DoorBlockAssetFactory).add(GeneratedItemAssetFactory).add(DoorBlockLootFactory).add(BlockTagFactory(BlockTags.WOODEN_DOORS)).add(ItemTagFactory(ItemTags.WOODEN_DOORS)).add(DoorRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_door")) }
    val rubber_pressure_plate by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(PressurePlateBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_PRESSURE_PLATES)).add(ItemTagFactory(ItemTags.WOODEN_PRESSURE_PLATES)).add(PressurePlateRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_pressure_plate")) }
    val rubber_button by entry(::initialiser) { data().defaultLang().defaultLootTable().mineableAxe().add(CustomItemAssetFactory { d, t, i -> Models.BUTTON_INVENTORY.upload(i(t) ?: idi.loc(t), TextureMap.texture(LCCBlocks.rubber_planks.identifierLoc()), d.models) }).add(ButtonBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_BUTTONS)).add(ItemTagFactory(ItemTags.WOODEN_BUTTONS)).add(SingleShapelessFromItemRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_button")) }
    val rubber_fence by entry(::initialiser) { data().defaultLang().defaultLootTable().mineableAxe().add(CustomItemAssetFactory { d, t, i -> Models.FENCE_INVENTORY.upload(i(t) ?: idi.loc(t), TextureMap.texture(LCCBlocks.rubber_planks.identifierLoc()), d.models) }).add(FenceBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_FENCES)).add(ItemTagFactory(ItemTags.WOODEN_FENCES)).add(FenceRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence")) }
    val rubber_fence_gate by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().mineableAxe().add(FenceGateBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.FENCE_GATES)).add(FenceGateRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence_gate")) }
    val rubber_trapdoor by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().mineableAxe().add(TrapdoorBlockAssetFactory).add(BlockTagFactory(BlockTags.WOODEN_TRAPDOORS)).add(ItemTagFactory(ItemTags.WOODEN_TRAPDOORS)).add(TrapdoorRecipeFactory(LCCBlocks.rubber_planks, group = "wooden_trapdoor")) }
    val rubber_sign by entry(::initialiser) { data().defaultLang().defaultLootTable().mineableAxe().add(ParticleBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(GeneratedItemAssetFactory).add(BlockTagFactory(BlockTags.STANDING_SIGNS)).add(ItemTagFactory(ItemTags.SIGNS)).add(SignRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK), group = "wooden_sign")) }
    val rubber_wall_sign by entry(::initialiser) { data().mineableAxe().add(ParticleBlockAssetFactory(LCCBlocks.rubber_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WALL_SIGNS)) }
    val treetap_bowl by entry(::initialiser) { data().defaultLang().mineablePickaxe().withStoneTool().add(TreetapStorageBlockAssetFactory(AbstractTreetapBlock.TreetapContainer.BOWL, LCCModelTemplates.template_treetap_bowl_1, LCCModelTemplates.template_treetap_bowl_2, LCCModelTemplates.template_treetap_bowl_3, LCCModelTemplates.template_treetap_bowl_dried) { d, t, i -> LCCModelTemplates.template_treetap_bowl.upload(i(t) ?: idb.loc(t), TextureMap().put(LCCModelTextureKeys.t0, LCCBlocks.treetap.identifierLoc()).put(LCCModelTextureKeys.t1, idb.loc(t)), d.models) }).add(TreetapContainerBlockLootFactory(LCCBlocks.treetap, Items.BOWL)) }
    val treetap by entry(::initialiser) {
        data().defaultLang().defaultItemAsset().mineablePickaxe().withStoneTool().add(TreetapBlockAssetFactory).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 2)
                .pattern("i  ")
                .pattern("iii")
                .input('i', Items.IRON_INGOT)
                .criterion("has_rubber_tree", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.rubber_tree).build()))
                .apply { offerShaped(this, d) }
        }).add(TreetapBlockLootFactory<TreetapBlock.TreetapState, TreetapBlock.TreetapState>(LCCBlocks.treetap, TreetapBlock.tap, { it.container?.item }))
    }
    val dried_treetap by entry(::initialiser) { data().defaultLang().mineablePickaxe().withStoneTool().add(TreetapDriedBlockAssetFactory).add(TreetapBlockLootFactory(LCCBlocks.treetap, DriedTreetapBlock.container, AbstractTreetapBlock.TreetapContainer::item, DriedTreetapBlock.liquid, { it.dryProduct?.item })) }
    val oxygen_extractor by entry(::initialiser) {
        data().defaultLang().defaultLootTable().defaultItemAsset().mineablePickaxe().withStoneTool().add(CustomBlockAssetFactory(mb.cubeBottomTop(textureBottom = { LCCBlocks.machine_enclosure.identifierLocSuffix("bottom") }))).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
                .pattern("i i")
                .pattern("i i")
                .pattern("bmb")
                .input('m', LCCBlocks.machine_enclosure)
                .input('i', Items.IRON_INGOT)
                .input('b', Items.BUCKET)
                .apply { hasCriterionShaped(this, LCCItems.oxygen_tank) }
                .apply { offerShaped(this, d) }
        })
    }

    val kiln by entry(::initialiser) {
        data().defaultLang().defaultLootTable().defaultItemAsset().mineablePickaxe().add(FurnaceBlockAssetFactory(textureTop = LCCBlocks.kiln.identifierLocSuffix("side"), textureBottom = LCCBlocks.kiln.identifierLocSuffix("side"))).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
                .pattern("bbb")
                .pattern("bfb")
                .pattern("bbb")
                .input('b', Blocks.BRICKS)
                .input('f', Blocks.FURNACE)
                .apply { hasCriterionShaped(this, Blocks.BRICKS) }
                .apply { offerShaped(this, d) }
        })
    }

    val dirt_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineableShovel().add(SlabBlockAssetFactory(Identifier("minecraft:block/dirt"), full = Identifier("minecraft:block/dirt"))).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(RiftFromItemRecipeFactory(Blocks.DIRT, 2)).add(SlabLootFactory) }
    val classic_wooden_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(StairsBlockAssetFactory(LCCBlocks.classic_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_planks)).add(RiftFromTagRecipeFactory(ItemTags.WOODEN_STAIRS)) }
    val classic_wooden_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineableAxe().add(SlabBlockAssetFactory(LCCBlocks.classic_planks.identifierLoc(), full = LCCBlocks.classic_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_planks)).add(RiftFromTagRecipeFactory(ItemTags.WOODEN_SLABS)).add(SlabLootFactory) }
    val classic_cobblestone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StairsBlockAssetFactory(LCCBlocks.classic_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE_STAIRS)).add(StonecutterItemRecipeFactory(LCCBlocks.classic_cobblestone)) }
    val classic_cobblestone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().add(SlabBlockAssetFactory(LCCBlocks.classic_cobblestone.identifierLoc(), full = LCCBlocks.classic_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE_SLAB)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.classic_cobblestone, 2)) }
    val classic_brick_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StairsBlockAssetFactory(LCCBlocks.classic_bricks.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_bricks)).add(RiftFromItemRecipeFactory(Blocks.BRICK_STAIRS)).add(StonecutterItemRecipeFactory(LCCBlocks.classic_bricks)) }
    val classic_brick_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().add(SlabBlockAssetFactory(LCCBlocks.classic_bricks.identifierLoc(), full = LCCBlocks.classic_bricks.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_bricks)).add(RiftFromItemRecipeFactory(Blocks.BRICK_SLAB)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.classic_bricks, 2)) }
    val classic_mossy_cobblestone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StairsBlockAssetFactory(LCCBlocks.classic_mossy_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_mossy_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE_STAIRS)).add(StonecutterItemRecipeFactory(LCCBlocks.classic_mossy_cobblestone)) }
    val classic_mossy_cobblestone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().add(SlabBlockAssetFactory(LCCBlocks.classic_mossy_cobblestone.identifierLoc(), full = LCCBlocks.classic_mossy_cobblestone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_mossy_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE_SLAB)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.classic_mossy_cobblestone, 2)) }
    val rhyolite_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StairsBlockAssetFactory(LCCBlocks.rhyolite.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.rhyolite)).add(StonecutterItemRecipeFactory(LCCBlocks.rhyolite)) }
    val rhyolite_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().add(SlabBlockAssetFactory(LCCBlocks.rhyolite.identifierLoc(), full = LCCBlocks.rhyolite.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.rhyolite)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.rhyolite, 2)) }

    val heavy_uranium_shielding by entry(::initialiser) {
        data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().mineablePickaxe().withDiamondTool().add(CustomRecipeFactory { d, i ->
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
        })
    }
    val nuclear_generator by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().withStoneTool().add(NuclearFiredGeneratorBlockAssetFactory).add(CustomItemAssetFactory { d, t, i ->
            LCCModelTemplates.template_nuclear_generator_item.upload(idi.loc(t), TextureMap()
                .put(LCCModelTextureKeys.t0, LCC.block("generator"))
                .put(LCCModelTextureKeys.t1, idi.locSuffix(t, "bottom_side", folder = "block"))
                .put(LCCModelTextureKeys.t2, idi.locSuffix(t, "top_side", folder = "block"))
                .put(LCCModelTextureKeys.t3, LCCBlocks.machine_enclosure.asItem().identifierLocSuffix("bottom", folder = "block"))
                .put(LCCModelTextureKeys.t4, LCCBlocks.machine_enclosure.asItem().identifierLocSuffix("top", folder = "block"))
                .put(LCCModelTextureKeys.t5, idi.locSuffix(t, "redstone", folder = "block"))
                .put(LCCModelTextureKeys.t6, idi.locSuffix(t, "bottom", folder = "block"))
                .put(LCCModelTextureKeys.t7, idi.locSuffix(t, "level", folder = "block"))
                .put(LCCModelTextureKeys.t8, idi.locSuffix(t, "level", folder = "block"))
                .put(LCCModelTextureKeys.t9, LCCBlocks.heavy_uranium_shielding.identifierLoc(folder = "block")), d.models)
        }
        ).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i)
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
        })
    }
    val failing_nuclear_generator by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().withStoneTool().add(ExplodingNuclearFiredGeneratorBlockAssetFactory).add(BlockTagFactory(LCCBlockTags.temperature_nuclear)) }

    val rock_salt by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().mineablePickaxe().add(RotationBlockAssetFactory((0..3).toList(), (0..3).toList(), mb.cubeAll())) }
    val scattered_salt by entry(::initialiser) {
        data().defaultLang().add(SaltBlockLootFactory).add(SaltBlockAssetFactory).add(CustomItemAssetFactory { d, t, i ->
            LCCModelTemplates.template_salt_item.upload(i(t) ?: idi.loc(t), TextureMap.texture(LCC.block("salt")), d.models)
        })
    }
    val salt_block by entry(::initialiser) {
        data().defaultLang().defaultLootTable().defaultItemAsset().mineableShovel().add(RotationBlockAssetFactory((0..3).toList(), (0..3).toList(), mb.cubeAll { LCC.block("salt") })).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 1)
                .pattern("sss")
                .pattern("sss")
                .pattern("sss")
                .input('s', LCCItems.salt)
                .apply { hasCriterionShaped(this, LCCItems.salt) }
                .apply { offerShaped(this, d) }
        })
    }
    val alarm by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.template_alarm_item.upload(i(t) ?: idi.loc(t), TextureMap.texture(idi.loc(t, folder = "block")), d.models) }).add(HorizontalBlockAssetFactory({ d, t, i -> LCCModelTemplates.template_alarm.upload(i(t) ?: idb.loc(t), TextureMap.texture(idb.loc(t)), d.models) })).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder(i, 2)
                .pattern("bib")
                .pattern("nln")
                .input('b', Blocks.BELL)
                .input('i', Items.IRON_INGOT)
                .input('l', Blocks.LEVER)
                .input('n', Blocks.NOTE_BLOCK)
                .apply { hasCriterionShaped(this, Blocks.BELL) }
                .apply { offerShaped(this, d) }
        })
    }
    val radar by entry(::initialiser) {
        data().defaultLang().defaultLootTable().defaultItemAsset().add(RadarBlockAssetFactory).add(CustomRecipeFactory { d, i ->
            //TODO temp recipe before sculks
            ShapedRecipeJsonBuilder(i, 1)
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
        })
    }
    val rubber_block by entry(::initialiser) {
        data().defaultLang().defaultLootTable().defaultItemAsset().add(MultipleBlockAssetFactory(y = (0..3).toList(), List(5) {
            mb.cubeAll { t -> idb.locSuffix(t, it.plus(1).toString()) }
        })).add(Storage9RecipeFactory(LCCItems.heavy_duty_rubber))
    }

    val deposits by entry(::initialiser) { data().affects(LCCBlocks.all.values.filterIsInstance<DepositBlock>()).defaultLang().defaultItemAsset().mineableShovel().add(DepositBlockAssetFactory).add(DepositBlockLootFactory).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }

    val rubber_piston by entry(::initialiser) {
        data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(PistonBlockAssetFactory).add(CustomItemAssetFactory(mi.cubeBottomTop(texture = { idi.loc(it, folder = "block") }, textureBottom = { Identifier("block/piston_bottom") }))).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 1)
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
            ShapedRecipeJsonBuilder.create(i, 1)
                .pattern("rrr")
                .pattern(" p ")
                .input('r', LCCItems.heavy_duty_rubber)
                .input('p', Blocks.PISTON)
                .apply { hasCriterionShaped(this, LCCItems.heavy_duty_rubber) }
                .apply { offerShaped(this, d, i.identifier.suffix("upgrade")) }
        })
    }
    val rubber_piston_head by entry(::initialiser) { data().mineablePickaxe().add(PistonHeadBlockAssetFactory(idb.locSuffix(LCCBlocks.rubber_piston, "top"))) }

    val cracked_mud_pressure_plate by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(PressurePlateBlockAssetFactory(LCCBlocks.cracked_mud.identifierLoc())).add(BlockTagFactory(BlockTags.PRESSURE_PLATES)).add(PressurePlateRecipeFactory(LCCBlocks.cracked_mud)).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }

    val explosive_paste by entry(::initialiser) {
        data().defaultLang().defaultLootTable().add(GeneratedItemAssetFactory).add(WireBlockAssetFactory(
            { d, t, i -> LCCModelTemplates.template_redstone_dust_dot.upload(i(t) ?: idb.loc(t), TextureMap.texture(i(t) ?: idb.loc(t)).put(TextureKey.PARTICLE, idb.loc(t)).put(LCCModelTextureKeys.overlay, Identifier("block/redstone_dust_overlay")), d.models) },
            { s, l -> ModelProvider.ModelFactory { d, t, i -> s.transform(LCCModelTemplates.template_redstone_dust_side_alt, LCCModelTemplates.template_redstone_dust_side).upload(i(t) ?: idb.loc(t), TextureMap.texture(idb.locSuffix(t, "line".plus(l.toString()))).put(TextureKey.PARTICLE, idb.loc(t)).put(LCCModelTextureKeys.overlay, Identifier("block/redstone_dust_overlay")), d.models) } },
            { d, t, i -> LCCModelTemplates.template_redstone_dust_up.upload(i(t) ?: idb.loc(t), TextureMap.texture(idb.locSuffix(t, "up")).put(TextureKey.PARTICLE, idb.loc(t)).put(LCCModelTextureKeys.overlay, Identifier("block/redstone_dust_overlay")), d.models) },
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
        })
    }

    val rusting_iron_blocks by entry(::initialiser) { data().affects(LCCBlocks.rusted_iron_blocks.values.toList().dropLast(1)).defaultLang().defaultLootTable().defaultItemAsset().defaultBlockAsset().mineablePickaxe().withStoneTool().add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val rusted_iron_block by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().defaultBlockAsset().mineablePickaxe().withStoneTool().add(Storage9RecipeFactory(LCCItems.iron_oxide)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val rusted_iron_bars by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().withStoneTool().add(GeneratedBlockItemAssetFactory).add(BarsBlockAssetFactory(
            { d, t, i -> LCCModelTemplates.template_iron_bars_post.upload(i(t) ?: idb.loc(t), TextureMap().put(LCCModelTextureKeys.bars, idb.loc(t)), d.models) },
            { d, t, i -> LCCModelTemplates.template_iron_bars_post_ends.upload(i(t) ?: idb.loc(t), TextureMap().put(TextureKey.EDGE, idb.loc(t)), d.models) },
            { d, t, i -> LCCModelTemplates.template_iron_bars_cap.upload(i(t) ?: idb.loc(t), TextureMap().put(LCCModelTextureKeys.bars, idb.loc(t)), d.models) },
            { d, t, i -> LCCModelTemplates.template_iron_bars_cap_alt.upload(i(t) ?: idb.loc(t), TextureMap().put(LCCModelTextureKeys.bars, idb.loc(t)), d.models) },
            { d, t, i -> LCCModelTemplates.template_iron_bars_side.upload(i(t) ?: idb.loc(t), TextureMap().put(LCCModelTextureKeys.bars, idb.loc(t)).put(TextureKey.EDGE, idb.loc(t)), d.models) },
            { d, t, i -> LCCModelTemplates.template_iron_bars_side_alt.upload(i(t) ?: idb.loc(t), TextureMap().put(LCCModelTextureKeys.bars, idb.loc(t)).put(TextureKey.EDGE, idb.loc(t)), d.models) }
        )).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 16)
                .pattern("iii")
                .pattern("iii")
                .input('i', LCCItems.iron_oxide)
                .apply { hasCriterionShaped(this, LCCItems.iron_oxide) }
                .apply { offerShaped(this, d) }
        }).add(BlockTagFactory(LCCBlockTags.wasteland_required))
    }

    val improvised_explosive by entry(::initialiser) {
        data().defaultLang().defaultItemAsset().mineablePickaxe().withStoneTool().add(VariantBlockAssetFactory(ImprovisedExplosiveBlock.ie_state, { s ->
            mb.cube(
                textureParticle = { idb.locSuffix(it, "inactive") },
                textureUp = { idb.locSuffix(it, "top") },
                textureDown = { idb.locSuffix(it, "bottom") },
                textureNorth = { idb.locSuffix(it, s.asString()) },
                textureEast = { idb.locSuffix(it, s.asString()) },
                textureSouth = { idb.locSuffix(it, s.asString()) },
                textureWest = { idb.locSuffix(it, s.asString()) }
            )
        })).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 1)
                .pattern("iei")
                .pattern("ltl")
                .pattern("iei")
                .input('i', LCCItems.iron_oxide)
                .input('e', LCCBlocks.explosive_paste)
                .input('l', Blocks.REDSTONE_LAMP)
                .input('t', Blocks.TNT)
                .apply { hasCriterionShaped(this, LCCBlocks.explosive_paste) }
                .apply { offerShaped(this, d) }
        }).add(SalvageBlockLootFactory).add(BlockTagFactory(LCCBlockTags.wasteland_required))
    }

    val fortstone by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().mineablePickaxe().add(SilkBlockLootFactory(LCCBlocks.cobbled_fortstone)).add(SmeltFromItemRecipeFactory(LCCBlocks.cobbled_fortstone, RecipeSerializer.SMELTING, experience = 0.1f)).add(BlockTagFactory(BlockTags.ENDERMAN_HOLDABLE)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val cobbled_fortstone by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().mineablePickaxe().add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val polished_fortstone by entry(::initialiser) {
        data().defaultLang().defaultLootTable().mineablePickaxe().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory(mi.cubeAll { idi.loc(it, folder = "block") })).add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 4)
                .pattern("ff")
                .pattern("ff")
                .input('f', LCCBlocks.fortstone)
                .apply { hasCriterionShaped(this, LCCBlocks.fortstone) }
                .apply { offerShaped(this, d) }
        }).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone)).add(BlockTagFactory(LCCBlockTags.wasteland_required))
    }
    val fortstone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StairsBlockAssetFactory(LCCBlocks.fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.fortstone)).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val fortstone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().add(SlabBlockAssetFactory(LCCBlocks.fortstone.identifierLoc(), full = LCCBlocks.fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.fortstone)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone, 2)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val cobbled_fortstone_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(StairsBlockAssetFactory(LCCBlocks.cobbled_fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.cobbled_fortstone)).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone, name = LCC.id("cobbled_fortstone_stairs_from_fortstone_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cobbled_fortstone)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val cobbled_fortstone_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().add(SlabBlockAssetFactory(LCCBlocks.cobbled_fortstone.identifierLoc(), full = LCCBlocks.cobbled_fortstone.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.cobbled_fortstone)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone, 2, name = LCC.id("cobbled_fortstone_slabs_from_fortstone_stonecutting"))).add(StonecutterItemRecipeFactory(LCCBlocks.cobbled_fortstone, 2)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val fortstone_wall by entry(::initialiser) { data().defaultLang().defaultLootTable().mineablePickaxe().add(WallBlockAssetFactory(LCCBlocks.fortstone.identifierLoc())).add(CustomItemAssetFactory { d, t, i -> Models.WALL_INVENTORY.upload(idi.loc(t), TextureMap().put(TextureKey.WALL, LCCBlocks.fortstone.identifierLoc()), d.models) }).add(BlockTagFactory(BlockTags.WALLS)).add(ItemTagFactory(ItemTags.WALLS)).add(WallRecipeFactory(LCCBlocks.fortstone)).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val cobbled_fortstone_wall by entry(::initialiser) { data().defaultLang().defaultLootTable().mineablePickaxe().add(WallBlockAssetFactory(LCCBlocks.cobbled_fortstone.identifierLoc())).add(CustomItemAssetFactory { d, t, i -> Models.WALL_INVENTORY.upload(idi.loc(t), TextureMap().put(TextureKey.WALL, LCCBlocks.cobbled_fortstone.identifierLoc()), d.models) }).add(BlockTagFactory(BlockTags.WALLS)).add(ItemTagFactory(ItemTags.WALLS)).add(WallRecipeFactory(LCCBlocks.cobbled_fortstone)).add(StonecutterItemRecipeFactory(LCCBlocks.cobbled_fortstone)).add(StonecutterItemRecipeFactory(LCCBlocks.fortstone, name = LCC.id("cobbled_fortstone_wall_from_fortstone_stonecutting"))).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }

    val deadwood_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.deadwood_log.identifierLoc(), textureEnd = LCCBlocks.deadwood_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCBlockTags.deadwood_logs)).add(ItemTagFactory(LCCItemTags.deadwood_logs)).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.deadwood_log.identifierLoc(), textureEnd = LCCBlocks.deadwood_log.identifierLoc())).add(BlockTagFactory(LCCBlockTags.deadwood_logs)).add(ItemTagFactory(LCCItemTags.deadwood_logs)).add(BarkRecipeFactory(LCCBlocks.deadwood_log)).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val stripped_deadwood_log by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_deadwood_log.identifierLoc(), textureEnd = LCCBlocks.stripped_deadwood_log.identifierLocSuffix("top"))).add(BlockTagFactory(LCCBlockTags.deadwood_logs)).add(ItemTagFactory(LCCItemTags.deadwood_logs)).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val stripped_deadwood by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(ColumnBlockAssetFactory(textureSide = LCCBlocks.stripped_deadwood_log.identifierLoc(), textureEnd = LCCBlocks.stripped_deadwood_log.identifierLoc())).add(BlockTagFactory(LCCBlockTags.deadwood_logs)).add(ItemTagFactory(LCCItemTags.deadwood_logs)).add(BarkRecipeFactory(LCCBlocks.stripped_deadwood_log)).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_planks by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().mineableAxe().add(BlockTagFactory(BlockTags.PLANKS)).add(ItemTagFactory(ItemTags.PLANKS)).add(SingleShapelessFromTagRecipeFactory(LCCItemTags.deadwood_logs, "has_deadwood_logs" to InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.deadwood_logs).build()), outputCount = 4, group = "planks")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(StairsBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.WOODEN_STAIRS)).add(StairsRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_stairs")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineableAxe().add(SlabBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc(), full = LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.WOODEN_SLABS)).add(SlabRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_slab")).add(SlabLootFactory).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_door by entry(::initialiser) { data().defaultLang().mineableAxe().add(DoorBlockAssetFactory).add(GeneratedItemAssetFactory).add(DoorBlockLootFactory).add(BlockTagFactory(BlockTags.WOODEN_DOORS)).add(ItemTagFactory(ItemTags.WOODEN_DOORS)).add(DoorRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_door")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_pressure_plate by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineableAxe().add(PressurePlateBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_PRESSURE_PLATES)).add(ItemTagFactory(ItemTags.WOODEN_PRESSURE_PLATES)).add(PressurePlateRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_pressure_plate")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_button by entry(::initialiser) { data().defaultLang().defaultLootTable().mineableAxe().add(CustomItemAssetFactory { d, t, i -> Models.BUTTON_INVENTORY.upload(i(t) ?: idi.loc(t), TextureMap.texture(LCCBlocks.deadwood_planks.identifierLoc()), d.models) }).add(ButtonBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_BUTTONS)).add(ItemTagFactory(ItemTags.WOODEN_BUTTONS)).add(SingleShapelessFromItemRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_button")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_fence by entry(::initialiser) { data().defaultLang().defaultLootTable().mineableAxe().add(CustomItemAssetFactory { d, t, i -> Models.FENCE_INVENTORY.upload(i(t) ?: idi.loc(t), TextureMap.texture(LCCBlocks.deadwood_planks.identifierLoc()), d.models) }).add(FenceBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WOODEN_FENCES)).add(ItemTagFactory(ItemTags.WOODEN_FENCES)).add(FenceRecipeFactory(LCCBlocks.deadwood_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_fence_gate by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().mineableAxe().add(FenceGateBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.FENCE_GATES)).add(FenceGateRecipeFactory(LCCBlocks.deadwood_planks, Ingredient.ofItems(Items.STICK), group = "wooden_fence_gate")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_trapdoor by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().mineableAxe().add(TrapdoorBlockAssetFactory).add(BlockTagFactory(BlockTags.WOODEN_TRAPDOORS)).add(ItemTagFactory(ItemTags.WOODEN_TRAPDOORS)).add(TrapdoorRecipeFactory(LCCBlocks.deadwood_planks, group = "wooden_trapdoor")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_sign by entry(::initialiser) { data().defaultLang().defaultLootTable().mineableAxe().add(ParticleBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(GeneratedItemAssetFactory).add(BlockTagFactory(BlockTags.STANDING_SIGNS)).add(ItemTagFactory(ItemTags.SIGNS)).add(SignRecipeFactory(LCCBlocks.deadwood_planks, Ingredient.ofItems(Items.STICK), group = "wooden_sign")).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val deadwood_wall_sign by entry(::initialiser) { data().mineableAxe().add(ParticleBlockAssetFactory(LCCBlocks.deadwood_planks.identifierLoc())).add(BlockTagFactory(BlockTags.WALL_SIGNS)).add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }

    val spike_blocks by entry(::initialiser) { data().affects(LCCBlocks.all.values.filterIsInstance<SpikesBlock>()).defaultLang().defaultLootTable().defaultItemAsset().mineablePickaxe().withStoneTool().add(DirectionalBlockAssetFactory { d, t, i -> LCCModelTemplates.template_spikes.upload(i(t) ?: idb.loc(t), TextureMap.texture(idb.loc(t)), d.models) }).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val spikes by entry(::initialiser) {
        data().add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 2)
                .pattern(" n ")
                .pattern("nin")
                .pattern("iii")
                .input('n', LCCItems.iron_oxide_nugget)
                .input('i', LCCItems.iron_oxide)
                .apply { hasCriterionShaped(this, LCCItems.iron_oxide) }
                .apply { hasCriterionShaped(this, LCCItems.iron_oxide_nugget) }
                .apply { offerShaped(this, d) }
        })
    }
    val bleeding_spikes by entry(::initialiser) {
        data().add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 1)
                .pattern("h")
                .pattern("s")
                .input('h', LCCItems.heart_half[HeartType.RED])
                .input('s', LCCBlocks.spikes)
                .apply { hasCriterionShaped(this, LCCBlocks.spikes) }
                .criterion("has_red_hearts", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.red_hearts).build()))
                .apply { offerShaped(this, d) }
        })
    }
    val poison_spikes by entry(::initialiser) {
        data().add(CustomRecipeFactory { d, i ->
            ShapedRecipeJsonBuilder.create(i, 1)
                .pattern("w")
                .pattern("s")
                .input('w', LCCItems.stinger)
                .input('s', LCCBlocks.spikes)
                .apply { hasCriterionShaped(this, LCCBlocks.spikes) }
                .apply { hasCriterionShaped(this, LCCItems.stinger) }
                .apply { offerShaped(this, d) }
        })
    }

    val shattered_glass by entry(::initialiser) { data().affects(LCCBlocks.entries.values.filter { it.tags.contains("shattered_glass") }.map { it.entry }).defaultLang().defaultItemAsset().defaultBlockAsset().mineablePickaxe().add(SilkBlockLootFactory).add(BlockTagFactory(BlockTags.IMPERMEABLE)) }
    val shattered_glass_pane by entry(::initialiser) { data().affects(LCCBlocks.entries.values.filter { it.tags.contains("shattered_glass_pane") }.map { it.entry }).defaultLang().mineablePickaxe().add(GeneratedBlockItemAssetFactory { i -> idi.loc(i, "block").modify { it.replace("_pane", "") } }).add(GlassPaneBlockAssetFactory { b -> Identifier(idb.locSuffix(b, "top").path.replace("shattered_", "")) }).add(SilkBlockLootFactory) }

    val rhyolite_wall by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().add(WallBlockAssetFactory(LCCBlocks.rhyolite.identifierLoc())).add(CustomItemAssetFactory { d, t, i -> Models.WALL_INVENTORY.upload(idi.loc(t), TextureMap().put(TextureKey.WALL, LCCBlocks.rhyolite.identifierLoc()), d.models) }).add(BlockTagFactory(BlockTags.WALLS)).add(ItemTagFactory(ItemTags.WALLS)).add(WallRecipeFactory(LCCBlocks.rhyolite)).add(StonecutterItemRecipeFactory(LCCBlocks.rhyolite)) }

    val sapphire_altar by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().withDiamondTool().add(SapphireAltarBlockAssetFactory).add(SilkBlockLootFactory(LCCBlocks.sapphire_altar_brick, UniformLootNumberProvider.create(8f, 16f))).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val bomb_board_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().add(BombBoardBlockAssetFactory).add(BombBoardBlockLootFactory).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }

    val sapphire_altar_brick by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().mineablePickaxe().withDiamondTool().add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val sapphire_altar_brick_stairs by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withDiamondTool().add(StairsBlockAssetFactory(LCCBlocks.sapphire_altar_brick.identifierLoc())).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.sapphire_altar_brick)).add(StonecutterItemRecipeFactory(LCCBlocks.sapphire_altar_brick)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val sapphire_altar_brick_slab by entry(::initialiser) { data().defaultLang().defaultItemAsset().mineablePickaxe().withDiamondTool().add(SlabBlockAssetFactory(LCCBlocks.sapphire_altar_brick.identifierLoc(), full = LCCBlocks.sapphire_altar_brick.identifierLoc())).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.sapphire_altar_brick)).add(SlabLootFactory).add(StonecutterItemRecipeFactory(LCCBlocks.sapphire_altar_brick, 2)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }
    val sapphire_altar_brick_wall by entry(::initialiser) { data().defaultLang().defaultLootTable().mineablePickaxe().withDiamondTool().add(WallBlockAssetFactory(LCCBlocks.sapphire_altar_brick.identifierLoc())).add(CustomItemAssetFactory { d, t, i -> Models.WALL_INVENTORY.upload(idi.loc(t), TextureMap().put(TextureKey.WALL, LCCBlocks.sapphire_altar_brick.identifierLoc()), d.models) }).add(BlockTagFactory(BlockTags.WALLS)).add(ItemTagFactory(ItemTags.WALLS)).add(WallRecipeFactory(LCCBlocks.sapphire_altar_brick)).add(StonecutterItemRecipeFactory(LCCBlocks.sapphire_altar_brick)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }

    val three_leaf_clover by entry(::initialiser) { data().defaultLang().defaultLootTable().add(RotationBlockAssetFactory(y = (0..3).toList()) { d, t, i -> LCCModelTemplates.textured_cross.upload(idb.loc(t), TextureMap().put(LCCModelTextureKeys.t0, idb.loc(t)).put(LCCModelTextureKeys.t1, idb.locSuffix(t, "alt")), d.models) }).add(GeneratedItemAssetFactory).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_three_leaf_clover by entry(::initialiser) { data().defaultLang().add(RotationBlockAssetFactory(y = (0..3).toList()) { d, t, i -> LCCModelTemplates.flower_pot_textured_cross.upload(idb.loc(t), TextureMap().put(LCCModelTextureKeys.t0, idb.loc(LCCBlocks.three_leaf_clover)).put(LCCModelTextureKeys.t1, idb.locSuffix(LCCBlocks.three_leaf_clover, "alt")), d.models) }).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val four_leaf_clover by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedItemAssetFactory).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_four_leaf_clover by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val forget_me_not by entry(::initialiser) { data().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_forget_me_not by entry(::initialiser) { data().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }

    val enhancing_chamber by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().mineableAxe().add(BlockTagFactory(LCCBlockTags.wasteland_effective)).add(DirectionalBlockAssetFactory { d, t, i -> LCCModelTemplates.template_enhancing_chamber.upload(i(t) ?: idb.loc(t), TextureMap().put(TextureKey.TOP, idb.locSuffix(t, "top")).put(TextureKey.SIDE, idb.locSuffix(t, "side")).put(TextureKey.BOTTOM, idb.locSuffix(t, "bottom")).put(TextureKey.PARTICLE, idb.locSuffix(t, "side")), d.models) }).add(BlockTagFactory(LCCBlockTags.wasteland_effective)).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("w w")
            .pattern("w w")
            .pattern("www")
            .input('w', LCCBlocks.deadwood_planks)
            .apply { hasCriterionShaped(this, LCCBlocks.deadwood_planks) }
            .apply { hasCriterionShaped(this, LCCItems.enhancing_pyre_alpha) }
            .apply { offerShaped(this, d) }
    }).add(ComplexRecipeFactory(LCCRecipeSerializers.overlevel_enchants, LCC.id("overlevel_enchants"))) }
    val imbuing_press by entry(::initialiser) { data().defaultLang().defaultLootTable().defaultItemAsset().mineablePickaxe().add(BlockTagFactory(LCCBlockTags.wasteland_required)).add(HorizontalBlockAssetFactory({ d, t, i -> LCCModelTemplates.template_imbuing_press.upload(i(t) ?: idb.loc(t), TextureMap().put(LCCModelTextureKeys.t0, idb.locSuffix(t, "spike")).put(LCCModelTextureKeys.t1, idb.loc(t)).put(LCCModelTextureKeys.t2, idb.locSuffix(t, "side")).put(LCCModelTextureKeys.t3, idb.locSuffix(t, "bottom")).put(TextureKey.PARTICLE, idb.locSuffix(t, "bottom")), d.models) })).add(BlockTagFactory(LCCBlockTags.wasteland_required)).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("fsf")
            .pattern("fpf")
            .pattern("fff")
            .input('f', LCCBlocks.cobbled_fortstone)
            .input('s', LCCBlocks.cobbled_fortstone_slab)
            .input('p', Blocks.POINTED_DRIPSTONE)
            .apply { hasCriterionShaped(this, LCCBlocks.cobbled_fortstone) }
            .apply { hasCriterionShaped(this, LCCBlocks.cobbled_fortstone_slab) }
            .apply { offerShaped(this, d) }
    }) }

    val papercomb_block by entry(::initialiser) { data().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineableHoe().add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }
    val paper_envelopes by entry(::initialiser) { data().affects(LCCBlocks.paper_envelope.values.toList()).defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().mineableHoe().add(BlockTagFactory(LCCBlockTags.wasteland_effective)) }

    val attractive_magnetic_iron_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(SideBottomTopBlockAssetFactory(textureTop = LCC.block("magnetic_iron_block_red"), textureBottom = LCC.block("magnetic_iron_block_blue"), textureSide = idb.loc(LCCBlocks.attractive_magnetic_iron_block))).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("m m")
            .pattern("m m")
            .pattern("mmm")
            .input('m', LCCItems.magnetic_iron)
            .apply { hasCriterionShaped(this, LCCItems.magnetic_iron) }
            .apply { offerShaped(this, d) }
    }).add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonBuilder.create(LCCItems.magnetic_iron, 7)
            .input(i)
            .apply { hasCriterionShapeless(this, i) }
            .apply { offerShapeless(this, d, LCC.id("magnetic_iron_from_attractive")) }
    }) }
    val repulsive_magnetic_iron_block by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultLootTable().mineablePickaxe().withStoneTool().add(SideBottomTopBlockAssetFactory(textureTop = LCC.block("magnetic_iron_block_blue"), textureBottom = LCC.block("magnetic_iron_block_red"), textureSide = idb.loc(LCCBlocks.repulsive_magnetic_iron_block))).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("mmm")
            .pattern("m m")
            .pattern("m m")
            .input('m', LCCItems.magnetic_iron)
            .apply { hasCriterionShaped(this, LCCItems.magnetic_iron) }
            .apply { offerShaped(this, d) }
    }).add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonBuilder.create(LCCItems.magnetic_iron, 7)
            .input(i)
            .apply { hasCriterionShapeless(this, i) }
            .apply { offerShapeless(this, d, LCC.id("magnetic_iron_from_repulsive")) }
    }) }

    val spawning_pit by entry(::initialiser) { data().defaultLang().defaultItemAsset().defaultBlockAsset().mineablePickaxe().add(SilkBlockLootFactory(LCCBlocks.cracked_mud)).add(BlockTagFactory(LCCBlockTags.wasteland_required)) }

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
    private fun BlockDataContainer.mineablePickaxe() = add(BlockTagFactory(BlockTags.PICKAXE_MINEABLE))
    private fun BlockDataContainer.mineableAxe() = add(BlockTagFactory(BlockTags.AXE_MINEABLE))
    private fun BlockDataContainer.mineableShovel() = add(BlockTagFactory(BlockTags.SHOVEL_MINEABLE))
    private fun BlockDataContainer.mineableHoe() = add(BlockTagFactory(BlockTags.HOE_MINEABLE))
    private fun BlockDataContainer.mineableSword() = add(BlockTagFactory(FabricMineableTags.SWORD_MINEABLE))
    private fun BlockDataContainer.mineableShears() = add(BlockTagFactory(FabricMineableTags.SHEARS_MINEABLE))
    private fun BlockDataContainer.withStoneTool() = add(BlockTagFactory(BlockTags.NEEDS_STONE_TOOL))
    private fun BlockDataContainer.withIronTool() = add(BlockTagFactory(BlockTags.NEEDS_IRON_TOOL))
    private fun BlockDataContainer.withDiamondTool() = add(BlockTagFactory(BlockTags.NEEDS_DIAMOND_TOOL))

}