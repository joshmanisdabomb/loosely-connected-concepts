package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import net.minecraft.data.client.model.TextureKey

object LCCModelTextureKeys : AdvancedDirectory<String, TextureKey, Unit, Unit>() {

    val t0 by entry(::initialiser) { "0" }
    val t1 by entry(::initialiser) { "1" }
    val t2 by entry(::initialiser) { "2" }
    val t3 by entry(::initialiser) { "3" }
    val t4 by entry(::initialiser) { "4" }
    val t5 by entry(::initialiser) { "5" }
    val t6 by entry(::initialiser) { "6" }
    val t7 by entry(::initialiser) { "7" }
    val t8 by entry(::initialiser) { "8" }
    val t9 by entry(::initialiser) { "9" }

    val layer1 by entry(::initialiser) { name }
    val overlay by entry(::initialiser) { name }

    fun initialiser(input: String, context: DirectoryContext<Unit>, parameters: Unit) = DataUtils.textureKey(input, null)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}