package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ClassicSaplingBlock extends FunctionalSaplingBlock implements IPottableBlock, LCCBlockHelper {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;

    public ClassicSaplingBlock(Properties properties) {
        super((random, b) -> Feature.NORMAL_TREE.withConfiguration(LCCFeatures.CLASSIC_TREE_CONFIG), () -> LCCBlocks.potted_classic_sapling.getDefaultState(), properties);
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
        this.func_226942_a_(world, pos, state.with(STAGE, 1), rand);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public RenderType getRenderLayer() {
        return RenderType.getCutout();
    }

}
