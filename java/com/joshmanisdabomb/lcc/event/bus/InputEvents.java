package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.registry.LCCEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputEvents {

    private boolean lastStun = false;

    @SubscribeEvent
    public void onPlayerInput(InputUpdateEvent e) {
        PlayerEntity player = e.getPlayer();
        if (player.isPotionActive(LCCEffects.stun) && !player.isCreative()) {
            e.getMovementInput().forwardKeyDown = false;
            e.getMovementInput().backKeyDown = false;
            e.getMovementInput().leftKeyDown = false;
            e.getMovementInput().rightKeyDown = false;
            e.getMovementInput().jump = false;
            e.getMovementInput().sneaking = false;
            e.getMovementInput().moveForward = 0.0F;
            e.getMovementInput().moveStrafe = 0.0F;
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.RawMouseEvent e) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isPotionActive(LCCEffects.stun) && !player.isCreative()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        boolean stun = player != null && player.isPotionActive(LCCEffects.stun) && !player.isCreative();
        if (stun != lastStun) {
            MouseHelper mh = stun ? new StunMouseHelper(Minecraft.getInstance()) : new MouseHelper(Minecraft.getInstance());
            mh.registerCallbacks(Minecraft.getInstance().getMainWindow().getHandle());
            if (Minecraft.getInstance().currentScreen == null) mh.grabMouse();
            else mh.ungrabMouse();
            //Minecraft.getInstance().mouseHelper = mh;
        }
        lastStun = stun;
    }

    private static class StunMouseHelper extends MouseHelper {

        public StunMouseHelper(Minecraft p_i47672_1_) {
            super(p_i47672_1_);
        }

        public void updatePlayerLook() {

        }

    }

}
