package com.joshmanisdabomb.lcc.potion;

import com.joshmanisdabomb.lcc.registry.LCCEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.client.config.GuiUtils;

public class StunEffect extends Effect {

    public StunEffect(EffectType type, int color) {
        super(type, color);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {

    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount();
    }

    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, int x, int y, float z) {
        Minecraft.getInstance().getTextureManager().bindTexture(LCCEffects.POTION_TEXTURE);
        GuiUtils.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18, 1000F);
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getTextureManager().bindTexture(LCCEffects.POTION_TEXTURE);
        GuiUtils.drawTexturedModalRect(x + 3, y + 3, 0, 0, 18, 18, 1000F);
    }

}
