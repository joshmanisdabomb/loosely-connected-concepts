package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.directory.LCCTags
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.Tag

enum class ToolEffectivity(effective: Tag<Block>, required: Tag<Block>, equipment: Tag<Item>) {

    WASTELAND(LCCTags.wasteland_effective, LCCTags.wasteland_required, LCCTags.wasteland_equipment)

}