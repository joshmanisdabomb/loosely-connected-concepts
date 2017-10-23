package com.joshmanisdabomb.aimagg.te.model;

import java.util.HashMap;
import java.util.Map.Entry;

import com.joshmanisdabomb.aimagg.te.AimaggTEBouncePad;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;

public class AimaggTEBouncePadModel extends ModelBase {

	public final HashMap<String, ModelRenderer> parts = new HashMap<String, ModelRenderer>();
	public final HashMap<Integer, ModelRenderer> pistons = new HashMap<Integer, ModelRenderer>();

	public final ModelRenderer base1;
	public final ModelRenderer base2;
	public final ModelRenderer base3;
	public final ModelRenderer base4;
	public final ModelRenderer basebottom;
	public final ModelRenderer pad;
	public final ModelRenderer piston12;
	public final ModelRenderer piston11;
	public final ModelRenderer piston10;
	public final ModelRenderer piston9;
	public final ModelRenderer piston8;
	public final ModelRenderer piston7;
	public final ModelRenderer piston6;
	public final ModelRenderer piston5;
	public final ModelRenderer piston4;
	public final ModelRenderer piston3;
	public final ModelRenderer piston2;

	public AimaggTEBouncePadModel() {
		
		textureWidth = 64;
		textureHeight = 64;

		base1 = new ModelRenderer(this, "base1").setTextureOffset(0, 0);
		base1.addBox(-8.0F, -8.0F, -8.0F, 15, 5, 1);
		base1.rotateAngleY = 0F;
		parts.put(base1.boxName, base1);

		base2 = new ModelRenderer(this, "base2").setTextureOffset(0, 0);
		base2.addBox(-8.0F, -8.0F, -8.0F, 15, 5, 1);
		base2.rotateAngleY = (float)(Math.PI / 2);
		parts.put(base2.boxName, base2);

		base3 = new ModelRenderer(this, "base3").setTextureOffset(0, 0);
		base3.addBox(-8.0F, -8.0F, -8.0F, 15, 5, 1);
		base3.rotateAngleY = (float)(Math.PI);
		parts.put(base3.boxName, base3);

		base4 = new ModelRenderer(this, "base4").setTextureOffset(0, 0);
		base4.addBox(-8.0F, -8.0F, -8.0F, 15, 5, 1);
		base4.rotateAngleY = (float)(Math.PI / -2);
		parts.put(base4.boxName, base4);

		basebottom = new ModelRenderer(this, "basebottom").setTextureOffset(0, 22);
		basebottom.addBox(-7.0F, -8.0F, -7.0F, 14, 2, 14);
		parts.put(basebottom.boxName, basebottom);

		pad = new ModelRenderer(this, "pad").setTextureOffset(0, 6);
		pad.addBox(-7.0F, 6.0F, -7.0F, 14, 2, 14);
		parts.put(pad.boxName, pad);

		piston12 = new ModelRenderer(this, "piston12").setTextureOffset(0, 38);
		piston12.addBox(-1.5F, -6.0F, -1.5F, 3, 12, 3);
		pistons.put(12, piston12);

		piston11 = new ModelRenderer(this, "piston11").setTextureOffset(0, 39);
		piston11.addBox(-1.5F, -5.0F, -1.5F, 3, 11, 3);
		pistons.put(11, piston11);

		piston10 = new ModelRenderer(this, "piston10").setTextureOffset(0, 40);
		piston10.addBox(-1.5F, -4.0F, -1.5F, 3, 10, 3);
		pistons.put(10, piston10);

		piston9 = new ModelRenderer(this, "piston9").setTextureOffset(0, 41);
		piston9.addBox(-1.5F, -3.0F, -1.5F, 3, 9, 3);
		pistons.put(9, piston9);

		piston8 = new ModelRenderer(this, "piston8").setTextureOffset(0, 42);
		piston8.addBox(-1.5F, -2.0F, -1.5F, 3, 8, 3);
		pistons.put(8, piston8);

		piston7 = new ModelRenderer(this, "piston7").setTextureOffset(0, 43);
		piston7.addBox(-1.5F, -1.0F, -1.5F, 3, 7, 3);
		pistons.put(7, piston7);

		piston6 = new ModelRenderer(this, "piston6").setTextureOffset(0, 44);
		piston6.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3);
		pistons.put(6, piston6);

		piston5 = new ModelRenderer(this, "piston5").setTextureOffset(0, 45);
		piston5.addBox(-1.5F, 1.0F, -1.5F, 3, 5, 3);
		pistons.put(5, piston5);

		piston4 = new ModelRenderer(this, "piston4").setTextureOffset(0, 46);
		piston4.addBox(-1.5F, 2.0F, -1.5F, 3, 4, 3);
		pistons.put(4, piston4);

		piston3 = new ModelRenderer(this, "piston3").setTextureOffset(0, 47);
		piston3.addBox(-1.5F, 3.0F, -1.5F, 3, 3, 3);
		pistons.put(3, piston3);

		piston2 = new ModelRenderer(this, "piston2").setTextureOffset(0, 48);
		piston2.addBox(-1.5F, 4.0F, -1.5F, 3, 2, 3);
		pistons.put(2, piston2);

	}

	public void render(TileEntity te, float scale) {
		float offsetAmount = 12-((AimaggTEBouncePad)te).getExtension();
		int pistonLength = MathHelper.ceil(((AimaggTEBouncePad)te).getExtension());
		
		parts.get("pad").offsetY -= offsetAmount;
		pistons.get(pistonLength).offsetY -= offsetAmount;
		
		for (Entry<String, ModelRenderer> e : parts.entrySet()) {
			e.getValue().render(scale);
		}
		pistons.get(pistonLength).render(scale);
		
		parts.get("pad").offsetY += offsetAmount;
		pistons.get(pistonLength).offsetY += offsetAmount;
	}
	
}