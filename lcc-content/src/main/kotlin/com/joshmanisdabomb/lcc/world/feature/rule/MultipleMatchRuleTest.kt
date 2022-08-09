package com.joshmanisdabomb.lcc.world.feature.rule

import com.joshmanisdabomb.lcc.directory.LCCRuleTests
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.structure.rule.RuleTest
import net.minecraft.tag.TagKey
import net.minecraft.util.math.random.Random
import net.minecraft.util.registry.Registry

class MultipleMatchRuleTest(private val blocks: List<Block>, private val states: List<BlockState>, private val tags: List<TagKey<Block>>) : RuleTest() {

    override fun test(state: BlockState, random: Random) = blocks.any { state.isOf(it) } || states.any { state == it } || tags.any { state.isIn(it) }

    override fun getType() = LCCRuleTests.multiple_match

    companion object {
        val codec: Codec<MultipleMatchRuleTest> = RecordCodecBuilder.create {
            it.group(
                Registry.BLOCK.codec.listOf().fieldOf("blocks").forGetter { it.blocks },
                Codec.list(BlockState.CODEC).fieldOf("states").forGetter { it.states },
                Codec.list(TagKey.codec(Registry.BLOCK_KEY)).fieldOf("tags").forGetter { it.tags }
            ).apply(it) { b, s, t -> MultipleMatchRuleTest(b, s, t) }
        }
    }
}