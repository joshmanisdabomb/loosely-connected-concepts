package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public abstract class LCCDamage {

    public static final DamageSource GAUNTLET_PUNCH_WALL = (new DamageSource("lcc.gauntlet_punch_wall")).setDamageBypassesArmor().setDamageIsAbsolute();

    public static final DamageSource NUKE = (new DamageSource("lcc.nuke")).setExplosion();

    public static DamageSource causeGauntletUppercutDamage(Entity entity) {
        return new EntityDamageSource("lcc.gauntlet_uppercut", entity);
    }

    public static DamageSource causeGauntletPunchDamage(Entity entity) {
        return new EntityDamageSource("lcc.gauntlet_punch", entity);
    }

    public static DamageSource causeNukeDamage(NuclearExplosionEntity entity) {
        return entity.getCausedBy() == null ? NUKE : new EntityDamageSource("lcc.nuke.player", entity.getCausedBy()).setExplosion();
    }

}