package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.mixin.`object`.builder.PointOfInterestTypeAccessor
import net.minecraft.util.registry.Registry
import net.minecraft.world.poi.PointOfInterestType

object LCCPointsOfInterest : BasicDirectory<PointOfInterestType, Unit>(), RegistryDirectory<PointOfInterestType, Unit, Unit> {

    override val registry = Registry.POINT_OF_INTEREST_TYPE

    override fun regId(name: String) = LCC.id(name)

    val papercomb by entry(::initialiser) { PointOfInterestTypeAccessor.callCreate(id.toString(), LCCBlocks.papercomb_block.stateManager.states.toSet(), 0, 1) }

    override fun <V : PointOfInterestType> afterInit(initialised: V, entry: DirectoryEntry<out PointOfInterestType, out V>, parameters: Unit) {
        PointOfInterestTypeAccessor.callSetup(initialised)
    }

    override fun defaultProperties(name: String) = Unit

}
