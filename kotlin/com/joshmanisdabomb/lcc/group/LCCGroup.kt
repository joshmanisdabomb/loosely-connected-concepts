package com.joshmanisdabomb.lcc.group

import com.joshmanisdabomb.creativeex.CreativeExCategory
import com.joshmanisdabomb.creativeex.CreativeExGroup
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.item.ItemStack

class LCCGroup : CreativeExGroup(LCC.id("group")) {

    override fun createIcon() = ItemStack(LCCBlocks.test_block)

    enum class LCCGroupCategory(override val groupColor: Long) : CreativeExCategory {

        RESOURCES(0xFFFF00FF),
        TOOLS(0xFFFF00FF),
        GIZMOS(0xFFFF00FF),
        RAINBOW(0xFFAD72AD),
        SPREADERS(0xFF5D473E),
        WASTELAND(0xFFFF00FF),
        NUCLEAR(0xFFFF00FF),
        COMPUTING(0xFF194a19),
        NOSTALGIA(0xFF69854E),
        POWER(0xFFFF00FF),
        HEALTH(0xFFFF00FF),
        TESTING(0xFFFF00FF);

        override val sortValue = ordinal

    }

}