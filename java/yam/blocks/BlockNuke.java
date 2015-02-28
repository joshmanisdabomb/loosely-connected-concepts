package yam.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.entity.EntityNukePrimed;

public class BlockNuke extends BlockGeneric {

	public BlockNuke(String textureFront, String textureSide, String textureTop, String textureBottom) {
		super(Material.tnt, textureFront, textureSide, textureTop, textureBottom);
		
		this.setHardness(0.0F);
		this.setResistance(0);
		this.setStepSound(Block.soundTypeGrass);
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);

        if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            this.setOff(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
    {
        if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            this.setOff(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
	
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
    {
        if (!par1World.isRemote)
        {
        	EntityNukePrimed entitytntprimed = new EntityNukePrimed(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F));
            entitytntprimed.fuse = par1World.rand.nextInt(20) + 10;
            par1World.spawnEntityInWorld(entitytntprimed);
        }
    }
	
	public void setOff(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            if ((par5 & 1) == 1)
            {
            	EntityNukePrimed entitytntprimed = new EntityNukePrimed(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F));
                par1World.spawnEntityInWorld(entitytntprimed);
                par1World.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 2.0F, 0.0F);
                par1World.playSoundAtEntity(entitytntprimed, YetAnotherMod.MODID + ":blocks.nuke.siren", 64.0F, 1.0F);
            }
        }
    }
	
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity instanceof EntityArrow && !par1World.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow)par5Entity;

            if (entityarrow.isBurning())
            {
                this.setOff(par1World, par2, par3, par4, 1);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par5EntityPlayer.getCurrentEquippedItem() != null && (par5EntityPlayer.getCurrentEquippedItem().getItem() == Items.flint_and_steel || par5EntityPlayer.getCurrentEquippedItem().getItem() == YetAnotherMod.atomSplitter))
        {
            this.setOff(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
            return true;
        }
        else
        {
            return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
        }
    }
	
	public boolean canDropFromExplosion(Explosion par1Explosion)
    {
        return false;
    }
	
	public void getExtraInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Fuse Time: " + EnumChatFormatting.DARK_GRAY + "16.0 seconds");
		list.add(EnumChatFormatting.GRAY + "Radius: " + EnumChatFormatting.DARK_GRAY + "100.0 blocks");
		list.add(EnumChatFormatting.GRAY + "Point Blank Damage: " + EnumChatFormatting.DARK_GRAY + "25.0 hearts");
		list.add(EnumChatFormatting.GRAY + "Creates: " + EnumChatFormatting.DARK_GREEN + "Nuclear Fire" + EnumChatFormatting.DARK_GRAY + ", " + EnumChatFormatting.GRAY + "Nuclear Waste" + EnumChatFormatting.DARK_GRAY);
		list.add(EnumChatFormatting.GRAY + "Debuffs: " + EnumChatFormatting.DARK_GREEN + "Radiation I-IV");
	}

}
