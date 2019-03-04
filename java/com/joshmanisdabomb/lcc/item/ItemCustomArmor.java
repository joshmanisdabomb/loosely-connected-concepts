package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemCustomArmor extends ItemArmor {

    public ItemCustomArmor(IArmorMaterial material, EntityEquipmentSlot slot, Item.Properties builder) {
        super(material, slot, builder);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return LCC.MODID + ":textures/models/armor/" + material.getName() + "_layer_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + (type == null ? "" : ("_" + type)) + ".png";
    }
}
