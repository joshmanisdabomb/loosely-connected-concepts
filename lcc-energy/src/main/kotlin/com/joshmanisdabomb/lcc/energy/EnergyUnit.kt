package com.joshmanisdabomb.lcc.energy

import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier

interface EnergyUnit {

    val id: Identifier
    val name: String
    val units: String
    val tags: Array<Identifier>

    val multiplier: Float

    fun convert(amount: Float, unit: EnergyUnit) = (amount / unit.multiplier) * this.multiplier

    fun fromStandard(amount: Float) = amount * this.multiplier

    fun toStandard(amount: Float) = amount / this.multiplier

    fun display(amount: Float): String

    fun displayWithUnits(amount: Float) = "${display(amount)} $units"

    fun fromFuel(item: ItemConvertible) = fromStandard(FuelRegistry.INSTANCE.get(item).toFloat())

    fun fromCoal() = fromCoals(1f)

    fun fromCoals(coals: Float) = fromStandard(1600f) * coals

    fun fromPlank() = fromPlanks(1f)

    fun fromPlanks(planks: Float) = fromStandard(300f) * planks

}