package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.widens.CommonWidens
import net.minecraft.world.poi.PointOfInterestType

object LCCPointsOfInterest : BasicDirectory<PointOfInterestType, Unit>() {

    val papercomb by entry(::initialiser) { CommonWidens.registerPointOfInterest(id, LCCBlocks.papercomb_block.stateManager.states.toSet(), 0, 1) }

    fun initialiser(input: PointOfInterestType, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit

}
