package yam.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.entity.EntityRainbowGolem;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;

public class BlockRainbowGolem extends BlockGeneric {

	public BlockRainbowGolem() {
		super(Material.gourd, "rainbow/golem", "storage/rainbow/linear", "storage/rainbow/radial", "storage/rainbow/radial");
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (correctStructureX(par1World, par2, par3, par4) || correctStructureZ(par1World, par2, par3, par4)) {
			breakStructure(par1World, par2, par3, par4);
			spawnGolem(par1World, par2, par3, par4);
		}
	}

	private void spawnGolem(World par1World, int par2, int par3, int par4) {
		ParticleHandler.particleSpark(par1World, ParticleType.RAINBOW, par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, 0.05D, 100);
		if (!par1World.isRemote) {
			EntityRainbowGolem entitygolem = new EntityRainbowGolem(par1World);
	        entitygolem.setPlayerCreated(true);
	        entitygolem.setLocationAndAngles((double)par2 + 0.5D, (double)par3 - 1.95D, (double)par4 + 0.5D, 0.0F, 0.0F);
	        par1World.spawnEntityInWorld(entitygolem);
	        
			par1World.playSoundAtEntity(entitygolem, YetAnotherMod.MODID + ":mob.rainbowgolem.spawn", 4.0F, 1.0F);
		}
	}

	private boolean correctStructureX(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlock(par2, par3 - 1, par4) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 - 1, par3 - 1, par4) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 + 1, par3 - 1, par4) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2, par3 - 2, par4) != YetAnotherMod.rainbowBlock) {return false;}
		return true;
	}
	
	private boolean correctStructureZ(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlock(par2, par3 - 1, par4) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2, par3 - 1, par4 - 1) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2, par3 - 1, par4 + 1) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2, par3 - 2, par4) != YetAnotherMod.rainbowBlock) {return false;}
		return true;
	}
	
	private void breakStructure(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) { 
			if (correctStructureX(par1World, par2, par3, par4)) {
				par1World.setBlock(par2, par3 - 1, par4, Blocks.air);
				par1World.setBlock(par2 - 1, par3 - 1, par4, Blocks.air);
				par1World.setBlock(par2 + 1, par3 - 1, par4, Blocks.air);
				par1World.setBlock(par2, par3 - 2, par4, Blocks.air);
			} else if (correctStructureZ(par1World, par2, par3, par4)) {
				par1World.setBlock(par2, par3 - 1, par4, Blocks.air);
				par1World.setBlock(par2, par3 - 1, par4 - 1, Blocks.air);
				par1World.setBlock(par2, par3 - 1, par4 + 1, Blocks.air);
				par1World.setBlock(par2, par3 - 2, par4, Blocks.air);
			}
			par1World.setBlock(par2, par3, par4, Blocks.air);
		}
	}
	
}
