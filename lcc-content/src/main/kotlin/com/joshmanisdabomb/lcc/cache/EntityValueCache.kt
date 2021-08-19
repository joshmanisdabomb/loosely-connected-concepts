package com.joshmanisdabomb.lcc.cache

import net.minecraft.util.math.Vec3d

interface EntityValueCache {

    val lcc_fullVelocity: Vec3d

    val lcc_fullVelocityBeforeCollides: Vec3d

}