package com.joshmanisdabomb.lcc.trait;

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

interface LCCFluidTrait {

    @JvmDefault
    @Environment(EnvType.CLIENT)
    fun lcc_fogColor(): FloatArray? = null

    @JvmDefault
    @Environment(EnvType.CLIENT)
    fun lcc_fogDensity(): Float? = null

}
