package yam.blocks;

import java.util.Random;

import yam.YetAnotherMod;
import yam.gen.GenTree;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockCustomSapling extends BlockGeneric {

	private Block[] grassTiles;
	private GenTree treeGen;
	private boolean instantBoneMealGrowth;

	public BlockCustomSapling(String texture, Block[] grassTiles, GenTree treeGenerator, boolean instantBoneMealGrowth) {
		super(Material.plants, texture);
		
		this.grassTiles = grassTiles;
		this.treeGen = treeGenerator;
		
		this.setHardness(0);
		this.setResistance(0);
		this.setLightOpacity(0);
        this.setTickRandomly(true);
        this.setBlockBounds(0.1f, 0.0F, 0.1f, 0.9f, 0.8f, 0.9f);
	}
	
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
    	Block b = p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_);
		return this.isBlockValidBase(b);
    }
	
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
	
	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);

        if (!p_149674_1_.isRemote && p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9 && p_149674_5_.nextInt(7) == 0)
        {
        	this.advanceStage(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
        }
    }
	
	public void advanceStage(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
		int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);

        if ((l & 8) == 0)
        {
            p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l | 8, 4);
        }
        else
        {
        	this.grow(p_149674_1_, p_149674_5_, p_149674_2_, p_149674_3_, p_149674_4_);
        }
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
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

	public void whenBonedorMealed(World world, Random rng, int x, int y, int z) {
		if (instantBoneMealGrowth) {
			this.grow(world, rng, x, y, z);
		} else {
        	this.advanceStage(world, x, y, z, rng);
		}
	}

	public void grow(World world, Random rng, int x, int y, int z) {
		int l = world.getBlockMetadata(x,y,z);
		
		world.setBlock(x, y, z, Blocks.air, 0, 0);
    	if (!treeGen.generateFromSapling(world, rng, x, y, z)) {
    		world.setBlock(x,y,z,this,l,0);
    	}
	}

	public boolean isBlockValidBase(Block b) {
		for (int i = 0; i < this.grassTiles.length; i++) {
			if (this.grassTiles[i] == b) {return true;}
		}
        return false;
	}

}
