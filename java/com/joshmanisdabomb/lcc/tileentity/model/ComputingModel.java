package com.joshmanisdabomb.lcc.tileentity.model;

import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.state.properties.SlabType;

public class ComputingModel extends Model {

    private final RendererModel[] module_top = new RendererModel[4];
    private final RendererModel[] module_bottom = new RendererModel[4];

    public ComputingModel() {
        textureWidth = 64;
        textureHeight = 256;

        for (int i = 0; i < 4; i++) {
            module_top[i] = new RendererModel(this);
            module_top[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            module_top[i].rotateAngleX = (float)Math.PI;
            module_top[i].rotateAngleY = module_top[i].rotateAngleZ = 0.0F;
            module_top[i].setTextureOffset(0, i * 24);
            module_top[i].addBox(-8.0F, 0.0F, -8.0F, 16, 8, 16);

            module_bottom[i] = new RendererModel(this);
            module_bottom[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            module_bottom[i].rotateAngleX = (float)Math.PI;
            module_bottom[i].rotateAngleY = module_bottom[i].rotateAngleZ = 0.0F;
            module_bottom[i].setTextureOffset(0, 96 + (i * 24));
            module_bottom[i].addBox(-8.0F, 8.0F, -8.0F, 16, 8, 16);
        }
    }

    public void renderModule(ComputingTileEntity te, float scale, SlabType module) {
        RendererModel[] ms = module == SlabType.TOP ? module_top : module_bottom;
        RendererModel m = ms[(te.isModuleConnectedAbove(module) ? 1 : 0) + (te.isModuleConnectedBelow(module) ? 2 : 0)];

        ComputingTileEntity.ComputingModule cm = te.getModule(module);

        int index = cm.direction.getHorizontalIndex();
        if (index == 3) index = 1;
        else if (index == 1) index = 3;
        m.rotateAngleY = (float)Math.PI * (index / 2F);

        m.render(scale);
    }

}