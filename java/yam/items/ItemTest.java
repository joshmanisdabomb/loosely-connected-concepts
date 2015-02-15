package yam.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.dimension.moon.TeleporterMoon;

public class ItemTest extends ItemGeneric {

	public ItemTest(String texture) {
		super(texture);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if ((par3EntityPlayer.ridingEntity == null) && (par3EntityPlayer.riddenByEntity == null) && ((par3EntityPlayer instanceof EntityPlayerMP)))
        {
            EntityPlayerMP player = (EntityPlayerMP)par3EntityPlayer;
            MinecraftServer mServer = MinecraftServer.getServer();
            player.mcServer.getConfigurationManager().transferPlayerToDimension(player, YetAnotherMod.moonDimID, new TeleporterMoon(mServer.worldServerForDimension(YetAnotherMod.moonDimID)));
        }
		return par1ItemStack;
	}

}
