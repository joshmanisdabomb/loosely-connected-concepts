package yam.items.tools;

import yam.YetAnotherMod;
import net.minecraft.item.ItemAxe;

public class ItemCustomAxe extends ItemAxe {

	public ItemCustomAxe(String texture, ToolMaterial p_i45327_1_) {
		super(p_i45327_1_);

		this.setTextureName(YetAnotherMod.MODID + ":" + texture);
		this.setCreativeTab(YetAnotherMod.global);
	}

}
