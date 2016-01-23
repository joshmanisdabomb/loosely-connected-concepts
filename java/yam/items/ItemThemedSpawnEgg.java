package yam.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import yam.YetAnotherMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemThemedSpawnEgg extends ItemSpawnEgg {

	private String texturePath;
	
	public ItemThemedSpawnEgg(String texturePath, int id, int primary, int secondary) {
		super(id, primary, secondary);
		this.texturePath = texturePath;
		this.setTextureName(YetAnotherMod.MODID + ":egg/" + texturePath + "/base");
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return (par2 == 0) ? primaryColor : secondaryColor;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":egg/" + texturePath + "/overlay");
    }

}
