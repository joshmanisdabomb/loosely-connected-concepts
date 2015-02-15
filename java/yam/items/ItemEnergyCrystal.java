package yam.items;

import yam.YetAnotherMod;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnergyCrystal extends ItemGeneric {

	public ItemEnergyCrystal(String string) {
		super(string);
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (checkPortal(par3World, par4, par5 + 1, par6, par2EntityPlayer)) {placePortal(par3World, par4, par5 + 1, par6); return true;} 
		if (checkPortal(par3World, par4, par5, par6, par2EntityPlayer)) {placePortal(par3World, par4, par5, par6); return true;} 
		return false;
	}
	
	private boolean checkPortal(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (par1World.getBlock(par2 + 2, par3, par4 + 1) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 + 2, par3, par4 - 1) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 - 2, par3, par4) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 - 2, par3, par4 + 1) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 - 2, par3, par4 - 1) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2, par3, par4 + 2) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 + 1, par3, par4 + 2) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 - 1, par3, par4 + 2) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2, par3, par4 - 2) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 + 1, par3, par4 - 2) != YetAnotherMod.rainbowBlock) {return false;}
		if (par1World.getBlock(par2 - 1, par3, par4 - 2) != YetAnotherMod.rainbowBlock) {return false;}
		if (par5EntityPlayer.dimension != 0) {return false;}
		return true;
	}
	
	private void placePortal(World par1World, int par2, int par3, int par4) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				par1World.setBlock(par2 + i, par3, par4 + j, YetAnotherMod.rainbowPortal, 0, 2);
			}
		}
		par1World.playSound(par2, par3, par4, "yam:blocks.rainbow.portal", 1.0F, 1.0F, true);
		if (par1World.isRemote) {
			ParticleHandler.particleSparkUpwards(par1World, ParticleType.RAINBOW, par2+0.5, par3+0.5, par4+0.5, 0.05, 2, 40);
			ParticleHandler.particleSparkUpwards(par1World, ParticleType.CLOUD, par2+0.5, par3+0.5, par4+0.5, 0.05, 2, 20);
		}
	}

}
