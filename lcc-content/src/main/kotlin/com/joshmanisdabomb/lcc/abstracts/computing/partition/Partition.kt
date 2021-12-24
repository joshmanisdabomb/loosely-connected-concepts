package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.directory.LCCRegistries

abstract class Partition {

    val id get() = LCCRegistries.computer_partitions.getKey(this).orElseThrow(::RuntimeException).value

}