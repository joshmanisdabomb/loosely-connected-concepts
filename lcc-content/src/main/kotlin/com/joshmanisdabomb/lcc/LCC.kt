package com.joshmanisdabomb.lcc

import com.google.common.base.CaseFormat
import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object LCC : ModInitializer {

    const val modid = "lcc"

    override fun onInitialize() {
        LCCSounds.init()
        LCCBlocks.init()
        LCCFluids.init()
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
        LCCCommands.init()
        LCCWorldgen.init()
        LCCBiomes.init()
        LCCTags.init()
    }

    fun id(path: String): Identifier = Identifier(modid, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, path))

    fun gui(name: String): Identifier = id("textures/gui/$name.png")

}