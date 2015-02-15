package yam.items;

import yam.YetAnotherMod;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemNoms extends ItemFood {

	private boolean drink = false;
	
	public ItemNoms(String string, int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);

		this.setTextureName(YetAnotherMod.MODID + ":food/" + string);
		this.setCreativeTab(YetAnotherMod.global);
	}
	
	public void setDrinkable() {
		drink = true;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return drink ? EnumAction.drink : EnumAction.eat;
    }

}
