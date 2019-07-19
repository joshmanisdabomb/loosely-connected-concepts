package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.data.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.item.GauntletItem;
import com.joshmanisdabomb.lcc.registry.LCCEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GeneralEvents {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        if (e.getEntityPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof GauntletItem) {
            e.setNewSpeed(-1);
        }
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent e) {
        LivingEntity entity = e.getEntityLiving();
        entity.getCapability(HeartsCapability.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
            HeartsFunctionality.tick(hearts, entity);
        });
        entity.getCapability(GauntletCapability.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            GauntletFunctionality.tick(gauntlet, entity.getHeldItem(Hand.MAIN_HAND), entity);
        });
        if (entity.isPotionActive(LCCEffects.stun)) {
            entity.onGround = false;
            if (entity instanceof MobEntity) {
                ((MobEntity)entity).goalSelector.getRunningGoals()
                    .filter(g -> g.getGoal() instanceof CreeperSwellGoal || g.getGoal() instanceof MeleeAttackGoal || g.getGoal() instanceof RangedAttackGoal || g.getGoal() instanceof RangedBowAttackGoal || g.getGoal() instanceof RangedCrossbowAttackGoal)
                    .forEach(PrioritizedGoal::resetTask);
            }
        }
    }

    @SubscribeEvent
    public void onItemUse(LivingEntityUseItemEvent e) {
        if (e.getEntityLiving().isPotionActive(LCCEffects.stun)) {
            if (e.isCancelable()) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent e) {
        if (e.getSource().getClass().equals(EntityDamageSource.class) && e.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = ((LivingEntity)e.getSource().getTrueSource());
            if (attacker.isPotionActive(LCCEffects.stun) || (attacker.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GauntletItem && !e.getSource().getDamageType().startsWith("lcc.gauntlet_"))) {
                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        PlayerEntity player = e.getEntityPlayer();
        if (player.isPotionActive(LCCEffects.stun) && !player.isCreative()) {
            if (e.isCancelable()) {
                e.setCancellationResult(ActionResultType.FAIL);
                e.setCanceled(true);
            } else if (e.hasResult()) {
                e.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent e) {
        LivingEntity entity = e.getEntityLiving();
        entity.getCapability(HeartsCapability.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
            HeartsFunctionality.hurt(hearts, entity, e);
        });
    }

}
