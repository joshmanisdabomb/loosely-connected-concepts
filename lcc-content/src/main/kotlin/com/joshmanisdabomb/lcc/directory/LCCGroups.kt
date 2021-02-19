package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.group.LCCGroup
import net.minecraft.item.ItemGroup

object LCCGroups : BasicDirectory<ItemGroup, Unit>() {

    val group by entry(::initialiser) { LCCGroup() }

    private fun <G : ItemGroup> initialiser(input: G, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

}

