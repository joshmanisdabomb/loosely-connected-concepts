package com.joshmanisdabomb.lcc.tileentity.model;

import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.state.properties.SlabType;

public class ComputingModel extends Model {

    private final ModelRenderer[] module_top = new ModelRenderer[4];
    private final ModelRenderer[] module_bottom = new ModelRenderer[4];

    private final ModelRenderer[] power_light_top = new ModelRenderer[4];
    private final ModelRenderer[] power_light_bottom = new ModelRenderer[4];
    private final ModelRenderer[] read_light_top = new ModelRenderer[4];
    private final ModelRenderer[] read_light_bottom = new ModelRenderer[4];

    public ComputingModel() {
        super(RenderType::getEntitySolid);

        textureWidth = 64;
        textureHeight = 256;

        for (int i = 0; i < 4; i++) {
            module_top[i] = new ModelRenderer(this);
            module_top[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            module_top[i].rotateAngleX = (float)Math.PI;
            module_top[i].rotateAngleY = module_top[i].rotateAngleZ = 0.0F;
            module_top[i].setTextureOffset(0, i * 24);
            module_top[i].addBox(-8.0F, 0.0F, -8.0F, 16, 8, 16);

            module_bottom[i] = new ModelRenderer(this);
            module_bottom[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            module_bottom[i].rotateAngleX = (float)Math.PI;
            module_bottom[i].rotateAngleY = module_bottom[i].rotateAngleZ = 0.0F;
            module_bottom[i].setTextureOffset(0, 96 + (i * 24));
            module_bottom[i].addBox(-8.0F, 8.0F, -8.0F, 16, 8, 16);

            power_light_top[i] = new ModelRenderer(this);
            power_light_top[i].setTextureSize(128, 256);
            power_light_top[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            power_light_top[i].rotateAngleX = (float)Math.PI;
            power_light_top[i].rotateAngleY = module_top[i].rotateAngleZ = 0.0F;
            power_light_top[i].setTextureOffset(0, i * 24);
            power_light_top[i].addBox(-8.0F, 0.0F, -8.0F, 16, 8, 16);

            power_light_bottom[i] = new ModelRenderer(this);
            power_light_bottom[i].setTextureSize(128, 256);
            power_light_bottom[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            power_light_bottom[i].rotateAngleX = (float)Math.PI;
            power_light_bottom[i].rotateAngleY = module_bottom[i].rotateAngleZ = 0.0F;
            power_light_bottom[i].setTextureOffset(0, 96 + (i * 24));
            power_light_bottom[i].addBox(-8.0F, 8.0F, -8.0F, 16, 8, 16);

            read_light_top[i] = new ModelRenderer(this);
            read_light_top[i].setTextureSize(128, 256);
            read_light_top[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            read_light_top[i].rotateAngleX = (float)Math.PI;
            read_light_top[i].rotateAngleY = module_top[i].rotateAngleZ = 0.0F;
            read_light_top[i].setTextureOffset(64, i * 24);
            read_light_top[i].addBox(-8.0F, 0.0F, -8.0F, 16, 8, 16);

            read_light_bottom[i] = new ModelRenderer(this);
            read_light_bottom[i].setTextureSize(128, 256);
            read_light_bottom[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            read_light_bottom[i].rotateAngleX = (float)Math.PI;
            read_light_bottom[i].rotateAngleY = module_bottom[i].rotateAngleZ = 0.0F;
            read_light_bottom[i].setTextureOffset(64, 96 + (i * 24));
            read_light_bottom[i].addBox(-8.0F, 8.0F, -8.0F, 16, 8, 16);
        }
    }

    private void render(ComputingTileEntity te, float scale, SlabType location, ModelRenderer[] ms) {
        ModelRenderer m = ms[(te.isModuleConnectedAbove(location) ? 1 : 0) + (te.isModuleConnectedBelow(location) ? 2 : 0)];

        ComputingModule module = te.getModule(location);

        int index = module.direction.getHorizontalIndex();
        if (index == 3) index = 1;
        else if (index == 1) index = 3;
        m.rotateAngleY = (float)Math.PI * (index / 2F);

        //m.render(scale);
    }

    public void renderModule(ComputingTileEntity te, float scale, SlabType location) {
        this.render(te, scale, location, location == SlabType.TOP ? module_top : module_bottom);
    }

    public void renderPowerLight(ComputingTileEntity te, float scale, SlabType location, boolean read) {
        this.render(te, scale, location, location == SlabType.TOP ? (read ? read_light_top : power_light_top) : (read ? read_light_bottom : power_light_bottom));
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {

    }

}