package yam.items;

import java.util.List;

import yam.YetAnotherMod;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemAtomSplitter extends ItemGeneric {
	
	private Block block;

	public ItemAtomSplitter(String texture, Block fire, int durability)
    {
		super(texture);

		this.block = fire;
		this.setMaxDamage(durability);
		this.setMaxStackSize(1);
    }
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            --par5;
        }

        if (par7 == 1)
        {
            ++par5;
        }

        if (par7 == 2)
        {
            --par6;
        }

        if (par7 == 3)
        {
            ++par6;
        }

        if (par7 == 4)
        {
            --par4;
        }

        if (par7 == 5)
        {
            ++par4;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            if (par3World.isAirBlock(par4, par5, par6))
            {
                par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.2F);
                par3World.setBlock(par4, par5, par6, YetAnotherMod.nuclearFire);
            }

            par1ItemStack.damageItem(1, par2EntityPlayer);
            return true;
        }
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Places: " + EnumChatFormatting.DARK_GREEN + "Nuclear Fire");
	}
	
}
