package com.joshmanisdabomb.aimagg.te.model;

import java.util.HashMap;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

public class AimaggTELaunchPadModel extends ModelBase {
	public HashMap<String, ModelRenderer> parts = new HashMap<String, ModelRenderer>();

	ModelRenderer pad;
	ModelRenderer raise;
	ModelRenderer base;
	ModelRenderer barbase1;
	ModelRenderer barbase2;
	ModelRenderer barbase3;
	ModelRenderer barbase4;
	ModelRenderer bar1;
	ModelRenderer bar2;
	ModelRenderer bar3;
	ModelRenderer bar4;

	public AimaggTELaunchPadModel() {
		textureWidth = 64;
		textureHeight = 128;

		pad = new ModelRenderer(this, 0, 0);
		pad.mirror = false;
		pad.addBox(-6.0F, 3.0F, -6.0F, 12, 1, 12);
		pad.setRotationPoint(0.0F, -32.0F, 0.0F);
		pad.setTextureSize(64, 128);
		parts.put(pad.boxName, pad);

		raise = new ModelRenderer(this, 0, 13);
		raise.mirror = false;
		raise.addBox(-7.0F, 2.0F, -7.0F, 14, 1, 14);
		raise.setRotationPoint(0.0F, -32.0F, 0.0F);
		raise.setTextureSize(64, 128);
		parts.put(raise.boxName, raise);

		base = new ModelRenderer(this, 0, 28);
		base.mirror = false;
		base.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16);
		base.setRotationPoint(0.0F, -32.0F, 0.0F);
		base.setTextureSize(64, 128);
		parts.put(base.boxName, base);

		barbase1 = new ModelRenderer(this, 0, 46);
		barbase1.mirror = false;
		barbase1.addBox(5.0F, 3.0F, 5.0F, 2, 2, 2);
		barbase1.setRotationPoint(0.0F, -32.0F, 0.0F);
		barbase1.setTextureSize(64, 128);
		parts.put(barbase1.boxName, barbase1);

		barbase2 = new ModelRenderer(this, 0, 46);
		barbase2.mirror = false;
		barbase2.addBox(-7.0F, 3.0F, 5.0F, 2, 2, 2);
		barbase2.setRotationPoint(0.0F, -32.0F, 0.0F);
		barbase2.setTextureSize(64, 128);
		parts.put(barbase2.boxName, barbase2);

		barbase3 = new ModelRenderer(this, 0, 46);
		barbase3.mirror = false;
		barbase3.addBox(-7.0F, 3.0F, -7.0F, 2, 2, 2);
		barbase3.setRotationPoint(0.0F, -32.0F, 0.0F);
		barbase3.setTextureSize(64, 128);
		parts.put(barbase3.boxName, barbase3);

		barbase4 = new ModelRenderer(this, 0, 46);
		barbase4.mirror = false;
		barbase4.addBox(5.0F, 3.0F, -7.0F, 2, 2, 2);
		barbase4.setRotationPoint(0.0F, -32.0F, 0.0F);
		barbase4.setTextureSize(64, 128);
		parts.put(barbase4.boxName, barbase4);

		bar1 = new ModelRenderer(this, 0, 50);
		bar1.mirror = false;
		bar1.addBox(6.0F, 5.0F, 6.0F, 1, 27, 1);
		bar1.setRotationPoint(0.0F, -32.0F, 0.0F);
		bar1.setTextureSize(64, 128);
		parts.put(bar1.boxName, bar1);

		bar2 = new ModelRenderer(this, 0, 50);
		bar2.mirror = false;
		bar2.addBox(6.0F, 5.0F, -7.0F, 1, 27, 1);
		bar2.setRotationPoint(0.0F, -32.0F, 0.0F);
		bar2.setTextureSize(64, 128);
		parts.put(bar2.boxName, bar2);

		bar3 = new ModelRenderer(this, 0, 50);
		bar3.mirror = false;
		bar3.addBox(-7.0F, 5.0F, -7.0F, 1, 27, 1);
		bar3.setRotationPoint(0.0F, -32.0F, 0.0F);
		bar3.setTextureSize(64, 128);
		parts.put(bar3.boxName, bar3);

		bar4 = new ModelRenderer(this, 0, 50);
		bar4.mirror = false;
		bar4.addBox(-7.0F, 5.0F, 6.0F, 1, 27, 1);
		bar4.setRotationPoint(0.0F, -32.0F, 0.0F);
		bar4.setTextureSize(64, 128);
		parts.put(bar4.boxName, bar4);

	}

	public void render(TileEntity te, float scale) {
		pad.render(scale);
		raise.render(scale);
		base.render(scale);
		barbase2.render(scale);
		barbase3.render(scale);
		barbase4.render(scale);
		bar1.render(scale);
		bar2.render(scale);
		bar3.render(scale);
		bar4.render(scale);
		barbase1.render(scale);
	}
	
}