package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import com.joshmanisdabomb.lcc.directory.LCCTags
import me.shedaniel.cloth.api.datagen.v1.TagData
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.tag.Tag

object LCCTagData : AdvancedDirectory<Unit, TagData.TagBuilder<*>, Unit, Unit>() {
    
    val wasteland_effective by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.appendTag(LCCTags.wasteland_required.cast()) }
    val wasteland_required by entry(::blockInitialiser) {}

    val nether_reactor_base by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.GOLD_BLOCK) }
    val nether_reactor_shell by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.COBBLESTONE) }

    val gold_blocks by entry(::itemInitialiser) {}.addInitListener { context, _ -> context.entry.append(Items.GOLD_BLOCK) }
    val furnace_generator_double by entry(::itemInitialiser) {}.addInitListener { context, _ -> context.entry.appendTag(ItemTags.COALS).append(Items.COAL_BLOCK) }

    val rubber_logs by entry(::blockInitialiser) {}.addInitListener { context, _ -> LCCData.tags.block(BlockTags.LOGS_THAT_BURN).appendTag(context.entry.id) }
    val rubber_logs_i by entry(::itemInitialiser) {}.addInitListener { context, _ -> LCCData.tags.item(ItemTags.LOGS_THAT_BURN).appendTag(context.entry.id) }

    val temperature_lukewarm by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.appendTag(BlockTags.CANDLES).appendTag(BlockTags.CANDLE_CAKES) }
    val temperature_warm by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.MAGMA_BLOCK) }
    val temperature_hot by entry(::blockInitialiser) {}
    val temperature_scalding by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.TORCH) }
    val temperature_soul_scalding by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.SOUL_TORCH) }
    val temperature_burning by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.FIRE) }
    val temperature_soul_burning by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.SOUL_FIRE) }
    val temperature_scorching by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.CAMPFIRE) }
    val temperature_soul_scorching by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.SOUL_CAMPFIRE) }
    val temperature_red_hot by entry(::blockInitialiser) {}.addInitListener { context, _ -> context.entry.append(Blocks.LAVA) }
    val temperature_nuclear by entry(::blockInitialiser) {}

    val temperature_scalding_e by entry(::entityInitialiser) {}.addInitListener { context, _ -> context.entry.append(EntityType.MAGMA_CUBE) }
    val temperature_red_hot_e by entry(::entityInitialiser) {}.addInitListener { context, _ -> context.entry.append(EntityType.BLAZE) }

    val salt_weakness by entry(::entityInitialiser) {}.addInitListener { context, _ -> context.entry.append(EntityType.SQUID, EntityType.GLOW_SQUID, EntityType.SILVERFISH, EntityType.ENDERMITE) }

    val oil by entry(::fluidInitialiser) {}
    val asphalt by entry(::fluidInitialiser) {}

    fun blockInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.block(LCCTags[context.name].cast().id)
    fun entityInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.entity(LCCTags[context.name].cast().id)
    fun itemInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.item(LCCTags[context.name].cast().id)
    fun fluidInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = LCCData.tags.fluid(LCCTags[context.name].cast().id)

    private fun <T> Tag<T>.cast() = this as Tag.Identified<T>

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}
