package yam.items;

import java.util.List;

import yam.CustomPotion;
import yam.YetAnotherMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRadioactive extends ItemGeneric {

	private int radioactivity;

	public ItemRadioactive(String texture, int radioactivity) {
		super(texture);
		
		this.radioactivity = radioactivity;
	}
	
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par3Entity instanceof EntityLivingBase) {
			if (par3Entity instanceof EntityPlayer && ((EntityPlayer)par3Entity).capabilities.isCreativeMode) {return;}
			((EntityLivingBase) par3Entity).addPotionEffect(new PotionEffect(CustomPotion.radiation.id, 160, radioactivity));
		}
	}

	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Debuffs: " + EnumChatFormatting.DARK_GREEN + "Radiation " + (radioactivity == 0 ? "I" : StatCollector.translateToLocal("potion.potency." + radioactivity).trim()));
	}
	
}
