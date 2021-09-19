package com.joshmanisdabomb.lcc.world.feature.rule

import com.joshmanisdabomb.lcc.directory.LCCRuleTests
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.structure.rule.RuleTest
import net.minecraft.tag.ServerTagManagerHolder
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry
import java.util.*

class MultipleMatchRuleTest(private val blocks: List<Block>, private val states: List<BlockState>, private val tags: List<Tag<Block>>) : RuleTest() {

    override fun test(state: BlockState, random: Random) = blocks.any { state.isOf(it) } || states.any { state == it } || tags.any { state.isIn(it) }

    override fun getType() = LCCRuleTests.multiple_match

    companion object {
        val codec: Codec<MultipleMatchRuleTest> = RecordCodecBuilder.create {
            it.group(
                Codec.list(Registry.BLOCK).fieldOf("blocks").forGetter { it.blocks },
                Codec.list(BlockState.CODEC).fieldOf("states").forGetter { it.states },
                Codec.list(Tag.codec { ServerTagManagerHolder.getTagManager().getOrCreateTagGroup(Registry.BLOCK_KEY) }).fieldOf("tags").forGetter { it.tags }
            ).apply(it) { b, s, t -> MultipleMatchRuleTest(b, s, t) }
        }
    }
}