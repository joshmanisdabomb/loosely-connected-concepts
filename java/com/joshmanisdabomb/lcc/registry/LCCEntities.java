package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.entity.*;
import com.joshmanisdabomb.lcc.entity.render.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.ArrayList;

public abstract class LCCEntities {

    public static final ArrayList<EntityType<?>> all = new ArrayList<>();

    //Nostalgic
    public static EntityType<ClassicZombiePigmanEntity> classic_zombie_pigman;

    //Explosives
    public static EntityType<ClassicTNTEntity> classic_tnt;
    public static EntityType<AtomicBombEntity> atomic_bomb;
    public static EntityType<MultipartHitbox> hitbox;
    public static EntityType<NuclearExplosionEntity> nuclear_explosion;

    public static void init(RegistryEvent.Register<EntityType<?>> e) {
        //Helpers
        all.add(hitbox = (EntityType<MultipartHitbox>)EntityType.Builder.<MultipartHitbox>create(MultipartHitbox::new, EntityClassification.MISC).setTrackingRange(10).setUpdateInterval(2).setShouldReceiveVelocityUpdates(true).size(0.98F, 0.98F).setCustomClientFactory((spawn, world) -> new MultipartHitbox(LCCEntities.hitbox, world)).build("hitbox").setRegistryName(LCC.MODID, "hitbox"));

        //Nostalgic
        all.add(classic_zombie_pigman = (EntityType<ClassicZombiePigmanEntity>)EntityType.Builder.<ClassicZombiePigmanEntity>create(ClassicZombiePigmanEntity::new, EntityClassification.MONSTER).setTrackingRange(5).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).immuneToFire().size(0.6F, 1.95F).build("classic_zombie_pigman").setRegistryName(LCC.MODID, "classic_zombie_pigman"));

        //Explosives
        all.add(classic_tnt = (EntityType<ClassicTNTEntity>)EntityType.Builder.<ClassicTNTEntity>create(ClassicTNTEntity::new, EntityClassification.MISC).setTrackingRange(10).setUpdateInterval(10).setShouldReceiveVelocityUpdates(true).immuneToFire().size(0.98F, 0.98F).setCustomClientFactory((spawn, world) -> new ClassicTNTEntity(LCCEntities.classic_tnt, world)).build("classic_tnt").setRegistryName(LCC.MODID, "classic_tnt"));
        all.add(atomic_bomb = (EntityType<AtomicBombEntity>)EntityType.Builder.<AtomicBombEntity>create(AtomicBombEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(2).setShouldReceiveVelocityUpdates(true).immuneToFire().size(0.98F, 0.98F).setCustomClientFactory((spawn, world) -> new AtomicBombEntity(LCCEntities.atomic_bomb, world)).build("atomic_bomb").setRegistryName(LCC.MODID, "atomic_bomb"));
        all.add(nuclear_explosion = (EntityType<NuclearExplosionEntity>)EntityType.Builder.<NuclearExplosionEntity>create(NuclearExplosionEntity::new, EntityClassification.MISC).setTrackingRange(10).setUpdateInterval(Integer.MAX_VALUE).setShouldReceiveVelocityUpdates(false).size(10F, 10F).setCustomClientFactory((spawn, world) -> new NuclearExplosionEntity(LCCEntities.nuclear_explosion, world)).build("nuclear_explosion").setRegistryName(LCC.MODID, "nuclear_explosion"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ClassicTNTEntity.class, manager -> new StateBasedTNTRenderer(LCCBlocks.classic_tnt.getDefaultState(), manager));
        RenderingRegistry.registerEntityRenderingHandler(NuclearExplosionEntity.class, NuclearExplosionRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ClassicZombiePigmanEntity.class, ClassicZombiePigmanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MultipartHitbox.class, EmptyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AtomicBombEntity.class, AtomicBombRenderer::new);
    }

}
