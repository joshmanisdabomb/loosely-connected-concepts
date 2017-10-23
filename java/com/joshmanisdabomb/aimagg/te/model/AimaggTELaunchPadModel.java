package com.joshmanisdabomb.aimagg.te.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

public class AimaggTELaunchPadModel extends ModelBase {
	
	public final HashMap<String, ModelRenderer> parts = new HashMap<String, ModelRenderer>();

	public final ModelRenderer pad;
	public final ModelRenderer raise;
	public final ModelRenderer base;
	public final ModelRenderer barbase1;
	public final ModelRenderer barbase2;
	public final ModelRenderer barbase3;
	public final ModelRenderer barbase4;
	public final ModelRenderer bar1;
	public final ModelRenderer bar2;
	public final ModelRenderer bar3;
	public final ModelRenderer bar4;

	public AimaggTELaunchPadModel() {
		
		textureWidth = 64;
		textureHeight = 128;

		pad = new ModelRenderer(this, "pad").setTextureOffset(0, 0);
		pad.addBox(-6.0F, 3.0F, -6.0F, 12, 1, 12);
		parts.put(pad.boxName, pad);

		raise = new ModelRenderer(this, "raise").setTextureOffset(0, 13);
		raise.addBox(-7.0F, 2.0F, -7.0F, 14, 1, 14);
		parts.put(raise.boxName, raise);

		base = new ModelRenderer(this, "base").setTextureOffset(0, 28);
		base.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16);
		parts.put(base.boxName, base);

		barbase1 = new ModelRenderer(this, "barbase1").setTextureOffset(0, 46);
		barbase1.addBox(5.0F, 3.0F, 5.0F, 2, 2, 2);
		parts.put(barbase1.boxName, barbase1);

		barbase2 = new ModelRenderer(this, "barbase2").setTextureOffset(0, 46);
		barbase2.addBox(-7.0F, 3.0F, 5.0F, 2, 2, 2);
		parts.put(barbase2.boxName, barbase2);

		barbase3 = new ModelRenderer(this, "barbase3").setTextureOffset(0, 46);
		barbase3.addBox(-7.0F, 3.0F, -7.0F, 2, 2, 2);
		parts.put(barbase3.boxName, barbase3);

		barbase4 = new ModelRenderer(this, "barbase4").setTextureOffset(0, 46);
		barbase4.addBox(5.0F, 3.0F, -7.0F, 2, 2, 2);
		parts.put(barbase4.boxName, barbase4);

		bar1 = new ModelRenderer(this, "bar1").setTextureOffset(0, 50);
		bar1.addBox(6.0F, 5.0F, 6.0F, 1, 27, 1);
		parts.put(bar1.boxName, bar1);

		bar2 = new ModelRenderer(this, "bar2").setTextureOffset(0, 50);
		bar2.addBox(6.0F, 5.0F, -7.0F, 1, 27, 1);
		parts.put(bar2.boxName, bar2);

		bar3 = new ModelRenderer(this, "bar3").setTextureOffset(0, 50);
		bar3.addBox(-7.0F, 5.0F, -7.0F, 1, 27, 1);
		parts.put(bar3.boxName, bar3);

		bar4 = new ModelRenderer(this, "bar4").setTextureOffset(0, 50);
		bar4.addBox(-7.0F, 5.0F, 6.0F, 1, 27, 1);
		parts.put(bar4.boxName, bar4);

	}

	public void render(TileEntity te, float scale) {
		for (Entry<String, ModelRenderer> e : parts.entrySet()) {
			e.getValue().render(scale);
		}
	}
	
}