package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.class)
public interface LootPoolAccessor {

    @Accessor("entries")
    public LootPoolEntry[] getPoolEntries();

    @Accessor("conditions")
    public LootCondition[] getConditions();

    @Accessor("functions")
    public LootFunction[] getFunctions();

    @Accessor("rolls")
    public LootNumberProvider getRolls();

    @Accessor("bonusRolls")
    public LootNumberProvider getBonusRolls();

}