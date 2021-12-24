package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.prefix
import net.minecraft.util.StringIdentifiable

abstract class ComputerModule {

    val id get() = LCCRegistries.computer_modules.getKey(this).orElseThrow(::RuntimeException).value

    val lootTableId get() = id.prefix("lcc/computer_module/", "")

}