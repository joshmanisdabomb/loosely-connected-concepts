package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory

object LCCAltarChallenges : BasicDirectory<AltarChallenge, Unit>(), RegistryDirectory<AltarChallenge, Unit, Unit> {

    override val registry = LCCRegistries.altar_challenges

    override fun regId(name: String) = LCC.id(name)

    val minesweeper by entry(::initialiser) { MinesweeperAltarChallenge() }
    val arena by entry(::initialiser) { ArenaAltarChallenge() }

    override fun defaultProperties(name: String) = Unit

}
