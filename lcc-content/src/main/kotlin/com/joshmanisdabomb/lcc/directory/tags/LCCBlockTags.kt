package com.joshmanisdabomb.lcc.directory.tags

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import net.minecraft.block.Block
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LCCBlockTags : AdvancedDirectory<Identifier?, TagKey<Block>, Unit, Unit>() {

    val wasteland_effective by entry(::initialiser) { null }
    val wasteland_required by entry(::initialiser) { null }
    val crowbar_salvageable by entry(::initialiser) { null }

    val rainbow_effective by entry(::initialiser) { null }
    val rainbow_required by entry(::initialiser) { null }

    val nether_reactor_base by entry(::initialiser) { null }
    val nether_reactor_shell by entry(::initialiser) { null }

    val temperature_lukewarm by entry(::initialiser) { null }
    val temperature_warm by entry(::initialiser) { null }
    val temperature_hot by entry(::initialiser) { null }
    val temperature_scalding by entry(::initialiser) { null }
    val temperature_soul_scalding by entry(::initialiser) { null }
    val temperature_burning by entry(::initialiser) { null }
    val temperature_soul_burning by entry(::initialiser) { null }
    val temperature_scorching by entry(::initialiser) { null }
    val temperature_soul_scorching by entry(::initialiser) { null }
    val temperature_red_hot by entry(::initialiser) { null }
    val temperature_nuclear by entry(::initialiser) { null }

    val radioactive by entry(::initialiser) { null }

    val rainbow_gate_idols by entry(::initialiser) { null }

    val rubber_logs by entry(::initialiser) { null }
    val deadwood_logs by entry(::initialiser) { null }

    fun initialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = TagKey.of(Registry.BLOCK_KEY, input ?: context.id)

    override fun defaultProperties(name: String) = Unit

    override fun defaultContext() = Unit

    override fun id(name: String) = LCC.id(name)

}