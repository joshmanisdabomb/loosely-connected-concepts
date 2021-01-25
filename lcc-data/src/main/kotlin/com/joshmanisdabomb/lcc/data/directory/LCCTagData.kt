package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import me.shedaniel.cloth.api.datagen.v1.TagData
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.tag.Tag

object LCCTagData : ThingDirectory<TagData.TagBuilder<*>.() -> Unit, TagData.(Tag<*>) -> TagData.TagBuilder<*>>() {

    private val block: TagData.(Tag<*>) -> TagData.TagBuilder<*> = { block(it as Tag.Identified<Block>) }
    private val item: TagData.(Tag<*>) -> TagData.TagBuilder<*> = { item(it as Tag.Identified<Item>) }
    private val fluid: TagData.(Tag<*>) -> TagData.TagBuilder<*> = { fluid(it as Tag.Identified<Fluid>) }
    private val entity: TagData.(Tag<*>) -> TagData.TagBuilder<*> = { entity(it as Tag.Identified<EntityType<*>>) }

    val wasteland_effective by createWithName(block) { { this.castBlock().appendTag(LCCTags.wasteland_required.cast()) } }
    val wasteland_required by createWithName(block) { { this.castBlock() } }

    val nether_reactor_base by createWithName(block) { { this.castBlock().append(Blocks.GOLD_BLOCK) } }
    val nether_reactor_shell by createWithName(block) { { this.castBlock().append(Blocks.COBBLESTONE) } }

    val gold_blocks by createWithName(item) { { this.castItem().append(Items.GOLD_BLOCK) } }

    val furnace_generator_double by createWithName(item) { { this.castItem().appendTag(ItemTags.COALS).append(Items.COAL_BLOCK) } }

    val geothermal_warm by createWithName(block) { { this.castBlock().appendTag(BlockTags.CANDLES).appendTag(BlockTags.CANDLE_CAKES) } }
    val geothermal_hot by createWithName(block) { { this.castBlock().append(Blocks.MAGMA_BLOCK) } }
    val geothermal_heated by createWithName(block) { { this.castBlock().append(Blocks.TORCH) } }
    val geothermal_soul_heated by createWithName(block) { { this.castBlock().append(Blocks.SOUL_TORCH) } }
    val geothermal_burning by createWithName(block) { { this.castBlock().append(Blocks.FIRE) } }
    val geothermal_soul_burning by createWithName(block) { { this.castBlock().append(Blocks.SOUL_FIRE) } }
    val geothermal_flaming by createWithName(block) { { this.castBlock().append(Blocks.CAMPFIRE) } }
    val geothermal_soul_flaming by createWithName(block) { { this.castBlock().append(Blocks.SOUL_CAMPFIRE) } }
    val geothermal_full by createWithName(block) { { this.castBlock().append(Blocks.LAVA) } }

    val geothermal_magma by createWithName(entity) { { this.castEntity().append(EntityType.MAGMA_CUBE) } }
    val geothermal_blaze by createWithName(entity) { { this.castEntity().append(EntityType.BLAZE) } }

    val oil by createWithName(fluid) { { this.castFluid() } }
    val asphalt by createWithName(fluid) { { this.castFluid() } }

    override fun registerAll(things: Map<String, TagData.TagBuilder<*>.() -> Unit>, properties: Map<String, TagData.(Tag<*>) -> TagData.TagBuilder<*>>) {
        super.registerAll(things, properties)
        things.forEach { (k, v) ->
            val builder = LCCData.accessor.tags.run { properties[k]!!(this, LCCTags[k]!!) }
            v(builder)
        }
    }

    private fun <T> Tag<T>.cast() = this as Tag.Identified<T>
    private fun TagData.TagBuilder<*>.castBlock() = this as TagData.TagBuilder<Block>
    private fun TagData.TagBuilder<*>.castItem() = this as TagData.TagBuilder<Item>
    private fun TagData.TagBuilder<*>.castFluid() = this as TagData.TagBuilder<Fluid>
    private fun TagData.TagBuilder<*>.castEntity() = this as TagData.TagBuilder<EntityType<*>>

}
