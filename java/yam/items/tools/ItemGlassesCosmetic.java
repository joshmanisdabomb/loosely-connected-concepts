package yam.items.tools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import yam.YetAnotherMod;

public class ItemGlassesCosmetic extends ItemCosmeticArmor {
	
	private String texture;
	
	public ItemGlassesCosmetic(String texture, int id) {
		super(YetAnotherMod.amCosmetic, "", id, 0, true);
		this.setTextureName(YetAnotherMod.MODID + ":glasses/" + texture);
		this.texture = texture;
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Cosmetic: " + EnumChatFormatting.GREEN + "Yes");
	}
	
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		return YetAnotherMod.MODID + ":textures/armor/glasses/" + texture + ".png";
	}
	
}
