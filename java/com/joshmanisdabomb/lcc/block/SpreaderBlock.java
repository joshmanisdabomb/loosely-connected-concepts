package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.data.capability.SpreaderCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class SpreaderBlock extends Block implements LCCBlockHelper, TintedBlock {

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
        return 0;
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        SpreaderCapability.Provider.getGlobal(worldIn.getServer()).ifPresent(spreader -> {
            if (!worldIn.getPendingBlockTicks().isTickScheduled(pos, this)) worldIn.getPendingBlockTicks().scheduleTick(pos, this, spreader.getTickSpeed(color, worldIn.rand));
        });
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        SpreaderCapability.Provider.getGlobal(worldIn.getServer()).ifPresent(spreader -> {
            if (!worldIn.getPendingBlockTicks().isTickScheduled(pos, this)) worldIn.getPendingBlockTicks().scheduleTick(pos, this, spreader.getTickSpeed(color, worldIn.rand));
        });
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isRemote) {
            SpreaderCapability.Provider.getGlobal(world.getServer()).ifPresent(spreader -> {
                if (spreader.isEnabled(color)) {
                    if (state.get(AGE) < 15) {
                        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(pos);
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                for (int k = -1; k <= 1; k++) {
                                    if ((i != 0 || j != 0 || k != 0) && random.nextInt(3) == 0 && isSpreadable(world, world.getBlockState(mpos.setPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k)), mpos, spreader)) {
                                        world.setBlockState(mpos, state.with(AGE, MathHelper.clamp(state.get(AGE) + spreader.getDecayAge(color, random), 0, 15)));
                                    }
                                }
                            }
                        }
                    }

                    if (spreader.isEater(color) && random.nextInt(3) == 0) {
                        world.removeBlock(pos, false);
                    } else {
                        world.getPendingBlockTicks().scheduleTick(pos, this, spreader.getTickSpeed(color, random));
                    }
                } else {
                    world.getPendingBlockTicks().scheduleTick(pos, this, random.nextInt(241) + 240);
                }
            });
        }
    }

    private boolean isSpreadable(World world, BlockState state, BlockPos pos, SpreaderCapability spreader) {
        if (state.getBlock() == this) return false;
        boolean liquid = state.getMaterial().isLiquid();
        if (spreader.throughLiquid(color) && liquid) return true;
        boolean air = state.isAir(world, pos);
        if (spreader.throughAir(color) && air) return true;
        return spreader.throughGround(color) && !liquid && !air;
    }

    @Override
    public int getBlockTintColor(BlockState state, IEnviromentBlockReader world, BlockPos pos, int tintIndex) {
        return this.getItemTintColor(null, tintIndex);
    }

    @Override
    public int getItemTintColor(ItemStack stack, int tintIndex) {
        float[] c = color.getColorComponentValues();
        return (int)(c[0]*255) << 16 | (int)(c[1]*255) << 8 | (int)(c[2]*255);
    }

}