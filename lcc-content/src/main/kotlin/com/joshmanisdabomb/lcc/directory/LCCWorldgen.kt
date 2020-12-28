package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.world.carver.WastelandCaveCarver
import com.joshmanisdabomb.lcc.world.carver.WastelandRavineCarver
import com.joshmanisdabomb.lcc.world.decorator.NearLavaLakeDecorator
import com.joshmanisdabomb.lcc.world.feature.OilGeyserFeature
import com.joshmanisdabomb.lcc.world.feature.SmallGeodeFeature
import com.joshmanisdabomb.lcc.world.feature.config.SmallGeodeFeatureConfig
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Blocks
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.ProbabilityConfig
import net.minecraft.world.gen.UniformIntDistribution
import net.minecraft.world.gen.carver.Carver
import net.minecraft.world.gen.carver.ConfiguredCarver
import net.minecraft.world.gen.decorator.*
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

object LCCWorldgen {
    fun init() {
        LCCFeatures.init()
        LCCDecorators.init()
        LCCConfiguredFeatures.init()
        LCCCarvers.init()
        LCCConfiguredCarvers.init()
        LCCConfiguredSurfaceBuilders.init()

        biomeModifications()
    }

    fun biomeModifications() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.biome != LCCBiomes.wasteland }, GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.uranium));
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.biome != LCCBiomes.wasteland }, GenerationStep.Feature.UNDERGROUND_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.topaz_geode));
    }
}

object LCCFeatures : RegistryDirectory<Feature<*>, Unit>() {

    override val _registry by lazy { Registry.FEATURE }

    override fun id(path: String) = LCC.id(path)

    val topaz_geode by create { SmallGeodeFeature(SmallGeodeFeatureConfig.codec) }

    val oil_geyser by create { OilGeyserFeature(DefaultFeatureConfig.CODEC) }

}

object LCCConfiguredFeatures : RegistryDirectory<ConfiguredFeature<*, *>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_FEATURE }

    override fun id(path: String) = LCC.id(path)

    val abundant_coal by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.defaultState, 14)).rangeOf(128).spreadHorizontally().repeat(26) }
    val abundant_iron by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.defaultState, 11)).rangeOf(128).spreadHorizontally().repeat(26) }
    val abundant_copper by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COPPER_ORE.defaultState, 14)).rangeOf(128).spreadHorizontally().repeat(15) }

    val oil_geyser by create { LCCFeatures.oil_geyser.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.01f, 1))) }
    val oil_hidden by create { Feature.NO_SURFACE_ORE.configure(OreFeatureConfig(BlockMatchRuleTest(LCCBlocks.cracked_mud), LCCBlocks.oil.defaultState, 6)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(59, 59, 67))).spreadHorizontally().applyChance(4) }

    val uranium by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, LCCBlocks.uranium_ore.defaultState, 4)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(3, 12, 128))).spreadHorizontally().applyChance(2) }
    val uranium_wasteland by create { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, LCCBlocks.uranium_ore.defaultState, 4)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(3, 12, 128))).spreadHorizontally().applyChance(2).repeat(3) }

    val topaz_geode by create { LCCFeatures.topaz_geode.configure(SmallGeodeFeatureConfig(43, LCCBlocks.topaz_block, LCCBlocks.budding_topaz, LCCBlocks.rhyolite.defaultState, LCCBlocks.pumice.defaultState)).decorate(LCCDecorators.near_lava_lake.configure(DecoratorConfig.DEFAULT)).spreadHorizontally().applyChance(2) }

    val classic_tree by create { Feature.TREE.configure((TreeFeatureConfig.Builder(SimpleBlockStateProvider(Blocks.OAK_LOG.defaultState), SimpleBlockStateProvider(LCCBlocks.classic_leaves.defaultState), BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), StraightTrunkPlacer(4, 2, 0), TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()) }

}

object LCCCarvers : RegistryDirectory<Carver<*>, Unit>() {

    override val _registry by lazy { Registry.CARVER }

    override fun id(path: String) = LCC.id(path)

    val wasteland_cave by create { WastelandCaveCarver(ProbabilityConfig.CODEC, 256) }
    val wasteland_ravine by create { WastelandRavineCarver(ProbabilityConfig.CODEC) }

}

object LCCConfiguredCarvers : RegistryDirectory<ConfiguredCarver<*>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_CARVER }

    override fun id(path: String) = LCC.id(path)

    val wasteland_cave by create { LCCCarvers.wasteland_cave.configure(ProbabilityConfig(0.03F)) }
    val wasteland_ravine by create { LCCCarvers.wasteland_ravine.configure(ProbabilityConfig(0.03F)) }

}

object LCCConfiguredSurfaceBuilders : RegistryDirectory<ConfiguredSurfaceBuilder<*>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_SURFACE_BUILDER }

    override fun id(path: String) = LCC.id(path)

    val wasteland by create { SurfaceBuilder.DEFAULT.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState)) }

}

object LCCDecorators : RegistryDirectory<Decorator<*>, Unit>() {

    override val _registry by lazy { Registry.DECORATOR }

    override fun id(path: String) = LCC.id(path)

    val near_lava_lake by create { NearLavaLakeDecorator(NopeDecoratorConfig.CODEC) }

}