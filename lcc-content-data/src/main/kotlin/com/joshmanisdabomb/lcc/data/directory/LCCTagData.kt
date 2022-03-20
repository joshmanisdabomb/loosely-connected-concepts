package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.batches.TagBatch
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import com.joshmanisdabomb.lcc.directory.LCCBiomes
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.tags.LCCBlockTags
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.item.SwordItem
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LCCTagData : AdvancedDirectory<Identifier?, TagBatch.TagBuilder<*, *>, Unit, Unit>() {

    val wasteland_biomes by entry(::biomeInitialiser) { LCC.id("wasteland") }.addInitListener { context, _ -> context.entry.attach(LCCBiomes.wasteland_barrens).attach(LCCBiomes.wasteland_spikes) }

    val wasteland_effective by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attachTag(LCCBlockTags.wasteland_required) }
    val wasteland_required by entry(::blockInitialiser) { null }
    val wasteland_equipment by entry(::itemInitialiser) { null }
    val wasteland_offense by entry(::entityInitialiser) { null }
    val wasteland_defense by entry(::entityInitialiser) { null }

    val nether_reactor_base by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.GOLD_BLOCK) }
    val nether_reactor_shell by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.COBBLESTONE) }

    val gold_blocks by entry(::itemInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Items.GOLD_BLOCK) }
    val furnace_generator_double by entry(::itemInitialiser) { null }.addInitListener { context, _ -> context.entry.attachTag(ItemTags.COALS).attach(Items.COAL_BLOCK) }

    val rubber_logs by entry(::blockInitialiser) { null }.addInitListener { context, _ -> LCCData.tags.block(BlockTags.LOGS_THAT_BURN).attachTagId(context.entry.id) }
    val rubber_logs_i by entry(::itemInitialiser) { LCC.id(name.dropLast(2)) }.addInitListener { context, _ -> LCCData.tags.item(ItemTags.LOGS_THAT_BURN).attachTagId(context.entry.id) }

    val temperature_lukewarm by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attachTag(BlockTags.CANDLES).attachTag(BlockTags.CANDLE_CAKES) }
    val temperature_warm by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.MAGMA_BLOCK) }
    val temperature_hot by entry(::blockInitialiser) { null }
    val temperature_scalding by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.TORCH) }
    val temperature_soul_scalding by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.SOUL_TORCH) }
    val temperature_burning by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.FIRE) }
    val temperature_soul_burning by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.SOUL_FIRE) }
    val temperature_scorching by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.CAMPFIRE) }
    val temperature_soul_scorching by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.SOUL_CAMPFIRE) }
    val temperature_red_hot by entry(::blockInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(Blocks.LAVA) }
    val temperature_nuclear by entry(::blockInitialiser) { null }

    val temperature_scalding_e by entry(::entityInitialiser) { LCC.id(name.dropLast(2)) }.addInitListener { context, _ -> context.entry.attach(EntityType.MAGMA_CUBE) }
    val temperature_red_hot_e by entry(::entityInitialiser) { LCC.id(name.dropLast(2)) }.addInitListener { context, _ -> context.entry.attach(EntityType.BLAZE) }

    val salt_weakness by entry(::entityInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(EntityType.SQUID, EntityType.GLOW_SQUID, EntityType.SILVERFISH, EntityType.ENDERMITE) }

    val oil by entry(::fluidInitialiser) { null }
    val asphalt by entry(::fluidInitialiser) { null }

    val hearts by entry(::itemInitialiser) { null }.addInitListener { context, _ -> context.entry.attachTag(LCCItemTags.red_hearts).attachTag(LCCItemTags.iron_hearts).attachTag(LCCItemTags.crystal_hearts).attachTag(LCCItemTags.temporary_hearts) }

    val imbuable by entry(::itemInitialiser) { null }.addInitListener { context, _ -> context.entry.attach(*Registry.ITEM.filterIsInstance<SwordItem>().toTypedArray()).attach(LCCItems.knife) }

    fun blockInitialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.block(input ?: context.id)
    fun biomeInitialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.biome(input ?: context.id)
    fun itemInitialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.item(input ?: context.id)
    fun entityInitialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.entity(input ?: context.id)
    fun fluidInitialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.fluid(input ?: context.id)

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}
