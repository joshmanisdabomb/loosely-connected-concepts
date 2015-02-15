package yam.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantGeneric extends Enchantment {

	private int minLevel;
	private int maxLevel;
	private int minEnchant;
	private int maxEnchant;

	public EnchantGeneric(String name, int id, int rarity, int minLevel, int maxLevel, int minEnchant, int maxEnchant, EnumEnchantmentType par3EnumEnchantmentType) {
		super(id, rarity, par3EnumEnchantmentType);
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.minEnchant = minEnchant;
		this.maxEnchant = maxEnchant;
		this.setName(name);
	}
	
	public int getMinLevel()
    {
        return minLevel;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return maxLevel;
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return minEnchant + par1 * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return this.getMinEnchantability(par1) + maxEnchant;
    }

}
