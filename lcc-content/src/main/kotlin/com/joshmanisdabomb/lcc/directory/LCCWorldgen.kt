package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.world.carver.WastelandCaveCarver
import com.joshmanisdabomb.lcc.world.carver.WastelandRavineCarver
import com.joshmanisdabomb.lcc.world.decorator.NearLavaLakeDecorator
import com.joshmanisdabomb.lcc.world.feature.OilGeyserFeature
import com.joshmanisdabomb.lcc.world.feature.RubberTreeFeature
import com.joshmanisdabomb.lcc.world.feature.SmallGeodeFeature
import com.joshmanisdabomb.lcc.world.feature.config.SmallGeodeFeatureConfig
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Blocks
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.util.math.floatprovider.TrapezoidFloatProvider
import net.minecraft.util.math.floatprovider.UniformFloatProvider
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.UniformIntDistribution
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.carver.*
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.decorator.NopeDecoratorConfig
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
        //Ores
        with (BiomeSelectors.foundInOverworld().and { it.biome != LCCBiomes.wasteland }) {
            BiomeModifications.addFeature(this, GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.uranium_stone));
            BiomeModifications.addFeature(this, GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.uranium_deepslate));
            BiomeModifications.addFeature(this, GenerationStep.Feature.UNDERGROUND_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.topaz_geode));
        }

        //Rubber Tree
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { when (it.biome.category) { Biome.Category.SAVANNA, Biome.Category.PLAINS -> true; else -> false } }, GenerationStep.Feature.VEGETAL_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.rubber_trees_rare))
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { when (it.biome.category) { Biome.Category.MESA -> true; else -> false } }, GenerationStep.Feature.VEGETAL_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.rubber_trees_uncommon))
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { when (it.biome.category) { Biome.Category.JUNGLE -> true; else -> false } }, GenerationStep.Feature.VEGETAL_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.rubber_trees_common))
    }
}

object LCCFeatures : BasicDirectory<Feature<out FeatureConfig>, Unit>(), RegistryDirectory<Feature<out FeatureConfig>, Unit, Unit> {

    override val registry by lazy { Registry.FEATURE }

    override fun regId(name: String) = LCC.id(name)

    val topaz_geode by entry(::initialiser) { SmallGeodeFeature(SmallGeodeFeatureConfig.codec) }

    val oil_geyser by entry(::initialiser) { OilGeyserFeature(DefaultFeatureConfig.CODEC) }

    val rubber_tree by entry(::initialiser) { RubberTreeFeature(TreeFeatureConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredFeatures : BasicDirectory<ConfiguredFeature<out FeatureConfig, out Feature<out FeatureConfig>>, Unit>(), RegistryDirectory<ConfiguredFeature<out FeatureConfig, out Feature<out FeatureConfig>>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_FEATURE }

    override fun regId(name: String) = LCC.id(name)

    val abundant_coal by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, Blocks.COAL_ORE.defaultState, 14)).averageDepth(YOffset.fixed(64), 64).spreadHorizontally().repeat(15) }
    val abundant_iron_stone by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, Blocks.IRON_ORE.defaultState, 4)).averageDepth(YOffset.fixed(0), 64).spreadHorizontally().repeat(15) }
    val abundant_iron_deepslate by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_IRON_ORE.defaultState, 14)).averageDepth(YOffset.getBottom(), 64).spreadHorizontally().repeat(15) }
    val abundant_copper by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, Blocks.COPPER_ORE.defaultState, 14)).averageDepth(YOffset.fixed(48), 30).spreadHorizontally().repeat(15) }

    val oil_geyser by entry(::initialiser) { LCCFeatures.oil_geyser.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.015f, 1))) }
    val oil_hidden by entry(::initialiser) { Feature.SCATTERED_ORE.configure(OreFeatureConfig(BlockMatchRuleTest(LCCBlocks.cracked_mud), LCCBlocks.oil.defaultState, 5)).averageDepth(YOffset.fixed(63), 3).spreadHorizontally().applyChance(4) }

    val uranium_stone by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, LCCBlocks.uranium_ore.defaultState, 4)).averageDepth(YOffset.getBottom(), 160).spreadHorizontally() }
    val uranium_deepslate by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, LCCBlocks.deepslate_uranium_ore.defaultState, 4)).averageDepth(YOffset.getBottom(), 160).spreadHorizontally() }
    val uranium_wasteland by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, LCCBlocks.deepslate_uranium_ore.defaultState, 4)).rangeOf(YOffset.getBottom(), YOffset.fixed(64)).spreadHorizontally().repeat(3).applyChance(2) }

    val topaz_geode by entry(::initialiser) { LCCFeatures.topaz_geode.configure(SmallGeodeFeatureConfig(37, LCCBlocks.topaz_block, LCCBlocks.budding_topaz, LCCBlocks.rhyolite.defaultState, LCCBlocks.pumice.defaultState)).decorate(LCCDecorators.near_lava_lake.configure(DecoratorConfig.DEFAULT)).spreadHorizontally().applyChance(2) }

    val classic_tree by entry(::initialiser) { Feature.TREE.configure((TreeFeatureConfig.Builder(SimpleBlockStateProvider(Blocks.OAK_LOG.defaultState), StraightTrunkPlacer(4, 2, 0), SimpleBlockStateProvider(LCCBlocks.classic_leaves.defaultState), BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()) }
    val rubber_tree by entry(::initialiser) { LCCFeatures.rubber_tree.configure(TreeFeatureConfig.Builder(SimpleBlockStateProvider(LCCBlocks.natural_rubber_log.defaultState), StraightTrunkPlacer(4, 3, 1), SimpleBlockStateProvider(LCCBlocks.rubber_leaves.defaultState), BlobFoliagePlacer(UniformIntDistribution.of(0), UniformIntDistribution.of(0), 0), TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()) }
    val rubber_trees_rare by entry(::initialiser) { rubber_tree.decorate(ConfiguredFeatures.Decorators.field_29534).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.007F, 1))) }
    val rubber_trees_uncommon by entry(::initialiser) { rubber_tree.decorate(ConfiguredFeatures.Decorators.field_29534).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.07F, 1))) }
    val rubber_trees_common by entry(::initialiser) { rubber_tree.decorate(ConfiguredFeatures.Decorators.field_29534).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.35F, 1))) }

    override fun defaultProperties(name: String) = Unit

}

object LCCCarvers : BasicDirectory<Carver<out CarverConfig>, Unit>(), RegistryDirectory<Carver<out CarverConfig>, Unit, Unit> {

    override val registry by lazy { Registry.CARVER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_cave by entry(::initialiser) { WastelandCaveCarver(CarverConfig.CONFIG_CODEC) }
    val wasteland_ravine by entry(::initialiser) { WastelandRavineCarver(RavineCarverConfig.RAVINE_CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredCarvers : BasicDirectory<ConfiguredCarver<out CarverConfig>, Unit>(), RegistryDirectory<ConfiguredCarver<out CarverConfig>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_CARVER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_cave by entry(::initialiser) { LCCCarvers.wasteland_cave.configure(CarverConfig(0.34F)) }
    val wasteland_ravine by entry(::initialiser) { LCCCarvers.wasteland_ravine.configure(RavineCarverConfig(0.02f, CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.defaultState), YOffset.fixed(10), YOffset.fixed(67), UniformIntDistribution.of(3), UniformFloatProvider.create(0.75f, 0.25f), UniformFloatProvider.create(-0.125f, 0.25f), TrapezoidFloatProvider.create(0.0f, 6.0f, 2.0f), 3, UniformFloatProvider.create(0.75f, 0.25f), 1.0f, 0.0f)) }

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