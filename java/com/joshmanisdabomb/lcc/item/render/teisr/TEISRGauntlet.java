package com.joshmanisdabomb.lcc.item.render.teisr;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.item.render.model.ModelGauntlet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TEISRGauntlet extends TileEntityItemStackRenderer {

    private final ModelGauntlet mg = new ModelGauntlet();
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/gauntlet.png");

    @Override
    public void renderByItem(ItemStack is) {
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        mg.render(is);
        GlStateManager.popMatrix();
    }

}
