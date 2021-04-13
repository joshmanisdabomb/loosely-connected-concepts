package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.adaptation.sign.LCCSign
import com.joshmanisdabomb.lcc.adaptation.sign.LCCSignType
import net.minecraft.block.SignBlock
import net.minecraft.block.WallSignBlock

class LCCSignBlock(settings: Settings, override val type: LCCSignType) : SignBlock(settings, type.base), LCCSign

class LCCWallSignBlock(settings: Settings, override val type: LCCSignType) : WallSignBlock(settings, type.base), LCCSign