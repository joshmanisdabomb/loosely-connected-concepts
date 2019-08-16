package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvents {

    @SubscribeEvent
    public void onHandEvent(RenderSpecificHandEvent e) {
        Minecraft mc = Minecraft.getInstance();

        if (e.getHand() == Hand.MAIN_HAND && e.getItemStack().getItem() == LCCItems.gauntlet) {
            mc.getFirstPersonRenderer().renderItemInFirstPerson(mc.player, e.getPartialTicks(), e.getInterpolatedPitch(), Hand.MAIN_HAND, e.getSwingProgress(), ItemStack.EMPTY, e.getEquipProgress());
            GlStateManager.rotatef(-35.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(-75.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(175.0F, 0.0F, 0.0F, 1.0F);
            //GlStateManager.rotatef((float)MathHelper.clamp(mc.player.posX, -360, 360), 1.0F, 0.0F, 0.0F);
            //GlStateManager.rotatef((float)MathHelper.clamp(mc.player.posY - 200, -360, 360), 0.0F, 1.0F, 0.0F);
            //GlStateManager.rotatef((float)MathHelper.clamp(mc.player.posZ, -360, 360), 0.0F, 0.0F, 1.0F);
            double f = 9.486972888484538 * 0.0625;
            double f1 = -0.5449205513527204 * 0.0625;
            double f2 = 7.521918554545667 * 0.0625;
            GlStateManager.translated(f, f1, f2);
            float fx = 30.38779732888174F;
            float fy = 1.5F;//198.1828506694462F - 200;
            float fz = 21.05712457328804F;
            GlStateManager.rotatef(fx, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(fy, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(fz, 0.0F, 0.0F, 1.0F);
        }
    }

}