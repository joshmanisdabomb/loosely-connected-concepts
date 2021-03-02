package com.joshmanisdabomb.lcc.world.feature.tree

import com.joshmanisdabomb.lcc.directory.LCCConfiguredFeatures
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import java.util.*

class RubberSaplingGenerator : SaplingGenerator() {

    override fun createTreeFeature(random: Random, bees: Boolean): ConfiguredFeature<TreeFeatureConfig, *>? {
        return LCCConfiguredFeatures.rubber_tree
    }

}