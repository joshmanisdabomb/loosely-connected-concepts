package yam.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BlockBouncepad extends BlockGeneric {

	private double height;
	private double realHeight;

	public BlockBouncepad(String texture, double height, double realHeight) {
		super(Material.ground, "bounce/" + texture + "side", "bounce/" + texture + "side", "bounce/" + texture, "bounce/" + texture);
		
		this.height = height;
		this.realHeight = realHeight;
		
		this.setHardness(0.8F);
		this.setResistance(10.0F);
		this.setStepSound(Block.soundTypeStone);
		this.setLightOpacity(0);
		
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
		this.setRenderType(0, false);
	}
	
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return par1World.isBlockNormalCubeDefault(par2, par3 - 1, par4, false);
    }
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        this.checkBlockCoordValid(par1World, par2, par3, par4);
    }
	
	protected final void checkBlockCoordValid(World par1World, int par2, int par3, int par4)
    {
        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
	
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return this.canPlaceBlockAt(par1World, par2, par3, par4);
    }
	
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
		if (height != 0.0D) {
			par1World.playSoundEffect(par2, par3, par4, "random.bow", 1.0F, 2.0F);
			par5Entity.motionY = height;
		} else if (par5Entity.fallDistance > 5) {
			par1World.playSoundEffect(par2, par3, par4, "dig.cloth", 2.0F, 1.0F);
		}
		par5Entity.fallDistance = 0.0F;
        super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
    }
	
	public void getExtraInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Estimated Height: " + (realHeight > 0 ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.DARK_GRAY) + realHeight + " blocks");
		list.add(EnumChatFormatting.GRAY + "Negates Fall Damage: " + (realHeight > 0 ? EnumChatFormatting.RED + "No" : EnumChatFormatting.GREEN + "Yes"));
	}

}
