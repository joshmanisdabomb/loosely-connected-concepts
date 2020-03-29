package com.joshmanisdabomb.lcc.gen.dimension.provider;

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
import java.util.stream.Collectors;

public class MultiBiomeProvider extends BiomeProvider {

    private final Layer genBiomes;

    public MultiBiomeProvider(MultiBiomeProviderSettings settings) {
        super(settings.entries.entrySet().stream().flatMap(e -> e.getValue().stream()).map(be -> be.biome).collect(Collectors.toSet()));
        this.genBiomes = buildProcedure(settings.wi.getSeed(), settings.wi.getGenerator(), settings);
    }

    @AdaptedFromSource
    protected static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> buildProcedure(WorldType worldTypeIn, MultiBiomeProviderSettings settings, LongFunction<C> contextFactory) {
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
        return lvt_8_1_;
    }

    protected static Layer buildProcedure(long seed, WorldType typeIn, MultiBiomeProviderSettings settings) {
        int i = 25;
        IAreaFactory<LazyArea> area = buildProcedure(typeIn, settings, (p_215737_2_) -> new LazyAreaLayerContext(25, seed, p_215737_2_));
        return new Layer(area);
    }

    public boolean hasStructure(Structure<?> structureIn) {
        return this.hasStructureCache.computeIfAbsent(structureIn, (p_205006_1_) -> {
            for (Biome biome : this.field_226837_c_) {
                if (biome.hasStructure(p_205006_1_)) {
                    return true;
                }
            }

            return false;
        });
    }

    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for (Biome biome : this.field_226837_c_) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            }
        }

        return this.topBlocksCache;
    }

    public Biome getNoiseBiome(int x, int y, int z) {
        return this.genBiomes.func_215738_a(x, z);
    }

    public static class MultiBiomeProviderSettings implements IBiomeProviderSettings {

        private final HashMap<BiomeManager.BiomeType, ArrayList<BiomeManager.BiomeEntry>> entries = new HashMap<>();

        private WorldInfo wi;

        public MultiBiomeProviderSettings(WorldInfo wi) {
            this.wi = wi;
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
