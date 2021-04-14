package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.adaptation.sign.LCCSign
import com.joshmanisdabomb.lcc.adaptation.sign.LCCSignType
import net.minecraft.block.SignBlock
import net.minecraft.block.WallSignBlock

class LCCSignBlock(settings: Settings, override val lcc_sign: LCCSignType) : SignBlock(settings, lcc_sign.base), LCCSign

class LCCWallSignBlock(settings: Settings, override val lcc_sign: LCCSignType) : WallSignBlock(settings, lcc_sign.base), LCCSign