package yam.dimension.sheol;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;
import yam.YetAnotherMod;

public class SheolDimProvider extends WorldProvider {
	
	private float[] colorsSunriseSunset = new float[4];

	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(YetAnotherMod.biomeSheol, 0.0F);
		this.dimensionId = YetAnotherMod.sheolDimID;
	}
	
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderSheol(this.worldObj, this.worldObj.getSeed());
    }
	
	@Override
	public String getDimensionName() {
		return "Sheol";
	}
	
	public boolean canRespawnHere() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public Vec3 drawClouds(float partialTicks) {
        return worldObj.drawCloudsBody(partialTicks);
    }

    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1) {
        return 0.0F;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int par1, int par2) {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
    	return Vec3.createVectorHelper(0.2D, 0.0D, 0.0D);
    }
    
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float par1, float par2) {
    	return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
    }
    
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }
    
    public double getMovementFactor() {
        return 1.0;
    }
    
    public String getWelcomeMessage() {
        return "Entering Sheol";
    }

    public String getDepartMessage() {
        return "Leaving Sheol";
    }
    
    protected void generateLightBrightnessTable()
    {
        for (int i = 0; i <= 15; i++) {
			this.lightBrightnessTable[i] = (float)(0.03D/(Math.sqrt(16)-Math.sqrt(i)));
		}
    }
    
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float par1, float par2)
    {
    	this.colorsSunriseSunset[0] = 0.0F;
    	this.colorsSunriseSunset[1] = 0.0F;
    	this.colorsSunriseSunset[2] = 0.0F;
    	this.colorsSunriseSunset[3] = 0.0F;
    	return this.colorsSunriseSunset;
    }
    
    public boolean isSurfaceWorld() {
    	return false;
    }

}
