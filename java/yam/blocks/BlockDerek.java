package yam.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.entity.EntityDerek;

public class BlockDerek extends BlockGeneric {

	public BlockDerek(String texture) {
		super(Material.gourd, texture, "wasteland/derek/skin", "wasteland/derek/skin", "wasteland/derek/skin");
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (correctStructureX(par1World, par2, par3, par4) || correctStructureZ(par1World, par2, par3, par4)) {
			breakStructure(par1World, par2, par3, par4);
			spawnGolem(par1World, par2, par3, par4);
		}
	}

	private void spawnGolem(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			EntityDerek derek = new EntityDerek(par1World);
			derek.setLocationAndAngles((double)par2 + 0.5D, (double)par3 - 1.95D, (double)par4 + 0.5D, 0.0F, 0.0F);
	        par1World.spawnEntityInWorld(derek);
	        par1World.createExplosion(derek, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, 8, false);
		}
	}

	private boolean correctStructureX(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlock(par2, par3 - 1, par4) != YetAnotherMod.derekHeart) {return false;}
		if (par1World.getBlock(par2 - 1, par3 - 1, par4) != YetAnotherMod.derekSkin) {return false;}
		if (par1World.getBlock(par2 + 1, par3 - 1, par4) != YetAnotherMod.derekSkin) {return false;}
		if (par1World.getBlock(par2, par3 - 2, par4) != YetAnotherMod.derekSoul) {return false;}
		return true;
	}
	
	private boolean correctStructureZ(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlock(par2, par3 - 1, par4) != YetAnotherMod.derekHeart) {return false;}
		if (par1World.getBlock(par2, par3 - 1, par4 - 1) != YetAnotherMod.derekSkin) {return false;}
		if (par1World.getBlock(par2, par3 - 1, par4 + 1) != YetAnotherMod.derekSkin) {return false;}
		if (par1World.getBlock(par2, par3 - 2, par4) != YetAnotherMod.derekSoul) {return false;}
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
