package com.joshmanisdabomb.lcc.energy

class EnergyTransaction(vararg actions: (amount: Float) -> Float?) {

    private val actions = mutableListOf(*actions)

    fun include(action: (amount: Float) -> Float?) = includeAll(action)

    fun includeAll(vararg action: (amount: Float) -> Float?) = actions.addAll(action).let { this }

    fun includeAll(actions: List<(amount: Float) -> Float?>) = includeAll(*actions.toTypedArray())

    fun run(amount: Float): Float {
        var a = 0f
        for (action in actions) {
            if (a >= amount) break
            a += action(amount - a) ?: 0f
        }
        return a
    }

}