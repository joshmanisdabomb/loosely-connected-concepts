package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.BigTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionalSaplingBlock extends SaplingBlock implements LCCBlockHelper, IPottableBlock {

    protected final BiFunction<Random, Boolean, ConfiguredFeature<TreeFeatureConfig,?>> feature;
    protected final Function<Random, ConfiguredFeature<HugeTreeFeatureConfig, ?>> hugeFeature;

    public final IPottableBlock potted;

    public FunctionalSaplingBlock(BiFunction<Random, Boolean, ConfiguredFeature<TreeFeatureConfig, ?>> smallTree, IPottableBlock potted, Properties properties) {
        super(new Tree() {
            @Nullable
            @Override
            protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
                return smallTree.apply(random, b);
            }
        }, properties);

        this.feature = smallTree;
        this.hugeFeature = random -> null;

        this.potted = potted;
    }

    public FunctionalSaplingBlock(BiFunction<Random, Boolean, ConfiguredFeature<TreeFeatureConfig, ?>> smallTree, Function<Random, ConfiguredFeature<HugeTreeFeatureConfig, ?>> bigTree, IPottableBlock potted, Properties properties) {
        super(new BigTree() {
            @Nullable
            @Override
            protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
                return smallTree.apply(random, b);
            }

            @Nullable
            @Override
            protected ConfiguredFeature<HugeTreeFeatureConfig, ?> getHugeTreeFeature(Random random) {
                return bigTree.apply(random);
            }
        }, properties);

        this.feature = smallTree;
        this.hugeFeature = bigTree;

        this.potted = potted;
    }

    @Override
    public RenderType getRenderLayer() {
        return RenderType.getCutout();
    }

    @Override
    public BlockState getPottedState() {
        return this.potted.getPottedState();
    }

}
