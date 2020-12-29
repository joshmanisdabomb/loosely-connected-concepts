package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCTags
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.block.Block
import net.minecraft.tag.Tag
import java.nio.file.Paths
import kotlin.system.exitProcess

object LCCData : PreLaunchEntrypoint {

    val handler by lazy { DataGeneratorHandler.create(Paths.get("../lcc-content/src/generated/resources")) }

    val tags by lazy { handler.tags }

    override fun onPreLaunch() {
        LCC.onInitialize()

        tags.block(LCCTags.nether_reactor_base as Tag.Identified<Block>).append(LCCBlocks.all.values.random())

        handler.run()
        exitProcess(0)
    }

}