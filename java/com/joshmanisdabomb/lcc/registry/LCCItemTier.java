package com.joshmanisdabomb.lcc.registry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public enum LCCItemTier implements IItemTier {

    RUBY(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.fromItems(LCCItems.ruby)),
    TOPAZ(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.fromItems(LCCItems.topaz)),
    EMERALD(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.fromItems(Items.EMERALD)),
    SAPPHIRE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.fromItems(LCCItems.sapphire)),
    AMETHYST(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.fromItems(LCCItems.amethyst)),
    RED_CANDY_CANE(0, 314, 10.0F, 3.0F, 5, () -> Ingredient.fromTag(LCCTags.RED_CANDY_CANES.item)),
    GREEN_CANDY_CANE(0, 314, 10.0F, 3.0F, 5, () -> Ingredient.fromTag(LCCTags.BLUE_CANDY_CANES.item)),
    BLUE_CANDY_CANE(0, 314, 10.0F, 3.0F, 5, () -> Ingredient.fromTag(LCCTags.GREEN_CANDY_CANES.item)),
    VIVID_WOOD(0, 127, 12.0F, 2.0F, 33, () -> Ingredient.fromItems(LCCBlocks.vivid_planks.asItem())),
    TWILIGHT(1, 1149, 13.5F, 3.5F, 14, () -> Ingredient.fromItems(LCCBlocks.twilight_cobblestone.asItem())),
    NEON(2, 2102, 16.0F, 4.0F, 25, () -> Ingredient.fromItems(LCCItems.neon));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    LCCItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = Lazy.of(repairMaterial);
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
        return this.repairMaterial.get();
    }

}
