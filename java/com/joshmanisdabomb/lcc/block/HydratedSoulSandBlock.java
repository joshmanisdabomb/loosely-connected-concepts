package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.data.capability.SpreaderCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class HydratedSoulSandBlock extends Block {

    public static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    private static final Predicate<BlockState> WATER = state -> state.getBlock() == Blocks.WATER || state.getFluidState().getFluid() == Fluids.FLOWING_WATER;

    public HydratedSoulSandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.isInWater()) {
            double y = entityIn.getMotion().getY();
            if (y > 0) {
                int waterHeight = 1;
                while (WATER.test(worldIn.getBlockState(pos.up(waterHeight)))) {
                    waterHeight++;
                }
                if (waterHeight > 1) entityIn.setMotion(entityIn.getMotion().mul(1.0D, 0.0D, 1.0D).add(0.0D, 0.16D*waterHeight, 0.0D));
            }
        }
        entityIn.setMotion(entityIn.getMotion().mul(0.4D, 1.0D, 0.4D));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        Direction side = Direction.random(rand);
        world.addOptionalParticle(ParticleTypes.BUBBLE_POP,
            (double)pos.getX() + (side.getXOffset() != 0 ? (MathHelper.clamp(side.getXOffset(), -0.1D, 1D) + (rand.nextDouble() * 0.1D)) : rand.nextDouble()),
            (double)pos.getY() + (side.getYOffset() != 0 ? (MathHelper.clamp(side.getYOffset(), -0.1D, 1D) + (rand.nextDouble() * 0.1D)) : rand.nextDouble()),
            (double)pos.getZ() + (side.getZOffset() != 0 ? (MathHelper.clamp(side.getZOffset(), -0.1D, 1D) + (rand.nextDouble() * 0.1D)) : rand.nextDouble()),
            0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return true;
    }

}
