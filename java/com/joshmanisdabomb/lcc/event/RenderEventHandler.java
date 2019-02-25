package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.LCCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEventHandler {

    @SubscribeEvent
    public void onHandEvent(RenderSpecificHandEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if (e.getHand() == EnumHand.MAIN_HAND && e.getItemStack().getItem() == LCCItems.gauntlet) {
            mc.getFirstPersonRenderer().renderItemInFirstPerson(mc.player, e.getPartialTicks(), e.getInterpolatedPitch(), EnumHand.MAIN_HAND, e.getSwingProgress(), ItemStack.EMPTY, e.getEquipProgress());
        }
    }

}