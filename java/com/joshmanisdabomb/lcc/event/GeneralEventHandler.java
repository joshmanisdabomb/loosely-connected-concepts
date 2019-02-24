package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.item.ItemGauntlet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class GeneralEventHandler {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        if (e.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGauntlet) {
            e.setNewSpeed(-1);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(LivingEvent.LivingUpdateEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        entity.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            gauntlet.tick();
            if (gauntlet.isPunched()) {
                if (entity.collidedHorizontally || Math.max(Math.abs(entity.motionX), Math.abs(entity.motionZ)) < 0.8D) {
                    gauntlet.stopPunched();
                    entity.motionX = 0.0F;
                    entity.motionZ = 0.0F;
                } else {
                    entity.motionY = 0.0F;
                    entity.isAirBorne = true;
                }
            } else if (gauntlet.isPunching()) {
                List<Entity> entities = entity.world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox().grow(1.0F), (en) -> true);
                if (entities.size() > 0) {
                    for (Entity other : entities) {
                        other.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet2 -> {
                            other.addVelocity(gauntlet.getPunchVelocityX(), 0.3F, gauntlet.getPunchVelocityZ());
                            other.fallDistance = 0.0F;
                            other.isAirBorne = true;
                            gauntlet2.punched(gauntlet.getPunchStrength());
                        });
                    }
                    gauntlet.stopPunch();
                    entity.motionX = 0.0F;
                    entity.motionY = 0.0F;
                    entity.motionZ = 0.0F;
                    entity.fallDistance = Math.min(entity.fallDistance, 0.0F);
                    entity.isAirBorne = true;
                } else if (entity.collidedHorizontally || Math.max(Math.abs(entity.motionX), Math.abs(entity.motionZ)) < 0.8D) {
                    gauntlet.stopPunch();
                    entity.motionX = 0.0F;
                    entity.motionZ = 0.0F;
                } else {
                    entity.motionY = 0.0F;
                    entity.isAirBorne = true;
                }
            }
        });
    }

}
