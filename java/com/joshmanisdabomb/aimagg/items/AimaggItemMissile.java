package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.entity.missile.AimaggEntityMissileExplosive;
import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityMissileSmallRender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class AimaggItemMissile extends AimaggItemBasic {

	public static enum MissileType {
		EXPLOSIVE(0, true, AimaggEntityMissileExplosive.texture),
		FIRE(1, true, AimaggEntityMissileExplosive.texture),
		NUCLEAR(2, false, AimaggEntityMissileExplosive.texture);
		
		private int metadata;
		public boolean showStrength;
		private final ResourceLocation texture;

		MissileType(int metadata, boolean showStrength, ResourceLocation texture) {
			this.metadata = metadata;
			this.showStrength = showStrength;
			this.texture = texture;
		}
		
		public static MissileType getFromMetadata(int metadata) {
			for (MissileType m : values()) {
				if (metadata == m.metadata) {
					return m;
				}
			}
			return null;
		}

		public int getMetadata() {
			return this.metadata;
		}

		public boolean showStrength() {
			return this.showStrength;
		}

		public ResourceLocation getEntityTexture() {
			return this.texture;
		}
	}
	
	public AimaggItemMissile(String internalName, int sortVal) {
		super(internalName, sortVal);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + MissileType.getFromMetadata(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		
        if (MissileType.getFromMetadata(stack.getMetadata()).showStrength() && stack.hasTagCompound()) {
        	tooltip.add(
        				TextFormatting.WHITE + 
        				new TextComponentTranslation("tooltip.missile.strength", new Object[0]).getUnformattedText() + 
        			    TextFormatting.RED +
        			    String.format(stack.getTagCompound().getInteger("strength") + new TextComponentTranslation("tooltip.missile.strength.suffix", new Object[0]).getUnformattedText())
        			   );
        }
	}
	
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+is.getMetadata();
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (MissileType m : MissileType.values()) {
			ItemStack is = new ItemStack(itemIn, 1, m.getMetadata());
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setInteger("strength", 1);
            subItems.add(is);
        }
	}
	
	@Override
	public boolean usesCustomModels() {
		return true;
	}

}
