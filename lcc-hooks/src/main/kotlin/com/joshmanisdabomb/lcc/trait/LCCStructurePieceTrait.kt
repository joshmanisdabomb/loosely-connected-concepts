package com.joshmanisdabomb.lcc.trait

import net.minecraft.util.math.BlockBox

interface LCCStructurePieceTrait {

    fun getAdaptationBoundingBox(original: BlockBox) = original

}