package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.item.ItemGauntlet;
import com.joshmanisdabomb.lcc.registry.LCCPotions;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
        if (entity.isPotionActive(LCCPotions.stun)) {
            entity.onGround = false;
            if (entity instanceof EntityLiving) {
                ((EntityLiving)entity).tasks.taskEntries.stream()
                    .filter(t -> t.action instanceof EntityAICreeperSwell || t.action instanceof EntityAIAttackMelee || t.action instanceof EntityAIAttackRanged || t.action instanceof EntityAIAttackRangedBow)
                    .forEach(t -> t.action.resetTask());
            }
        }
    }

    @SubscribeEvent
    public void onItemUse(LivingEntityUseItemEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        if (entity.isPotionActive(LCCPotions.stun)) {
            if (e.isCancelable()) e.setCanceled(true);
        }
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

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        EntityPlayer player = e.getEntityPlayer();
        System.out.println(e.getClass().getCanonicalName());
        if (player.isPotionActive(LCCPotions.stun) && !player.isCreative()) {
            if (e.isCancelable()) {
                e.setCancellationResult(EnumActionResult.FAIL);
                e.setCanceled(true);
            } else if (e.hasResult()) {
                e.setResult(Event.Result.DENY);
            }
        }
    }

}
