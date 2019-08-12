package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.data.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.data.capability.SpreaderCapability;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.server.MinecraftServer;
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
import net.minecraftforge.common.util.LazyOptional;

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
        if (!world.isRemote) {
            SpreaderCapability.Provider.getGlobalCapability(world.getServer()).ifPresent(spreader -> {
                if (spreader.isEnabled(color)) {
                    BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(pos);
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            for (int k = -1; k <= 1; k++) {
                                if (random.nextInt(3) == 0 && isSpreadable(world, world.getBlockState(mpos.setPos(pos.getX()+i,pos.getY()+j,pos.getZ()+k)), mpos, spreader)) {
                                    world.setBlockState(pos, state.with(AGE, state.get(AGE) + spreader.getDecayAge(color, random)));
                                }
                            }
                        }
                    }

                    if (spreader.isEater(color) && random.nextInt(3) == 0) {
                        world.destroyBlock(pos, false);
                    } else {
                        world.getPendingBlockTicks().scheduleTick(pos, this, spreader.getTickSpeed(color, random));
                    }
                } else {
                    world.getPendingBlockTicks().scheduleTick(pos, this, random.nextInt(121) + 120);
                }
            });
        }

    }

    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState other, Direction side) {
        return other.getBlock() == this || super.isSideInvisible(state, other, side);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    private boolean isSpreadable(World world, BlockState state, BlockPos pos, SpreaderCapability spreader) {
        if (state.getBlock() == this) return false;
        boolean liquid = state.getMaterial().isLiquid();
        if (spreader.throughLiquid(color) && liquid) return true;
        boolean air = state.isAir(world, pos);
        if (spreader.throughAir(color) && air) return true;
        if (spreader.throughGround(color) && !liquid && !air) return true;
        return false;
    }

}