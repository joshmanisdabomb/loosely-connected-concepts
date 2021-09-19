package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.facade.piston.AbstractPistonBlock

class RubberPistonBlock(sticky: Boolean, settings: Settings) : AbstractPistonBlock(sticky, settings) {

    override val head get() = LCCBlocks.rubber_piston_head

}
