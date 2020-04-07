package com.joshmanisdabomb.lcc.item.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.item.model.GauntletModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GauntletRenderer extends ItemStackTileEntityRenderer {

    private final GauntletModel model = new GauntletModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/gauntlet.png");

    @Override
    public void render(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        matrix.push();
        matrix.scale(1, 1, 1);
        matrix.rotate(Vector3f.XP.rotationDegrees(180.0F));
        model.render(matrix, buffer.getBuffer(model.getRenderType(TEXTURE)), light, overlay, 1, 1, 1, 1);
        matrix.pop();
    }

    public void renderHand(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        model.render(matrix, buffer.getBuffer(model.getRenderType(TEXTURE)), light, overlay, 1, 1, 1, 1);
    }

}
