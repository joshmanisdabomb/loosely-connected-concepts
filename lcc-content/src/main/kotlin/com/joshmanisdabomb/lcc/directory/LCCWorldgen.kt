package com.joshmanisdabomb.lcc.directory

/*import com.joshmanisdabomb.lcc.world.feature.structure.SapphireAltarStructureFeature
import com.joshmanisdabomb.lcc.world.feature.structure.WastelandObeliskStructureFeature
import com.joshmanisdabomb.lcc.world.feature.structure.WastelandTentStructureFeature*/
import com.google.common.collect.ImmutableList
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.tags.LCCBiomeTags
import com.joshmanisdabomb.lcc.world.biome.surface.WastelandMaterialRule
import com.joshmanisdabomb.lcc.world.feature.*
import com.joshmanisdabomb.lcc.world.feature.config.SmallGeodeFeatureConfig
import com.joshmanisdabomb.lcc.world.feature.structure.WastelandTentStructure
import com.joshmanisdabomb.lcc.world.placement.HeightThreshold
import com.joshmanisdabomb.lcc.world.placement.NearLavaPlacement
import com.mojang.serialization.Codec
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Blocks
import net.minecraft.structure.StructurePieceType
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.tag.BiomeTags
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.blockpredicate.BlockPredicate
import net.minecraft.world.gen.carver.Carver
import net.minecraft.world.gen.carver.CarverConfig
import net.minecraft.world.gen.carver.ConfiguredCarver
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.heightprovider.TrapezoidHeightProvider
import net.minecraft.world.gen.placementmodifier.*
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider
import net.minecraft.world.gen.structure.Structure
import net.minecraft.world.gen.structure.StructureType
import net.minecraft.world.gen.surfacebuilder.MaterialRules
import net.minecraft.world.gen.trunk.StraightTrunkPlacer
import java.util.List

object LCCWorldgen {
    fun init() {
        LCCFeatures.init()
        LCCConfiguredFeatures.init()
        LCCPlacedFeatures.init()
        LCCCarvers.init()
        LCCConfiguredCarvers.init()
        LCCPlacementModifierTypes.init()
        LCCStructurePieceTypes.init()
        LCCStructureTypes.init()

        biomeModifications()
    }

    fun biomeModifications() {
        //Ores
        with (BiomeSelectors.foundInOverworld().and { !it.hasTag(LCCBiomeTags.wasteland) }) {
            BiomeModifications.addFeature(this, GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.uranium.key.get())
            BiomeModifications.addFeature(this, GenerationStep.Feature.UNDERGROUND_DECORATION, LCCPlacedFeatures.topaz_geode.key.get())
        }

        //Rubber Tree
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.hasTag(BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE) || it.hasTag(BiomeTags.VILLAGE_SAVANNA_HAS_STRUCTURE) }, GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.rubber_trees_rare.key.get())
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.hasTag(BiomeTags.IS_BADLANDS) }, GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.rubber_trees_uncommon.key.get())
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.hasTag(BiomeTags.IS_JUNGLE) }, GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.rubber_trees_common.key.get())

        //Salt
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and { it.hasTag(BiomeTags.IS_DEEP_OCEAN) }, GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.salt.key.get())
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

object LCCConfiguredFeatures : AdvancedDirectory<ConfiguredFeature<out FeatureConfig, out Feature<out FeatureConfig>>, RegistryEntry<ConfiguredFeature<*, *>>, Unit, Unit>() {

    val abundant_coal by entry(::initialiser) { ConfiguredFeature(Feature.ORE, OreFeatureConfig(OreConfiguredFeatures.COAL_ORES, 14)) }
    val abundant_iron by entry(::initialiser) { ConfiguredFeature(Feature.ORE, OreFeatureConfig(OreConfiguredFeatures.IRON_ORES, 4)) }
    val abundant_copper by entry(::initialiser) { ConfiguredFeature(Feature.ORE, OreFeatureConfig(OreConfiguredFeatures.COPPER_ORES, 14)) }

    val oil_geyser by entry(::initialiser) { ConfiguredFeature(LCCFeatures.oil_geyser, FeatureConfig.DEFAULT) }
    val oil_pockets by entry(::initialiser) { ConfiguredFeature(LCCFeatures.oil_pockets, FeatureConfig.DEFAULT) }

    val uranium by entry(::initialiser) { ConfiguredFeature(Feature.ORE, OreFeatureConfig(uranium_targets, 4)) }

    val tungsten by entry(::initialiser) { ConfiguredFeature(Feature.ORE, OreFeatureConfig(tungsten_targets, 7)) }

    val topaz_geode by entry(::initialiser) { ConfiguredFeature(LCCFeatures.topaz_geode, SmallGeodeFeatureConfig(37, LCCBlocks.topaz_block, LCCBlocks.budding_topaz, LCCBlocks.rhyolite.defaultState, LCCBlocks.pumice.defaultState)) }

    val classic_tree by entry(::initialiser) { ConfiguredFeature(Feature.TREE, (TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG.defaultState), StraightTrunkPlacer(4, 2, 0), BlockStateProvider.of(LCCBlocks.classic_leaves.defaultState), BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()) }
    val rubber_tree by entry(::initialiser) { ConfiguredFeature(LCCFeatures.rubber_tree, TreeFeatureConfig.Builder(BlockStateProvider.of(LCCBlocks.natural_rubber_log.defaultState), StraightTrunkPlacer(4, 3, 1), BlockStateProvider.of(LCCBlocks.rubber_leaves.defaultState), BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0), TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()) }

    val salt by entry(::initialiser) { ConfiguredFeature(Feature.DISK, DiskFeatureConfig(PredicatedStateProvider.of(BlockStateProvider.of(LCCBlocks.rock_salt.defaultState)), BlockPredicate.matchingBlocks(List.of(Blocks.DIRT, Blocks.STONE)), UniformIntProvider.create(2, 4), 3)) }

    //TODO custom deposit feature
    /* val deposits by entry(::initialiser) { ConfiguredFeature(Feature.FLOWER, RandomPatchFeatureConfig(96, 6, 2) {
        ConfiguredFeature(Feature.SIMPLE_BLOCK, SimpleBlockFeatureConfig(NoiseBlockStateProvider(2345L, DoublePerlinNoiseSampler.NoiseParameters(0, 1.0, *DoubleArray(0)), 0.020833334f, listOf(LCCBlocks.deposit.defaultState)))).withInAirFilter()
    }) }*/
    val landmines by entry(::initialiser) { ConfiguredFeature(LCCFeatures.landmines, FeatureConfig.DEFAULT) }
    val fortstone_patches by entry(::initialiser) { ConfiguredFeature(Feature.ORE, OreFeatureConfig(BlockMatchRuleTest(LCCBlocks.cracked_mud), LCCBlocks.fortstone.defaultState, 45)) }
    val wasteland_spikes by entry(::initialiser) { ConfiguredFeature(LCCFeatures.wasteland_spikes, FeatureConfig.DEFAULT) }
    val deadwood_logs by entry(::initialiser) { ConfiguredFeature(LCCFeatures.deadwood_logs, FeatureConfig.DEFAULT) }
    val spike_trap by entry(::initialiser) { ConfiguredFeature(LCCFeatures.spike_trap, FeatureConfig.DEFAULT) }
    val wasp_hive by entry(::initialiser) { ConfiguredFeature(LCCFeatures.wasp_hive, FeatureConfig.DEFAULT) }

    //val mud by entry(::initialiser) { ConfiguredFeature(Feature.ORE, OreFeatureConfig(MultipleMatchRuleTest(listOf(Blocks.GRASS_BLOCK, Blocks.PODZOL), emptyList(), listOf(BlockTags.DIRT)), LCCBlocks.mud.defaultState, 40)) }

    private fun <C : FeatureConfig, F : Feature<C>> initialiser(input: ConfiguredFeature<C, F>, context: DirectoryContext<Unit>, parameters: Unit): RegistryEntry<ConfiguredFeature<*, *>> {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, context.id, input) as RegistryEntry<ConfiguredFeature<*, *>>
    }

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

    val uranium_targets = ImmutableList.of(
        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, LCCBlocks.uranium_ore.defaultState),
        OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, LCCBlocks.deepslate_uranium_ore.defaultState)
    )
    val tungsten_targets = ImmutableList.of(
        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, LCCBlocks.tungsten_ore.defaultState),
        OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, LCCBlocks.deepslate_tungsten_ore.defaultState)
    )

}

object LCCPlacedFeatures : AdvancedDirectory<PlacedFeature, RegistryEntry<PlacedFeature>, Unit, Unit>() {

    private val wasteland_spikes_threshold = TrapezoidHeightProvider.create(YOffset.fixed(80), YOffset.fixed(92))

    //TODO review heightmap placements
    val abundant_coal by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.abundant_coal, listOf(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(128)), BiomePlacementModifier.of())) }
    val abundant_iron by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.abundant_iron, listOf(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(64)), BiomePlacementModifier.of())) }
    val abundant_copper by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.abundant_copper, listOf(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(18), YOffset.fixed(78)), BiomePlacementModifier.of())) }

    val oil_geyser by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.oil_geyser, listOf(RarityFilterPlacementModifier.of(20), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of(), HeightThreshold(wasteland_spikes_threshold, true))) }
    val oil_pockets by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.oil_pockets, listOf(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of(), HeightThreshold(wasteland_spikes_threshold, true))) }

    val uranium by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.uranium, listOf(CountPlacementModifier.of(1), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-116), YOffset.fixed(52)), BiomePlacementModifier.of())) } //TODO near air
    val uranium_wasteland by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.uranium, listOf(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-160), YOffset.fixed(96)), BiomePlacementModifier.of())) } //TODO near air

    val tungsten by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.uranium, listOf(CountPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-10), YOffset.fixed(10)), BiomePlacementModifier.of())) }

    val topaz_geode by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.topaz_geode, listOf(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), NearLavaPlacement.instance)) } //TODO near lava lake

    val rubber_trees_rare by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.rubber_tree, listOf(RarityFilterPlacementModifier.of(500), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())) }
    val rubber_trees_uncommon by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.rubber_tree, listOf(RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())) }
    val rubber_trees_common by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.rubber_tree, listOf(RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())) }

    val salt by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.salt, listOf(RarityFilterPlacementModifier.of(13), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of())) }

    //val deposits by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.deposits, listOf(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of())) }
    val landmines by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.landmines, listOf(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of())) }
    val fortstone_patches by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.fortstone_patches, listOf(CountPlacementModifier.of(70), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(88), YOffset.getTop()), BiomePlacementModifier.of())) }
    val wasteland_spikes by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.wasteland_spikes, listOf(CountPlacementModifier.of(11), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of(), HeightThreshold(wasteland_spikes_threshold))) }
    val deadwood_logs by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.deadwood_logs, listOf(RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of(), HeightThreshold(wasteland_spikes_threshold, true))) }
    val spike_trap by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.spike_trap, listOf(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of(), HeightThreshold(wasteland_spikes_threshold, true))) }
    val wasp_hive by entry(::initialiser) { PlacedFeature(LCCConfiguredFeatures.wasp_hive, listOf(RarityFilterPlacementModifier.of(50), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of())) }

    private fun initialiser(input: PlacedFeature, context: DirectoryContext<Unit>, parameters: Unit): RegistryEntry<PlacedFeature> {
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, context.id, input)
    }

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}

object LCCCarvers : BasicDirectory<Carver<out CarverConfig>, Unit>(), RegistryDirectory<Carver<out CarverConfig>, Unit, Unit> {

    override val registry by lazy { Registry.CARVER }

    override fun regId(name: String) = LCC.id(name)

    //val wasteland_cave by entry(::initialiser) { WastelandCaveCarver(CaveCarverConfig.CAVE_CODEC) }
    //val wasteland_ravine by entry(::initialiser) { WastelandRavineCarver(RavineCarverConfig.RAVINE_CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCConfiguredCarvers : AdvancedDirectory<ConfiguredCarver<out CarverConfig>, RegistryEntry<out ConfiguredCarver<out CarverConfig>>, Unit, Unit>() {

    /*val wasteland_cave by entry(::initialiser) { LCCCarvers.wasteland_cave.configure(CaveCarverConfig(
        0.15F,
        UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(180)),
        UniformFloatProvider.create(0.1F, 0.9F),
        YOffset.aboveBottom(8),
        CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.defaultState),
        Registry.BLOCK.getOrCreateEntryList(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
        UniformFloatProvider.create(0.7F, 1.4F),
        UniformFloatProvider.create(0.8F, 1.3F),
        UniformFloatProvider.create(-1.0F, -0.4F)
    )) }
    val wasteland_ravine by entry(::initialiser) { LCCCarvers.wasteland_ravine.configure(RavineCarverConfig(
        0.01F,
        UniformHeightProvider.create(YOffset.fixed(10), YOffset.fixed(67)),
        ConstantFloatProvider.create(3.0F),
        YOffset.aboveBottom(8),
        CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.defaultState),
        Registry.BLOCK.getOrCreateEntryList(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
        UniformFloatProvider.create(-0.125F, 0.125F),
        RavineCarverConfig.Shape(
            UniformFloatProvider.create(0.75F, 1.0F),
            TrapezoidFloatProvider.create(0.0F, 6.0F, 2.0F),
            3,
            UniformFloatProvider.create(0.75F, 1.0F),
            1.0F,
            0.0F
        )
    )) }*/

    override fun id(name: String) = LCC.id(name)

    private fun <C : CarverConfig> initialiser(input: ConfiguredCarver<C>, context: DirectoryContext<Unit>, parameters: Unit): RegistryEntry<ConfiguredCarver<C>> {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, context.id, input) as RegistryEntry<ConfiguredCarver<C>>
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}

object LCCMaterialRules : BasicDirectory<Codec<out MaterialRules.MaterialRule>, Unit>(), RegistryDirectory<Codec<out MaterialRules.MaterialRule>, Unit, Unit> {

    override val registry by lazy { Registry.MATERIAL_RULE }

    override fun regId(name: String) = LCC.id(name)

    val wasteland by entry(::initialiser) { WastelandMaterialRule.codec.codec }

    override fun defaultProperties(name: String) = Unit

}

object LCCPlacementModifierTypes : BasicDirectory<PlacementModifierType<out PlacementModifier>, Unit>(), RegistryDirectory<PlacementModifierType<out PlacementModifier>, Unit, Unit> {

    override val registry by lazy { Registry.PLACEMENT_MODIFIER_TYPE }

    override fun regId(name: String) = LCC.id(name)

    val near_lava_lake by entry(::initialiser) { PlacementModifierType { NearLavaPlacement.codec } }
    val height_threshold by entry(::initialiser) { PlacementModifierType { HeightThreshold.codec } }
    //val near_air by entry(::initialiser) { NearAirDecorator(NopeDecoratorConfig.CODEC) }

    override fun defaultProperties(name: String) = Unit

}

object LCCStructureTypes : BasicDirectory<StructureType<out Structure>, Unit>(), RegistryDirectory<StructureType<out Structure>, Unit, Unit> {

    override val registry by lazy { Registry.STRUCTURE_TYPE }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_tent by entry(::initialiser) { StructureType { WastelandTentStructure.codec } }

    override fun defaultProperties(name: String) = Unit

}

object LCCStructurePieceTypes : BasicDirectory<StructurePieceType, Unit>(), RegistryDirectory<StructurePieceType, Unit, Unit> {

    override val registry by lazy { Registry.STRUCTURE_PIECE }

    override fun regId(name: String) = LCC.id(name)

    val wasteland_tent by entry(::initialiser) { StructurePieceType.ManagerAware(WastelandTentStructure::Piece) }

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