package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class HydratedSoulSandBlock extends Block {

    public HydratedSoulSandBlock(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        double x = (double)pos.getX();
        double y = (double)pos.getY();
        double z = (double)pos.getZ();
        world.addOptionalParticle(ParticleTypes.BUBBLE_POP, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
    }

}
