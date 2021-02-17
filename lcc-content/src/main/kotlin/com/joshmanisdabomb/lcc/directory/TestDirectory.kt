package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.HorizontalBlock
import com.joshmanisdabomb.lcc.group.LCCGroup
import com.joshmanisdabomb.lcc.settings.BlockExtraSettings
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting
import com.joshmanisdabomb.lcc.settings.FlammableExtraSetting
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.util.registry.Registry

object TestDirectory : BasicDirectory<Block, BlockExtraSettings>() {

    val new_test_block by entry(::initialiser) { Block(AbstractBlock.Settings.copy(Blocks.BONE_BLOCK)) }
        .setProperties(BlockExtraSettings().add(CreativeExExtraSetting(LCCGroup.LCCGroupCategory.TESTING)).add(FlammableExtraSetting(1,1)))
        .addTags("a")
    val new_test_block_2 by entry(::initialiser) { Block(AbstractBlock.Settings.copy(Blocks.BONE_BLOCK)) }
        .setProperties(BlockExtraSettings().add(CreativeExExtraSetting(LCCGroup.LCCGroupCategory.TESTING)).add(FlammableExtraSetting(1,1)))
        .addTags("a", "b")
    val new_test_block_3 by entry(::initialiser) { HorizontalBlock(AbstractBlock.Settings.copy(new_test_block_2)) }
        .setProperties(BlockExtraSettings().add(CreativeExExtraSetting(LCCGroup.LCCGroupCategory.TESTING)).add(FlammableExtraSetting(1,1)))
        .addTags("b", "c")

    private fun <B : Block> initialiser(input: B, context: DirectoryContext<BlockExtraSettings, Unit>): B {
        Registry.register(Registry.BLOCK, LCC.id(context.name), input)
        return input
    }

    override fun defaultProperties(name: String) = BlockExtraSettings()

    override fun defaultContext(name: String) = Unit

}