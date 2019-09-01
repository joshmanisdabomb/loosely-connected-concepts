package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.entity.ClassicTNTEntity;
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import com.joshmanisdabomb.lcc.entity.render.NuclearExplosionRenderer;
import com.joshmanisdabomb.lcc.entity.render.StateBasedTNTRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.ArrayList;

public abstract class LCCEntities {

    public static final ArrayList<EntityType<?>> all = new ArrayList<>();

    //Explosives
    public static EntityType<ClassicTNTEntity> classic_tnt;
    public static EntityType<NuclearExplosionEntity> nuclear_explosion;

    public static void init(RegistryEvent.Register<EntityType<?>> e) {
        //Explosives
        all.add(classic_tnt = (EntityType<ClassicTNTEntity>)EntityType.Builder.<ClassicTNTEntity>create(ClassicTNTEntity::new, EntityClassification.MISC).setTrackingRange(10).setUpdateInterval(10).setShouldReceiveVelocityUpdates(true).immuneToFire().size(0.98F, 0.98F).build("classic_tnt").setRegistryName(LCC.MODID, "classic_tnt"));
        all.add(nuclear_explosion = (EntityType<NuclearExplosionEntity>)EntityType.Builder.create(NuclearExplosionEntity::new, EntityClassification.MISC).setTrackingRange(2000).setUpdateInterval(1).setShouldReceiveVelocityUpdates(false).size(10F, 10F).build("nuclear_explosion").setRegistryName(LCC.MODID, "nuclear_explosion"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ClassicTNTEntity.class, manager -> new StateBasedTNTRenderer(LCCBlocks.classic_tnt.getDefaultState(), manager));
        RenderingRegistry.registerEntityRenderingHandler(NuclearExplosionEntity.class, NuclearExplosionRenderer::new);
    }

}
