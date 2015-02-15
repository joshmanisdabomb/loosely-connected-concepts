package yam.items;

import java.awt.Color;
import java.util.Random;

import yam.YetAnotherMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpawnEgg extends ItemGeneric {
	
	private Random rand = new Random();
	
	public int id;
	public int primaryColor;
	public int secondaryColor;
	
	@SideOnly(Side.CLIENT)
	protected IIcon theIcon;
	
	public ItemSpawnEgg(int id, int primaryColor, int secondaryColor) {
		super("");
		
		this.id = id;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		
		this.setTextureName("minecraft:spawn_egg");
	}
	
	private static int newRandomColour(Random rand) {
		int high = rand.nextInt(3);
		int low = rand.nextInt(2);
		int red; int green; int blue;
		switch (high) {
			default: {
		        red = 255;
		        switch (low) {
		        	default: {
				        green = rand.nextInt(256);
				        blue = 0;
				        return new Color(red, green, blue).getRGB();
		        	}
		        	case 1: {
				        green = 0;
				        blue = rand.nextInt(256);
				        return new Color(red, green, blue).getRGB();
		        	}
		        }
			}
			case 1: {
		        green = 255;
		        switch (low) {
		        	default: {
				        red = rand.nextInt(256);
				        blue = 0;
				        return new Color(red, green, blue).getRGB();
		        	}
		        	case 1: {
				        red = 0;
				        blue = rand.nextInt(256);
				        return new Color(red, green, blue).getRGB();
		        	}
		        }
			}
			case 2: {
		        blue = 255;
		        switch (low) {
		        	default: {
				        green = rand.nextInt(256);
				        red = 0;
				        return new Color(red, green, blue).getRGB();
		        	}
		        	case 1: {
				        green = 0;
				        red = rand.nextInt(256);
				        return new Color(red, green, blue).getRGB();
		        	}
		        }
			}
		}
	}
	
	public static Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6)
    {
        Entity entity = null;

        for (int j = 0; j < 1; ++j)
        {
            entity = EntityList.createEntityByID(par1, par0World);

            if (entity != null && entity instanceof EntityLivingBase)
            {
                EntityLiving entityliving = (EntityLiving)entity;
                entity.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F), 0.0F);
                entityliving.rotationYawHead = entityliving.rotationYaw;
                entityliving.renderYawOffset = entityliving.rotationYaw;
                entityliving.onSpawnWithEgg((IEntityLivingData)null);
                par0World.spawnEntityInWorld(entity);
                entityliving.playLivingSound();
            }
        }

        return entity;
    }
	
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else
        {
            Block block = par3World.getBlock(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && block.getRenderType() == 11)
            {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(par3World, id, (double)par4 + 0.5D, (double)par5 + d0, (double)par6 + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName())
                {
                    ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
                }

                if (!par2EntityPlayer.capabilities.isCreativeMode)
                {
                    --par1ItemStack.stackSize;
                }
            }

            return true;
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par2World.isRemote)
        {
            return par1ItemStack;
        }
        else
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

            if (movingobjectposition == null)
            {
                return par1ItemStack;
            }
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
                    {
                        return par1ItemStack;
                    }

                    if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack))
                    {
                        return par1ItemStack;
                    }

                    if (par2World.getBlock(i, j, k) instanceof BlockLiquid)
                    {
                        Entity entity = spawnCreature(par2World, par1ItemStack.getItemDamage(), (double)i, (double)j, (double)k);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName())
                            {
                                ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
                            }

                            if (!par3EntityPlayer.capabilities.isCreativeMode)
                            {
                                --par1ItemStack.stackSize;
                            }
                        }
                    }
                }

                return par1ItemStack;
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
		return (par2 == 0) ? primaryColor : secondaryColor;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon(this.getIconString() + "_overlay");
    }

}
