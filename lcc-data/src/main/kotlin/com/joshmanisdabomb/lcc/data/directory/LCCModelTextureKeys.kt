package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.directory.ThingDirectory
import net.minecraft.data.client.model.TextureKey

object LCCModelTextureKeys : ThingDirectory<TextureKey, Unit>() {

    val t0 by createWithName { TextureKey.of("0", null) }
    val t1 by createWithName { TextureKey.of("1", null) }
    val t2 by createWithName { TextureKey.of("2", null) }
    val t3 by createWithName { TextureKey.of("3", null) }
    val t4 by createWithName { TextureKey.of("4", null) }
    val t5 by createWithName { TextureKey.of("5", null) }

    val layer1 by createWithName { TextureKey.of(it, null) }

}