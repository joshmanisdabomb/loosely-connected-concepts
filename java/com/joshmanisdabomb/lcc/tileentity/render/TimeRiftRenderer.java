package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.TimeRiftTileEntity;
import com.joshmanisdabomb.lcc.tileentity.model.TimeRiftModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TimeRiftRenderer extends TileEntityRenderer<TimeRiftTileEntity> {

    protected final TimeRiftModel model = new TimeRiftModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/time_rift.png");

    public TimeRiftRenderer(TileEntityRendererDispatcher terd) {
        super(terd);
    }

    @Override
    public void render(TimeRiftTileEntity te, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        float size = MathHelper.lerp(partialTicks, te.lastSize, te.size);
        matrix.push();
        matrix.translate(0.5, 0.5, 0.5);
        matrix.scale(size, size, size);
        model.render(matrix, buffer.getBuffer(model.getRenderType(TEXTURE)), light, overlay, 1, 1, 1, 1);
        matrix.pop();
    }

    public static class Item extends ItemStackTileEntityRenderer {

        protected final TimeRiftModel model = new TimeRiftModel();

        @Override
        public void render(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
            matrix.push();
            matrix.translate(0.5, 0.5, 0.5);
            model.render(matrix, buffer.getBuffer(model.getRenderType(TEXTURE)), light, overlay, 1, 1, 1, 1);
            matrix.pop();
        }

    }

}
