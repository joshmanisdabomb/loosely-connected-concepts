package com.joshmanisdabomb.aimagg.entity.model;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AimaggEntityMissileFatModel extends ModelBase {
	private ArrayList<ModelRenderer> parts = new ArrayList<ModelRenderer>();

	ModelRenderer flipper1;
	ModelRenderer flipper2;
	ModelRenderer flipper3;
	ModelRenderer flipper4;
	ModelRenderer body;
	ModelRenderer head1;
	ModelRenderer head2;
	ModelRenderer head3;
	ModelRenderer head4;

	public AimaggEntityMissileFatModel() {
		textureWidth = 32;
		textureHeight = 64;
		
		float pointX = 0.0F;
		float pointY = 16.0F;
		float pointZ = 0.0F;

		flipper1 = new ModelRenderer(this, 0, 44);
		flipper1.mirror = false;
		flipper1.addBox(-1.0F, 0.001F-pointY, -6.0F, 2, 8, 12);
		flipper1.setRotationPoint(pointX, pointY, pointZ);
		flipper1.setTextureSize(32, 64);
		parts.add(flipper1);

		flipper2 = new ModelRenderer(this, 6, 44);
		flipper2.mirror = true;
		flipper2.addBox(-0.5F, 0.002F-pointY, -7.0F, 1, 6, 14);
		flipper2.setRotationPoint(pointX, pointY, pointZ);
		flipper2.setTextureSize(32, 64);
		parts.add(flipper2);

		flipper3 = new ModelRenderer(this, 0, 44);
		flipper3.mirror = false;
		flipper3.addBox(-6.0F, 0.001F-pointY, -1.0F, 12, 8, 2);
		flipper3.setRotationPoint(pointX, pointY, pointZ);
		flipper3.setTextureSize(32, 64);
		parts.add(flipper3);

		flipper4 = new ModelRenderer(this, 6, 44);
		flipper4.mirror = false;
		flipper4.addBox(-7.0F, 0.002F-pointY, -0.5F, 14, 6, 1);
		flipper4.setRotationPoint(pointX, pointY, pointZ);
		flipper4.setTextureSize(32, 64);
		parts.add(flipper4);

		body = new ModelRenderer(this, 0, 0);
		body.mirror = false;
		body.addBox(-5.0F, 0.0F-pointY, -5.0F, 10, 34, 10);
		body.setRotationPoint(pointX, pointY, pointZ);
		body.setTextureSize(32, 64);
		parts.add(body);

		head1 = new ModelRenderer(this, 0, 44);
		head1.mirror = false;
		head1.addBox(-4.0F, 37.0F-pointY, -4.0F, 8, 1, 8);
		head1.setRotationPoint(pointX, pointY, pointZ);
		head1.setTextureSize(32, 64);
		parts.add(head1);

		head3 = new ModelRenderer(this, 0, 44);
		head3.mirror = false;
		head3.addBox(-5.5F, 30.0F-pointY, -5.5F, 11, 5, 11);
		head3.setRotationPoint(pointX, pointY, pointZ);
		head3.setTextureSize(32, 64);
		parts.add(head3);

		head4 = new ModelRenderer(this, 0, 44);
		head4.mirror = false;
		head4.addBox(-2.5F, 38.0F-pointY, -2.5F, 5, 1, 5);
		head4.setRotationPoint(pointX, pointY, pointZ);
		head4.setTextureSize(32, 64);
		parts.add(head4);

		head2 = new ModelRenderer(this, 0, 44);
		head2.mirror = false;
		head2.addBox(-5.0F, 35.0F-pointY, -5.0F, 10, 2, 10);
		head2.setRotationPoint(pointX, pointY, pointZ);
		head2.setTextureSize(32, 64);
		parts.add(head2);

	}

	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		//Entity is null in launch pad.
		
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        
		for (ModelRenderer mr : parts) {
			mr.render(scale);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entityIn) {		
		for (ModelRenderer mr : parts) {
			mr.rotateAngleX = headPitch * (float)(Math.PI/180);
			mr.rotateAngleY = netHeadYaw * (float)(Math.PI/180);
			mr.rotateAngleZ = 0;
		}
	}
}