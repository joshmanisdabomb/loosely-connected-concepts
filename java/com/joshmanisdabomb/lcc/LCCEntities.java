package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.entity.EntityNuclearExplosion;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCEntities {

    public static ArrayList<EntityType<?>> all = new ArrayList<>();

    //Explosions
    public static EntityType<?> nuclear_explosion;

    public static void init(RegistryEvent.Register<EntityType<?>> entityRegistryEvent) {
        //Explosions
        all.add(nuclear_explosion = EntityType.Builder.create(EntityNuclearExplosion.class, EntityNuclearExplosion::new).build("lcc:nuclear_explosion").setRegistryName(LCC.MODID, "nuclear_explosion"));
    }

}
