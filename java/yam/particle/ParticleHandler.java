package yam.particle;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.packet.PacketParticle;
import yam.packet.PacketParticleExplosion;
import yam.packet.PacketParticleSpark;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ParticleHandler {
	
	public static void spawnParticle(World world, ParticleType type, double x, double y, double z, double speedX, double speedY, double speedZ) {
		if (world.isRemote) {
			spawnParticleClient(world, type, x, y, z, speedX, speedY, speedZ);
			YetAnotherMod.channel.sendToAllAround(new PacketParticle(type, x, y, z, speedX, speedY, speedZ), new TargetPoint(world.provider.dimensionId, x, y, z, 40));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void spawnParticleClient(World world, ParticleType type, double x, double y, double z, double speedX, double speedY, double speedZ) {
		EntityFX effect;
		switch(type) {	
			case RAINBOW: {
				effect = new EntityRainbowFX(world, x, y, z, speedX, speedY, speedZ);
				Minecraft.getMinecraft().effectRenderer.addEffect(effect);
				return;
			}
			case RAINSPLOSION: {
				effect = new EntityRainsplodeFX(Minecraft.getMinecraft().renderEngine, world, x, y, z, speedX, speedY, speedZ);
				Minecraft.getMinecraft().effectRenderer.addEffect(effect);
				return;
			}
			case BLASTER: {
				effect = new EntityBlasterFX(world, x, y, z, speedX, speedY, speedZ);
				Minecraft.getMinecraft().effectRenderer.addEffect(effect);
				return;
			}
			case NEON: {
				effect = new EntityNeonFX(world, x, y, z, speedX, speedY, speedZ);
				Minecraft.getMinecraft().effectRenderer.addEffect(effect);
				return;
			}
			case AMPLISLIME: {
				effect = new EntityAmplislimeFX(world, x, y, z, speedX, speedY, speedZ, YetAnotherMod.amplifyBomb, 0);
				Minecraft.getMinecraft().effectRenderer.addEffect(effect);
				return;
			}
			case AMPSPLOSION: {
				effect = new EntityAmplifyBombFX(Minecraft.getMinecraft().renderEngine, world, x, y, z, speedX, speedY, speedZ);
				Minecraft.getMinecraft().effectRenderer.addEffect(effect);
				return;
			}
			case CLOUD: {
				world.spawnParticle("cloud", x, y, z, speedX, speedY, speedZ);
				return;
			}
		}
	}
	
	public static void particleSpark(World world, ParticleType type, double x, double y, double z, double speed, int intensity) {
		if (world.isRemote) {
			particleSparkClient(world, type, x, y, z, speed, intensity);
			YetAnotherMod.channel.sendToAllAround(new PacketParticleSpark(type, x, y, z, speed, intensity), new TargetPoint(world.provider.dimensionId, x, y, z, 40));
		}
	}

	@SideOnly(Side.CLIENT)
    public static void particleSparkClient(World par1World, ParticleType type, double x, double y, double z, double speed, int intensity) {
    	Random rand = new Random();
    	for (int i = 0; i < intensity; i++) {
    		double speedX = (rand.nextGaussian() - 0.5) * (speed*2);
    	    double speedY = (rand.nextGaussian() - 0.5) * (speed*2);
    	    double speedZ = (rand.nextGaussian() - 0.5) * (speed*2);
    	    spawnParticleClient(par1World, type, x, y, z, speedX, speedY, speedZ);
		}
    }
	
	public static void particleSparkUpwards(World world, ParticleType type, double x, double y, double z, double speed, double speedY, int intensity) {
		if (world.isRemote) {
			particleSparkUpwardsClient(world, type, x, y, z, speed, speedY, intensity);
			YetAnotherMod.channel.sendToAllAround(new PacketParticleSpark(type, x, y, z, speed, intensity), new TargetPoint(world.provider.dimensionId, x, y, z, 40));
		};
	}

	@SideOnly(Side.CLIENT)
    public static void particleSparkUpwardsClient(World par1World, ParticleType type, double x, double y, double z, double speed, double speedY, int intensity) {
    	Random rand = new Random();
    	for (int i = 0; i < intensity; i++) {
    		double speedX = (rand.nextGaussian() - 0.5) * speed;
    	    double speedZ = (rand.nextGaussian() - 0.5) * speed;
    	    spawnParticleClient(par1World, type, x, y, z, speedX, speedY, speedZ);
		}
    }

	public static void particleExplosion(World world, ParticleType type, double x, double y, double z, double range, int amount) {
		if (world.isRemote) {
			particleExplosionClient(world, type, x, y, z, range, amount);
			YetAnotherMod.channel.sendToAllAround(new PacketParticleExplosion(type, x, y, z, range, amount), new TargetPoint(world.provider.dimensionId, x, y, z, 40));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void particleExplosionClient(World world, ParticleType type, double x, double y, double z, double range, int amount) {
		Random rand = new Random();
    	for (int i = 0; i < amount; i++) {
    		double x2 = x + ((rand.nextDouble() - 0.5) * range);
    		double y2 = y + ((rand.nextDouble() - 0.5) * range);
    		double z2 = z + ((rand.nextDouble() - 0.5) * range);
    	    spawnParticleClient(world, type, x2, y2, z2, 0, 0, 0);
		}
	}
    
    public static enum ParticleType {
    	RAINBOW(0),
    	RAINSPLOSION(1),
    	BLASTER(2),
    	NEON(3),
    	AMPLISLIME(4),
    	AMPSPLOSION(5),
    	CLOUD(6);
    	
    	private int code;
    	
    	ParticleType(int code) {
    		this.code = code;
    	}

		public int getCode() {
			return code;
		}
		
		public static ParticleType getFromCode(int code) {
			for (ParticleType pt : ParticleType.values()) {
				if (pt.getCode() == code) {
					return pt;
				}
			}
			return null;
		}
    }
    
}
