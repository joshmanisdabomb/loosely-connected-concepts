package yam.items.tools;

import yam.YetAnotherMod;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item.ToolMaterial;

public class ItemCustomPickaxe extends ItemPickaxe {

	public ItemCustomPickaxe(String texture, ToolMaterial p_i45327_1_) {
		super(p_i45327_1_);

		this.setTextureName(YetAnotherMod.MODID + ":" + texture);
		this.setCreativeTab(YetAnotherMod.global);
	}

}
