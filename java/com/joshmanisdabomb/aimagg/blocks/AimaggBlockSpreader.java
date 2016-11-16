package com.joshmanisdabomb.aimagg.blocks;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.te.AimaggTESpreader;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;

public class AimaggBlockSpreader extends AimaggBlockBasic implements ITileEntityProvider {
	
	public static final PropertyInteger ID = PropertyInteger.create("id", 0, 15);
	
	public AimaggBlockSpreader(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(internalName, sortVal, material, mcolor);
		this.setLightOpacity(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ID, 0));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ID);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ID, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTESpreader();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.removeTileEntity(pos);
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity te = world.getTileEntity(pos);
        return te == null ? false : te.receiveClientEvent(id, param);
    }
	
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		TileEntity te = worldIn.getTileEntity(pos);	
	    if (te instanceof AimaggTESpreader) {
			int speedMin = 42 - (((AimaggTESpreader)te).getTileData().getInteger("speed") * 2);
	    	worldIn.scheduleUpdate(pos, this, speedMin + this.RANDOM.nextInt(speedMin + 1));
	    }
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	    NBTTagCompound sNBT = stack.getSubCompound(Constants.MOD_ID + "_spreader", false);
	    if (sNBT != null) {
	    	int speed = sNBT.getInteger("speed");
			int speedMin = 42 - (speed * 2);

		    TileEntity te = world.getTileEntity(pos);
		    ((AimaggTESpreader)te).setFromValues(speed, sNBT.getInteger("damage"), sNBT.getInteger("range"), sNBT.getInteger("spread"), sNBT.getBoolean("eating"), sNBT.getInteger("child"), sNBT.getBoolean("inGround"), sNBT.getBoolean("inLiquid"), sNBT.getBoolean("inAir"));
		    
			world.scheduleUpdate(pos, this, speedMin + this.RANDOM.nextInt(speedMin + 1));
	    }
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		TileEntity te = worldIn.getTileEntity(pos);	
	    if (te instanceof AimaggTESpreader) {
			int speed = ((AimaggTESpreader)te).getTileData().getInteger("speed");
			int speedMin = 42 - (speed * 2);
			int range = ((AimaggTESpreader)te).getTileData().getInteger("range");
			int spread = ((AimaggTESpreader)te).getTileData().getInteger("spread");
			int maxFails = ((spread*2)+3)*((spread*2)+3)*((spread*2)+3);
			int child = ((AimaggTESpreader)te).getTileData().getInteger("child");
			boolean eating = ((AimaggTESpreader)te).getTileData().getBoolean("eating");
	
			boolean inGround = ((AimaggTESpreader)te).getTileData().getBoolean("inGround");
			boolean inLiquid = ((AimaggTESpreader)te).getTileData().getBoolean("inLiquid");
			boolean inAir = ((AimaggTESpreader)te).getTileData().getBoolean("inAir");
			
			boolean destroy = false;
			int fails = 0;
			
			if (range == 65 || child < (range+1)*3) {
				for (int i = -spread-1; i <= spread+1; i++) {
					for (int j = -spread-1; j <= spread+1; j++) {
						for (int k = -spread-1; k <= spread+1; k++) {
							IBlockState state2 = worldIn.getBlockState(pos.add(i, j, k));
							if (state2.getBlock() == AimaggBlocks.spreader) {
								fails++;
							} else if (rand.nextInt(eating ? 4 : 10) == 0) {
								if ((inLiquid && state2.getMaterial().isLiquid()) || (inAir && state2.getMaterial() == Material.AIR) || (inGround && !state2.getMaterial().isLiquid() && !(state2.getMaterial() == Material.AIR))) {
									worldIn.setBlockState(pos.add(i, j, k), state, 3);
									
									TileEntity te2 = worldIn.getTileEntity(pos.add(i, j, k));
									((AimaggTESpreader)te2).setFromOtherTE((AimaggTESpreader)te);
									if (range < 65) {te2.getTileData().setInteger("child", child+Math.min(Math.abs(i),Math.min(Math.abs(j),Math.abs(k))));}
									
									worldIn.scheduleUpdate(pos.add(i, j, k), this, speedMin + rand.nextInt(speedMin + 1));
									destroy = eating;
								}
							}
						}
					}
				}
				
				if (destroy) {
					worldIn.setBlockToAir(pos);
				} else if (fails < maxFails) {
					worldIn.scheduleUpdate(pos, this, speedMin + rand.nextInt(speedMin + 1));
				}
			}
	    }
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ID);
	}

}
