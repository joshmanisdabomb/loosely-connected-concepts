package com.joshmanisdabomb.lcc.facade.piston

import net.minecraft.block.PistonBlock
import net.minecraft.block.enums.PistonType

interface LCCPistonHead {

    val bases: Map<PistonType, PistonBlock>

}