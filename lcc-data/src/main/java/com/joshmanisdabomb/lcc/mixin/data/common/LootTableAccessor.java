package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.function.LootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootTable.class)
public interface LootTableAccessor {

    @Accessor("pools")
    public LootPool[] getPools();

    @Accessor("functions")
    public LootFunction[] getFunctions();

}