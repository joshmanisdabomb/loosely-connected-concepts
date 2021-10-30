package com.joshmanisdabomb.lcc.directory

/*import com.joshmanisdabomb.lcc.world.feature.structure.SapphireAltarStructureFeature
import com.joshmanisdabomb.lcc.world.feature.structure.WastelandObeliskStructureFeature
import com.joshmanisdabomb.lcc.world.feature.structure.WastelandTentStructureFeature*/
import com.google.common.collect.ImmutableList
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.mixin.hooks.common.DecoratorAccessor
import com.joshmanisdabomb.lcc.world.carver.WastelandCaveCarver
import com.joshmanisdabomb.lcc.world.carver.WastelandRavineCarver
import com.joshmanisdabomb.lcc.world.decorator.NearAirDecorator
import com.joshmanisdabomb.lcc.world.decorator.NearLavaLakeDecorator
import com.joshmanisdabomb.lcc.world.feature.*
import com.joshmanisdabomb.lcc.world.feature.config.SmallGeodeFeatureConfig
import com.joshmanisdabomb.lcc.world.feature.rule.MultipleMatchRuleTest
import net.minecraft.block.Blocks
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.floatprovider.ConstantFloatProvider
import net.minecraft.util.math.floatprovider.TrapezoidFloatProvider
import net.minecraft.util.math.floatprovider.UniformFloatProvider
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.CountConfig
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.carver.*
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.decorator.NopeDecoratorConfig
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.heightprovider.UniformHeightProvider
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

object LCCWorldgen {
    fun init() {
        LCCFeatures.init()
        LCCDecorators.init()
        LCCConfiguredFeatures.init()
        LCCCarvers.init()
        LCCConfiguredCarvers.init()
        /*LCCSurfaceBuilders.init()
        LCCConfiguredSurfaceBuilders.init()
        LCCStructurePieceTypes.init()
        LCCStructureFeatures.init()
        LCCConfiguredStructureFeatures.init()*/

        biomeModifications()
    }

    fun biomeModifications() {
        /*//Ores
        with (BiomeSelectors.foundInOverworld().and { LCCBiomes.getOrNull(it.biome)?.tags?.contains("wasteland") != true }) {
            BiomeModifications.addFeature(this, GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.uranium))
            BiomeModifications.addFeature(this, GenerationStep.Feature.UNDERGROUND_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.topaz_geode))
        }

        //Rubber Tree
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { when (it.biome.category) { Biome.Category.SAVANNA, Biome.Category.PLAINS -> true; else -> false } }, GenerationStep.Feature.VEGETAL_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.rubber_trees_rare))
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { when (it.biome.category) { Biome.Category.MESA -> true; else -> false } }, GenerationStep.Feature.VEGETAL_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.rubber_trees_uncommon))
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { when (it.biome.category) { Biome.Category.JUNGLE -> true; else -> false } }, GenerationStep.Feature.VEGETAL_DECORATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.rubber_trees_common))

        //Salt
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.biomeKey.value.namespace == "minecraft" && it.biome.category == Biome.Category.OCEAN && it.biomeKey.value.path.contains("deep") }, GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.salt))

        //Mud
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.biomeKey.value.namespace == "minecraft" && (it.biomeKey.value.path.contains("jungle") || it.biomeKey.value.path.contains("dark_forest") || it.biomeKey.value.path.contains("swamp")) }, GenerationStep.Feature.TOP_LAYER_MODIFICATION, LCCConfiguredFeatures.getRegistryKey(LCCConfiguredFeatures.mud))
        */
    }
}

object LCCFeatures : BasicDirectory<Feature<out FeatureConfig>, Unit>(), RegistryDirectory<Feature<out FeatureConfig>, Unit, Unit> {

    override val registry by lazy { Registry.FEATURE }

    override fun regId(name: String) = LCC.id(name)

    val topaz_geode by entry(::initialiser) { SmallGeodeFeature(SmallGeodeFeatureConfig.codec) }

    val oil_geyser by entry(::initialiser) { OilGeyserFeature(DefaultFeatureConfig.CODEC) }
    val oil_pockets by entry(::initialiser) { OilPocketsFeature(DefaultFeatureConfig.CODEC) }

    val rubber_tree by entry(::initialiser) { RubberTreeFeature(TreeFeatureConfig.CODEC) }

    val wasteland_spikes by entry(::initialiser) { WastelandSpikesFeature(DefaultFeatureConfig.CODEC) }
    val landmines by entry(::initialiser) { LandmineFeature(DefaultFeatureConfig.CODEC) }
    val deadwood_logs by entry(::initialiser) { DeadwoodLogsFeature(DefaultFeatureConfig.CODEC) }
    val spike_trap by entry(::initialiser) { SpikeTrapFeature(DefaultFeatureConfig.CODEC) }
    val wasp_hive by entry(::initialiser) { WaspHiveFeature(DefaultFeatureConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredFeatures : BasicDirectory<ConfiguredFeature<out FeatureConfig, out Feature<out FeatureConfig>>, Unit>(), RegistryDirectory<ConfiguredFeature<out FeatureConfig, out Feature<out FeatureConfig>>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_FEATURE }

    override fun regId(name: String) = LCC.id(name)

    val abundant_coal by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(ConfiguredFeatures.COAL_ORE_TARGETS, 14)).triangleRange(YOffset.fixed(0), YOffset.fixed(128)).spreadHorizontally().repeat(15) }
    val abundant_iron by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(ConfiguredFeatures.IRON_ORE_TARGETS, 4)).triangleRange(YOffset.fixed(-64), YOffset.fixed(64)).spreadHorizontally().repeat(15) }
    val abundant_copper by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(ConfiguredFeatures.COPPER_ORE_TARGETS, 14)).triangleRange(YOffset.fixed(18), YOffset.fixed(78)).spreadHorizontally().repeat(15) }

    val oil_geyser by entry(::initialiser) { LCCFeatures.oil_geyser.configure(FeatureConfig.DEFAULT).decorate(DecoratorAccessor.getSquareHeightmap()).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.015f, 1))) }
    val oil_pockets by entry(::initialiser) { LCCFeatures.oil_pockets.configure(FeatureConfig.DEFAULT).decorate(Decorator.COUNT_MULTILAYER.configure(CountConfig(1))).applyChance(12) }

    val uranium by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(uranium_targets, 4)).decorate(LCCDecorators.near_air.configure(DecoratorConfig.DEFAULT)).triangleRange(YOffset.aboveBottom(-116), YOffset.fixed(52)).spreadHorizontally() }
    val uranium_wasteland by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(uranium_targets, 4)).decorate(LCCDecorators.near_air.configure(DecoratorConfig.DEFAULT)).triangleRange(YOffset.aboveBottom(-160), YOffset.fixed(96)).spreadHorizontally().repeat(5) }

    val tungsten by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(tungsten_targets, 7)).uniformRange(YOffset.fixed(-10), YOffset.fixed(10)).spreadHorizontally().repeat(2) }

    val topaz_geode by entry(::initialiser) { LCCFeatures.topaz_geode.configure(SmallGeodeFeatureConfig(37, LCCBlocks.topaz_block, LCCBlocks.budding_topaz, LCCBlocks.rhyolite.defaultState, LCCBlocks.pumice.defaultState)).decorate(LCCDecorators.near_lava_lake.configure(DecoratorConfig.DEFAULT)).spreadHorizontally().applyChance(2) }

    val classic_tree by entry(::initialiser) { Feature.TREE.configure((TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG.defaultState), StraightTrunkPlacer(4, 2, 0), BlockStateProvider.of(LCCBlocks.classic_leaves.defaultState), BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()) }
    val rubber_tree by entry(::initialiser) { LCCFeatures.rubber_tree.configure(TreeFeatureConfig.Builder(BlockStateProvider.of(LCCBlocks.natural_rubber_log.defaultState), StraightTrunkPlacer(4, 3, 1), BlockStateProvider.of(LCCBlocks.rubber_leaves.defaultState), BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0), TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()) }
    val rubber_trees_rare by entry(::initialiser) { rubber_tree.decorate(DecoratorAccessor.getSquareHeightmapOceanFloorNoWater()).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.002F, 1))) }
    val rubber_trees_uncommon by entry(::initialiser) { rubber_tree.decorate(DecoratorAccessor.getSquareHeightmapOceanFloorNoWater()).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.04F, 1))) }
    val rubber_trees_common by entry(::initialiser) { rubber_tree.decorate(DecoratorAccessor.getSquareHeightmapOceanFloorNoWater()).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.35F, 1))) }

    val salt by entry(::initialiser) { Feature.DISK.configure(DiskFeatureConfig(LCCBlocks.rock_salt.defaultState, UniformIntProvider.create(2, 6), 3, ImmutableList.of(Blocks.STONE.defaultState, Blocks.DIRT.defaultState, LCCBlocks.rock_salt.defaultState))).spreadHorizontally().decorate(DecoratorAccessor.getSquareTopSolidHeightmap()).applyChance(13) }

    val deposits by entry(::initialiser) { Feature.FLOWER.configure(RandomPatchFeatureConfig(96, 6, 2) {
        Feature.SIMPLE_BLOCK.configure(SimpleBlockFeatureConfig(NoiseBlockStateProvider(2345L, DoublePerlinNoiseSampler.NoiseParameters(0, 1.0, *DoubleArray(0)), 0.020833334f, listOf(LCCBlocks.deposit.defaultState)))).onlyInAir()
    }).decorate(DecoratorAccessor.getSquareHeightmap()) }
    val landmines by entry(::initialiser) { LCCFeatures.landmines.configure(FeatureConfig.DEFAULT).decorate(DecoratorAccessor.getSquareHeightmap()).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.15f, 1))) }
    val fortstone_patches by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(BlockMatchRuleTest(LCCBlocks.cracked_mud), LCCBlocks.fortstone.defaultState, 45)).uniformRange(YOffset.fixed(75), YOffset.getTop()).spreadHorizontally().repeat(70) }
    val wasteland_spikes by entry(::initialiser) { LCCFeatures.wasteland_spikes.configure(FeatureConfig.DEFAULT).decorate(Decorator.COUNT_MULTILAYER.configure(CountConfig(8))) }
    val deadwood_logs by entry(::initialiser) { LCCFeatures.deadwood_logs.configure(FeatureConfig.DEFAULT).decorate(DecoratorAccessor.getSquareHeightmapOceanFloorNoWater()).applyChance(14) }
    val spike_trap by entry(::initialiser) { LCCFeatures.spike_trap.configure(FeatureConfig.DEFAULT).decorate(DecoratorAccessor.getSquareHeightmap()).decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(0, 0.4f, 1))) }
    val wasp_hive by entry(::initialiser) { LCCFeatures.wasp_hive.configure(FeatureConfig.DEFAULT).decorate(DecoratorAccessor.getSquareHeightmap()).applyChance(90) }

    val mud by entry(::initialiser) { Feature.ORE.configure(OreFeatureConfig(MultipleMatchRuleTest(listOf(Blocks.GRASS_BLOCK, Blocks.PODZOL), emptyList(), listOf(BlockTags.DIRT)), LCCBlocks.mud.defaultState, 40)).spreadHorizontally().decorate(DecoratorAccessor.getHeightmapWorldSurface()) }

    override fun defaultProperties(name: String) = Unit

    val uranium_targets = ImmutableList.of(
        OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, LCCBlocks.uranium_ore.defaultState),
        OreFeatureConfig.createTarget(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, LCCBlocks.deepslate_uranium_ore.defaultState)
    )
    val tungsten_targets = ImmutableList.of(
        OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, LCCBlocks.tungsten_ore.defaultState),
        OreFeatureConfig.createTarget(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, LCCBlocks.deepslate_tungsten_ore.defaultState)
    )

}

object LCCCarvers : BasicDirectory<Carver<out CarverConfig>, Unit>(), RegistryDirectory<Carver<out CarverConfig>, Unit, Unit> {

    override val registry by lazy { Registry.CARVER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_cave by entry(::initialiser) { WastelandCaveCarver(CaveCarverConfig.CAVE_CODEC) }
    val wasteland_ravine by entry(::initialiser) { WastelandRavineCarver(RavineCarverConfig.RAVINE_CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredCarvers : BasicDirectory<ConfiguredCarver<out CarverConfig>, Unit>(), RegistryDirectory<ConfiguredCarver<out CarverConfig>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_CARVER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_cave by entry(::initialiser) { LCCCarvers.wasteland_cave.configure(CaveCarverConfig(
        0.15F,
        UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(180)),
        UniformFloatProvider.create(0.1F, 0.9F),
        YOffset.aboveBottom(8),
        CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()),
        UniformFloatProvider.create(0.7F, 1.4F),
        UniformFloatProvider.create(0.8F, 1.3F),
        UniformFloatProvider.create(-1.0F, -0.4F)
    )) }
    val wasteland_ravine by entry(::initialiser) { LCCCarvers.wasteland_ravine.configure(RavineCarverConfig(
        0.01F,
        UniformHeightProvider.create(YOffset.fixed(10), YOffset.fixed(67)),
        ConstantFloatProvider.create(3.0F),
        YOffset.aboveBottom(8),
        CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()),
        UniformFloatProvider.create(-0.125F, 0.125F),
        RavineCarverConfig.Shape(
            UniformFloatProvider.create(0.75F, 1.0F),
            TrapezoidFloatProvider.create(0.0F, 6.0F, 2.0F),
            3,
            UniformFloatProvider.create(0.75F, 1.0F),
            1.0F,
            0.0F
        )
    )) }
    override fun defaultProperties(name: String) = Unit

}

/*object LCCSurfaceBuilders : BasicDirectory<SurfaceBuilder<out SurfaceConfig>, Unit>(), RegistryDirectory<SurfaceBuilder<out SurfaceConfig>, Unit, Unit> {

    override val registry by lazy { Registry.SURFACE_BUILDER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_barrens by entry(::initialiser) { WastelandSurfaceBuilder(TernarySurfaceConfig.CODEC) }
    val wasteland_spikes by entry(::initialiser) { WastelandSpikesSurfaceBuilder(TernarySurfaceConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredSurfaceBuilders : BasicDirectory<ConfiguredSurfaceBuilder<out SurfaceConfig>, Unit>(), RegistryDirectory<ConfiguredSurfaceBuilder<out SurfaceConfig>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_SURFACE_BUILDER }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_barrens by entry(::initialiser) { LCCSurfaceBuilders.wasteland_barrens.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.mud.defaultState)) }
    val wasteland_spikes by entry(::initialiser) { LCCSurfaceBuilders.wasteland_spikes.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.mud.defaultState)) }

    override fun defaultProperties(name: String) = Unit

}*/

object LCCDecorators : BasicDirectory<Decorator<out DecoratorConfig>, Unit>(), RegistryDirectory<Decorator<out DecoratorConfig>, Unit, Unit> {

    override val registry by lazy { Registry.DECORATOR }

    override fun regId(name: String) = LCC.id(name)

    val near_lava_lake by entry(::initialiser) { NearLavaLakeDecorator(NopeDecoratorConfig.CODEC) }
    val near_air by entry(::initialiser) { NearAirDecorator(NopeDecoratorConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}

/*object LCCStructureFeatures : AdvancedDirectory<FabricStructureBuilder<out FeatureConfig, out StructureFeature<out FeatureConfig>>, StructureFeature<out FeatureConfig>, GenerationStep.Feature, Unit>() {

    override fun id(name: String) = LCC.id(name)

    val wasteland_tent by entry(::initialiser) {
        FabricStructureBuilder.create(id, WastelandTentStructureFeature(DefaultFeatureConfig.CODEC))
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(12, 8, 5648943)
            .adjustsSurface()
    }
    val sapphire_altar by entry(::initialiser) {
        FabricStructureBuilder.create(id, SapphireAltarStructureFeature(DefaultFeatureConfig.CODEC))
            .step(GenerationStep.Feature.TOP_LAYER_MODIFICATION)
            .defaultConfig(8, 6, 24758369)
            .adjustsSurface()
    }
    val wasteland_obelisk by entry(::initialiser) {
        FabricStructureBuilder.create(id, WastelandObeliskStructureFeature(DefaultFeatureConfig.CODEC))
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(21, 20, 420839089)
            .adjustsSurface()
    }

    fun <C : FeatureConfig, S : StructureFeature<C>> initialiser(input: FabricStructureBuilder<C, S>, context: DirectoryContext<GenerationStep.Feature>, parameters: Unit): S {
        return input.register()
    }

    override fun defaultProperties(name: String) = GenerationStep.Feature.RAW_GENERATION

    override fun defaultContext() = Unit

}

object LCCConfiguredStructureFeatures : BasicDirectory<ConfiguredStructureFeature<out FeatureConfig, out StructureFeature<out FeatureConfig>>, Unit>(), RegistryDirectory<ConfiguredStructureFeature<out FeatureConfig, out StructureFeature<out FeatureConfig>>, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_tent by entry(::initialiser) { LCCStructureFeatures.wasteland_tent.configure(FeatureConfig.DEFAULT) }
    val sapphire_altar by entry(::initialiser) { LCCStructureFeatures.sapphire_altar.configure(FeatureConfig.DEFAULT) }
    val wasteland_obelisk by entry(::initialiser) { LCCStructureFeatures.wasteland_obelisk.configure(FeatureConfig.DEFAULT) }

    override fun defaultProperties(name: String) = Unit

}

object LCCStructurePieceTypes : BasicDirectory<StructurePieceType, Unit>(), RegistryDirectory<StructurePieceType, Unit, Unit> {

    override val registry by lazy { Registry.STRUCTURE_PIECE }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_tent by entry(::initialiser) { StructurePieceType(WastelandTentStructureFeature::Piece) }
    val sapphire_altar by entry(::initialiser) { StructurePieceType(SapphireAltarStructureFeature::Piece) }
    val wasteland_obelisk by entry(::initialiser) { StructurePieceType(WastelandObeliskStructureFeature::Piece) }

    override fun defaultProperties(name: String) = Unit

}*/