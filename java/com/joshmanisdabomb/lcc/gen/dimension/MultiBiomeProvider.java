package com.joshmanisdabomb.lcc.gen.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.joshmanisdabomb.lcc.misc.AdaptedFromSource;
import net.minecraft.block.BlockState;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.LongFunction;

public class MultiBiomeProvider extends BiomeProvider {

    private final Biome[] biomes;
    private final Layer genBiomes;
    private final Layer biomeFactoryLayer;

    public MultiBiomeProvider(MultiBiomeProviderSettings settings) {
        this.biomes = settings.entries.entrySet().stream().flatMap(e -> e.getValue().stream()).map(be -> be.biome).toArray(Biome[]::new);
        Layer[] alayer = buildProcedure(settings.wi.getSeed(), settings.wi.getGenerator(), settings);
        this.genBiomes = alayer[0];
        this.biomeFactoryLayer = alayer[1];
    }

    @AdaptedFromSource
    protected static <T extends IArea, C extends IExtendedNoiseRandom<T>> ImmutableList<IAreaFactory<T>> buildProcedure(WorldType worldTypeIn, MultiBiomeProviderSettings settings, LongFunction<C> contextFactory) {
        IAreaFactory<T> iareafactory = IslandLayer.INSTANCE.apply(contextFactory.apply(1L));
        iareafactory = ZoomLayer.FUZZY.apply(contextFactory.apply(2000L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(1L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2001L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(50L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(70L), iareafactory);
        iareafactory = RemoveTooMuchOceanLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
        iareafactory = EdgeLayer.CoolWarm.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = EdgeLayer.HeatIce.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = EdgeLayer.Special.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2002L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2003L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(4L), iareafactory);
        iareafactory = DeepOceanLayer.INSTANCE.apply(contextFactory.apply(4L), iareafactory);
        iareafactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory);

        iareafactory = (new MultiBiomeLayer(settings)).apply(contextFactory.apply(200L), iareafactory);
        iareafactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iareafactory, 2, contextFactory);
        IAreaFactory<T> lvt_8_1_ = EdgeBiomeLayer.INSTANCE.apply(contextFactory.apply(1000L), iareafactory);

        for(int k = 0; k < 4; ++k) {
            lvt_8_1_ = ZoomLayer.NORMAL.apply(contextFactory.apply((long) (1000 + k)), lvt_8_1_);
        }

        lvt_8_1_ = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);
        IAreaFactory<T> iareafactory5 = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(10L), lvt_8_1_);
        return ImmutableList.of(lvt_8_1_, iareafactory5, lvt_8_1_);
    }

    protected static Layer[] buildProcedure(long seed, WorldType typeIn, MultiBiomeProviderSettings settings) {
        int i = 25;
        ImmutableList<IAreaFactory<LazyArea>> immutablelist = buildProcedure(typeIn, settings, (p_215737_2_) -> new LazyAreaLayerContext(25, seed, p_215737_2_));
        Layer layer = new Layer(immutablelist.get(0));
        Layer layer1 = new Layer(immutablelist.get(1));
        Layer layer2 = new Layer(immutablelist.get(2));
        return new Layer[]{layer, layer1, layer2};
    }

    @Override
    public Biome getBiome(int x, int y) {
        return this.biomeFactoryLayer.func_215738_a(x, y);
    }

    @Override
    public Biome getBiomeAtFactorFour(int factorFourX, int factorFourZ) {
        return this.genBiomes.func_215738_a(factorFourX, factorFourZ);
    }

    public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
        return this.biomeFactoryLayer.generateBiomes(x, z, width, length);
    }

    public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
        int i = centerX - sideLength >> 2;
        int j = centerZ - sideLength >> 2;
        int k = centerX + sideLength >> 2;
        int l = centerZ + sideLength >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        Set<Biome> set = Sets.newHashSet();
        Collections.addAll(set, this.genBiomes.generateBiomes(i, j, i1, j1));
        return set;
    }

    @Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
        int i = x - range >> 2;
        int j = z - range >> 2;
        int k = x + range >> 2;
        int l = z + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        Biome[] abiome = this.genBiomes.generateBiomes(i, j, i1, j1);
        BlockPos blockpos = null;
        int k1 = 0;

        for(int l1 = 0; l1 < i1 * j1; ++l1) {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            if (biomes.contains(abiome[l1])) {
                if (blockpos == null || random.nextInt(k1 + 1) == 0) {
                    blockpos = new BlockPos(i2, 0, j2);
                }

                ++k1;
            }
        }

        return blockpos;
    }

    public boolean hasStructure(Structure<?> structureIn) {
        return this.hasStructureCache.computeIfAbsent(structureIn, (p_205006_1_) -> {
            for (Biome biome : this.biomes) {
                if (biome.hasStructure(p_205006_1_)) {
                    return true;
                }
            }

            return false;
        });
    }

    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for (Biome biome : this.biomes) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            }
        }

        return this.topBlocksCache;
    }

    public static class MultiBiomeProviderSettings implements IBiomeProviderSettings {

        private final HashMap<BiomeManager.BiomeType, ArrayList<BiomeManager.BiomeEntry>> entries = new HashMap<>();

        private WorldInfo wi;

        public MultiBiomeProviderSettings setWorldInfo(WorldInfo info) {
            this.wi = info;
            return this;
        }

        public MultiBiomeProviderSettings addBiome(BiomeManager.BiomeEntry entry) {
            for (BiomeManager.BiomeType t : BiomeManager.BiomeType.values()) {
                this.addBiome(entry, t);
            }
            return this;
        }

        public MultiBiomeProviderSettings addBiome(BiomeManager.BiomeEntry entry, BiomeManager.BiomeType type) {
            entries.computeIfAbsent(type, t -> new ArrayList<>()).add(entry);
            return this;
        }

    }

    public static class MultiBiomeLayer implements IC0Transformer {

        private final MultiBiomeProviderSettings settings;

        public MultiBiomeLayer(MultiBiomeProviderSettings settings) {
            this.settings = settings;
        }

        @Override
        @AdaptedFromSource
        public int apply(INoiseRandom context, int value) {
            int i = (value & 3840) >> 8;
            value = value & -3841;
            BiomeManager.BiomeType type = BiomeManager.BiomeType.values()[Math.abs((i - 1) % 4)];

            ArrayList<BiomeManager.BiomeEntry> biomeList = settings.entries.get(type);
            int weight = WeightedRandom.getTotalWeight(biomeList);
            BiomeManager.BiomeEntry entry = WeightedRandom.getRandomItem(biomeList, context.random(weight));

            return Registry.BIOME.getId(entry.biome);
        }

    }

}
