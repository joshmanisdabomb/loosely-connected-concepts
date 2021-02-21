package com.joshmanisdabomb.lcc.abstracts.gauntlet

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.BasicDirectory

object GauntletDirectory : BasicDirectory<GauntletAction<out GauntletActorInstance>, Unit>() {

    val uppercut by entry(::initialiser) { UppercutGauntletAction }
    val punch by entry(::initialiser) { PunchGauntletAction }
    //leap/stomp
    //falcon punch, three explosion in a line, long cast time

    fun <G : GauntletAction<A>, A : GauntletActorInstance> initialiser(input: G, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit

}