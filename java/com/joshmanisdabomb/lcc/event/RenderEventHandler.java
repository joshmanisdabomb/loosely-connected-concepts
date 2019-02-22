package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.LCCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEventHandler {

    @SubscribeEvent
    public void onHandEvent(RenderSpecificHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getHand() == EnumHand.MAIN_HAND && event.getItemStack().getItem() == LCCItems.gauntlet) {
            mc.getFirstPersonRenderer().renderItemInFirstPerson(mc.player, event.getPartialTicks(), event.getInterpolatedPitch(), EnumHand.MAIN_HAND, event.getSwingProgress(), ItemStack.EMPTY, event.getEquipProgress());
            event.setCanceled(true);
        }
    }

}