package com.joshmanisdabomb.aimagg.items.equipment;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;

class AimaggItemCustomShears extends AimaggItemBasic {

	final ToolMaterial toolMaterial;

	public AimaggItemCustomShears(String internalName, ToolMaterial tm) {
		super(internalName);
		this.toolMaterial = tm;
        this.setMaxStackSize(1);
        this.setMaxDamage(MathHelper.floor(tm.getMaxUses() * AimaggEquipment.DURABILITY_SHEARS_RATIO));
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		if (state.getBlock() instanceof AimaggBlockBasic) {
			return ((AimaggBlockBasic)state.getBlock()).isCustomToolEffective(this.toolMaterial, stack, state) ? this.toolMaterial.getEfficiencyOnProperMaterial() : super.getStrVsBlock(stack, state);
		}
		return super.getStrVsBlock(stack, state);
	}
	
	@Override
	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Constants.MOD_ID + ":equipment/" + this.getInternalName(), "inventory"));
	}

}
