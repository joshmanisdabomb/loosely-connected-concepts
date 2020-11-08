package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.stack
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup

object LCCGroups : ThingDirectory<ItemGroup, Unit>() {

    val group by create { FabricItemGroupBuilder.create(LCC.id("group")).icon({ LCCItems.test_item.stack() }).build() }

}
