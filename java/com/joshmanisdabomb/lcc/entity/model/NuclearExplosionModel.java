package com.joshmanisdabomb.lcc.entity.model;

import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class NuclearExplosionModel extends EntityModel<NuclearExplosionEntity> {
	private final RendererModel diagonal;
	private final RendererModel wave;
	private final RendererModel wave2;
	private final RendererModel wave3;
	private final RendererModel regular;

	public NuclearExplosionModel() {
		textureWidth = 4096;
		textureHeight = 4096;

		diagonal = new RendererModel(this);
		diagonal.setRotationPoint(0.0F, 24.0F, 0.0F);
		diagonal.rotateAngleY = -0.7854F;
		diagonal.cubeList.add(new ModelBox(diagonal, 2048, 0, -40.0F, -40.0F, -40.0F, 80, 80, 80, 0.0F, false));

		wave = new RendererModel(this);
		wave.setRotationPoint(0.0F, 0.0F, 0.0F);
		diagonal.addChild(wave);
		wave.cubeList.add(new ModelBox(wave, 0, 2048, -60.0F, -5.0F, -80.0F, 140, 10, 20, 0.0F, false));
		wave.cubeList.add(new ModelBox(wave, 0, 2048, -80.0F, -5.0F, 60.0F, 140, 10, 20, 0.0F, false));
		wave.cubeList.add(new ModelBox(wave, 0, 2048, 60.0F, -5.0F, -60.0F, 20, 10, 140, 0.0F, false));
		wave.cubeList.add(new ModelBox(wave, 0, 2048, -80.0F, -5.0F, -80.0F, 20, 10, 140, 0.0F, false));

		wave2 = new RendererModel(this);
		wave2.setRotationPoint(0.0F, 0.0F, 0.0F);
		diagonal.addChild(wave2);
		wave2.cubeList.add(new ModelBox(wave2, 0, 2048, -55.0F, -23.0F, -65.0F, 120, 3, 10, 0.0F, false));
		wave2.cubeList.add(new ModelBox(wave2, 0, 2048, -65.0F, -23.0F, 55.0F, 120, 3, 10, 0.0F, false));
		wave2.cubeList.add(new ModelBox(wave2, 0, 2048, 55.0F, -23.0F, -55.0F, 10, 3, 120, 0.0F, false));
		wave2.cubeList.add(new ModelBox(wave2, 0, 2048, -65.0F, -23.0F, -65.0F, 10, 3, 120, 0.0F, false));

		wave3 = new RendererModel(this);
		wave3.setRotationPoint(0.0F, 0.0F, 0.0F);
		diagonal.addChild(wave3);
		wave3.cubeList.add(new ModelBox(wave3, 0, 2048, -55.0F, 20.0F, -65.0F, 120, 3, 10, 0.0F, false));
		wave3.cubeList.add(new ModelBox(wave3, 0, 2048, -65.0F, 20.0F, 55.0F, 120, 3, 10, 0.0F, false));
		wave3.cubeList.add(new ModelBox(wave3, 0, 2048, 55.0F, 20.0F, -55.0F, 10, 3, 120, 0.0F, false));
		wave3.cubeList.add(new ModelBox(wave3, 0, 2048, -65.0F, 20.0F, -65.0F, 10, 3, 120, 0.0F, false));

		regular = new RendererModel(this);
		regular.setRotationPoint(0.0F, 24.0F, 0.0F);
		regular.cubeList.add(new ModelBox(regular, 0, 0, -100.0F, 40.0F, -100.0F, 200, 60, 200, 0.0F, false));
	}

	@Override
	public void render(NuclearExplosionEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		diagonal.render(scale);
		regular.render(scale);
	}

	@Override
	public void setRotationAngles(NuclearExplosionEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

	}

}