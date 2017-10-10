package com.joshmanisdabomb.aimagg.items.equipment;

import com.google.common.collect.Multimap;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment.AimaggItemCustomTool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class AimaggItemCustomHoe extends AimaggItemCustomTool {

	public AimaggItemCustomHoe(String internalName, ToolMaterial tm) {
		super(internalName, tm);
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