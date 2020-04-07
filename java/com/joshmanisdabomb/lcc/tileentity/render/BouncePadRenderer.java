package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BouncePadRenderer extends TileEntityRenderer<BouncePadTileEntity>  {

    private final ModelRenderer[] bouncers = new ModelRenderer[9];
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/bounce_pad.png");

    public BouncePadRenderer(TileEntityRendererDispatcher terd) {
        super(terd);

        for (int i = 0; i < bouncers.length; i++) {
            bouncers[i] = new ModelRenderer(64, 32, 0, 0);
            bouncers[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            bouncers[i].rotateAngleX = (float)Math.PI;
            bouncers[i].addBox(-7.0F, i, -7.0F, 14, 10 - i, 14);
        }
    }

    @Override
    public void render(BouncePadTileEntity te, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        int bouncer = this.getExtensionModel(te, partialTicks);
        float offset = bouncer == 0 ? 0 : this.getExtensionOffset(te, partialTicks);

        matrix.push();
        matrix.translate(0.5, 0.5 + offset, 0.5);
        bouncers[bouncer].render(matrix, buffer.getBuffer(RenderType.getEntitySolid(TEXTURE)), light, overlay, 1, 1, 1, 1);
        matrix.pop();
    }

    public float getExtension(BouncePadTileEntity te, float partialTicks) {
        return MathHelper.lerp(partialTicks, te.prevExtension, te.extension);
    }

    public float getExtensionOffset(BouncePadTileEntity te, float partialTicks) {
        return (this.getExtension(te, partialTicks) % 1F) * 0.0625F;
    }

    public int getExtensionModel(BouncePadTileEntity te, float partialTicks) {
        return MathHelper.clamp(9-(int)Math.ceil(this.getExtension(te, partialTicks)), 0, 8);
    }

}