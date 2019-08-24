package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class HydratedSoulSandBlock extends SoulSandBlock {

    public HydratedSoulSandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        ((CustomBubbleColumnBlock)LCCBlocks.hydrated_soul_sand_bubble_column).place(world, pos.up(), false);
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

}
