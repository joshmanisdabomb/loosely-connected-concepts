package com.joshmanisdabomb.aimagg.blocks;

import java.util.Comparator;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.AimaggItemColored;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggBlockBasic extends Block {

	private final String internalName;
	
	private final int sortValue;

	private final ItemBlock itemBlock;
	
	public AimaggBlockBasic(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(material, mcolor);
		this.setUnlocalizedName(this.internalName = internalName);
		this.setRegistryName(this.internalName);
		this.setCreativeTab(AimlessAgglomeration.tab);
		this.sortValue = sortVal;
		
		AimaggBlocks.registry.add(this);
		AimaggBlocks.ibRegistry.add(this.itemBlock = this.createItemBlock());
		
		this.initialise();
	}

	public String getInternalName() {
		return internalName;
	}

	public int getSortValue(ItemStack is) {
		return sortValue;
	}

	public ItemBlock createItemBlock() {
		ItemBlock ib = new ItemBlock(this);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}

	public ItemBlock getItemBlock() {
		return itemBlock;
	}
	
	public void initialise() {
	}

	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName(), "inventory"));
	}

}
