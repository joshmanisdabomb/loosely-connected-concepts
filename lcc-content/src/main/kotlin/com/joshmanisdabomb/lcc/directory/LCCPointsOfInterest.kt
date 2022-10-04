package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.`object`.builder.v1.world.poi.PointOfInterestHelper
import net.minecraft.world.poi.PointOfInterestType

object LCCPointsOfInterest : BasicDirectory<PointOfInterestType, Unit>() {

    val papercomb by entry(::initialiser) { PointOfInterestHelper.register(id, 0, 1, LCCBlocks.papercomb_block.stateManager.states.toSet()) }

    fun initialiser(input: PointOfInterestType, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun id(name: String) = LCC.id(name)

}
