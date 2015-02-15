package yam.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.biome.BiomeRainbow;

public class ItemRainbowSeeds extends ItemGeneric {

	private Random rand = new Random();
	
	public ItemRainbowSeeds(String texture) {
		super(texture);
		this.setMaxDamage(1);
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (!par3World.isRemote && par3World.getBlock(par4, par5, par6) == YetAnotherMod.rainbowDirt) {
			par3World.setBlock(par4, par5, par6, BiomeRainbow.topBlocks[rand.nextInt(16)]);
			par1ItemStack.damageItem(2, par2EntityPlayer);
			return true;
		}
		return false;
	}

}
