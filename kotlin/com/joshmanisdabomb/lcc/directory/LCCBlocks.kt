package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.creativeex.CreativeExCategory
import com.joshmanisdabomb.creativeex.CreativeExSetKey
import com.joshmanisdabomb.lcc.abstracts.color.ClassicDyeColor
import com.joshmanisdabomb.lcc.block.*
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
import com.joshmanisdabomb.lcc.directory.LCCBlocks.ExtraSettings.Companion.sortValueDefault
import com.joshmanisdabomb.lcc.directory.LCCBlocks.ExtraSettings.Companion.sortValueFrom
import com.joshmanisdabomb.lcc.directory.LCCBlocks.ExtraSettings.Companion.sortValueInt
import com.joshmanisdabomb.lcc.extensions.toInt
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags.*
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.entity.EntityType
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.DyeColor
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction.Axis
import net.minecraft.util.math.IntRange
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import kotlin.reflect.KProperty0

object LCCBlocks : RegistryDirectory<Block, LCCBlocks.ExtraSettings>() {

    override val _registry by lazy { Registry.BLOCK }

    //Test Blocks
    val test_block by create(ExtraSettings().creativeEx(TESTING)) { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_2 by create(ExtraSettings().creativeEx(TESTING)) { HorizontalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_3 by create(ExtraSettings().creativeEx(TESTING)) { DirectionalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_4 by create(ExtraSettings().creativeEx(TESTING)) { PillarBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_5 by create(ExtraSettings().creativeEx(TESTING)) { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }

    //Resources
    val ruby_ore by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby))) { OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(PICKAXES, 2), IntRange.between(3, 7)) }
    val ruby_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LAVA).strength(5.0F, 6.0F).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
    val sapphire_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::sapphire))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(5.0F, 6.0F).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
    val uranium_ore by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::uranium))) { OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(PICKAXES, 3), IntRange.between(3, 7)) }
    val uranium_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::uranium))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }
    val enriched_uranium_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::enriched_uranium))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }

    val topaz_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::topaz_shard))) { AmethystBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.WHITE_TERRACOTTA).strength(1.5f).breakByTool(PICKAXES, 2).sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
    val budding_topaz by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(101))) { BuddingCrystalBlock(arrayOf(small_topaz_bud, medium_topaz_bud, large_topaz_bud, topaz_cluster), FabricBlockSettings.of(Material.AMETHYST, MapColor.WHITE_TERRACOTTA).ticksRandomly().strength(1.5f).breakByHand(false).sounds(BlockSoundGroup.AMETHYST_BLOCK)) }
    val topaz_cluster by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(105)).renderCutout()) { AmethystClusterBlock(7, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.WHITE_TERRACOTTA).breakByTool(PICKAXES, 2).ticksRandomly().nonOpaque().strength(1.5f).luminance {it.get(Properties.LIT).toInt(5)}.sounds(BlockSoundGroup.AMETHYST_CLUSTER)) }
    val large_topaz_bud by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(104)).renderCutout()) { AmethystClusterBlock(5, 3, FabricBlockSettings.copy(topaz_cluster).luminance {it.get(Properties.LIT).toInt(4)}.sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)) }
    val medium_topaz_bud by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(103)).renderCutout()) { AmethystClusterBlock(4, 3, FabricBlockSettings.copy(topaz_cluster).luminance {it.get(Properties.LIT).toInt(2)}.sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)) }
    val small_topaz_bud by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(102)).renderCutout()) { AmethystClusterBlock(3, 4, FabricBlockSettings.copy(topaz_cluster).luminance {it.get(Properties.LIT).toInt(1)}.sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)) }

    //Building
    val pumice by create(ExtraSettings().creativeEx(BUILDING)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.LIGHT_GRAY_TERRACOTTA).sounds(BlockSoundGroup.POINTED_DRIPSTONE).breakByTool(PICKAXES).requiresTool().strength(0.4f, 4.0f)) }
    val rhyolite by create(ExtraSettings().creativeEx(BUILDING)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.STONE).breakByTool(PICKAXES).requiresTool().strength(1.0f, 10.0f)) }

    //Gizmos
    val asphalt by create(ExtraSettings()) { AsphaltBlock(LCCFluids.asphalt_still, Settings.copy(Blocks.WATER).strength(100.0F).ticksRandomly()) }
    val road by create(ExtraSettings()) { RoadBlock(Settings.of(Material.STONE, DyeColor.GRAY).strength(2.0F, 8.0F).sounds(BlockSoundGroup.TUFF)) }
    val soaking_soul_sand by create(ExtraSettings().creativeEx(GIZMOS)) { SoakingSoulSandBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.BROWN_TERRACOTTA).breakByTool(SHOVELS).strength(0.75F, 2.5F).sounds(LCCSounds.soaking_soul_sand)) }
    val bounce_pad by create(ExtraSettings().creativeEx(GIZMOS)) { BouncePadBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).breakByTool(PICKAXES, 2).strength(5.0F, 6.0F).sounds(BlockSoundGroup.COPPER), doubleArrayOf(1.0, 1.4, 1.8, 2.2, 2.6)) }

    //Wasteland
    val cracked_mud by create(ExtraSettings().creativeEx(WASTELAND)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.WHITE_TERRACOTTA).strength(2.0F, 0.1F).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val oil by create(ExtraSettings().flammable(3000, 300, Blocks.FIRE)) { OilBlock(LCCFluids.oil_still, Settings.copy(Blocks.WATER).strength(2.0F)) }
    //IDEA deadwood, rarely spawns naturally or dries out wood
    //IDEA rusted iron blocks, first tier of wasteland tools

    //TODO atomic bomb

    //TODO spreaders

    //TODO computers

    //IDEA custom currency, mints to print money and coins, banks for converting to and from materials, wallets for storage (maybe work similar to bundles), credit cards for instant transfers

    //Crafters
    val spawner_table by create(ExtraSettings().creativeEx(CRAFTERS)) { DungeonTableBlock(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE).strength(20.0F, 50.0F).luminance(7).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
    //IDEA uranium refiner, oil refiner -> (fuel, asphalt, plastic), rainbow refiner (saturator?), universal refiner
    //IDEA Kiln for faster smelting of non smokable or blastables

    //Nostalgia TODO
    val time_rift by create(ExtraSettings().creativeEx(NOSTALGIA).dynamicItemRender { ::TimeRiftBlockEntityRenderer }) { TimeRiftBlock(Settings.of(Material.SOIL, MapColor.BLACK).strength(0.0F).sounds(BlockSoundGroup.WEEPING_VINES).noCollision().nonOpaque().solidBlock(::never).allowsSpawning(::never)) }
    //IDEA time rift, basic method of taking blocks back in time to notable versions (4 ancient debris, 4 clocks, 1 sim fabric)
    //IDEA time weaver, crafting table made of time rift, ruby blocks and something else - to allow taking blocks and items through time

    val classic_grass_block by create(ExtraSettings().creativeEx(NOSTALGIA)) { ClassicGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LIME).strength(0.6f).breakByTool(SHOVELS).ticksRandomly().sounds(BlockSoundGroup.GRASS)) }
    val classic_cobblestone by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE).strength(2.0f, 6.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val classic_planks by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 5.0f).breakByTool(AXES).sounds(BlockSoundGroup.WOOD)) }
    val classic_leaves by create(ExtraSettings().creativeEx(NOSTALGIA).renderCutoutMipped()) { FunctionalLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2f).breakByTool(HOES).ticksRandomly().sounds(BlockSoundGroup.GRASS).allowsSpawning(::never).suffocates(::never).blockVision(::never)) }
    val classic_sapling by create(ExtraSettings().creativeEx(NOSTALGIA).renderCutout()) { ClassicSaplingBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
    val potted_classic_sapling by create(ExtraSettings().renderCutout()) { FlowerPotBlock(classic_sapling, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
    val classic_gravel by create(ExtraSettings().creativeEx(NOSTALGIA)) { object : FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE).breakByTool(SHOVELS).strength(0.6f).sounds(BlockSoundGroup.GRAVEL)) {
        override fun getColor(state: BlockState, world: BlockView, pos: BlockPos) = 0x9C9193
    } }
    val classic_sponge by create(ExtraSettings().creativeEx(NOSTALGIA)) { ClassicSpongeBlock(FabricBlockSettings.of(Material.SPONGE).breakByTool(HOES).strength(0.6f).sounds(BlockSoundGroup.GRASS)) }
    val classic_glass by create(ExtraSettings().creativeEx(NOSTALGIA).renderCutout()) { GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(::never).solidBlock(::never).suffocates(::never).blockVision(::never)) }
    val classic_cloth by createMap(*ClassicDyeColor.values(), propertySupplier = { ExtraSettings().creativeEx(NOSTALGIA, sortValueDefault(), "classic_cloth", { stack -> it }) }) { key, name, properties -> Block(FabricBlockSettings.of(Material.WOOL, key.lcc_mapColor).breakByTool(SHEARS).strength(0.8f).sounds(BlockSoundGroup.WOOL)) }
    val classic_rose by create(ExtraSettings().creativeEx(NOSTALGIA).renderCutout()) { FlowerBlock(StatusEffects.ABSORPTION, 4, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
    val potted_classic_rose by create(ExtraSettings().renderCutout()) { FlowerPotBlock(classic_rose, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
    val cyan_flower by create(ExtraSettings().creativeEx(NOSTALGIA).renderCutout()) { FlowerBlock(StatusEffects.LEVITATION, 5, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)) }
    val potted_cyan_flower by create(ExtraSettings().renderCutout()) { FlowerPotBlock(cyan_flower, Settings.of(Material.DECORATION).breakInstantly().nonOpaque()) }
    val classic_iron_block by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.METAL, MapColor.IRON).breakByTool(PICKAXES, 1).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
    val alpha_iron_block by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.copy(classic_iron_block)) }
    val classic_gold_block by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).breakByTool(PICKAXES, 2).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
    val alpha_gold_block by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.copy(classic_gold_block)) }
    val classic_diamond_block by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND).breakByTool(PICKAXES, 2).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)) }
    val alpha_diamond_block by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.copy(classic_diamond_block)) }
    val classic_bricks by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(2.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val classic_tnt by create(ExtraSettings().creativeEx(NOSTALGIA)) { FunctionalTNTBlock(FabricBlockSettings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS)) }
    val classic_mossy_cobblestone by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE).strength(2.0f, 6.0f).breakByTool(PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val classic_chest by create(ExtraSettings().creativeEx(NOSTALGIA)) { ClassicChestBlock(FabricBlockSettings.of(Material.WOOD).breakByTool(AXES).strength(2.5f).sounds(BlockSoundGroup.WOOD)) }
    val nether_reactor by create(ExtraSettings().creativeEx(NOSTALGIA)) { NetherReactorBlock(FabricBlockSettings.of(Material.STONE, MapColor.CYAN).breakByTool(PICKAXES).requiresTool().strength(4.0f, 5.0f).sounds(BlockSoundGroup.STONE)) }
    val classic_crying_obsidian by create(ExtraSettings().creativeEx(NOSTALGIA)) { ClassicCryingObsidianBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(50.0f, 1200.0f).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val glowing_obsidian by create(ExtraSettings().creativeEx(NOSTALGIA)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.NETHER).strength(50.0f, 1200.0f).luminance(12).breakByTool(PICKAXES, 3).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val classic_stonecutter by create(ExtraSettings().creativeEx(NOSTALGIA)) { ClassicStonecutterBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE).breakByTool(PICKAXES).requiresTool().strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)) }
    val cog by create(ExtraSettings().creativeEx(NOSTALGIA)) { CogBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.IRON).breakInstantly().sounds(BlockSoundGroup.METAL)) }

    //TODO pill printer

    //TODO rainbow
    //IDEA shinestream, slippy passthrough block to gain speed
    //IDEA dash blocks, made from star plating and shinestream

    override fun register(key: String, thing: Block, properties: ExtraSettings) = super.register(key, thing, properties).apply { properties.initBlock(thing) }

    fun initClient() {
        all.forEach { (k, v) -> allProperties[k]!!.initBlockClient(v) }
    }

    override fun getDefaultProperty() = ExtraSettings()

    fun Block.traitHorizontalPlacement(context: ItemPlacementContext, property: DirectionProperty = HorizontalFacingBlock.FACING) = defaultState.with(property, context.playerFacing.opposite)!!
    fun Block.traitDirectionalPlayerPlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.playerLookDirection.opposite)!!
    fun Block.traitDirectionalFacePlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.side)!!
    fun Block.traitPillarPlacement(context: ItemPlacementContext, property: EnumProperty<Axis> = PillarBlock.AXIS) = defaultState.with(property, context.side.axis)!!

    class ExtraSettings internal constructor(vararg flammabilityEntries: FlammabilityEntry = emptyArray(), private var renderLayer: (() -> () -> RenderLayer)? = null, category: CreativeExCategory? = null, dynamicRender: (() -> (BlockEntityRendererFactory.Context?) -> BuiltinItemRendererRegistry.DynamicItemRenderer)? = null, sortValue: (default: Int, item: Item) -> Int = sortValueDefault(), set: String? = null, setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) : LCCItems.ExtraSettings(dynamicRender, category, sortValue, set, setKey) {

        private val flammability = mutableListOf(*flammabilityEntries)

        fun initBlock(block: Block) {
            flammability.forEach { f -> f.fires.forEach { FlammableBlockRegistry.getInstance(it).add(block, f.burn, f.chance) } }
        }

        fun initBlockClient(block: Block) {
            if (renderLayer != null) BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer!!()())
        }

        override fun creativeEx(category: CreativeExCategory, sortValue: (default: Int, item: Item) -> Int, set: String?, setKey: ((stack: ItemStack) -> CreativeExSetKey)?): ExtraSettings = super.creativeEx(category, sortValue, set, setKey).let { this }

        override fun dynamicItemRender(renderer: () -> (context: BlockEntityRendererFactory.Context?) -> BuiltinItemRendererRegistry.DynamicItemRenderer) = super.dynamicItemRender(renderer).let { this }

        fun flammable(burn: Int, chance: Int, vararg fires: Block) = flammability.add(FlammabilityEntry(burn, chance, *fires)).let { this }

        fun renderCutout() = this.also { renderLayer = { RenderLayer::getCutout } }

        fun renderCutoutMipped() = this.also { renderLayer = { RenderLayer::getCutoutMipped } }

        fun renderTranslucent() = this.also { renderLayer = { RenderLayer::getTranslucent } }

        companion object {

            internal class FlammabilityEntry(val burn: Int, val chance: Int, vararg val fires: Block)

            fun sortValueDefault() = LCCItems.ExtraSettings.sortValueDefault()

            fun sortValueInt(sortValue: Int) = LCCItems.ExtraSettings.sortValueInt(sortValue)

            fun sortValueFrom(item: KProperty0<Item>) = LCCItems.ExtraSettings.sortValueFrom(item)

            @JvmName("sortValueFromBlock")
            fun sortValueFrom(block: KProperty0<Block>) = LCCItems.ExtraSettings.sortValueFrom(block)

        }

    }

    private fun never(state: BlockState, world: BlockView, pos: BlockPos) = false

    private fun never(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>) = false

}