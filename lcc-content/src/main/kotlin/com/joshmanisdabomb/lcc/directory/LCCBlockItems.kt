package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCItems.defaults
import com.joshmanisdabomb.lcc.item.block.PowerSourceBlockItem
import com.joshmanisdabomb.lcc.item.block.RadioactiveBlockItem
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object LCCBlockItems : ThingDirectory<LCCBlockItems.Replacement, Unit>() {

    private val _map = mutableMapOf<String, BlockItem>()

    val oil by create { Replacement() }
    val asphalt by create { Replacement() }
    val road by create { Replacement() }

    val power_source by create { Replacement(PowerSourceBlockItem(LCCBlocks.power_source, Item.Settings().defaults())) }

    val uranium_ore by create { Replacement(RadioactiveBlockItem(1, 0, LCCBlocks.uranium_ore, Item.Settings().defaults())) }
    val uranium_block by create { Replacement(RadioactiveBlockItem(4, 0, LCCBlocks.uranium_block, Item.Settings().defaults())) }
    val enriched_uranium_block by create { Replacement(RadioactiveBlockItem(4, 1, LCCBlocks.enriched_uranium_block, Item.Settings().defaults())) }
    val heavy_uranium_block by create { Replacement(RadioactiveBlockItem(4, 0, LCCBlocks.heavy_uranium_block, Item.Settings().defaults())) }
    val nuclear_waste by create { Replacement(RadioactiveBlockItem(16, 3, LCCBlocks.nuclear_waste, Item.Settings().defaults())) }

    val potted_classic_sapling by create { Replacement() }
    val potted_classic_rose by create { Replacement() }
    val potted_cyan_flower by create { Replacement() }

    val nuclear_fire by create { Replacement() }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        LCCBlocks.all.forEach { (k, v) ->
            val blockItem = all.getOrDefault(k, Replacement(BlockItem(v, Item.Settings().defaults()))).bi ?: return@forEach
            Registry.register(Registry.ITEM, LCC.id(k), blockItem)
            LCCBlocks.getProperties(blockItem.block)?.initItem(blockItem)
            _map[k] = blockItem
        }
    }

    fun initClient() {
        _map.forEach { (k, v) ->
            LCCBlocks.getProperties(v.block)?.initItemClient(v)
        }
    }

    data class Replacement(val bi: BlockItem? = null)

}