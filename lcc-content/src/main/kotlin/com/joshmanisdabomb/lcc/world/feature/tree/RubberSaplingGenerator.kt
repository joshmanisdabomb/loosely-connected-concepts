package com.joshmanisdabomb.lcc.world.feature.tree

import com.joshmanisdabomb.lcc.directory.LCCConfiguredFeatures
import net.minecraft.block.sapling.SaplingGenerator
import java.util.*

class RubberSaplingGenerator : SaplingGenerator() {

    override fun getTreeFeature(random: Random, bees: Boolean) = LCCConfiguredFeatures.rubber_tree

}