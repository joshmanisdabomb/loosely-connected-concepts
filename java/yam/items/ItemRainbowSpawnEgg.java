package yam.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemRainbowSpawnEgg extends ItemSpawnEgg {

	public ItemRainbowSpawnEgg(int id, int secondaryColor) {
		super(id, 0, secondaryColor);
		this.setTextureName("yam:rainbow/egg");
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return (par2 == 0) ? Integer.parseInt("FFFFFF", 16) : secondaryColor;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon("yam:rainbow/eggOverlay");
    }

}
