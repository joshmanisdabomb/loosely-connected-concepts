package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;

public class ComputingRenderer extends TileEntityRenderer<ComputingTileEntity> {

    private final ModelRenderer power;
    private final ModelRenderer read;

    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/computer_lighting.png");

    public ComputingRenderer(TileEntityRendererDispatcher terd) {
        super(terd);

        power = new ModelRenderer(8, 8, 0, 0);
        power.addBox(2.0F, 2.0F, -1.0F, 2, 3, 1);
        read = new ModelRenderer(8, 8, 0, 4);
        read.addBox(2.0F, 2.0F, -1.0F, 2, 3, 1);
    }

    @Override
    public void render(ComputingTileEntity te, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        matrix.push();
        for (ComputingModule m : te.getInstalledModules()) {
            matrix.push();
            if (m.location == SlabType.TOP) matrix.translate(0, 9/16F, 0);
            if (m.isPowered()) {
                matrix.translate(0.5, 0.5, 0.5);
                matrix.rotate(Vector3f.YN.rotationDegrees(((m.direction.getHorizontalIndex() + 2) % 4) * 90));
                matrix.translate(-0.5, -0.5, -0.5005);
                (m.isReading() ? read : power).render(matrix, buffer.getBuffer(RenderType.getEntityAlpha(TEXTURE, 0.01F)), light, overlay, 1, 1, 1, 1);
            }
            matrix.pop();
        }
        matrix.pop();
    }

}
