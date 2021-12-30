package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.directory.LCCRegistries
import net.minecraft.util.Formatting

abstract class PartitionType {

    abstract val nameColor: Formatting
    open val noFreeSpace: Boolean = false

    val id get() = LCCRegistries.computer_partitions.getKey(this).orElseThrow(::RuntimeException).value
    open val translationKey get() = "computing.${id.namespace}.partition.${id.path}"

}