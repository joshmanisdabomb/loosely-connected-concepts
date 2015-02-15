package yam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityTrashCan;
import yam.gui.GuiHandler;

public class BlockTrashCan extends BlockGeneric implements ITileEntityProvider {

	public BlockTrashCan() {
		super(Material.iron, "trashcan/side", "trashcan/side", "trashcan/top", "trashcan/top");
		this.setHardness(4.0F);
		this.setResistance(20.0F);
		this.setHarvestLevel("pickaxe", 1);
		this.disableStats();
		this.setCreativeTab(null);
		this.setLightOpacity(0);
		this.setStepSound(soundTypeAnvil);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityTrashCan();
    }
	
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    }

    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are) {
    	TileEntity tileEntity = world.getTileEntity(x, y, z);
	    if (tileEntity == null || player.isSneaking()) {return false;}
	    player.openGui(YetAnotherMod.instance, GuiHandler.trash, world, x, y, z);
        return true;
    }
    
    public int getRenderType()
    {
        return 22;
    }
    
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(YetAnotherMod.trashCan, 1);
    }
	
}
