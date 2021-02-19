package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.color.ClassicDyeColor
import com.joshmanisdabomb.lcc.block.*
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
import com.joshmanisdabomb.lcc.entity.ClassicTNTEntity
import com.joshmanisdabomb.lcc.extensions.toInt
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import com.joshmanisdabomb.lcc.settings.BlockExtraSettings
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.creativeEx
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.creativeExSet
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.sortValueFrom
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.sortValueInt
import com.joshmanisdabomb.lcc.settings.DynamicItemRenderExtraSetting.Companion.dynamicItemRender
import com.joshmanisdabomb.lcc.settings.FlammableExtraSetting.Companion.flammability
import com.joshmanisdabomb.lcc.settings.RenderLayerExtraSetting.Companion.cutout
import com.joshmanisdabomb.lcc.settings.RenderLayerExtraSetting.Companion.cutoutMipped
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags.*
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.property.Properties
import net.minecraft.util.DyeColor
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.IntRange
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView

object LCCBlocks : BlockDirectory() {

    override fun regId(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = BlockExtraSettings()

    override val registry by lazy { Registry.BLOCK }

    //Test Blocks
    val test_block by entry(::initialiser) { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
        .setProperties(BlockExtraSettings().creativeEx(TESTING))
    val test_block_2 by entry(::initialiser) { HorizontalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
        .setProperties(BlockExtraSettings().creativeEx(TESTING))
    val test_block_3 by entry(::initialiser) { DirectionalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
        .setProperties(BlockExtraSettings().creativeEx(TESTING))
    val test_block_4 by entry(::initialiser) { PillarBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
        .setProperties(BlockExtraSettings().creativeEx(TESTING))
    val test_block_5 by entry(::initialiser) { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
        .setProperties(BlockExtraSettings().creativeEx(TESTING))

    //Resources
    val ruby_ore by entry(::initialiser) { OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(PICKAXES, 2), IntRange.between(3, 7)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby)))
    val ruby_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BRIGHT_RED).strength(5.0F, 6.0F).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby)))
    val sapphire_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(5.0F, 6.0F).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::sapphire)))

    val topaz_block by entry(::initialiser) { AmethystBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).strength(1.5f).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::topaz_shard)))
    val topaz_cluster by entry(::initialiser) { AmethystClusterBlock(7, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).breakByTool(PICKAXES, 2).ticksRandomly().nonOpaque().strength(1.5f).luminance { it[Properties.LIT].toInt(5) }.sounds(BlockSoundGroup.AMETHYST_CLUSTER)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(105)).cutout())
    val large_topaz_bud by entry(::initialiser) { AmethystClusterBlock(5, 3, FabricBlockSettings.copy(topaz_cluster).luminance { it[Properties.LIT].toInt(4) }.sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(104)).cutout())
    val medium_topaz_bud by entry(::initialiser) { AmethystClusterBlock(4, 3, FabricBlockSettings.copy(topaz_cluster).luminance { it[Properties.LIT].toInt(2) }.sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(103)).cutout())
    val small_topaz_bud by entry(::initialiser) { AmethystClusterBlock(3, 4, FabricBlockSettings.copy(topaz_cluster).luminance { it[Properties.LIT].toInt(1) }.sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(102)).cutout())
    val budding_topaz by entry(::initialiser) { BuddingCrystalBlock(arrayOf(small_topaz_bud, medium_topaz_bud, large_topaz_bud, topaz_cluster), FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).ticksRandomly().strength(1.5f).breakByHand(false).sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(101)))

    //Building
    val pumice by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).sounds(BlockSoundGroup.POINTED_DRIPSTONE).breakByTool(PICKAXES).requiresTool().strength(0.4f, 4.0f)) }
        .setProperties(BlockExtraSettings().creativeEx(BUILDING))
    val rhyolite by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.STONE).breakByTool(PICKAXES).requiresTool().strength(1.0f, 10.0f)) }
        .setProperties(BlockExtraSettings().creativeEx(BUILDING))

    //Gizmos
    val asphalt by entry(::initialiser) { AsphaltBlock(LCCFluids.asphalt_still, Settings.copy(Blocks.WATER).strength(100.0F).ticksRandomly()) }
        .setProperties(BlockExtraSettings())
    val road by entry(::initialiser) { RoadBlock(Settings.of(Material.STONE, DyeColor.GRAY).strength(2.0F, 8.0F).sounds(BlockSoundGroup.TUFF)) }
        .setProperties(BlockExtraSettings())
    val soaking_soul_sand by entry(::initialiser) { SoakingSoulSandBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_BROWN).breakByTool(SHOVELS).strength(0.75F, 2.5F).sounds(LCCSounds.soaking_soul_sand)) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))
    val bounce_pad by entry(::initialiser) { BouncePadBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).breakByTool(PICKAXES, 2).strength(5.0F, 6.0F).sounds(BlockSoundGroup.COPPER), doubleArrayOf(1.0, 1.4, 1.8, 2.2, 2.6)) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))

    //Wasteland
    val cracked_mud by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(2.0F, 0.1F).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val oil by entry(::initialiser) { OilBlock(LCCFluids.oil_still, Settings.copy(Blocks.WATER).strength(2.0F)) }
        .setProperties(BlockExtraSettings().flammability(3000, 300, Blocks.FIRE))
    //IDEA deadwood, rarely spawns naturally or dries out wood
    //IDEA rusted iron blocks, first tier of wasteland tools
    //TODO minesweep blocks
    //TODO fortstone
    //TODO reinforced stone or similar for nuke protection

    //Nuclear
    val uranium_ore by entry(::initialiser) { RadioactiveBlock(2, 0, FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(PICKAXES, 3)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(0)))
    val uranium_block by entry(::initialiser) { RadioactiveBlock(3, 0, FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(11)))
    val enriched_uranium_block by entry(::initialiser) { RadioactiveBlock(3, 1, FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(21)))
    val heavy_uranium_block by entry(::initialiser) { RadioactiveBlock(3, 0, FabricBlockSettings.of(Material.METAL, MapColor.GREEN).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(31)))
    val nuclear_waste by entry(::initialiser) { NuclearWasteBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_CYAN).strength(-1f, 3600000f).sounds(BlockSoundGroup.CORAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(40, 1)))
    val nuclear_fire by entry(::initialiser) { NuclearFireBlock(FabricBlockSettings.of(Material.FIRE, MapColor.LIME).noCollision().breakInstantly().luminance { 15 }.ticksRandomly().sounds(BlockSoundGroup.WOOL)) }
        .setProperties(BlockExtraSettings().cutout())
    val atomic_bomb by entry(::initialiser) { AtomicBombBlock(FabricBlockSettings.of(Material.REPAIR_STATION, MapColor.IRON_GRAY).strength(9.0F, 1200.0F).breakByTool(PICKAXES, 1).requiresTool().sounds(BlockSoundGroup.ANVIL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR))

    //TODO spreaders

    //TODO computers

    //IDEA custom currency, mints to print money and coins, banks for converting to and from materials, wallets for storage (maybe work similar to bundles), credit cards for instant transfers

    //Crafters
    val spawner_table by entry(::initialiser) { DungeonTableBlock(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE).strength(20.0F, 50.0F).luminance(7).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(CRAFTERS))
    //IDEA Kiln for faster smelting of non smokable or blastables
    val refiner by entry(::initialiser) { RefinerBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).breakByTool(PICKAXES, 1).requiresTool().sounds(BlockSoundGroup.COPPER).luminance(RefiningBlock.Companion::brightness)) }
        .setProperties(BlockExtraSettings().creativeEx(CRAFTERS))
    val composite_processor by entry(::initialiser) { CompositeProcessorBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLACK).strength(10.0F, 10.0F).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.COPPER).luminance(RefiningBlock.Companion::brightness)) }
        .setProperties(BlockExtraSettings().creativeEx(CRAFTERS))

    //IDEA rainbow refiner (saturator?)

    //Power
    val power_cable by entry(::initialiser) { PowerCableBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(1.1F, 1.0F).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val coal_generator by entry(::initialiser) { CoalFiredGeneratorBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val oil_generator by entry(::initialiser) { OilFiredGeneratorBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val turbine by entry(::initialiser) { TurbineBlock(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).breakByTool(PICKAXES, 1).requiresTool().strength(3.0f, 5.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val solar_panel by entry(::initialiser) { SolarPanelBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(1.1F, 2.0F).breakByTool(PICKAXES, 1).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val energy_bank by entry(::initialiser) { EnergyBankBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).breakByTool(PICKAXES, 1).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val power_source by entry(::initialiser) { PowerSourceBlock(FabricBlockSettings.of(Material.METAL, MapColor.CYAN).strength(-1.0F, 3600000f).dropsNothing().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER, sortValueInt(1000)))

    //Nostalgia
    val time_rift by entry(::initialiser) { TimeRiftBlock(Settings.of(Material.SOIL, MapColor.BLACK).strength(2.0F, 0.0F).sounds(BlockSoundGroup.WEEPING_VINES).noCollision().nonOpaque().solidBlock(::never).allowsSpawning(::never)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA, sortValueInt(-1)).dynamicItemRender { ::TimeRiftBlockEntityRenderer })
    //IDEA time weaver, crafting table made of time rift, ruby blocks and something else - to allow taking blocks and items through time

    val classic_grass_block by entry(::initialiser) { ClassicGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LIME).strength(0.6f).breakByTool(SHOVELS).ticksRandomly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA, sortValueInt(0, 1)))
    val classic_cobblestone by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0f, 6.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_planks by entry(::initialiser) { Block(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 5.0f).breakByTool(AXES).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_leaves by entry(::initialiser) { FunctionalLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2f).breakByTool(HOES).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(::never).suffocates(::never).blockVision(::never)) { it.isOf(Blocks.OAK_LOG) } }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutoutMipped())
    val classic_sapling by entry(::initialiser) { ClassicSaplingBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val potted_classic_sapling by entry(::initialiser) { FlowerPotBlock(classic_sapling, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val classic_gravel by entry(::initialiser) { object : FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE_GRAY).breakByTool(SHOVELS).strength(0.6f).sounds(BlockSoundGroup.GRAVEL)) {
        override fun getColor(state: BlockState, world: BlockView, pos: BlockPos) = 0x9C9193
    } }.setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_sponge by entry(::initialiser) { ClassicSpongeBlock(FabricBlockSettings.of(Material.SPONGE).breakByTool(HOES).strength(0.6f).sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_glass by entry(::initialiser) { GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(::never).solidBlock(::never).suffocates(::never).blockVision(::never)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val classic_cloth by entryMap(::initialiser, *ClassicDyeColor.values()) { Block(FabricBlockSettings.of(Material.WOOL, it.lcc_mapColor).breakByTool(SHEARS).strength(0.8f).sounds(BlockSoundGroup.WOOL)) }
        .setPropertySupplier { BlockExtraSettings().creativeExSet(NOSTALGIA, "classic_cloth") { stack -> it } }
    val classic_rose by entry(::initialiser) { FlowerBlock(StatusEffects.ABSORPTION, 4, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val potted_classic_rose by entry(::initialiser) { FlowerPotBlock(classic_rose, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val cyan_flower by entry(::initialiser) { FlowerBlock(StatusEffects.LEVITATION, 5, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val potted_cyan_flower by entry(::initialiser) { FlowerPotBlock(cyan_flower, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val classic_iron_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).breakByTool(PICKAXES, 1).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val alpha_iron_block by entry(::initialiser) { Block(FabricBlockSettings.copy(classic_iron_block)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_gold_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).breakByTool(PICKAXES, 2).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val alpha_gold_block by entry(::initialiser) { Block(FabricBlockSettings.copy(classic_gold_block)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_diamond_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE).breakByTool(PICKAXES, 2).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val alpha_diamond_block by entry(::initialiser) { Block(FabricBlockSettings.copy(classic_diamond_block)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_bricks by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(2.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_tnt by entry(::initialiser) { FunctionalTNTBlock(::ClassicTNTEntity, FabricBlockSettings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS), unstable = true) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_mossy_cobblestone by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0f, 6.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_chest by entry(::initialiser) { ClassicChestBlock(FabricBlockSettings.of(Material.WOOD).breakByTool(AXES).strength(2.5f).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val nether_reactor by entry(::initialiser) { NetherReactorBlock(FabricBlockSettings.copyOf(Settings.of(Material.STONE, NetherReactorBlock::getMapColor)).breakByTool(PICKAXES).requiresTool().strength(4.0f, 5.0f).sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_crying_obsidian by entry(::initialiser) { ClassicCryingObsidianBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(50.0f, 1200.0f).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val glowing_obsidian by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).strength(50.0f, 1200.0f).luminance(12).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val pocket_stonecutter by entry(::initialiser) { ClassicStonecutterBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).breakByTool(PICKAXES).requiresTool().strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val cog by entry(::initialiser) { CogBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.IRON_GRAY).breakInstantly().noCollision().nonOpaque().solidBlock(::never).allowsSpawning(::never).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    //IDEA locked chest, only found in parallel classic dimensions

    //TODO pill printer

    //TODO rainbow
    //IDEA shinestream, slippy passthrough block to gain speed
    //IDEA dash blocks, made from star plating and shinestream

}