package yam.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.dimension.sheol.TeleporterSheol;
import yam.entity.EntityBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlaster extends ItemGeneric {

	private String texture;
	private String bulletS;
	public IIcon bullet;
	private int damage;
	private int chargeTime;
	private Random rand = new Random();
	
	public ItemBlaster(String texture, String bullet, int charges, int chargeTime, int damage) {
		super("weapons/blaster/" + texture);
		this.texture = "weapons/blaster/" + texture;
		bulletS = "weapons/blaster/" + bullet;
		this.damage = damage;
		this.chargeTime = chargeTime;
		this.setMaxDamage(charges*chargeTime);
		this.setMaxStackSize(1);
	}
	
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	/*if ((par3EntityPlayer.ridingEntity == null) && (par3EntityPlayer.riddenByEntity == null) && ((par3EntityPlayer instanceof EntityPlayerMP)) && par3EntityPlayer.dimension == 0)
        {
            EntityPlayerMP player = (EntityPlayerMP) par3EntityPlayer;
            MinecraftServer mServer = MinecraftServer.getServer();
            player.mcServer.getConfigurationManager().transferPlayerToDimension(player, YetAnotherMod.sheolDimID, new TeleporterSheol(mServer.worldServerForDimension(YetAnotherMod.sheolDimID)));
        }*/
    	
    	if (par1ItemStack.getItemDamage() >= par1ItemStack.getMaxDamage() - chargeTime) {return par1ItemStack;}
        par1ItemStack.damageItem(chargeTime, par3EntityPlayer);
        par2World.playSoundAtEntity(par3EntityPlayer, "yam:items.blaster", 0.5F, (rand.nextFloat()*0.2F)+0.9F);
        
        if (!par2World.isRemote)
        {
        	Random rand = new Random();
        	Vec3 look = par3EntityPlayer.getLookVec();
        	EntityBullet bullet = new EntityBullet(par2World, par3EntityPlayer, 0, 0, 0);
        	bullet.setPosition(par3EntityPlayer.posX + look.xCoord * 1.6, par3EntityPlayer.posY + 1, par3EntityPlayer.posZ + look.zCoord * 1.6);
        	bullet.accelerationX = (look.xCoord * 0.08) + (rand.nextGaussian() * 0.002);
        	bullet.accelerationY = (look.yCoord * 0.08) + (rand.nextGaussian() * 0.002);
        	bullet.accelerationZ = (look.zCoord * 0.08) + (rand.nextGaussian() * 0.002);
        	bullet.setDamage(damage);
            par2World.spawnEntityInWorld(bullet);
        }

        return par1ItemStack;
    }

	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + texture);
		this.bullet = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + bulletS);
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Damage: " + EnumChatFormatting.YELLOW + (double)(damage)/2.0D + " hearts");
	}
	
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par1ItemStack.getItemDamage() > 0) {par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);}
	}
   
}
