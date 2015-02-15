package yam.items.tools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import yam.YetAnotherMod;

public class ItemCustomArmor extends ItemArmor implements ISpecialArmor {

	private String texture;
	private int placement;
	private boolean cosmetic;
	protected String extraInfo;
	private double protection;
	
	public ItemCustomArmor(ArmorMaterial p_i45325_1_, double protection, String texture, int p_i45325_2_, int placement, boolean cosmetic) {
		super(p_i45325_1_, p_i45325_2_, placement);
		
		this.setCreativeTab(YetAnotherMod.global);
		
		this.texture = texture;
		this.placement = placement;
		this.cosmetic = cosmetic;
		this.protection = protection;
		
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

	public static boolean isFullSpacesuit(ItemStack equipmentInSlot, ItemStack equipmentInSlot2, ItemStack equipmentInSlot3, ItemStack equipmentInSlot4) {
		if (equipmentInSlot == null || equipmentInSlot2 == null || equipmentInSlot3 == null || equipmentInSlot4 == null) {return false;}
		return (equipmentInSlot.getItem() == YetAnotherMod.spacesuitBoots && equipmentInSlot2.getItem() == YetAnotherMod.spacesuitLeggings && equipmentInSlot3.getItem() == YetAnotherMod.spacesuitChestplate && equipmentInSlot4.getItem() == YetAnotherMod.spacesuitHelmet);
	}

	public static int getCactusDamage(ItemStack equipmentInSlot, ItemStack equipmentInSlot1, ItemStack equipmentInSlot2, ItemStack equipmentInSlot3, ItemStack equipmentInSlot4) {
		int cactusDamage = 0;
		cactusDamage += (equipmentInSlot != null && equipmentInSlot.getItem() == YetAnotherMod.cactusSword) ? 1 : 0;
		cactusDamage += (equipmentInSlot != null && equipmentInSlot.getItem() == YetAnotherMod.cactusPickaxe) ? 1 : 0;
		cactusDamage += (equipmentInSlot != null && equipmentInSlot.getItem() == YetAnotherMod.cactusShovel) ? 1 : 0;
		cactusDamage += (equipmentInSlot != null && equipmentInSlot.getItem() == YetAnotherMod.cactusAxe) ? 1 : 0;
		cactusDamage += (equipmentInSlot != null && equipmentInSlot.getItem() == YetAnotherMod.cactusHoe) ? 1 : 0;
		cactusDamage += (equipmentInSlot1 != null && equipmentInSlot1.getItem() == YetAnotherMod.cactusBoots) ? 1 : 0;
		cactusDamage += (equipmentInSlot2 != null && equipmentInSlot2.getItem() == YetAnotherMod.cactusLeggings) ? 1 : 0;
		cactusDamage += (equipmentInSlot3 != null && equipmentInSlot3.getItem() == YetAnotherMod.cactusChestplate) ? 1 : 0;
		cactusDamage += (equipmentInSlot4 != null && equipmentInSlot4.getItem() == YetAnotherMod.cactusHelmet) ? 1 : 0;
		return cactusDamage;
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (cosmetic) {list.add("Cosmetic: \247aYes");}
		if (extraInfo != null) {list.add(extraInfo);}
	}
	
	public void setExtraInformation(String line) {
		this.extraInfo = line;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase entity, ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(5, 1.0F, (int)(damage*this.protection));
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 5;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		stack.damageItem(1, entity);
	}

}
