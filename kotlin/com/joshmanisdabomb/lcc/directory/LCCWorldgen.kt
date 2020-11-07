package com.joshmanisdabomb.lcc.directory

import net.minecraft.block.Blocks
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig

object LCCWorldgen {
    fun init() {
        LCCConfiguredSurfaceBuilders.init()
        LCCConfiguredFeatures.init()
    }
}

object LCCConfiguredSurfaceBuilders : RegistryDirectory<ConfiguredSurfaceBuilder<*>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_SURFACE_BUILDER }

    val wasteland by create { SurfaceBuilder.DEFAULT.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState)) }

}

object LCCConfiguredFeatures : RegistryDirectory<ConfiguredFeature<*, *>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_FEATURE }

    val abundant_coal by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.defaultState, 17)).rangeOf(128).spreadHorizontally().repeat(30) }
    val abundant_iron by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.defaultState, 12)).rangeOf(128).spreadHorizontally().repeat(18) }

}