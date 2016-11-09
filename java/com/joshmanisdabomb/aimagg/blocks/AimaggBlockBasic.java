package com.joshmanisdabomb.aimagg.blocks;

import java.util.Comparator;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AimaggBlockBasic extends Block {

	private final String internalName;
	
	private final int sortValue;
	
	public AimaggBlockBasic(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(material, mcolor);
		this.setUnlocalizedName(this.internalName = internalName);
		this.setRegistryName(this.internalName);
		this.setCreativeTab(AimlessAgglomeration.tab);
		this.sortValue = sortVal;
		
		AimaggBlocks.registry.add(this);
	}

	public int getSortValue(ItemStack is) {
		return sortValue;
	}

}
