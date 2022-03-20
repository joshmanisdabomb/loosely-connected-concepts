package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCItems.defaults
import com.joshmanisdabomb.lcc.item.block.PlasticBlockItem
import com.joshmanisdabomb.lcc.item.block.PowerSourceBlockItem
import com.joshmanisdabomb.lcc.item.block.RadioactiveBlockItem
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.SignItem
import net.minecraft.item.TallBlockItem
import net.minecraft.util.registry.Registry

object LCCBlockItems : AdvancedDirectory<LCCBlockItems.Replacement, BlockItem?, Block, Unit>() {

    private val _map = mutableMapOf<String, BlockItem>()

    val oil by entry(::initialiser) { Replacement() }
    val asphalt by entry(::initialiser) { Replacement() }
    val road by entry(::initialiser) { Replacement() }

    val power_source by entry(::initialiser) { Replacement(PowerSourceBlockItem(properties, Item.Settings().defaults())) }

    val uranium_ore by entry(::initialiser) { Replacement(RadioactiveBlockItem(1, 0, properties, Item.Settings().defaults())) }
    val deepslate_uranium_ore by entry(::initialiser) { Replacement(RadioactiveBlockItem(1, 0, properties, Item.Settings().defaults())) }
    val uranium_block by entry(::initialiser) { Replacement(RadioactiveBlockItem(4, 0, properties, Item.Settings().defaults())) }
    val enriched_uranium_block by entry(::initialiser) { Replacement(RadioactiveBlockItem(4, 1, properties, Item.Settings().defaults())) }
    val heavy_uranium_block by entry(::initialiser) { Replacement(RadioactiveBlockItem(4, 0, properties, Item.Settings().defaults())) }
    val nuclear_waste by entry(::initialiser) { Replacement(RadioactiveBlockItem(16, 3, properties, Item.Settings().defaults())) }

    val potted_classic_sapling by entry(::initialiser) { Replacement() }
    val potted_classic_rose by entry(::initialiser) { Replacement() }
    val potted_cyan_flower by entry(::initialiser) { Replacement() }
    val potted_rubber_sapling by entry(::initialiser) { Replacement() }
    val potted_three_leaf_clover by entry(::initialiser) { Replacement() }
    val potted_four_leaf_clover by entry(::initialiser) { Replacement() }
    val potted_forget_me_not by entry(::initialiser) { Replacement() }

    val nuclear_fire by entry(::initialiser) { Replacement() }

    val rubber_door by entry(::initialiser) { Replacement(TallBlockItem(properties, Item.Settings().defaults())) }
    val treetap_bowl by entry(::initialiser) { Replacement() }
    val dried_treetap by entry(::initialiser) { Replacement() }

    val rubber_sign by entry(::initialiser) { Replacement(SignItem(Item.Settings().defaults(), LCCBlocks.rubber_sign, LCCBlocks.rubber_wall_sign)) }
    val rubber_wall_sign by entry(::initialiser) { Replacement() }

    val rubber_piston_head by entry(::initialiser) { Replacement() }

    val deadwood_sign by entry(::initialiser) { Replacement(SignItem(Item.Settings().defaults(), LCCBlocks.deadwood_sign, LCCBlocks.deadwood_wall_sign)) }
    val deadwood_wall_sign by entry(::initialiser) { Replacement() }

    val computing by entry(::initialiser) { Replacement() }
    val terminal by entry(::initialiser) { Replacement(PlasticBlockItem(properties, Item.Settings().defaults())) }

    override fun afterInitAll(initialised: List<DirectoryEntry<out Replacement, out BlockItem?>>, filter: (context: DirectoryContext<Block>) -> Boolean) {
        LCCBlocks.all.forEach { (k, v) ->
            val bi = initialised.firstOrNull { it.name == k }?.input ?: Replacement(BlockItem(v, Item.Settings().defaults()))
            val blockItem = bi.bi ?: return@forEach
            Registry.register(Registry.ITEM, LCC.id(bi.name ?: k), blockItem)
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

    data class Replacement(val bi: BlockItem? = null, val name: String? = null)

}