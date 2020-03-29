package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.registry.LCCEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;

import java.util.List;

public interface MultipartEntity {

    default EntityType<MultipartHitbox> getHitboxType() {
        return LCCEntities.hitbox;
    }

    List<MultipartHitbox> getHitboxes();

    default void addHitbox(MultipartHitbox h) {
        this.getHitboxes().add(h);
        ((Entity)this).getEntityWorld().addEntity(h);
    }

    default void removeHitbox(MultipartHitbox h) {
        this.getHitboxes().remove(h);
        h.remove();
    }

    default void cleanupHitboxes() {
        this.getHitboxes().forEach(Entity::remove);
    }

    default void tickHitbox(MultipartHitbox hitbox, int index) {
        Entity e = (Entity)this;
        hitbox.setMotion(e.getMotion());
        hitbox.move(MoverType.SELF, hitbox.getMotion());
        hitbox.setPosition(e.getPosX(), e.getPosY(), e.getPosZ());
        hitbox.recalculateSize();
    }

    default void hitboxParallelTick() {
        for (MultipartHitbox h : this.getHitboxes()) {
            this.tickHitbox(h, h.getIndex());
        }
    }

}
