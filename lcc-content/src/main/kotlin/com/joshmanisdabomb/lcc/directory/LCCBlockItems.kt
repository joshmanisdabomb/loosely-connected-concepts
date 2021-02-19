package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCItems.defaults
import com.joshmanisdabomb.lcc.item.block.PowerSourceBlockItem
import com.joshmanisdabomb.lcc.item.block.RadioactiveBlockItem
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object LCCBlockItems : AdvancedDirectory<LCCBlockItems.Replacement, BlockItem?, Block, Unit>() {

    private val _map = mutableMapOf<String, BlockItem>()

    val oil by entry(::initialiser) { Replacement() }
    val asphalt by entry(::initialiser) { Replacement() }
    val road by entry(::initialiser) { Replacement() }

    val power_source by entry(::initialiser) { Replacement(PowerSourceBlockItem(LCCBlocks.power_source, Item.Settings().defaults())) }

    val uranium_ore by entry(::initialiser) { Replacement(RadioactiveBlockItem(1, 0, LCCBlocks.uranium_ore, Item.Settings().defaults())) }
    val uranium_block by entry(::initialiser) { Replacement(RadioactiveBlockItem(4, 0, LCCBlocks.uranium_block, Item.Settings().defaults())) }
    val enriched_uranium_block by entry(::initialiser) { Replacement(RadioactiveBlockItem(4, 1, LCCBlocks.enriched_uranium_block, Item.Settings().defaults())) }
    val heavy_uranium_block by entry(::initialiser) { Replacement(RadioactiveBlockItem(4, 0, LCCBlocks.heavy_uranium_block, Item.Settings().defaults())) }
    val nuclear_waste by entry(::initialiser) { Replacement(RadioactiveBlockItem(16, 3, LCCBlocks.nuclear_waste, Item.Settings().defaults())) }

    val potted_classic_sapling by entry(::initialiser) { Replacement() }
    val potted_classic_rose by entry(::initialiser) { Replacement() }
    val potted_cyan_flower by entry(::initialiser) { Replacement() }

    val nuclear_fire by entry(::initialiser) { Replacement() }

    override fun afterInitAll(initialised: List<DirectoryEntry<out Replacement, out BlockItem?>>, filter: (context: DirectoryContext<Block>) -> Boolean) {
        LCCBlocks.all.forEach { (k, v) ->
            val blockItem = (initialised.firstOrNull { it.name == k }?.input ?: Replacement(BlockItem(v, Item.Settings().defaults()))).bi ?: return@forEach
            Registry.register(Registry.ITEM, LCC.id(k), blockItem)
            LCCBlocks[blockItem.block].properties.initItem(blockItem)
            _map[k] = blockItem
        }
    }

    fun initialiser(input: Replacement, context: DirectoryContext<Block>, parameters: Unit) = input.bi

    override fun defaultProperties(name: String) = LCCBlocks[name]
    override fun defaultContext() = Unit

    fun initClient() {
        _map.forEach { (k, v) ->  LCCBlocks[v.block].properties.initItemClient(v) }
    }

    data class Replacement(val bi: BlockItem? = null)

}