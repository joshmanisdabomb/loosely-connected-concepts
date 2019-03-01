package com.joshmanisdabomb.lcc.functionality;

import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.data.capability.CapabilityHearts;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public abstract class HeartsFunctionality {

    public static final float IRON_LIMIT = 20.0F;
    public static final float CRYSTAL_LIMIT = 20.0F;
    public static final float TEMPORARY_USUAL_LIMIT = 20.0F;

    public static void tick(CapabilityHearts.CIHearts hearts, EntityLivingBase actor) {
        if (hearts.getTemporaryHealth() > 0) {
            hearts.addTemporaryHealth(-0.02F, Float.MAX_VALUE);
        }
    }

    public static void capabilityClone(CapabilityHearts.CIHearts heartsOriginal, CapabilityHearts.CIHearts heartsNew, EntityPlayer playerOriginal, EntityPlayer playerNew, PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            heartsNew.setIronMaxHealth(heartsOriginal.getIronMaxHealth());
            heartsNew.setIronHealth(heartsOriginal.getIronHealth());
            heartsNew.setCrystalMaxHealth(heartsOriginal.getCrystalMaxHealth());
            heartsNew.setCrystalHealth(heartsOriginal.getCrystalHealth());
            heartsNew.setTemporaryHealth(heartsOriginal.getTemporaryHealth(), Float.MAX_VALUE);
        }
    }

}
