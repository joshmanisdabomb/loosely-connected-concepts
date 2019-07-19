package com.joshmanisdabomb.lcc.registry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyLoadBase;

import java.util.function.Supplier;

public enum LCCItemTier implements IItemTier {

    RUBY(3, 1561, 8.0F, 3.0F, 10, () -> { return Ingredient.fromItems(new IItemProvider[]{LCCItems.ruby}); }),
    TOPAZ(3, 1561, 8.0F, 3.0F, 10, () -> { return Ingredient.fromItems(new IItemProvider[]{LCCItems.topaz}); }),
    EMERALD(3, 1561, 8.0F, 3.0F, 10, () -> { return Ingredient.fromItems(new IItemProvider[]{Items.EMERALD}); }),
    SAPPHIRE(3, 1561, 8.0F, 3.0F, 10, () -> { return Ingredient.fromItems(new IItemProvider[]{LCCItems.sapphire}); }),
    AMETHYST(3, 1561, 8.0F, 3.0F, 10, () -> { return Ingredient.fromItems(new IItemProvider[]{LCCItems.amethyst}); });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadBase<Ingredient> repairMaterial;

    private LCCItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadBase<>(repairMaterial);
    }

    @Override
    public int getMaxUses() {
        return this.maxUses;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return (Ingredient)this.repairMaterial.getValue();
    }

}
