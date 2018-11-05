package com.joshmanisdabomb.aimagg.items.equipment;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.util.AimaggHarvestLevel.Specialization;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

class AimaggItemCustomShears extends AimaggItemBasic {

	final ToolMaterial toolMaterial;
	final Specialization s;
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this.s != null) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				String s = "tooltip.aimagg:tool.specialization." + this.s.name().toLowerCase();
		    	tooltip.add(
		    				TextFormatting.GRAY + 
		    				I18n.format("tooltip.aimagg:tool.specialization.1", new Object[] {I18n.format(s, new Object[] {})})
		    			   );
		    	tooltip.add(
		    				TextFormatting.GRAY + 
		    				I18n.format("tooltip.aimagg:tool.specialization.2", new Object[] {I18n.format(s, new Object[] {})})
		    			   );
			} else {
		    	tooltip.add(
		    				TextFormatting.DARK_GRAY + 
		    				I18n.format("tooltip.aimagg:press_shift_for_more", new Object[0])
		    			   );
			}
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public AimaggItemCustomShears(String internalName, ToolMaterial tm, Specialization s) {
		super(internalName);
		this.toolMaterial = tm;
		this.s = s;
        this.setMaxStackSize(1);
        this.setMaxDamage(MathHelper.floor(tm.getMaxUses() * AimaggEquipment.DURABILITY_SHEARS_RATIO));
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		if (state.getBlock() instanceof AimaggBlockBasic) {
			return ((AimaggBlockBasic)state.getBlock()).isCustomToolEffective(this.toolMaterial, this.s, stack, state) ? this.toolMaterial.getEfficiencyOnProperMaterial() : super.getStrVsBlock(stack, state);
		}
		return super.getStrVsBlock(stack, state);
	}
	
	@Override
	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Constants.MOD_ID + ":equipment/" + this.getInternalName(), "inventory"));
	}

}
