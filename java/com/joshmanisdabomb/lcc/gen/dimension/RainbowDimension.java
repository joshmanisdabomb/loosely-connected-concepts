package com.joshmanisdabomb.lcc.gen.dimension;

import com.joshmanisdabomb.lcc.gen.dimension.provider.MultiBiomeProvider;
import com.joshmanisdabomb.lcc.registry.LCCBiomes;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.EndGenerationSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

public class RainbowDimension extends Dimension {

    @OnlyIn(Dist.CLIENT)
    private static float rainbowClientTicks;

    public RainbowDimension(World world, DimensionType type) {
        super(world, type, 0.06F);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        EndGenerationSettings genSettings = ChunkGeneratorType.FLOATING_ISLANDS.createSettings();
        genSettings.setDefaultBlock(LCCBlocks.twilight_stone.getDefaultState());
        genSettings.setDefaultFluid(Blocks.AIR.getDefaultState());
        genSettings.setSpawnPos(new BlockPos(0, 100, 0));

        MultiBiomeProvider.MultiBiomeProviderSettings bpSettings = LCCDimensions.multiple_biomes.createSettings(this.world.getWorldInfo())
            .addBiome(new BiomeManager.BiomeEntry(LCCBiomes.rainbow_candyland, 20))
            .addBiome(new BiomeManager.BiomeEntry(LCCBiomes.rainbow_colorful, 20))
            .addBiome(new BiomeManager.BiomeEntry(LCCBiomes.rainbow_starlight, 20))
            .addBiome(new BiomeManager.BiomeEntry(LCCBiomes.rainbow_terrene, 20));

        return LCCDimensions.floating_islands_amplified.create(this.world, LCCDimensions.multiple_biomes.create(bpSettings), genSettings);
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
        Color c = this.getRainbowColor();
        final float f = MathHelper.clamp(MathHelper.cos(this.world.getCelestialAngle(partialTicks) * ((float)Math.PI * 2F)) * 2.0F + 0.5F, 0.0F, 1.0F);
        return new Vec3d((c.getRed() / 255F) * 0.1F + (f * 0.9F), (c.getGreen() / 255F) * 0.1F + (f * 0.9F), (c.getBlue() / 255F) * 0.1F + (f * 0.9F));
    }

    /*@Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getSkyColor(BlockPos cameraPos, float partialTicks) {
        rainbowClientTicks += partialTicks;
        Color c = this.getRainbowColor();
        return new Vec3d(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
    }*/

    @OnlyIn(Dist.CLIENT)
    public Color getRainbowColor() {
        return new Color(Color.HSBtoRGB((rainbowClientTicks / 5000F) % 1F, 1.0F, 1.0F));
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
