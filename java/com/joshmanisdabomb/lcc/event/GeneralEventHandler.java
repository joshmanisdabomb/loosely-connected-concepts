package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.item.ItemGauntlet;
import com.joshmanisdabomb.lcc.registry.LCCPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.activation.MailcapCommandMap;

public class GeneralEventHandler {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        if (e.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGauntlet) {
            e.setNewSpeed(-1);
        }
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        entity.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            GauntletFunctionality.tick(gauntlet, entity.getHeldItem(EnumHand.MAIN_HAND), entity);
        });
        entity.setRevengeTarget(null);

    }

    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent e) {
        if (e.getSource().getClass().equals(EntityDamageSource.class) && e.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = ((EntityLivingBase)e.getSource().getTrueSource());
            if (attacker.isPotionActive(LCCPotions.stun) || (attacker.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGauntlet && !e.getSource().getDamageType().startsWith("lcc:gauntlet_"))) {
                e.setCanceled(true);
            }
        }
    }

}
