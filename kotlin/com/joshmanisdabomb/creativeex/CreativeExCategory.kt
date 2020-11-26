package com.joshmanisdabomb.creativeex

interface CreativeExCategory {

    val groupColor get() = 0xFF5B5B5B

    val sortValue: Int
    val comparator: (CreativeExStackDisplay) -> Int

}