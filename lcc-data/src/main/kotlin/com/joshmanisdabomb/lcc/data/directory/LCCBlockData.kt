package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.block.AbstractTreetapBlock
import com.joshmanisdabomb.lcc.block.DriedTreetapBlock
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.block.TreetapBlock
import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
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
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.recipe.RefiningRecipe
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.block.Blocks
import net.minecraft.data.client.model.Models
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Items
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.recipe.Ingredient
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier

object LCCBlockData : BasicDirectory<BlockDataContainer, Unit>() {

    val test_block_2 by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(HorizontalBlockAssetFactory) }
    val test_block_3 by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(DirectionalBlockAssetFactory) }
    val test_block_4 by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory) }
    val test_block_5 by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block")) }) }

    val ruby_ore by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(OreBlockLootFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_ORE)) }
    val ruby_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.ruby)).add(RiftFromItemRecipeFactory(Blocks.EMERALD_BLOCK)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val topaz_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.topaz_shard, from = false)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val budding_topaz by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val topaz_clusters by entry(::initialiser) { BlockDataContainer().affects(LCCBlocks.budding_topaz.crystals.toList()).defaultLang().add(ClusterBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(ClusterBlockLootFactory(LCCItems.topaz_shard)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val sapphire_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage9RecipeFactory(LCCItems.sapphire)).add(BlockTagFactory(BlockTags.BEACON_BASE_BLOCKS)) }
    val uranium_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.uranium)) }
    val enriched_uranium_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.enriched_uranium)).add(ItemTagFactory(LCCTags.enriched_uranium)) }
    val heavy_uranium_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(StorageTranslationFactory).add(Storage4RecipeFactory(LCCItems.heavy_uranium)) }

    val cracked_mud by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory).add(BlockTagFactory(LCCTags.wasteland_effective)).add(BlockTagFactory(BlockTags.ENDERMAN_HOLDABLE)) }

    val oil by entry(::initialiser) { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("block/oil_still"))) }
    val asphalt by entry(::initialiser) { BlockDataContainer().defaultLang().add(ParticleBlockAssetFactory(LCC.id("block/asphalt_still"))) }
    val road by entry(::initialiser) { BlockDataContainer().defaultLang().add(RoadBlockAssetFactory) } //TODO maybe drop something

    val pumice by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RotationBlockAssetFactory(x = (0..3).toList(), y = (0..3).toList())) }
    val rhyolite by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(MirroredBlockAssetFactory) }

    val soaking_soul_sand by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonFactory.create(i, 8)
            .input(Blocks.WET_SPONGE)
            .input(Blocks.SOUL_SAND, 8)
            .apply { hasCriterionShapeless(this, Blocks.SPONGE) }
            .apply { offerShapeless(this, d) }
    }) }
    val bounce_pad by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(BouncePadBlockAssetFactory).add(BouncePadItemAssetFactory).add(CustomRecipeFactory { d, i ->
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

    val time_rift by entry(::initialiser) { BlockDataContainer().defaultLang().add(LiteralTranslationFactory("Wibbly Wobbly Timey Wimey Stuff", "en_gb")).add(ParticleBlockAssetFactory).add(DynamicItemAssetFactory(DynamicItemAssetFactory.block)).add(SilkBlockLootFactory(LCCItems.simulation_fabric)).add(CustomRecipeFactory { d, i ->
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
    val spawner_table by entry(::initialiser) { BlockDataContainer().defaultItemAsset().add(LiteralTranslationFactory("Arcane Table")).add(DungeonTableBlockAssetFactory).add(DungeonTableBlockLootFactory).add(CustomRecipeFactory { d, i ->
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

    val classic_grass_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(ClassicGrassBlockAssetFactory).add(SilkBlockLootFactory(Blocks.DIRT)).add(RiftFromItemRecipeFactory(Blocks.GRASS_BLOCK)) }
    val classic_cobblestone by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE)).add(BlockTagFactory(LCCTags.nether_reactor_shell)) }
    val classic_planks by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromTagRecipeFactory(ItemTags.PLANKS)) }
    val classic_leaves by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(LeavesBlockLootFactory(LCCBlocks.classic_sapling)).add(RiftFromTagRecipeFactory(ItemTags.LEAVES)).add(BlockTagFactory(BlockTags.LEAVES)) }
    val classic_sapling by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromTagRecipeFactory(ItemTags.SAPLINGS)).add(BlockTagFactory(BlockTags.SAPLINGS)) }
    val potted_classic_sapling by entry(::initialiser) { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_rose by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.POPPY)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_classic_rose by entry(::initialiser) { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val cyan_flower by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(RiftFromItemRecipeFactory(Blocks.BLUE_ORCHID)).add(BlockTagFactory(BlockTags.SMALL_FLOWERS)) }
    val potted_cyan_flower by entry(::initialiser) { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val classic_gravel by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(ChanceAlternativeBlockLootFactory(Items.FLINT, 0.1f, 0.14285715f, 0.25f, 1.0f)).add(RiftFromItemRecipeFactory(Blocks.GRAVEL)) }
    val classic_sponge by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.SPONGE)) }
    val classic_glass by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().add(SilkBlockLootFactory).add(RiftFromItemRecipeFactory(Blocks.GLASS)).add(BlockTagFactory(BlockTags.IMPERMEABLE)) }
    val classic_bricks by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.BRICKS)) }
    val classic_mossy_cobblestone by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE)) }
    val classic_iron_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.IRON_BLOCK)) }
    val classic_gold_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.GOLD_BLOCK)).add(BlockTagFactory(LCCTags.nether_reactor_base)).add(ItemTagFactory(LCCTags.gold_blocks)) }
    val classic_diamond_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(SideBottomTopBlockAssetFactory).add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Classic", "Classic Block of") }).add(RiftFromItemRecipeFactory(Blocks.DIAMOND_BLOCK)) }
    val alpha_iron_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val alpha_gold_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }).add(BlockTagFactory(LCCTags.nether_reactor_base)).add(ItemTagFactory(LCCTags.gold_blocks)) }
    val alpha_diamond_block by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultBlockAsset().defaultLootTable().add(StorageTranslationFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Block of Alpha", "Alpha Block of") }) }
    val classic_tnt by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SideBottomTopBlockAssetFactory).add(TransformTranslationFactory(*LCCData.accessor.locales.toTypedArray()) { it.replace("Tnt", "TNT") }).add(RiftFromItemRecipeFactory(Blocks.TNT)) }
    val pocket_stonecutter by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StonecutterBlockAssetFactory).add(RiftFromItemRecipeFactory(Blocks.STONECUTTER)) }
    val classic_chest by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(ClassicChestBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelOrientable(d, i, texture = loc(i, folder = "block")) }).add(RiftFromItemRecipeFactory(Blocks.CHEST)) }
    val nether_reactor by entry(::initialiser) { BlockDataContainer().defaultLang().add(NetherReactorBlockAssetFactory).add(ParentBlockItemAssetFactory(LCC.id("block/nether_reactor_ready"))).add(NetherReactorBlockLootFactory).add(RiftFromItemRecipeFactory(Items.NETHER_STAR)) }
    val classic_crying_obsidian by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory { d, i -> modelCubeAll(d, i, texture = loc(i, folder = "block") { it.plus("_static") }) }).add(RiftFromItemRecipeFactory(Blocks.CRYING_OBSIDIAN)).add(BlockTagFactory(BlockTags.CRYSTAL_SOUND_BLOCKS)) }
    val cog by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(GeneratedBlockItemAssetFactory).add(CogBlockAssetFactory).add(RiftFromItemRecipeFactory(Items.REDSTONE)) }

    val red_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.RED_WOOL)) }
    val orange_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.ORANGE_WOOL)) }
    val yellow_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.YELLOW_WOOL)) }
    val lime_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIME_WOOL)) }
    val light_blue_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIGHT_BLUE_WOOL)) }
    val purple_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.PURPLE_WOOL)) }
    val magenta_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.MAGENTA_WOOL)) }
    val gray_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.GRAY_WOOL)) }
    val light_gray_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.LIGHT_GRAY_WOOL)) }
    val white_classic_cloth by entry(::initialiser) { BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable().add(RiftFromItemRecipeFactory(Blocks.WHITE_WOOL)) }

    val refiner by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(RefiningBlockAssetFactory).add(ConcreteRefiningRecipeFactory).add(CustomRecipeFactory { d, i ->
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
            .apply { offerInterface(this, d, suffix(Items.LEATHER.identifier.run { LCC.id(path) }, "from_refiner")) }
    }) }
    val power_cable by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(Cable4BlockAssetFactory).add(Cable4ItemAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 3)
            .pattern("ccc")
            .input('c', Items.COPPER_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }) }

    val coal_generator by entry(::initialiser) { BlockDataContainer().defaultLootTable().defaultItemAsset().add(LiteralTranslationFactory("Furnace Generator", "en_us")).add(FiredGeneratorBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("cec")
            .pattern("cfc")
            .pattern("iii")
            .input('c', Items.COPPER_INGOT)
            .input('e', Items.IRON_BARS)
            .input('f', Blocks.FURNACE)
            .input('i', Items.IRON_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }).add(ItemTagFactory(LCCTags.generators)) }
    val oil_generator by entry(::initialiser) { BlockDataContainer().defaultLootTable().defaultItemAsset().add(LiteralTranslationFactory("Combustion Generator", "en_us")).add(FiredGeneratorBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("cec")
            .pattern("cfc")
            .pattern("iii")
            .input('c', Items.COPPER_INGOT)
            .input('e', Items.IRON_BARS)
            .input('f', Items.FLINT_AND_STEEL)
            .input('i', Items.IRON_INGOT)
            .apply { hasCriterionShaped(this, Items.COPPER_INGOT) }
            .apply { offerShaped(this, d) }
    }).add(ItemTagFactory(LCCTags.generators)) }

    val solar_panel by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(SpecialBlockAssetFactory).add(CustomItemAssetFactory { d, i -> LCCModelTemplates.template_solar_panel.upload(loc(i), Texture().put(TextureKey.TOP, loc(i, folder = "block")).put(TextureKey.SIDE, loc(i, folder = "block") { it.plus("_side") }).put(TextureKey.BOTTOM, loc(i, folder = "block") { it.plus("_bottom") }), d.modelStates::addModel) }).add(CustomRecipeFactory { d, i ->
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

    val turbine by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(LiteralTranslationFactory("Steam Turbine", "en_us")).add(CustomItemAssetFactory { d, i -> LCCModelTemplates.template_solar_panel.upload(loc(i), Texture().put(TextureKey.TOP, loc(i, folder = "block")).put(TextureKey.SIDE, loc(LCC.id("solar_panel"), folder = "block") { it.plus("_side") }).put(TextureKey.BOTTOM, loc(LCC.id("solar_panel"), folder = "block") { it.plus("_bottom") }), d.modelStates::addModel) }).add(TurbineBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory.create(i)
            .pattern(" t ")
            .pattern("ici")
            .input('t', LCCItems.turbine_blades)
            .input('i', Items.IRON_INGOT)
            .input('c', Items.COPPER_INGOT)
            .apply { hasCriterionShaped(this, LCCItems.turbine_blades) }
            .apply { offerShaped(this, d) }
    }) }

    val energy_bank by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().defaultItemAsset().add(DirectionalBlockAssetFactory { d, b -> modelOrientableBottom(d, b, texture = loc(LCC.id("refiner")), textureTop = loc(b), textureFront = loc(LCC.id("refiner_side"))) }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("cec")
            .pattern("bbb")
            .pattern("bbb")
            .input('c', Items.COPPER_INGOT)
            .input('e', Items.IRON_BLOCK)
            .input('b', LCCItems.redstone_battery)
            .apply { hasCriterionShaped(this, LCCItems.redstone_battery) }
            .apply { offerShaped(this, d) }
    }) }

    val nuclear_fire by entry(::initialiser) { BlockDataContainer().defaultLang().add(StaticFireBlockAssetFactory) }
    val atomic_bomb by entry(::initialiser) { BlockDataContainer().defaultLang().add(AtomicBombBlockAssetFactory).add(AtomicBombBlockLootFactory).add(CustomItemAssetFactory { d, i -> LCCModelTemplates.template_atomic_bomb_item.upload(loc(i), Texture().put(LCCModelTextureKeys.t1, loc(i, folder = "block") { it.plus("_tail_side") }).put(LCCModelTextureKeys.t2, loc(i, folder = "block") { it.plus("_tail") }).put(LCCModelTextureKeys.t3, loc(i, folder = "block") { it.plus("_fin") }).put(LCCModelTextureKeys.t4, loc(i, folder = "block") { it.plus("_core") }).put(LCCModelTextureKeys.t5, loc(i, folder = "block") { it.plus("_head") }), d.modelStates::addModel) }).add(CustomRecipeFactory { d, i ->
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

    val natural_rubber_log by entry(::initialiser) { BlockDataContainer().defaultLang().add(CustomItemAssetFactory { d, i -> val texture = loc(LCCBlocks.rubber_log.asItem(), folder = "block"); modelOrientableBottom(d, i, texture = texture, textureFront = suffix(texture, "tapped"), textureSide = texture, textureBottom = suffix(texture, "top")) }).add(BooleanHorizontalSideBlockAssetFactory(LCC.id("block/rubber_log_tapped"), LCC.id("block/rubber_log"), LCC.id("block/rubber_log_top"))).add(SimpleBlockLootFactory(LCCBlocks.rubber_log)).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_log by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCC.id("block/rubber_log"), textureEnd = LCC.id("block/rubber_log_top"))).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_wood by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCC.id("block/rubber_log"), textureEnd = LCC.id("block/rubber_log"))).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(BarkRecipeFactory(LCCBlocks.rubber_log)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val sappy_stripped_rubber_log by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SapBurstBlockAssetFactory(texture = LCC.id("block/stripped_rubber_log"))).add(SimpleBlockLootFactory(LCCBlocks.stripped_rubber_log)).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val stripped_rubber_log by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCC.id("block/stripped_rubber_log"), textureEnd = LCC.id("block/stripped_rubber_log_top"))).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val stripped_rubber_wood by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(ColumnBlockAssetFactory(textureSide = LCC.id("block/stripped_rubber_log"), textureEnd = LCC.id("block/stripped_rubber_log"))).add(BlockTagFactory(LCCTags.rubber_logs)).add(ItemTagFactory(LCCTags.rubber_logs_i)).add(BarkRecipeFactory(LCCBlocks.stripped_rubber_log)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_planks by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().defaultBlockAsset().add(BlockTagFactory(BlockTags.PLANKS)).add(ItemTagFactory(ItemTags.PLANKS)).add(SingleShapelessFromTagRecipeFactory(LCCTags.rubber_logs_i, "has_rubber_logs" to InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCTags.rubber_logs_i).build()), outputCount = 4)) }
    val rubber_sapling by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(PlantBlockAssetFactory).add(GeneratedBlockItemAssetFactory).add(BlockTagFactory(BlockTags.SAPLINGS)).add(ItemTagFactory(ItemTags.SAPLINGS)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val potted_rubber_sapling by entry(::initialiser) { BlockDataContainer().defaultLang().add(PottedPlantBlockAssetFactory).add(PottedPlantBlockLootFactory).add(BlockTagFactory(BlockTags.FLOWER_POTS)) }
    val rubber_leaves by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(LeavesBlockLootFactory(LCCBlocks.rubber_sapling, saplingChance = floatArrayOf(0.067F, 0.09F, 0.12F, 0.15F))).add(BlockTagFactory(BlockTags.LEAVES)).add(TintBlockAssetFactory).add(BlockTagFactory(BlockTags.LEAVES)).add(ItemTagFactory(ItemTags.LEAVES)).add(ItemTagFactory(LCCTags.rubber_tree)) }
    val rubber_stairs by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCC.id("block/rubber_planks"))).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.WOODEN_STAIRS)).add(StairsRecipeFactory(LCCBlocks.rubber_planks)) }
    val rubber_slab by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCC.id("block/rubber_planks")) { d, b -> loc(LCCBlocks.rubber_planks) }).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.WOODEN_SLABS)).add(SlabRecipeFactory(LCCBlocks.rubber_planks)).add(SlabLootFactory) }
    val rubber_door by entry(::initialiser) { BlockDataContainer().defaultLang().add(DoorBlockAssetFactory).add(GeneratedItemAssetFactory).add(DoorBlockLootFactory).add(BlockTagFactory(BlockTags.WOODEN_DOORS)).add(ItemTagFactory(ItemTags.WOODEN_DOORS)).add(DoorRecipeFactory(LCCBlocks.rubber_planks)) }
    val rubber_pressure_plate by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(PressurePlateBlockAssetFactory(LCC.id("block/rubber_planks"))).add(BlockTagFactory(BlockTags.WOODEN_PRESSURE_PLATES)).add(ItemTagFactory(ItemTags.WOODEN_PRESSURE_PLATES)).add(PressurePlateRecipeFactory(LCCBlocks.rubber_planks)) }
    val rubber_button by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(CustomItemAssetFactory { d, i -> Models.BUTTON_INVENTORY.upload(loc(i), Texture.texture(loc(LCCBlocks.rubber_planks.asItem(), folder = "block")), d.modelStates::addModel) }).add(ButtonBlockAssetFactory(LCC.id("block/rubber_planks"))).add(BlockTagFactory(BlockTags.WOODEN_BUTTONS)).add(ItemTagFactory(ItemTags.WOODEN_BUTTONS)).add(SingleShapelessFromItemRecipeFactory(LCCBlocks.rubber_planks)) }
    val rubber_fence by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().add(CustomItemAssetFactory { d, i -> Models.FENCE_INVENTORY.upload(loc(i), Texture.texture(loc(LCCBlocks.rubber_planks.asItem(), folder = "block")), d.modelStates::addModel) }).add(FenceBlockAssetFactory(LCC.id("block/rubber_planks"))).add(BlockTagFactory(BlockTags.WOODEN_FENCES)).add(ItemTagFactory(ItemTags.WOODEN_FENCES)).add(FenceRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK))) }
    val rubber_fence_gate by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().defaultItemAsset().add(FenceGateBlockAssetFactory(LCC.id("block/rubber_planks"))).add(BlockTagFactory(BlockTags.FENCE_GATES)).add(FenceGateRecipeFactory(LCCBlocks.rubber_planks, Ingredient.ofItems(Items.STICK))) }
    val rubber_trapdoor by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().defaultItemAsset().add(TrapdoorBlockAssetFactory).add(BlockTagFactory(BlockTags.WOODEN_TRAPDOORS)).add(ItemTagFactory(ItemTags.WOODEN_TRAPDOORS)).add(TrapdoorRecipeFactory(LCCBlocks.rubber_planks)) }
    val treetap_bowl by entry(::initialiser) { BlockDataContainer().defaultLang().add(TreetapStorageBlockAssetFactory(AbstractTreetapBlock.TreetapContainer.BOWL, LCCModelTemplates.template_treetap_bowl_1, LCCModelTemplates.template_treetap_bowl_2, LCCModelTemplates.template_treetap_bowl_3, LCCModelTemplates.template_treetap_bowl_dried) { d, b -> LCCModelTemplates.template_treetap_bowl.upload(loc(b), Texture().put(LCCModelTextureKeys.t0, loc(LCCBlocks.treetap)).put(LCCModelTextureKeys.t1, loc(b)), d.modelStates::addModel) }).add(TreetapContainerBlockLootFactory(LCCBlocks.treetap, Items.BOWL)) }
    val treetap by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(TreetapBlockAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 2)
            .pattern("i  ")
            .pattern("iii")
            .input('i', Items.IRON_INGOT)
            .criterion("has_rubber_tree", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCTags.rubber_tree).build()))
            .apply { offerShaped(this, d) }
    }).add(TreetapBlockLootFactory<TreetapBlock.TreetapState, TreetapBlock.TreetapState>(LCCBlocks.treetap, TreetapBlock.tap, { it.container?.item })) }
    val dried_treetap by entry(::initialiser) { BlockDataContainer().defaultLang().add(TreetapDriedBlockAssetFactory).add(TreetapBlockLootFactory(LCCBlocks.treetap, DriedTreetapBlock.container, AbstractTreetapBlock.TreetapContainer::item, DriedTreetapBlock.liquid, { it.dryProduct?.item })) }
    val oxygen_extractor by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().defaultItemAsset().add(CustomBlockAssetFactory { d, b -> modelCubeBottomTop(d, b, textureBottom = loc(LCC.id("refiner_bottom"))) }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("chc")
            .pattern("cbc")
            .pattern("iii")
            .input('c', Items.COPPER_INGOT)
            .input('h', Items.HOPPER)
            .input('b', Items.BUCKET)
            .input('i', Items.IRON_INGOT)
            .apply { hasCriterionShaped(this, LCCItems.oxygen_tank) }
            .apply { offerShaped(this, d) }
    }) }

    val kiln by entry(::initialiser) { BlockDataContainer().defaultLang().defaultLootTable().defaultItemAsset().add(FurnaceBlockAssetFactory(textureTop = LCC.id("block/kiln_side"), textureBottom = LCC.id("block/kiln_side"))).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonFactory(i, 1)
            .pattern("bbb")
            .pattern("bfb")
            .pattern("bbb")
            .input('b', Blocks.BRICKS)
            .input('f', Blocks.FURNACE)
            .apply { hasCriterionShaped(this, Blocks.BRICKS) }
            .apply { offerShaped(this, d) }
    }) }

    val dirt_slab by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(Identifier("minecraft:block/dirt")) { d, b -> Identifier("minecraft:block/dirt") }).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(RiftFromItemRecipeFactory(Blocks.DIRT, 2)).add(SlabLootFactory) }
    val classic_wooden_stairs by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCC.id("block/classic_planks"))).add(BlockTagFactory(BlockTags.WOODEN_STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_planks)).add(RiftFromTagRecipeFactory(ItemTags.WOODEN_STAIRS)) }
    val classic_wooden_slab by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCC.id("block/classic_planks")) { d, b -> loc(LCCBlocks.classic_planks) }).add(BlockTagFactory(BlockTags.WOODEN_SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_planks)).add(RiftFromTagRecipeFactory(ItemTags.WOODEN_SLABS)).add(SlabLootFactory) }
    val classic_cobblestone_stairs by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCC.id("block/classic_cobblestone"))).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE_STAIRS)) }
    val classic_cobblestone_slab by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCC.id("block/classic_cobblestone")) { d, b -> loc(LCCBlocks.classic_cobblestone) }).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.COBBLESTONE_SLAB)).add(SlabLootFactory) }
    val classic_brick_stairs by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCC.id("block/classic_bricks"))).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_bricks)).add(RiftFromItemRecipeFactory(Blocks.BRICK_STAIRS)) }
    val classic_brick_slab by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCC.id("block/classic_bricks")) { d, b -> loc(LCCBlocks.classic_bricks) }).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_bricks)).add(RiftFromItemRecipeFactory(Blocks.BRICK_SLAB)).add(SlabLootFactory) }
    val classic_mossy_cobblestone_stairs by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCC.id("block/classic_mossy_cobblestone"))).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.classic_mossy_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE_STAIRS)) }
    val classic_mossy_cobblestone_slab by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCC.id("block/classic_mossy_cobblestone")) { d, b -> loc(LCCBlocks.classic_mossy_cobblestone) }).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.classic_mossy_cobblestone)).add(RiftFromItemRecipeFactory(Blocks.MOSSY_COBBLESTONE_SLAB)).add(SlabLootFactory) }
    val rhyolite_stairs by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().defaultLootTable().add(StairsBlockAssetFactory(LCC.id("block/rhyolite"))).add(BlockTagFactory(BlockTags.STAIRS)).add(ItemTagFactory(ItemTags.STAIRS)).add(StairsRecipeFactory(LCCBlocks.rhyolite)) }
    val rhyolite_slab by entry(::initialiser) { BlockDataContainer().defaultLang().defaultItemAsset().add(SlabBlockAssetFactory(LCC.id("block/rhyolite")) { d, b -> loc(LCCBlocks.rhyolite) }).add(BlockTagFactory(BlockTags.SLABS)).add(ItemTagFactory(ItemTags.SLABS)).add(SlabRecipeFactory(LCCBlocks.rhyolite)).add(SlabLootFactory) }

    fun initialiser(input: BlockDataContainer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out BlockDataContainer, out BlockDataContainer>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.init(it.name, LCCBlocks.getOrNull(it.name)) }

        val missing = LCCBlocks.all.values.minus(initialised.flatMap { it.entry.affects })
        missing.forEach { val key = LCCBlocks[it].name; defaults().init(key, it) }
    }

    fun defaults() = BlockDataContainer().defaultLang().defaultBlockAsset().defaultItemAsset().defaultLootTable()

    private fun BlockDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun BlockDataContainer.defaultBlockAsset() = add(SimpleBlockAssetFactory)
    private fun BlockDataContainer.defaultItemAsset() = add(ParentBlockItemAssetFactory)
    private fun BlockDataContainer.defaultLootTable() = add(SelfBlockLootFactory)

}