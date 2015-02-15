package yam.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import yam.CustomPotion;
import yam.YetAnotherMod;
import yam.entity.EntityMLGArrow;
import yam.items.ItemGeneric;

public class ItemRepeater extends ItemBow {
	
	public ItemRepeater(String texture, int durability, int speed) {
		super();
		
		this.setTextureName(YetAnotherMod.MODID + ":" + texture);
		this.setCreativeTab(YetAnotherMod.global);
		this.setMaxDamage(durability);
		this.setMaxStackSize(1);
	}

	public boolean isFull3D() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
    }
	
	public void fireNormalArrow(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, boolean infinite) {
		if (!par2World.isRemote) {
			EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, 1.5F);
			entityarrow.setIsCritical(true);
			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
	        entityarrow.setDamage(2.0D + k);
			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack); if (l > 0) {
	            entityarrow.setKnockbackStrength(l);
	        }
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0) {
	            entityarrow.setFire(100);
	        }
			entityarrow.canBePickedUp = infinite ? 2 : 1;
			par2World.spawnEntityInWorld(entityarrow);
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / itemRand.nextFloat() + 0.5F);
		}
	}
	
	public static void fireMLGArrow(World par1World, EntityPlayer par2EntityPlayer) {
		if (!par1World.isRemote) {
			EntityMLGArrow entitymlgarrow = new EntityMLGArrow(par1World, par2EntityPlayer, 1.5F);
			entitymlgarrow.setIsCritical(true);
	        entitymlgarrow.setDamage(0.0D);
			entitymlgarrow.canBePickedUp = 2;
			par1World.spawnEntityInWorld(entitymlgarrow);
	        par1World.playSoundAtEntity(par2EntityPlayer, YetAnotherMod.MODID + ":items.sniper", 3.0F, 1.0F - (itemRand.nextFloat() * 0.3F + 0.15F));
		}
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (CustomPotion.isPro(par3EntityPlayer)) {
			fireMLGArrow(par2World, par3EntityPlayer);
		} else {
			boolean creative = par3EntityPlayer.capabilities.isCreativeMode;
			boolean infinite = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
			if (par3EntityPlayer.inventory.hasItem(Items.arrow)) {
				fireNormalArrow(par1ItemStack, par2World, par3EntityPlayer, infinite);
				par1ItemStack.damageItem(1, par3EntityPlayer);
				if (!infinite) {par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);}
			} else if (creative) {
				fireNormalArrow(par1ItemStack, par2World, par3EntityPlayer, true);
			}
		}
		return par1ItemStack;
    }
}
