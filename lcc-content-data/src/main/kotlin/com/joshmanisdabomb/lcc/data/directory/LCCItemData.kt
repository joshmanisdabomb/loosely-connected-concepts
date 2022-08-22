package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.container.ItemDataContainer
import com.joshmanisdabomb.lcc.data.factory.asset.ModelAccess
import com.joshmanisdabomb.lcc.data.factory.asset.item.*
import com.joshmanisdabomb.lcc.data.factory.recipe.*
import com.joshmanisdabomb.lcc.data.factory.tag.HeartItemTagFactory
import com.joshmanisdabomb.lcc.data.factory.tag.ItemTagFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.LiteralTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.TransformTranslationFactory
import com.joshmanisdabomb.lcc.data.json.recipe.ImbuingRecipeJsonFactory
import com.joshmanisdabomb.lcc.data.json.recipe.RefiningShapelessRecipeJsonFactory
import com.joshmanisdabomb.lcc.directory.*
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.item.HeartItem
import com.joshmanisdabomb.lcc.recipe.refining.RefiningSimpleRecipe
import net.minecraft.advancement.criterion.EffectsChangedCriterion
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.block.Blocks
import net.minecraft.data.client.Models
import net.minecraft.data.client.TextureKey
import net.minecraft.data.client.TextureMap
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.data.server.recipe.SmithingRecipeJsonBuilder
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ArmorItem
import net.minecraft.item.Items
import net.minecraft.item.ToolItem
import net.minecraft.predicate.entity.EntityEffectPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.tag.ItemTags

object LCCItemData : BasicDirectory<ItemDataContainer, Unit>(), ModelAccess {

    val ruby by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SmeltFromItemRecipeFactory(LCCBlocks.ruby_ore, RecipeSerializer.SMELTING)).add(SmeltFromItemRecipeFactory(LCCBlocks.ruby_ore, RecipeSerializer.BLASTING)).add(RiftFromItemRecipeFactory(Items.EMERALD)).add(ItemTagFactory(ItemTags.BEACON_PAYMENT_ITEMS)) }
    val sapphire by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ItemTagFactory(ItemTags.BEACON_PAYMENT_ITEMS)) }
    val uranium by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SmeltFromItemRecipeFactory(LCCBlocks.uranium_ore, RecipeSerializer.SMELTING)).add(SmeltFromItemRecipeFactory(LCCBlocks.uranium_ore, RecipeSerializer.BLASTING)).add(SmeltFromItemRecipeFactory(LCCBlocks.deepslate_uranium_ore, RecipeSerializer.SMELTING, name = LCC.id("uranium_from_deepslate_ore"))).add(SmeltFromItemRecipeFactory(LCCBlocks.deepslate_uranium_ore, RecipeSerializer.BLASTING, name = LCC.id("uranium_from_deepslate_ore_blasting"))) }
    val enriched_uranium by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ItemTagFactory(LCCItemTags.enriched_uranium)) }
    val uranium_nugget by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(Nugget9RecipeFactory(LCCItems.uranium)) }
    val enriched_uranium_nugget by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(Nugget9RecipeFactory(LCCItems.enriched_uranium)).add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(LCCItems.uranium)
            .addOutput(i, 1, RefiningSimpleRecipe.OutputFunction.RangeOutputFunction(8))
            .addOutput(LCCItems.heavy_uranium, 1)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.uranium", 1, RefiningBlock.RefiningProcess.ENRICHING)
            .speed(4000, 0.005f, 400f)
            .energyPerOperation(LooseEnergy.fromCoals(24f))
            .apply { hasCriterionInterface(this, LCCItems.uranium) }
            .apply { offerInterface(this, d, suffix(i.identifier.run { LCC.id(path) }, "from_refiner")) }
    }).add(ItemTagFactory(LCCItemTags.enriched_uranium)) }
    val heavy_uranium_nugget by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(Nugget9RecipeFactory(LCCItems.heavy_uranium)) }

    val tungsten_ingot by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SmeltFromItemRecipeFactory(LCCItems.raw_tungsten, RecipeSerializer.SMELTING)).add(SmeltFromItemRecipeFactory(LCCItems.raw_tungsten, RecipeSerializer.BLASTING)).add(SmeltFromItemRecipeFactory(LCCBlocks.tungsten_ore, RecipeSerializer.SMELTING, name = LCC.id("tungsten_from_ore"))).add(SmeltFromItemRecipeFactory(LCCBlocks.tungsten_ore, RecipeSerializer.BLASTING, name = LCC.id("tungsten_from_ore_blasting"))).add(SmeltFromItemRecipeFactory(LCCBlocks.deepslate_tungsten_ore, RecipeSerializer.SMELTING, name = LCC.id("tungsten_from_deepslate_ore"))).add(SmeltFromItemRecipeFactory(LCCBlocks.deepslate_tungsten_ore, RecipeSerializer.BLASTING, name = LCC.id("tungsten_from_deepslate_ore_blasting"))) }

    val ruby_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("ruby_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCItems.ruby)) }
    val topaz_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("topaz_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCItems.topaz_shard)) }
    val emerald_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("emerald_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(Items.EMERALD)) }
    val sapphire_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("sapphire_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCItems.sapphire)).add(ItemTagFactory(LCCItemTags.wasteland_equipment)) }
    val amethyst_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("amethyst_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(Items.AMETHYST_SHARD)) }
    val ruby_armor by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.startsWith("ruby_") }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.ruby)) }
    val topaz_armor by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.startsWith("topaz_") }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.topaz_shard)) }
    val emerald_armor by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.startsWith("emerald_") }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(Items.EMERALD)) }
    val sapphire_armor by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.startsWith("sapphire_") }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.sapphire)).add(ItemTagFactory(LCCItemTags.wasteland_equipment)) }
    val amethyst_armor by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.startsWith("amethyst_") }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(Items.AMETHYST_SHARD)) }

    val simulation_fabric by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonBuilder.create(i)
            .input(Blocks.STONE).input(Blocks.ANDESITE).input(Blocks.GRANITE).input(Blocks.DIORITE).input(Blocks.COBBLESTONE).input(Blocks.MOSSY_COBBLESTONE).input(Blocks.STONE_BRICKS).input(Blocks.BRICKS)
            .input(Blocks.DIRT).input(Blocks.GRASS_BLOCK).input(Blocks.SAND).input(Blocks.GRAVEL).input(Blocks.CLAY)
            .input(Blocks.OAK_LOG).input(Blocks.SPRUCE_LOG).input(Blocks.BIRCH_LOG).input(Blocks.JUNGLE_LOG).input(Blocks.ACACIA_LOG).input(Blocks.DARK_OAK_LOG)
            .input(Blocks.WHITE_WOOL).input(Items.COAL).input(Items.DIAMOND).input(Items.EMERALD)
            .input(Blocks.OBSIDIAN).input(Blocks.NETHERRACK).input(Blocks.SOUL_SAND).input(Blocks.GLOWSTONE)
            .input(Items.FEATHER).input(Items.ARROW).input(Items.STRING).input(Items.GUNPOWDER).input(Items.PORKCHOP).input(Items.COD).input(Items.GOLDEN_APPLE)
            .input(Blocks.CRAFTING_TABLE).input(Blocks.FURNACE).input(Blocks.CHEST).input(Blocks.JUKEBOX).input(Blocks.TNT).input(Blocks.RED_BED)
            .input(Items.COMPASS).input(Items.CLOCK).input(ItemTags.MUSIC_DISCS)
            .input(Items.CACTUS).input(Items.SUGAR_CANE).input(Items.WHEAT_SEEDS).input(Items.DANDELION).input(Items.POPPY)
            .apply { hasCriterionShapeless(this, LCCItems.simulation_fabric) }
            .apply { offerShapeless(this, d, override = LCCRecipeSerializers.spawner_table_shapeless) }
    }) }
    val classic_leather_helmet by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.LEATHER_HELMET)) }
    val classic_leather_chestplate by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.LEATHER_CHESTPLATE)) }
    val classic_leather_leggings by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.LEATHER_LEGGINGS)) }
    val classic_leather_boots by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.LEATHER_BOOTS)) }
    val classic_studded_leather_helmet by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i -> SmithingRecipeJsonBuilder.create(Ingredient.ofItems(LCCItems.classic_leather_helmet), Ingredient.ofItems(Items.IRON_INGOT), i).apply { hasCriterionSmithing(this, LCCItems.classic_leather_helmet) }.apply { offerSmithing(this, d, LCCItems[i].id) } }) }
    val classic_studded_leather_chestplate by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i -> SmithingRecipeJsonBuilder.create(Ingredient.ofItems(LCCItems.classic_leather_chestplate), Ingredient.ofItems(Items.IRON_INGOT), i).apply { hasCriterionSmithing(this, LCCItems.classic_leather_helmet) }.apply { offerSmithing(this, d, LCCItems[i].id) } }) }
    val classic_studded_leather_leggings by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i -> SmithingRecipeJsonBuilder.create(Ingredient.ofItems(LCCItems.classic_leather_leggings), Ingredient.ofItems(Items.IRON_INGOT), i).apply { hasCriterionSmithing(this, LCCItems.classic_leather_helmet) }.apply { offerSmithing(this, d, LCCItems[i].id) } }) }
    val classic_studded_leather_boots by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i -> SmithingRecipeJsonBuilder.create(Ingredient.ofItems(LCCItems.classic_leather_boots), Ingredient.ofItems(Items.IRON_INGOT), i).apply { hasCriterionSmithing(this, LCCItems.classic_leather_helmet) }.apply { offerSmithing(this, d, LCCItems[i].id) } }) }
    val classic_raw_porkchop by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.PORKCHOP)) }
    val classic_cooked_porkchop by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.COOKED_PORKCHOP)).add(SmeltFromItemRecipeFactory(LCCItems.classic_raw_porkchop, RecipeSerializer.SMELTING, experience = 0.35f)).add(SmeltFromItemRecipeFactory(LCCItems.classic_raw_porkchop, RecipeSerializer.SMOKING, experience = 0.35f)).add(SmeltFromItemRecipeFactory(LCCItems.classic_raw_porkchop, RecipeSerializer.CAMPFIRE_COOKING, experience = 0.35f)) }
    val classic_raw_fish by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.COD)) }
    val classic_cooked_fish by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.COOKED_COD)).add(SmeltFromItemRecipeFactory(LCCItems.classic_raw_fish, RecipeSerializer.SMELTING, experience = 0.35f)).add(SmeltFromItemRecipeFactory(LCCItems.classic_raw_fish, RecipeSerializer.SMOKING, experience = 0.35f)).add(SmeltFromItemRecipeFactory(LCCItems.classic_raw_fish, RecipeSerializer.CAMPFIRE_COOKING, experience = 0.35f)) }
    val classic_apple by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.APPLE)) }
    val classic_golden_apple by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(RiftFromItemRecipeFactory(Items.ENCHANTED_GOLDEN_APPLE)).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("ggg")
            .pattern("gag")
            .pattern("ggg")
            .input('g', LCCItemTags.gold_blocks)
            .input('a', LCCItems.classic_apple)
            .apply { hasCriterionShaped(this, LCCItems.classic_apple) }
            .apply { offerShaped(this, d) }
    }) }
    val quiver by entry(::initialiser) { data().defaultLang().add(QuiverItemAssetFactory).add(RiftFromItemRecipeFactory(Items.BUNDLE)) }

    val gauntlet by entry(::initialiser) { data().add(DynamicItemAssetFactory).add(LiteralTranslationFactory("Doom Gauntlet")).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
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
            .apply { hasCriterionShaped(this, Items.NETHER_STAR) }
            .apply { offerShaped(this, d, override = LCCRecipeSerializers.spawner_table_shaped) }
    }) }

    val oil_bucket by entry(::initialiser) { data().defaultItemAsset().add(LiteralTranslationFactory("Crude Oil Bucket")).add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(i, 2)
            .addOutput(LCCItems.fuel_bucket)
            .addOutput(LCCItems.refined_oil_bucket)
            .addOutput(LCCItems.tar_ball)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.fractional_distillation", 7, RefiningBlock.RefiningProcess.TREATING)
            .speed(3000, 0.008f, 400f)
            .energyPerOperation(LooseEnergy.fromCoals(4f))
            .apply { hasCriterionInterface(this, i) }
            .apply { offerInterface(this, d, LCC.id("oil_distillation")) }
    }) }
    val refined_oil_bucket by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(LCCItems.tar_ball, 2)
            .addInput(Items.WATER_BUCKET)
            .addOutput(i)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.tar_cracking", 8, RefiningBlock.RefiningProcess.TREATING)
            .speed(6000, 0.012f, 400f)
            .energyPerOperation(LooseEnergy.fromCoals(8f))
            .apply { hasCriterionInterface(this, LCCItems.tar_ball) }
            .apply { offerInterface(this, d) }
    }).add(ComplexRecipeFactory(LCCRecipeSerializers.polymerization, LCC.id("plastic"))) }
    val fuel_bucket by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(LCCItems.refined_oil_bucket, 2)
            .addOutput(i)
            .addOutput(Items.BUCKET)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.oil_cracking", 8, RefiningBlock.RefiningProcess.TREATING)
            .speed(3000, 0.008f, 400f)
            .energyPerOperation(LooseEnergy.fromCoals(12f))
            .apply { hasCriterionInterface(this, LCCItems.refined_oil_bucket) }
            .apply { offerInterface(this, d) }
    }) }
    val asphalt_bucket by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(LCCItems.tar_ball)
            .addInput(Blocks.GRAVEL, 8)
            .addInput(Blocks.SAND, 8)
            .addInput(Items.BUCKET)
            .addOutput(i)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.asphalt_mixing", 0, RefiningBlock.RefiningProcess.MIXING)
            .speed(6000, 0.012f, 400f)
            .energyPerOperation(LooseEnergy.fromCoals(6f))
            .apply { hasCriterionInterface(this, LCCItems.tar_ball) }
            .apply { offerInterface(this, d) }
    }) }

    val redstone_battery by entry(::initialiser) { data().defaultLang().add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.generated1.upload(i(t) ?: idi.loc(t), TextureMap().put(TextureKey.LAYER0, idi.loc(t)).put(LCCModelTextureKeys.layer1, idi.locSuffix(t, "overlay")), d.models) }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder(i, 1)
            .pattern("iei")
            .pattern("crc")
            .pattern("crc")
            .input('c', Items.COPPER_INGOT)
            .input('i', Items.IRON_INGOT)
            .input('e', Items.IRON_NUGGET)
            .input('r', Blocks.REDSTONE_BLOCK)
            .criterion("has_generator", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.generators).build()))
            .apply { offerShaped(this, d) }
    }) }

    val silicon by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(Blocks.SAND)
            .addInput(Items.COAL)
            .addOutput(i)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.arc", 3, RefiningBlock.RefiningProcess.ARC_SMELTING)
            .speed(600, 0.006f, 200f)
            .energyPerOperation(LooseEnergy.fromCoals(3f))
            .apply { hasCriterionInterface(this, LCCBlocks.refiner) }
            .apply { offerInterface(this, d) }
        RefiningShapelessRecipeJsonFactory()
            .addInput(Blocks.RED_SAND)
            .addInput(Items.COAL)
            .addOutput(i)
            .addOutput(i, 1, RefiningSimpleRecipe.OutputFunction.ChanceOutputFunction(0.2f))
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.arc", 3, RefiningBlock.RefiningProcess.ARC_SMELTING)
            .speed(650, 0.006f, 200f)
            .energyPerOperation(LooseEnergy.fromCoals(3.3f))
            .apply { hasCriterionInterface(this, LCCBlocks.refiner) }
            .apply { offerInterface(this, d, suffix(loc(i), "from_red_sand")) }
    }) }
    val turbine_blades by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("b b")
            .pattern(" c ")
            .pattern("b b")
            .input('b', Items.IRON_INGOT)
            .input('c', Items.IRON_NUGGET)
            .criterion("has_generator", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.generators).build()))
            .apply { offerShaped(this, d) }
    }) }
    val nuclear_fuel by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(LCCItems.enriched_uranium_nugget)
            .addInput(LCCItems.heavy_uranium_nugget, 2)
            .addOutput(i, 2)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.pellet_compression", 5, RefiningBlock.RefiningProcess.PRESSING)
            .speed(1200, 0.006f, 200f)
            .energyPerOperation(LooseEnergy.fromCoals(10f))
            .apply { hasCriterionInterface(this, LCCItems.heavy_uranium_nugget) }
            .apply { offerInterface(this, d) }
    }) }

    val flexible_rubber by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(LCCItems.latex_bottle, 4)
            .addOutput(i)
            .addOutput(Items.GLASS_BOTTLE, 4)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.dry", 4, RefiningBlock.RefiningProcess.DRYING)
            .speed(1200, 0.008f, 400f)
            .energyPerOperation(LooseEnergy.fromCoals(1f))
            .apply { hasCriterionInterface(this, LCCItems.latex_bottle) }
            .apply { offerInterface(this, d) }
    }) }
    val heavy_duty_rubber by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(SmeltFromItemRecipeFactory(LCCItems.flexible_rubber, RecipeSerializer.SMELTING, experience = 0.1f)) }

    val oxygen_tank by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("nin")
            .pattern("b b")
            .pattern("b b")
            .input('b', Items.GLASS_BOTTLE)
            .input('i', Items.IRON_INGOT)
            .input('n', Items.IRON_NUGGET)
            .criterion("has_suit", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LCCItemTags.airlocked_suits).build()))
            .apply { offerShaped(this, d) }
    }) }
    val hazmat_helmet by entry(::initialiser) { data().defaultLang().add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.generated1.upload(i(t) ?: idi.loc(t), TextureMap().put(TextureKey.LAYER0, idi.loc(t)).put(LCCModelTextureKeys.layer1, idi.locSuffix(t, "overlay")), d.models) }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("rrr")
            .pattern("rvr")
            .input('r', LCCItems.heavy_duty_rubber)
            .input('v', Blocks.GLASS_PANE)
            .apply { hasCriterionShaped(this, LCCItems.heavy_duty_rubber) }
            .criterion("has_radiation", EffectsChangedCriterion.Conditions.create(EntityEffectPredicate.create().withEffect(LCCEffects.radiation)))
            .apply { offerShaped(this, d) }
    }).add(ItemTagFactory(LCCItemTags.airlocked_suits)) }
    val hazmat_chestplate by entry(::initialiser) { data().defaultLang().add(CustomItemAssetFactory { d, t, i -> LCCModelTemplates.generated1.upload(i(t) ?: idi.loc(t), TextureMap().put(TextureKey.LAYER0, idi.loc(t)).put(LCCModelTextureKeys.layer1, idi.locSuffix(t, "overlay")), d.models) }).add(ComplexRecipeFactory(LCCRecipeSerializers.hazmat_chestplate)).add(ItemTagFactory(LCCItemTags.airlocked_suits)) }
    val hazmat_leggings by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("rrr")
            .pattern("r r")
            .pattern("r r")
            .input('r', LCCItems.heavy_duty_rubber)
            .apply { hasCriterionShaped(this, LCCItems.heavy_duty_rubber) }
            .criterion("has_radiation", EffectsChangedCriterion.Conditions.create(EntityEffectPredicate.create().withEffect(LCCEffects.radiation)))
            .apply { offerShaped(this, d) }
    }).add(ItemTagFactory(LCCItemTags.airlocked_suits)) }
    val hazmat_boots by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("r r")
            .pattern("r r")
            .input('r', LCCItems.heavy_duty_rubber)
            .apply { hasCriterionShaped(this, LCCItems.heavy_duty_rubber) }
            .criterion("has_radiation", EffectsChangedCriterion.Conditions.create(EntityEffectPredicate.create().withEffect(LCCEffects.radiation)))
            .apply { offerShaped(this, d) }
    }).add(ItemTagFactory(LCCItemTags.airlocked_suits)) }

    val salt by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        RefiningShapelessRecipeJsonFactory()
            .addInput(LCCBlocks.rock_salt)
            .addInput(Items.WATER_BUCKET)
            .addOutput(i)
            .addOutput(Items.BUCKET)
            .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
            .meta("container.lcc.refining.recipe.salt", 6, RefiningBlock.RefiningProcess.PURIFYING)
            .speed(300, 0.008f, 100f)
            .energyPerTick(5f)
            .apply { hasCriterionInterface(this, LCCBlocks.rock_salt) }
            .apply { offerInterface(this, d) }
    }) }
    val rubber_boat by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(BoatRecipeFactory(LCCBlocks.rubber_planks)) }
    val deadwood_boat by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(BoatRecipeFactory(LCCBlocks.deadwood_planks)) }

    val radiation_detector by entry(::initialiser) { data().defaultLang().add(RadiationDetectorItemAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("gt ")
            .pattern("ccl")
            .pattern("  i")
            .input('g', Blocks.GLASS)
            .input('t', LCCItems.tungsten_ingot)
            .input('c', Items.COPPER_INGOT)
            .input('l', Blocks.REDSTONE_LAMP)
            .input('i', Items.IRON_INGOT)
            .apply { hasCriterionShaped(this, LCCItems.tungsten_ingot) }
            .apply { offerShaped(this, d) }
    }) }

    val wasteland_spawn_eggs by entry(::initialiser) { data().affects(LCCItems.entries.values.filter { it.tags.contains("wasteland_spawn_egg") }.map { it.entry }).defaultLang().add(MultiLayerGeneratedItemAssetFactory({ LCC.id("item/wasteland_spawn_egg") }, { LCC.id("item/wasteland_spawn_egg_overlay") }, { LCC.id("item/wasteland_spawn_egg_goop") })) }

    val deadwood_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("deadwood_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCBlocks.deadwood_planks)).add(ItemTagFactory(LCCItemTags.wasteland_equipment)) }
    val fortstone_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("fortstone_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCBlocks.cobbled_fortstone)).add(ItemTagFactory(LCCItemTags.wasteland_equipment)) }
    val rusty_iron_equipment by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ToolItem && k.startsWith("rusty_iron_") }.values.toList()).defaultLang().add(HandheldItemAssetFactory).add(ToolRecipeFactory(LCCItems.iron_oxide)).add(ItemTagFactory(LCCItemTags.wasteland_equipment)) }
    val rusty_iron_armor by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.startsWith("rusty_iron_") }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.iron_oxide)).add(ItemTagFactory(LCCItemTags.wasteland_equipment)) }

    val crowbar by entry(::initialiser) { data().defaultLang().add(HandheldItemAssetFactory).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("ii ")
            .pattern(" i ")
            .pattern(" ii")
            .input('i', LCCItems.iron_oxide)
            .apply { hasCriterionShaped(this, LCCItems.iron_oxide) }
            .apply { offerShaped(this, d) }
    }) }

    val flexible_plastic by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ItemTagFactory(LCCItemTags.plastic)) }
    val rigid_plastic by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ItemTagFactory(LCCItemTags.plastic)) }

    val plastic_bag by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("p p")
            .pattern(" p ")
            .input('p', LCCItems.flexible_plastic)
            .apply { hasCriterionShaped(this, LCCItems.flexible_plastic) }
            .apply { offerShaped(this, d, override = LCCRecipeSerializers.plastic_shaped) }
    }) }

    val iron_oxide_nugget by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(Nugget9RecipeFactory(LCCItems.iron_oxide)) }
    val hearts by entry(::initialiser) { data().affects(LCCItems.all.values.filterIsInstance<HeartItem>()).defaultLang().defaultItemAsset().add(HeartRecipeFactory).add(HeartContainerRecipeFactory).add(HeartItemTagFactory).add(TransformTranslationFactory(*LCCData.lang.getLocales().toTypedArray()) { it.replace(" Full", "") }) }
    val altar_challenge_key by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("ii")
            .pattern("ii")
            .pattern("in")
            .input('i', LCCItems.iron_oxide)
            .input('n', LCCItems.iron_oxide_nugget)
            .apply { hasCriterionShaped(this, LCCItems.iron_oxide) }
            .apply { hasCriterionShaped(this, LCCItems.iron_oxide_nugget) }
            .apply { offerShaped(this, d) }
    }) }

    val enhancing_pyre_alpha by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ItemTagFactory(LCCItemTags.enhancing_pyre)) }
    val enhancing_pyre_beta by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ItemTagFactory(LCCItemTags.enhancing_pyre)).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern(" e ")
            .pattern("ese")
            .pattern(" e ")
            .input('e', LCCItems.enhancing_pyre_alpha)
            .input('s', LCCItems.sapphire)
            .apply { hasCriterionShaped(this, LCCItems.enhancing_pyre_alpha) }
            .apply { offerShaped(this, d, override = LCCRecipeSerializers.spawner_table_shaped) }
    }) }
    val enhancing_pyre_omega by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(ItemTagFactory(LCCItemTags.enhancing_pyre)).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern(" e ")
            .pattern("ene")
            .pattern(" e ")
            .input('e', LCCItems.enhancing_pyre_beta)
            .input('n', Items.NETHER_STAR)
            .apply { hasCriterionShaped(this, LCCItems.enhancing_pyre_beta) }
            .apply { hasCriterionShaped(this, Items.NETHER_STAR) }
            .apply { offerShaped(this, d, override = LCCRecipeSerializers.spawner_table_shaped) }
    }) }

    val knife by entry(::initialiser) { data().defaultLang().add(DurabilityItemAssetFactory({ c -> mi.handheld { idi.locSuffix(it, c.toString()) } }, doubleArrayOf(0.0, 0.0001, 0.3333, 0.6666))) }
    val calendar by entry(::initialiser) { data().defaultLang().add(DynamicItemAssetFactory(DynamicItemAssetFactory.item, "front")).add(CustomItemAssetFactory { d, t, i -> Models.GENERATED.upload(idi.locSuffix(t, "base"), TextureMap.layer0(idi.loc(t)), d.models) }).add(CustomRecipeFactory { d, i ->
        ShapedRecipeJsonBuilder.create(i)
            .pattern("ppp")
            .pattern("prp")
            .pattern("ppp")
            .input('p', Items.PAPER)
            .input('r', Items.REDSTONE)
            .apply { hasCriterionShaped(this, Items.REDSTONE) }
            .apply { offerShaped(this, d) }
    }) }

    val stinger by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ImbuingRecipeJsonFactory(Ingredient.ofItems(i), 30)
            .addEffect(StatusEffectInstance(StatusEffects.POISON, 100, 1))
            .apply { hasCriterionInterface(this, i) }
            .apply { offerInterface(this, d, LCC.id("stinger")) }
    }) }

    val magnetic_iron by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonBuilder.create(i, 2)
            .input(i)
            .input(LCCItems.iron_oxide)
            .apply { hasCriterionShapeless(this, i) }
            .apply { offerShapeless(this, d) }
    }) }

    val scroll_of_reconditioning by entry(::initialiser) { data().defaultLang().defaultItemAsset().add(CustomRecipeFactory { d, i ->
        ShapelessRecipeJsonBuilder.create(i)
            .input(LCCBlocks.forget_me_not)
            .input(LCCItems.enhancing_pyre_beta)
            .input(Items.GLOW_INK_SAC)
            .input(Items.PAPER)
            .apply { hasCriterionShapeless(this, LCCBlocks.forget_me_not) }
            .apply { offerShapeless(this, d) }
    }) }

    val woodlouse_armor by entry(::initialiser) { data().affects(LCCItems.all.filter { (k, v) -> v is ArmorItem && k.startsWith("woodlouse_") }.values.toList()).defaultLang().defaultItemAsset().add(ArmorRecipeFactory(LCCItems.woodlouse_shell)).add(ItemTagFactory(LCCItemTags.wasteland_equipment)) }

    fun initialiser(input: ItemDataContainer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out ItemDataContainer, out ItemDataContainer>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.init(it.name, LCCItems.getOrNull(it.name)) }

        val missing = LCCItems.all.values.minus(initialised.flatMap { it.entry.affects })
        missing.forEach { val key = LCCItems[it].name; defaults().init(key, it) }
    }

    private fun data() = ItemDataContainer(LCCData)
    private fun defaults() = data().defaultLang().defaultItemAsset()

    private fun ItemDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)
    private fun ItemDataContainer.defaultItemAsset() = add(GeneratedItemAssetFactory)

}