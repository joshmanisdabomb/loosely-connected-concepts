package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.LCCEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNuclearExplosion extends Entity {

    public EntityNuclearExplosion(World worldIn) {
        super(LCCEntities.nuclear_explosion, worldIn);
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(NBTTagCompound compound) {

    }

    @Override
    protected void writeAdditional(NBTTagCompound compound) {

    }

}