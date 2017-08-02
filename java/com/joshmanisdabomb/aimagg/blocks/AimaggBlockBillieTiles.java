package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;

import com.joshmanisdabomb.aimagg.te.AimaggTEBillieTiles;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AimaggBlockBillieTiles extends AimaggBlockBasic implements ITileEntityProvider {

	public static final PropertyEnum<BillieTileType> BTT = PropertyEnum.<BillieTileType>create("btt", BillieTileType.class);

	public AimaggBlockBillieTiles(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(internalName, sortVal, material, mcolor);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BTT, BillieTileType.STONE));
		this.setLightOpacity(0);
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		List<Entity> list = world.getTileEntity(pos).getWorld().getEntitiesWithinAABB(Entity.class, this.getBoundingBox(state, world, pos).expand(5, 5, 5));
		int value = 0;
		for (Entity e : list) {
			/* TODO get different way of excluding these kind of entities: if (!(e instanceof EntityItemFrame || e instanceof AimaggEntityMissile || e instanceof AimaggEntityNuclearExplosion)) {
				e.getPosition();
			}*/
		}
		return value;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTEBillieTiles();
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
	
	public static enum BillieTileType implements IStringSerializable {
		STONE(0, "stone"),
		COBBLESTONE(1, "cobblestone"),
		GRANITE(2, "granite"),
		DIORITE(3, "diorite"),
		ANDESITE(4, "andesite");

		public int id;
		public String name;
		
		BillieTileType(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public int getID() {
			return id;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}

}
