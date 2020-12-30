package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.directory.ThingDirectory
import net.minecraft.data.client.model.TextureKey

object ModelTextureKeys : ThingDirectory<TextureKey, Unit>() {

    val t0 by createWithName { TextureKey.method_27044("0", null) }
    val t1 by createWithName { TextureKey.method_27044("1", null) }
    val t2 by createWithName { TextureKey.method_27044("2", null) }
    val t3 by createWithName { TextureKey.method_27044("3", null) }

}