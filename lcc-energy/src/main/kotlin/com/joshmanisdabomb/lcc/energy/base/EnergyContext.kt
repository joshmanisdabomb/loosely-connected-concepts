package com.joshmanisdabomb.lcc.energy.base

abstract class EnergyContext {

    internal fun <C : EnergyContext> defaultOtherImplementation(): C {
        try {
            return defaultOther() as C
        } catch (e: ClassCastException) {
            throw RuntimeException("Cannot use default implementation as receiving energy handler does not accept returned energy context type.")
        }
    }

    abstract fun defaultOther(): EnergyContext

}