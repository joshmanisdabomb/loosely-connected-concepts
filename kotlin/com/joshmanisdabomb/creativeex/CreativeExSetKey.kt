package com.joshmanisdabomb.creativeex

interface CreativeExSetKey {

    val selectionWidth get() = 2
    val selectionHeight get() = 2

    val selectionColor: FloatArray
    val selectionHoverColor get() = floatArrayOf(1.0f, 1.0f, 1.0f, 0.5019608f)

}