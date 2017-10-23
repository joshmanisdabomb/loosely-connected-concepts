package com.joshmanisdabomb.aimagg.util;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.entity.model.AimaggEntityMissileFatModel;
import com.joshmanisdabomb.aimagg.entity.model.AimaggEntityMissileSkinnyModel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public enum MissileType {
	EXPLOSIVE(ModelType.SKINNY, StrengthUnits.TNT, 1.3F, 0.01F, 1.04F),
	FIRE(ModelType.SKINNY, StrengthUnits.TNT, 1.3F, 0.01F, 1.04F),
	NUCLEAR(ModelType.FAT, StrengthUnits.KILOTONS, 0.8F, 0.004F, 1.02F);

	private final StrengthUnits strUnits;
	private final ModelType modelType;
	
	private final ModelResourceLocation mrl;
	private final ResourceLocation entityTexture;
	
	private float topSpeed;
	private float initialSpeed;
	private float speedModifier;

	MissileType(ModelType mot, StrengthUnits su, float topSpeed, float initialSpeed, float speedModifier) {
		this.entityTexture = new ResourceLocation(Constants.MOD_ID + ":textures/entity/missile/" + ((this.modelType = mot).getEntityTextureSubfolder()) + "/" + this.name().toLowerCase() + ".png");
		this.strUnits = su;
		
		this.topSpeed = topSpeed;
		this.initialSpeed = initialSpeed;
		this.speedModifier = speedModifier;
		
		this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":missile/" + this.name().toLowerCase(), "inventory");
	}

	public static MissileType getFromMetadata(int metadata) {
		return MissileType.values()[metadata];
	}

	public int getMetadata() {
		return this.ordinal();
	}

	public StrengthUnits getStrengthUnits() {
		return this.strUnits;
	}

	public ModelType getModelType() {
		return this.modelType;
	}

	public ResourceLocation getEntityTexture() {
		return this.entityTexture;
	}
	
	public float getTopSpeed() {
		return topSpeed;
	}

	public float getInitialSpeed() {
		return initialSpeed;
	}

	public float getSpeedModifier() {
		return speedModifier;
	}
	
	public ModelResourceLocation getItemModel() {
		return mrl;
	}
	
	public static enum ModelType {
		
		SKINNY(AimaggEntityMissileSkinnyModel.class),
		FAT(AimaggEntityMissileFatModel.class);

		private final Class<? extends ModelBase> modelClass;
		private ModelBase launchPadModel;

		ModelType(Class<? extends ModelBase> launchPadClass) {
			this.modelClass = launchPadClass;
			try {
				this.launchPadModel = launchPadClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		public String getEntityTextureSubfolder() {
			return this.name();
		}
		
		public ModelBase getLaunchPadModel() {
			return this.launchPadModel;
		}
		
		public Class<? extends ModelBase> getModelClass() {
			return this.modelClass;
		}

		public static ModelBase[] getEntityModels() {
			ModelBase[] mb = new ModelBase[ModelType.values().length];
			for (int i = 0; i < mb.length; i++) {
				try {
					mb[i] = ModelType.values()[i].getModelClass().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return mb;
		}
		
	}
	
	public static enum StrengthUnits {
		
		TNT(TextFormatting.RED, 1.0F, false),
		KILOTONS(TextFormatting.GREEN, 0.05F, true);
		
		private final TextFormatting color;
		
		private final float multiply;
		private final boolean useDecimals;

		StrengthUnits(TextFormatting color, float multiply, boolean useDecimals) {
			this.color = color;
			this.multiply = multiply;
			this.useDecimals = useDecimals;
		}

		public String getTooltip(int strength) {
			return TextFormatting.GRAY + I18n.format("tooltip.aimagg:missile.strength", new Object[] {this.color, this.useDecimals ? (float)(strength*this.multiply) : (int)(strength*this.multiply), I18n.format("tooltip.aimagg:missile.strength." + this.name().toLowerCase(), new Object[0])});
		}
		
	}
	
}