package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.BigTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class ClassicSaplingBlock extends SaplingBlock {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    public static final Tree CLASSIC_TREE = new Tree() {
        @Nullable
        @Override
        protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
            return new TreeFeature(NoFeatureConfig::deserialize, true, 4, Blocks.OAK_LOG.getDefaultState(), LCCBlocks.classic_leaves.getDefaultState(), false).setSapling((IPlantable)LCCBlocks.classic_sapling);
        }
    };

    public ClassicSaplingBlock(Properties properties) {
        super(CLASSIC_TREE, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0).with(AGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) { builder.add(STAGE, AGE); }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getLight(pos.up()) >= 9 && random.nextInt(5) == 0) {
            if (state.get(AGE) < 7) {
                world.setBlockState(pos, state.cycle(AGE), 3);
            } else if (state.get(STAGE) < 1) {
                world.setBlockState(pos, state.with(AGE, 0).cycle(STAGE), 3);
            } else {
                this.grow(world, pos, state, random);
            }
        }

    }

    @Override
    public void grow(IWorld world, BlockPos pos, BlockState state, Random rand) {
        if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos)) return;
        CLASSIC_TREE.spawn(world, pos, state, rand);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        this.grow(world, pos, state, rand);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

}
