package com.joshmanisdabomb.aimagg.items.equipment;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment.AimaggItemCustomTool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

class AimaggItemCustomPickaxe extends AimaggItemCustomTool {

	public AimaggItemCustomPickaxe(String internalName, ToolMaterial tm) {
		super(internalName, tm);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		if (state.getBlock() instanceof AimaggBlockBasic) {
			return ((AimaggBlockBasic)state.getBlock()).isCustomToolEffective(this.toolMaterial, stack, state) ? this.toolMaterial.getEfficiencyOnProperMaterial() : super.getStrVsBlock(stack, state);
		}
		return super.getStrVsBlock(stack, state);
	}

	@Override
	public double getAttackDamage() {
		return this.toolMaterial.getDamageVsEntity() + 1.0D;
	}

	@Override
	public double getAttackSpeed() {
		return -2.8D;
	}

	@Override
	public int getHitDurabilityLoss() {
		return 2;
	}

	@Override
	public int getBreakDurabilityLoss() {
		return 1;
	}
	
	@Override
	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Constants.MOD_ID + ":equipment/" + this.getInternalName(), "inventory"));
	}
	
}