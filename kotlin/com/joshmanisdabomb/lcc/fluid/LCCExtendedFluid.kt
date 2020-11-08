package com.joshmanisdabomb.lcc.fluid;

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

interface LCCExtendedFluid {

    @JvmDefault
    @Environment(EnvType.CLIENT)
    fun lcc_fogColor(): Array<Float>? = null

    @JvmDefault
    @Environment(EnvType.CLIENT)
    fun lcc_fogDensity(): Float? = null

}
