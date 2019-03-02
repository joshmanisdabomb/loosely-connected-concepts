package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.registry.LCCPotions;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InputEventHandler {

    @SubscribeEvent
    public void onPlayerInput(InputUpdateEvent e) {
        EntityPlayer player = e.getEntityPlayer();
        if (player.isPotionActive(LCCPotions.stun) && !player.isCreative()) {
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
        EntityPlayerSP player = Minecraft.getInstance().player;
        GameSettings settings = Minecraft.getInstance().gameSettings;
        if (player != null && player.isPotionActive(LCCPotions.stun) && !player.isCreative()) {
            KeyBinding.setKeyBindState(settings.keyBindAttack.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindUseItem.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindPickBlock.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindDrop.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindSwapHands.getKey(), false);
        }
    }

}
