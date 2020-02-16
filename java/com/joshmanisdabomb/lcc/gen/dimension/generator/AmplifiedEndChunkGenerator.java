package com.joshmanisdabomb.lcc.gen.dimension.generator;

import com.joshmanisdabomb.lcc.misc.AdaptedFromSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.EndGenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;

public class AmplifiedEndChunkGenerator extends NoiseChunkGenerator<EndGenerationSettings> {

    private static final float[] field_222576_h = Util.make(new float[25], (p_222575_0_) -> {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
                p_222575_0_[i + 2 + (j + 2) * 5] = f;
            }
        }
    });

    private final OctavesNoiseGenerator depthNoise;

    public AmplifiedEndChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn, EndGenerationSettings settingsIn) {
        super(worldIn, biomeProviderIn, 8, 8, 256, settingsIn, true);
        this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 16);
    }

    @Override
    protected void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {
        this.func_222546_a(noiseColumn, noiseX, noiseZ, 513.309, 513.309, 9.555149841308594D, 5.277574920654297D, 96, -4000);
    }

    @Override
    @AdaptedFromSource
    protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {double[] adouble = new double[2];
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        int i = 2;
        float f3 = this.biomeProvider.getBiomeAtFactorFour(noiseX, noiseZ).getDepth();

        for(int j = -2; j <= 2; ++j) {
            for(int k = -2; k <= 2; ++k) {
                Biome biome = this.biomeProvider.getBiomeAtFactorFour(noiseX + j, noiseZ + k);
                float f4 = biome.getDepth();
                float f5 = biome.getScale();

                float f6 = field_222576_h[j + 2 + (k + 2) * 5] / (f4 + 2.0F);
                if (f4 > 0.0F) {
                    f4 = f4 * 1.4F;
                    f5 = f5 * 1.4F;
                }
                if (biome.getDepth() > f3) {
                    f6 /= 2.0F;
                }

                f += f5 * f6;
                f1 += f4 * f6;
                f2 += f6;
            }
        }

        f = f / f2;
        f1 = f1 / f2;
        f = f * 0.9F + 0.1F;
        f1 = (f1 * 4.0F - 1.0F) / 8.0F;
        adouble[0] = (double)f1 + this.getNoiseDepthAt(noiseX, noiseZ);
        adouble[1] = (double)f;
        return adouble;
    }

    @AdaptedFromSource
    private double getNoiseDepthAt(int noiseX, int noiseZ) {
        double d0 = this.depthNoise.getValue((double)(noiseX * 200), 10.0D, (double)(noiseZ * 200), 1.0D, 0.0D, true) / 8000.0D;
        if (d0 < 0.0D) {
            d0 = -d0 * 0.3D;
        }

        d0 = d0 * 3.0D - 2.0D;
        if (d0 < 0.0D) {
            d0 = d0 / 28.0D;
        } else {
            if (d0 > 1.0D) {
                d0 = 1.0D;
            }

            d0 = d0 / 40.0D;
        }

        return d0;
    }

    @Override
    protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
        return 8.0D - p_222545_1_;
    }

    @Override
    protected double func_222551_g() {
        return ((int)super.func_222551_g() / 2D);
    }

    @Override
    protected double func_222553_h() {
        return 8.0D;
    }

    @Override
    public int getGroundHeight() {
        return 128;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

}
