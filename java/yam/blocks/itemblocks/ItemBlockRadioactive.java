package yam.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import yam.CustomPotion;
import yam.YetAnotherMod;
import yam.blocks.BlockRadioactive;

public class ItemBlockRadioactive extends ItemBlockExtraInfo {

	public ItemBlockRadioactive(Block block) {
		super(block);
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par3Entity instanceof EntityLivingBase) {
			if (par3Entity instanceof EntityPlayer && ((EntityPlayer)par3Entity).capabilities.isCreativeMode) {return;}
			((EntityLivingBase) par3Entity).addPotionEffect(new PotionEffect(CustomPotion.radiation.id, 160, ((BlockRadioactive)block).radioactivity));
		}
	}
	
}
