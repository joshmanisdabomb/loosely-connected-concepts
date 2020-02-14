package com.joshmanisdabomb.lcc.gen.dimension;

import com.joshmanisdabomb.lcc.registry.LCCDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nullable;
import java.util.Random;

public class RainbowDimension extends Dimension {

    public RainbowDimension(World world, DimensionType type) {
        super(world, type);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        EndGenerationSettings genSettings = ChunkGeneratorType.FLOATING_ISLANDS.createSettings();
        genSettings.setDefaultBlock(Blocks.STONE.getDefaultState());
        genSettings.setDefaultFluid(Blocks.AIR.getDefaultState());
        genSettings.setSpawnPos(new BlockPos(0, 100, 0));

        MultiBiomeProvider.MultiBiomeProviderSettings bpSettings = LCCDimensions.multiple_biomes.createSettings()
            .setWorldInfo(this.world.getWorldInfo())
            .addBiome(new BiomeManager.BiomeEntry(Biomes.PLAINS, 20))
            .addBiome(new BiomeManager.BiomeEntry(Biomes.NETHER, 20))
            .addBiome(new BiomeManager.BiomeEntry(Biomes.THE_END, 20));

        return ChunkGeneratorType.FLOATING_ISLANDS.create(this.world, LCCDimensions.multiple_biomes.create(bpSettings), genSettings);
    }

    protected void generateLightBrightnessTable() {
        super.generateLightBrightnessTable();
        for(int i = 0; i <= 15; ++i) {
            this.lightBrightnessTable[i] = (this.lightBrightnessTable[i] * 0.9F);
        }
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        Random random = new Random(this.world.getSeed());
        BlockPos blockpos = new BlockPos(chunkPosIn.getXStart() + random.nextInt(15), 0, chunkPosIn.getZEnd() + random.nextInt(15));
        return this.world.getGroundAboveSeaLevel(blockpos).getMaterial().blocksMovement() ? blockpos : null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return this.findSpawn(new ChunkPos(posX >> 4, posZ >> 4), checkValid);
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.0F;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d(1, 1, 1);
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    @Override
    public boolean hasSkyLight() {
        return true;
    }

}
