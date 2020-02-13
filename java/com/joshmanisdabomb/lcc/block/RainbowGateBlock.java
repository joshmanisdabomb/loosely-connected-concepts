package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.RainbowGateModel;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.joshmanisdabomb.lcc.block.RainbowPortalBlock.AXIS;
import static com.joshmanisdabomb.lcc.block.RainbowPortalBlock.MIDDLE;

public class RainbowGateBlock extends Block implements AdvancedBlockRender {

    private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    public static final IntegerProperty Y = IntegerProperty.create("y", 0, 3);

    public RainbowGateBlock(Properties p) {
        super(p);
        this.stateContainer.getBaseState().with(Y, 0);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(Y);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.isValidPosition(world, pos)) {
            world.destroyBlock(pos, true);
        } else {
            gaterotation:
            for (int i = 0; i < 4; i++) {
                Direction d = Direction.byHorizontalIndex(i);
                if (world.getBlockState(pos.offset(d)).getBlock() == LCCBlocks.rainbow_portal) continue;

                BlockPos other = pos.offset(d, 4);
                if (world.getBlockState(other) == state) {
                    int y = state.get(Y);
                    BlockPos pos2 = pos.down(y+1);

                    List<ItemEntity> cores = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.getX() + 0.5, pos.getY() - y, pos.getZ() + 0.5, other.getX() + 0.5, other.getY() + (4 - y), other.getZ() + 0.5), e -> e.getItem().getItem() == LCCItems.chromatic_core);
                    if (cores.size() > 0) {
                        //Check ground for full blocks.
                        boolean ground = true;
                        BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos();
                        for (int j = 0; j < 5; j++) {
                            ground = ground && RainbowPortalBlock.validGround(world, bp.setPos(pos2).move(d, j));
                        }
                        if (!ground) continue;

                        //Check completion of rainbow gates.
                        BlockPos other2 = other.down(y+1);
                        for (int j = 0; j < 4; j++) {
                            if (world.getBlockState(bp.setPos(pos2).move(Direction.UP, j+1)) != LCCBlocks.rainbow_gate.getDefaultState().with(Y, j)) continue gaterotation;
                            if (world.getBlockState(bp.setPos(other2).move(Direction.UP, j+1)) != LCCBlocks.rainbow_gate.getDefaultState().with(Y, j)) continue gaterotation;
                        }

                        //Create portal.
                        cores.get(0).getItem().shrink(1);
                        for (int j = 0; j < 4; j++) {
                            for (int k = 0; k < 3; k++) {
                                world.setBlockState(bp.setPos(pos2).move(Direction.UP, j+1).move(d, k+1), LCCBlocks.rainbow_portal.getDefaultState().with(AXIS, d.getAxis()).with(Y, j).with(MIDDLE, k == 1), 18);
                            }
                        }
                    }
                }
            }
            world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
        }
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 1;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState below = world.getBlockState(pos.down());
        if (below.getBlock() == LCCBlocks.rainbow_gate) {
            int y = below.get(Y);
            if (y == 3) return null;
            return super.getStateForPlacement(context).with(Y, y+1);
        } else {
            return super.getStateForPlacement(context).with(Y, 0);
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        int y = state.get(Y);
        if (y == 0) return RainbowPortalBlock.validGround(world, pos.down());
        BlockState below = world.getBlockState(pos.down());
        return below.getBlock() == LCCBlocks.rainbow_gate && below.get(Y) == y-1;
    }

    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (!state.isValidPosition(world, currentPos)) {
            world.destroyBlock(currentPos, true);
        }
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public IBakedModel newModel(Block block) {
        return new RainbowGateModel(block);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Arrays.asList(
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/base"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/redstone"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/gold"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/lapis"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/uranium"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/coal"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/iron")
        );
    }

}
