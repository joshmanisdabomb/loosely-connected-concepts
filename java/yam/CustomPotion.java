package yam;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import yam.items.tools.ItemCustomArmor;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomPotion extends Potion {

	private static final ResourceLocation field_110839_f = new ResourceLocation(YetAnotherMod.MODID, "textures/gui/potions.png");
	private int statusIconIndex;
	private Random rand = new Random();
	
    public static final Potion radiation = new CustomPotion(24, true, Integer.parseInt("55AA44", 16)).setIconIndex(0,0).setPotionName("potion.radiation");
    public static final Potion bleeding = new CustomPotion(25, true, Integer.parseInt("772211", 16)).setIconIndex(2,0).setPotionName("potion.bleeding").func_111184_a(SharedMonsterAttributes.maxHealth, "5A141BE7-1A90-44AA-A870-474AD77DDE35", -2.0D, 0);
    public static final Potion amplify = new CustomPotion(26, true, Integer.parseInt("FFBB00", 16)).setIconIndex(4,0).setPotionName("potion.amplify");
    public static final Potion mlg = new CustomPotion(27, true, Integer.parseInt("999999", 16)).setIconIndex(6,0).setPotionName("potion.mlg");
    public static final Potion starburst = new CustomPotion(28, true, Integer.parseInt("FFBB00", 16)).setIconIndex(7,0).setPotionName("potion.starburst");
    public static final Potion kineticallychallenged = new CustomPotion(29, true, Integer.parseInt("9900CC", 16)).setIconIndex(8,0).setPotionName("potion.kineticallychallenged");
    public static final Potion charm = new CustomPotion(30, true, Integer.parseInt("FF00FF", 16)).setIconIndex(9,0).setPotionName("potion.charm");
	
	public static Potion[] potionTypes;
	
	public CustomPotion(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}
	
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2) {
		if (this.id == radiation.id) {
			if (!ItemCustomArmor.isFullHazmat(par1EntityLivingBase.getEquipmentInSlot(1), par1EntityLivingBase.getEquipmentInSlot(2), par1EntityLivingBase.getEquipmentInSlot(3), par1EntityLivingBase.getEquipmentInSlot(4))) {
				par1EntityLivingBase.attackEntityFrom(CustomDamage.nuclear, 1.0F);
			}
		} else if (this.id == mlg.id) {
			if (par1EntityLivingBase instanceof EntityPlayer) {
				((EntityPlayer)par1EntityLivingBase).refreshDisplayName();
			}
		} else if (this.id == starburst.id) {
			ParticleHandler.particleSpark(par1EntityLivingBase.worldObj, ParticleType.RAINBOW, par1EntityLivingBase.posX, par1EntityLivingBase.posY, par1EntityLivingBase.posZ, 0.1, 4);
		} else if (this.id == charm.id) {
			((EntityMob)par1EntityLivingBase).setAttackTarget(null);
			if (par1EntityLivingBase instanceof EntityMob) {AxisAlignedBB aabb = par1EntityLivingBase.boundingBox;
				if (aabb != null) {
					List entities = par1EntityLivingBase.worldObj.getEntitiesWithinAABBExcludingEntity(par1EntityLivingBase, aabb.expand(par1EntityLivingBase.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).getAttributeValue(), par1EntityLivingBase.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).getAttributeValue(), par1EntityLivingBase.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).getAttributeValue()));
					((EntityMob)par1EntityLivingBase).setAttackTarget(null);
					((EntityMob)par1EntityLivingBase).setRevengeTarget(null);
					for (Object ent : entities) {
						if (ent instanceof EntityLivingBase && !(ent instanceof EntityPlayer)) {
							((EntityMob)par1EntityLivingBase).setAttackTarget((EntityLivingBase)ent);
							((EntityMob)par1EntityLivingBase).setRevengeTarget((EntityLivingBase)ent);
						}
					}
				}
			}
		}
	}
	
	public boolean isReady(int par1, int par2) {
        int k;
        if (this.id == radiation.id) {
            k = 50 - (par2 * 10);
            return k > 0 ? par1 % k == 0 : true;
        } else if (this.id == mlg.id) {
            return true;
        } else if (this.id == starburst.id) {
            return true;
        } else if (this.id == charm.id) {
            k = 5;
            return k > 0 ? par1 % k == 0 : true;
        }
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() 
	{
	    Minecraft.getMinecraft().renderEngine.bindTexture(this.field_110839_f);
	    return true;
	}
	
	public Potion setIconIndex(int par1, int par2)
    {
        this.statusIconIndex = par1 + par2 * 8;
        return this;
    }
	
	@SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        return this.statusIconIndex;
    }
	
	public static boolean isEligible(Entity entity, boolean ignoreCreative) {
		if (!(entity instanceof EntityLivingBase)) {return false;}
		if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode && !ignoreCreative) {return false;}
		return true;
	}

	public static boolean isPro(EntityPlayer par3EntityPlayer) {
		return par3EntityPlayer.getActivePotionEffect(mlg) != null;
	}

}
