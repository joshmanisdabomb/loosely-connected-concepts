package com.joshmanisdabomb.aimagg.dimension.rainbow;

import java.awt.Color;
import java.util.Date;

import javax.annotation.Nullable;

import com.joshmanisdabomb.aimagg.AimaggDimension;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggWorldProviderRainbow extends WorldProvider {
	
	public static float fogTicks = 0.0F;
	
	@Override
	public DimensionType getDimensionType() {
		return AimaggDimension.RAINBOW.getDimensionType();
	}
	
	@Override
	public void init() {
		this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		fogTicks += partialTicks;
		
		final float f = MathHelper.clamp(MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) * 2.0F + 0.5F, 0.0F, 1.0F);
		
        Color c = new Color(Color.HSBtoRGB((fogTicks / 1100F) % 1F, 0.5F, f * 0.7F + 0.3F));
        return new Vec3d(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
    }
	
	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
		float f = 0.4F;
		float f1 = MathHelper.cos(celestialAngle * ((float) Math.PI * 2F)) - 0.0F;
		float f2 = -0.0F;

		final float[] colorsSunriseSunset = new float[4];
		if (f1 >= -0.4F && f1 <= 0.4F) {
			float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
			float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float) Math.PI)) * 0.99F;
			f4 = f4 * f4;
			colorsSunriseSunset[0] = f3 * 0.2F + 0.8F;
			colorsSunriseSunset[1] = f3 * f3 * 0.2F + 0.4F;
			colorsSunriseSunset[2] = f3 * f3 * 0.2F + 0.8F;
			colorsSunriseSunset[3] = f4;
			return colorsSunriseSunset;
		} else {
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(net.minecraft.entity.Entity cameraEntity, float partialTicks) {
		final float f = MathHelper.clamp(MathHelper.cos(this.world.getCelestialAngle(partialTicks) * ((float)Math.PI * 2F)) * 2.0F + 0.5F, 0.0F, 1.0F);
		
		Color c = new Color(Color.HSBtoRGB((fogTicks / 1100F) % 1F, 1.0F, f * 0.95F + 0.05F));
        return new Vec3d(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
    }

	@Override
	@SideOnly(Side.CLIENT)
    public Vec3d getCloudColor(float partialTicks) {
		final float f = MathHelper.clamp(MathHelper.cos(this.world.getCelestialAngle(partialTicks) * ((float)Math.PI * 2F)) * 2.0F + 0.5F, 0.0F, 1.0F);
		
		Color c = new Color(Color.HSBtoRGB((fogTicks / 1100F) % 1F, 0.5F, f * 0.7F + 0.3F));
        return new Vec3d(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
    }
	
	@Override
	public boolean canRespawnHere() {
        return true;
    }

	@Override
	public int getRespawnDimension(net.minecraft.entity.player.EntityPlayerMP player) {
        return this.getDimension();
    }

	@Override
    public IChunkGenerator createChunkGenerator() {
        return WorldType.DEFAULT.getChunkGenerator(world, world.getWorldInfo().getGeneratorOptions());
    }

}
