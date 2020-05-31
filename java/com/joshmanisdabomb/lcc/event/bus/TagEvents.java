package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TagEvents {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        for (LCCTags.AreaEffectivity ae : LCCTags.AreaEffectivity.values()) {
            if (e.getState().getBlock().isIn(ae.effective.block)) {
                if (e.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem().isIn(ae.equipment)) return;
                if (e.getState().getBlock().isIn(ae.required.block)) {
                    e.setNewSpeed(ae.getRequiredSpeed(e.getOriginalSpeed()));
                    return;
                }
                e.setNewSpeed(ae.getEffectiveSpeed(e.getOriginalSpeed()));
            }
        }
    }

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent e) {
        for (LCCTags.AreaEffectivity ae : LCCTags.AreaEffectivity.values()) {
            System.out.println(ae);
            if (e.getState().getBlock().isIn(ae.required.block)) {
                if (e.getHarvester() != null) {
                    if (!e.getHarvester().getHeldItem(Hand.MAIN_HAND).getItem().isIn(ae.equipment)) {
                        e.getDrops().clear();
                        e.setDropChance(0.0F);
                        return;
                    } else {
                        return;
                    }
                } else {
                    e.getDrops().clear();
                    e.setDropChance(0.0F);
                    return;
                }
            }
        }
    }

}