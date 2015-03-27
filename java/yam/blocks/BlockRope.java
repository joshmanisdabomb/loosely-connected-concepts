package yam.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class BlockRope extends BlockGeneric {

	public BlockRope() {
		super(Material.cloth, "rope");
		
		this.setBlockName("ropeBlock");
		this.setHardness(0.3F);
		this.setResistance(0.2F);
		this.setStepSound(Block.soundTypeCloth);
		this.setLightOpacity(0);
		this.setBlockBounds(0.4375F, 0F, 0.4375F, 0.5625F, 1F, 0.5625F);
		this.setCreativeTab(null);
		this.disableStats();
	}
	
	@Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
    {
        return true;
    }
	
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return (p_149742_1_.isBlockNormalCubeDefault(p_149742_2_, p_149742_3_ + 1, p_149742_4_, false) || p_149742_1_.getBlock(p_149742_2_, p_149742_3_ + 1, p_149742_4_) == this);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        this.func_150170_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    protected final boolean func_150170_e(World p_150170_1_, int p_150170_2_, int p_150170_3_, int p_150170_4_)
    {
        if (!this.canBlockStay(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_))
        {
            this.dropBlockAsItem(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_, p_150170_1_.getBlockMetadata(p_150170_2_, p_150170_3_, p_150170_4_), 0);
            p_150170_1_.setBlockToAir(p_150170_2_, p_150170_3_, p_150170_4_);
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
    {
        return this.canPlaceBlockAt(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 1;
    }
	
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return YetAnotherMod.rope;
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are) {
		if (player.getHeldItem() == null || player.getHeldItem().getItem() != YetAnotherMod.rope) {return false;}
		int i = 0;
		Block b;
    	do {
    		b = world.getBlock(x, y-i, z);
    		if (b == Blocks.air) {
    			world.setBlock(x, y-i, z, this);
    			if (!player.capabilities.isCreativeMode) {player.inventory.getCurrentItem().stackSize -= 1;}
    			world.playSoundEffect(x, y-i, z, this.stepSound.getBreakSound(), this.stepSound.getVolume(), this.stepSound.getPitch());
    			break;
    		}
    		i += 1;
    	} while (b == this);
        return true;
    }
	
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return YetAnotherMod.rope;
    }
	
}
