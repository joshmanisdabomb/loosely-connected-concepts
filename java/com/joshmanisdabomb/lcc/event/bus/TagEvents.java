package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TagEvents {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        System.out.println(e.getOriginalSpeed());
        if (e.getState().getBlock().isIn(LCCTags.RAINBOW_EFFECTIVE.block)) {
            if (e.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem().isIn(LCCTags.RAINBOW_EQUIPMENT)) return;
            if (e.getState().getBlock().isIn(LCCTags.RAINBOW_REQUIRED.block)) {
                e.setNewSpeed(0.1F + (e.getOriginalSpeed() * 0.015F));
                return;
            }
            e.setNewSpeed(0.2F + (e.getOriginalSpeed() * 0.03F));
        } else if (e.getState().getBlock().isIn(LCCTags.WASTELAND_EFFECTIVE.block)) {
            if (e.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem().isIn(LCCTags.WASTELAND_EQUIPMENT)) return;
            if (e.getState().getBlock().isIn(LCCTags.WASTELAND_REQUIRED.block)) {
                e.setNewSpeed(0.1F + (e.getOriginalSpeed() * 0.015F));
                return;
            }
            e.setNewSpeed(0.2F + (e.getOriginalSpeed() * 0.03F));
        }
    }

}