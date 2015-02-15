package yam.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityTickField;
import yam.gui.GuiHandler;

public class BlockTickField extends BlockGeneric implements ITileEntityProvider {

	public BlockTickField() {
		super(Material.iron, "tickField");
		this.setHardness(6.0F);
		this.setResistance(30.0F);
		this.setHarvestLevel("pickaxe", 3);
		this.setLightOpacity(0);
		this.setLightLevel(1.0F);
		this.setStepSound(soundTypeMetal);
	}
	
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityTickField();
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
	    player.openGui(YetAnotherMod.instance, GuiHandler.tickfield, world, x, y, z);
        return true;
    }
    
    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
    	System.out.println(p_149674_1_.isRemote);
    	
    	TileEntityTickField tileEntity = (TileEntityTickField) p_149674_1_.getTileEntity(p_149674_2_, p_149674_3_, p_149674_4_);
    	
    	for (int i = -tileEntity.getRange(); i <= tileEntity.getRange(); i++) {
        	for (int j = -tileEntity.getRange(); j <= tileEntity.getRange(); j++) {
            	for (int k = -tileEntity.getRange(); k <= tileEntity.getRange(); k++) {
            		if (p_149674_1_.getBlock(p_149674_2_ + i, p_149674_3_ + j, p_149674_4_ + k) != YetAnotherMod.tickField) {
            			p_149674_1_.getBlock(p_149674_2_ + i, p_149674_3_ + j, p_149674_4_ + k).updateTick(p_149674_1_, p_149674_2_ + i, p_149674_3_ + j, p_149674_4_ + k, new Random());
            		}
            	}
        	}
    	}
    	
    	p_149674_1_.scheduleBlockUpdate(p_149674_2_, p_149674_3_, p_149674_4_, this, tileEntity.getSpeed());
    	super.updateTick(p_149674_1_, p_149674_4_, p_149674_4_, p_149674_4_, p_149674_5_);
    }
	
}
