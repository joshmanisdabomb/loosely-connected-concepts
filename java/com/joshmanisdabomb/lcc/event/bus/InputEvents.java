package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.registry.LCCEffects;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputEvents {

    @SubscribeEvent
    public void onPlayerInput(InputUpdateEvent e) {
        PlayerEntity player = e.getEntityPlayer();
        if (player.isPotionActive(LCCEffects.stun) && !player.isCreative()) {
            e.getMovementInput().forwardKeyDown = false;
            e.getMovementInput().backKeyDown = false;
            e.getMovementInput().leftKeyDown = false;
            e.getMovementInput().rightKeyDown = false;
            e.getMovementInput().jump = false;
            e.getMovementInput().sneak = false;
            e.getMovementInput().moveForward = 0.0F;
            e.getMovementInput().moveStrafe = 0.0F;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        GameSettings settings = Minecraft.getInstance().gameSettings;
        if (player != null && player.isPotionActive(LCCEffects.stun) && !player.isCreative()) {
            KeyBinding.setKeyBindState(settings.keyBindAttack.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindUseItem.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindPickBlock.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindDrop.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindSwapHands.getKey(), false);
        }
    }

}
