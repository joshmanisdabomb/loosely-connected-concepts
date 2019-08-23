package com.joshmanisdabomb.lcc.tileentity.model;

import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

public class BouncePadModel extends Model {

    private final RendererModel[] bouncers = new RendererModel[9];

    public BouncePadModel() {
        textureWidth = 64;
        textureHeight = 32;

        for (int i = 0; i < bouncers.length; i++) {
            bouncers[i] = new RendererModel(this);
            bouncers[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            bouncers[i].rotateAngleX = (float)Math.PI;
            bouncers[i].addBox(-7.0F, i, -7.0F, 14, 10 - i, 14);
        }
    }

    public void render(BouncePadTileEntity te, float scale) {
        int bouncer = te.getExtensionModel();
        float offset = bouncer == 0 ? 0 : te.getExtensionOffset();

        bouncers[bouncer].offsetY += offset;
        bouncers[bouncer].render(scale);
        bouncers[bouncer].offsetY -= offset;
    }

}