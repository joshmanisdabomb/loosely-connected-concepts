package com.joshmanisdabomb.lcc.gui.overlay;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.item.ItemGauntlet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class OverlayGauntlet extends Gui implements LCCOverlay {

    public static final int ABILITY_WIDTH = 18;
    public static final int ABILITY_HEIGHT = 5;

    public static final int ABILITY_UPPERCUT_V = 27;
    public static final int ABILITY_PUNCH_V = 32;
    public static final int ABILITY_STOMP_V = 37;

    public static final int ABILITY_COOLDOWN_Ud = ABILITY_WIDTH*5;
    public static final int ABILITY_TICK_Ud = -ABILITY_WIDTH;
    public static final int ABILITY_DISABLED_Ud = ABILITY_WIDTH*6;
    public static final int ABILITY_CHARGE_U = ABILITY_WIDTH*12;
    public static final int ABILITY_USE_U = ABILITY_WIDTH*13;

    public static final ResourceLocation GAUNTLET_ICONS = new ResourceLocation(LCC.MODID, "textures/gui/gauntlet.png");

    @Override
    public void draw(EntityPlayer player, Minecraft minecraft, RenderGameOverlayEvent.Post event) {
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!player.isSpectator() && stack.getItem() instanceof ItemGauntlet) {
            minecraft.getTextureManager().bindTexture(GAUNTLET_ICONS);

            player.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
                int abilityY = Minecraft.getInstance().mainWindow.getScaledHeight()/2 + 10;

                int punchX = Minecraft.getInstance().mainWindow.getScaledWidth()/2 - ABILITY_WIDTH/2;
                int uppercutX = punchX - 20;
                int stompX = punchX + 20;

                boolean useFlag = gauntlet.getPunchDurationRaw() > 0 || gauntlet.getStompDurationRaw() > 0;

                int uppercutU = (gauntlet.getUppercutCooldownRaw() <= 0 ? ABILITY_TICK_Ud * gauntlet.getUppercutCooldownRaw() : ABILITY_COOLDOWN_Ud) + (useFlag ? ABILITY_DISABLED_Ud : 0);
                int punchU = (gauntlet.getPunchCooldownRaw() <= 0 ? ABILITY_TICK_Ud * gauntlet.getPunchCooldownRaw() : ABILITY_COOLDOWN_Ud) + (useFlag ? ABILITY_DISABLED_Ud : 0);
                int stompU = (gauntlet.getStompCooldownRaw() <= 0 ? ABILITY_TICK_Ud * gauntlet.getStompCooldownRaw() : ABILITY_COOLDOWN_Ud) + (useFlag ? ABILITY_DISABLED_Ud : 0);

                this.drawTexturedModalRect(uppercutX, abilityY, uppercutU, ABILITY_UPPERCUT_V, ABILITY_WIDTH, ABILITY_HEIGHT);
                this.drawTexturedModalRect(punchX, abilityY, gauntlet.getPunchDurationRaw() > 0 ? ABILITY_USE_U : punchU, ABILITY_PUNCH_V, ABILITY_WIDTH, ABILITY_HEIGHT);
                this.drawTexturedModalRect(stompX, abilityY, gauntlet.getStompDurationRaw() > 0 ? ABILITY_USE_U : stompU, ABILITY_STOMP_V, ABILITY_WIDTH, ABILITY_HEIGHT);

                if (player.isHandActive() && player.getActiveHand() == EnumHand.MAIN_HAND && player.getItemInUseCount() > 0) {
                    float strength = GauntletFunctionality.getStrength(stack, player.getItemInUseCount());
                    this.drawTexturedModalRect(punchX, abilityY, ABILITY_CHARGE_U, ABILITY_PUNCH_V, (int)Math.floor(strength * ABILITY_WIDTH), ABILITY_HEIGHT);
                }
            });

            minecraft.getTextureManager().bindTexture(ICONS);
        }
    }

}
