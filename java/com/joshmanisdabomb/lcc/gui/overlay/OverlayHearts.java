package com.joshmanisdabomb.lcc.gui.overlay;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.data.capability.CapabilityHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class OverlayHearts extends Gui implements LCCOverlay {

    public static final int HEART_WIDTH = 9;
    public static final int HEART_HEIGHT = 9;

    public static final int HEART_IRON_V = 0;
    public static final int HEART_CRYSTAL_V = HEART_HEIGHT*2;
    public static final int HEART_TEMPORARY_V = HEART_HEIGHT*4;

    public static final int HEART_NONCONTAINER_U = HEART_WIDTH*8;

    public static final int HEART_CONTAINER_HALF_Ud = HEART_WIDTH*4;
    public static final int HEART_HALF_Ud = HEART_WIDTH;
    public static final int HEART_POISON_Ud = HEART_WIDTH*4;
    public static final int HEART_WITHER_Ud = HEART_WIDTH*8;
    public static final int HEART_HARDCORE_Vd = HEART_HEIGHT;

    public int originalHeartX;
    public int originalHeartRows;
    public int originalHeartSeparation;
    public int heartColour;
    public int heartHardcore;

    public static final ResourceLocation HEART_ICONS = new ResourceLocation(LCC.MODID, "textures/gui/hearts.png");

    @Override
    public void draw(EntityPlayer player, Minecraft minecraft, RenderGameOverlayEvent.Post event) {
        minecraft.getTextureManager().bindTexture(HEART_ICONS);

        originalHeartX = (Minecraft.getInstance().mainWindow.getScaledWidth() / 2) - 91;
        originalHeartRows = MathHelper.ceil((player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getValue() + player.getAbsorptionAmount()) / 20.0F);
        originalHeartSeparation = Math.max(10 - (originalHeartRows - 2), 3);
        heartColour = player.isPotionActive(MobEffects.POISON) ? HEART_POISON_Ud : player.isPotionActive(MobEffects.WITHER) ? HEART_WITHER_Ud : 0;
        heartHardcore = minecraft.world.getWorldInfo().isHardcore() ? HEART_HARDCORE_Vd : 0;

        player.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
            this.drawHealthType(HEART_IRON_V, hearts.getIronHealth(), hearts.getIronMaxHealth());
            this.drawHealthType(HEART_CRYSTAL_V, hearts.getCrystalHealth(), hearts.getCrystalMaxHealth());
            this.drawHealthType(HEART_TEMPORARY_V, hearts.getTemporaryHealth(), hearts.getTemporaryHealth());
        });

        minecraft.getTextureManager().bindTexture(ICONS);
    }

    //TODO: support for temporary health above 20.0F
    private void drawHealthType(int typeV, float health, float healthMax) {
        int heartY = Minecraft.getInstance().mainWindow.getScaledHeight() - GuiIngameForge.left_height - 1;
        for (float i = (int)Math.ceil(healthMax); i > 0; i -= (i % 2 == 1) ? 1 : 2) {
            if (i % 2 == 1) {
                this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), heartY, HEART_CONTAINER_HALF_Ud, typeV, HEART_WIDTH, HEART_HEIGHT);
            } else {
                this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), heartY, 0, typeV, HEART_WIDTH, HEART_HEIGHT);
            }
        }
        for (float i = (int)Math.ceil(health); i > 0; i -= (i % 2 == 1) ? 1 : 2) {
            if (i % 2 == 1) {
                this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), heartY, HEART_NONCONTAINER_U + HEART_HALF_Ud + heartColour, typeV + heartHardcore, HEART_WIDTH, HEART_HEIGHT);
            } else {
                this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), heartY, HEART_NONCONTAINER_U + heartColour, typeV + heartHardcore, HEART_WIDTH, HEART_HEIGHT);
            }
        }
        if (healthMax > 0) {
            GuiIngameForge.left_height += this.originalHeartSeparation;
        }
    }

}
