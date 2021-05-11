package com.joshmanisdabomb.lcc.adaptation;

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

interface LCCExtendedFluid {

    @JvmDefault
    @Environment(EnvType.CLIENT)
    fun lcc_fogColor(): FloatArray? = null

    @JvmDefault
    @Environment(EnvType.CLIENT)
    fun lcc_fogDensity(): Float? = null

}
