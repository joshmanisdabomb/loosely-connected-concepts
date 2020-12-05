package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.group.LCCGroup
import net.minecraft.item.ItemGroup

object LCCGroups : ThingDirectory<ItemGroup, Unit>() {

    val group by create { LCCGroup() }

}

