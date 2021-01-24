package com.joshmanisdabomb.lcc.energy.base

import com.joshmanisdabomb.lcc.energy.EnergyUnit

interface EnergyStorage<C : EnergyContext> : EnergyHandler<C> {

    override fun isEnergyUsable(context: C) = getRawEnergy(context) != null

    fun getRawEnergy(context: C): Float?
    fun setRawEnergy(context: C, amount: Float)

    fun getRawEnergyMultiplier(context: C) = 1f
    fun getRawEnergyMinimum(context: C) = 0f
    fun getRawEnergyMaximum(context: C): Float?
    fun getRawEnergyBounds(context: C): ClosedRange<Float>? {
        return getRawEnergyMinimum(context).rangeTo(getRawEnergyMaximum(context) ?: return null)
    }

    fun getEnergy(unit: EnergyUnit, context: C): Float? {
        getRawEnergy(context)?.also { return (it / getRawEnergyMultiplier(context)) * unit.multiplier }
        return null
    }

    override fun getMinimumEnergy(unit: EnergyUnit, context: C) = getRawEnergyMinimum(context).div(getRawEnergyMultiplier(context)).times(unit.multiplier)

    override fun getMaximumEnergy(unit: EnergyUnit, context: C) = getRawEnergyMaximum(context)?.div(getRawEnergyMultiplier(context))?.times(unit.multiplier)

    override fun getEnergyBounds(unit: EnergyUnit, context: C): ClosedRange<Float>? {
        return getMinimumEnergy(unit, context)..(getMaximumEnergy(unit, context) ?: return null)
    }

    override fun getEnergySpace(unit: EnergyUnit, context: C): Float? {
        return getMaximumEnergy(unit, context)?.minus(getEnergy(unit, context) ?: return null)
    }

    fun getEnergyFill(context: C): Float? {
        return getRawEnergy(context)?.div(getRawEnergyMaximum(context) ?: return null)
    }

    fun setEnergyDirect(amount: Float, unit: EnergyUnit, context: C): Float? {
        getRawEnergy(context)?.also {
            val before = it
            var after = ((amount / unit.multiplier) * getRawEnergyMultiplier(context))
            getRawEnergyBounds(context)?.apply { after = after.coerceIn(this) }
            if (after == before || onEnergyChanged(context, before)) {
                setRawEnergy(context, after)
                return (after / getRawEnergyMultiplier(context)) * unit.multiplier
            }
            return before
        }
        return null
    }

    fun <O : EnergyContext> setEnergy(amount: Float, unit: EnergyUnit, from: EnergyHandler<O>, context: C, otherContext: C.() -> O = EnergyContext::defaultOtherImplementation) = setEnergyDirect(amount, unit, context)

    override fun addEnergyDirect(amount: Float, unit: EnergyUnit, context: C): Float {
        val space = getEnergySpace(unit, context)
        space?.also { if (it <= 0f) return 0f }
        val get = getEnergy(unit, context) ?: return 0f
        val set = setEnergyDirect(get.plus(amount.run { coerceAtMost(space ?: return@run this) }), unit, context) ?: return 0f
        return set - get
    }

    override fun addEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: C): Float = addEnergyDirect(amount, unit, context)

    override fun removeEnergyDirect(amount: Float, unit: EnergyUnit, context: C): Float {
        val available = amount - getMinimumEnergy(unit, context)
        if (available <= 0f) return 0f
        val get = getEnergy(unit, context) ?: return 0f
        val set = setEnergyDirect(get.minus(amount.run { coerceAtLeast(available) }), unit, context) ?: return 0f
        return get - set
    }

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: C): Float = removeEnergyDirect(amount, unit, context)

    fun onEnergyChanged(context: C, before: Float) = true

}