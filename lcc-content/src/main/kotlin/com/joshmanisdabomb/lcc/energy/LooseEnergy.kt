package com.joshmanisdabomb.lcc.energy

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.extensions.decimalFormat

object LooseEnergy : EnergyUnit {

    override val id = LCC.id("energy")
    override val name = "Loose Energy"
    override val units = "LE"
    override val tags = arrayOf(LCC.id("electrical"))

    override val multiplier = 5f

    override fun display(amount: Float) = amount.decimalFormat(force = true)

}