package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.item.render.GauntletRenderer;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvents {

    public static final GauntletRenderer GAUNTLET = new GauntletRenderer();

    @SubscribeEvent
    public void onHandEvent(RenderSpecificHandEvent e) {
        Minecraft mc = Minecraft.getInstance();

        if (e.getHand() == Hand.MAIN_HAND && e.getItemStack().getItem() == LCCItems.gauntlet) {
            GlStateManager.pushMatrix();
            boolean flag = mc.player.getPrimaryHand() != HandSide.LEFT;
            float f = flag ? 1.0F : -1.0F;
            float f1 = MathHelper.sqrt(e.getSwingProgress());
            float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
            float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
            float f4 = -0.4F * MathHelper.sin(e.getSwingProgress() * (float)Math.PI);
            GlStateManager.translatef(f * (f2 + 0.64000005F), f3 + -0.6F + e.getEquipProgress() * -0.6F, f4 + -0.71999997F);
            GlStateManager.rotatef(f * 45.0F, 0.0F, 1.0F, 0.0F);
            float f5 = MathHelper.sin(e.getSwingProgress() * e.getSwingProgress() * (float)Math.PI);
            float f6 = MathHelper.sin(f1 * (float)Math.PI);
            GlStateManager.rotatef(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
            AbstractClientPlayerEntity abstractclientplayerentity = mc.player;
            mc.getTextureManager().bindTexture(abstractclientplayerentity.getLocationSkin());
            GlStateManager.translatef(f * -1.0F, 3.6F, 3.5F);
            GlStateManager.rotatef(f * 120.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotatef(200.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(f * -135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translatef(f * 5.6F, 0.0F, 0.0F);

            EntityRenderer<AbstractClientPlayerEntity> r = mc.getRenderManager().getRenderer(mc.player);
            PlayerRenderer pr = (PlayerRenderer)r;
            if (flag) pr.renderRightArm(mc.player);
            else pr.renderLeftArm(mc.player);

            GlStateManager.scalef(1.0F, -1.0F, -1.0F);
            GlStateManager.translatef(f * -0.425f, -1, 0);
            GAUNTLET.render(e.getItemStack(), true);

            e.setCanceled(true);
            GlStateManager.popMatrix();
        }
    }

}