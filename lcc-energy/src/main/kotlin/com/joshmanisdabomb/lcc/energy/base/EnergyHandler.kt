package com.joshmanisdabomb.lcc.energy.base

import com.joshmanisdabomb.lcc.energy.EnergyUnit

interface EnergyHandler<C : EnergyContext> {

    fun isEnergyUsable(context: C) = true

    fun addEnergyDirect(amount: Float, unit: EnergyUnit, context: C): Float

    fun removeEnergyDirect(amount: Float, unit: EnergyUnit, context: C): Float

    fun addEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: C) = addEnergyDirect(amount, unit, context)

    fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: C) = removeEnergyDirect(amount, unit, context)

    fun <O : EnergyContext> insertEnergy(target: EnergyHandler<O>, amount: Float, unit: EnergyUnit, context: C, otherContext: C.() -> O = EnergyContext::defaultOtherImplementation): Float {
        val extracted = this.removeEnergy(target, amount, unit, context)
        if (extracted == 0f) return 0f
        val added = target.addEnergy(this, extracted, unit, context.otherContext())
        if (extracted - added > 0f) this.addEnergyDirect(extracted - added, unit, context)
        return added
    }

    fun <O : EnergyContext> extractEnergy(target: EnergyHandler<O>, amount: Float, unit: EnergyUnit, context: C, otherContext: C.() -> O = EnergyContext::defaultOtherImplementation): Float {
        if (amount <= 0f) return 0f
        val space = getEnergySpace(unit, context)
        space?.also { if (it <= 0f) return 0f }
        val extracted = target.removeEnergy(this, amount.run { coerceAtMost(space ?: return@run this) }, unit, context.otherContext())
        if (extracted == 0f) return 0f
        val added = this.addEnergy(target, extracted, unit, context)
        if (extracted - added > 0f) target.addEnergyDirect(extracted - added, unit, context.otherContext())
        return added
    }

    fun getMinimumEnergy(unit: EnergyUnit, context: C): Float? = null
    fun getMaximumEnergy(unit: EnergyUnit, context: C): Float? = null
    fun getEnergyBounds(unit: EnergyUnit, context: C): ClosedRange<Float>? {
        return getMinimumEnergy(unit, context)?.rangeTo((getMaximumEnergy(unit, context) ?: return null))
    }
    fun getEnergySpace(unit: EnergyUnit, context: C): Float? = null

}