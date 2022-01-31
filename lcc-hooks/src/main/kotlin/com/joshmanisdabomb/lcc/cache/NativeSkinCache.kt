package com.joshmanisdabomb.lcc.cache

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.texture.NativeImage

@Environment(EnvType.CLIENT)
interface NativeSkinCache {

    val image: NativeImage

}