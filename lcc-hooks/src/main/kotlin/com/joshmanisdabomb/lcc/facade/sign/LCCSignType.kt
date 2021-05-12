package com.joshmanisdabomb.lcc.facade.sign

import net.minecraft.util.Identifier
import net.minecraft.util.SignType

data class LCCSignType(val texture: Identifier, val base: SignType = SignType.OAK)