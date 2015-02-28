package yam.items.tools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ISpecialArmor;
import yam.YetAnotherMod;

public class ItemCosmeticArmor extends ItemArmor {

	private String texture;
	private int placement;
	private boolean cosmetic;
	protected String extraInfo;
	
	public ItemCosmeticArmor(ArmorMaterial p_i45325_1_, String texture, int p_i45325_2_, int placement, boolean cosmetic) {
		super(p_i45325_1_, p_i45325_2_, placement);
		
		this.setCreativeTab(YetAnotherMod.global);
		
		this.texture = texture;
		this.placement = placement;
		this.cosmetic = cosmetic;
		
		switch (placement) {
			default: this.setTextureName(YetAnotherMod.MODID + ":" + texture + "/helmet"); return;
			case 1: this.setTextureName(YetAnotherMod.MODID + ":" + texture + "/chestplate"); return;
			case 2: this.setTextureName(YetAnotherMod.MODID + ":" + texture + "/leggings"); return;
			case 3: this.setTextureName(YetAnotherMod.MODID + ":" + texture + "/boots"); return;
		}
	}

	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		return (this.placement == 2) ? YetAnotherMod.MODID + ":textures/armor/" + texture + "_layer_2.png" : YetAnotherMod.MODID + ":textures/armor/" + texture + "_layer_1.png";
	}

	public static boolean isFullHazmat(ItemStack equipmentInSlot, ItemStack equipmentInSlot2, ItemStack equipmentInSlot3, ItemStack equipmentInSlot4) {
		if (equipmentInSlot == null || equipmentInSlot2 == null || equipmentInSlot3 == null || equipmentInSlot4 == null) {return false;}
		return (equipmentInSlot.getItem() == YetAnotherMod.hazmatBoots && equipmentInSlot2.getItem() == YetAnotherMod.hazmatLeggings && equipmentInSlot3.getItem() == YetAnotherMod.hazmatChestplate && equipmentInSlot4.getItem() == YetAnotherMod.hazmatHelmet);
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (cosmetic) {list.add(EnumChatFormatting.GRAY + "Cosmetic: " + EnumChatFormatting.GREEN + "Yes");}
		if (extraInfo != null) {list.add(extraInfo);}
	}
	
	public void setExtraInformation(String line) {
		this.extraInfo = line;
	}

}
