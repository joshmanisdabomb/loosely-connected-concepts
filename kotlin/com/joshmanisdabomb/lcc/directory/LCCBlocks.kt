package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.creativeex.CreativeExCategory
import com.joshmanisdabomb.creativeex.CreativeExGroup
import com.joshmanisdabomb.creativeex.CreativeExSetKey
import com.joshmanisdabomb.lcc.block.DirectionalBlock
import com.joshmanisdabomb.lcc.block.HorizontalBlock
import com.joshmanisdabomb.lcc.block.OilBlock
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.DyeColor
import net.minecraft.util.Util
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction.Axis
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome

object LCCBlocks : RegistryDirectory<Block, LCCBlocks.ExtraSettings>() {

    override val _registry by lazy { Registry.BLOCK }

    val test_block by create(ExtraSettings().creativeEx(TESTING)) { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_2 by create(ExtraSettings().creativeEx(TESTING)) { HorizontalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_3 by create(ExtraSettings().creativeEx(TESTING)) { DirectionalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_4 by create(ExtraSettings().creativeEx(TESTING)) { PillarBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }

    val cracked_mud by create(ExtraSettings().creativeEx(WASTELAND)) { Block(FabricBlockSettings.of(Material.STONE, MapColor.WHITE_TERRACOTTA).strength(2.0F, 0.1F).breakByTool(FabricToolTags.PICKAXES, 0).requiresTool().sounds(BlockSoundGroup.STONE)) }
    val oil by create(ExtraSettings().flammable(3000, 300, Blocks.FIRE)) { OilBlock(LCCFluids.oil_still, Settings.copy(Blocks.WATER).strength(2.0F)) }

    //

    override fun register(key: String, thing: Block, properties: ExtraSettings) = super.register(key, thing, properties).apply { properties.init(thing) }

    override fun getDefaultProperty() = ExtraSettings()

    fun Block.traitHorizontalPlacement(context: ItemPlacementContext, property: DirectionProperty = HorizontalFacingBlock.FACING) = defaultState.with(property, context.playerFacing.opposite)!!
    fun Block.traitDirectionalPlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.playerLookDirection.opposite)!!
    fun Block.traitPillarPlacement(context: ItemPlacementContext, property: EnumProperty<Axis> = PillarBlock.AXIS) = defaultState.with(property, context.side.axis)!!

    class ExtraSettings internal constructor (vararg flammabilityEntries: FlammabilityEntry = emptyArray(), private var category: CreativeExCategory? = null, private var sortValue: () -> Int = { _sortValue++ }, private var set: String? = null, private var setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) {

        private val flammability = mutableListOf(*flammabilityEntries)

        fun flammable(burn: Int, chance: Int, vararg fires: Block): ExtraSettings {
            flammability.add(FlammabilityEntry(burn, chance, *fires))
            return this
        }

        fun creativeEx(category: CreativeExCategory, set: String? = null, sortValue: () -> Int = { _sortValue++ }, setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null): ExtraSettings {
            this.category = category
            this.set = set
            this.setKey = setKey
            this.sortValue = sortValue
            return this
        }

        fun init(block: Block) {
            flammability.forEach { f -> f.fires.forEach { FlammableBlockRegistry.getInstance(it).add(block, f.burn, f.chance) } }
        }

        fun initItem(item: BlockItem) {
            if (category == null) return
            val group = item.group as? CreativeExGroup ?: return
            val list = Util.make(DefaultedList.of<ItemStack>()) { item.appendStacks(item.group, it) }
            if (set != null && setKey != null) {
                list.forEach { group.addToSet(it, set!!, setKey!!.invoke(it), category!!, sortValue()) }
            } else {
                list.forEach { group.addToCategory(it, category!!, sortValue()) }
            }
        }

        companion object {
            private var _sortValue = 0;

            internal class FlammabilityEntry(val burn: Int, val chance: Int, vararg val fires: Block) {}
        }

    }

}