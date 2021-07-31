package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.directory.LCCTags
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.Tag

enum class ToolEffectivity(val effective: Tag<Block>, val required: Tag<Block>, val equipment: Tag<Item>) {

    WASTELAND(LCCTags.wasteland_effective, LCCTags.wasteland_required, LCCTags.wasteland_equipment)

}