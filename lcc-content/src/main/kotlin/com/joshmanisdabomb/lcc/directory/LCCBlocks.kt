package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.color.ClassicDyeColor
import com.joshmanisdabomb.lcc.abstracts.color.ColorConstants
import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.abstracts.types.IronRustType
import com.joshmanisdabomb.lcc.abstracts.types.WoodType
import com.joshmanisdabomb.lcc.block.*
import com.joshmanisdabomb.lcc.block.DoorBlock
import com.joshmanisdabomb.lcc.block.PaneBlock
import com.joshmanisdabomb.lcc.block.PressurePlateBlock
import com.joshmanisdabomb.lcc.block.SaplingBlock
import com.joshmanisdabomb.lcc.block.StairsBlock
import com.joshmanisdabomb.lcc.block.TrapdoorBlock
import com.joshmanisdabomb.lcc.block.WoodenButtonBlock
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
import com.joshmanisdabomb.lcc.entity.ClassicTNTEntity
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.facade.piston.AbstractPistonHeadBlock
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import com.joshmanisdabomb.lcc.settings.BlockColorExtraSetting.Companion.blockColor
import com.joshmanisdabomb.lcc.settings.BlockExtraSettings
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.creativeEx
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.creativeExSet
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.sortValueFrom
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.sortValueInt
import com.joshmanisdabomb.lcc.settings.DynamicItemRenderExtraSetting.Companion.dynamicItemRender
import com.joshmanisdabomb.lcc.settings.FlammableExtraSetting.Companion.flammability
import com.joshmanisdabomb.lcc.settings.RenderLayerExtraSetting.Companion.cutout
import com.joshmanisdabomb.lcc.settings.RenderLayerExtraSetting.Companion.cutoutMipped
import com.joshmanisdabomb.lcc.settings.RenderLayerExtraSetting.Companion.translucent
import com.joshmanisdabomb.lcc.settings.StackColorExtraSetting.Companion.stackColor
import com.joshmanisdabomb.lcc.world.feature.tree.RubberSaplingGenerator
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.PressurePlateBlock.ActivationRule
import net.minecraft.block.enums.PistonType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.property.Properties
import net.minecraft.tag.BlockTags
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import kotlin.math.pow

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
    val ruby_ore by entry(::initialiser) { OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).requiresTool(), UniformIntProvider.create(3, 7)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby)))
    val ruby_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BRIGHT_RED).strength(5.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby)))
    val sapphire_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(5.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::sapphire)))

    val topaz_block by entry(::initialiser) { AmethystBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).strength(1.5f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::topaz_shard)))
    val topaz_cluster by entry(::initialiser) { AmethystClusterBlock(7, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).ticksRandomly().nonOpaque().strength(1.5f).luminance { 5 }.sounds(BlockSoundGroup.AMETHYST_CLUSTER)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(105)).cutout())
    val large_topaz_bud by entry(::initialiser) { AmethystClusterBlock(5, 3, FabricBlockSettings.copyOf(topaz_cluster).luminance { 4 }.sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(104)).cutout())
    val medium_topaz_bud by entry(::initialiser) { AmethystClusterBlock(4, 3, FabricBlockSettings.copyOf(topaz_cluster).luminance { 2 }.sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(103)).cutout())
    val small_topaz_bud by entry(::initialiser) { AmethystClusterBlock(3, 4, FabricBlockSettings.copyOf(topaz_cluster).luminance { 1 }.sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(102)).cutout())
    val budding_topaz by entry(::initialiser) { BuddingCrystalBlock(arrayOf(small_topaz_bud, medium_topaz_bud, large_topaz_bud, topaz_cluster), FabricBlockSettings.of(Material.AMETHYST, MapColor.TERRACOTTA_WHITE).requiresTool().ticksRandomly().strength(1.5f).sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueInt(101)))

    val tungsten_ore by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).requiresTool()) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::raw_tungsten, 1)))
    val deepslate_tungsten_ore by entry(::initialiser) { Block(FabricBlockSettings.copyOf(tungsten_ore).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES))
    val raw_tungsten_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE).strength(5.0f, 6.0f).requiresTool()) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::tungsten_ingot, 1)))
    val tungsten_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BRIGHT_TEAL).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES))
    val cut_tungsten by entry(::initialiser) { Block(FabricBlockSettings.copyOf(tungsten_block)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES))
    val cut_tungsten_stairs by entry(::initialiser) { StairsBlock(cut_tungsten.defaultState, FabricBlockSettings.copyOf(cut_tungsten)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES))
    val cut_tungsten_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(cut_tungsten)) }
        .setProperties(BlockExtraSettings().creativeEx(RESOURCES))

    //Building
    val pumice by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).sounds(BlockSoundGroup.POINTED_DRIPSTONE).requiresTool().strength(0.4f, 4.0f)) }
        .setProperties(BlockExtraSettings().creativeEx(BUILDING))
    val rhyolite by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.STONE).requiresTool().strength(1.0f, 10.0f)) }
        .setProperties(BlockExtraSettings().creativeEx(BUILDING))
    val rhyolite_stairs by entry(::initialiser) { StairsBlock(rhyolite.defaultState, FabricBlockSettings.copyOf(rhyolite)) }
        .setProperties(BlockExtraSettings().creativeEx(BUILDING))
    val rhyolite_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(rhyolite)) }
        .setProperties(BlockExtraSettings().creativeEx(BUILDING))
    val rhyolite_wall by entry(::initialiser) { WallBlock(FabricBlockSettings.copyOf(rhyolite)) }
        .setProperties(BlockExtraSettings().creativeEx(BUILDING))

    //Materials
    val rubber_block by entry(::initialiser) { RubberBlock(FabricBlockSettings.of(Material.SOIL, MapColor.BLACK).strength(2.0f).sounds(BlockSoundGroup.SHROOMLIGHT)) }
        .setProperties(BlockExtraSettings().creativeEx(MATERIALS, sortValueInt(50, 1)))
    val rock_salt by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(0.7f, 1.0f).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(MATERIALS, sortValueInt(150, 1)))
    val scattered_salt by entry(::initialiser) { SaltBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.WHITE).nonOpaque().breakInstantly().sounds(BlockSoundGroup.CAVE_VINES)) }
        .setProperties(BlockExtraSettings().creativeEx(MATERIALS))
    val salt_block by entry(::initialiser) { object : FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.2f).sounds(BlockSoundGroup.CAVE_VINES)) {
        override fun getColor(state: BlockState, world: BlockView, pos: BlockPos) = 0xFFF7F0
    } }.setProperties(BlockExtraSettings().creativeEx(MATERIALS))

    //Sap Production
    val treetap by entry(::initialiser) { TreetapBlock(FabricBlockSettings.of(Material.DECORATION).strength(1.5f, 0f).requiresTool().ticksRandomly().nonOpaque().sounds(BlockSoundGroup.CHAIN)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val treetap_bowl by entry(::initialiser) { object : TreetapStorageBlock(FabricBlockSettings.copyOf(treetap)) {
        override val container get() = TreetapContainer.BOWL
    } }
    val dried_treetap by entry(::initialiser) { DriedTreetapBlock(FabricBlockSettings.copyOf(treetap)) }
    val rubber_log by entry(::initialiser) { FunctionalLogBlock(FabricBlockSettings.copyOf(Settings.of(Material.WOOD, pillarMapColorProvider(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GREEN))).strength(2.0f).sounds(BlockSoundGroup.WOOD)) { stripped_rubber_log.defaultState.with(Properties.AXIS, it[Properties.AXIS]) } }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION, sortValueInt(100, 1)))
    val natural_rubber_log by entry(::initialiser) { SapLogBlock(AbstractTreetapBlock.TreetapLiquid.LATEX, FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0f).sounds(BlockSoundGroup.WOOD)) { sappy_stripped_rubber_log.defaultState } }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_wood by entry(::initialiser) { LogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_GREEN).strength(2.0f).sounds(BlockSoundGroup.WOOD)) { stripped_rubber_wood.defaultState.with(Properties.AXIS, it[Properties.AXIS]) } }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_planks by entry(::initialiser) { Block(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_sapling by entry(::initialiser) { SaplingBlock(RubberSaplingGenerator(), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION).cutout())
    val potted_rubber_sapling by entry(::initialiser) { FlowerPotBlock(rubber_sapling, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val rubber_leaves by entry(::initialiser) { FunctionalLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(::never).suffocates(::never).blockVision(::never)) { it.isIn(BlockTags.LOGS) } }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION).cutoutMipped().blockColor { _, _, _, _ -> ColorConstants.rubber_leaves }.stackColor { _, _ -> ColorConstants.rubber_leaves })
    val stripped_rubber_log by entry(::initialiser) { PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val sappy_stripped_rubber_log by entry(::initialiser) { SapBurstBlock(AbstractTreetapBlock.TreetapLiquid.LATEX, stripped_rubber_log.defaultState, FabricBlockSettings.copyOf(stripped_rubber_log)) { it.nextInt(10).plus(10) } }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val stripped_rubber_wood by entry(::initialiser) { PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_stairs by entry(::initialiser) { StairsBlock(rubber_planks.defaultState, FabricBlockSettings.copyOf(rubber_planks)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(rubber_planks)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_sign by entry(::initialiser) { LCCSignBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), LCCSignTypes.rubber) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_wall_sign by entry(::initialiser) { LCCWallSignBlock(FabricBlockSettings.copyOf(rubber_sign).dropsLike(rubber_sign), LCCSignTypes.rubber) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_door by entry(::initialiser) { DoorBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION).cutout())
    val rubber_pressure_plate by entry(::initialiser) { PressurePlateBlock(ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(0.5F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_button by entry(::initialiser) { WoodenButtonBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_fence by entry(::initialiser) { FenceBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_fence_gate by entry(::initialiser) { FenceGateBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION))
    val rubber_trapdoor by entry(::initialiser) { TrapdoorBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(::never)) }
        .setProperties(BlockExtraSettings().creativeEx(SAP_PRODUCTION).cutout())

    //Gizmos
    val asphalt by entry(::initialiser) { AsphaltBlock(LCCFluids.asphalt_still, Settings.copy(Blocks.WATER).strength(100.0F).ticksRandomly()) }
        .setProperties(BlockExtraSettings())
    val road by entry(::initialiser) { RoadBlock(Settings.of(Material.STONE, DyeColor.GRAY).requiresTool().strength(2.0F, 8.0F).sounds(BlockSoundGroup.TUFF)) }
        .setProperties(BlockExtraSettings())
    val soaking_soul_sand by entry(::initialiser) { SoakingSoulSandBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_BROWN).strength(0.75F, 2.5F).sounds(LCCSounds.soaking_soul_sand)) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))
    val bounce_pad by entry(::initialiser) { BouncePadBlock(FabricBlockSettings.of(Material.METAL, MapColor.GRAY).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.COPPER), doubleArrayOf(1.0, 1.4, 1.8, 2.2, 2.6)) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))
    val alarm by entry(::initialiser) { AlarmBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).requiresTool().strength(3.0f).sounds(BlockSoundGroup.ANVIL)) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))
    val radar by entry(::initialiser) { RadarBlock(FabricBlockSettings.of(Material.SCULK, MapColor.CYAN).strength(1.5f).sounds(BlockSoundGroup.SCULK_SENSOR).luminance(1).emissiveLighting { state, world, pos -> state[Properties.TRIGGERED] }) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS).cutout())
    val rubber_piston by entry(::initialiser) { RubberPistonBlock(false, FabricBlockSettings.of(Material.PISTON).strength(1.5f).solidBlock(::never).suffocates { state, _, _ -> state[Properties.EXTENDED] }.blockVision { state, _, _ -> state[Properties.EXTENDED] }) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))
    val rubber_piston_head by entry(::initialiser) { object : AbstractPistonHeadBlock(FabricBlockSettings.of(Material.PISTON).strength(1.5f).dropsNothing()) {
        override val bases: Map<PistonType, PistonBlock> by lazy { mapOf(PistonType.DEFAULT to rubber_piston) }
    } }
    val attractive_magnetic_iron_block by entry(::initialiser) { MagneticBlock(1.0, 5.0, FabricBlockSettings.of(Material.METAL, MapColor.DULL_PINK).strength(5.0f, 6.0f).requiresTool().sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))
    val repulsive_magnetic_iron_block by entry(::initialiser) { MagneticBlock(-1.0, 5.0, FabricBlockSettings.of(Material.METAL, MapColor.PALE_PURPLE).strength(5.0f, 6.0f).requiresTool().sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(GIZMOS))
    //TODO rope

    //Wasteland
    /*val mud by entry(::initialiser) { MudBlock(FabricBlockSettings.of(Material.SOIL, MapColor.BROWN).strength(0.3f, 3.5f).velocityMultiplier(0.5F).ticksRandomly().allowsSpawning(::always).solidBlock(::always).blockVision(::always).suffocates(::always).sounds(BlockSoundGroup.SLIME)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(0, 1)))*/
    val cracked_mud by entry(::initialiser) { HardeningBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(1.8F, 0.1F).requiresTool().sounds(BlockSoundGroup.STONE)) { Blocks.MUD.defaultState } }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val cracked_mud_pressure_plate by entry(::initialiser) { SprintPressurePlateBlock(FabricBlockSettings.copyOf(cracked_mud)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    val oil by entry(::initialiser) { OilBlock(LCCFluids.oil_still, Settings.copy(Blocks.WATER).strength(2.0F)) }
        .setProperties(BlockExtraSettings().flammability(3000, 300, Blocks.FIRE))

    val deposit by entry(::initialiser) { DepositBlock(FabricBlockSettings.of(Material.SOIL, MapColor.TERRACOTTA_BROWN).strength(1.0F, 3.0F).dynamicBounds().offsetType(AbstractBlock.OffsetType.XZ).sounds(BlockSoundGroup.FUNGUS)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(100, 1)).cutout())
    val infested_deposit by entry(::initialiser) { DepositBlock(FabricBlockSettings.of(Material.SOIL, MapColor.TERRACOTTA_LIGHT_GRAY).strength(1.0F, 3.0F).dynamicBounds().offsetType(AbstractBlock.OffsetType.XZ).sounds(BlockSoundGroup.FUNGUS)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())
    val luring_deposit by entry(::initialiser) { DepositBlock(FabricBlockSettings.of(Material.SOIL, MapColor.TERRACOTTA_BLACK).strength(1.0F, 3.0F).dynamicBounds().offsetType(AbstractBlock.OffsetType.XZ).sounds(BlockSoundGroup.FUNGUS)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())

    val sapphire_altar by entry(::initialiser) { SapphireAltarBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_BLUE).strength(9.0F, 18000000F).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(200, 1)).cutout())
    val sapphire_altar_brick by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_BLUE).strength(6.0f, 18000000f).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val sapphire_altar_brick_stairs by entry(::initialiser) { StairsBlock(sapphire_altar_brick.defaultState, FabricBlockSettings.copyOf(sapphire_altar_brick)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val sapphire_altar_brick_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(sapphire_altar_brick)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val sapphire_altar_brick_wall by entry(::initialiser) { WallBlock(FabricBlockSettings.copyOf(sapphire_altar_brick)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    val papercomb_block by entry(::initialiser) { PapercombBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.TERRACOTTA_WHITE).strength(4.0F, 0.0F).requiresTool().sounds(BlockSoundGroup.HANGING_ROOTS).suffocates(::never)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(90000, 1)).flammability(60, 70, Blocks.FIRE))
    val paper_envelope by entryMap(::initialiser, *WoodType.values()) { Block(FabricBlockSettings.of(Material.SOLID_ORGANIC, when (it) { WoodType.WARPED -> MapColor.DARK_AQUA; WoodType.CRIMSON -> MapColor.TERRACOTTA_MAGENTA; WoodType.DARK_OAK, WoodType.JUNGLE, WoodType.ACACIA -> MapColor.TERRACOTTA_WHITE; else -> MapColor.PALE_YELLOW }).requiresTool().strength(8.0F, 2.0F).sounds(BlockSoundGroup.HANGING_ROOTS)) }
        .setPropertySupplier { BlockExtraSettings().creativeEx(WASTELAND).flammability(60, 70, Blocks.FIRE) }

    val fortstone by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.GRAY).strength(7.0f, 1200.0f).requiresTool().sounds(BlockSoundGroup.BASALT)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(3000, 1)))
    val fortstone_stairs by entry(::initialiser) { StairsBlock(fortstone.defaultState, FabricBlockSettings.copyOf(fortstone)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val fortstone_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(fortstone)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val fortstone_wall by entry(::initialiser) { WallBlock(FabricBlockSettings.copyOf(fortstone)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val cobbled_fortstone by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.GRAY).strength(7.0f, 1200.0f).requiresTool().sounds(BlockSoundGroup.BASALT)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val cobbled_fortstone_stairs by entry(::initialiser) { StairsBlock(cobbled_fortstone.defaultState, FabricBlockSettings.copyOf(cobbled_fortstone)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val cobbled_fortstone_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(cobbled_fortstone)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val cobbled_fortstone_wall by entry(::initialiser) { WallBlock(FabricBlockSettings.copyOf(cobbled_fortstone)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val polished_fortstone by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.GRAY).strength(7.0f, 1200.0f).requiresTool().sounds(BlockSoundGroup.BASALT)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    val rusted_iron_blocks by entryMap(::initialiser, *IronRustType.values()) { WastelandRustingBlock(FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_BROWN).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.STONE)) { IronRustType.values().map { LCCBlocks[it.name.toLowerCase().plus("_iron_block")] }.toTypedArray() } }
        .setInstanceNameSupplier { _, k -> k.name.toLowerCase().plus("_iron_block") }
        .setPropertySupplier { (BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(4000+it.ordinal, 1))) }
    val rusted_iron_bars by entry(::initialiser) { PaneBlock(FabricBlockSettings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL).nonOpaque()) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutoutMipped())

    val spikes by entry(::initialiser) { SpikesBlock(FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_BROWN).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.STONE).nonOpaque()) { f, e -> f } }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val bleeding_spikes by entry(::initialiser) { SpikesBlock(FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_BROWN).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.STONE).nonOpaque()) { f, e -> f.pow(1.2f).coerceAtLeast(2.0f) } }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val poison_spikes by entry(::initialiser) { SpikesBlock(FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_BROWN).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.STONE).nonOpaque()) { f, e -> e.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, 170, 0)); f } }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    val explosive_paste by entry(::initialiser) { ExplosivePasteBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().emissiveLighting { state, world, pos -> state[Properties.LIT] }) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(5000, 1)).cutout())
    val improvised_explosive by entry(::initialiser) { ImprovisedExplosiveBlock(FabricBlockSettings.of(Material.TNT, MapColor.DIRT_BROWN).strength(4.0f, 0.0F).requiresTool().luminance { (it[ImprovisedExplosiveBlock.ie_state] != ImprovisedExplosiveBlock.ImprovisedExplosiveState.INACTIVE).transformInt(7) }.sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    val deadwood_log by entry(::initialiser) { FunctionalLogBlock(FabricBlockSettings.copyOf(Settings.of(Material.WOOD, pillarMapColorProvider(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GREEN))).strength(2.0f).sounds(BlockSoundGroup.WOOD)) { stripped_deadwood_log.defaultState.with(Properties.AXIS, it[Properties.AXIS]) } }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND, sortValueInt(2000, 1)))
    val deadwood by entry(::initialiser) { LogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_GREEN).strength(2.0f).sounds(BlockSoundGroup.WOOD)) { stripped_deadwood.defaultState.with(Properties.AXIS, it[Properties.AXIS]) } }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_planks by entry(::initialiser) { Block(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val stripped_deadwood_log by entry(::initialiser) { PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val stripped_deadwood by entry(::initialiser) { PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_stairs by entry(::initialiser) { StairsBlock(deadwood_planks.defaultState, FabricBlockSettings.copyOf(deadwood_planks)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(deadwood_planks)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_sign by entry(::initialiser) { LCCSignBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), LCCSignTypes.deadwood) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_wall_sign by entry(::initialiser) { LCCWallSignBlock(FabricBlockSettings.copyOf(deadwood_sign).dropsLike(deadwood_sign), LCCSignTypes.deadwood) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_door by entry(::initialiser) { DoorBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())
    val deadwood_pressure_plate by entry(::initialiser) { PressurePlateBlock(ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(0.5F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_button by entry(::initialiser) { WoodenButtonBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_fence by entry(::initialiser) { FenceBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_fence_gate by entry(::initialiser) { FenceGateBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val deadwood_trapdoor by entry(::initialiser) { TrapdoorBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(::never)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())

    val shattered_glass by entry(::initialiser) { ShatteredGlassBlock(Blocks.GLASS, FabricBlockSettings.of(Material.GLASS, MapColor.CLEAR).strength(0.0f, 0.0f).nonOpaque().allowsSpawning(::never).solidBlock(::never).suffocates(::never).blockVision(::never).sounds(BlockSoundGroup.GLASS)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())
        .addTags("shattered_glass")
    val shattered_glass_pane by entry(::initialiser) { ShatteredPaneBlock(Blocks.GLASS_PANE, FabricBlockSettings.of(Material.GLASS, MapColor.CLEAR).strength(0.0f, 0.0f).nonOpaque().sounds(BlockSoundGroup.GLASS)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutoutMipped())
        .addTags("shattered_glass_pane")
    val shattered_tinted_glass by entry(::initialiser) { object : ShatteredGlassBlock(Blocks.TINTED_GLASS, FabricBlockSettings.of(Material.GLASS, MapColor.GRAY).strength(0.0f, 0.0f).nonOpaque().allowsSpawning(::never).solidBlock(::never).suffocates(::never).blockVision(::never).sounds(BlockSoundGroup.GLASS)) {
        override fun isTranslucent(state: BlockState, world: BlockView, pos: BlockPos) = false
        override fun getOpacity(state: BlockState, world: BlockView, pos: BlockPos) = world.maxLightLevel
    } }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).translucent())
        .addTags("shattered_glass")
    val stained_shattered_glass by entryMap(::initialiser, *DyeColor.values()) { StainedShatteredGlassBlock(Registry.BLOCK[Identifier("${it}_stained_glass")] as StainedGlassBlock, FabricBlockSettings.of(Material.GLASS, it.mapColor).strength(0.0f, 0.0f).nonOpaque().allowsSpawning(::never).solidBlock(::never).suffocates(::never).blockVision(::never).sounds(BlockSoundGroup.GLASS)) }
        .setInstanceNameSupplier { n, k -> "shattered_${k}_stained_glass" }
        .setPropertySupplier { BlockExtraSettings().creativeExSet(WASTELAND, "stained_shattered_glass") { stack -> it as LCCExtendedDyeColor }.translucent() }
        .addTags("shattered_glass")
    val stained_shattered_glass_pane by entryMap(::initialiser, *DyeColor.values()) { StainedShatteredPaneBlock(Registry.BLOCK[Identifier("${it}_stained_glass_pane")] as StainedGlassPaneBlock, FabricBlockSettings.of(Material.GLASS, it.mapColor).strength(0.0f, 0.0f).nonOpaque().sounds(BlockSoundGroup.GLASS)) }
        .setInstanceNameSupplier { n, k -> "shattered_${k}_stained_glass_pane" }
        .setPropertySupplier { BlockExtraSettings().creativeExSet(WASTELAND, "stained_shattered_glass_pane") { stack -> it as LCCExtendedDyeColor }.translucent() }
        .addTags("shattered_glass_pane")

    val bomb_board_block by entry(::initialiser) { BombBoardBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_BROWN).strength(3.0f, 2.0f).requiresTool().allowsSpawning(::never).sounds(BlockSoundGroup.TUFF)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    val wasteland_obelisk by entry(::initialiser) { WastelandObeliskBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(1.8F, 5.0F).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    val three_leaf_clover by entry(::initialiser) { CloverBlock(StatusEffects.UNLUCK, 210, FabricBlockSettings.copyOf(Blocks.DANDELION).ticksRandomly(), stewDuration = 18) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())
    val potted_three_leaf_clover by entry(::initialiser) { FlowerPotBlock(three_leaf_clover, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val four_leaf_clover by entry(::initialiser) { CloverBlock(StatusEffects.LUCK, 210, FabricBlockSettings.copyOf(three_leaf_clover), stewDuration = 18) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())
    val potted_four_leaf_clover by entry(::initialiser) { FlowerPotBlock(four_leaf_clover, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val forget_me_not by entry(::initialiser) { FlowerBlock(StatusEffects.INVISIBILITY, 3, FabricBlockSettings.copyOf(Blocks.DANDELION)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())
    val potted_forget_me_not by entry(::initialiser) { FlowerPotBlock(forget_me_not, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())

    val enhancing_chamber by entry(::initialiser) { EnhancingChamberBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_GREEN).strength(2.0f).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))
    val imbuing_press by entry(::initialiser) { ImbuingPressBlock(FabricBlockSettings.of(Material.STONE, MapColor.GRAY).strength(7.0f, 1200.0f).requiresTool().sounds(BlockSoundGroup.BASALT)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND).cutout())

    val spawning_pit by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(1.8F, 0.1F).emissiveLighting(::always).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(WASTELAND))

    //TODO reinforced stone or similar for nuke protection

    //Nuclear
    val uranium_ore by entry(::initialiser) { RadioactiveBlock(2, 0, FabricBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).requiresTool()) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(0)))
    val deepslate_uranium_ore by entry(::initialiser) { RadioactiveBlock(2, 0, FabricBlockSettings.copyOf(uranium_ore).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(1)))
    val uranium_block by entry(::initialiser) { RadioactiveBlock(3, 0, FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(11)))
    val enriched_uranium_block by entry(::initialiser) { RadioactiveBlock(3, 1, FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(21)))
    val heavy_uranium_block by entry(::initialiser) { RadioactiveBlock(3, 0, FabricBlockSettings.of(Material.METAL, MapColor.GREEN).strength(5.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(31)))
    val heavy_uranium_shielding by entry(::initialiser) { RadioactiveShieldingBlock(6, 0, FabricBlockSettings.of(Material.METAL, MapColor.GREEN).strength(10.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.FUNGUS)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(35)))
    val nuclear_waste by entry(::initialiser) { NuclearWasteBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_CYAN).strength(-1f, 3600000f).sounds(BlockSoundGroup.CORAL).dropsNothing()) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR, sortValueInt(40, 1)))
    val nuclear_fire by entry(::initialiser) { NuclearFireBlock(FabricBlockSettings.of(Material.FIRE, MapColor.LIME).noCollision().breakInstantly().luminance { 15 }.ticksRandomly().sounds(BlockSoundGroup.WOOL)) }
        .setProperties(BlockExtraSettings().cutout())
    val atomic_bomb by entry(::initialiser) { AtomicBombBlock(FabricBlockSettings.of(Material.REPAIR_STATION, MapColor.IRON_GRAY).strength(9.0F, 1200.0F).requiresTool().sounds(BlockSoundGroup.ANVIL)) }
        .setProperties(BlockExtraSettings().creativeEx(NUCLEAR))

    //TODO spreaders

    //TODO computers

    //IDEA custom currency, mints to print money and coins, banks for converting to and from materials, wallets for storage (maybe work similar to bundles), credit cards for instant transfers

    //Crafters
    val spawner_table by entry(::initialiser) { DungeonTableBlock(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE).strength(20.0F, 50.0F).luminance(7).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(CRAFTERS))
    val kiln by entry(::initialiser) { KilnBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(3.5F, 10.5F).requiresTool()) }
        .setProperties(BlockExtraSettings().creativeEx(CRAFTERS))
    val refiner by entry(::initialiser) { RefinerBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).requiresTool().sounds(BlockSoundGroup.COPPER).luminance(RefiningBlock.Companion::brightness)) }
        .setProperties(BlockExtraSettings().creativeEx(CRAFTERS))
    val composite_processor by entry(::initialiser) { CompositeProcessorBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLACK).strength(10.0F, 10.0F).requiresTool().sounds(BlockSoundGroup.COPPER).luminance(RefiningBlock.Companion::brightness)) }
        .setProperties(BlockExtraSettings().creativeEx(CRAFTERS))

    //IDEA rainbow refiner (saturator?)

    //Power
    val machine_enclosure by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val power_cable by entry(::initialiser) { PowerCableBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(1.1F, 1.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val coal_generator by entry(::initialiser) { CoalFiredGeneratorBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val oil_generator by entry(::initialiser) { OilFiredGeneratorBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val nuclear_generator by entry(::initialiser) { NuclearFiredGeneratorBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(6.0F, 3.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val failing_nuclear_generator by entry(::initialiser) { ExplodingNuclearFiredGeneratorBlock(FabricBlockSettings.copyOf(nuclear_generator).luminance(15).dropsNothing()) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val turbine by entry(::initialiser) { TurbineBlock(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).requiresTool().strength(3.0f, 5.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER, sortValueInt(100, 1)))
    val solar_panel by entry(::initialiser) { SolarPanelBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(1.1F, 2.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val energy_bank by entry(::initialiser) { EnergyBankBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0F, 5.0F).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER, sortValueInt(5000)))
    val oxygen_extractor by entry(::initialiser) { OxygenExtractorBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(4.0f, 5.0f).requiresTool().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER))
    val power_source by entry(::initialiser) { PowerSourceBlock(FabricBlockSettings.of(Material.METAL, MapColor.CYAN).strength(-1.0F, 3600000f).dropsNothing().sounds(BlockSoundGroup.COPPER)) }
        .setProperties(BlockExtraSettings().creativeEx(POWER, sortValueInt(1000)))

    //Nostalgia
    val time_rift by entry(::initialiser) { TimeRiftBlock(Settings.of(Material.SOIL, MapColor.BLACK).strength(2.0F, 0.0F).sounds(BlockSoundGroup.WEEPING_VINES).noCollision().nonOpaque().solidBlock(::never).allowsSpawning(::never)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA, sortValueInt(-1)).dynamicItemRender { { TimeRiftBlockEntityRenderer(null) } })
    //IDEA time weaver, crafting table made of time rift, ruby blocks and something else - to allow taking blocks and items through time

    val classic_grass_block by entry(::initialiser) { ClassicGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LIME).strength(0.6f).ticksRandomly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA, sortValueInt(0, 1)))
    val dirt_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(Blocks.DIRT)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_cobblestone by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_cobblestone_stairs by entry(::initialiser) { StairsBlock(classic_cobblestone.defaultState, FabricBlockSettings.copyOf(classic_cobblestone)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_cobblestone_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(classic_cobblestone)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_planks by entry(::initialiser) { Block(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 5.0f).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_wooden_stairs by entry(::initialiser) { StairsBlock(classic_planks.defaultState, FabricBlockSettings.copyOf(classic_planks)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_wooden_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(classic_planks)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_leaves by entry(::initialiser) { FunctionalLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(::never).suffocates(::never).blockVision(::never)) { it.isOf(Blocks.OAK_LOG) } }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutoutMipped())
    val classic_sapling by entry(::initialiser) { ClassicSaplingBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val potted_classic_sapling by entry(::initialiser) { FlowerPotBlock(classic_sapling, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val classic_gravel by entry(::initialiser) { object : FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE_GRAY).strength(0.6f).sounds(BlockSoundGroup.GRAVEL)) {
        override fun getColor(state: BlockState, world: BlockView, pos: BlockPos) = 0x9C9193
    } }.setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_sponge by entry(::initialiser) { ClassicSpongeBlock(FabricBlockSettings.of(Material.SPONGE).strength(0.6f).sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_glass by entry(::initialiser) { GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(::never).solidBlock(::never).suffocates(::never).blockVision(::never)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val classic_cloth by entryMap(::initialiser, *ClassicDyeColor.values()) { Block(FabricBlockSettings.of(Material.WOOL, it.lcc_mapColor).strength(0.8f).sounds(BlockSoundGroup.WOOL)) }
        .setPropertySupplier { BlockExtraSettings().creativeExSet(NOSTALGIA, "classic_cloth") { stack -> it } }
    val classic_rose by entry(::initialiser) { FlowerBlock(StatusEffects.ABSORPTION, 4, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val potted_classic_rose by entry(::initialiser) { FlowerPotBlock(classic_rose, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val cyan_flower by entry(::initialiser) { FlowerBlock(StatusEffects.LEVITATION, 5, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    val potted_cyan_flower by entry(::initialiser) { FlowerPotBlock(cyan_flower, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
        .setProperties(BlockExtraSettings().cutout())
    val classic_iron_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val alpha_iron_block by entry(::initialiser) { Block(FabricBlockSettings.copyOf(classic_iron_block)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_gold_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val alpha_gold_block by entry(::initialiser) { Block(FabricBlockSettings.copyOf(classic_gold_block)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_diamond_block by entry(::initialiser) { Block(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val alpha_diamond_block by entry(::initialiser) { Block(FabricBlockSettings.copyOf(classic_diamond_block)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_bricks by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(2.0f).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_brick_stairs by entry(::initialiser) { StairsBlock(classic_bricks.defaultState, FabricBlockSettings.copyOf(classic_bricks)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_brick_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(classic_bricks)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_tnt by entry(::initialiser) { FunctionalTNTBlock(::ClassicTNTEntity, FabricBlockSettings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS), unstable = true) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_mossy_cobblestone by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_mossy_cobblestone_stairs by entry(::initialiser) { StairsBlock(classic_mossy_cobblestone.defaultState, FabricBlockSettings.copyOf(classic_mossy_cobblestone)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_mossy_cobblestone_slab by entry(::initialiser) { SlabBlock(FabricBlockSettings.copyOf(classic_mossy_cobblestone)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_chest by entry(::initialiser) { ClassicChestBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f).sounds(BlockSoundGroup.WOOD)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val nether_reactor by entry(::initialiser) { NetherReactorBlock(FabricBlockSettings.copyOf(Settings.of(Material.STONE, NetherReactorBlock::getMapColor)).requiresTool().strength(4.0f, 5.0f).sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val classic_crying_obsidian by entry(::initialiser) { ClassicCryingObsidianBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(50.0f, 1200.0f).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val glowing_obsidian by entry(::initialiser) { Block(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).strength(50.0f, 1200.0f).luminance(12).requiresTool().sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val pocket_stonecutter by entry(::initialiser) { ClassicStonecutterBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA))
    val cog by entry(::initialiser) { CogBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.IRON_GRAY).breakInstantly().noCollision().nonOpaque().solidBlock(::never).allowsSpawning(::never).sounds(BlockSoundGroup.METAL)) }
        .setProperties(BlockExtraSettings().creativeEx(NOSTALGIA).cutout())
    //IDEA locked chest, only found in parallel classic dimensions

    //TODO pill printer

    //TODO rainbow
    //IDEA shinestream, slippy passthrough block to gain speed
    //IDEA dash blocks, made from star plating and shinestream

}