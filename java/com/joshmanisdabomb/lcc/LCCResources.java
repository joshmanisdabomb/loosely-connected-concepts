package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.block.BlockResourceOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public enum LCCResources {

    RUBY(false, OreType.DROP_INGOT, true, true),
    TOPAZ(false, OreType.DROP_INGOT, true, true),
    SAPPHIRE(false, OreType.DROP_INGOT, true, true),
    AMETHYST(false, OreType.DROP_INGOT, true, true),
    URANIUM(true, OreType.DROP_SELF, true, true);

    public final Item nugget;
    public final Block ore;
    public final OreType oreType;
    public final Item ingot;
    public final Block storage;

    LCCResources(boolean nugget, OreType ore, boolean ingot, boolean storage) {
        this.nugget = nugget ? new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(LCC.MODID, this.name().toLowerCase() + "_nugget") : null;
        this.ore = (ore != OreType.NONE) ? new BlockResourceOre(Block.Properties.create(Material.ROCK, EnumDyeColor.RED), this).setRegistryName(LCC.MODID, this.name().toLowerCase() + "_ore") : null;
        this.oreType = ore;
        this.ingot = ingot ? new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(LCC.MODID, this.name().toLowerCase() + "_ingot") : null;
        this.storage = storage ? new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.RED)).setRegistryName(LCC.MODID, this.name().toLowerCase() + "_storage") : null;
    }

    public enum OreType {
        NONE,
        DROP_SELF,
        DROP_INGOT;
    }

}
