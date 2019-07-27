package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import com.joshmanisdabomb.lcc.entity.render.NuclearExplosionRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.ArrayList;

public abstract class LCCEntities {

    public static ArrayList<EntityType<?>> all = new ArrayList<>();

    //Explosions
    public static EntityType<?> nuclear_explosion;

    public static void init(RegistryEvent.Register<EntityType<?>> e) {
        //Explosions
        all.add(nuclear_explosion = EntityType.Builder.create(NuclearExplosionEntity::new, EntityClassification.MISC).setTrackingRange(2000).setUpdateInterval(1).setShouldReceiveVelocityUpdates(false).size(10F, 10F).build("nuclear_explosion").setRegistryName(LCC.MODID, "nuclear_explosion"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(NuclearExplosionEntity.class, NuclearExplosionRenderer::new);
    }

}
