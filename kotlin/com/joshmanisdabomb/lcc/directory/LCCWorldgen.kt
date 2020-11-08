package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.world.feature.OilGeyserFeature
import net.minecraft.block.Blocks
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.RangeDecoratorConfig
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig

object LCCWorldgen {
    fun init() {
        LCCFeatures.init()
        LCCConfiguredFeatures.init()
        LCCConfiguredSurfaceBuilders.init()
    }
}

object LCCFeatures : RegistryDirectory<Feature<*>, Unit>() {

    override val _registry by lazy { Registry.FEATURE }

    val oil_geyser by create { OilGeyserFeature(DefaultFeatureConfig.CODEC) }

}

object LCCConfiguredFeatures : RegistryDirectory<ConfiguredFeature<*, *>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_FEATURE }

    val abundant_coal by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.defaultState, 17)).rangeOf(128).spreadHorizontally().repeat(30) }
    val abundant_iron by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.defaultState, 12)).rangeOf(128).spreadHorizontally().repeat(18) }

    val oil_geyser by create { LCCFeatures.oil_geyser.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.01f, 1))) }
    val oil_hidden by create { Feature.NO_SURFACE_ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, LCCBlocks.oil.defaultState, 6)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(50, 50, 67))).spreadHorizontally().applyChance(8) }

    //todo uranium, more abundant in wasteland

}

object LCCConfiguredSurfaceBuilders : RegistryDirectory<ConfiguredSurfaceBuilder<*>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_SURFACE_BUILDER }

    val wasteland by create { SurfaceBuilder.DEFAULT.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState)) }

}