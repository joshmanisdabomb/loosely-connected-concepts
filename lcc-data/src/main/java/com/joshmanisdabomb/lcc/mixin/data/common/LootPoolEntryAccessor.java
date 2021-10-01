package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.LootPoolEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPoolEntry.class)
public interface LootPoolEntryAccessor {

    @Accessor("conditions")
    public LootCondition[] getConditions();

}