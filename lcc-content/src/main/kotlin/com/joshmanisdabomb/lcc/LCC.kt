package com.joshmanisdabomb.lcc

import com.google.common.base.CaseFormat
import com.joshmanisdabomb.lcc.abstracts.challenges.LCCAltarChallenges
import com.joshmanisdabomb.lcc.abstracts.computing.medium.LCCDigitalMediums
import com.joshmanisdabomb.lcc.abstracts.computing.module.LCCComputerModules
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitions
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletDirectory
import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object LCC : ModInitializer {

    var initialised = false
    const val modid = "lcc"

    override fun onInitialize() {
        if (initialised) return

        LCCRegistries.init()

        LCCDigitalMediums.init()
        LCCComputerModules.init()
        LCCPartitions.init()

        LCCGroups.init()
        LCCSounds.init()
        LCCFluids.init()
        LCCSignTypes.init()
        LCCTags.init()
        LCCBlocks.init()
        LCCEntities.init()
        LCCBoatTypes.init()
        LCCItems.init()
        LCCBlockItems.init()
        LCCBlockEntities.init()
        LCCEvents.init()
        LCCScreenHandlers.init()
        LCCPacketsToServer.init()
        LCCParticles.init()
        LCCRecipeTypes.init()
        LCCRecipeSerializers.init()
        LCCDamage.init()
        LCCEffects.init()
        LCCRuleTests.init()
        LCCWorldgen.init()
        LCCBiomes.init()
        LCCChunkTickets.init()
        LCCCriteria.init()
        LCCCommands.init()
        LCCPointsOfInterest.init()

        LCCAltarChallenges.init()
        GauntletDirectory.init()

        initialised = true
    }

    fun id(path: String) = Identifier(modid, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, path))

    fun block(name: String) = id("block/$name")
    fun item(name: String) = id("item/$name")
    fun entity(name: String) = id("entity/$name")
    fun gui(name: String) = id("textures/gui/$name.png")

}