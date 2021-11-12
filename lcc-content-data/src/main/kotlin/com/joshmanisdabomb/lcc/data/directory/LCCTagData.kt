package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.batches.TagBatch
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import com.joshmanisdabomb.lcc.directory.LCCTags
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.tag.Tag

object LCCTagData : AdvancedDirectory<Unit, TagBatch.TagBuilder<*, *>, Unit, Unit>() {

    val wasteland_effective by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attachTag(LCCTags.wasteland_required.cast()) }
    val wasteland_required by entry(::blockInitialiser) {}
    val wasteland_equipment by entry(::itemInitialiser) {}
    val wasteland_combat by entry(::entityInitialiser) {}
    val wasteland_resistant by entry(::entityInitialiser) {}.addInitListener { context, _ -> context.entry.attachTag(LCCTags.wasteland_combat.cast()) }

    val nether_reactor_base by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.GOLD_BLOCK) }
    val nether_reactor_shell by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.COBBLESTONE) }

    val gold_blocks by entry(::itemInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Items.GOLD_BLOCK) }
    val furnace_generator_double by entry(::itemInitialiser) {}.addInitListener { context, _ -> context.entry.attachTag(ItemTags.COALS).attach(Items.COAL_BLOCK) }

    val rubber_logs by entry(::blockInitialiser) {}.addInitListener { context, _ -> LCCData.tags.block(BlockTags.LOGS_THAT_BURN).attachTagId(context.entry.id) }
    val rubber_logs_i by entry(::itemInitialiser) {}.addInitListener { context, _ -> LCCData.tags.item(ItemTags.LOGS_THAT_BURN).attachTagId(context.entry.id) }

    val temperature_lukewarm by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attachTag(BlockTags.CANDLES).attachTag(BlockTags.CANDLE_CAKES) }
    val temperature_warm by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.MAGMA_BLOCK) }
    val temperature_hot by entry(::blockInitialiser) {}
    val temperature_scalding by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.TORCH) }
    val temperature_soul_scalding by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.SOUL_TORCH) }
    val temperature_burning by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.FIRE) }
    val temperature_soul_burning by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.SOUL_FIRE) }
    val temperature_scorching by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.CAMPFIRE) }
    val temperature_soul_scorching by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.SOUL_CAMPFIRE) }
    val temperature_red_hot by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.attach(Blocks.LAVA) }
    val temperature_nuclear by entry(::blockInitialiser) {}

    val temperature_scalding_e by entry(::entityInitialiser) {}.addInitListener { context, _ -> context.entry.attach(EntityType.MAGMA_CUBE) }
    val temperature_red_hot_e by entry(::entityInitialiser) {}.addInitListener { context, _ -> context.entry.attach(EntityType.BLAZE) }

    val salt_weakness by entry(::entityInitialiser) {}.addInitListener { context, _ -> context.entry.attach(EntityType.SQUID, EntityType.GLOW_SQUID, EntityType.SILVERFISH, EntityType.ENDERMITE) }

    val oil by entry(::fluidInitialiser) {}
    val asphalt by entry(::fluidInitialiser) {}

    val hearts by entry(::itemInitialiser) {}.addInitListener { context, _ -> context.entry.attachTag(LCCTags.red_hearts.cast()).attachTag(LCCTags.iron_hearts.cast()).attachTag(LCCTags.crystal_hearts.cast()).attachTag(LCCTags.temporary_hearts.cast()) }

    fun blockInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.block(LCCTags[context.name].cast().id)
    fun itemInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.item(LCCTags[context.name].cast().id)
    fun entityInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.entity(LCCTags[context.name].cast().id)
    fun fluidInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.fluid(LCCTags[context.name].cast().id)

    private fun <T> Tag<T>.cast() = this as Tag.Identified<T>

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}
