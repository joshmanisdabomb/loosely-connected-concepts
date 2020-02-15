package com.joshmanisdabomb.lcc.registry;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class ColorfulSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

    public static final OctavesNoiseGenerator[] COLORS = new OctavesNoiseGenerator[16];
    static {
        for (int i = 0; i < COLORS.length; i++) {
            COLORS[i] = new OctavesNoiseGenerator(new Random(3452108 + i), 2);
        }
    }

    public ColorfulSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> deserialiser) {
        super(deserialiser);
    }

    private DyeColor getColor(int x, int y, int z) {
        int color = 0;
        double maxNoise = Math.abs(COLORS[0].getValue((double)x * 0.025D, y * 0.025D, (double)z * 0.025D, 1.0D, 0.0D, false));
        for (int i = 1; i < COLORS.length; i++) {
            double n = Math.abs(COLORS[i].getValue((double)x * 0.025D, y * 0.025D, (double)z * 0.025D, 1.0D, 0.0D, false));
            if (n > maxNoise) {
                color = i;
                maxNoise = n;
            }
        }
        return DyeColor.byId(color);
    }

    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState top, BlockState middle, BlockState bottom, int sealevel) {
        DyeColor color = this.getColor(x, startHeight, z);
        top = LCCBlocks.sparkling_grass_block.get(color).getDefaultState();

        BlockState blockstate = top;
        BlockState blockstate1 = middle;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
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

                    if (i1 < sealevel && (blockstate == null || blockstate.isAir())) {
                        blockpos$mutableblockpos.setPos(k, i1, l);
                    }

                    i = j;
                    if (i1 >= sealevel - 1) {
                        chunkIn.setBlockState(blockpos$mutableblockpos, blockstate, false);
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
