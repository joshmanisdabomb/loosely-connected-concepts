package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;

import com.joshmanisdabomb.aimagg.data.MissileType;
import com.joshmanisdabomb.aimagg.data.OreIngotStorage;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockOre extends AimaggBlockBasic {

    public static final PropertyEnum<OreIngotStorage> TYPE = PropertyEnum.<OreIngotStorage>create("type", OreIngotStorage.class);
    
	public AimaggBlockOre(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(internalName, sortVal, material, mcolor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, OreIngotStorage.RUBY));
	}
	
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }
	
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, OreIngotStorage.getFromMetadata(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
    
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+(is.getMetadata()*3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (OreIngotStorage ois : OreIngotStorage.getAllWithOreForm()) {
        	items.add(new ItemStack(this, 1, ois.getMetadata()));
        }
	}
        
	@Override
	public ItemBlock getItemBlock() {
		ItemBlock ib = new ItemBlock(this) {
			@Override
			public int getMetadata(int metadata) {
				return metadata;
			}

			@Override
			public String getUnlocalizedName(ItemStack stack) {
				return super.getUnlocalizedName() + "." + OreIngotStorage.getFromMetadata(stack.getMetadata()).getName();
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		return ib;
	}

}
