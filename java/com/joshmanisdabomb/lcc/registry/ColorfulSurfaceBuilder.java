package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.gen.biome.RainbowColorfulBiome;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class ColorfulSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

    public ColorfulSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> deserialiser) {
        super(deserialiser);
    }

    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState top, BlockState middle, BlockState bottom, int sealevel) {
        BlockState blockstate = top;
        BlockState blockstate1 = middle;
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
        int i = -1;
        int j = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int k = x & 15;
        int l = z & 15;

        for(int i1 = startHeight; i1 >= 0; --i1) {
            blockpos$mutableblockpos.setPos(k, i1, l);
            BlockState blockstate2 = chunkIn.getBlockState(blockpos$mutableblockpos);
            if (blockstate2.isAir()) {
                i = -1;
            } else if (blockstate2.getBlock() == defaultBlock.getBlock()) {
                if (i == -1) {
                    if (j <= 0) {
                        blockstate = Blocks.AIR.getDefaultState();
                        blockstate1 = defaultBlock;
                    } else if (i1 >= sealevel - 4 && i1 <= sealevel + 1) {
                        blockstate = top;
                        blockstate1 = middle;
                    }

                    if (i1 < sealevel && blockstate.isAir()) {
                        blockpos$mutableblockpos.setPos(k, i1, l);
                    }

                    i = j;
                    if (i1 >= sealevel - 1) {
                        if (blockstate != top) {
                            chunkIn.setBlockState(blockpos$mutableblockpos, blockstate, false);
                        } else {
                            DyeColor color = RainbowColorfulBiome.getColor(x, i1, z);
                            chunkIn.setBlockState(blockpos$mutableblockpos, LCCBlocks.sparkling_grass_block.get(color).getDefaultState(), false);
                        }
                    } else if (i1 < sealevel - 7 - j) {
                        blockstate = Blocks.AIR.getDefaultState();
                        blockstate1 = defaultBlock;
                        chunkIn.setBlockState(blockpos$mutableblockpos, bottom, false);
                    } else {
                        chunkIn.setBlockState(blockpos$mutableblockpos, blockstate1, false);
                    }
                } else if (i > 0) {
                    --i;
                    chunkIn.setBlockState(blockpos$mutableblockpos, blockstate1, false);
                }
            }
        }

    }

}
