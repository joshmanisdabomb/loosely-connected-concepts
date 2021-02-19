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
import net.minecraft.world.gen.carver.CarverConfig
import net.minecraft.world.gen.carver.ConfiguredCarver
import net.minecraft.world.gen.decorator.*
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig
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

object LCCFeatures : BasicDirectory<Feature<out FeatureConfig>, Unit>(), RegistryDirectory<Feature<out FeatureConfig>, Unit, Unit> {

    override val registry by lazy { Registry.FEATURE }

    override fun regId(name: String) = LCC.id(name)

    val topaz_geode by entry(::initialiser) { SmallGeodeFeature(SmallGeodeFeatureConfig.codec) }

    val oil_geyser by entry(::initialiser) { OilGeyserFeature(DefaultFeatureConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredFeatures : BasicDirectory<ConfiguredFeature<out FeatureConfig, out Feature<out FeatureConfig>>, Unit>(), RegistryDirectory<ConfiguredFeature<out FeatureConfig, out Feature<out FeatureConfig>>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_FEATURE }

    override fun regId(name: String) = LCC.id(name)

    val abundant_coal by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.defaultState, 14)).rangeOf(-64, 128).spreadHorizontally().repeat(26) }
    val abundant_iron by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.defaultState, 11)).rangeOf(-64, 128).spreadHorizontally().repeat(26) }
    val abundant_copper by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.COPPER_ORE.defaultState, 14)).rangeOf(-64, 128).spreadHorizontally().repeat(15) }

    val oil_geyser by entry(::initialiser) { LCCFeatures.oil_geyser.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.01f, 1))) }
    val oil_hidden by entry(::initialiser) { Feature.NO_SURFACE_ORE.configure(OreFeatureConfig(BlockMatchRuleTest(LCCBlocks.cracked_mud), LCCBlocks.oil.defaultState, 6)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(59, 59, 67))).spreadHorizontally().applyChance(4) }

    val uranium by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, LCCBlocks.uranium_ore.defaultState, 4)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(3, 12, 128))).spreadHorizontally().applyChance(2) }
    val uranium_wasteland by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, LCCBlocks.uranium_ore.defaultState, 4)).decorate(Decorator.RANGE.configure(RangeDecoratorConfig(3, 12, 128))).spreadHorizontally().applyChance(2).repeat(3) }

    val topaz_geode by entry(::initialiser) { LCCFeatures.topaz_geode.configure(SmallGeodeFeatureConfig(37, LCCBlocks.topaz_block, LCCBlocks.budding_topaz, LCCBlocks.rhyolite.defaultState, LCCBlocks.pumice.defaultState)).decorate(LCCDecorators.near_lava_lake.configure(DecoratorConfig.DEFAULT)).spreadHorizontally().applyChance(2) }

    val classic_tree by entry(::initialiser) { Feature.TREE.configure((TreeFeatureConfig.Builder(SimpleBlockStateProvider(Blocks.OAK_LOG.defaultState), SimpleBlockStateProvider(LCCBlocks.classic_leaves.defaultState), BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), StraightTrunkPlacer(4, 2, 0), TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()) }

    override fun defaultProperties(name: String) = Unit

}

object LCCCarvers : BasicDirectory<Carver<out CarverConfig>, Unit>(), RegistryDirectory<Carver<out CarverConfig>, Unit, Unit> {

    override val registry by lazy { Registry.CARVER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_cave by entry(::initialiser) { WastelandCaveCarver(ProbabilityConfig.CODEC, 256) }
    val wasteland_ravine by entry(::initialiser) { WastelandRavineCarver(ProbabilityConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredCarvers : BasicDirectory<ConfiguredCarver<out CarverConfig>, Unit>(), RegistryDirectory<ConfiguredCarver<out CarverConfig>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_CARVER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_cave by entry(::initialiser) { LCCCarvers.wasteland_cave.configure(ProbabilityConfig(0.03F)) }
    val wasteland_ravine by entry(::initialiser) { LCCCarvers.wasteland_ravine.configure(ProbabilityConfig(0.03F)) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredSurfaceBuilders : BasicDirectory<ConfiguredSurfaceBuilder<out SurfaceConfig>, Unit>(), RegistryDirectory<ConfiguredSurfaceBuilder<out SurfaceConfig>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_SURFACE_BUILDER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland by entry(::initialiser) { SurfaceBuilder.DEFAULT.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState)) }

    override fun defaultProperties(name: String) = Unit

}

object LCCDecorators : BasicDirectory<Decorator<out DecoratorConfig>, Unit>(), RegistryDirectory<Decorator<out DecoratorConfig>, Unit, Unit> {

    override val registry by lazy { Registry.DECORATOR }

    override fun regId(name: String) = LCC.id(name)

    val near_lava_lake by entry(::initialiser) { NearLavaLakeDecorator(NopeDecoratorConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}