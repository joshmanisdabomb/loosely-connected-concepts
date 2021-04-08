package com.joshmanisdabomb.lcc

import com.google.common.base.CaseFormat
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletDirectory
import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object LCC : ModInitializer {

    const val modid = "lcc"

    override fun onInitialize() {
        LCCGroups.init()
        LCCSounds.init()
        LCCFluids.init()
        LCCBlocks.init()
        LCCItems.init()
        LCCEntities.init()
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
        LCCWorldgen.init()
        LCCBiomes.init()
        LCCTags.init()
        LCCChunkTickets.init()
        LCCCriteria.init()

        GauntletDirectory.init()
    }

    fun id(path: String) = Identifier(modid, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, path))

    fun block(name: String) = id("block/$name")
    fun item(name: String) = id("item/$name")
    fun entity(name: String) = id("entity/$name")
    fun gui(name: String) = id("textures/gui/$name.png")

}