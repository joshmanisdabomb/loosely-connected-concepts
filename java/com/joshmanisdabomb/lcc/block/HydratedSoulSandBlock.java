package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class HydratedSoulSandBlock extends SoulSandBlock {

    public HydratedSoulSandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        LCCBlocks.hydrated_soul_sand_bubble_column.place(world, pos.up(), false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        Direction side = Direction.random(rand);
        world.addOptionalParticle(LCCParticles.hydrated_soul_sand_bubble,
            (double)pos.getX() + (side.getXOffset() != 0 ? (MathHelper.clamp(side.getXOffset(), -0.1D, 1D) + (rand.nextDouble() * 0.1D)) : rand.nextDouble()),
            (double)pos.getY() + (side.getYOffset() != 0 ? (MathHelper.clamp(side.getYOffset(), -0.1D, 1D) + (rand.nextDouble() * 0.1D)) : rand.nextDouble()),
            (double)pos.getZ() + (side.getZOffset() != 0 ? (MathHelper.clamp(side.getZOffset(), -0.1D, 1D) + (rand.nextDouble() * 0.1D)) : rand.nextDouble()),
            side.getXOffset() == 0 ? ((rand.nextDouble() - 0.5) * 0.1) : 0,
            side.getYOffset() == 0 ? ((rand.nextDouble() - 0.5) * 0.1) : 0,
            side.getZOffset() == 0 ? ((rand.nextDouble() - 0.5) * 0.1) : 0);
    }

}
