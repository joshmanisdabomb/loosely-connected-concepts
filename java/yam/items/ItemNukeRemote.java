package yam.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.BlockSpreading;
import yam.entity.EntityNukeMissile;
import yam.entity.EntityNukePrimed;

public class ItemNukeRemote extends ItemGeneric {

	public ItemNukeRemote(String texture, int durability) {
		super("remote/" + texture);
		
		this.setMaxDamage(durability);
		this.setMaxStackSize(1);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par2World.isRemote) {
			if (par3EntityPlayer.inventory.hasItem(Item.getItemFromBlock(YetAnotherMod.nuke))) {
				par1ItemStack.damageItem(1, par3EntityPlayer);
				par3EntityPlayer.inventory.consumeInventoryItem(Item.getItemFromBlock(YetAnotherMod.nuke));
				int x = (int)Math.round(par3EntityPlayer.posX);
				int y = (int)Math.round(par3EntityPlayer.posY);
				int z = (int)Math.round(par3EntityPlayer.posZ);
	
				EntityNukeMissile entitynukemissile = new EntityNukeMissile(par2World, (double)((float)x + 0.5F), 500, (double)((float)z + 0.5F));
		        par2World.spawnEntityInWorld(entitynukemissile);
			}
		}
        par2World.playSoundAtEntity(par3EntityPlayer, YetAnotherMod.MODID + ":blocks.nuke.siren", 64.0F, 1.0F);
		return par1ItemStack;
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Launches: \2472Nuclear Missile");
	}
	
}
