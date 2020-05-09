package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class ClassicSaplingBlock extends SaplingBlock implements IPottableBlock, LCCBlockHelper {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;

    public static final TreeFeatureConfig CLASSIC_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(LCCBlocks.classic_leaves.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(LCCBlocks.classic_sapling).build();
    public static final Tree CLASSIC_TREE = new Tree() {
        @Nullable
        @Override
        protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
            return Feature.NORMAL_TREE.withConfiguration(CLASSIC_TREE_CONFIG);
        }
    };

    public ClassicSaplingBlock(Properties properties) {
        super(CLASSIC_TREE, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0).with(AGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) { builder.add(STAGE, AGE); }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getLight(pos.up()) >= 9 && random.nextInt(5) == 0) {
            if (state.get(AGE) < 7) {
                world.setBlockState(pos, state.cycle(AGE), 3);
            } else if (state.get(STAGE) < 1) {
                world.setBlockState(pos, state.with(AGE, 0).cycle(STAGE), 3);
            } else {
                this.grow(world, random, pos, state);
            }
        }

    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos)) return;
        CLASSIC_TREE.func_225545_a_(world, world.getChunkProvider().getChunkGenerator(), pos, state, rand);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public BlockState getPottedState() {
        return LCCBlocks.potted_classic_sapling.getDefaultState();
    }

    @Override
    public RenderType getRenderLayer() {
        return RenderType.getCutout();
    }

}
