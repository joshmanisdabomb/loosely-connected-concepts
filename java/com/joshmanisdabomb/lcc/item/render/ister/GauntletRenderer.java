package com.joshmanisdabomb.lcc.item.render.ister;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.item.render.model.GauntletModel;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GauntletRenderer extends ItemStackTileEntityRenderer {

    private final GauntletModel gm = new GauntletModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/gauntlet.png");

    @Override
    public void renderByItem(ItemStack is) {
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        gm.render(is);
        GlStateManager.popMatrix();
    }

}
