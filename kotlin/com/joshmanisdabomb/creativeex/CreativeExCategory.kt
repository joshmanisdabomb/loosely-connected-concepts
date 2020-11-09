package com.joshmanisdabomb.creativeex

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

interface CreativeExCategory {

    val groupColor get() = 0xFF5B5B5B

    val sortValue: Int

}