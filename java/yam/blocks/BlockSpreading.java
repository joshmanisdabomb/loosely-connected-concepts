package yam.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class BlockSpreading extends BlockGeneric {

	private int type = 1;
	private boolean charged = false;
	private boolean eater = false;
	
	public BlockSpreading(String texture, int type, boolean charged, boolean eater) {
		super(Material.ground, "spreaders/" + texture);
		
		this.type = type;
		this.charged = charged;
		this.eater = eater;
		
		this.setTickRandomly(!charged);
		this.setHardness(0.1F);
		this.setResistance(0F);
		this.setStepSound(Block.soundTypeGlass);
		this.setBlockName(texture);
		this.setLightLevel(1F);
		this.setLightOpacity(0);
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
	    super.onBlockAdded(par1World, par2, par3, par4);

	    if (par1World.getBlock(par2, par3, par4) == this && charged) {
	        par1World.scheduleBlockUpdate(par2, par3, par4, this, 20);
	    }
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote) {
			for (int var6 = 0; var6 < 3; ++var6) {
				int var7 = par2 + par5Random.nextInt(3) - 1;
                int var8 = par3 + par5Random.nextInt(3) - 1;
                int var9 = par4 + par5Random.nextInt(3) - 1;

                //Air (0,1)
                if (par1World.getBlock(var7, var8, var9) == Blocks.air && type < 2) {
                	par1World.setBlock(var7, var8, var9, this);
                }
                //Ground (1,2)
                if ((par1World.getBlock(var7, var8, var9) != Blocks.air && par1World.getBlock(var7, var8, var9) != Blocks.bedrock && par1World.getBlock(var7, var8, var9) != Blocks.dispenser && par1World.getBlock(var7, var8, var9) != Blocks.chest && par1World.getBlock(var7, var8, var9) != Blocks.furnace && par1World.getBlock(var7, var8, var9) != Blocks.brewing_stand && par1World.getBlock(var7, var8, var9) != Blocks.end_portal && par1World.getBlock(var7, var8, var9) != Blocks.end_portal_frame && par1World.getBlock(var7, var8, var9) != Blocks.command_block && par1World.getBlock(var7, var8, var9) != Blocks.beacon && par1World.getBlock(var7, var8, var9) != Blocks.trapped_chest && par1World.getBlock(var7, var8, var9) != Blocks.hopper && par1World.getBlock(var7, var8, var9) != Blocks.dropper && par1World.getBlock(var7, var8, var9) != YetAnotherMod.antispreader) && type > 0) {
                	par1World.setBlock(var7, var8, var9, this);
                }
                if (eater) {
                	par1World.setBlock(par2, par3, par4, Blocks.air);
                }
                if (charged) {
                	par1World.scheduleBlockUpdate(par2, par3, par4, this, 20);
                }
			}
		}
	}

	public void getExtraInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Spreads In: " + (type == 0 ? EnumChatFormatting.DARK_PURPLE + "Air" : type == 1 ? EnumChatFormatting.GREEN + "Both" : EnumChatFormatting.GOLD + "Ground"));
		list.add(EnumChatFormatting.GRAY + "Speed: " + (charged ? EnumChatFormatting.AQUA + "Fast" : EnumChatFormatting.BLUE + "Normal"));
		list.add(EnumChatFormatting.GRAY + "Eats: " + (eater ? EnumChatFormatting.GREEN + "Yes" : EnumChatFormatting.RED + "No"));
	}

}
