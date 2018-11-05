package com.joshmanisdabomb.aimagg.items.equipment;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment.AimaggItemCustomTool;
import com.joshmanisdabomb.aimagg.util.AimaggHarvestLevel.Specialization;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

class AimaggItemCustomSword extends AimaggItemCustomTool {

	public AimaggItemCustomSword(String internalName, ToolMaterial tm, Specialization s) {
		super(internalName, tm, s);
	}

	@Override
	public double getAttackDamage() {
		return this.toolMaterial.getDamageVsEntity() + 3.0D;
	}

	@Override
	public double getAttackSpeed() {
		return -2.4000000953674316D;
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