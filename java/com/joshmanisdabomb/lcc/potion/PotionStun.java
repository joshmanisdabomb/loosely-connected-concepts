package com.joshmanisdabomb.lcc.potion;

import com.joshmanisdabomb.lcc.registry.LCCPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionStun extends Potion {

    public PotionStun(boolean bad, int color) {
        super(bad, color);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {

    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount();
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.getTextureManager().bindTexture(LCCPotions.POTION_TEXTURE);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }

    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(LCCPotions.POTION_TEXTURE);
        mc.ingameGUI.drawTexturedModalRect(x + 3, y + 3, 0, 0, 18, 18);
    }

}
