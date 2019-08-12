package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.data.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.data.capability.SpreaderCapability;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class SpreaderBlock extends Block implements LCCBlockHelper {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;

    private final DyeColor color;

    public SpreaderBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 20;
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        int age = state.get(AGE);

        System.out.println("Color: " + color.getName());
        System.out.println("Age: " + AGE);
        world.getCapability(SpreaderCapability.Provider.DEFAULT_CAPABILITY).ifPresent(spreader -> {
            System.out.println("Enabled: " + spreader.enabled.getOrDefault(color, true));
            System.out.println("Speed Level: " + spreader.speedLevel.getOrDefault(color, 0));
            System.out.println("Damage Level: " + spreader.damageLevel.getOrDefault(color, 0));
            System.out.println("Decay Level: " + spreader.decayLevel.getOrDefault(color, 0));
            System.out.println("Eating: " + spreader.eating.getOrDefault(color, false));
            System.out.println("Through Ground: " + spreader.throughGround.getOrDefault(color, true));
            System.out.println("Through Water: " + spreader.throughWater.getOrDefault(color, false));
            System.out.println("Through Air: " + spreader.throughAir.getOrDefault(color, false));
        });

    }

    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState other, Direction side) {
        return other.getBlock() == this || super.isSideInvisible(state, other, side);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}