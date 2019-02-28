package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.potion.PotionStun;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCPotions {

    public static final ResourceLocation POTION_TEXTURE = new ResourceLocation(LCC.MODID, "textures/gui/potions.png");

    public static final ArrayList<Potion> all = new ArrayList<>();

    public static Potion stun;

    public static void init(RegistryEvent.Register<Potion> e) {
        all.add(stun = new PotionStun(true, 0x00ffd8)
            .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "2ea94220-39e7-11e9-b210-50fabd873d93", (double)-1F, 2)
            .setRegistryName(LCC.MODID, "stun"));
    }

}
