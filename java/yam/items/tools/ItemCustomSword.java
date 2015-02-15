package yam.items.tools;

import yam.YetAnotherMod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;

public class ItemCustomSword extends ItemSword {

	public ItemCustomSword(String texture, ToolMaterial p_i45327_1_) {
		super(p_i45327_1_);

		this.setTextureName(YetAnotherMod.MODID + ":" + texture);
		this.setCreativeTab(YetAnotherMod.global);
	}

}
