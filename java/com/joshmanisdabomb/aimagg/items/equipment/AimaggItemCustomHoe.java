package com.joshmanisdabomb.aimagg.items.equipment;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment.AimaggItemCustomTool;
import com.joshmanisdabomb.aimagg.util.AimaggHarvestLevel.Specialization;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

class AimaggItemCustomHoe extends AimaggItemCustomTool {

	public AimaggItemCustomHoe(String internalName, ToolMaterial tm, Specialization s) {
		super(internalName, tm, s);
	}
	
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

	@Override
	public double getAttackDamage() {
		return 0.0D;
	}

	@Override
	public double getAttackSpeed() {
		return this.toolMaterial.getDamageVsEntity() - 3.0F;
	}

	@Override
	public int getHitDurabilityLoss() {
		return 1;
	}

	@Override
	public int getBreakDurabilityLoss() {
		return 2;
	}
	
	@Override
	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Constants.MOD_ID + ":equipment/" + this.getInternalName(), "inventory"));
	}
	
}