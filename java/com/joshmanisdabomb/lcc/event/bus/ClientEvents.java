package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.registry.LCCEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

    @SubscribeEvent
    public void onClientTick(TickEvent.RenderTickEvent e) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (e.phase == TickEvent.Phase.START && player != null && player.getActivePotionEffect(LCCEffects.radiation) != null) {
            player.hurtTime = 0;
        }
    }

}
