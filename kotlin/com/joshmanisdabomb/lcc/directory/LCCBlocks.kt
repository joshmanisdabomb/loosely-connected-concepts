package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.creativeex.CreativeExCategory
import com.joshmanisdabomb.creativeex.CreativeExSetKey
import com.joshmanisdabomb.lcc.block.*
import com.joshmanisdabomb.lcc.concepts.color.ClassicDyeColor
import com.joshmanisdabomb.lcc.directory.LCCBlocks.ExtraSettings.Companion.sortValueDefault
import com.joshmanisdabomb.lcc.directory.LCCBlocks.ExtraSettings.Companion.sortValueFrom
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.item.Item
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.DyeColor
import net.minecraft.util.math.Direction.Axis
import net.minecraft.util.math.IntRange
import net.minecraft.util.registry.Registry
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
    val ruby_ore by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby))) { OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(FabricToolTags.PICKAXES, 2), IntRange.between(3, 7)) }
    val ruby_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::ruby))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LAVA).strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
    val topaz_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::topaz))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.WHITE_TERRACOTTA).strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
    val sapphire_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::sapphire))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, 2).sounds(BlockSoundGroup.METAL)) }
    val uranium_ore by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::uranium))) { OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0F).breakByTool(FabricToolTags.PICKAXES, 3), IntRange.between(3, 7)) }
    val uranium_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::uranium))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }
    val enriched_uranium_block by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(LCCItems::enriched_uranium))) { Block(FabricBlockSettings.of(Material.METAL, MapColor.LIME).strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, 3).sounds(BlockSoundGroup.METAL)) }

    //Gizmos
    val asphalt by create(ExtraSettings()) { AsphaltBlock(LCCFluids.asphalt_still, Settings.copy(Blocks.WATER).strength(100.0F).ticksRandomly()) }
    val road by create(ExtraSettings()) { RoadBlock(Settings.of(Material.STONE, DyeColor.GRAY).strength(2.0F, 8.0F).sounds(BlockSoundGroup.TUFF)) }
    val soaking_soul_sand by create(ExtraSettings().creativeEx(GIZMOS)) { SoakingSoulSandBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.BROWN_TERRACOTTA).breakByTool(FabricToolTags.SHOVELS).strength(0.75F, 2.5F).sounds(LCCSounds.soaking_soul_sand)) }
    val bounce_pad by create(ExtraSettings().creativeEx(GIZMOS)) { BouncePadBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).breakByTool(FabricToolTags.PICKAXES, 2).strength(5.0F, 6.0F).sounds(BlockSoundGroup.COPPER), doubleArrayOf(1.0, 1.4, 1.8, 2.2, 2.6)) }

    //Wasteland
    val cracked_mud by create(ExtraSettings().creativeEx(WASTELAND)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.WHITE_TERRACOTTA).strength(2.0F, 0.1F).breakByTool(FabricToolTags.PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val oil by create(ExtraSettings().flammable(3000, 300, Blocks.FIRE)) { OilBlock(LCCFluids.oil_still, Settings.copy(Blocks.WATER).strength(2.0F)) }
    //IDEA deadwood, rarely spawns naturally or dries out wood
    //IDEA rusted iron blocks, first tier of wasteland tools

    //TODO atomic bomb

    //TODO spreaders

    //TODO computers

    //IDEA custom currency, mints to print money and coins, banks for converting to and from materials, wallets for storage (maybe work similar to bundles), credit cards for instant transfers

    //Crafters
    val spawner_table by create(ExtraSettings().creativeEx(CRAFTERS)) { DungeonTableBlock(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE).strength(20.0F, 50.0F).luminance { 7 }.breakByTool(FabricToolTags.PICKAXES).requiresTool().sounds(BlockSoundGroup.STONE)) }
    //IDEA uranium refiner, oil refiner -> (fuel, asphalt, plastic), rainbow refiner (saturator?), universal refiner
    //IDEA Kiln for faster smelting of non smokable or blastables

    //Nostalgia TODO
    //IDEA simulation fabric
    //IDEA time rift, basic method of taking blocks back in time to notable versions
    //IDEA time weaver, crafting table made of time rift, ruby blocks and something else - to allow taking blocks and items through time

    val classic_cloth by createMap(*ClassicDyeColor.values(), propertySupplier = { ExtraSettings().creativeEx(NOSTALGIA, sortValueDefault(), "classic_cloth", { stack -> it }) }) { key, name, properties -> Block(FabricBlockSettings.of(Material.WOOL, key.lcc_mapColor).requiresTool().breakByTool(FabricToolTags.SHEARS).strength(0.8f).sounds(BlockSoundGroup.WOOL)) }

    //TODO pill printer

    //TODO rainbow

    override fun register(key: String, thing: Block, properties: ExtraSettings) = super.register(key, thing, properties).apply { properties.initBlock(thing) }

    override fun getDefaultProperty() = ExtraSettings()

    fun Block.traitHorizontalPlacement(context: ItemPlacementContext, property: DirectionProperty = HorizontalFacingBlock.FACING) = defaultState.with(property, context.playerFacing.opposite)!!
    fun Block.traitDirectionalPlayerPlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.playerLookDirection.opposite)!!
    fun Block.traitDirectionalFacePlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.side)!!
    fun Block.traitPillarPlacement(context: ItemPlacementContext, property: EnumProperty<Axis> = PillarBlock.AXIS) = defaultState.with(property, context.side.axis)!!

    class ExtraSettings internal constructor(vararg flammabilityEntries: FlammabilityEntry = emptyArray(), category: CreativeExCategory? = null, sortValue: (default: Int, item: Item) -> Int = sortValueDefault(), set: String? = null, setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) : LCCItems.ExtraSettings(category, sortValue, set, setKey) {

        private val flammability = mutableListOf(*flammabilityEntries)

        override fun creativeEx(category: CreativeExCategory, sortValue: (default: Int, item: Item) -> Int, set: String?, setKey: ((stack: ItemStack) -> CreativeExSetKey)?): ExtraSettings = super.creativeEx(category, sortValue, set, setKey).let { this }

        fun flammable(burn: Int, chance: Int, vararg fires: Block): ExtraSettings {
            flammability.add(FlammabilityEntry(burn, chance, *fires))
            return this
        }

        fun initBlock(block: Block) {
            flammability.forEach { f -> f.fires.forEach { FlammableBlockRegistry.getInstance(it).add(block, f.burn, f.chance) } }
        }

        companion object {

            internal class FlammabilityEntry(val burn: Int, val chance: Int, vararg val fires: Block)

            fun sortValueDefault() = LCCItems.ExtraSettings.sortValueDefault()

            fun sortValueInt(sortValue: Int) = LCCItems.ExtraSettings.sortValueInt(sortValue)

            fun sortValueFrom(item: KProperty0<Item>) = LCCItems.ExtraSettings.sortValueFrom(item)

            @JvmName("sortValueFromBlock")
            fun sortValueFrom(block: KProperty0<Block>) = LCCItems.ExtraSettings.sortValueFrom(block)

        }

    }

}