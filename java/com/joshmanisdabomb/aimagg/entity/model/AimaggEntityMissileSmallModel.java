package com.joshmanisdabomb.aimagg.entity.model;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.entity.missile.AimaggEntityMissile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AimaggEntityMissileSmallModel extends ModelBase {
	private ArrayList<ModelRenderer> parts = new ArrayList<ModelRenderer>();

	ModelRenderer flipper1;
	ModelRenderer flipper2;
	ModelRenderer flipper3;
	ModelRenderer flipper4;
	ModelRenderer body;
	ModelRenderer head1;
	ModelRenderer head2;
	ModelRenderer head3;

	public AimaggEntityMissileSmallModel() {

		textureWidth = 32;
		textureHeight = 64;
		
		float pointX = 0.0F;
		float pointY = 16.0F;
		float pointZ = 0.0F;

		flipper1 = new ModelRenderer(this, 0, 38);
		flipper1.mirror = false;
		flipper1.addBox(2.0F, 0.0F-pointY, -0.5F, 2, 6, 1);
		flipper1.setRotationPoint(pointX, pointY, pointZ);
		flipper1.setTextureSize(textureWidth, textureHeight);
		parts.add(flipper1);

		flipper2 = new ModelRenderer(this, 6, 38);
		flipper2.mirror = false;
		flipper2.addBox(4.0F, 0.0F-pointY, -0.5F, 1, 4, 1);
		flipper2.setRotationPoint(pointX, pointY, pointZ);
		flipper2.setTextureSize(textureWidth, textureHeight);
		parts.add(flipper2);

		flipper3 = new ModelRenderer(this, 0, 38);
		flipper3.mirror = false;
		flipper3.addBox(-4.0F, 0.0F-pointY, -0.5F, 2, 6, 1);
		flipper3.setRotationPoint(pointX, pointY, pointZ);
		flipper3.setTextureSize(textureWidth, textureHeight);
		parts.add(flipper3);

		flipper4 = new ModelRenderer(this, 6, 38);
		flipper4.mirror = false;
		flipper4.addBox(-5.0F, 0.0F-pointY, -0.5F, 1, 4, 1);
		flipper4.setRotationPoint(pointX, pointY, pointZ);
		flipper4.setTextureSize(textureWidth, textureHeight);
		parts.add(flipper4);

		body = new ModelRenderer(this, 0, 0);
		body.mirror = false;
		body.addBox(-2.0F, 0.0F-pointY, -2.0F, 4, 34, 4);
		body.setRotationPoint(pointX, pointY, pointZ);
		body.setTextureSize(textureWidth, textureHeight);
		parts.add(body);

		head1 = new ModelRenderer(this, 0, 38);
		head1.mirror = false;
		head1.addBox(-2.5F, 34.0F-pointY, -2.5F, 5, 3, 5);
		head1.setRotationPoint(pointX, pointY, pointZ);
		head1.setTextureSize(textureWidth, textureHeight);
		parts.add(head1);

		head2 = new ModelRenderer(this, 0, 38);
		head2.mirror = false;
		head2.addBox(-2.0F, 37.0F-pointY, -2.0F, 4, 2, 4);
		head2.setRotationPoint(pointX, pointY, pointZ);
		head2.setTextureSize(textureWidth, textureHeight);
		parts.add(head2);

		head3 = new ModelRenderer(this, 0, 38);
		head3.mirror = false;
		head3.addBox(-1.5F, 39.0F-pointY, -1.5F, 3, 1, 3);
		head3.setRotationPoint(pointX, pointY, pointZ);
		head3.setTextureSize(textureWidth, textureHeight);
		parts.add(head3);

	}

	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
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