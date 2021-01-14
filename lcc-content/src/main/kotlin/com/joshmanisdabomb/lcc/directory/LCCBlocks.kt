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
import net.minecraft.world.BlockView

object LCCBlocks : BlockDirectory() {

    override fun id(path: String) = LCC.id(path)

    //Test Blocks
    val test_block by create(BlockExtraSettings().creativeEx(TESTING)) { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_2 by create(BlockExtraSettings().creativeEx(TESTING)) { HorizontalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_3 by create(BlockExtraSettings().creativeEx(TESTING)) { DirectionalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_4 by create(BlockExtraSettings().creativeEx(TESTING)) { PillarBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_5 by create(BlockExtraSettings().creativeEx(TESTING)) { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }

    //Resources
    val ruby_ore by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby))) { OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(PICKAXES, 2), IntRange.between(3, 7)) }
    val ruby_block by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BRIGHT_RED).strength(5.0F, 6.0F).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
    val sapphire_block by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::sapphire))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(5.0F, 6.0F).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }

    val topaz_block by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::topaz_shard))) { AmethystBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).strength(1.5f).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
    val budding_topaz by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(101))) { BuddingCrystalBlock(arrayOf(small_topaz_bud, medium_topaz_bud, large_topaz_bud, topaz_cluster), FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).ticksRandomly().strength(1.5f).breakByHand(false).sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
    val topaz_cluster by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(105)).cutout()) { AmethystClusterBlock(7, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).breakByTool(PICKAXES, 2).ticksRandomly().nonOpaque().strength(1.5f).luminance {it.get(Properties.LIT).toInt(5)}.sounds(BlockSoundGroup.AMETHYST_CLUSTER)) }
    val large_topaz_bud by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(104)).cutout()) { AmethystClusterBlock(5, 3, FabricBlockSettings.copy(topaz_cluster).luminance {it.get(Properties.LIT).toInt(4)}.sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)) }
    val medium_topaz_bud by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(103)).cutout()) { AmethystClusterBlock(4, 3, FabricBlockSettings.copy(topaz_cluster).luminance {it.get(Properties.LIT).toInt(2)}.sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)) }
    val small_topaz_bud by create(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(102)).cutout()) { AmethystClusterBlock(3, 4, FabricBlockSettings.copy(topaz_cluster).luminance {it.get(Properties.LIT).toInt(1)}.sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)) }

    //Building
    val pumice by create(BlockExtraSettings().creativeEx(BUILDING)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).sounds(BlockSoundGroup.POINTED_DRIPSTONE).breakByTool(PICKAXES).requiresTool().strength(0.4f, 4.0f)) }
    val rhyolite by create(BlockExtraSettings().creativeEx(BUILDING)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.STONE).breakByTool(PICKAXES).requiresTool().strength(1.0f, 10.0f)) }

    //Gizmos
    val asphalt by create(BlockExtraSettings()) { AsphaltBlock(LCCFluids.asphalt_still, Settings.copy(Blocks.WATER).strength(100.0F).ticksRandomly()) }
    val road by create(BlockExtraSettings()) { RoadBlock(Settings.of(Material.STONE, DyeColor.GRAY).strength(2.0F, 8.0F).sounds(BlockSoundGroup.TUFF)) }
    val soaking_soul_sand by create(BlockExtraSettings().creativeEx(GIZMOS)) { SoakingSoulSandBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_BROWN).breakByTool(SHOVELS).strength(0.75F, 2.5F).sounds(LCCSounds.soaking_soul_sand)) }
    val bounce_pad by create(BlockExtraSettings().creativeEx(GIZMOS)) { BouncePadBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).breakByTool(PICKAXES, 2).strength(5.0F, 6.0F).sounds(BlockSoundGroup.COPPER), doubleArrayOf(1.0, 1.4, 1.8, 2.2, 2.6)) }

    //Wasteland
    val cracked_mud by create(BlockExtraSettings().creativeEx(WASTELAND)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(2.0F, 0.1F).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val oil by create(BlockExtraSettings().flammability(3000, 300, Blocks.FIRE)) { OilBlock(LCCFluids.oil_still, Settings.copy(Blocks.WATER).strength(2.0F)) }
    //IDEA deadwood, rarely spawns naturally or dries out wood
    //IDEA rusted iron blocks, first tier of wasteland tools

    //Nuclear
    val uranium_ore by create(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(0))) { Block(FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(PICKAXES, 3)) }
    val uranium_block by create(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(11))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }
    val enriched_uranium_block by create(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(21))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }
    val heavy_uranium_block by create(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(31))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.GREEN).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }

    //TODO atomic bomb

    //TODO spreaders

    //TODO computers

    //IDEA custom currency, mints to print money and coins, banks for converting to and from materials, wallets for storage (maybe work similar to bundles), credit cards for instant transfers

    //Crafters
    val spawner_table by create(BlockExtraSettings().creativeEx(CRAFTERS)) { DungeonTableBlock(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE).strength(20.0F, 50.0F).luminance(7).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
    //IDEA Kiln for faster smelting of non smokable or blastables
    val refiner by create(BlockExtraSettings().creativeEx(CRAFTERS)) { RefinerBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).breakByTool(PICKAXES, 1).requiresTool().sounds(BlockSoundGroup.COPPER).luminance(RefiningBlock.Companion::brightness)) }
    val composite_processor by create(BlockExtraSettings().creativeEx(CRAFTERS)) { CompositeProcessorBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLACK).strength(10.0F, 10.0F).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.COPPER).luminance(RefiningBlock.Companion::brightness)) }

    //IDEA rainbow refiner (saturator?)

    //Power
    val power_cable by create(BlockExtraSettings().creativeEx(POWER)) { PowerCableBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(1.1F, 1.0F).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.COPPER)) }
    val solar_panel by create(BlockExtraSettings().creativeEx(POWER)) { SolarPanelBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(4.0F, 2.0F).breakByTool(PICKAXES, 1).requiresTool().sounds(BlockSoundGroup.COPPER)) }
    val power_source by create(BlockExtraSettings().creativeEx(POWER)) { PowerSourceBlock(FabricBlockSettings.of(Material.METAL, MapColor.CYAN).strength(-1.0F, 3600000f).dropsNothing().sounds(BlockSoundGroup.COPPER)) }

    //Nostalgia
    val time_rift by create(BlockExtraSettings().creativeEx(NOSTALGIA, sortValueInt(-1)).dynamicItemRender { ::TimeRiftBlockEntityRenderer }) { TimeRiftBlock(Settings.of(Material.SOIL, MapColor.BLACK).strength(2.0F, 0.0F).sounds(BlockSoundGroup.WEEPING_VINES).noCollision().nonOpaque().solidBlock(::never).allowsSpawning(::never)) }
    //IDEA time weaver, crafting table made of time rift, ruby blocks and something else - to allow taking blocks and items through time

    val classic_grass_block by create(BlockExtraSettings().creativeEx(NOSTALGIA, sortValueInt(0, 1))) { ClassicGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LIME).strength(0.6f).breakByTool(SHOVELS).ticksRandomly().sounds(BlockSoundGroup.GRASS)) }
    val classic_cobblestone by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0f, 6.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val classic_planks by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 5.0f).breakByTool(AXES).sounds(BlockSoundGroup.WOOD)) }
    val classic_leaves by create(BlockExtraSettings().creativeEx(NOSTALGIA).cutoutMipped()) { FunctionalLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2f).breakByTool(HOES).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(::never).suffocates(::never).blockVision(::never)) { it.isOf(Blocks.OAK_LOG) } }
    val classic_sapling by create(BlockExtraSettings().creativeEx(NOSTALGIA).cutout()) { ClassicSaplingBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
    val potted_classic_sapling by create(BlockExtraSettings().cutout()) { FlowerPotBlock(classic_sapling, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
    val classic_gravel by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { object : FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE_GRAY).breakByTool(SHOVELS).strength(0.6f).sounds(BlockSoundGroup.GRAVEL)) {
        override fun getColor(state: BlockState, world: BlockView, pos: BlockPos) = 0x9C9193
    } }
    val classic_sponge by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { ClassicSpongeBlock(FabricBlockSettings.of(Material.SPONGE).breakByTool(HOES).strength(0.6f).sounds(BlockSoundGroup.GRASS)) }
    val classic_glass by create(BlockExtraSettings().creativeEx(NOSTALGIA).cutout()) { GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(::never).solidBlock(::never).suffocates(::never).blockVision(::never)) }
    val classic_cloth by createMap(*ClassicDyeColor.values(), propertySupplier = { BlockExtraSettings().creativeExSet(NOSTALGIA, "classic_cloth") { stack -> it } }) { key, name, properties -> Block(FabricBlockSettings.of(Material.WOOL, key.lcc_mapColor).breakByTool(SHEARS).strength(0.8f).sounds(BlockSoundGroup.WOOL)) }
    val classic_rose by create(BlockExtraSettings().creativeEx(NOSTALGIA).cutout()) { FlowerBlock(StatusEffects.ABSORPTION, 4, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
    val potted_classic_rose by create(BlockExtraSettings().cutout()) { FlowerPotBlock(classic_rose, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
    val cyan_flower by create(BlockExtraSettings().creativeEx(NOSTALGIA).cutout()) { FlowerBlock(StatusEffects.LEVITATION, 5, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
    val potted_cyan_flower by create(BlockExtraSettings().cutout()) { FlowerPotBlock(cyan_flower, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
    val classic_iron_block by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).breakByTool(PICKAXES, 1).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
    val alpha_iron_block by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.copy(classic_iron_block)) }
    val classic_gold_block by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).breakByTool(PICKAXES, 2).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
    val alpha_gold_block by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.copy(classic_gold_block)) }
    val classic_diamond_block by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE).breakByTool(PICKAXES, 2).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
    val alpha_diamond_block by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.copy(classic_diamond_block)) }
    val classic_bricks by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(2.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val classic_tnt by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { FunctionalTNTBlock(::ClassicTNTEntity, FabricBlockSettings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS), unstable = true) }
    val classic_mossy_cobblestone by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0f, 6.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val classic_chest by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { ClassicChestBlock(FabricBlockSettings.of(Material.WOOD).breakByTool(AXES).strength(2.5f).sounds(BlockSoundGroup.WOOD)) }
    val nether_reactor by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { NetherReactorBlock(FabricBlockSettings.copyOf(Settings.of(Material.STONE, NetherReactorBlock::getMapColor)).breakByTool(PICKAXES).requiresTool().strength(4.0f, 5.0f).sounds(BlockSoundGroup.STONE)) }
    val classic_crying_obsidian by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { ClassicCryingObsidianBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(50.0f, 1200.0f).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val glowing_obsidian by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).strength(50.0f, 1200.0f).luminance(12).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val pocket_stonecutter by create(BlockExtraSettings().creativeEx(NOSTALGIA)) { ClassicStonecutterBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).breakByTool(PICKAXES).requiresTool().strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)) }
    val cog by create(BlockExtraSettings().creativeEx(NOSTALGIA).cutout()) { CogBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.IRON_GRAY).breakInstantly().noCollision().nonOpaque().solidBlock(::never).allowsSpawning(::never).sounds(BlockSoundGroup.METAL)) }
    //IDEA locked chest, only found in parallel classic dimensions

    //TODO pill printer

    //TODO rainbow
    //IDEA shinestream, slippy passthrough block to gain speed
    //IDEA dash blocks, made from star plating and shinestream

}