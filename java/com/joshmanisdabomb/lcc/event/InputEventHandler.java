package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.registry.LCCPotions;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InputEventHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        EntityPlayerSP player = Minecraft.getInstance().player;
        GameSettings settings = Minecraft.getInstance().gameSettings;
        if (player != null && player.isPotionActive(LCCPotions.stun) && !player.isCreative()) {
            KeyBinding.setKeyBindState(settings.keyBindAttack.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindUseItem.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindPickBlock.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindForward.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindBack.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindLeft.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindRight.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindJump.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindSneak.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindSprint.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindDrop.getKey(), false);
            KeyBinding.setKeyBindState(settings.keyBindSwapHands.getKey(), false);
        }
    }

}
