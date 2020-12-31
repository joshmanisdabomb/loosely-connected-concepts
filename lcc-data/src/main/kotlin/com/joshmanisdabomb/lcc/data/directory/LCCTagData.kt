package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import me.shedaniel.cloth.api.datagen.v1.TagData
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.tag.Tag

object LCCTagData : ThingDirectory<TagData.TagBuilder<*>.() -> Unit, TagData.(Tag<*>) -> TagData.TagBuilder<*>>() {

    val block: TagData.(Tag<*>) -> TagData.TagBuilder<*> = { block(it as Tag.Identified<Block>) }

    val wasteland_effective by createWithName(block) { { this.castBlock().appendTag(LCCTags.wasteland_required.cast()) } }
    val wasteland_required by createWithName(block) { { this.castBlock() } }

    val nether_reactor_base by createWithName(block) { { this.castBlock().append(Blocks.GOLD_BLOCK) } }
    val nether_reactor_shell by createWithName(block) { { this.castBlock().append(Blocks.COBBLESTONE) } }

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

}
