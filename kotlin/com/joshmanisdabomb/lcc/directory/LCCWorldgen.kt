package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.world.carver.WastelandCaveCarver
import com.joshmanisdabomb.lcc.world.carver.WastelandRavineCarver
import com.joshmanisdabomb.lcc.world.feature.OilGeyserFeature
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Blocks
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.ProbabilityConfig
import net.minecraft.world.gen.carver.Carver
import net.minecraft.world.gen.carver.CaveCarver
import net.minecraft.world.gen.carver.ConfiguredCarver
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
        LCCCarvers.init()
        LCCConfiguredCarvers.init()
        LCCConfiguredSurfaceBuilders.init()

        biomeModifications()
    }

    fun biomeModifications() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.biome != LCCBiomes.wasteland }, GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.uranium));
    }
}

object LCCFeatures : RegistryDirectory<Feature<*>, Unit>() {

    override val _registry by lazy { Registry.FEATURE }

    val oil_geyser by create { OilGeyserFeature(DefaultFeatureConfig.CODEC) }

}

object LCCConfiguredFeatures : RegistryDirectory<ConfiguredFeature<*, *>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_FEATURE }

    val abundant_coal by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.defaultState, 14)).rangeOf(128).spreadHorizontally().repeat(26) }
    val abundant_iron by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.defaultState, 11)).rangeOf(128).spreadHorizontally().repeat(26) }
    val abundant_copper by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COPPER_ORE.defaultState, 14)).rangeOf(128).spreadHorizontally().repeat(15) }

    val oil_geyser by create { LCCFeatures.oil_geyser.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.01f, 1))) }
    val oil_hidden by create { Feature.NO_SURFACE_ORE.configure(OreFeatureConfig(BlockMatchRuleTest(LCCBlocks.cracked_mud), LCCBlocks.oil.defaultState, 6)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(59, 59, 67))).spreadHorizontally().applyChance(4) }

    val uranium by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, LCCBlocks.uranium_ore.defaultState, 4)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(3, 12, 128))).spreadHorizontally().applyChance(2) }
    val uranium_wasteland by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, LCCBlocks.uranium_ore.defaultState, 4)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(3, 12, 128))).spreadHorizontally().applyChance(2).repeat(3) }

}

object LCCCarvers : RegistryDirectory<Carver<*>, Unit>() {

    override val _registry by lazy { Registry.CARVER }

    val wasteland_cave by create { WastelandCaveCarver(ProbabilityConfig.CODEC, 256) }
    val wasteland_ravine by create { WastelandRavineCarver(ProbabilityConfig.CODEC) }

}

object LCCConfiguredCarvers : RegistryDirectory<ConfiguredCarver<*>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_CARVER }

    val wasteland_cave by create { LCCCarvers.wasteland_cave.configure(ProbabilityConfig(0.03F)) }
    val wasteland_ravine by create { LCCCarvers.wasteland_ravine.configure(ProbabilityConfig(0.03F)) }

}

object LCCConfiguredSurfaceBuilders : RegistryDirectory<ConfiguredSurfaceBuilder<*>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_SURFACE_BUILDER }

    val wasteland by create { SurfaceBuilder.DEFAULT.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState)) }

}